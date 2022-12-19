package com.mif.moms.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mif.moms.MainActivity;
import com.mif.moms.R;
import com.mif.moms.adapter.AdapterImunisasi;
import com.mif.moms.adapter.AdapterListRiwayatDokter;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.model.ModelDetailImunisasi;
import com.mif.moms.model.ModelDetailRiwayat;
import com.mif.moms.model.ModelImunisasi;
import com.mif.moms.model.ModelRiwayat;
import com.mif.moms.suster.HomeSuster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryImunisasi extends AppCompatActivity {
    SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;
    ArrayList<ModelImunisasi> parentItemArrayList;
    AdapterImunisasi adapterImunisasi;
    List<ModelDetailImunisasi> childItemArrayList;

    JSONArray dataArr;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String mIdAnak, id_imunisasi = "", tgl_imunisasi, berat_anak, tinggi_anak, onActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_imunisasi);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        mIdAnak = intent.getStringExtra("id_anak");
        onActivity = intent.getStringExtra("onActivity");

        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerRiwayat);

        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

        loadRiwayat();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRiwayat();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void loadRiwayat(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_ANAK + "/riwayatImun?id_anak=" + mIdAnak, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("true")) {
                        dataArr = jsonObject.getJSONArray("data");
                        parentItemArrayList = new ArrayList<>();
                        childItemArrayList = new ArrayList<>();

                        for (int i = 0; i < dataArr.length(); i++) {
                            JSONObject dataobj = dataArr.getJSONObject(i);
                            if (i == dataArr.length()-1) {
                                if (id_imunisasi.trim().equals("")) {
                                    id_imunisasi = dataobj.getString("id_imunisasi");
                                    tgl_imunisasi = dataobj.getString("tgl_imunisasi");
                                    berat_anak = dataobj.getString("berat_anak");
                                    tinggi_anak = dataobj.getString("tinggi_anak");
                                    ModelDetailImunisasi modelChild = new ModelDetailImunisasi(dataobj.getString("nama_imunisasi"));
                                    childItemArrayList.add(modelChild);

                                    parentItemArrayList.add(new ModelImunisasi(childItemArrayList, id_imunisasi, tgl_imunisasi, berat_anak, tinggi_anak));
                                } else  {
                                    if (id_imunisasi.trim().equals(dataobj.getString("id_imunisasi"))){
                                        ModelDetailImunisasi modelChild = new ModelDetailImunisasi(dataobj.getString("nama_imunisasi"));
                                        childItemArrayList.add(modelChild);

                                        parentItemArrayList.add(new ModelImunisasi(childItemArrayList, id_imunisasi, tgl_imunisasi, berat_anak, tinggi_anak));
                                    } else {
                                        parentItemArrayList.add(new ModelImunisasi(childItemArrayList, id_imunisasi, tgl_imunisasi, berat_anak, tinggi_anak));
                                        childItemArrayList = new ArrayList<>();

                                        id_imunisasi = dataobj.getString("id_imunisasi");
                                        tgl_imunisasi = dataobj.getString("tgl_imunisasi");
                                        berat_anak = dataobj.getString("berat_anak");
                                        tinggi_anak = dataobj.getString("tinggi_anak");
                                        ModelDetailImunisasi modelChild = new ModelDetailImunisasi(dataobj.getString("nama_imunisasi"));
                                        childItemArrayList.add(modelChild);

                                        parentItemArrayList.add(new ModelImunisasi(childItemArrayList, id_imunisasi, tgl_imunisasi, berat_anak, tinggi_anak));
                                    }
                                }
                            } else {
                                if (id_imunisasi.trim().equals("")){
                                    id_imunisasi = dataobj.getString("id_imunisasi");
                                    tgl_imunisasi = dataobj.getString("tgl_imunisasi");
                                    berat_anak = dataobj.getString("berat_anak");
                                    tinggi_anak = dataobj.getString("tinggi_anak");

                                    ModelDetailImunisasi modelChild = new ModelDetailImunisasi(dataobj.getString("nama_imunisasi"));
                                    childItemArrayList.add(modelChild);
                                } else {
                                    if (id_imunisasi.trim().equals(dataobj.getString("id_imunisasi"))){
                                        ModelDetailImunisasi modelChild = new ModelDetailImunisasi(dataobj.getString("nama_imunisasi"));
                                        childItemArrayList.add(modelChild);
                                    } else {
                                        parentItemArrayList.add(new ModelImunisasi(childItemArrayList, id_imunisasi, tgl_imunisasi, berat_anak, tinggi_anak));
                                        childItemArrayList = new ArrayList<>();

                                        id_imunisasi = dataobj.getString("id_imunisasi");
                                        tgl_imunisasi = dataobj.getString("tgl_imunisasi");
                                        berat_anak = dataobj.getString("berat_anak");
                                        tinggi_anak = dataobj.getString("tinggi_anak");

                                        ModelDetailImunisasi modelChild = new ModelDetailImunisasi(dataobj.getString("nama_imunisasi"));
                                        childItemArrayList.add(modelChild);
                                    }
                                }
                            }
                        }
                        adapterImunisasi = new AdapterImunisasi(HistoryImunisasi.this, parentItemArrayList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HistoryImunisasi.this, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapterImunisasi);

                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    Toast.makeText(HistoryImunisasi.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryImunisasi.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (onActivity.equals("histo_dokter")) {
            Intent abc = new Intent(HistoryImunisasi.this, HomeDoctor.class);
            startActivity(abc);
            finish();
        } else if (onActivity.equals("histo_ortu")){
            Intent abc = new Intent(HistoryImunisasi.this, MainActivity.class);
            startActivity(abc);
            finish();
        }   else if (onActivity.equals("HistoImunSuster")){
            Intent abc = new Intent(HistoryImunisasi.this, HomeSuster.class);
            startActivity(abc);
            finish();
        }
    }
}