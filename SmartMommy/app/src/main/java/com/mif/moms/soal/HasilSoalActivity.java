package com.mif.moms.soal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

public class HasilSoalActivity extends AppCompatActivity {
    TextView texthasil, textdeskripsi;
    MaterialButton btnkembali;

    String id_hasil_intent;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_soal);
        getSupportActionBar().hide();
        texthasil = findViewById(R.id.textHasilSoal);
        textdeskripsi = findViewById(R.id.textDeskripsihasil);
        btnkembali = findViewById(R.id.btnbackhome);
        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        id_hasil_intent = intent.getStringExtra("id_hasil");

        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(HasilSoalActivity.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });

        loadHasil();
    }

    public void loadHasil()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_PAKET + "/hasil?id_hasil=" + id_hasil_intent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String pesan = jsonObject.getString("pesan");
                    if (pesan.equals("Anak Sesuai Dengan Tahapan")){
                        texthasil.setText(pesan);
                        textdeskripsi.setText("Selamat perkembangan anak sesuai dengan tehapan usianya, silahkan lakukan penilaan kembali pada tahapan usia selanjutnya");
                    } else if (pesan.equals("Anak Penyimpangan Meragukan")){
                        texthasil.setText(pesan);
                        textdeskripsi.setText("perkembangan anak masuk kategoti meragukan silahkan lakukan stimulasi berikut ini selama 2 minggu kemudian lakukan penilaian ulang sesuai tahapan usia");
                    } else if (pesan.equals("Kemungkinan Penyimpangan")){
                        texthasil.setText(pesan);
                        textdeskripsi.setText("perkembangan anak masuk kategori menyimpang, silahkan kunjungi pelanyanan kesehatan dan laukuakn stimulasi \n" + "berikut ini sesering mungkin lakukan penilaian ulang setiap 2 minggu");
                    }
                } catch (JSONException e){
                    Toast.makeText(HasilSoalActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HasilSoalActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent abc = new Intent(HasilSoalActivity.this, MainActivity.class);
        startActivity(abc);
        finish();
    }
}