package com.dingding.dingalarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PlayReceiver extends BroadcastReceiver {
    String CHANNEL_ID ="11";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bData = intent.getExtras();
        if(bData.get("msg").equals("3")){
            Log.d("xding", "執行3");

            notify(context, "3分鐘到");

        } else if(bData.get("msg").equals("5")){
            Log.d("xding", "執行5");
            notify(context, "5分鐘到");
        } else{
            Log.d("xding", "自定執行" + bData.get("msg"));
            notify(context, bData.get("msg") + "秒鐘到");

        }



//        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void notify(Context context, String text){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(text)
                .setContentText("text")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "時間到", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("提醒鬧鐘");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());

    }
}
