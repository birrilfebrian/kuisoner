package com.mif.moms.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
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
import com.mif.moms.ImageResizer;
import com.mif.moms.MainActivity;
import com.mif.moms.R;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.configfile.authdata;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfilActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    String foto, id_user;
    EditText nama, username, email;
    MaterialButton buttonsimpan;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    authdata authdataa;

    Bitmap bitmap;
    private final int IMG_REQUEST = 1;
    boolean statusImage = false, doubleBackToExit;
    private int CAMERA_REQUEST = 1888;
    private int GALLERY_REQUEST = 1999;
    final CharSequence[] dialogItems = {"Kamera", "Galeri", "Batal"};
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        getSupportActionBar().hide();

        authdataa = new authdata(this);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        circleImageView = findViewById(R.id.foto_editakun);
        nama = findViewById(R.id.nama_editakun);
        username = findViewById(R.id.username_editakun);
        email = findViewById(R.id.email_editakun);
        buttonsimpan = findViewById(R.id.btn_editakun);

        id_user = authdataa.getId_user();
        nama.setText(authdataa.getNama());
        email.setText(authdataa.getEmail());
        username.setText(authdataa.getUsername());
        foto = ServerApi.FotoUser + authdataa.getFoto();
        Picasso.get().load(foto).into(circleImageView);

        username.setEnabled(false);
        email.setEnabled(false);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        buttonsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama.getText().toString().matches("")){
                    Toast.makeText(EditProfilActivity.this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    edit();
                    Toast.makeText(EditProfilActivity.this, "Berhasil edit akun", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(EditProfilActivity.this, MainActivity.class);
                    startActivity(a);
                    finish();
                }
            }
        });
    }

    public void edit(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, ServerApi.URL_EDITAKUN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    JSONObject update = jsonObject.getJSONObject("update");

                    authdataa.setNamaPengguna(update.getString("nama"));
                    authdataa.setFoto(update.getString("foto"));
                } catch (JSONException e){
                    Toast.makeText(EditProfilActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditProfilActivity.this, "Gagal edit akun", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                params.put("nama", nama.getText().toString().trim());
                params.put("foto", imageToString(bitmap));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
            Intent abc = new Intent(EditProfilActivity.this, MainActivity.class);
            startActivity(abc);
            finish();
    }

    private void selectImage() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(EditProfilActivity.this);
        dialog.setTitle("Masukkan Foto");
        dialog.setItems(dialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // select by camera
                if (dialogItems[which].equals("Kamera")) {
                    if (ContextCompat.checkSelfPermission(EditProfilActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfilActivity.this,
                                new String[]{
                                        Manifest.permission.CAMERA
                                }, REQUEST_CAMERA);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }

                //select by gallery
                if (dialogItems[which].equals("Galeri")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, GALLERY_REQUEST);
                }

                if (dialogItems[which].equals("Batal")) {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            bitmap = ImageResizer.reduceBitmapSize(photo, 240000);

            circleImageView.setImageBitmap(bitmap);
            Toast.makeText(this, "Berhasil mengambil foto", Toast.LENGTH_SHORT).show();
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(path);
                Bitmap photo = BitmapFactory.decodeStream(inputStream);
                bitmap = ImageResizer.reduceBitmapSize(photo, 240000);

                circleImageView.setImageBitmap(bitmap);
                Toast.makeText(this, "Berhasil mengambil foto", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Harap Pilih foto", Toast.LENGTH_SHORT).show();
        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}