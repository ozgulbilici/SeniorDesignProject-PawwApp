package com.finalproject.seniordesignproject.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finalproject.seniordesignproject.R;
import com.finalproject.seniordesignproject.ml.Catbm;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class ScanCat extends AppCompatActivity {

    Button camera, gallery;
    ImageView imageView;
    TextView result;
    int imageSize = 150;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_cat);

        camera = findViewById(R.id.button);
        gallery = findViewById(R.id.button2);

        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);


        camera.setOnClickListener(view -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 3);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });

        result.setPaintFlags(result.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        result.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String breed = result.getText().toString();

                if (!breed.isEmpty()) {
                    String webUrl = ""; // Varsayılan web sayfası URL'si

                    // Doğru web sayfasını belirlemek için kedi cinslerini kontrol et
                    if (breed.equals("Abyssinian")) {
                        webUrl = "https://en.wikipedia.org/wiki/Abyssinian_cat";
                    } else if (breed.equals("Bengal")) {
                        webUrl = "https://en.wikipedia.org/wiki/Bengal_cat";
                    } else if (breed.equals("Birman")) {
                        webUrl = "https://en.wikipedia.org/wiki/Birman";
                    }else if (breed.equals("Bombay")) {
                        webUrl = "https://en.wikipedia.org/wiki/Bombay_cat";
                    }else if (breed.equals("British Shorthair")) {
                        webUrl = "https://en.wikipedia.org/wiki/British_Shorthair";
                    }else if (breed.equals("Calico")) {
                        webUrl = "https://en.wikipedia.org/wiki/Calico_cat";
                    }else if (breed.equals("Cornish Rex")) {
                        webUrl = "https://en.wikipedia.org/wiki/Cornish_Rex";
                    }else if (breed.equals("Egyptian Mau")) {
                        webUrl = "https://en.wikipedia.org/wiki/Egyptian_Mau";
                    }else if (breed.equals("Exotic Shorthair")) {
                        webUrl = "https://en.wikipedia.org/wiki/Exotic_Shorthair";
                    }else if (breed.equals("Maine Coon")) {
                        webUrl = "https://en.wikipedia.org/wiki/Maine_Coon";
                    }else if (breed.equals("Norwegian Forest")) {
                        webUrl = "https://en.wikipedia.org/wiki/Norwegian_Forest_cat";
                    }else if (breed.equals("Persian")) {
                        webUrl = "https://en.wikipedia.org/wiki/Persian_cat";
                    }else if (breed.equals("Ragdoll")) {
                        webUrl = "https://en.wikipedia.org/wiki/Ragdoll";
                    }else if (breed.equals("Russian Blue")) {
                        webUrl = "https://en.wikipedia.org/wiki/Russian_Blue";
                    }else if (breed.equals("Scootish Fold")) {
                        webUrl = "https://www.purina.co.uk/find-a-pet/cat-breeds/scottish-fold";
                    }else if (breed.equals("Siamese")) {
                        webUrl = "https://en.wikipedia.org/wiki/Siamese_cat";
                    }else if (breed.equals("Tabby")) {
                        webUrl = "https://en.wikipedia.org/wiki/Tabby_cat";
                    }else if (breed.equals("Tortoiseshell")) {
                        webUrl = "https://en.wikipedia.org/wiki/Tortoiseshell_cat";
                    }else if (breed.equals("Turkish Van")) {
                        webUrl = "https://en.wikipedia.org/wiki/Turkish_Van";
                    }else if (breed.equals("Tuxedo")) {
                        webUrl = "https://en.wikipedia.org/wiki/Bicolor_cat#Tuxedo";
                    }
                    // Diğer kedi cinsleri için de benzer şekilde kontrol et ve web sayfasını belirle

                    // WebViewActivity'yi başlatmak ve web sayfasını açmak için Intent kullan
                    Intent intent = new Intent(ScanCat.this, WebViewActivity.class);
                    intent.putExtra("webUrl", webUrl);
                    startActivity(intent);
                }
            }
        });
    }


    public void classifyImage(Bitmap image){
        try {

            if (image.getWidth() != imageSize || image.getHeight() != imageSize) {
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            }
            Catbm model = Catbm.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 150, 150, 3}, DataType.FLOAT32);
            int bufferSize = 4 * imageSize * imageSize * 3; // Yeni boyutu hesaplayın
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bufferSize);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            // Her pikselin R, G ve B değerlerini alarak byte dizisine ekleyin
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) / 255.f); // R
                    byteBuffer.putFloat(((val >> 8) & 0xFF) / 255.f);  // G
                    byteBuffer.putFloat((val & 0xFF) / 255.f);         // B
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Catbm.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Abyssinian", "Bengal", "Birman", "Bombay", "British Shorthair",
                    "Calico","Cornish Rex","Egyptian Mau",
                    "Exotic Shorthair","Maine Coon","Norwegian Forest",
                    "Persian","Ragdoll","Russian Blue","Scootish Fold",
                    "Siamese","Sphynx","Tabby",
                    "Tortoiseshell","Turkish Van","Tuxedo"};
            result.setText(classes[maxPos]);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            assert data != null;
            if(requestCode == 3){
                try {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                    imageView.setImageBitmap(image);

                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}