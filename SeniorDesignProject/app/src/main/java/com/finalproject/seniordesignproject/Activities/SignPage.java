package com.finalproject.seniordesignproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject.seniordesignproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignPage extends AppCompatActivity {

    EditText editTextMail2;
    EditText editTextFullname;
    EditText editTextUsername;
    EditText editTextPassword2;
    Button signUpButton;
    TextView textViewLogIn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_page);

        editTextMail2 = findViewById(R.id.editTextMail2);
        editTextFullname = findViewById(R.id.editTextFullname);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        signUpButton = findViewById(R.id.SignUpButton);
        textViewLogIn = findViewById(R.id.textViewLogIn);

        firebaseAuth = FirebaseAuth.getInstance();

        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SignPage.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextMail2.getText().toString();
                String fullName = editTextFullname.getText().toString();
                String username = editTextUsername.getText().toString();
                String password = editTextPassword2.getText().toString();

                // E-posta doğrulama
                if (!isValidEmail(email)) {
                    Toast.makeText(SignPage.this, "Lütfen geçerli bir e-posta adresi girin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Şifre doğrulama
                if (!isValidPassword(password)) {
                    Toast.makeText(SignPage.this, "Şifre en az 6 karakterden oluşmalıdır.", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignPage.this, task -> {
                            if (task.isSuccessful()) {
                                // Kullanıcı başarıyla oluşturuldu, diğer sayfaya yönlendir
                                Toast.makeText(SignPage.this, "Kullanıcı kaydedildi. Lütfen giriş yapın.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignPage.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Kullanıcı oluşturma başarısız, hata mesajını göster
                                Exception exception = task.getException();
                                if (exception instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(SignPage.this, "Bu e-posta adresi zaten kayıtlı.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignPage.this, "Kullanıcı kaydedilemedi.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // E-posta doğrulama
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Şifre doğrulama
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
}


// Şifre doğrulama

