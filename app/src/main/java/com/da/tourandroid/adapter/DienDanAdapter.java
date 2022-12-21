package com.da.tourandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.R;
import com.da.tourandroid.model.DienDan;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

public class DienDanAdapter extends RecyclerView.Adapter<DienDanAdapter.DienDanViewHolder> {
    private Context context;
    private int layout;
    private ArrayList<DienDan> dienDans;
    private RequestQueue requestQueue;
    public DienDanAdapter(Context context, int layout, ArrayList<DienDan> dienDans) {
        this.context = context;
        this.layout = layout;
        this.dienDans = dienDans;
    }

    @NonNull
    @Override
    public DienDanAdapter.DienDanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        requestQueue= Volley.newRequestQueue(view.getContext());
        return new DienDanAdapter.DienDanViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull DienDanAdapter.DienDanViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.forumUsername.setText(dienDans.get(position).getSdt().toString());
        holder.forumDate.setText(dienDans.get(position).getThoiGian());
        holder.forumContent.setText(dienDans.get(position).getNoiDung());
    }
    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
    @Override
    public int getItemCount() {
        return dienDans.size();
    }
    public static class DienDanViewHolder extends RecyclerView.ViewHolder {

        TimelineView mTimelineView;
        AppCompatTextView forumUsername, forumDate,forumContent;

        public DienDanViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);
            forumUsername = itemView.findViewById(R.id.text_diendan_username);
            forumDate = itemView.findViewById(R.id.text_diendan_date);
            forumContent = itemView.findViewById(R.id.text_diendan_content);

            mTimelineView.initLine(viewType);
        }
    }
}
