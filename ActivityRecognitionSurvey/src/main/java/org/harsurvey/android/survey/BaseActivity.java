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

package org.harsurvey.android.survey;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.harsurvey.android.util.Constants;

public class BaseActivity extends AppCompatActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();

    public SurveyApplication app;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (SurveyApplication) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        setMenuStatus();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.SERVICE_CHANGE);
        registerReceiver(hideMenuReceiver, filter);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(hideMenuReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            return true;
        }
        else if (id == R.id.help) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.getStringResource(this, R.string.help_url))));
        }
        else if (id == R.id.action_service_active) {
            setDetectorService(false);
            app.getPreference().disableService();
        }
        else if (id == R.id.action_service_inactive) {
            app.getPreference().enableService();
            setDetectorService(true);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setDetectorService(boolean active) {
        if (!app.getPreference().getServiceStatus()) {
            return;
        }
        Intent intent = new Intent(this, DetectorService.class);
        if (active) {
            startService(intent);
        }
        else {
            stopService(intent);
        }
    }

    private void setMenuStatus() {
        if (app.getConnection().isClientConnected()) {
            this.menu.getItem(Constants.ACTION_BUTTON_ACTIVE).setVisible(true);
            this.menu.getItem(Constants.ACTION_BUTTON_INACTIVE).setVisible(false);
        }
        else {
           this.menu.getItem(Constants.ACTION_BUTTON_ACTIVE).setVisible(false);
           this.menu.getItem(Constants.ACTION_BUTTON_INACTIVE).setVisible(true);
        }
    }

    private BroadcastReceiver hideMenuReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Constants.SERVICE_CHANGE)) {
                BaseActivity.this.setMenuStatus();
            }
        }
    };
}
