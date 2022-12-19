package com.mif.moms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mif.moms.MainActivity;
import com.mif.moms.R;
import com.mif.moms.adapter.AdapterListAnak;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.configfile.authdata;
import com.mif.moms.doctor.HistoryImunisasi;
import com.mif.moms.model.ModelListAnak;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PilihAnakRiwayatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ModelListAnak> item;
    AdapterListAnak adapterListAnak;

    RequestQueue requestQueue;
    authdata authdataa;
    ProgressDialog progressDialog;

    String mIdUser, onActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_anak_riwayat);
        getSupportActionBar().hide();
        authdataa = new authdata(this);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        onActivity = intent.getStringExtra("onActivity");

        mIdUser = authdataa.getId_user();
        recyclerView = findViewById(R.id.recyclerPilihAnakRiwayat);

        loadAnak();
    }

    private void loadAnak(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_ANAK + "/listanak?id_user=" + mIdUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");

                    item = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++)
                    {
                        ModelListAnak modelListAnak = new ModelListAnak();
                        JSONObject datanya = data.getJSONObject(i);
                        modelListAnak.setId_anak(datanya.getString("id_anak"));
                        modelListAnak.setNama_anak(datanya.getString("nama_anak"));
                        modelListAnak.setTanggal_lahir(datanya.getString("tanggal_lahir"));
                        modelListAnak.setFoto_anak(datanya.getString("foto_anak"));
                        item.add(modelListAnak);
                    }
                    adapterListAnak = new AdapterListAnak(PilihAnakRiwayatActivity.this, item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PilihAnakRiwayatActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapterListAnak);

                    adapterListAnak.setListener(new AdapterListAnak.OnHistoryClickListener() {
                        @Override
                        public void onClick(int position) {
                            ModelListAnak modelListAnak = item.get(position);
                            if (onActivity.equals("histo_imun")) {
                                Intent detail = new Intent(PilihAnakRiwayatActivity.this, HistoryImunisasi.class);
                                detail.putExtra("onActivity", "histo_ortu");
                                detail.putExtra("id_anak", modelListAnak.getId_anak());
                                startActivity(detail);
                            } else {
                                Intent detail = new Intent(PilihAnakRiwayatActivity.this, RiwayatActivity.class);
                                detail.putExtra("id_anak", modelListAnak.getId_anak());
                                startActivity(detail);
                            }
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(PilihAnakRiwayatActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PilihAnakRiwayatActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent abc = new Intent(PilihAnakRiwayatActivity.this, MainActivity.class);
        startActivity(abc);
        finish();
    }
}