package com.da.tourandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.da.tourandroid.R;
import com.da.tourandroid.TourDetailsActivity;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;

import java.util.List;

public class TourAllAdapter extends RecyclerView.Adapter<TourAllAdapter.TourViewHolder> {

    private Context context;
    private int layout;
    private List<Tour> tourList;

    public TourAllAdapter(Context context, int layout, List<Tour> tourList) {
        this.context = context;
        this.layout = layout;
        this.tourList = tourList;
    }

    @NonNull
    @Override
    public TourAllAdapter.TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new TourAllAdapter.TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourAllAdapter.TourViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tourName.setText(tourList.get(position).getDiemDen());
        holder.tourDiemDi.setText(tourList.get(position).getDiemDi());
        holder.tourRating.setText(String.format("%s", Math.round((Math.random() + 4) * 10) / 10.0));
        holder.tourType.setText(tourList.get(position).getLoaiTour().getTenLoaiTour());
        holder.tourPrice.setText(String.format("%s", tourList.get(position).getGia()) + "đ");
        Glide.with(context)
                .load(tourList.get(position).getImage())
                .into(holder.tourImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                Common.setTour(tourList.get(position));
                Intent i = new Intent(context, TourDetailsActivity.class);
                context.startActivity(i);
                } catch (Exception ex) {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {

        ImageView tourImage;
        TextView tourName, tourDiemDi, tourRating, tourType, tourPrice;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);

            tourImage = itemView.findViewById(R.id.tour_image);
            tourName = itemView.findViewById(R.id.tour_name);
            tourDiemDi = itemView.findViewById(R.id.tour_diemDi);
            tourRating = itemView.findViewById(R.id.tour_rating);
            tourType = itemView.findViewById(R.id.tour_type);
            tourPrice = itemView.findViewById(R.id.tour_price);

        }
    }


}