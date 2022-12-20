package com.da.tourandroid.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.da.tourandroid.R;
import com.da.tourandroid.TourDetailsActivity;
import com.da.tourandroid.model.LichTrinh;
import com.da.tourandroid.model.Tour;
import com.da.tourandroid.utils.Common;
import com.github.vipulasri.timelineview.TimelineView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TourViewHolder> {

    private Context context;
    private int layout;
    private ArrayList<LichTrinh> timelineList;
    private RequestQueue requestQueue;

    public TimelineAdapter(Context context, int layout, ArrayList<LichTrinh> timelineList) {
        this.context = context;
        this.layout = layout;
        this.timelineList = timelineList;
    }

    @NonNull
    @Override
    public TimelineAdapter.TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        requestQueue= Volley.newRequestQueue(view.getContext());
        return new TimelineAdapter.TourViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.TourViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.timelineTitle.setText(timelineList.get(position).getDiaDiem().getTenDiaDiem());
        holder.timelineDate.setText(timelineList.get(position).getThoiGianBatDau());
        if(Common.getMode()==1&&Common.getDetailMode()==3){
            holder.btnEdit.setVisibility(View.VISIBLE);
        }else{
            holder.btnEdit.setVisibility(View.INVISIBLE);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Thay đổi lịch trình
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_timeline);

                EditText editTextContent = dialog.findViewById(R.id.editText_content);
                EditText editTextTime=dialog.findViewById(R.id.editText_time);
                TextView title=dialog.findViewById(R.id.textView_title);
                title.setText(timelineList.get(position).getDiaDiem().getTenDiaDiem());
                editTextContent.setText(timelineList.get(position).getNoiDungLichTrinh());
                editTextTime.setText(timelineList.get(position).getThoiGianBatDau());
                Button buttonAdd = dialog.findViewById(R.id.btn_edit);
                Button buttonCancel = dialog.findViewById(R.id.btn_cancel);

                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editTextContent.getText().equals("")||editTextTime.getText().equals(""))
                            Toast.makeText(view.getContext(), "Please enter enough information!", Toast.LENGTH_SHORT).show();
                        else {
                            String json="{\"noiDungLichTrinh\":\""+editTextContent.getText()+"\","
                                    +"\"thoiGianBatDau\":\""+editTextTime.getText()+"\","
                                    +"\"trangThai\":"+timelineList.get(position).getTrangThai()+"}";
                            String url = Common.getHost() + "lichTrinh/edit/"+timelineList.get(position).getId().getMaTour()+"/"
                                    +timelineList.get(position).getId().getMaDiaDiem()+"/"+timelineList.get(position).getId().getSttLichTrinh();
                            try {
                                JSONObject req=new JSONObject(json);
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, req,
                                        response -> {
                                            try {
                                                if(!response.get("noiDungLichTrinh").equals(null)){
                                                    //edit thành công
                                                    Toast.makeText(view.getContext(), "Edit timeline successfully", Toast.LENGTH_LONG).show();
                                                }else{
                                                    Toast.makeText(view.getContext(), "Error edit timeline!", Toast.LENGTH_LONG).show();
                                                }
                                            } catch (JSONException e) {
                                                Log.i("e:",e.toString());
                                                e.printStackTrace();
                                            }
                                        }, error -> Toast.makeText(view.getContext(), "Server error!", Toast.LENGTH_LONG).show()) {
                                    /**
                                     * Passing some request headers
                                     */
                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        HashMap<String, String> headers = new HashMap<String, String>();
                                        headers.put("Authorization", "Bearer " + Common.getToken());
                                        return headers;
                                    }
                                };
                                requestQueue.add(request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                buttonCancel.setOnClickListener(view1 -> {
                    dialog.dismiss();
                });

                dialog.show();
            }
        });
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
        Button btnEdit;

        public TourViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);
            timelineTitle = itemView.findViewById(R.id.text_timeline_title);
            timelineDate = itemView.findViewById(R.id.text_timeline_date);
            btnEdit =itemView.findViewById(R.id.btnEdit);

            mTimelineView.initLine(viewType);
        }
    }
}