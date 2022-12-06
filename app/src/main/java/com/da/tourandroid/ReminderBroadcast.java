package com.da.tourandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.da.tourandroid.utils.Common;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String content="";
        if(Common.getLichTrinh()!=null){
            content+="Ngày bắt đầu:"+Common.getLichTrinh().getThoiGianBatDau()+"\r\n"
                    +"Địa điểm:"+Common.getLichTrinh().getDiaDiem().getTenDiaDiem()
                    +"\r\n"+Common.getLichTrinh().getDiaDiem().getMoTa();
        }else{
            content+="Ngày bắt đầu:"+Common.getTour().getNgayBatDau()+"\r\n"+Common.getTour().getMoTa();
        }
        Log.d("Content:",content);
        Log.d("Điểm đến:",Common.getTour().getDiemDen());
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notifyTour")
                .setSmallIcon(R.drawable.icon_notify)
                .setContentInfo("Tour của bạn: "+Common.getTour().getDiemDi()+" đến "+ Common.getTour().getDiemDen())
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,builder.build());
   }
}
