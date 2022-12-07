package com.da.tourandroid;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.da.tourandroid.adapter.TimelineAdapter;
import com.da.tourandroid.adapter.TourRecommendAdapter;
import com.da.tourandroid.adapter.UserFeedbackAdapter;
import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.LichTrinhID;
import com.da.tourandroid.model.LoaiTour;
import com.da.tourandroid.model.PhanHoi;
import com.da.tourandroid.model.PhanHoiID;
import com.da.tourandroid.model.Tour;

import java.util.ArrayList;

public class TourDetailsActivity extends AppCompatActivity {
    ImageView img_tour, img_saved, imageViewBack;
    TextView txt_name, txt_price;
    RatingBar rb;
    ArrayList<LichTrinh> listTimeline;
    ArrayList<Tour> listRelatedTour;
    ArrayList<PhanHoi> listFeedback;
    RecyclerView relatedTourRecycle, timelineRecycleView, feedbackRecycleView;
    TourRecommendAdapter tourRecommendAdapter;
    TimelineAdapter timelineAdapter;
    UserFeedbackAdapter userFeedbackAdapter;
    AppCompatButton btnFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        map();
        dataInit();

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void map() {
        img_tour = findViewById(R.id.img_foody);
        txt_name = findViewById(R.id.txt_name_restaurant);
        timelineRecycleView = findViewById(R.id.lv_comment);
        feedbackRecycleView = findViewById(R.id.feedback_tour_recycler);
        rb = findViewById(R.id.ratingBar3);
        img_saved = findViewById(R.id.img_save_location);
        txt_price = findViewById(R.id.txt_restaurant_address);
        relatedTourRecycle = findViewById(R.id.related_tour_recycler);
        imageViewBack = findViewById(R.id.imageView_back);
        btnFeedback = findViewById(R.id.btn_feedback);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataTimeline(int id_res) {
        listTimeline.clear();
        listTimeline.add(new LichTrinh(new LichTrinhID(0,0,0), "di so thu", "07:00 AM"));
        listTimeline.add(new LichTrinh(new LichTrinhID(0,0,1), "di ho boi", "09:00 AM"));
        listTimeline.add(new LichTrinh(new LichTrinhID(0,0,2), "tham gia hoat dong ngoai troi", "03:00 PM"));
        listTimeline.add(new LichTrinh(new LichTrinhID(0,0,3), "thuong thuc mon an tai nha hang", "07:30 PM"));
        listTimeline.add(new LichTrinh(new LichTrinhID(0,0,4), "di dao bo bien", "09:00 PM"));
        listTimeline.add(new LichTrinh(new LichTrinhID(0,0,5), "di ngu", "011:30 PM"));
        timelineAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataRelatedTours(int id_res) {
        listRelatedTour.clear();

        Tour tour = new Tour();
        tour.setMaTour(0);
        tour.setDiemDen("diemDen");
        tour.setMoTa("moTa");
        tour.setDiemDi("diemDi");
        tour.setGia(99000L);
        tour.setTrangThai(1);
        LoaiTour loaiTour= new LoaiTour(0, "tenLoaiTour","moTa");
        tour.setLoaiTour(loaiTour);
        Tour tour1 = new Tour();
        tour.setMaTour(1);
        tour.setDiemDen("diemDen");
        tour.setMoTa("moTa");
        tour.setDiemDi("diemDi");
        tour.setGia(99000L);
        tour.setTrangThai(1);
        LoaiTour loaiTour1= new LoaiTour(1, "tenLoaiTour","moTa");
        tour.setLoaiTour(loaiTour1);
        Tour tour2 = new Tour();
        tour.setMaTour(2);
        tour.setDiemDen("diemDen");
        tour.setMoTa("moTa");
        tour.setDiemDi("diemDi");
        tour.setGia(99000L);
        tour.setTrangThai(1);
        LoaiTour loaiTour2= new LoaiTour(2, "tenLoaiTour","moTa");
        tour.setLoaiTour(loaiTour2);

        listRelatedTour.add(tour);
        listRelatedTour.add(tour1);
        listRelatedTour.add(tour2);

        tourRecommendAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDataFeedbacks(int id_res) {
        listFeedback.clear();

        listFeedback.add(new PhanHoi(new PhanHoiID(1, "0357499653"), "1. For using Glide in the android project, we have to add the dependency in gradle file. So, For adding dependency open app/build.gradle file in the app folder in your Android project and add the following lines inside it. ", "05/12/2022 03:10 PM"));
        listFeedback.add(new PhanHoi(new PhanHoiID(1, "0357499653"), "2. Now add InternetPermission inside the AndroidManifest.xml file. Open the manifest.xml file and add the following line. ", "30/11/2022 07:08 AM"));
        listFeedback.add(new PhanHoi(new PhanHoiID(1, "0357499653"), "3. Open the layout file for the main Activity. We need to add an ImageView to the layout. It doesn’t need to be fancy. The following code snippet shows you what I mean.", "03/12/2022 08:09 PM"));

        userFeedbackAdapter.notifyDataSetChanged();
    }

    private void dataInit() {
        listTimeline = new ArrayList<>();
        listRelatedTour = new ArrayList<>();
        listFeedback = new ArrayList<>();

        //Get restaurant info
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        float rating = intent.getFloatExtra("rating", 4);

        rb.setRating(rating);

        Tour tour = new Tour();
        tour.setMaTour(0);
        tour.setDiemDen("diemDen");
        tour.setMoTa("moTa");
        tour.setDiemDi("diemDi");
        tour.setGia(99000L);
        tour.setTrangThai(1);
        LoaiTour loaiTour= new LoaiTour(0, "tenLoaiTour","moTa");
        tour.setLoaiTour(loaiTour);

        //Set restaurant info
        txt_name.setText(tour.getDiemDen());
        img_tour.setImageResource(R.drawable.da_nang);
        txt_price.setText("99.000 vnđ");

        //Set timeline info
        timelineAdapter = new TimelineAdapter(this, R.layout.items_timeline, listTimeline);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        timelineRecycleView.setLayoutManager(layoutManager1);
        getDataTimeline(id);
        timelineRecycleView.setAdapter(timelineAdapter);

        //Set other tours info
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        relatedTourRecycle.setLayoutManager(layoutManager);
        tourRecommendAdapter = new TourRecommendAdapter(this, R.layout.items_recommended_tour, listRelatedTour);
        getDataRelatedTours(id);
        relatedTourRecycle.setAdapter(tourRecommendAdapter);

        //Set feedbacks info
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        feedbackRecycleView.setLayoutManager(layoutManager2);
        userFeedbackAdapter = new UserFeedbackAdapter(this, R.layout.items_user_feedback, listFeedback);
        getDataFeedbacks(id);
        feedbackRecycleView.setAdapter(userFeedbackAdapter);

        timelineRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        feedbackRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getApplicationContext());
                dialog.setContentView(R.layout.dialog_feedback);

                EditText editTextName = dialog.findViewById(R.id.editText_addTask);
                Button buttonAdd = dialog.findViewById(R.id.button_addTask);
                Button buttonCancel = dialog.findViewById(R.id.button_cancel);

                buttonAdd.setOnClickListener(view -> {
                    String name = editTextName.getText().toString();

                    if (name.equals(""))
                        Toast.makeText(getApplicationContext(), "This field is require!", Toast.LENGTH_SHORT).show();
                    else {
                        // TODO: save data
                        Toast.makeText(getApplicationContext(), "Added a feedback", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        // TODO: request data again
                    }
                });

                buttonCancel.setOnClickListener(view -> {
                    dialog.dismiss();
                });

                dialog.show();
            }
        });
    }
}
