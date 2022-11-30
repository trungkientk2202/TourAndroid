package com.da.tourandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.da.tourandroid.R;
import com.da.tourandroid.model.Tour;
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
        TextView textViewName, textViewRes;
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

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        Tour tour = tours.get(i);
        viewHolder.textViewName.setText("Điểm đến:"+ tour.getDiemDen());
        viewHolder.textViewRes.setText(tour.getMoTa());
        //viewHolder.imageView.setImageResource(food.getImage());

        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(context, FoodDetailsActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                i.putExtra("id", food.getId());
//                i.putExtra("name", food.getName());
//                i.putExtra("name_res", food.getRestaurant().getName());
//                i.putExtra("address_res", food.getRestaurant().getAddress());
//                i.putExtra("price", food.getPrice());
//                i.putExtra("rating", "4.7");
//                i.putExtra("image", food.getImage());
//                i.putExtra("image_res", food.getRestaurant().getImage());
//
//                context.startActivity(i);
            }
        });

        return view;
    }
}
