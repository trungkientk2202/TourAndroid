package com.da.tourandroid.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

//import com.google.gson.Gson;
//
//import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceOngoingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceOngoingFragment extends Fragment {

    View view;
//    ArrayList<OrderFood> orders;
//    OrderAdapter adapter;

    private ListView listViewOngoing;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InvoiceOngoingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceOngoingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceOngoingFragment newInstance(String param1, String param2) {
        InvoiceOngoingFragment fragment = new InvoiceOngoingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_invoice_ongoing, container, false);
//        database = new Database(view.getContext(), "foody.sqlite", null, 1);
//        listViewOngoing = view.findViewById(R.id.listView_ongoing);
//        orders = new ArrayList<>();
//
//        adapter = new OrderAdapter(view.getContext(), R.layout.items_ongoing, orders);
//        getOrdersOngoing();
//        listViewOngoing.setAdapter(adapter);
//
//        return view;
//    }
//    private void getOrdersOngoing(){
//        Gson gson = new Gson();
//        String json = view.getContext().getSharedPreferences("Account", Context.MODE_PRIVATE).getString("myAccount", "");
//        Account account = gson.fromJson(json, Account.class);
//
//        Cursor dataOrderFoods = database.GetData("SELECT * FROM OrderFood WHERE Status = 1");
//        orders.clear();
//        while (dataOrderFoods.moveToNext()) {
//            int id = dataOrderFoods.getInt(0);
//
//            Cursor foodObj = database.GetData("SELECT * FROM Food WHERE Id=" + dataOrderFoods.getInt(1));
//            Food food = null;
//            while (foodObj.moveToNext()) {
//                Cursor restaurantObj = database.GetData("SELECT * FROM Restaurant WHERE Id=" + foodObj.getInt(1));
//                Restaurant restaurant = null;
//                while (restaurantObj.moveToNext()) {
//                    restaurant = new Restaurant(
//                            restaurantObj.getInt(0),
//                            restaurantObj.getString(1),
//                            restaurantObj.getString(2),
//                            restaurantObj.getInt(3));
//                }
//                food = new Food(
//                        foodObj.getInt(0),
//                        restaurant,
//                        foodObj.getString(2),
//                        foodObj.getInt(3),
//                        foodObj.getInt(4));
//            }
//
//            int quantity = dataOrderFoods.getInt(2);
//            int price = dataOrderFoods.getInt(3);
//            int status = dataOrderFoods.getInt(4);
//
//            orders.add(new OrderFood(id, food, quantity, price, status, account));
//        }
//        adapter.notifyDataSetChanged();
//    }
}