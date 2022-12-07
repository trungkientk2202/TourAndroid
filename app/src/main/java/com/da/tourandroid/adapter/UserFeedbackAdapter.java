package com.da.tourandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.da.tourandroid.R;
import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.PhanHoi;

import java.util.ArrayList;

public class UserFeedbackAdapter extends RecyclerView.Adapter<UserFeedbackAdapter.TourViewHolder> {

    private Context context;
    private int layout;
    private ArrayList<PhanHoi> feedbackList;

    public UserFeedbackAdapter(Context context, int layout, ArrayList<PhanHoi> feedbackList) {
        this.context = context;
        this.layout = layout;
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public UserFeedbackAdapter.TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new UserFeedbackAdapter.TourViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserFeedbackAdapter.TourViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.feedbackUsername.setText(feedbackList.get(position).getKhachHang().getTen());
        holder.feedbackContent.setText(feedbackList.get(position).getNoiDung());
        holder.feedbackDate.setText(feedbackList.get(position).getThoiGian());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        TextView feedbackUsername, feedbackContent, feedbackDate;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);

            feedbackUsername = itemView.findViewById(R.id.textView_name);
            feedbackContent = itemView.findViewById(R.id.textView_desc);
            feedbackDate = itemView.findViewById(R.id.textView_date);
        }
    }


}