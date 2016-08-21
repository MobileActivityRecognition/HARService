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

package org.harsurvey.android.util;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings.Secure;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;

import org.hardroid.common.HumanActivity.Type;
import org.harsurvey.android.survey.R;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Configuration Parameters
 */
public class Constants {
    /**
     * Set default clock units
     */
    public static final int SECOND = 1000;
    public static final int MINUTE = 60*SECOND;
    public static final int HOUR = 60*MINUTE;

    /**
     * Set default INTERVAL TIME
     */
    public static final int INTERVAL_DEFAULT = 1*MINUTE;

    public static final String NONE = "N/A";

    public static final String DATABASE_NAME = "activities.db";
    public static final int DATABASE_VERSION = 4;

    /**
     * Actions
     */
    public static final String DETECTED_ACTIVITY_BROADCAST = "org.harsurvey.android.DETECTED_ACTIVITY_BROADCAST";
    public static final String DETECTED_ACTIVITY_EXTRA = "org.harsurvey.android.DETECTED_ACTIVITY_EXTRA";
    public static final String REQUEST_SYNCRONIZATION = "org.harsurvey.android.REQUEST_SYNCRONIZATION";
    public static final String SERVICE_CHANGE = "org.harsurvey.android.SERVICE_CHANGE";

    public static final int ACTION_BUTTON_REFRESH = 0;
    public static final int ACTION_BUTTON_ACTIVE = 0;
    public static final int ACTION_BUTTON_INACTIVE = 1;

    public static final String REST_URL = "%sARrecolector/webresources/com.fpuna.entities.collaborativesession";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String[] ACTIVITY_LIST = {
            Type.STILL.toString(),
            Type.WALKING.toString(),
            Type.RUNNING.toString(),
            Type.IN_VEHICLE.toString(),
            Type.ON_BICYCLE.toString()
    };

    public static final String INTRO_CARD = "INTRO";
    public static final int MAX_CARDS = 5;

    public static Map<Type, Integer> activityIcon = new LinkedHashMap<>();
    public static Map<Type, Integer> activityIconSmall = new LinkedHashMap<>();

    static {
        activityIcon.put(Type.IN_VEHICLE, R.drawable.ic_activity_car);
        activityIcon.put(Type.ON_BICYCLE, R.drawable.ic_activity_bike);
        activityIcon.put(Type.RUNNING, R.drawable.ic_activity_run);
        activityIcon.put(Type.STILL, R.drawable.ic_activity_still);
        activityIcon.put(Type.TILTING, R.drawable.ic_activity_tilt);
        activityIcon.put(Type.UNKNOWN, R.drawable.ic_activity_unk);
        activityIcon.put(Type.WALKING, R.drawable.ic_activity_walk);

        activityIconSmall.put(Type.IN_VEHICLE, R.drawable.ic_notification_car);
        activityIconSmall.put(Type.ON_BICYCLE, R.drawable.ic_notification_bike);
        activityIconSmall.put(Type.RUNNING, R.drawable.ic_notification_run);
        activityIconSmall.put(Type.STILL, R.drawable.ic_notification_still);
        activityIconSmall.put(Type.TILTING, R.drawable.ic_notification_tilt);
        activityIconSmall.put(Type.UNKNOWN, R.drawable.ic_notification_unk);
        activityIconSmall.put(Type.WALKING, R.drawable.ic_notification_walk);
    }

    public static String getActivityString(Context context, Type detectedActivityType) {
        Resources resources = context.getResources();
        switch(detectedActivityType) {
            case IN_VEHICLE:
                return resources.getString(R.string.in_vehicle);
            case ON_BICYCLE:
                return resources.getString(R.string.on_bicycle);
            case RUNNING:
                return resources.getString(R.string.running);
            case STILL:
                return resources.getString(R.string.still);
            case UNKNOWN:
                return resources.getString(R.string.unknown);
            case WALKING:
                return resources.getString(R.string.walking);
            case TILTING:
                return resources.getString(R.string.tilting);
            default:
                return resources.getString(R.string.unidentifiable_activity,
                        detectedActivityType.toString());
        }
    }

    public static Drawable getActivityIcon(Context context, Type detectedActivity) {
        return ContextCompat.getDrawable(context, activityIcon.get(detectedActivity));
    }

    public static Bitmap getBitmapIcon(Context context, Type detectedActivity) {
        return getBitmapIcon(context, activityIconSmall.get(detectedActivity));
    }

    public static Bitmap getBitmapIcon(Context context, int resource) {
        return BitmapFactory.decodeResource(context.getResources(),
                resource);
    }

    public static String getStringResource(Context context, int resource) {
        return context.getResources().getString(resource);
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static String getDeviceOwner() {
        String name = android.os.Build.USER;

        if (!TextUtils.isEmpty(name)) {
            return name;
        }
        return null;
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getDeviceId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        String deviceId = "";
        if (!"9774d56d682e549c".equals(androidId)) {
            deviceId = UUID.nameUUIDFromBytes(androidId.getBytes()).toString();
        }
        else {
            deviceId = UUID.randomUUID().toString();
        }
        return deviceId;
    }

    public static String formatShortDate(Context context, long time) {
        return DateUtils.formatDateTime(context, time, DateUtils.FORMAT_SHOW_DATE |
                DateUtils.FORMAT_SHOW_TIME);
    }
}
