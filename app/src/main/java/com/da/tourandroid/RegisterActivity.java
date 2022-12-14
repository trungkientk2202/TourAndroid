package com.da.tourandroid;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private ImageView imageViewBack;
    private TextView txtPhone,txtName,txtBirthday,txtPass,txtConfirmPass,txtZalo;
    private Button btnRegister;
    private RadioGroup radioGroup;
    private RadioButton radioMale,radioFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageViewBack = findViewById(R.id.imageView_back);
        btnRegister= findViewById(R.id.btn_register);
        txtPhone=findViewById(R.id.register_sdt);
        txtName=findViewById(R.id.register_name);
        txtBirthday=findViewById(R.id.register_birthday);
        txtPass=findViewById(R.id.register_password);
        txtConfirmPass=findViewById(R.id.register_confirm_password);
        radioGroup=findViewById(R.id.register_sex);
        radioMale=findViewById(R.id.register_male);
        radioFemale=findViewById(R.id.register_female);
        txtZalo=findViewById(R.id.register_zalo);
        requestQueue= Volley.newRequestQueue(this);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtPhone.getText().toString().equals("")||txtName.getText().toString().equals("")||txtBirthday.getText().toString().equals("")||txtPass.getText().toString().equals("")){
                    Toast.makeText(view.getContext(),"Please enter enough information!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!txtPass.getText().toString().equals(txtConfirmPass.getText().toString())){
                    Toast.makeText(view.getContext(),"Password doesn't match!",Toast.LENGTH_LONG).show();
                    return;
                }
                String json="{\"sdt\":\""+txtPhone.getText().toString()+"\","
                        +"\"ten\":\""+txtName.getText().toString()+"\","
                        +"\"ngaySinh\":\""+txtBirthday.getText().toString()+"\","
                        +"\"phai\":"+(radioGroup.getCheckedRadioButtonId()==radioMale.getId())+","
                        +"\"matKhau\":\""+txtPass.getText().toString()+"\","
                        +"\"zalo\":\""+txtZalo.getText().toString()+"\"}";
                try {
                    JSONObject req=new JSONObject(json);
                    String url = Common.getHost()+"khachHang/add";
                    JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, req,
                            response -> {
                                try {
                                    if(response.getString("sdt")!=null){
                                        Toast.makeText(view.getContext(),"Register successfully!",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }, error -> {
                        Toast.makeText(view.getContext(),"Server error!",Toast.LENGTH_LONG).show();
                    });
                    requestQueue.add(request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}