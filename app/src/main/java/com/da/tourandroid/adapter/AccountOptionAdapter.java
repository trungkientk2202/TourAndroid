package com.da.tourandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.da.tourandroid.LoginActivity;
import com.da.tourandroid.R;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.AccountOption;
import com.da.tourandroid.utils.Common;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AccountOptionAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<AccountOption> options;

    public AccountOptionAdapter(Context context, int layout, ArrayList<AccountOption> options) {
        this.context = context;
        this.layout = layout;
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.size();
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
        TextView textView;
        ShapeableImageView imageView;
        LinearLayout linearLayout;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder.imageView = view.findViewById(R.id.shapeableImageView_optLogo);
            viewHolder.textView = view.findViewById(R.id.textView_optName);
            viewHolder.linearLayout = view.findViewById(R.id.linearLayout_accountOption);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        AccountOption opt = options.get(i);
        viewHolder.textView.setText(opt.getOptionName());
        viewHolder.imageView.setImageResource(opt.getOptionLogo());

        switch (options.size()) {
            case 6:
                viewHolder.imageView.setBackgroundColor(Color.parseColor("#008dde"));
                break;
            case 2:
                viewHolder.imageView.setBackgroundColor(Color.parseColor("#17a47a"));
                break;
            case 3:
                viewHolder.imageView.setBackgroundColor(Color.parseColor("#828284"));
                break;
        }

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (opt.getOptionName()) {
                    case "Payment":
                        break;
                    case "History":

                        break;
                    case "Invoice":
                        break;
                    case "Reward Credits":
                        break;
                    case "My Vouchers":
                        break;
                    case "For Shop Owners":
                        break;
                    case "Invite Friends":
                        break;
                    case "Feedback":
                        break;
                    case "User Policy":
                        break;
                    case "App Settings":
                        break;
                    case "Log Out":
                        SharedPreferences.Editor editor = context.getSharedPreferences("Account", Context.MODE_PRIVATE).edit();
                        editor.remove("myTaiKhoan");
                        editor.remove("myToken");
                        editor.remove("myKhachHang");
                        editor.apply();
                        //-----------------
                        Common.lichTrinhs = null;
                        Common.tours = null;
                        //--------------
                        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });


        return view;
    }
}