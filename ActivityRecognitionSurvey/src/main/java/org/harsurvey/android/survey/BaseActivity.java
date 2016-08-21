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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.harsurvey.android.util.Constants;

public class BaseActivity extends AppCompatActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();

    public SurveyApplication app;
    private FloatingActionButton actionButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (SurveyApplication) getApplication();

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionButton = (FloatingActionButton) findViewById(R.id.fab);
        if (actionButton != null) {
            actionButton.setOnClickListener(onActionButtonClick);
            actionButton.setVisibility(View.INVISIBLE);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.SERVICE_CHANGE);
        registerReceiver(serviceStatusReceiver, filter);
    }

    protected void setToolbarTitle(int stringResouce) {
        if (toolbar != null) {
            toolbar.setTitle(stringResouce);
        }
    }

    protected void setActionButtonVisibility(boolean visible) {
        if (visible) {
            actionButton.setVisibility(View.VISIBLE);
        }
        else {
            actionButton.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(serviceStatusReceiver);
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

        return super.onOptionsItemSelected(item);
    }

    protected void setDetectorService(boolean active) {
        Intent intent = new Intent(this, DetectorService.class);
        if (active) {
            startService(intent);
        }
        else {
            stopService(intent);
        }
    }

    protected void setActionButtonStatus() {
        String message;
        if (app.getConnection().isClientConnected()) {
            message = Constants.getStringResource(this, R.string.connected);
            actionButton.setImageResource(R.drawable.ic_stop);
        }
        else {
            message = Constants.getStringResource(this, R.string.disconnected);
            actionButton.setImageResource(R.drawable.ic_play_arrow);
        }
        setActionButtonVisibility(true);
        Snackbar.make(findViewById(R.id.main_content), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private BroadcastReceiver serviceStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Constants.SERVICE_CHANGE)) {
                BaseActivity.this.setActionButtonStatus();
            }
        }
    };

    private View.OnClickListener onActionButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (app.getConnection().isClientConnected()) {
                setDetectorService(false);
                app.getPreference().disableService();
            }
            else {
                app.getPreference().enableService();
                setDetectorService(true);
            }
        }
    };
}
