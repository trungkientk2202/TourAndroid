package com.da.tourandroid;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.da.tourandroid.utils.Common;

import java.util.Random;

public class NotifyBroadcast extends BroadcastReceiver {
//    @SuppressLint({"LaunchActivityFromNotification", "InlinedApi"})
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Notify: ","Điểm hẹn");
        int a=new Random().nextInt();
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getActivity(
                context, new Random().nextInt(), intent, PendingIntent.FLAG_MUTABLE| PendingIntent.FLAG_MUTABLE
        );
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notify")
                .setSmallIcon(R.drawable.icon_notify)
                .setContentTitle(Common.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Common.getContent()))
                .setFullScreenIntent(pendingIntent,true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(a,builder.build());
    }
}
