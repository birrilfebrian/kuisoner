package com.mif.moms.model;

import java.util.List;

public class ModelImunisasi {
    String id_imunisasi, tglImunisasi, beratAnak, tinggiAnak;
    private boolean isExpandable;
    private List<ModelDetailImunisasi> nestedList;

    public ModelImunisasi(List<ModelDetailImunisasi> nestedList, String id_imunisasi, String tglImunisasi, String beratAnak, String tinggiAnak){
        this.nestedList = nestedList;
        this.id_imunisasi = id_imunisasi;
        this.tglImunisasi = tglImunisasi;
        this.beratAnak = beratAnak;
        this.tinggiAnak = tinggiAnak;
        isExpandable = false;
    }

    public List<ModelDetailImunisasi> getNestedList() {
        return nestedList;
    }

    public String getBeratAnak() {
        return beratAnak;
    }

    public String getId_imunisasi() {
        return id_imunisasi;
    }

    public String getTglImunisasi() {
        return tglImunisasi;
    }

    public String getTinggiAnak() {
        return tinggiAnak;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setBeratAnak(String beratAnak) {
        this.beratAnak = beratAnak;
    }

    public void setId_imunisasi(String id_imunisasi) {
        this.id_imunisasi = id_imunisasi;
    }

    public void setTglImunisasi(String tglImunisasi) {
        this.tglImunisasi = tglImunisasi;
    }

    public void setTinggiAnak(String tinggiAnak) {
        this.tinggiAnak = tinggiAnak;
    }
}
