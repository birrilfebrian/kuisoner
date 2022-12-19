package com.mif.moms.model;

import com.mif.moms.configfile.ServerApi;

public class ModelListVaksinTemp {
    private String id_anak;
    private String id_user;
    private String jenis_imun;


    public String getId_anak() {
        return id_anak;
    }

    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }

    public String getid_user() {
        return id_user;
    }

    public void setid_user(String id_user) {
        this.id_user = id_user;
    }

    public String getjenis_imun() {
        return jenis_imun;
    }

    public void setjenis_imun(String jenis_imun) {
        this.jenis_imun = jenis_imun;
    }

}
