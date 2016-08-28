/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 alberto
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

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;

import org.harsurvey.android.util.Constants;

/**
 * Notification Settings
 */
public class NotificationSettingsPopup {
    private final BaseActivity context;
    private final ArrayAdapter<String> listItems;
    private final String[] listValues;

    public NotificationSettingsPopup(Context context) {
        this.context = (BaseActivity) context;
        this.listItems = new ArrayAdapter<>(context, R.layout.listitem);
        this.listItems.addAll(Constants.getListResources(context, R.array.pref_notification_titles));
        this.listValues = Constants.getListResources(context, R.array.pref_notification_values);
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.pick_notification_title)
                .setAdapter(this.listItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int future = Integer.parseInt(listValues[i]);
                        context.app.getPreference().setNotificationOption(future);
                        String message = Constants.getStringResource(context, R.string.pick_notification_none);
                        if (future > 0) {
                            long now = Constants.getCurrentTime();
                            message = Constants.getStringResource(context, R.string.pick_notification_message) +
                            " " +
                            DateUtils.formatDateTime(context, now + future*Constants.HOUR,
                            DateUtils.FORMAT_SHOW_DATE |
                            DateUtils.FORMAT_SHOW_TIME);
                        }
                        context.showMessage(message);
                    }
                })
                .setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
