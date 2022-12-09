package com.da.tourandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.da.tourandroid.R;
import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TourViewHolder> {

    private Context context;
    private int layout;
    private ArrayList<LichTrinh> timelineList;

    public TimelineAdapter(Context context, int layout, ArrayList<LichTrinh> timelineList) {
        this.context = context;
        this.layout = layout;
        this.timelineList = timelineList;
    }

    @NonNull
    @Override
    public TimelineAdapter.TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new TimelineAdapter.TourViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.TourViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.timelineTitle.setText(timelineList.get(position).getDiaDiem().getTenDiaDiem());
        holder.timelineDate.setText(timelineList.get(position).getThoiGianBatDau());
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return timelineList.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {

        TimelineView mTimelineView;
        TextView timelineTitle, timelineDate;

        public TourViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);
            timelineTitle = itemView.findViewById(R.id.text_timeline_title);
            timelineDate = itemView.findViewById(R.id.text_timeline_date);

            mTimelineView.initLine(viewType);
        }
    }
}