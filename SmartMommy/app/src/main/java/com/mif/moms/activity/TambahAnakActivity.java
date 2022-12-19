package com.mif.moms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TambahAnakActivity extends AppCompatActivity {
    EditText nama, tgllhr, jnsklm;
    MaterialButton simpan;

    ProgressDialog progressDialog;
    authdata authdataa;
    RequestQueue requestQueue;

    String iduser;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_anak);
        getSupportActionBar().hide();
        authdataa = new authdata(this);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        iduser = authdataa.getId_user();
        nama = findViewById(R.id.edt_nama_tambahanak);
        tgllhr = findViewById(R.id.edt_tgllahir_anak);
        jnsklm = findViewById(R.id.edt_jeniskelamin_anak);
        simpan = findViewById(R.id.btn_simpan_tambahanak);

        jnsklm.setInputType(InputType.TYPE_NULL);
        jnsklm.setFocusable(false);
        jnsklm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahAnakActivity.this);
                builder.setTitle("Pilih Jenis Kelamin");

                // buat array list
                final String[] options = {"Laki-laki", "Perempuan"};

                //Pass array list di Alert dialog
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jnsklm.setText(options[which]);
                    }
                });
                // buat dan tampilkan alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = tahun + "-" + bulan + "-" + hari;
                tgllhr.setText(text);
            }
        };
        tgllhr.setInputType(InputType.TYPE_NULL);
        tgllhr.setFocusable(false);
        tgllhr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TambahAnakActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama.getText().toString().trim().matches("")){
                    Toast.makeText(TambahAnakActivity.this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tgllhr.getText().toString().trim().matches("")){
                    Toast.makeText(TambahAnakActivity.this, "Tanggal lahir tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (jnsklm.getText().toString().trim().matches("")){
                    Toast.makeText(TambahAnakActivity.this, "Jenis kelamin tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }

                tambahanak();
            }
        });
    }

    public void tambahanak(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_ANAK + "/tambahanak", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");

                    Intent done = new Intent(TambahAnakActivity.this, MainActivity.class);
                    Toast.makeText(TambahAnakActivity.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(done);
                    finish();
                } catch (JSONException e){
                    Toast.makeText(TambahAnakActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(TambahAnakActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(TambahAnakActivity.this, "Anak berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", iduser);
                params.put("nama_anak", nama.getText().toString().trim());
                params.put("tanggal_lahir", tgllhr.getText().toString().trim());
                params.put("jenis_kelamin", jnsklm.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent abc = new Intent(TambahAnakActivity.this, MainActivity.class);
        startActivity(abc);
        finish();
    }
}