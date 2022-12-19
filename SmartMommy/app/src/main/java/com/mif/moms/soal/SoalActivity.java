package com.mif.moms.soal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.mif.moms.MainActivity;
import com.mif.moms.R;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.configfile.authdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SoalActivity extends AppCompatActivity {
    MaterialButton btnya, btntidak;
    TextView txtjenis, txtsoal;

    ProgressDialog progressDialog;
    authdata authdataa;
    RequestQueue requestQueue;

    String id_anak;
    String id_jenis;
    String id_user;
    String id_paket;
    String id_soal;
    String id_hasil;
    int jumlah_soal;
    int jumlahsoal1 = 1;
    int jumlahsoalfiks;
    int nomorsoal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        init();
        getSupportActionBar().hide();

        id_user = authdataa.getId_user();
        Intent intent = getIntent();
        id_anak = intent.getStringExtra("id_anak");
        id_jenis = intent.getStringExtra("id_jenis");

        loadSoalAwal();
        btnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanYa();
            }
        });
        btntidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanTidak();
            }
        });
    }

    private void init(){
        authdataa = new authdata(this);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        txtjenis = findViewById(R.id.text_jenis);
        txtsoal = findViewById(R.id.text_soal);
        btnya = findViewById(R.id.btn_ya);
        btntidak = findViewById(R.id.btn_tidak);
    }

    public void loadSoal(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_PAKET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    JSONObject soal = data.getJSONObject(nomorsoal);
                    id_soal = soal.getString("id_soal");
                    id_paket = soal.getString("id_paket");
                    System.out.println("Id Paket : " + id_paket);
                    jumlah_soal = soal.getInt("jumlah_soal");
                    jumlahsoalfiks = jumlah_soal - jumlahsoal1;
                    txtsoal.setText(soal.getString("soal"));
                } catch (JSONException e){
                    Toast.makeText(SoalActivity.this, "Soal tidak ada, umur dan quisioner tidak sesuai!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SoalActivity.this, MainActivity.class));
                    SoalActivity.this.finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SoalActivity.this, "Soal tidak ada!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("jenis", id_jenis);
                params.put("id_anak", id_anak);
//                params.put("id_user", id_user);
                Log.e("data dari param: ", "" + params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void loadSoalAwal(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_PAKET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    JSONObject soal = data.getJSONObject(nomorsoal);
                    id_soal = soal.getString("id_soal");
                    id_paket = soal.getString("id_paket");
                    System.out.println("Id Paket : " + id_paket);
                    jumlah_soal = soal.getInt("jumlah_soal");
                    jumlahsoalfiks = jumlah_soal - jumlahsoal1;
                    txtsoal.setText(soal.getString("soal"));
                    postSoal();
                } catch (JSONException e){
                    Toast.makeText(SoalActivity.this, "Soal tidak ada, umur dan quisioner tidak sesuai!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SoalActivity.this, MainActivity.class));
                    SoalActivity.this.finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SoalActivity.this, "Soal tidak ada!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("jenis", id_jenis);
                params.put("id_anak", id_anak);
//                params.put("id_user", id_user);
                Log.e("data dari param: ", "" + params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void postSoal(){
//        System.out.println("id_paket : " + id_paket);
//        return;
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_PAKET + "/hasil1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("data");
                    id_hasil = data;
                } catch (JSONException e){
                    Toast.makeText(SoalActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SoalActivity.this, "Soal tidak ada!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("jenis", id_jenis);
                params.put("id_anak", id_anak);
                params.put("id_user", id_user);
                params.put("id_paket", id_paket);
                Log.e("data dari param: ", "" + params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void simpanYa(){
//        System.out.println("Id Hasil " + id_hasil);
//        return;
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_PAKET + "/dhasil", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(SoalActivity.this, "Berhasil menjawab", Toast.LENGTH_SHORT).show();
                    nomorsoal++;
                    if (nomorsoal > jumlahsoalfiks){
                        simpanHasil();
                    } else {
                        loadSoal();
                    }
                } catch (JSONException e){
                    Toast.makeText(SoalActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SoalActivity.this, "Gagal menjawab", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_hasil", id_hasil);
                params.put("id_soal", id_soal);
                params.put("jawaban", "1");
                Log.e("data dari Ya: ", "" + params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void simpanTidak(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_PAKET + "/dhasil", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(SoalActivity.this, "Berasil menjawab", Toast.LENGTH_SHORT).show();
                    nomorsoal++;
                    if (nomorsoal > jumlahsoalfiks){
                        simpanHasil();
                    } else {
                        loadSoal();
                    }
                } catch (JSONException e){
                    Toast.makeText(SoalActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SoalActivity.this, "Gagal menjawab", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_hasil", id_hasil);
                params.put("id_soal", id_soal);
                params.put("jawaban", "0");
                Log.e("data dari Tidak: ", "" + params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void simpanHasil(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_PAKET + "/hasil", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(SoalActivity.this, "Kuisioner telah selesai!", Toast.LENGTH_SHORT).show();
                    Intent selesai = new Intent(SoalActivity.this, HasilSoalActivity.class);
                    selesai.putExtra("id_hasil", id_hasil);
                    startActivity(selesai);
                    finish();
                } catch (JSONException e){
                    Toast.makeText(SoalActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SoalActivity.this, "Gagal!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_hasil", id_hasil);
                Log.e("data dari Hasil: ", "" + params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Selesaikan kuisioner terlebih dahulu!", Toast.LENGTH_SHORT).show();
    }
}