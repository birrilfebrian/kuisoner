package com.mif.moms.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mif.moms.MainActivity;
import com.mif.moms.R;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.configfile.authdata;
import com.mif.moms.doctor.HomeDoctor;
import com.mif.moms.suster.HomeSuster;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView register;
    EditText username, password;
    Button btnlogin;

    authdata authdataa;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        getSupportActionBar().hide();

        if (authdataa.isLogin() == true){
            if (authdataa.getStatusLogin().equals("Orang Tua")){
                Intent masuk = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(masuk);
                finish();
            } else if (authdataa.getStatusLogin().equals("Dokter")) {
                Intent masuk = new Intent(LoginActivity.this, HomeDoctor.class);
                startActivity(masuk);
                finish();
            } else if (authdataa.getStatusLogin().equals("Suster")) {
                Intent masuk = new Intent(LoginActivity.this, HomeSuster.class);
                startActivity(masuk);
                finish();
            } else {
                Intent masuk = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(masuk);
                finish();
            }
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regis = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regis);
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username belum diisi", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Password belum diisi", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });
    }

    private void init(){
        authdataa = new authdata(this);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        register = findViewById(R.id.txt_daftar);
        username = findViewById(R.id.edt_username_login);
        password = findViewById(R.id.edt_password_login);
        btnlogin = findViewById(R.id.btn_login);
    }

    public void login(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://baby.hasil-jaya.com/api/Login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("data").matches("Maaf Akun Anda Tidak Aktif")){
                        Toast.makeText(LoginActivity.this, "Account is not Actived!", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject data = jsonObject.getJSONObject("data");
                        authdataa.setdatauser(
                                data.getString("id_user"),
                                data.getString("username"),
                                data.getString("nama"),
                                data.getString("email"),
                                data.getString("foto"),
                                data.getString("status")
                        );

                        if (data.getString("status").equals("Orang Tua")) {
                            Intent masuk = new Intent(LoginActivity.this, MainActivity.class);
                            Toast.makeText(LoginActivity.this, data.getString("pesan"), Toast.LENGTH_SHORT).show();
                            startActivity(masuk);
                            finish();
                        } else if (data.getString("status").equals("Dokter")) {
                            Intent masuk = new Intent(LoginActivity.this, HomeDoctor.class);
                            Toast.makeText(LoginActivity.this, data.getString("pesan"), Toast.LENGTH_SHORT).show();
                            startActivity(masuk);
                            finish();
                        } else {
                            Intent masuk = new Intent(LoginActivity.this, HomeSuster.class);
                            Toast.makeText(LoginActivity.this, data.getString("pesan"), Toast.LENGTH_SHORT).show();
                            startActivity(masuk);
                            finish();
                        }

                    }
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Volley Errpr", ":" + error);
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(LoginActivity.this, "Username / Password yang anda masukkan salah !", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString().trim());
                params.put("password", password.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}