package com.mif.moms.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.mif.moms.R;
import com.mif.moms.configfile.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText nama, username, email, password;
    MaterialButton buttonsimpan;
    TextView login;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        getSupportActionBar().hide();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(log);
                finish();
            }
        });

        buttonsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }

    private void init(){
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        nama = findViewById(R.id.edt_nama_register);
        username = findViewById(R.id.edt_username_register);
        email = findViewById(R.id.edt_email_register);
        password = findViewById(R.id.edt_password_register);
        buttonsimpan = findViewById(R.id.btn_simpan_register);
        login = findViewById(R.id.txt_login);
    }

    public void simpan(){

        final String namanya = nama.getText().toString().trim();
        final String usernamenya = username.getText().toString().trim();
        final String emailnya = email.getText().toString().trim();
        final String passwordnya = password.getText().toString().trim();

        if (namanya.matches("")){
            Toast.makeText(RegisterActivity.this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (usernamenya.matches("")){
            Toast.makeText(RegisterActivity.this, "Username tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordnya.matches("")){
            Toast.makeText(RegisterActivity.this, "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (emailnya.matches("")){
            Toast.makeText(RegisterActivity.this, "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_REGIS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){
//                        String message = jsonObject.getString("message");
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                        Intent logeng = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(logeng);
                        finish();
                    } else if (status.equals(false)) {
//                        String message = jsonObject.getString("message");
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("TAG", "onResponse: status " + status);
                } catch (JSONException e){
                    progressDialog.dismiss();
                    Log.e("TAG", "onResponse: " + e.getMessage());
                    Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("TAG", "onErrorResponse: " + error.getMessage());
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", namanya);
                params.put("username", usernamenya);
                params.put("password", passwordnya);
                params.put("email", emailnya);
                params.put("status", "Orang Tua");
                Log.e("data dari param: ", "" + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(25000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        Intent abc = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(abc);
        finish();
    }

}