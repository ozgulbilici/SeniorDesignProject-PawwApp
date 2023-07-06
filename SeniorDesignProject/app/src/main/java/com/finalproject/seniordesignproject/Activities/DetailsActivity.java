package com.finalproject.seniordesignproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject.seniordesignproject.R;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imgCatResim;
    private TextView txtCatİsim,txtCatIrk,txtCatRenk,txtCatSehir,txtCatAciklama;
    private String catName,catIrk,catRenk,catSehir,catAciklama;
    private Bitmap catResim;
    private Button delete;

    private void init(){
        imgCatResim = (ImageView) findViewById(R.id.detay_activity_imageViewCatResim);
        txtCatİsim = (TextView) findViewById(R.id.detay_activity_textViewCatİsim);
        txtCatIrk = (TextView) findViewById(R.id.detay_activity_textViewCatIrk);
        txtCatRenk = (TextView) findViewById(R.id.detay_activity_textViewCatRenk);
        txtCatSehir = (TextView) findViewById(R.id.detay_activity_textViewCatSehir);
        txtCatAciklama = (TextView) findViewById(R.id.detay_activity_textViewCatAciklama);
        delete = (Button)findViewById(R.id.detay_activity_buttonDelete);

        catName = HomePage.catDetail.getCatName();
        catIrk= HomePage.catDetail.getCatIrk();
        catRenk = HomePage.catDetail.getCatRenk();
        catResim = HomePage.catDetail.getCatResim();
        catSehir = HomePage.catDetail.getCatSehir();
        catAciklama = HomePage.catDetail.getCatAciklama();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();

        if(!TextUtils.isEmpty(catName) &&
                !TextUtils.isEmpty(catIrk) &&
                !TextUtils.isEmpty(catRenk) &&
                !TextUtils.isEmpty(catSehir) &&
                !TextUtils.isEmpty(catAciklama)){

            txtCatİsim.setText(catName);
            txtCatIrk.setText(catIrk);
            txtCatRenk.setText(catRenk);
            txtCatSehir.setText(catSehir);
            txtCatAciklama.setText(catAciklama);
            imgCatResim.setImageBitmap(catResim);
        }

        Button btnDelete = findViewById(R.id.detay_activity_buttonDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = openOrCreateDatabase("Kediler", MODE_PRIVATE, null);
                database.execSQL("DELETE FROM kediler WHERE kediAdi = '" + catName + "'");
                showToast("Kayıt başarıyla silindi.");

                // İşlem tamamlandıktan sonra ana ekrana dönüş yapabilirsiniz
                Intent intent = new Intent(DetailsActivity.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void showToast(String mesaj) {
        Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(this, HomePage.class);
        startActivity(backIntent);
        finish();
    }


}