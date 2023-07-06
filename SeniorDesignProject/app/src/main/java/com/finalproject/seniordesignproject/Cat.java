package com.finalproject.seniordesignproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Cat {
    private String kediİsmi,kediIrki,kediRengi,kediSehir,kediAciklama;
    private Bitmap kediResim;


    public Cat(){}

    public Cat(String kediİsmi, String kediIrki, String kediRengi, Bitmap kediResim,String kediSehir,String kediAciklama) {
        this.kediİsmi = kediİsmi;
        this.kediIrki = kediIrki;
        this.kediRengi = kediRengi;
        this.kediResim = kediResim;
        this.kediSehir = kediSehir;
        this.kediAciklama = kediAciklama;
    }

    public String getKediİsmi() {
        return kediİsmi;
    }

    public void setKediİsmi(String kediİsmi) {
        this.kediİsmi = kediİsmi;
    }

    public String getKediIrki() {
        return kediIrki;
    }

    public void setKediIrki(String kediIrki) {
        this.kediIrki = kediIrki;
    }

    public String getKediRengi() {
        return kediRengi;
    }

    public void setKediRengi(String kediRengi) {
        this.kediRengi = kediRengi;
    }

    public Bitmap getKediResim() {
        return kediResim;
    }

    public void setKediResim(Bitmap kediResim) {
        this.kediResim = kediResim;
    }

    public String getKediSehir() {
        return kediSehir;
    }

    public void setKediSehir(String kediSehir) {
        this.kediSehir = kediSehir;
    }

    public String getKediAciklama() {
        return kediAciklama;
    }

    public void setKediAciklama(String kediAciklama) {
        this.kediAciklama = kediAciklama;
    }

    static public ArrayList<Cat> getData(Context context){
        ArrayList<Cat> catList = new ArrayList<>();

        ArrayList<String> catNameList = new ArrayList<>();
        ArrayList<String> catIrkList = new ArrayList<>();
        ArrayList<String> catRenkList = new ArrayList<>();
        ArrayList<Bitmap> catResimList = new ArrayList<>();
        ArrayList<String> catSehirList = new ArrayList<>();
        ArrayList<String> catAciklamaList = new ArrayList<>();

        try {
            SQLiteDatabase database = context.openOrCreateDatabase("Kediler",Context.MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM kediler ",null);

            int kediIsmiIndex = cursor.getColumnIndex("kediAdi");
            int kediIrkiIndex = cursor.getColumnIndex("kediIrki");
            int kediRengiIndex = cursor.getColumnIndex("kediRengi");
            int kediResimIndex = cursor.getColumnIndex("kediResim");
            int kediSehirIndex = cursor.getColumnIndex("kediSehir");
            int kediAciklamaIndex = cursor.getColumnIndex("kediAciklama");

            while(cursor.moveToNext()){
                catNameList.add(cursor.getString(kediIsmiIndex));
                catIrkList.add(cursor.getString(kediIrkiIndex));
                catRenkList.add(cursor.getString(kediRengiIndex));
                catSehirList.add(cursor.getString(kediSehirIndex));
                catAciklamaList.add(cursor.getString(kediAciklamaIndex));

                byte[] gelenResimByte = cursor.getBlob(kediResimIndex);
                Bitmap gelenResim = BitmapFactory.decodeByteArray(gelenResimByte,0, gelenResimByte.length);
                catResimList.add(gelenResim);
            }
            cursor.close();

            for (int i=0;i<catNameList.size();i++){
                Cat cat = new Cat();
                cat.setKediİsmi(catNameList.get(i));
                cat.setKediIrki(catIrkList.get(i));
                cat.setKediRengi(catRenkList.get(i));
                cat.setKediResim(catResimList.get(i));
                cat.setKediSehir(catSehirList.get(i));
                cat.setKediAciklama(catAciklamaList.get(i));

                catList.add(cat);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return catList;
    }
}
