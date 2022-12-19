package com.mif.moms.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mif.moms.R;
import com.mif.moms.configfile.authdata;

public class HomeDoctor extends AppCompatActivity {
    authdata authdataa;
    ImageView imgLogout;
    TextView namadoctor;
    ImageView cvHistoryQuestionnaire, cvHistoryImmun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);
        getSupportActionBar().hide();
        init();

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authdataa.logout();
            }
        });

        cvHistoryImmun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anak = new Intent(HomeDoctor.this, ListAnakDoctor.class);
                anak.putExtra("onActivity", "HistoImun");
                startActivity(anak);
                finish();
            }
        });

        cvHistoryQuestionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anak = new Intent(HomeDoctor.this, ListAnakDoctor.class);
                anak.putExtra("onActivity", "HistoKuis");
                startActivity(anak);
                finish();
            }
        });
    }

    private void init(){
        authdataa = new authdata(this);
        namadoctor = findViewById(R.id.namadoctor);
        imgLogout = findViewById(R.id.imgLogout);
        cvHistoryImmun = findViewById(R.id.cvHistoryImmun);
        cvHistoryQuestionnaire = findViewById(R.id.cvHistoryQuestionnaire);

        namadoctor.setText(authdataa.getNama());
    }
}