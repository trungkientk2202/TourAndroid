package com.da.tourandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.da.tourandroid.R;
import com.da.tourandroid.model.PhanHoi;

import java.util.ArrayList;

public class FeedbackAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<PhanHoi> feedbackList;

    public FeedbackAdapter(Context context, int layout, ArrayList<PhanHoi> feedbackList) {
        this.context = context;
        this.layout = layout;
        this.feedbackList = feedbackList;
    }

    @Override
    public int getCount() {
        return feedbackList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textViewName, textViewDate, textViewDesc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder.imageView = convertView.findViewById(R.id.tour_image);
            viewHolder.textViewName = convertView.findViewById(R.id.textView_name);
            viewHolder.textViewDate = convertView.findViewById(R.id.textView_date);
            viewHolder.textViewDesc = convertView.findViewById(R.id.textView_desc);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        PhanHoi feedback = feedbackList.get(position);
        if (feedback != null) {
            viewHolder.textViewName.setText(feedback.getTour().getDiemDen());
            viewHolder.textViewDate.setText(feedback.getThoiGian());
            viewHolder.textViewDesc.setText(feedback.getNoiDung());

//            viewHolder.imageView.setImageResource(feedback.getFood().getImage());
        Glide.with(context)
                .load(feedback.getTour().getImage())
                .into(viewHolder.imageView);
        }

        return convertView;
    }
}
