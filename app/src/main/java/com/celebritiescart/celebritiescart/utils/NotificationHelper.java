package com.celebritiescart.celebritiescart.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;



/**
 * NotificationHelper is used to create new Notifications
 **/

public class NotificationHelper {


    public static final int NOTIFICATION_REQUEST_CODE = 100;


    //*********** Used to create Notifications ********//

    public static void showNewNotification(Context context, Intent intent, String title, String msg) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showNewNotificationMod(context,intent,title,msg);

        } else {
            String channelId = "channel-01";
            Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            Intent notificationIntent;

            if (intent != null) {
                notificationIntent = intent;
            } else {
                notificationIntent = new Intent(context, MainActivity.class);
            }

            PendingIntent pendingIntent = PendingIntent.getActivity((context), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            // Create Notification
            Notification notification = builder
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setTicker(context.getString(R.string.app_name))
                    .setColor(context.getResources().getColor( R.color.colorPrimary))
                    .setSmallIcon(R.mipmap.ic_launcher)

                    .setSound(notificationSound)
                    .setLights(Color.RED, 3000, 3000)
                    .setVibrate(new long[]{1000, 1000})
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();


            notificationManager.notify(NOTIFICATION_REQUEST_CODE, notification);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void showNewNotificationMod(Context context, Intent intent, String title, String body) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String channelId = "channel-01";
        String channelName = "Products Info";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(body)
                .setTicker(context.getString(R.string.app_name))
                .setColor(context.getColor( R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(notificationSound)
                .setLights(Color.RED, 3000, 3000)
                .setVibrate(new long[]{1000, 1000})
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true);

        PendingIntent resultPendingIntent;
        try {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(intent);


            resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

        } catch (Exception e) {
            Intent notificationIntent;

            if (intent != null) {
                notificationIntent = intent;
            } else {
                notificationIntent = new Intent(context, MainActivity.class);
            }

            resultPendingIntent = PendingIntent.getActivity((context), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        }

        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_REQUEST_CODE, mBuilder.build());
    }


}

