package com.da.tourandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.da.tourandroid.R;
import com.da.tourandroid.adapter.FeedbackAdapter;
import com.da.tourandroid.model.PhanHoi;
import com.da.tourandroid.model.PhanHoiID;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {

    private ListView listViewFeedback;

    private FeedbackAdapter feedbackAdapter;
    private ArrayList<PhanHoi> feedbacks;

    private View view;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
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
        view = inflater.inflate(R.layout.fragment_favourite, container, false);

        listViewFeedback = view.findViewById(R.id.listView_feedback);
        feedbacks = new ArrayList<>();

        feedbackAdapter = new FeedbackAdapter(view.getContext(), R.layout.items_feedback, feedbacks);
        getFeedbacks();
        listViewFeedback.setAdapter(feedbackAdapter);

        return view;
    }

    private void getFeedbacks() {
        feedbacks.clear();
        feedbacks.add(new PhanHoi(new PhanHoiID(1, "0357499653"), "1. For using Glide in the android project, we have to add the dependency in gradle file. So, For adding dependency open app/build.gradle file in the app folder in your Android project and add the following lines inside it. ", "05/12/2022 03:10 PM"));
        feedbacks.add(new PhanHoi(new PhanHoiID(1, "0357499653"), "2. Now add InternetPermission inside the AndroidManifest.xml file. Open the manifest.xml file and add the following line. ", "30/11/2022 07:08 AM"));
        feedbacks.add(new PhanHoi(new PhanHoiID(1, "0357499653"), "3. Open the layout file for the main Activity. We need to add an ImageView to the layout. It doesn’t need to be fancy. The following code snippet shows you what I mean.", "03/12/2022 08:09 PM"));
        feedbackAdapter.notifyDataSetChanged();
    }
}