package org.harsurvey.android.survey;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.hardroid.common.HumanActivity;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationReceiver.class.getSimpleName();

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SurveyApplication app= ((SurveyApplication) context.getApplicationContext());
        if (intent.getAction().equals(Constants.DETECTED_ACTIVITY_BROADCAST) && !app.isOnTop()) {
            Log.d(TAG, "Nuevos datos disponibles");
            HumanActivity activity = intent.getParcelableExtra(Constants.DETECTED_ACTIVITY_EXTRA);

            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent activityIntent = new Intent(context, FeedActivity.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, -1, activityIntent,
                    PendingIntent.FLAG_ONE_SHOT);

            Resources resources = context.getResources();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setLargeIcon(Constants.getBitmapIcon(context.getApplicationContext(),
                            R.drawable.ic_notification_unkown))
                    .setContentTitle(resources.getString(R.string.notification_title))
                    .setContentText(resources.getString(R.string.notification_text))
                    .setSubText(resources.getString(R.string.notification_description))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            notificationManager.notify(1, builder.build());
        }
    }
}
