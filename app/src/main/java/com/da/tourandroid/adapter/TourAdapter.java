package com.da.tourandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.da.tourandroid.R;
import com.da.tourandroid.model.Tour;

import java.util.List;

public class TourAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Tour> tourList;

    public TourAdapter(Context context, int layout, List<Tour> tourList) {
        this.context = context;
        this.layout = layout;
        this.tourList = tourList;
    }

    @Override
    public int getCount() {
        return tourList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView img_tour;
        TextView txt_name, txt_date, txt_to, txt_price;
        Button btn_order, btn_received, btn_cancel;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TourAdapter.ViewHolder viewHolder;
        Tour tour = tourList.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder = new TourAdapter.ViewHolder();

            viewHolder.img_tour = view.findViewById(R.id.img_ordered_food);
            viewHolder.txt_name = view.findViewById(R.id.txt_ordered_food);
            viewHolder.txt_date = view.findViewById(R.id.txtDate);
            viewHolder.txt_to = view.findViewById(R.id.txt_ordered_quantity);
            viewHolder.txt_price = view.findViewById(R.id.txtRes);
            
            switch (layout) {
                case R.layout.items_history:
                    viewHolder.btn_order = view.findViewById(R.id.btn_reorder);
                    viewHolder.btn_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Food food = order.getFood();
//                            Intent i = new Intent(context, FoodDetailsActivity.class);
//
//                            i.putExtra("id", food.getId());
//                            i.putExtra("name", food.getName());
//                            i.putExtra("name_res", food.getRestaurant().getName());
//                            i.putExtra("address_res", food.getRestaurant().getAddress());
//                            i.putExtra("price", food.getPrice());
//                            i.putExtra("rating", "4.7");
//                            i.putExtra("image", food.getImage());
//                            i.putExtra("image_res", food.getRestaurant().getImage());
//
//                            context.startActivity(i);
                        }
                    });
                    break;
                case R.layout.items_onplan:
//                    viewHolder.btn_received = view.findViewById(R.id.btn_receiver);
//                    viewHolder.btn_cancel = view.findViewById(R.id.btn_cancel);
//                    viewHolder.btn_received.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            tourList.remove(i);
//                            notifyDataSetChanged();
//                            database.QueryData("UPDATE OrderFood SET Status=" + 2 + " WHERE Id=" + order.getId());
//                            Toast.makeText(context, "Thank you!!!\nEnjoy your meal!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    viewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            tourList.remove(i);
//                            notifyDataSetChanged();
//                            database.QueryData("UPDATE OrderFood SET Status=" + 3 + " WHERE Id=" + order.getId());
//                            Toast.makeText(context, "Deleted this order(" + order.getFood().getName() + ")", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    break;
            }

            view.setTag(viewHolder);
        } else {
            viewHolder = (TourAdapter.ViewHolder) view.getTag();
        }
//
//        viewHolder.txt_name.setText(order.getFood().getName());
//        viewHolder.txt_price.setText(String.valueOf(order.getPrice_total()));
//        viewHolder.txt_date.setText(order.getFood().getRestaurant().getName());
//        viewHolder.txt_price.setText(String.valueOf(order.getQuantity()));
        Glide.with(context)
                .load("https://res.cloudinary.com/dtsahwrtk/image/upload/v1635424284/samples/landscapes/nature-mountains.jpg")
                .into(viewHolder.img_tour);
        return view;
    }
}