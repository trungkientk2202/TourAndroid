package com.da.tourandroid.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.da.tourandroid.BuildConfig;
import com.da.tourandroid.R;
import com.da.tourandroid.TourDetailsActivity;
import com.da.tourandroid.model.KhachHang;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.adapter.TourAdapter;
import com.da.tourandroid.model.LoaiTour;
import com.da.tourandroid.model.QuanLyTour;
import com.da.tourandroid.model.QuanLyTourID;
import com.da.tourandroid.model.ThamGiaTour;
import com.da.tourandroid.model.ThamGiaTourID;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceOngoingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceOngoingFragment extends Fragment implements OnMapReadyCallback {

    View view;

    private static final String TAG = InvoiceOngoingFragment.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition cameraPosition;

    // The entry point to the Places API.
    private PlacesClient placesClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] likelyPlaceNames;
    private String[] likelyPlaceAddresses;
    private List[] likelyPlaceAttributions;
    private LatLng[] likelyPlaceLatLngs;

    ArrayList<ThamGiaTour> thamGiaTours;
    private RequestQueue requestQueue;
    SearchView searchView;
    private ListView listViewOngoing;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AppCompatButton btnDetail,btnDiemHen;
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

    }

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
        this.mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(@NonNull Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(@NonNull Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
                        (FrameLayout) view.findViewById(R.id.map), false);

                TextView title = infoWindow.findViewById(R.id.title);
                title.setText(marker.getTitle());

                TextView snippet = infoWindow.findViewById(R.id.snippet);
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        requestQueue= Volley.newRequestQueue(view.getContext());
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
        thamGiaTours = new ArrayList<>();
//        initAllLocaltion();
        // Get list localtion
        getLocationOngoing();
        //Get all localtion
    }

    private void getAllLocaltion() {
        for (ThamGiaTour tgtour:thamGiaTours) {
            String[] a =tgtour.getVitri().split(";");
            LatLng pos = new LatLng(Double.parseDouble(a[0]), Double.parseDouble(a[1]));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,13));
            mMap.addMarker(new MarkerOptions()
                    .title(tgtour.getKhachHang().getTen())
                    .snippet(tgtour.getKhachHang().getSdt())
                    .position(pos));
        }

    }
    private void initAllLocaltion() {
        String[] a ={"Me","Văn Tuấn","Thành Nam","Ngọc Lan","Mỹ Hạnh"};
        double m=10.848777148671006;
        double n=106.78653130742524;
        for (int i=0;i< 5;i++) {
            LatLng pos = new LatLng(m+i*i/100.0, n-i/100.0);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,13));
            mMap.addMarker(new MarkerOptions()
                    .title("Vị trí của")
                    .snippet(a[i])
                    .position(pos));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_invoice_ongoing, container, false);
        // Inflate the layout for this fragment
        searchView = view.findViewById(R.id.idSearchView);

        btnDetail=(AppCompatButton) view.findViewById(R.id.detail_tour);
        btnDiemHen=(AppCompatButton) view.findViewById(R.id.diemHen);
        btnDiemHen.setVisibility(View.INVISIBLE);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.setDetailMode(3);
                Intent i = new Intent(view.getContext(), TourDetailsActivity.class);
                startActivity(i);
            }
        });
        requestQueue= Volley.newRequestQueue(view.getContext());
        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Construct a PlacesClient
        Places.initialize(requireContext().getApplicationContext(), BuildConfig.MAPS_API_KEY);
        placesClient = Places.createClient(requireContext());

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // Build the map.
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);
        // adding on query listener for our search view.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on below line we are getting the
                // location name from search view.
                String location = searchView.getQuery().toString();

                // below line is to create a list of address
                // where we will store the list of all address.
                List<Address> addressList = null;
                // checking if the entered location is null or not.
                if (location != null || location.equals("")) {
                    // on below line we are creating and initializing a geo coder.
                    Geocoder geocoder = new Geocoder(view.getContext());
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(addressList.size()==0){
                        return false;
                    }
                    btnDiemHen.setVisibility(View.VISIBLE);
                    // on below line we are getting the location
                    // from our list a first position.
                    Address address = addressList.get(0);

                    // on below line we are creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    // on below line we are adding marker to that position.
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));

                    // below line is to animate camera to that position.
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    btnDiemHen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(view.getContext(),"Tạo điểm hẹn "+location+" thành công!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        // at last we calling our map fragment to update.
        mMapFragment.getMapAsync(this);
        return view;
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    private void getLocationOngoing(){
        //lấy thông tin tour đang diễn ra
        if(Common.mode==2) {
            Common.setTour(null);
            String url = Common.getHost() + "tgtour/findList/" + Common.getKhachHang().getSdt()+"/2";
            Log.i("url: ", url);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                            Common.setThamGiaTour(new ThamGiaTour());

                            try {
                                JSONObject jsonObject = response.getJSONObject(0);

                                JSONObject objID = jsonObject.getJSONObject("id");
                                Common.getThamGiaTour().setId(new ThamGiaTourID(objID.getInt("maTour"),
                                        objID.getString("sdt")));
                                Common.getThamGiaTour().setCheckIn(jsonObject.getBoolean("checkIn"));
                                Common.getThamGiaTour().setGhiChu(jsonObject.getString("ghiChu"));
                                Common.getThamGiaTour().setDiaDiemDon(jsonObject.getString("diaDiemDon"));
                                JSONObject objTour = jsonObject.getJSONObject("tour");
                                Tour tour = new Tour();
                                tour.setMaTour(objTour.getInt("maTour"));
                                tour.setDiemDen(objTour.getString("diemDen"));
                                //Log.i("Diem den: ", tour.getDiemDen());
                                tour.setMoTa(objTour.getString("moTa"));
                                tour.setDiemDi(objTour.getString("diemDi"));
                                tour.setGia(objTour.getLong("gia"));
                                tour.setTrangThai(objTour.getInt("trangThai"));
                                tour.setImage(objTour.getString("image"));
                                tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                                JSONObject object = objTour.getJSONObject("loaiTour");
                                LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                                tour.setLoaiTour(loaiTour);
                                Common.setTour(tour);
                                Common.getThamGiaTour().setTour(tour);

                                JSONObject objKh = jsonObject.getJSONObject("khachHang");
                                KhachHang khachHang=new KhachHang();
                                khachHang.setSdt(objKh.getString("sdt"));
                                khachHang.setTen(objKh.getString("ten"));
                                khachHang.setMatKhau(objKh.getString("matKhau"));
                                khachHang.setPhai(objKh.getBoolean("phai"));
                                khachHang.setNgaySinh(!objKh.getString("ngaySinh").equals("null") ?new SimpleDateFormat("yyyy-MM-dd").parse(objKh.getString("ngaySinh")):null);
                                khachHang.setZalo("zalo");
                                Common.getThamGiaTour().setKhachHang(khachHang);
                                Common.setTour(tour);
                                getListThamGiaTour();
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }
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
            String url = Common.getHost() + "qltour/findList/" + Common.getTaiKhoan().getSdt()+"/2";
            //Log.i("url: ", url);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                            QuanLyTour quanLyTour=new QuanLyTour();
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                JSONObject objID = jsonObject.getJSONObject("id");
                                quanLyTour.setId(new QuanLyTourID(objID.getInt("maTour"), objID.getString("sdt")));
                                quanLyTour.setGhiChu(jsonObject.getString("ghiChu"));

                                JSONObject objTour = jsonObject.getJSONObject("tour");
                                Tour tour = new Tour();
                                tour.setMaTour(objTour.getInt("maTour"));
                                tour.setDiemDen(objTour.getString("diemDen"));
                                tour.setMoTa(objTour.getString("moTa"));
                                tour.setDiemDi(objTour.getString("diemDi"));
                                tour.setGia(objTour.getLong("gia"));
                                tour.setTrangThai(objTour.getInt("trangThai"));
                                tour.setImage(objTour.getString("image"));
                                tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                                JSONObject object = objTour.getJSONObject("loaiTour");
                                LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                                tour.setLoaiTour(loaiTour);
                                Common.setTour(tour);
                                getListThamGiaTour();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

    }
    public void getListThamGiaTour(){
        if(Common.getTour()==null){
            Toast.makeText(view.getContext(),"You are not currently participating in any tour!",Toast.LENGTH_LONG).show();
            return;
        }
        //lấy list tham gia tour bao gồm vị trí
        thamGiaTours=new ArrayList<>();
        String url = Common.getHost() + "tgtour/findByMaTour/" +Common.getTour().getMaTour();
        Log.i("url: ", url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        ThamGiaTour thamGiaTour = new ThamGiaTour();
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            JSONObject objID = jsonObject.getJSONObject("id");
                            thamGiaTour.setId(new ThamGiaTourID(objID.getInt("maTour"),
                                    objID.getString("sdt")));
                            thamGiaTour.setCheckIn(jsonObject.getBoolean("checkIn"));
                            thamGiaTour.setGhiChu(jsonObject.getString("ghiChu"));
                            thamGiaTour.setDiaDiemDon(jsonObject.getString("diaDiemDon"));
                            thamGiaTour.setVitri(jsonObject.getString("vitri"));

                            JSONObject objTour = jsonObject.getJSONObject("tour");
                            Tour tour = new Tour();
                            tour.setMaTour(objTour.getInt("maTour"));
                            tour.setDiemDen(objTour.getString("diemDen"));
                            //Log.i("Diem den: ", tour.getDiemDen());
                            tour.setMoTa(objTour.getString("moTa"));
                            tour.setDiemDi(objTour.getString("diemDi"));
                            tour.setGia(objTour.getLong("gia"));
                            tour.setTrangThai(objTour.getInt("trangThai"));
                            tour.setImage(objTour.getString("image"));
                            tour.setNgayBatDau(objTour.getString("ngayBatDau"));
                            JSONObject object = objTour.getJSONObject("loaiTour");
                            LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                            tour.setLoaiTour(loaiTour);
                            Common.setTour(tour);
                            thamGiaTour.setTour(tour);

                            JSONObject objKh = jsonObject.getJSONObject("khachHang");
                            KhachHang khachHang=new KhachHang();
                            khachHang.setSdt(objKh.getString("sdt"));
                            khachHang.setTen(objKh.getString("ten"));
                            khachHang.setMatKhau(objKh.getString("matKhau"));
                            khachHang.setPhai(objKh.getBoolean("phai"));
                            khachHang.setNgaySinh(!objKh.getString("ngaySinh").equals("null") ?new SimpleDateFormat("yyyy-MM-dd").parse(objKh.getString("ngaySinh")):null);
                            khachHang.setZalo("zalo");
                            thamGiaTour.setKhachHang(khachHang);
                            thamGiaTours.add(thamGiaTour);
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    //hiển thị vị trí trên map
                    getAllLocaltion();
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
    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(requireActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(requireContext().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}