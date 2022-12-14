package com.da.tourandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import java.util.Random;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int a=new Random().nextInt();
        Log.i("id",a+",size: "+Common.getLichTrinhs().size());
        Tour tour;
        LichTrinh lichTrinh=Common.getLichTrinhs().poll();
        String content="";
        if(lichTrinh!=null){
            tour=lichTrinh.getTour();
            content+="Ngày bắt đầu:"+lichTrinh.getThoiGianBatDau()+"\r\n"
                    +"Địa điểm:"+lichTrinh.getDiaDiem().getTenDiaDiem()
                    +"\r\n"+lichTrinh.getDiaDiem().getMoTa();
        }else{
            tour=Common.getTours().poll();
            content+="Ngày bắt đầu:"+tour.getNgayBatDau()+"\r\n"+tour.getMoTa();
        }
        Log.i("Content:",content);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notifyTour")
                .setSmallIcon(R.drawable.icon_notify)
                .setContentTitle("Tour của bạn: "+tour.getDiemDi()+" đến "+ tour.getDiemDen())
//                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(a,builder.build());
        Log.i("done","");
   }
}
