package com.da.tourandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.da.tourandroid.R;
import com.da.tourandroid.TourDetailsActivity;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Tour> tours;

    public SearchAdapter(Context context, int layout, ArrayList<Tour> tours) {
        this.context = context;
        this.layout = layout;
        this.tours = tours;
    }

    @Override
    public int getCount() {
        return tours.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class ViewHolder {
        TextView textViewName, textViewRes,textViewNgayBD,textViewPrice;
        ShapeableImageView imageView;
        ConstraintLayout constraintLayout;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder.imageView = view.findViewById(R.id.shapeableImageView_image);
            viewHolder.textViewName = view.findViewById(R.id.textView_name);
            viewHolder.textViewRes = view.findViewById(R.id.textView_res);
            viewHolder.constraintLayout = view.findViewById(R.id.constraintLayout);
            viewHolder.textViewNgayBD=view.findViewById(R.id.textView_ngayBatDau);
            viewHolder.textViewPrice=view.findViewById(R.id.textView_price);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        Tour tour = tours.get(i);
        viewHolder.textViewName.setText("Điểm đến:"+ tour.getDiemDen());
        viewHolder.textViewRes.setText(tour.getMoTa().equals("null")?"":tour.getMoTa());
        viewHolder.textViewNgayBD.setText(tour.getNgayBatDau().equals("null")?"":tour.getNgayBatDau());
        viewHolder.textViewPrice.setText(tour.getGia()+"");
        //viewHolder.imageView.setImageResource(food.getImage());

        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Common.setTour(tour);
                    Common.setDetailMode(1);
                    Intent i = new Intent(context, TourDetailsActivity.class);
                    context.startActivity(i);
                } catch (Exception ex) {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        });

        return view;
    }
}
