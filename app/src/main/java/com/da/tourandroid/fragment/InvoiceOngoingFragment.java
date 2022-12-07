package com.da.tourandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.da.tourandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.R;
import com.da.tourandroid.adapter.TourAdapter;
import com.da.tourandroid.adapter.TourAllAdapter;
import com.da.tourandroid.model.LoaiTour;
import com.da.tourandroid.model.QuanLyTour;
import com.da.tourandroid.model.QuanLyTourID;
import com.da.tourandroid.model.ThamGiaTour;
import com.da.tourandroid.model.ThamGiaTourID;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Objects;

//import com.google.gson.Gson;
//
//import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceOngoingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceOngoingFragment extends Fragment implements OnMapReadyCallback {

    View view;
    private GoogleMap mMap;
//    ArrayList<OrderFood> orders;
//    OrderAdapter adapter;
    ArrayList<Tour> tours;
    private TourAdapter adapter;
    private RequestQueue requestQueue;

    private ListView listViewOngoing;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InvoiceOngoingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceOngoingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceOngoingFragment newInstance(String param1, String param2) {
        InvoiceOngoingFragment fragment = new InvoiceOngoingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_invoice_ongoing, container, false);
        requestQueue= Volley.newRequestQueue(view.getContext());
        listViewOngoing = view.findViewById(R.id.listView_ongoing);
        tours = new ArrayList<>();

        adapter = new TourAdapter(view.getContext(), R.layout.items_ongoing, tours);
        getToursOngoing();
        listViewOngoing.setAdapter(adapter);

        return view;
    }
    private void getToursOngoing(){
        if(Common.mode==2) {
            String url = Common.getHost() + "tgtour/findList/" + Common.getKhachHang().getSdt()+"/2";
            //Log.i("url: ", url);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        for (int i = 0; i < response.length(); i++) {
                            ThamGiaTour thamGiaTour=new ThamGiaTour();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONObject objID = jsonObject.getJSONObject("id");
                                thamGiaTour.setId(new ThamGiaTourID(objID.getInt("maTour"), objID.getString("sdt")));
                                thamGiaTour.setCheckIn(jsonObject.getBoolean("checkIn"));
                                thamGiaTour.setGhiChu(jsonObject.getString("ghiChu"));
                                thamGiaTour.setDiaDiemDon(jsonObject.getString("diaDiemDon"));
                                JSONObject objTour = jsonObject.getJSONObject("tour");
                                Tour tour = new Tour();
                                tour.setMaTour(objTour.getInt("maTour"));
                                tour.setDiemDen(objTour.getString("diemDen"));
                                tour.setMoTa(objTour.getString("moTa").equals("null") ? null : jsonObject.getString("moTa"));
                                tour.setDiemDi(objTour.getString("diemDi"));
                                tour.setGia(objTour.getLong("gia"));
                                tour.setTrangThai(objTour.getInt("trangThai"));
                                tour.setImage(objTour.getString("image"));
                                tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                                JSONObject object = objTour.getJSONObject("loaiTour");
                                LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                                tour.setLoaiTour(loaiTour);
                                tours.add(tour);
                                thamGiaTour.setTour(tour);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listViewOngoing = view.findViewById(R.id.listView_ongoing);
                        adapter = new TourAdapter(view.getContext(), R.layout.items_ongoing, tours);
                        listViewOngoing.setAdapter(adapter);
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
        }else{
            tours = new ArrayList<>();
            String url = Common.getHost() + "qltour/findList/" + Common.getTaiKhoan().getSdt()+"/2";
            //Log.i("url: ", url);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        for (int i = 0; i < response.length(); i++) {
                            QuanLyTour quanLyTour=new QuanLyTour();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONObject objID = jsonObject.getJSONObject("id");
                                quanLyTour.setId(new QuanLyTourID(objID.getInt("maTour"), objID.getString("sdt")));
                                quanLyTour.setGhiChu(jsonObject.getString("ghiChu"));

                                JSONObject objTour = jsonObject.getJSONObject("tour");
                                Tour tour = new Tour();
                                tour.setMaTour(objTour.getInt("maTour"));
                                tour.setDiemDen(objTour.getString("diemDen"));
                                tour.setMoTa(objTour.getString("moTa").equals("null") ? null : jsonObject.getString("moTa"));
                                tour.setDiemDi(objTour.getString("diemDi"));
                                tour.setGia(objTour.getLong("gia"));
                                tour.setTrangThai(objTour.getInt("trangThai"));
                                tour.setImage(objTour.getString("image"));
                                tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                                JSONObject object = objTour.getJSONObject("loaiTour");
                                LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                                tour.setLoaiTour(loaiTour);
                                tours.add(tour);
                                quanLyTour.setTour(tour);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listViewOngoing = view.findViewById(R.id.listView_ongoing);
                        adapter = new TourAdapter(view.getContext(), R.layout.items_ongoing, tours);
                        listViewOngoing.setAdapter(adapter);
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
        }
        adapter.notifyDataSetChanged();
    }
}