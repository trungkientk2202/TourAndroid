package com.da.tourandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.da.tourandroid.R;
import com.da.tourandroid.TourDetailsActivity;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import java.util.List;

public class TourAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Tour> tourList;

    public TourAdapter(Context context, int layout, List<Tour> tourList) {
        this.context = context;
        this.layout = layout;
        this.tourList = tourList;
    }

    @Override
    public int getCount() {
        return tourList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView img_tour;
        TextView txt_diemDen_tour, txtNgayBatDau, txt_diemDi_tour, txt_gia;
        Button btn_feedback, btn_join, btn_cancel,btn_preview;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TourAdapter.ViewHolder viewHolder;
        Tour tour = tourList.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder = new TourAdapter.ViewHolder();

            viewHolder.img_tour = view.findViewById(R.id.img_tour);
            viewHolder.txt_diemDen_tour = view.findViewById(R.id.txt_diemDen_tour);
            viewHolder.txtNgayBatDau = view.findViewById(R.id.txtNgayBatDau);
            viewHolder.txt_diemDi_tour = view.findViewById(R.id.txt_diemDi_tour);
            viewHolder.txt_gia = view.findViewById(R.id.txt_gia);
            
            switch (layout) {
                case R.layout.items_history:
                    viewHolder.btn_feedback = view.findViewById(R.id.btn_feedback);
                    viewHolder.btn_feedback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Common.setTour(tour);
                            Intent i = new Intent(context, TourDetailsActivity.class);
                            context.startActivity(i);
                        }
                    });
                    break;
                case R.layout.items_onplan:
                    viewHolder.btn_preview = view.findViewById(R.id.btn_preview);
                    viewHolder.btn_preview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Common.setTour(tour);
                            Intent i = new Intent(context, TourDetailsActivity.class);
                            context.startActivity(i);
                        }
                    });
                    break;
                case R.layout.items_ongoing:
                    viewHolder.btn_join = view.findViewById(R.id.btn_join);
                    viewHolder.btn_cancel = view.findViewById(R.id.btn_cancel);
                    viewHolder.btn_join.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Common.setTour(tour);
                            Intent i = new Intent(context, TourDetailsActivity.class);
                            context.startActivity(i);
                        }
                    });
                    viewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
            }

            view.setTag(viewHolder);
        } else {
            viewHolder = (TourAdapter.ViewHolder) view.getTag();
        }

        viewHolder.txt_diemDen_tour.setText(tour.getDiemDen());
        viewHolder.txtNgayBatDau.setText(tour.getNgayBatDau());
        viewHolder.txt_diemDi_tour.setText(tour.getDiemDi());
        viewHolder.txt_gia.setText(String.valueOf(tour.getGia()));
        Glide.with(context)
                .load(tour.getImage())
                .into(viewHolder.img_tour);
        return view;
    }
}