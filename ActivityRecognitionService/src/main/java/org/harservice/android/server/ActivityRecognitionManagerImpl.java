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

import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;

import org.harservice.android.common.ActivityRecognitionResult;
import org.harservice.android.common.IActivityRecognitionManager;
import org.harservice.android.common.IActivityRecognitionResponseListener;

import java.util.Timer;

/**
 * IBinder activity recognition manager implementation
 */
public class ActivityRecognitionManagerImpl extends IActivityRecognitionManager.Stub {
    public static final String TAG = ActivityRecognitionManagerImpl.class.getSimpleName();
    private ActivityRecognitionService service;


    /**
     * Private constructor
     *
     * Don't instantiate the object directly
     */
    private ActivityRecognitionManagerImpl() {
    }

    /**
     * Constructs a {@link IActivityRecognitionManager} binder implementation
     *  @param service Android service service
     *
     */
    public ActivityRecognitionManagerImpl(ActivityRecognitionService service) {
        this.service = service;
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
        service.addClient(clientId, detectionIntervalMillis, listener);
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
        listener.onResponse(service.getResult());
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
        service.removeClient(clientId, listener);
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
        return service.getResult();
    }

    /**
     * Helper to get the connected client application ID
     *
     * @return client application ID
     */
    private String getClientId() throws SecurityException{
        int pid = Binder.getCallingPid();
        //String appId = this.service.getPackageManager().getPackagesForUid(pid)[0];
        String appId = String.valueOf(pid);
        if (this.service.checkCallingPermission(
                Constants.ACCESS_ACTIVITY_RECOGNITION)
                == PackageManager.PERMISSION_GRANTED) {
            return appId;
        }
        else{
            Log.w(TAG, "Permision denied on ACTIVITY RECOGNITION " + appId);
            throw new SecurityException("Permission Denied");
        }
    }
}
