package com.mif.moms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mif.moms.MainActivity;
import com.mif.moms.R;
import com.mif.moms.VolleyMultipartRequest;
import com.mif.moms.configfile.ServerApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import pl.aprilapps.easyphotopicker.EasyImage;

public class DetailAnakActivity extends AppCompatActivity {
    TextView nama, tgllahir, jenisklmn, umur;
    ImageView imageView;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String id_anak, linkfoto;

    Bitmap bitmap;
    private int CAMERA_REQUEST = 1888;
    private int GALLERY_REQUEST = 1999;
    final CharSequence[] dialogItems = {"Kamera", "Galeri", "Batal"};
    public static final int REQUEST_CODE_CAMERA = 001;
    public static final int REQUEST_CODE_GALLERY = 002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anak);
        init();
        getSupportActionBar().hide();

        Intent intent = getIntent();
        id_anak = intent.getStringExtra("id_anak");
        Log.e("idnak", id_anak);
        loadAnak();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
                setRequestImage();
            }
        });
    }

    private void init(){
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        nama = findViewById(R.id.text_nama_detail);
        tgllahir = findViewById(R.id.text_tgllahir_detail);
        jenisklmn = findViewById(R.id.text_jeniskelamin_detail);
        umur = findViewById(R.id.text_umur_detail);
        imageView = findViewById(R.id.image_detail_foto);
    }

    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            }
            return false;
        } else {
            return true;
        }
    }

    private void setRequestImage() {
        CharSequence[] item = {"Kamera", "Galeri"};
        AlertDialog.Builder request = new AlertDialog.Builder(this)
                .setTitle("Pilih Foto Anak Anda")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                //Membuka Kamera Untuk Mengambil Gambar
                                EasyImage.openCamera(DetailAnakActivity.this, REQUEST_CODE_CAMERA);
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                EasyImage.openGallery(DetailAnakActivity.this, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        request.create();
        request.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Method Ini Digunakan Untuk Menghandle Error pada Image
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                //Method Ini Digunakan Untuk Menghandle Image
                switch (type) {
                    case REQUEST_CODE_CAMERA:
                        Glide.with(DetailAnakActivity.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                        bitmap = BitmapFactory.decodeFile(imageFile.getPath());
                        break;

                    case REQUEST_CODE_GALLERY:
                        Glide.with(DetailAnakActivity.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                        bitmap = BitmapFactory.decodeFile(imageFile.getPath());
                        break;
                }
                uploadFoto();
//                if (bitmap != null) {
//                    btn_pilih_foto_profil.setVisibility(View.GONE);
//                    btn_edit_foto_profil.setVisibility(View.VISIBLE);
//                    btn_simpan.setVisibility(View.VISIBLE);
//                    btn_simpan_disable.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Batalkan penanganan, Anda mungkin ingin menghapus foto yang diambil jika dibatalkan

            }
        });
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void uploadFoto() {
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ServerApi.URL_UPLOADFOTOANAK, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                try {
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    JSONObject res = new JSONObject(json);
                    Log.e("response", "qq" + res);
                    Toast.makeText(DetailAnakActivity.this, "Berhasil Upload Foto", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(DetailAnakActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(UploadFotoDaftarActivity.this, IlustrasiSuksesActivity.class);
//                    startActivity(intent);
//                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DetailAnakActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(DetailAnakActivity.this, "Anda belum memilih Foto!", Toast.LENGTH_SHORT).show();
                Log.e("err: ", "" + error);
//                Intent intent = new Intent(UploadFotoDaftarActivity.this, IlustrasiSuksesActivity.class);
//                startActivity(intent);
//                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_anak", id_anak);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("foto", new DataPart(imagename + ".jpeg", getFileDataFromDrawable(bitmap)));
                Log.e("foto: ", "" + imagename);
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(volleyMultipartRequest);
    }

    public void loadAnak(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_ANAK + "/detailanak?id_anak=" + id_anak, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    JSONObject datanya = data.getJSONObject(0);
                    nama.setText(":  " + datanya.getString("nama_anak"));
                    tgllahir.setText(":  " + datanya.getString("tanggal_lahir"));
                    jenisklmn.setText(":  " + datanya.getString("jenis_kelamin"));
                    umur.setText(":  " + datanya.getString("umur"));
                    linkfoto = ServerApi.FotoAnak + datanya.getString("foto_anak");
                    Picasso.get().load(linkfoto).into(imageView);
//                    foto = ServerApi.FotoUser + authdataa.getFoto();
//                    Picasso.get().load(foto).into(circleImageView);
                } catch (JSONException e){
                    Toast.makeText(DetailAnakActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DetailAnakActivity.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent abc = new Intent(DetailAnakActivity.this, MainActivity.class);
        startActivity(abc);
        finish();
    }
}