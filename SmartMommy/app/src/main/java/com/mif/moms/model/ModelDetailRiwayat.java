package com.mif.moms.model;

public class ModelDetailRiwayat {
    String mSoal, mJawaban;

    public ModelDetailRiwayat(String mSoal, String mJawaban){
        this.mSoal = mSoal;
        this.mJawaban = mJawaban;
    }

    public void setmJawaban(String mJawaban) {
        this.mJawaban = mJawaban;
    }

    public void setmSoal(String mSoal) {
        this.mSoal = mSoal;
    }

    public String getmJawaban() {
        return mJawaban;
    }

    public String getmSoal() {
        return mSoal;
    }
}
