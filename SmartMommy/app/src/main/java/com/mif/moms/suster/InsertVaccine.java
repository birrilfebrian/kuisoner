package com.mif.moms.suster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mif.moms.R;
import com.mif.moms.auth.LoginActivity;
import com.mif.moms.auth.RegisterActivity;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.configfile.authdata;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InsertVaccine extends AppCompatActivity {

    authdata authdataa;
    TextInputEditText edtInsertVaccine;
    FloatingActionButton fabConfirm, fabCancel;
    String iStrUserId, iStrIDAnak;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_vaccine);

        init();
        getIntentData();

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fabConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }

    private void init() {
        authdataa = new authdata(this);
        iStrUserId = authdataa.getId_user();
        fabConfirm = findViewById(R.id.fabConfirm);
        fabCancel = findViewById(R.id.fabCancel);
        edtInsertVaccine= findViewById(R.id.edtInsertVaccine);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

    }

    private void getIntentData() {
        Intent intent = getIntent();
        iStrIDAnak = intent.getStringExtra("id_anak");
    }

    public void simpan(){

        final String strImun = edtInsertVaccine.getText().toString().trim();

        if (strImun.matches("")){
            Toast.makeText(InsertVaccine.this, "Tuliskan Jenis Imunisasi terlebih dahulu!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_SUSTER + "/tambahTemp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){
                        progressDialog.dismiss();
                        Toast.makeText(InsertVaccine.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (status.equals(false)) {
                        progressDialog.dismiss();
                        Toast.makeText(InsertVaccine.this, "Insert Imunisasi Failed", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("TAG", "onResponse: status " + status);
                } catch (JSONException e){
                    progressDialog.dismiss();
                    Log.e("TAG", "onResponse: " + e.getMessage());
                    Toast.makeText(InsertVaccine.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("TAG", "onErrorResponse: " + error.getMessage());
                Toast.makeText(InsertVaccine.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_anak", iStrIDAnak);
                params.put("id_user", iStrUserId);
                params.put("nama_imunisasi", edtInsertVaccine.getText().toString().trim());
                Log.e("data dari param: ", "" + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(25000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}