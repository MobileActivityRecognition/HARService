/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.harservice.android.client;

import android.app.PendingIntent;
import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import org.harservice.android.api.ActivityRecognitionApi;
import org.harservice.android.api.ConnectionApi;
import org.harservice.android.common.ActivityRecognitionResult;
import org.harservice.android.common.IActivityRecognitionManager;

/**
 * This class provides access to the activity recognition services. This services allow to obtain
 * periodic updates of the human activity recognition, or fire and application-specific Intent.
 *
 * Don't instantiate this class directly, retrieve it through
 * {@link ActivityRecognitionClient#getService()}
 */
public class ActivityRecognitionManager implements ActivityRecognitionApi {
    public static final String TAG = ActivityRecognitionManager.class.getSimpleName();
    private IActivityRecognitionManager service;
    private ConnectionApi connection;
    private ActivityRecognitionUpdatesReceiver updatesReceiver;

    /**
     * @param connection
     * @param service
     * @param context
     */
    protected ActivityRecognitionManager(ConnectionApi connection, IBinder service, Context context) {
        this.connection = connection;
        this.service = IActivityRecognitionManager.Stub.asInterface(service);
        this.updatesReceiver = new ActivityRecognitionUpdatesReceiver(context, this);
    }

    @Override
    public void requestActivityUpdates(long detectionIntervalMillis, PendingIntent callbackIntent) {
        removeActivityUpdates(this.updatesReceiver.getListener());
        this.updatesReceiver.setCallback(callbackIntent);
        registerActivityUpdates(detectionIntervalMillis);
    }

    @Override
    public void requestActivityUpdates(long detectionIntervalMillis, ActivityRecognitionListener listener) {
        removeActivityUpdates(this.updatesReceiver.getCallback());
        this.updatesReceiver.setListener(listener);
        registerActivityUpdates(detectionIntervalMillis);
    }

    @Override
    public void requestActivityUpdates(long detectionIntervalMillis, ActivityRecognitionListener listener, Looper looper) {
        this.requestActivityUpdates(detectionIntervalMillis, listener);
    }

    @Override
    public void removeActivityUpdates(PendingIntent callbackIntent) {
        PendingIntent myPendingIntent = this.updatesReceiver.getCallback();
        if (myPendingIntent != null && myPendingIntent.equals(callbackIntent)) {
            this.updatesReceiver.setCallback(null);
            this.removeActivityUpdates();
        }
    }

    @Override
    public void removeActivityUpdates(ActivityRecognitionListener listener) {
        ActivityRecognitionListener myListener = this.updatesReceiver.getListener();
        if (myListener != null && myListener.equals(listener)) {
            this.updatesReceiver.setListener(null);
            this.removeActivityUpdates();
        }
    }

    @Override
    public void requestSingleUpdate(PendingIntent callbackIntent) {
        if (this.updatesReceiver.getCallback() == null) {
            this.updatesReceiver.setCallback(callbackIntent);
        }
        try {
            this.service.requestSingleUpdates(this.updatesReceiver);
        } catch (RemoteException e) {
            if (e.getMessage() != null) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * @return
     */
    public ActivityRecognitionResult getResults() {
        try {
            if (this.connection.isConnected()) {
                return this.service.getDetectedActivities();
            }
            else {
                // TODO: Reportar adecuadamente el error/excepcion
                return null;
            }
        } catch (RemoteException e) {
            if (e.getMessage() != null) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }
    }

    private void registerActivityUpdates(long detectionIntervalMillis) {
        try {
            if (this.connection.isConnected()) {
                this.service.requestActivityUpdates(detectionIntervalMillis, this.updatesReceiver);
            }
        } catch (RemoteException e) {
            if (e.getMessage() != null) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void removeActivityUpdates() {
        try {
            if (this.connection.isConnected()) {
                this.service.removeActivityUpdates(this.updatesReceiver);
            }
        } catch (RemoteException e) {
            if (e.getMessage() != null) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

}
