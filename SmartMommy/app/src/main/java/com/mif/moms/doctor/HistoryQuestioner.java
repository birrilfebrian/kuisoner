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
import com.mif.moms.activity.RiwayatActivity;
import com.mif.moms.adapter.AdapterListRiwayat;
import com.mif.moms.adapter.AdapterListRiwayatDokter;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.model.ModelDetailRiwayat;
import com.mif.moms.model.ModelRiwayat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryQuestioner extends AppCompatActivity {
    SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;
    ArrayList<ModelRiwayat> parentItemArrayList;
    AdapterListRiwayatDokter adapterListRiwayat;
    List<ModelDetailRiwayat> childItemArrayList;

    JSONArray dataArr;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String mIdAnak, id_hasil = "", tipsDokter;
    double totPoint = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_questioner);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        mIdAnak = intent.getStringExtra("id_anak");

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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_DOKTER + "/riwayatAnak?id_anak=" + mIdAnak, new Response.Listener<String>() {
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
                                if (id_hasil.trim().equals("")) {
                                    id_hasil = dataobj.getString("id_hasil");
                                    totPoint = dataobj.getDouble("total_point");
                                    tipsDokter = dataobj.getString("tips_dokter");
                                    ModelDetailRiwayat modelChild = new ModelDetailRiwayat(dataobj.getString("soal"), dataobj.getString("jawaban"));
                                    childItemArrayList.add(modelChild);

                                    parentItemArrayList.add(new ModelRiwayat(childItemArrayList, id_hasil, dataobj.getString("id_anak"), dataobj.getString("nama_anak"), dataobj.getString("tanggal_lahir"), dataobj.getString("foto_anak"), totPoint, tipsDokter));
                                } else  {
                                    if (id_hasil.trim().equals(dataobj.getString("id_hasil"))){
                                        ModelDetailRiwayat modelChild = new ModelDetailRiwayat(dataobj.getString("soal"), dataobj.getString("jawaban"));
                                        childItemArrayList.add(modelChild);

                                        parentItemArrayList.add(new ModelRiwayat(childItemArrayList, id_hasil, dataobj.getString("id_anak"), dataobj.getString("nama_anak"), dataobj.getString("tanggal_lahir"), dataobj.getString("foto_anak"), totPoint, tipsDokter));
                                    } else {
                                        parentItemArrayList.add(new ModelRiwayat(childItemArrayList, id_hasil, dataobj.getString("id_anak"), dataobj.getString("nama_anak"), dataobj.getString("tanggal_lahir"), dataobj.getString("foto_anak"), totPoint, tipsDokter));
                                        childItemArrayList = new ArrayList<>();

                                        id_hasil = dataobj.getString("id_hasil");
                                        totPoint = dataobj.getDouble("total_point");
                                        tipsDokter = dataobj.getString("tips_dokter");
                                        ModelDetailRiwayat modelChild = new ModelDetailRiwayat(dataobj.getString("soal"), dataobj.getString("jawaban"));
                                        childItemArrayList.add(modelChild);

                                        parentItemArrayList.add(new ModelRiwayat(childItemArrayList, id_hasil, dataobj.getString("id_anak"), dataobj.getString("nama_anak"), dataobj.getString("tanggal_lahir"), dataobj.getString("foto_anak"), totPoint, tipsDokter));
                                    }
                                }
                            } else {
                                if (id_hasil.trim().equals("")){
                                    id_hasil = dataobj.getString("id_hasil");
                                    totPoint = dataobj.getDouble("total_point");
                                    tipsDokter = dataobj.getString("tips_dokter");

                                    ModelDetailRiwayat modelChild = new ModelDetailRiwayat(dataobj.getString("soal"), dataobj.getString("jawaban"));
                                    childItemArrayList.add(modelChild);
                                } else {
                                    if (id_hasil.trim().equals(dataobj.getString("id_hasil"))){
                                        ModelDetailRiwayat modelChild = new ModelDetailRiwayat(dataobj.getString("soal"), dataobj.getString("jawaban"));
                                        childItemArrayList.add(modelChild);
                                    } else {
                                        parentItemArrayList.add(new ModelRiwayat(childItemArrayList, id_hasil, dataobj.getString("id_anak"), dataobj.getString("nama_anak"), dataobj.getString("tanggal_lahir"), dataobj.getString("foto_anak"), totPoint, tipsDokter));
                                        childItemArrayList = new ArrayList<>();

                                        id_hasil = dataobj.getString("id_hasil");
                                        totPoint = dataobj.getDouble("total_point");
                                        tipsDokter = dataobj.getString("tips_dokter");

                                        ModelDetailRiwayat modelChild = new ModelDetailRiwayat(dataobj.getString("soal"), dataobj.getString("jawaban"));
                                        childItemArrayList.add(modelChild);
                                    }
                                }
                            }
                        }
                        adapterListRiwayat = new AdapterListRiwayatDokter(HistoryQuestioner.this, parentItemArrayList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HistoryQuestioner.this, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapterListRiwayat);

                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                    }

//                    item = new ArrayList<>();
//                    for (int i = 0; i < data.length(); i++)
//                    {
//                        ModelRiwayat modelRiwayat = new ModelRiwayat();
//                        JSONObject datanya = data.getJSONObject(i);
//                        modelRiwayat.setId_anak(datanya.getString("id_anak"));
//                        modelRiwayat.setNama_anak(datanya.getString("nama_anak"));
//                        modelRiwayat.setTanggal_lahir(datanya.getString("tanggal_lahir"));
//                        modelRiwayat.setRiwayat(datanya.getDouble("total_point"));
//                        modelRiwayat.setFoto_anak(datanya.getString("foto_anak"));
//                        item.add(modelRiwayat);
//                    }
//                    adapterListRiwayat = new AdapterListRiwayat(RiwayatActivity.this, item);
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RiwayatActivity.this);
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setAdapter(adapterListRiwayat);
                } catch (JSONException e) {
                    Toast.makeText(HistoryQuestioner.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryQuestioner.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent abc = new Intent(HistoryQuestioner.this, HomeDoctor.class);
        startActivity(abc);
        finish();
    }
}