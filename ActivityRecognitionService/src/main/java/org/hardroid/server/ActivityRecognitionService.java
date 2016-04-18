/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hardroid.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.hardroid.common.ActivityRecognitionResult;
import org.hardroid.common.IActivityRecognitionResponseListener;
import org.hardroid.model.DecisionTreeClassifier;
import org.hardroid.model.WekaClassifier;
import org.hardroid.utils.DexModelLoader;

import java.util.Collections;
import java.util.LinkedHashMap;

import io.fabric.sdk.android.Fabric;

/**
 * This class implements the Activity Recognition Service
 */
public class ActivityRecognitionService extends Service {
    public static final String TAG = ActivityRecognitionService.class.getSimpleName();
    private ActivityRecognitionManagerImpl service;
    private ActivityRecognitionWorker worker;
    private boolean running = false;
    private final LinkedHashMap<String, ActivityRecognitionSubscription> subscriptions = new LinkedHashMap<>();
    private ActivityRecognitionResult result;
    private DexModelLoader modelLoader;

    @Override
    public IBinder onBind(Intent intent) {
        onServiceStarted();
        return service;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        service = new ActivityRecognitionManagerImpl(this);
        worker = new ActivityRecognitionWorker(this);
        modelLoader = new DexModelLoader(this);
        Fabric.with(this, new Crashlytics());
    }

    @Override
    public void onDestroy() {
        running = false;
        worker.cancel();
        Log.i(TAG, "Activity Recognition Service destroyed");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Client disconected");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        onServiceStarted();
        return START_STICKY;
    }

    public void onServiceStarted() {
        if (!running) {
            Log.v(TAG, "Activity Recognition Service started");
            running = true;
        }

        WekaClassifier model = modelLoader.
                retrieveModel(getString(R.string.model_name));
        if (model != null) {
            worker.setActivityClassifier(
                    new DecisionTreeClassifier(
                        model
                    )
            );
        }
    }

    /**
     * Returns the ActivityRecognitionResult indicating the data from the last HumanActivity
     *
     * @return last known HumanActivity
     */
    protected ActivityRecognitionResult getResult() {
        return result;
    }

    /**
     * Method to update activity recognition results.
     *
     * @param result {@link ActivityRecognitionResult} to be published
     */
    protected void publishResult(ActivityRecognitionResult result) {
        synchronized (result) {
            this.result = result;
            for (String clientId: subscriptions.keySet()) {
                ActivityRecognitionSubscription client = subscriptions.get(clientId);
                long now = System.currentTimeMillis();
                if (now - client.getLastUpdateTime() > client.getDetectionInterval()) {
                    IActivityRecognitionResponseListener listener = client.getListener();
                    try {
                        if (listener.asBinder().isBinderAlive()) {
                            Log.d(TAG, "Updating subscriber activities");
                            listener.onResponse(getResult());
                            client.setLastUpdateTime(now);
                        } else {
                            removeClient(clientId, listener);
                        }
                    } catch (RemoteException e) {
                        Log.d(TAG, "Client is dead, unsubscribing");
                        removeClient(clientId, listener);
                    }
                }
            }
        }
    }

    /**
     * Constructs a client subscription that will perform request updates.
     *
     * @param appId client application identification
     * @param detectionIntervalMillis time between updates
     * @param listener {@link IActivityRecognitionResponseListener} client binder object reference to
     *                                                             callback results
     */
    protected void addClient(String appId, long detectionIntervalMillis,
                             IActivityRecognitionResponseListener listener) {
        if (!subscriptions.containsKey(appId) && listener != null) {
            Log.i(TAG, "Subscribing client " + appId);
            subscriptions.put(appId, new ActivityRecognitionSubscription(appId,
                    detectionIntervalMillis, listener));
            ActivityRecognitionSubscription min = Collections.min(subscriptions.values(),
                    new ActivityRecognitionSubscription.DetectionTimeComparator());
            worker.starTimer(min.getDetectionInterval());
        }
    }

    /**
     * Removes a client subscription
     * @param appId client application identification
     * @param listener {@link IActivityRecognitionResponseListener} client binder object reference to
     *                                                             callback results
     */
    protected void removeClient(String appId, IActivityRecognitionResponseListener listener) {
        if (subscriptions.containsKey(appId) && listener != null) {
            Log.i(TAG, "Unsubscribing client " + appId);
            subscriptions.remove(appId);
            if (subscriptions.size() > 0) {
                ActivityRecognitionSubscription min = Collections.min(subscriptions.values(),
                        new ActivityRecognitionSubscription.DetectionTimeComparator());;
                worker.starTimer(min.getDetectionInterval());
            }
            else {
                worker.stopTimer();
            }
        }
    }

}
