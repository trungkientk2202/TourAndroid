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

import com.da.tourandroid.R;
import com.da.tourandroid.model.KhachHang;

import java.util.List;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.KhachHangViewHolder> {
    private Context context;
    private int layout;
    private List<KhachHang> resList;

    public UserSearchAdapter(Context context, int layout, List<KhachHang> resList) {
        this.context = context;
        this.layout = layout;
        this.resList = resList;
    }

    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new UserSearchAdapter.KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.resImage.setImageResource(R.drawable.avatar);
        holder.resName.setText(resList.get(position).getTen());
        holder.phone.setText(resList.get(position).getSdt());
    }

    @Override
    public int getItemCount() {
        return resList.size();
    }

    public static class KhachHangViewHolder extends RecyclerView.ViewHolder {
        ImageView resImage;
        TextView resName, phone;

        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);

            resImage = itemView.findViewById(R.id.shapeableImageView_image);
            resName = itemView.findViewById(R.id.textView_name);
            phone = itemView.findViewById(R.id.textView_res);
        }
    }
}
