package com.da.tourandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.da.tourandroid.R;
import com.da.tourandroid.model.Tour;

import java.util.List;

public class AllTourAdapter extends RecyclerView.Adapter<AllTourAdapter.TourViewHolder> {

    private Context context;
    private int layout;
    private List<Tour> tourList;

    public AllTourAdapter(Context context, int layout, List<Tour> tourList) {
        this.context = context;
        this.layout = layout;
        this.tourList = tourList;
    }

    @NonNull
    @Override
    public AllTourAdapter.TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new AllTourAdapter.TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTourAdapter.TourViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tourName.setText(tourList.get(position).getDiemDen());
        holder.tourDiemDi.setText(tourList.get(position).getDiemDi());
        holder.tourRating.setText(String.format("%s", Math.round((Math.random() + 4) * 10) / 10));
        holder.tourType.setText(tourList.get(position).getLoaiTour().getTenLoaiTour());
        holder.tourPrice.setText(String.format("%s", tourList.get(position).getGia())+"đ");
        holder.tourImage.setImageURI(Uri.parse("./drawable/popular1.png"));
        // Glide.with(context).load(foodList.get(position).getImageUrl()).into(holder.foodImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(context, FoodDetailsActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                i.putExtra("id", foodList.get(position).getId());
//                i.putExtra("name", foodList.get(position).getName());
//                i.putExtra("name_res", foodList.get(position).getRestaurant().getName());
//                i.putExtra("address_res", foodList.get(position).getRestaurant().getAddress());
//                i.putExtra("price", foodList.get(position).getPrice());
//                i.putExtra("rating", "4.7");
//                i.putExtra("image", foodList.get(position).getImage());
//                i.putExtra("image_res", foodList.get(position).getRestaurant().getImage());
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