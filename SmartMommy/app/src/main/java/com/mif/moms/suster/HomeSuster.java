package com.mif.moms.suster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.mif.moms.R;
import com.mif.moms.configfile.authdata;
import com.mif.moms.doctor.HomeDoctor;
import com.mif.moms.doctor.ListAnakDoctor;

public class HomeSuster extends AppCompatActivity {

    authdata authdataa;
    ImageView imgSignOut, imgMenuVaccine, imgHistoVaccine;
    TextView tvUserName;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_suster);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeStatusBarColor();
        } else getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        init();
        
        imgMenuVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anak = new Intent(HomeSuster.this, ListAnakDoctor.class);
                anak.putExtra("onActivity", "Suster");
                startActivity(anak);
                finish();
            }
        });
        
        imgHistoVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anak = new Intent(HomeSuster.this, ListAnakDoctor.class);
                anak.putExtra("onActivity", "HistoImunSuster");
                startActivity(anak);
                finish();
            }
        });

        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authdataa.logout();
            }
        });

    }

    private void init() {
        authdataa = new authdata(this);
        imgSignOut = findViewById(R.id.imgSignOut);
        imgMenuVaccine = findViewById(R.id.imgMenuVaccine);
        imgHistoVaccine = findViewById(R.id.imgHistoVaccine);
        tvUserName = findViewById(R.id.tvUserName);

        tvUserName.setText(authdataa.getUsername());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.accstop));
    }
}