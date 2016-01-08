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

import android.os.RemoteException;
import android.util.Log;

import org.harservice.android.common.IActivityRecognitionManager;
import org.harservice.android.common.IActivityRecognitionResponseListener;

import java.util.TimerTask;

/**
 * This class holds an Activity Recognition client subscription
 */
public class ActivityRecognitionSubscription extends TimerTask {
    public static final String TAG = ActivityRecognitionSubscription.class.getSimpleName();
    private String appId;
    private ActivityRecognitionManagerImpl manager;
    private IActivityRecognitionResponseListener listener;

    /**
     * Private constructor
     *
     * Don't create objects directly
     */
    private ActivityRecognitionSubscription() {
    }

    /**
     * Constructs a client subscription that will perform request updates.
     *
     * @param manager {@link IActivityRecognitionManager} to remove request updates if the client
     *                                                   binder is death
     * @param listener {@link IActivityRecognitionResponseListener} client binder object reference to
     *                                                             callback results
     */
    protected ActivityRecognitionSubscription(ActivityRecognitionManagerImpl manager,
                                           IActivityRecognitionResponseListener listener,
                                              final String appId) {
        Log.d(TAG, "Creating new subscriber thread");
        this.manager = manager;
        this.listener = listener;
        this.appId = appId;
    }

    @Override
    public void run() {
        try {
            if (this.listener.asBinder().isBinderAlive()) {
                Log.d(TAG, "Updating subscriber activities");
                this.listener.onResponse(this.manager.getResult());
            }
            else {
                this.manager.removeActivityUpdates(String.valueOf(appId));
            }
        } catch (RemoteException e) {
            this.manager.removeActivityUpdates(appId);
            if (e.getMessage() != null) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
