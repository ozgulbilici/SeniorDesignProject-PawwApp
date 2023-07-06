package com.finalproject.seniordesignproject.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.finalproject.seniordesignproject.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CatReportActivity extends AppCompatActivity {
    private EditText editTextName, editTextIrk, editTextRenk,editTextSehir,editTextAciklama;
    private ImageView imgCat;
    private String catName, catIrk, catRenk,catSehir,catAciklama;
    private int imgIzınAlmaKodu = 0, imgIzınVerildiKodu = 1;
    private Bitmap secilenResim, kucultulenResim, enBastakiResim;
    private Button btnKaydet;
    private Button btnSelect;
    private ImageView imageViewbackback;
    private static final int PICK_IMAGE_REQUEST = 1;
    private DatabaseHelper databaseHelper;

    private void init() {
        editTextName = findViewById(R.id.report_cat_editTxtName);
        editTextIrk = findViewById(R.id.report_cat_editTxtIrk);
        editTextRenk = findViewById(R.id.report_cat_editTxtRenk);
        editTextSehir = findViewById(R.id.report_cat_editTxtSehir);
        editTextAciklama = findViewById(R.id.report_cat_editTxtAciklama);
        imgCat = findViewById(R.id.report_cat_imgView);
        btnSelect = findViewById(R.id.btnSelect);
        btnKaydet = findViewById(R.id.kaydet);
        imageViewbackback = (ImageView)findViewById(R.id.imageViewbackback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_report);
        init();

        databaseHelper = new DatabaseHelper(this);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        imageViewbackback.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(CatReportActivity.this, ReportPage.class);
                startActivity(intent);
            }
        });
    }

    public void catSave(View v) {
        catName = editTextName.getText().toString();
        catIrk = editTextIrk.getText().toString();
        catRenk = editTextRenk.getText().toString();
        catSehir = editTextSehir.getText().toString();
        catAciklama = editTextAciklama.getText().toString();

        if (!TextUtils.isEmpty(catName)) {
            if (!TextUtils.isEmpty(catIrk)) {
                if (!TextUtils.isEmpty(catRenk)) {
                    if(!TextUtils.isEmpty(catSehir)) {
                        if(!TextUtils.isEmpty(catAciklama)) {
                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            kucultulenResim = resimKucult(secilenResim);
                            kucultulenResim.compress(Bitmap.CompressFormat.PNG, 75, outputStream);
                            byte[] kayitEdilecekResim = outputStream.toByteArray();

                            try {
                                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                                String sqlSorgusu = "INSERT INTO kediler (kediAdi, kediIrki, kediRengi, kediResim,kediSehir,kediAciklama) VALUES (?, ?, ?, ?,?,?)";
                                SQLiteStatement statement = db.compileStatement(sqlSorgusu);
                                statement.bindString(1, catName);
                                statement.bindString(2, catIrk);
                                statement.bindString(3, catRenk);
                                statement.bindBlob(4, kayitEdilecekResim);
                                statement.bindString(5, catSehir);
                                statement.bindString(6,catAciklama);
                                statement.execute();
                                showToast("Kayıt başarıyla eklendi.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else
                            showToast("Kedi açıklama boş olamaz.");

                    }else {
                        showToast("Kedi şehir boş olamaz.");
                    }
                } else {
                    showToast("Kedi rengi boş olamaz.");
                }
            } else {
                showToast("Kedi ırkı boş olamaz.");
            }
        } else {
            showToast("Kedi ismi boş olamaz.");
        }
    }

    private Bitmap resimKucult(Bitmap resim) {
        return Bitmap.createScaledBitmap(resim, 120, 150, true);
    }

    private void showToast(String mesaj) {
        Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_SHORT).show();
    }

    private void nesneleriTemizle() {
        editTextName.setText("");
        editTextIrk.setText("");
        editTextRenk.setText("");
        editTextSehir.setText("");
        editTextAciklama.setText("");
        enBastakiResim = BitmapFactory.decodeResource(this.getResources(), R.drawable.cat);
        imgCat.setImageBitmap(enBastakiResim);
        btnKaydet.setEnabled(false);
    }

    public void selImg(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, imgIzınAlmaKodu);
        } else {
            Intent resimiAl = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(resimiAl, imgIzınVerildiKodu);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == imgIzınAlmaKodu) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent resimiAl = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(resimiAl, imgIzınVerildiKodu);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == imgIzınVerildiKodu) {
            if (resultCode == RESULT_OK && data != null) {
                Uri resimUri = data.getData();
                try {
                    if (Build.VERSION.SDK_INT >= 28) {
                        ImageDecoder.Source resimSource = ImageDecoder.createSource(this.getContentResolver(), resimUri);
                        secilenResim = ImageDecoder.decodeBitmap(resimSource);
                        imgCat.setImageBitmap(secilenResim);
                    } else {
                        secilenResim = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resimUri);
                        imgCat.setImageBitmap(secilenResim);
                    }
                    btnKaydet.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    showToast("Fotoğraf seçilirken bir hata oluştu.");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(this, ReportPage.class);
        startActivity(backIntent);
    }
}
