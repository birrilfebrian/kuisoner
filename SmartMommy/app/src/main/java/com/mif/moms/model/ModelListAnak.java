package com.mif.moms.model;

import com.mif.moms.configfile.ServerApi;

public class ModelListAnak {
    private String id_anak;
    private String nama_anak;
    private String tanggal_lahir;
    private String foto_anak;


    public String getId_anak() {
        return id_anak;
    }

    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }

    public String getNama_anak() {
        return nama_anak;
    }

    public void setNama_anak(String nama_anak) {
        this.nama_anak = nama_anak;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = "Birth Date = " + tanggal_lahir;
    }

    public String getFoto_anak() {
//        return foto_anak;
        return ServerApi.FotoAnak + foto_anak;
    }

    public void setFoto_anak(String foto_anak) {
        this.foto_anak = foto_anak;
    }
}
