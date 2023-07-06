package com.finalproject.seniordesignproject;

import android.graphics.Bitmap;

public class CatDetails {
    private String catName,catIrk,catRenk,catSehir,catAciklama;
    private Bitmap catResim;

    public CatDetails(String catName, String catIrk, String catRenk, Bitmap catResim,String catSehir,String catAciklama) {
        this.catName = catName;
        this.catIrk = catIrk;
        this.catRenk = catRenk;
        this.catResim = catResim;
        this.catSehir = catSehir;
        this.catAciklama = catAciklama;
    }

    public String getCatName() {
        return catName;
    }

    public String getCatIrk() {
        return catIrk;
    }

    public String getCatRenk() {
        return catRenk;
    }

    public Bitmap getCatResim() {
        return catResim;
    }

    public String getCatSehir() {
        return catSehir;
    }

    public String getCatAciklama() {
        return catAciklama;
    }
}
