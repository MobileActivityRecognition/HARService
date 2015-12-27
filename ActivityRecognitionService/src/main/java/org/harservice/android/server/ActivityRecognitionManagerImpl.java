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

package org.harservice.android.server;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;

import org.harservice.android.common.ActivityRecognitionResult;
import org.harservice.android.common.HumanActivity;
import org.harservice.android.common.IActivityRecognitionManager;
import org.harservice.android.common.IActivityRecognitionResponseListener;

import java.util.HashMap;
import java.util.Timer;

/**
 * IBinder activity recognition manager implementation
 */
public class ActivityRecognitionManagerImpl extends IActivityRecognitionManager.Stub {
    public static final String TAG = ActivityRecognitionManagerImpl.class.getSimpleName();
    private HashMap<String, ActivityRecognitionSubscription> clients = new HashMap<>();
    private static ActivityRecognitionResult result;
    private Context context;
    private Timer timer;

    /**
     * Private constructor
     *
     * Don't instantiate the object directly
     */
    private ActivityRecognitionManagerImpl() {
    }

    /**
     * Constructs a {@link IActivityRecognitionManager} binder implementation
     *
     * @param context Android service context
     * @param timer Global timer object
     */
    public ActivityRecognitionManagerImpl(Context context, Timer timer) {
        this.context = context;
        result = new ActivityRecognitionResult(new HumanActivity(HumanActivity.Type.UNKNOWN, 100),
                0, 0);
        this.timer = timer;
    }

    /**
     * Register for activity recognition updates using a callback.
     *
     * @param detectionIntervalMillis time interval between activity recognition updates, in milliseconds
     * @param listener a {@link IActivityRecognitionResponseListener} whose
     * {@link IActivityRecognitionResponseListener#onResponse} will be called for each update
     * @throws SecurityException if no suitable permission is present
     */
    @Override
    public void requestActivityUpdates(long detectionIntervalMillis,
                                       IActivityRecognitionResponseListener listener)
            throws SecurityException {
        String clientId = getClientId();
        if (!this.clients.containsKey(clientId)) {
            Log.i(TAG, "Subscribing client " + clientId);
            this.clients.put(clientId, new ActivityRecognitionSubscription(this, listener));
            this.timer.schedule(this.clients.get(clientId), detectionIntervalMillis,
                    detectionIntervalMillis);
        }
    }

    /**
     * Request for a single activity recognition update using a callback.
     *
     * @param listener a {@link IActivityRecognitionResponseListener} whose
     * {@link IActivityRecognitionResponseListener#onResponse} will be called the update
     * @throws RemoteException if the calling thread failed to response
     * @throws SecurityException if no suitable permission is present
     */
    @Override
    public void requestSingleUpdates(IActivityRecognitionResponseListener listener)
            throws SecurityException, RemoteException {
        listener.onResponse(getResult());
    }

    /**
     * Removes activity recognition updates for the specified callback
     *
     * @param listener a {@link IActivityRecognitionResponseListener} whose
     * {@link IActivityRecognitionResponseListener#onResponse} called for each update
     * @throws SecurityException if no suitable permission is present
     */
    @Override
    public void removeActivityUpdates(IActivityRecognitionResponseListener listener)
            throws SecurityException {
        String clientId = getClientId();
        removeActivityUpdates(clientId);
    }

    /**
     * Inner removes activity recognition updates for the specified PID*
     * @param clientId
    */
    protected void removeActivityUpdates(String clientId) {
        if (this.clients.containsKey(clientId)) {
            Log.i(TAG, "Unsubscribing client " + clientId);
            this.clients.get(clientId).cancel();
            this.clients.remove(clientId);
        }
    }

    /**
     * Returns a ActivityRecognitionResult indicating the data from the last known HumanActivity
     *
     * @return the last known HumanActivity
     * @throws SecurityException if no suitable permission is present
     */
    @Override
    public ActivityRecognitionResult getDetectedActivities()
            throws SecurityException {
        return getResult();
    }

    /**
     * Returns the client subscribers count
     *
     * @return subscribers count
     */
    public int getClientCount() {
        return this.clients.size();
    }

    /**
     * Returns the ActivityRecognitionResult indicating the data from the last HumanActivity
     *
     * @return last known HumanActivity
     */
    public ActivityRecognitionResult getResult() {
        return result;
    }

    /**
     * Sets the last known HumanActivity
     *
     * @param otherResult last known HumanActivity
     */
    public synchronized void setResult(ActivityRecognitionResult otherResult) {
        result = otherResult;
    }

    /**
     * Helper to get the connected client application ID
     *
     * @return client application ID
     */
    private String getClientId() throws SecurityException{
        int pid = Binder.getCallingPid();
        //String appId = this.context.getPackageManager().getPackagesForUid(pid)[0];
        String appId = String.valueOf(pid);
        if (this.context.checkCallingPermission(
                ActivityRecognitionService.ACCESS_ACTIVITY_RECOGNITION)
                == PackageManager.PERMISSION_GRANTED) {
            return appId;
        }
        else{
            Log.w(TAG, "Permision denied on ACTIVITY RECOGNITION " + appId);
            throw new SecurityException("Permission Denied");
        }
    }
}
