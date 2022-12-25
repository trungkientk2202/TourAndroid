package com.da.tourandroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.da.tourandroid.adapter.DienDanAdapter;
import com.da.tourandroid.adapter.TimelineAdapter;
import com.da.tourandroid.model.DiaDiem;
import com.da.tourandroid.model.DienDan;
import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.LichTrinhID;
import com.da.tourandroid.model.LoaiTour;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DienDanActivity extends Activity {
    ImageView img_tour, imageViewBack;
    TextView txt_name_tour, txt_tour_price, thoiGianBatDau,textView_descDetails;
    RecyclerView lv_content;
    AppCompatButton btnAddContent,btnAlert;
    ArrayList<DienDan>dienDans;
    DienDanAdapter dienDanAdapter;
    RequestQueue requestQueue;
    EditText editTextContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        dienDans=new ArrayList<>();
        requestQueue= Volley.newRequestQueue(DienDanActivity.this);
        map();
        dataInit();
        Glide.with(DienDanActivity.this)
                .load(Common.getTour().getImage())
                .into(img_tour);

        img_tour.setImageResource(R.drawable.da_nang);
    }
    private void map() {
        img_tour = findViewById(R.id.imgView_tour);

        imageViewBack = findViewById(R.id.imageView_back);
        thoiGianBatDau=findViewById(R.id.thoiGianBatDau);
        textView_descDetails=findViewById(R.id.textView_descDetails);
        txt_name_tour = findViewById(R.id.txt_name_tour);
        txt_tour_price = findViewById(R.id.txt_tour_price);
        lv_content = findViewById(R.id.lv_content);
        btnAddContent = findViewById(R.id.btnAddContent);
        editTextContent=findViewById(R.id.editTextContent);
        btnAlert = findViewById(R.id.btnAlert);
    }
    private void dataInit() {
        dienDans=new ArrayList<>();

        txt_name_tour.setText(Common.getTour().getDiemDen());

        thoiGianBatDau.setText(Common.getTour().getNgayBatDau());
        textView_descDetails.setText(Common.getTour().getMoTa());
        txt_tour_price.setText(Common.getTour().getGia()+"đ");

        dienDanAdapter = new DienDanAdapter(this, R.layout.items_diendan, dienDans);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lv_content.setLayoutManager(layoutManager1);
        getDataDienDan((int) Common.getTour().getMaTour());
        lv_content.setAdapter(dienDanAdapter);
        if(Common.getMode()==1){
            btnAlert.setVisibility(View.VISIBLE);
        }else{
            btnAlert.setVisibility(View.INVISIBLE);
        }
        btnAddContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextContent.getText().length()<=0){
                    Toast.makeText(view.getContext(),"Please enter content information!",Toast.LENGTH_LONG).show();
                }else{
//                    String diemHen=address.getLatitude()+";"+address.getLongitude()+";"+txtNoiDung.getText().toString()+";"+txtGioHen.getText().toString();
                    String json="{\"maTour\":\""+Common.getTour().getMaTour()+"\",";
                    if(Common.getMode()==1){
                        //them diem hen vao db
                        json +="\"sdt\":\""+Common.getTaiKhoan().getSdt()+"\","
                                +"\"laHDV\": "+true+",";
                    }else{
                        //them diem hen vao db
                        json+="\"sdt\":\""+Common.getKhachHang().getSdt()+"\","
                                +"\"laHDV\": "+false+",";
                    }
                    json+="\"noiDung\":\""+editTextContent.getText().toString()+"\","
                            +"\"thongBaoTuHDV\":false,"
                            +"\"thoiGian\":\""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())+"\"}";
                    String url=Common.getHost()+"dienDan/add";
                    try {
                        JSONObject req=new JSONObject(json);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, req,
                                response -> {
                                    try {
                                        if(!response.get("id").equals(null)){
                                            //add thành công
                                            editTextContent.setText("");
                                            Toast.makeText(view.getContext(), "Add content successfully!", Toast.LENGTH_LONG).show();
                                            getDataDienDan((int) Common.getTour().getMaTour());
                                        }else{
                                            Toast.makeText(view.getContext(), "Add content failure!", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        Log.i("e:",e.toString());
                                        e.printStackTrace();
                                    }
                                }, error -> Toast.makeText(view.getContext(), "Server error!", Toast.LENGTH_LONG).show()) {
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json="{\"maTour\":\""+Common.getTour().getMaTour()+"\",";
                if(Common.getMode()==1){
                    //them diem hen vao db
                    json +="\"sdt\":\""+Common.getTaiKhoan().getSdt()+"\","
                            +"\"laHDV\": "+true+",";
                }else{
                    //them diem hen vao db
                    json+="\"sdt\":\""+Common.getKhachHang().getSdt()+"\","
                            +"\"laHDV\": "+false+",";
                }
                json+="\"noiDung\":\""+editTextContent.getText().toString()+"\","
                        +"\"thongBaoTuHDV\":true,"
                        +"\"thoiGian\":\""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())+"\"}";
                String url = Common.getHost() + "dienDan/add";
                try {
                    JSONObject req=new JSONObject(json);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, req,
                            response -> {
                                try {
                                    if(!response.get("id").equals(null)){
                                        //add notify 2
                                        try {
                                            String json1="{\"noiDung\":\""+editTextContent.getText().toString()+"\","
                                                    +"\"thoiGian\":\""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())+"\"}";
                                            String url1= Common.getHost()+"thongBao/edit/"+Common.getTour().getMaTour()+"/2";
                                            JSONObject req1=new JSONObject(json1);
                                            JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, url1, req1,
                                                    response1 -> {
                                                        try {
                                                            if(!response1.get("id").equals(null)){
                                                                Log.i("Push thong bao tap hop","ok");
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }, error -> Toast.makeText(view.getContext(), "Server error!", Toast.LENGTH_LONG).show()) {
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
                                            requestQueue.add(request1);

                                        }catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        getDataDienDan((int) Common.getTour().getMaTour());
                                    }else{
                                        Toast.makeText(view.getContext(), "Add content failure!", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    Log.i("e:",e.toString());
                                    e.printStackTrace();
                                }

                                //add thành công
                                editTextContent.setText("");
                                Toast.makeText(view.getContext(), "Add content successfully!", Toast.LENGTH_LONG).show();
                            }, error -> Toast.makeText(view.getContext(), "Server error!", Toast.LENGTH_LONG).show()) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDataDienDan(int maTour) {
        //get list dien dan
        dienDans.clear();
        String url = Common.getHost() + "dienDan/findByMaTour/" + maTour;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        DienDan dienDan=new DienDan();
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            dienDan.setId(jsonObject.getLong("id"));
                            dienDan.setMaTour(jsonObject.getLong("maTour"));
                            dienDan.setSdt(jsonObject.getString("sdt"));
                            dienDan.setNoiDung(jsonObject.getString("noiDung"));
                            dienDan.setThoiGian(jsonObject.getString("thoiGian"));
                            dienDan.setLaHDV(jsonObject.getBoolean("laHDV"));

                            dienDans.add(dienDan);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    dienDanAdapter = new DienDanAdapter(this, R.layout.items_diendan, dienDans);
                    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    lv_content.setLayoutManager(layoutManager1);
                    lv_content.setAdapter(dienDanAdapter);
                }, error -> Log.i("err:", error.toString())) {
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
        dienDanAdapter.notifyDataSetChanged();
    }
}
