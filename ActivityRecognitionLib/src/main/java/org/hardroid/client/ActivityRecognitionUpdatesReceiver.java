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

package org.hardroid.client;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import org.hardroid.common.ActivityRecognitionResult;
import org.hardroid.common.IActivityRecognitionResponseListener;


/**
 * This class provides interaction with the alarm manager in order to periodically get activity
 * recognition results
 *
 * @author agimenez
 */
public class ActivityRecognitionUpdatesReceiver extends IActivityRecognitionResponseListener.Stub {
    public static final String TAG = ActivityRecognitionUpdatesReceiver.class.getSimpleName();

    private Context context;
    private ActivityRecognitionManager manager;
    private PendingIntent callback;
    private ActivityRecognitionListener listener;
    private Handler handler;

    /**
     * Restricted constructor
     *
     * @param context
     *        android context
     * @param manager
     *        service connection manager
     */
    protected ActivityRecognitionUpdatesReceiver(Context context, ActivityRecognitionManager manager) {
        this.context = context;
        this.manager = manager;
        handler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                ActivityRecognitionUpdatesReceiver.this.sendIntent((ActivityRecognitionResult) msg.obj);
            }
        };
    }

    @Override
    public void onResponse(ActivityRecognitionResult result) throws RemoteException {
        Message msg = ActivityRecognitionUpdatesReceiver.this.handler.obtainMessage(1, result);
        ActivityRecognitionUpdatesReceiver.this.handler.sendMessage(msg);
    }

    private void sendIntent(ActivityRecognitionResult result) {
        if (result != null) {
            if (this.callback != null) {
                Intent sendIntent = new Intent();
                sendIntent.putExtra(ActivityRecognitionResult.EXTRA_ACTIVITY_RESULT, result);
                try {
                    callback.send(this.context, 0, sendIntent);
                } catch (PendingIntent.CanceledException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            if (this.listener != null) {
                this.listener.onActivityChanged(result);
            }
        }
    }

    /**
     * Returns the registered callback object
     *
     * @return client callback
     */
    public PendingIntent getCallback() {
        return callback;
    }

    /**
     * Register a callback object
     *
     * @param callback
     *        client callback
     */
    public void setCallback(PendingIntent callback) {
        this.callback = callback;
    }

    /**
     * Returns the registered listener object
     *
     * @return client listener
     */
    public ActivityRecognitionListener getListener() {
        return listener;
    }

    /**
     * Register a listener object
     *
     * @param listener
     *        client listener
     */
    public void setListener(ActivityRecognitionListener listener) {
        this.listener = listener;
    }

}
