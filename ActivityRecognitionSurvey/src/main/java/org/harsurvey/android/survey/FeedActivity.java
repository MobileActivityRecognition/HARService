/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.harsurvey.android.survey;
;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import org.harservice.android.common.HumanActivity;
import org.harsurvey.android.cards.Card;
import org.harsurvey.android.cards.CardStreamLinearLayout;
import org.harsurvey.android.cards.DetectedActivitiesAdapter;
import org.harsurvey.android.cards.OnCardClickListener;
import org.harsurvey.android.data.HumanActivityData;

import java.util.List;

/**
 * Show CardView Feed activity
 */
public class FeedActivity extends BaseActivity implements OnCardClickListener {
    CardStreamLinearLayout listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cardstream);
        listView = (CardStreamLinearLayout) findViewById(R.id.card_stream);
        List<HumanActivityData> activities = getUpdates();
        listView.setAdapter(new DetectedActivitiesAdapter(this, activities, this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        app.connect();
        if (!app.isClientConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(activityReceiver,
                new IntentFilter(Config.DETECTED_ACTIVITY_BROADCAST));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(activityReceiver);
    }

    public List<HumanActivityData> getUpdates() {
        return app.getActivitiesUpdates();
    }

    @Override
    public void onCardClick(int action, String tag) {
        Long id = Long.valueOf(tag.split("_")[1]);
        if (action == Card.ACTION_POSITIVE) {
            app.acceptActivity(id);
        }
        else {
            app.rejectActivity(id);
        }
    }

    private BroadcastReceiver activityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            app.addActivity((HumanActivity) intent.getParcelableExtra(Config.DETECTED_ACTIVITY_EXTRA));
        }
    };
}
