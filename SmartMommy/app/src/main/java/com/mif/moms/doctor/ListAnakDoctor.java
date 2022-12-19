package com.mif.moms.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mif.moms.MainActivity;
import com.mif.moms.R;
import com.mif.moms.activity.PilihAnakActivity;
import com.mif.moms.adapter.AdapterListAnak;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.model.ModelListAnak;
import com.mif.moms.soal.SoalActivity;
import com.mif.moms.suster.HomeSuster;
import com.mif.moms.suster.InputVaccine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListAnakDoctor extends AppCompatActivity {
    String onActivity;
    ImageView imgBack;
    SearchView searchView;
    RecyclerView recyclerAnak;
    List<ModelListAnak> item;
    AdapterListAnak adapterListAnak;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    JSONArray dataArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_anak_doctor);
        getSupportActionBar().hide();
        getIntentData();

        imgBack = findViewById(R.id.imageView2);
        searchView = findViewById(R.id.searchView);
        recyclerAnak = findViewById(R.id.recyclerAnak);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

        loadAnak();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.equals(""))
                {
                    item.clear();
                    for (int i = 0; i < dataArray.length(); i++)
                        try {
                            JSONObject datanya = dataArray.getJSONObject(i);
                            ModelListAnak modelListAnak = new ModelListAnak();
                            modelListAnak.setId_anak(datanya.getString("id_anak"));
                            modelListAnak.setNama_anak(datanya.getString("nama_anak"));
                            modelListAnak.setTanggal_lahir(datanya.getString("tanggal_lahir"));
                            modelListAnak.setFoto_anak(datanya.getString("foto_anak"));
                            item.add(modelListAnak);
                        } catch (Exception ea) {
                            Log.e("erronya atas", "" + ea);
                            ea.printStackTrace();
                        }
                    adapterListAnak = new AdapterListAnak(ListAnakDoctor.this, item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListAnakDoctor.this);
                    recyclerAnak.setLayoutManager(layoutManager);
                    recyclerAnak.setAdapter(adapterListAnak);

                    adapterListAnak.setListener(new AdapterListAnak.OnHistoryClickListener() {
                        @Override
                        public void onClick(int position) {
                            ModelListAnak modelListAnak = item.get(position);
                            if (onActivity.equals("HistoImun")){
                                Intent detail = new Intent(ListAnakDoctor.this, HistoryImunisasi.class);
                                detail.putExtra("id_anak", modelListAnak.getId_anak());
                                startActivity(detail);
                                finish();
                            } else if (onActivity.equals("Suster")) {
                                Intent detail = new Intent(ListAnakDoctor.this, InputVaccine.class);
                                detail.putExtra("id_anak", modelListAnak.getId_anak());
                                detail.putExtra("nama_anak", modelListAnak.getNama_anak());
                                detail.putExtra("tanggal_lahir", modelListAnak.getTanggal_lahir());
                                detail.putExtra("foto_anak", modelListAnak.getFoto_anak());
                                startActivity(detail);
                                finish();
                            } else if (onActivity.equals("HistoImunSuster")){
                                Intent detail = new Intent(ListAnakDoctor.this, HistoryImunisasi.class);
                                detail.putExtra("onActivity", "HistoImunSuster");
                                detail.putExtra("id_anak", modelListAnak.getId_anak());
                                startActivity(detail);
                                finish();
                            } else {

                            }
                        }
                    });
                }
                else
                {
                    adapterListAnak.getFilter().filter(newText);
                    return false;
                }
                return true;
            }
        });
    }

    private void loadAnak(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_ANAK + "/listanakAll", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dataArray = jsonObject.getJSONArray("data");

                    item = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++)
                    {
                        ModelListAnak modelListAnak = new ModelListAnak();
                        JSONObject datanya = dataArray.getJSONObject(i);
                        modelListAnak.setId_anak(datanya.getString("id_anak"));
                        modelListAnak.setNama_anak(datanya.getString("nama_anak"));
                        modelListAnak.setTanggal_lahir(datanya.getString("tanggal_lahir"));
                        modelListAnak.setFoto_anak(datanya.getString("foto_anak"));
                        item.add(modelListAnak);
                    }
                    adapterListAnak = new AdapterListAnak(ListAnakDoctor.this, item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListAnakDoctor.this);
                    recyclerAnak.setLayoutManager(layoutManager);
                    recyclerAnak.setAdapter(adapterListAnak);

                    adapterListAnak.setListener(new AdapterListAnak.OnHistoryClickListener() {
                        @Override
                        public void onClick(int position) {
                            ModelListAnak modelListAnak = item.get(position);
                            if (onActivity.equals("HistoImun")){
                                Intent detail = new Intent(ListAnakDoctor.this, HistoryImunisasi.class);
                                detail.putExtra("onActivity", "histo_dokter");
                                detail.putExtra("id_anak", modelListAnak.getId_anak());
                                startActivity(detail);
                                finish();
                            } else if (onActivity.equals("Suster")) {
                                Intent detail = new Intent(ListAnakDoctor.this, InputVaccine.class);
                                detail.putExtra("id_anak", modelListAnak.getId_anak());
                                detail.putExtra("nama_anak", modelListAnak.getNama_anak());
                                detail.putExtra("tanggal_lahir", modelListAnak.getTanggal_lahir());
                                detail.putExtra("foto_anak", modelListAnak.getFoto_anak());
                                startActivity(detail);
                                finish();
                            } else if (onActivity.equals("HistoImunSuster")) {
                                Intent detail = new Intent(ListAnakDoctor.this, HistoryImunisasi.class);
                                detail.putExtra("onActivity", "HistoImunSuster");
                                detail.putExtra("id_anak", modelListAnak.getId_anak());
                                startActivity(detail);
                                finish();
                            } else {
                                Intent detail = new Intent(ListAnakDoctor.this, HistoryQuestioner.class);
                                detail.putExtra("id_anak", modelListAnak.getId_anak());
                                startActivity(detail);
                                finish();
                            }
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(ListAnakDoctor.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ListAnakDoctor.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (onActivity.equals("HistoImun")){
            Intent detail = new Intent(ListAnakDoctor.this, HomeDoctor.class);
            startActivity(detail);
            finish();
        } else if (onActivity.equals("Suster")) {
            Intent detail = new Intent(ListAnakDoctor.this, HomeSuster.class);
            startActivity(detail);
            finish();
        } else if (onActivity.equals("HistoImunSuster")) {
            Intent detail = new Intent(ListAnakDoctor.this, HomeSuster.class);
            startActivity(detail);
            finish();
        } else {
            Intent detail = new Intent(ListAnakDoctor.this, HomeDoctor.class);
            startActivity(detail);
            finish();
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        onActivity = intent.getStringExtra("onActivity");
    }
}