package com.da.tourandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.da.tourandroid.R;
import com.da.tourandroid.TourDetailsActivity;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class TourRecommendAdapter extends RecyclerView.Adapter<TourRecommendAdapter.TourViewHolder> {

    private Context context;
    private int layout;
    private List<Tour> tourList;

    public TourRecommendAdapter(Context context, int layout, ArrayList<Tour> tourList) {
        this.context = context;
        this.layout = layout;
        this.tourList = tourList;
    }

    @NonNull
    @Override
    public TourRecommendAdapter.TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new TourRecommendAdapter.TourViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TourRecommendAdapter.TourViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tourName.setText(Common.getTour().getDiemDen());
        holder.tourRating.setText(String.format("%s", Math.round((Math.random() + 4) * 10) / 10));
        holder.tourPrice.setText(Common.getTour().getGia()+"đ");
        Glide.with(context)
                .load(Common.getTour().getImage())
                .into(holder.tourImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(context, FoodDetailsActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                i.putExtra("id", foodList.get(position).getId());
//                i.putExtra("name", foodList.get(position).getName());
//                i.putExtra("name_res", foodList.get(position).gettour().getName());
//                i.putExtra("address_res", foodList.get(position).gettour().getAddress());
//                i.putExtra("price", foodList.get(position).getPrice());
//                i.putExtra("rating", "4.7");
//                i.putExtra("image", foodList.get(position).getImage());
//                i.putExtra("image_res", foodList.get(position).gettour().getImage());
//
//                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {

        ImageView tourImage;
        TextView tourName, tourRating, tourPrice;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);

            tourImage = itemView.findViewById(R.id.food_image);
            tourName = itemView.findViewById(R.id.food_name);
            tourRating = itemView.findViewById(R.id.food_rating);
            tourPrice = itemView.findViewById(R.id.food_price);

        }
    }


}