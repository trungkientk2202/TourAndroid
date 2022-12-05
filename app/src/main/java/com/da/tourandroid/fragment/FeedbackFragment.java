package com.da.tourandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.R;
import com.da.tourandroid.adapter.FeedbackAdapter;
import com.da.tourandroid.model.LoaiTour;
import com.da.tourandroid.model.PhanHoi;
import com.da.tourandroid.model.PhanHoiID;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {

    private ListView listViewFeedback;
    private RequestQueue requestQueue;
    private FeedbackAdapter feedbackAdapter;
    private ArrayList<PhanHoi> feedbacks;

    private View view;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feedback, container, false);
        requestQueue= Volley.newRequestQueue(view.getContext());
        listViewFeedback = view.findViewById(R.id.listView_feedback);
        feedbacks = new ArrayList<>();

        feedbackAdapter = new FeedbackAdapter(view.getContext(), R.layout.items_feedback, feedbacks);
        listViewFeedback.setAdapter(feedbackAdapter);
        getFeedbacks();
        return view;
    }

    private void getFeedbacks() {
        if(Common.mode==2) {
            String url = Common.getHost() + "phanHoi/findBySdt/" + Common.getKhachHang().getSdt();
            Log.i("url: ", url);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        Log.i("response", response.toString());
                        Log.i("length", response.length() + "");
                        for (int i = 0; i < response.length(); i++) {
                            PhanHoi phanHoi = new PhanHoi();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.i("jsonObject", jsonObject.toString());
                                JSONObject objID = jsonObject.getJSONObject("id");
                                phanHoi.setId(new PhanHoiID(objID.getInt("maTour"), objID.getString("sdt")));
                                phanHoi.setNoiDung(jsonObject.getString("noiDung"));
                                phanHoi.setThoiGian(jsonObject.getString("thoiGian"));
                                JSONObject objTour = jsonObject.getJSONObject("tour");
                                Tour tour = new Tour();
                                tour.setMaTour(objTour.getInt("maTour"));
                                tour.setDiemDen(objTour.getString("diemDen"));
                                Log.i("Diem den: ", tour.getDiemDen());
                                tour.setMoTa(objTour.getString("moTa").equals("null") ? null : jsonObject.getString("moTa"));
                                tour.setDiemDi(objTour.getString("diemDi"));
                                tour.setGia(objTour.getLong("gia"));
                                tour.setTrangThai(objTour.getInt("trangThai"));
                                tour.setImage(objTour.getString("image"));
                                JSONObject object = objTour.getJSONObject("loaiTour");
                                LoaiTour loaiTour = new LoaiTour(object.getInt("maLoaiTour"), object.getString("tenLoaiTour"), object.getString("moTa").equals("null") ? null : object.getString("moTa"));
                                tour.setLoaiTour(loaiTour);
                                phanHoi.setTour(tour);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            feedbacks.add(phanHoi);
                        }
                        Log.i("feedbacks size: ", feedbacks.size() + "");
                        listViewFeedback = view.findViewById(R.id.listView_feedback);
                        feedbackAdapter = new FeedbackAdapter(view.getContext(), R.layout.items_feedback, feedbacks);
                        listViewFeedback.setAdapter(feedbackAdapter);
                        feedbackAdapter.notifyDataSetChanged();
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
}