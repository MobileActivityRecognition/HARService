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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.harservice.android.api.ActivityRecognitionApi;
import org.harservice.android.api.ConnectionApi;
import org.harservice.android.common.IActivityRecognitionManager;

import java.util.List;

/**
 * The main entry point for Activity Recognition Service integration
 *
 * This class is used for requesting activity updates. This method requires that Connection
 * be connected.
 */
public class ActivityRecognitionClient implements ConnectionApi {
    public static final String TAG = ActivityRecognitionClient.class.getSimpleName();
    private Context context;
    private boolean connected = false;
    private boolean connecting = false;
    private ActivityRecognitionManager apiManager = null;
    private ComponentName componentName = null;

    public ActivityRecognitionClient(Context context) {
        this.context = context.getApplicationContext();

        checkInstalledService();
    }

    private boolean checkInstalledService() {
        Intent implicit = new Intent(IActivityRecognitionManager.class.getName());
        List<ResolveInfo> matches = this.context.getPackageManager()
                .queryIntentServices(implicit, 0);
        if (matches.size() == 0) {
            Toast.makeText(this.context, R.string.installed_message, Toast.LENGTH_SHORT).show();
            // TODO: Lanzar a google play store
            return false;
        }
        else {
            ServiceInfo svcInfo = matches.get(0).serviceInfo;
            componentName = new ComponentName(svcInfo.applicationInfo.packageName, svcInfo.name);
            return true;
        }
    }

    @Override
    public void connect() {
        if (componentName == null && !checkInstalledService()) {
            return;
        }
        Intent explicit = new Intent(IActivityRecognitionManager.class.getName());
        explicit.setComponent(componentName);
        if (!this.context.bindService(explicit, connection, Context.BIND_AUTO_CREATE)) {
            this.connected = false;
            this.connecting = false;
            Log.w(TAG, "Failed to bind service");
        }
        else {
            this.connecting = true;
        }
    }

    @Override
    public void disconnect() {
        if (this.isConnected()) {
            this.context.unbindService(connection);
            this.connected = false;
            this.connecting = false;
        }
    }

    @Override
    public void reconnect() {
        this.disconnect();
        this.connect();
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

    @Override
    public boolean isConnecting() {
        return this.connecting;
    }

    /**
     * @return
     */
    public ActivityRecognitionApi getService() {
        return this.apiManager;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "Service IBINDER connected");
            ActivityRecognitionClient.this.connected = true;
            ActivityRecognitionClient.this.connecting = false;
            ActivityRecognitionClient self = ActivityRecognitionClient.this;
            self.apiManager = new ActivityRecognitionManager(
                    self, iBinder, self.context
                    );
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "Service IBINDER disconnected");
            ActivityRecognitionClient self = ActivityRecognitionClient.this;
            self.apiManager = null;
            ActivityRecognitionClient.this.connected = false;
        }
    };
}
