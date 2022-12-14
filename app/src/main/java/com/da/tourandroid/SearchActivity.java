package com.da.tourandroid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.adapter.SearchAdapter;
import com.da.tourandroid.model.LoaiTour;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private ImageView imageViewBack;
    private EditText editTextSearch;
    private ListView listViewSearch;
    private RequestQueue requestQueue;
    private ArrayList<Tour> tours;

    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        requestQueue= Volley.newRequestQueue(SearchActivity.this);
        tours = new ArrayList<>();

        imageViewBack = findViewById(R.id.imageView_back);
        editTextSearch = findViewById(R.id.editText_search);
        listViewSearch = findViewById(R.id.listView_search);

        editTextSearch.requestFocus();

        adapter = new SearchAdapter(this, R.layout.items_search, tours);
        listViewSearch.setAdapter(adapter);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tours.clear();
                if (!editTextSearch.getText().toString().equals("")) {
                    tours = new ArrayList<>();
                    String url = Common.getHost()+"tour/search";
                    String json="[\""+editTextSearch.getText()+"\"]";
                    try {
                        JSONArray req= new JSONArray(json);
                        Log.i("req: ",req.toString());
                        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, req,
                                response -> {
                                    Log.i("response: ",response.toString());
                                    for (int j =0;j<response.length();j++){
                                        Tour tour= new Tour();
                                        try {
                                            JSONObject jsonObject=response.getJSONObject(j);
                                            tour.setMaTour(jsonObject.getInt("maTour"));
                                            tour.setDiemDen(jsonObject.getString("diemDen"));
                                            tour.setMoTa(jsonObject.getString("moTa"));
                                            tour.setDiemDi(jsonObject.getString("diemDi"));
                                            tour.setGia(jsonObject.getLong("gia"));
                                            tour.setTrangThai(jsonObject.getInt("trangThai"));
                                            tour.setImage(jsonObject.getString("image"));
                                            tour.setNgayBatDau(jsonObject.getString("ngayBatDau"));
                                            JSONObject object=jsonObject.getJSONObject("loaiTour");
                                            LoaiTour loaiTour= new LoaiTour(object.getInt("maLoaiTour"),object.getString("tenLoaiTour"),object.getString("moTa").equals("null")?null:object.getString("moTa"));
                                            tour.setLoaiTour(loaiTour);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        tours.add(tour);
                                    }
                                    adapter = new SearchAdapter(SearchActivity.this, R.layout.items_search, tours);
                                    listViewSearch.setAdapter(adapter);
                                }, error -> Log.i("err:",error.toString())){
                            /**
                             * Passing some request headers
                             * */
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Authorization", "Bearer " + Common.getToken());
                                return headers;
                            }

                        };
                        requestQueue.add(request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // get previous page
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}