package com.da.tourandroid.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.da.tourandroid.R;
import com.da.tourandroid.adapter.AccountOptionAdapter;
import com.da.tourandroid.utils.AccountOption;
import com.da.tourandroid.utils.Common;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    private final ArrayList<AccountOption> opts1 = new ArrayList<>(Arrays.asList(
            new AccountOption("Payment", R.drawable.outline_payment_24),
            new AccountOption("History", R.drawable.outline_history_24),
            new AccountOption("Invoice", R.drawable.outline_receipt_24),
            new AccountOption("Reward Credits", R.drawable.outline_card_giftcard_24),
            new AccountOption("My Vouchers", R.drawable.outline_sell_24)));
            // new AccountOption("For Shop Owners", R.drawable.shoppee_food_logo)));

    private final ArrayList<AccountOption> opts2 = new ArrayList<>(Arrays.asList(
            new AccountOption("Invite Friends", R.drawable.outline_person_add_24),
            new AccountOption("Feedback", R.drawable.outline_feedback_24)));

    private final ArrayList<AccountOption> opts3 = new ArrayList<>(Arrays.asList(
            new AccountOption("User Policy", R.drawable.outline_policy_24),
            new AccountOption("App Settings", R.drawable.outline_settings_24),
            new AccountOption("Log Out", R.drawable.outline_logout_24)));

    private View view;

    private ShapeableImageView shapeableImageViewAvatar;
    private TextView textViewAccountName;

    private ListView listViewOpts1, listViewOpts2, listViewOpts3;

    SharedPreferences sharedPreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);

        sharedPreferences = view.getContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        initUI();

        return view;
    }

    private void initUI() {
        shapeableImageViewAvatar = view.findViewById(R.id.shapeableImageView_avatar);
        textViewAccountName = view.findViewById(R.id.textView_accountName);
        if(Common.getMode()==1) {
            textViewAccountName.setText(Common.getTaiKhoan().getTen());
        }else{
            textViewAccountName.setText(Common.getKhachHang().getTen());
        }
         // TODO: set user's name and avatar
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("myAccount", "");
//        Account account = gson.fromJson(json, Account.class);

//        textViewAccountName.setText(account.getName());
//        Glide.with(view.getContext()).load(account.getAvatar()).into(shapeableImageViewAvatar);

        listViewOpts1 = view.findViewById(R.id.listView_opt1);
        listViewOpts2 = view.findViewById(R.id.listView_opt2);
        listViewOpts3 = view.findViewById(R.id.listView_opt3);

        listViewOpts1.setAdapter(new AccountOptionAdapter(view.getContext(), R.layout.items_account_option, opts1));
        listViewOpts2.setAdapter(new AccountOptionAdapter(view.getContext(), R.layout.items_account_option, opts2));
        listViewOpts3.setAdapter(new AccountOptionAdapter(view.getContext(), R.layout.items_account_option, opts3));
    }
}