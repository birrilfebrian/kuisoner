package com.mif.moms.model;

import com.mif.moms.configfile.ServerApi;

import java.util.List;

public class ModelRiwayat {
    String id_hasil, id_anak, nama_anak, tanggal_lahir, foto_anak, text_tips;
    double riwayat;
    private boolean isExpandable;
    private List<ModelDetailRiwayat> nestedList;

    public ModelRiwayat(List<ModelDetailRiwayat> nestedList, String id_hasil, String id_anak, String nama_anak, String tanggal_lahir, String foto_anak, double riwayat, String text_tips) {
        this.nestedList = nestedList;
        this.id_hasil = id_hasil;
        this.id_anak = id_anak;
        this.nama_anak = nama_anak;
        this.tanggal_lahir = tanggal_lahir;
        this.foto_anak = foto_anak;
        this.riwayat = riwayat;
        this.text_tips = text_tips;
        isExpandable = false;
    }

    public String getId_anak() {
        return id_anak;
    }

    public List<ModelDetailRiwayat> getNestedList() {
        return nestedList;
    }

    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }

    public String getId_hasil() {
        return id_hasil;
    }

    public void setId_hasil(String id_hasil) {
        this.id_hasil = id_hasil;
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
        this.tanggal_lahir = "Tanggal Lahir = " + tanggal_lahir;
    }

    public void setText_tips(String text_tips) {
        this.text_tips = text_tips;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public String getFoto_anak() {
//        return foto_anak;
        return ServerApi.FotoAnak + foto_anak;
    }

    public void setFoto_anak(String foto_anak) {
        this.foto_anak = foto_anak;
    }

    public double getRiwayat() {
        return riwayat;
    }

    public void setRiwayat(double riwayat) {
        this.riwayat = riwayat;
    }

    public String getText_tips() {
        return text_tips;
    }
}
