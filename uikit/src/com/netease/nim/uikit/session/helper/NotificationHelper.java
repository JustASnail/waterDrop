package com.netease.nim.uikit.session.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.netease.nim.uikit.R;


/**
 * Created by hzliuxuanlin on 16/10/22.
 */
public class NotificationHelper {
    private Context context;

    private NotificationManager notificationManager;
    private Notification notification;

    public NotificationHelper(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void activeCallingNotification(boolean active, String title,String msg, int notifyId, Class<? extends AppCompatActivity> clazz) {
        if (notificationManager != null) {
            if (active) {
                buildCallingNotification(title, msg, notifyId, clazz);
                notificationManager.notify(notifyId, notification);
            } else {
                notificationManager.cancel(notifyId);
            }
        }
    }

    private void buildCallingNotification(String title, String msg, int notifyId, Class<? extends AppCompatActivity> clazz) {
        if (notification == null) {
            Intent localIntent = new Intent();
            localIntent.setClass(context, clazz);
            localIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            int iconId = R.drawable.ic_func_location;

            PendingIntent pendingIntent = PendingIntent.getActivity(context, notifyId, localIntent, PendingIntent
                    .FLAG_UPDATE_CURRENT);
            notification = makeNotification(pendingIntent, title, msg, msg, iconId, true, true);
        }
    }

    private Notification makeNotification(PendingIntent pendingIntent, String title, String content, String tickerText,
                                          int iconId, boolean ring, boolean vibrate) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(tickerText)
                .setSmallIcon(iconId);
        int defaults = Notification.DEFAULT_LIGHTS;
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (ring) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        builder.setDefaults(defaults);

        return builder.build();
    }
}
