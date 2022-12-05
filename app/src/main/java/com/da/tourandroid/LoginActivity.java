package com.da.tourandroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.da.tourandroid.model.KhachHang;
import com.da.tourandroid.model.TaiKhoan;
import com.da.tourandroid.utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextPassword;

    private AppCompatButton buttonLogin;
    private AppCompatButton buttonRegister;
    private TextView buttonForgotPassword;
    private RequestQueue requestQueue;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);

        // just check existing "phone" or "password"
        if (sharedPreferences.contains("myAccount")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        editTextPhone = findViewById(R.id.editText_phone);
        editTextPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);
        buttonRegister = findViewById(R.id.button_signUp);
        buttonForgotPassword = findViewById(R.id.button_forgotPassword);
        requestQueue= Volley.newRequestQueue(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        // Hide keyboard when not being focus on editText
        editTextPhone.setOnFocusChangeListener((view, b) -> hideKeyboard(view));
        editTextPassword.setOnFocusChangeListener((view, b) -> hideKeyboard(view));

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
//                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent1);
                //
                String json="{\"username\":\""+editTextPhone.getText().toString()+"\",\"password\":\""+editTextPassword.getText().toString()+"\"}";
                try {
                    JSONObject req=new JSONObject(json);
                    String url =Common.getHost()+"login";
                    JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, req,
                            response -> {
                                try {
                                    Common.setToken(response.getString("token"));
                                    JSONObject tk=response.getJSONObject("taiKhoan");
                                    Common.setTaiKhoan(new TaiKhoan(tk.getString("sdt"),tk.getString("ten"),tk.getString("matKhau"),tk.getBoolean("phai"), !tk.getString("ngaySinh").equals("null") ?new SimpleDateFormat("dd-MMM-yyyy").parse(tk.getString("ngaySinh")):null,tk.getString("zalo")));
                                    Common.mode=1;
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } catch (JSONException | ParseException e) {
                                    e.printStackTrace();
                                }
                            }, error -> {
                                Log.i("error",error.toString());
                                Toast.makeText(LoginActivity.this,"Server error 1, please try again! "+ error,Toast.LENGTH_LONG).show();
                            });
                    requestQueue.add(request);
                } catch (JSONException e) {
//                    Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                try {
                    JSONObject req=new JSONObject(json);
                    String url =Common.getHost()+"kh/login";
                    JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, req,
                            response -> {
                                try {
                                    Common.setToken(response.getString("token"));
                                    JSONObject kh=response.getJSONObject("khachHang");
                                    Log.i("ngaySinh: ",kh.getString("ngaySinh"));
                                    Common.setKhachHang(new KhachHang(kh.getString("sdt"),kh.getString("ten"),kh.getString("matKhau"),kh.getBoolean("phai"),null,kh.getString("zalo")));
                                    Common.mode=2;
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } catch (JSONException  e) {
                                    e.printStackTrace();
                                }
                            }, error -> {
                        Log.i("error",error.toString());
                        Toast.makeText(LoginActivity.this,"Server error, please try again! "+ error,Toast.LENGTH_LONG).show();
                    });
                    requestQueue.add(request);
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}