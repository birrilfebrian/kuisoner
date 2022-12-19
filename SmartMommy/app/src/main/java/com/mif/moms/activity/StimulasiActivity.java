package com.mif.moms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mif.moms.MainActivity;
import com.mif.moms.R;
import com.mif.moms.configfile.ServerApi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class StimulasiActivity extends AppCompatActivity {
    ImageView image1, image2, image3;

    TextView textBulan;

    String linkimage1, linkimage2, linkimage3, linkimage4, linkimage5, linkimage6, linkimage7, linkimage8,
            linkimage9, linkimage10, linkimage11, linkimage12, linkimage13, linkimage14, linkimage15, linkimage16, linkimage17;

    String id_anak, umurnya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stimulasi);
        Intent intent = getIntent();
        getSupportActionBar().hide();
        id_anak = intent.getStringExtra("id_anak");

        textBulan = findViewById(R.id.textbulanan);
        image1 = findViewById(R.id.imageStimulasi1);
        image2 = findViewById(R.id.imageStimulasi2);
        image3 = findViewById(R.id.imageStimulasi3);

        linkimage1 = ServerApi.FotoStimulasi + "1bln1stimulasi.PNG";
        linkimage2 = ServerApi.FotoStimulasi + "1bln2stimulasi.PNG";
        linkimage3 = ServerApi.FotoStimulasi + "2bln1stimulasi.PNG";
        linkimage4 = ServerApi.FotoStimulasi + "2bln2stimulasi.PNG";
        linkimage5 = ServerApi.FotoStimulasi + "3bln1stimulasi.PNG";
        linkimage6 = ServerApi.FotoStimulasi + "3bln2stimulasi.PNG";
        linkimage7 = ServerApi.FotoStimulasi + "4bln1stimulasi.PNG";
        linkimage8 = ServerApi.FotoStimulasi + "4bln2stimulasi.PNG";
        linkimage9 = ServerApi.FotoStimulasi + "5bln1stimulasi.PNG";
        linkimage10 = ServerApi.FotoStimulasi + "5bln2stimulasi.PNG";
        linkimage11 = ServerApi.FotoStimulasi + "6bln1stimulasi.PNG";
        linkimage12 = ServerApi.FotoStimulasi + "6bln2stimulasi.PNG";
        linkimage13 = ServerApi.FotoStimulasi + "7bln1stimulasi.PNG";
        linkimage14 = ServerApi.FotoStimulasi + "7bln2stimulasi.PNG";
        linkimage15 = ServerApi.FotoStimulasi + "8bln1stimulasi.PNG";
        linkimage16 = ServerApi.FotoStimulasi + "8bln2stimulasi.PNG";
        linkimage17 = ServerApi.FotoStimulasi + "8bln3stimulasi.PNG";

        loadStimulasi();
    }

    public void loadStimulasi()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_ANAK + "/liststimulasi?id_anak=" + id_anak, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("data");
                    umurnya = data;
                    textBulan.setText("Stimulasi dan Tahapan Perkembangan " + umurnya + " bulan");
                    if (umurnya.toString().trim().matches("0 - 3")){
                        Picasso.get().load(linkimage1).into(image1);
                        Picasso.get().load(linkimage2).into(image2);
                    } else if (umurnya.toString().trim().matches("3 - 6")){
                        Picasso.get().load(linkimage3).into(image1);
                        Picasso.get().load(linkimage4).into(image2);
                    } else if (umurnya.toString().trim().matches("6 - 9")){
                        Picasso.get().load(linkimage5).into(image1);
                        Picasso.get().load(linkimage6).into(image2);
                    } else if (umurnya.toString().trim().matches("9 - 12")){
                        Picasso.get().load(linkimage7).into(image1);
                        Picasso.get().load(linkimage8).into(image2);
                    } else if (umurnya.toString().trim().matches("12 - 18")){
                        Picasso.get().load(linkimage9).into(image1);
                        Picasso.get().load(linkimage10).into(image2);
                    } else if (umurnya.toString().trim().matches("18 - 24")){
                        Picasso.get().load(linkimage11).into(image1);
                        Picasso.get().load(linkimage12).into(image2);
                    } else if (umurnya.toString().trim().matches("24 - 36")){
                        Picasso.get().load(linkimage13).into(image1);
                        Picasso.get().load(linkimage14).into(image2);
                    } else if (umurnya.toString().trim().matches("36 - 48")){
                        Picasso.get().load(linkimage15).into(image1);
                        Picasso.get().load(linkimage16).into(image2);
                        Picasso.get().load(linkimage17).into(image3);
                    }
                } catch (JSONException e) {
                    Toast.makeText(StimulasiActivity.this, "Stimulasi tidak ada, umur tidak sesuai!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StimulasiActivity.this, MainActivity.class));
                    StimulasiActivity.this.finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StimulasiActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(StimulasiActivity.this);
        requestQueue.add(stringRequest);
    }
}