package com.finalproject.seniordesignproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.finalproject.seniordesignproject.R;

public class ReportPage extends AppCompatActivity {

    Button postbutton,scanbutton,allpostbutton;

    ImageView imageViewbackk;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);
        postbutton = (Button)findViewById(R.id.postbutton);
        scanbutton = (Button)findViewById(R.id.scanbutton);
        allpostbutton = (Button)findViewById(R.id.allpostbutton);
        imageViewbackk = (ImageView)findViewById(R.id.imageViewbackk);




        postbutton.setOnClickListener(new View.OnClickListener()

        { //Save butonu
            @Override
            public void onClick (View v){
                //Ardından Intent methodunu kullanarak nereden nereye gideceğini söylüyoruz.
                Intent intent = new Intent(ReportPage.this, CatReportActivity.class);
                startActivity(intent);
            }
        });


        scanbutton.setOnClickListener(new View.OnClickListener()

        { //Save butonu
            @Override
            public void onClick (View v){
                //Ardından Intent methodunu kullanarak nereden nereye gideceğini söylüyoruz.
                Intent intent = new Intent(ReportPage.this, ScanCat.class);
                startActivity(intent);
            }
        });

        allpostbutton.setOnClickListener(new View.OnClickListener()

        { //Save butonu
            @Override
            public void onClick (View v){
                //Ardından Intent methodunu kullanarak nereden nereye gideceğini söylüyoruz.
                Intent intent = new Intent(ReportPage.this, HomePage.class);
                startActivity(intent);
            }
        });



        imageViewbackk.setOnClickListener(new View.OnClickListener()

        { //Save butonu
            @Override
            public void onClick (View v){
                //Ardından Intent methodunu kullanarak nereden nereye gideceğini söylüyoruz.
                Intent intent = new Intent(ReportPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}