package com.mif.moms.suster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mif.moms.R;
import com.mif.moms.adapter.AdapterTempImunisasi;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.configfile.authdata;
import com.mif.moms.doctor.HistoryQuestioner;
import com.mif.moms.doctor.HomeDoctor;
import com.mif.moms.doctor.ListAnakDoctor;
import com.mif.moms.model.ModelListVaksinTemp;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputVaccine extends AppCompatActivity {

    authdata authdataa;
    ImageView imgBack, imgAnak, imgAddVaccine, imgRefresh;
    String iStrIDAnak, iStrNamaAnak, iStrTglLahir, iStrFotoAnak, iStrUserId;
    TextView tvBabyName, tvBabySex, tvBabyBirth;
    RecyclerView rvImun;
    SwipeRefreshLayout refreshListVaccine;
    JSONArray data;

    TextInputEditText edtInsertWeight, edtInsertHeight;

    AdapterTempImunisasi adapterBluebin;
    List<ModelListVaksinTemp> modelBinList;
    
    FloatingActionButton fab_save;

    String replaceWeight, replaceHeight;
    ProgressDialog progressDialog;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_vaccine);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeStatusBarColor();
        } else getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        init();
        getIntentData();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToActivity();
            }
        });

        imgAddVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialogInsert = new Intent(InputVaccine.this, InsertVaccine.class);
                dialogInsert.putExtra("onActivity", "InputVaccine");
                dialogInsert.putExtra("id_anak", iStrIDAnak);
                startActivity(dialogInsert);
            }
        });

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseTempImunisasi();
            }
        });

        refreshListVaccine.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                releaseTempImunisasi();
                refreshListVaccine.setRefreshing(false);
            }
        });
        
        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveImunisasi();
            }
        });

    }

    private void saveImunisasi() {
        final String rWeight = edtInsertWeight.getText().toString();
        replaceWeight =  rWeight.replace(",", ".");
        edtInsertWeight.setText(replaceWeight);

        final String rHeight = edtInsertHeight.getText().toString();
        replaceHeight =  rHeight.replace(",", ".");
        edtInsertHeight.setText(replaceHeight);

        //progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_SUSTER + "/tambahImunisasi", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("true")) {
                        Toast.makeText(InputVaccine.this, "Imunisasi Berhasil di Simpan", Toast.LENGTH_SHORT).show();
                        Intent abc = new Intent(InputVaccine.this, HomeSuster.class);
                        startActivity(abc);
                        finish();
                    } else {
                        Toast.makeText(InputVaccine.this, "Imunisasi Gagal di Simpan", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(InputVaccine.this, "Imunisasi Gagal di Simpan", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();

                String errMessage = "Terjadi Kesalahan.\nSilahkan mencoba kembali setelah beberapa saat.";
                if (error instanceof NetworkError){
                    errMessage = "Tidak dapat terhubung ke internet.\nSilahkan periksa jaringan anda.";
                } else if (error instanceof AuthFailureError) {
                    errMessage = "Pengiriman data gagal.\nSilahkan periksa kembali data anda.";
                } else if (error instanceof ClientError) {
                    errMessage = "Pengiriman data gagal.\nSilahkan periksa kembali data anda.";
                } else if (error instanceof TimeoutError) {
                    errMessage = "Waktu koneksi terputus.\nSilahkan periksa jaringan anda.";
                }

                Toast.makeText(InputVaccine.this, errMessage, Toast.LENGTH_SHORT).show();
                Log.e("e: ", "" + error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_anak", iStrIDAnak);
                params.put("id_user", iStrUserId);
                params.put("berat_anak", edtInsertWeight.getText().toString().trim());
                params.put("tinggi_anak", edtInsertHeight.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(InputVaccine.this);
        requestQueue.add(stringRequest);
    }


    private void init() {
        authdataa = new authdata(this);
        iStrUserId = authdataa.getId_user();
        imgBack = findViewById(R.id.imgBack);
        imgAnak = findViewById(R.id.imgAnak);
        imgAddVaccine = findViewById(R.id.imgAddVaccine);
        imgRefresh = findViewById(R.id.imgRefresh);
        tvBabyName = findViewById(R.id.tvBabyName);
        tvBabySex = findViewById(R.id.tvBabySex);
        tvBabyBirth = findViewById(R.id.tvBabyBirth);
        rvImun = findViewById(R.id.rvImun);
        refreshListVaccine = findViewById(R.id.refreshListVaccine);
        edtInsertWeight = findViewById(R.id.edtInsertWeight);
        edtInsertHeight = findViewById(R.id.edtInsertHeight);
        fab_save = findViewById(R.id.fab_save);

        rvImun.setHasFixedSize(true);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        iStrIDAnak = intent.getStringExtra("id_anak");
        iStrNamaAnak = intent.getStringExtra("nama_anak");
        iStrTglLahir = intent.getStringExtra("tanggal_lahir");
        iStrFotoAnak = intent.getStringExtra("foto_anak");


        Picasso.get().load(iStrFotoAnak).into(imgAnak);
        tvBabyName.setText(iStrNamaAnak);
        tvBabyBirth.setText(iStrTglLahir);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.accstop));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        BackToActivity();

    }

    private void BackToActivity() {

        Intent intentSendBack = new Intent(InputVaccine.this, HomeSuster.class);
        startActivity(intentSendBack);
        finish();

    }

    private void releaseTempImunisasi() {

        StringRequest stringRequest  = new StringRequest(Request.Method.GET, ServerApi.URL_SUSTER + "/getTemp?id_user="+iStrUserId+"&id_anak="+iStrIDAnak, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("res:BrowseTemp", ">>" + response);
                try {
                    JSONObject obj  = new JSONObject(response);
                    data = obj.getJSONArray("data");

                    modelBinList = new ArrayList<>();

                    Log.d("volley", "Record Count : " + data.length());

                    for (int i = 0; i < data.length(); i++)
                    {
                        ModelListVaksinTemp model = new ModelListVaksinTemp();
                        JSONObject dataobj = data.getJSONObject(i);
                        model.setjenis_imun(dataobj.getString("nama_imunisasi"));
                        model.setid_user(dataobj.getString("id_user"));
                        model.setId_anak(dataobj.getString("id_anak"));

                        modelBinList.add(model);
                    }
                    adapterBluebin = new AdapterTempImunisasi(InputVaccine.this, modelBinList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(InputVaccine.this);
                    rvImun.setLayoutManager(layoutManager);
                    rvImun.setAdapter(adapterBluebin);


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "err:BrowseBin >> " + error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}