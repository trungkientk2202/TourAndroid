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
        Tour tour=Common.getTours().poll();
        LichTrinh lichTrinh=Common.getLichTrinhs().poll();
        String content="";
        if(lichTrinh!=null){
            content+="Ngày bắt đầu:"+lichTrinh.getThoiGianBatDau()+"\r\n"
                    +"Địa điểm:"+lichTrinh.getDiaDiem().getTenDiaDiem()
                    +"\r\n"+lichTrinh.getDiaDiem().getMoTa();
        }else{
            content+="Ngày bắt đầu:"+tour.getNgayBatDau()+"\r\n"+tour.getMoTa();
        }
        Log.i("Content:",content);
        Log.i("Mã tour:",tour.getMaTour()+"");
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notifyTour")
                .setSmallIcon(R.drawable.icon_notify)
                .setContentInfo("Tour của bạn: "+tour+" đến "+ tour.getDiemDen())
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(new Random().nextInt(),builder.build());
   }
}
