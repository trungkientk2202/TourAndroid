package com.da.tourandroid;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.model.DienDan;
import com.da.tourandroid.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NotifyBroadcast extends BroadcastReceiver {
    private RequestQueue requestQueue;
    @Override
    public void onReceive(Context context, Intent intent) {
        requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        int mode = Common.getNotify().poll();
        Log.i("mode: ",mode+"");
        String url = Common.getHost() + "thongBao/find/"+Common.getTour().getMaTour()+"/";
        switch (mode){
            case 1:
                url+=1; //diem danh
                break;
            case 2:
                url+=1; //nhac diem danh
                break;
            case 3:
                url+=2; //thong bao
                break;
            case 4:
                url+=3; //diem hen
                break;
        }
        Log.i("url:",url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        switch (mode){
                            case 1://diem danh
                                Common.setTitle(response.getString("noiDung"));
                                Common.setContent("Vui lòng điểm danh!");
                                break;
                            case 2://nhac diem danh
                                Common.setTitle(response.getString("noiDung"));
                                Common.setContent("Cảnh báo! Hãy điểm danh để Hướng dẫn viên được biết bạn đã có mặt!");
                                break;
                            case 3: //thong bao
                                Common.setTitle("Thông báo từ hướng dẫn viên:");
                                Common.setContent(response.getString("noiDung"));
                                break;
                            case 4://diem hen
                                Common.setTitle(response.getString("noiDung"));
                                Common.setContent("Thời gian: "+response.getString("thoiGian"));
                                break;
                        }
                        Log.i("title: ",Common.getTitle());

                        Log.i("Content: ",Common.getContent());
                        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notify")
                                .setSmallIcon(R.drawable.icon_notify)
                                .setContentTitle(Common.getTitle())
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(Common.getContent()))
                                .setPriority(NotificationCompat.PRIORITY_MAX);
                        if(mode==2||mode==3){
                            Uri sound=Uri.parse("android.resource://" + context.getPackageName()+"/"+R.raw.sound_notify_custom);
                            builder.setSound(sound).setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
                        }
                        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
                        notificationManagerCompat.notify(2022+mode,builder.build());
                    } catch (JSONException e) {
                        Log.i("e:",e.toString());
                        e.printStackTrace();
                    }
                }, error -> Log.e("Error",error.toString())) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + Common.getToken());
                return headers;
            }
        };
        requestQueue.add(request);
    }
}
