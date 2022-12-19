package com.mif.moms.configfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.mif.moms.MainActivity;
import com.mif.moms.auth.LoginActivity;
import com.mif.moms.doctor.HomeDoctor;
import com.mif.moms.suster.HomeSuster;

public class authdata {
    //    private static authdata mInstance;
    public SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public Context mCtx;

    public static final String SHARED_PREF_NAME = "SmartMommy";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    private static final String id_user = "id_user";
    private static final String username = "username";
    private static final String nama = "nama";
    private static final String email = "email";
    private static final String foto = "foto";
    private static final String statusLogin = "status";


    public authdata(Context context) {
        this.mCtx = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setdatauser(String xid_user, String xusername, String xnama, String xemail, String xfoto, String xstatus) {

        editor.putBoolean(LOGIN_STATUS, true);
        editor.putString(id_user, xid_user);
        editor.putString(username, xusername);
        editor.putString(nama, xnama);
        editor.putString(email, xemail);
        editor.putString(foto, xfoto);
        editor.putString(statusLogin, xstatus);
        editor.apply();
    }


    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void logout() {
        if (getStatusLogin().equals("Orang Tua")){
            editor.clear();
            editor.commit();
            Intent login = new Intent(mCtx, LoginActivity.class);
            mCtx.startActivity(login);
            Toast.makeText(mCtx, "Anda berhasil Keluar.", Toast.LENGTH_SHORT).show();
            ((MainActivity) mCtx).finish();
        } else if (getStatusLogin().equals("Dokter")) {
            editor.clear();
            editor.commit();
            Intent login = new Intent(mCtx, LoginActivity.class);
            mCtx.startActivity(login);
            Toast.makeText(mCtx, "Anda berhasil Keluar.", Toast.LENGTH_SHORT).show();
            ((HomeDoctor) mCtx).finish();
        } else if (getStatusLogin().equals("Suster")) {
            editor.clear();
            editor.commit();
            Intent login = new Intent(mCtx, LoginActivity.class);
            mCtx.startActivity(login);
            Toast.makeText(mCtx, "Anda berhasil Keluar.", Toast.LENGTH_SHORT).show();
            ((HomeSuster) mCtx).finish();
        }
//        ((MainActivity) mCtx).finish();
    }

    public String getId_user() {
        return sharedPreferences.getString(id_user, null);
    }

    public String getNama() {
        return sharedPreferences.getString(nama, null);
    }

    public String getUsername() {
        return sharedPreferences.getString(username, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(email, null);
    }

    public String getFoto() {
        return sharedPreferences.getString(foto, null);
    }

    public String getStatusLogin(){
        return sharedPreferences.getString(statusLogin, null);
    }

    public void setNamaPengguna(String namaPengguna) {
        editor.putString(nama, namaPengguna);
        editor.apply();
    }

    public void setFoto(String fotoPengguna) {
        editor.putString(foto, fotoPengguna);
        editor.apply();
    }

}
