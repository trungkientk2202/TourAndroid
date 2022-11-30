package com.da.tourandroid.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.LoginActivity;
import com.da.tourandroid.MainActivity;
import com.da.tourandroid.R;
import com.da.tourandroid.SearchActivity;
import com.da.tourandroid.adapter.TourAllAdapter;
import com.da.tourandroid.model.LoaiTour;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recommendRecyclerView, allMenuRecyclerView;
    private RecyclerView restaurantRecyclerView;

    private TourAllAdapter allMenuAdapter;
//    private FoodRecommendedAdapter recommendAdapter;
//    private RestaurantAdapter resAdapter;
//
    private List<Tour> tours;
//    private ArrayList<Restaurant> restaurants;

    private View view;

    private RequestQueue requestQueue;
    private EditText editTextSearch;
    private ImageView imageViewCart;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestQueue= Volley.newRequestQueue(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        requestQueue= Volley.newRequestQueue(view.getContext());
        mapping();
        initUI();

        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Intent i = new Intent(view.getContext(), SearchActivity.class);
                    startActivity(i);
//                    editTextSearch.clearFocus();
                }
            }
        });

        return view;
    }

    private void mapping() {
        editTextSearch = view.findViewById(R.id.editText_search);
    }

    private void initUI() {

        tours = new ArrayList<>();
//        String url ="http://192.168.1.101:8080/tour/getAll";
//        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null,
//                response -> {
//                        for (int i =0;i<response.length();i++){
//                            Tour tour= new Tour();
//                            try {
//                                JSONObject jsonObject=response.getJSONObject(i);
//                                tour.setMaTour(jsonObject.getInt("maTour"));
//                                tour.setDiemDen(jsonObject.getString("diemDen"));
//                                tour.setMoTa(jsonObject.getString("moTa").equals("null")?null:jsonObject.getString("moTa"));
//                                tour.setDiemDi(jsonObject.getString("diemDi"));
//                                tour.setGia(jsonObject.getLong("gia"));
//                                JSONObject object=jsonObject.getJSONObject("loaiTour");
//                                LoaiTour loaiTour= new LoaiTour(object.getInt("maLoaiTour"),object.getString("tenLoaiTour"),object.getString("moTa").equals("null")?null:object.getString("moTa"));
//                                tour.setLoaiTour(loaiTour);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            tours.add(tour);
//                        }
//
//                }, error -> Log.i("err:",error.toString())){
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "Bearer " + Common.getToken());
//                return headers;
//            }
//
//        };
//        requestQueue.add(request);

        getAllMenu(tours);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getAllMenu(List<Tour> allMenuList) {
        Log.i("Size:",allMenuList.size()+"");
        allMenuRecyclerView = view.findViewById(R.id.tour_recycler);
        allMenuAdapter = new TourAllAdapter(view.getContext(), R.layout.items_allmenu_recycler, allMenuList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        allMenuAdapter.notifyDataSetChanged();
    }
}