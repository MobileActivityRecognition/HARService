package org.harsurvey.android.survey;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.hardroid.common.HumanActivity;
import org.harsurvey.android.util.Constants;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationReceiver.class.getSimpleName();

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SurveyApplication app = (SurveyApplication) context.getApplicationContext();
        if (intent.getAction().equals(Constants.DETECTED_ACTIVITY_BROADCAST) && !app.isOnTop()) {
            Log.d(TAG, "Nuevos datos disponibles");

            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent activityIntent = new Intent(context, FeedActivity.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, -1, activityIntent,
                    PendingIntent.FLAG_ONE_SHOT);

            HumanActivity activity = intent.getParcelableExtra(Constants.DETECTED_ACTIVITY_EXTRA);
            int smallIcon = R.drawable.ic_notification_ok;
            if (activity.getType().equals(HumanActivity.Type.UNKNOWN)) {
                smallIcon = R.drawable.ic_notification_nok;
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(smallIcon)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setLargeIcon(Constants.getBitmapIcon(context.getApplicationContext(),
                            activity.getType()))
                    .setContentTitle(Constants.getStringResource(context, R.string.notification_title))
                    .setContentText(Constants.getStringResource(context, R.string.notification_text))
                    .setSubText(Constants.getStringResource(context, R.string.notification_description))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            notificationManager.notify(1, builder.build());
        }
    }
}
