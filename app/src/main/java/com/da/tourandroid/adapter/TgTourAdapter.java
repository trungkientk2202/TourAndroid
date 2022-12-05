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

import com.da.tourandroid.R;
import com.da.tourandroid.model.Tour;

import java.util.List;
public class TgTourAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Tour> tours;

    public TgTourAdapter(Context context, int layout, List<Tour> tours) {
        this.context = context;
        this.layout = layout;
        this.tours = tours;
    }

    @Override
    public int getCount() {
        return tours.size();
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
        ImageView img_food;
        TextView txt_food, txt_price, txt_quantity, txt_address;
        Button btn_order, btn_received, btn_cancel;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TgTourAdapter.ViewHolder viewHolder;
        Tour tour = tours.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder = new TgTourAdapter.ViewHolder();

            viewHolder.txt_food = view.findViewById(R.id.txt_ordered_food);
            viewHolder.txt_address = view.findViewById(R.id.txtRes);
            viewHolder.img_food = view.findViewById(R.id.img_ordered_food);
            viewHolder.txt_price = view.findViewById(R.id.txt_ordered_price);
            viewHolder.txt_quantity = view.findViewById(R.id.txt_ordered_quantity);
            switch (layout) {
//                case R.layout.items_history:
//                    viewHolder.btn_order = view.findViewById(R.id.btn_reorder);
//                    viewHolder.btn_order.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
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
//                        }
//                    });
//                    break;
                case R.layout.items_ongoing:
                    viewHolder.btn_received = view.findViewById(R.id.btn_receiver);
                    viewHolder.btn_cancel = view.findViewById(R.id.btn_cancel);
                    viewHolder.btn_received.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            orderList.remove(i);
//                            notifyDataSetChanged();
//                            database.QueryData("UPDATE OrderFood SET Status=" + 2 + " WHERE Id=" + order.getId());
//                            Toast.makeText(context, "Thank you!!!\nEnjoy your meal!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    viewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            orderList.remove(i);
//                            notifyDataSetChanged();
//                            database.QueryData("UPDATE OrderFood SET Status=" + 3 + " WHERE Id=" + order.getId());
//                            Toast.makeText(context, "Deleted this order(" + order.getFood().getName() + ")", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }

            view.setTag(viewHolder);
        } else {
            viewHolder = (TgTourAdapter.ViewHolder) view.getTag();
        }
//
//        viewHolder.txt_food.setText(order.getFood().getName());
//        viewHolder.txt_price.setText(String.valueOf(order.getPrice_total()));
//        viewHolder.txt_address.setText(order.getFood().getRestaurant().getName());
//        viewHolder.img_food.setImageResource(order.getFood().getImage());
//        viewHolder.txt_quantity.setText(String.valueOf(order.getQuantity()));

        return view;
    }
}