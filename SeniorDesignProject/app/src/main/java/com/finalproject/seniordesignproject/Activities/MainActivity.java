package com.finalproject.seniordesignproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject.seniordesignproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class MainActivity extends AppCompatActivity {

    Button loginbutton;
    EditText editTextPassword;
    EditText editTextMail;
    TextView textViewForgotPassword;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbutton = findViewById(R.id.loginbutton);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextMail = findViewById(R.id.editTextMail);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        TextView textViewSignUp = findViewById(R.id.textViewSignUp);

        firebaseAuth = FirebaseAuth.getInstance();

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Signup sayfasına yönlendirme
                Intent intent = new Intent(MainActivity.this, SignPage.class);
                startActivity(intent);
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent1);
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextMail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!isValidEmail(email)) {
                    Toast.makeText(MainActivity.this, "Geçerli bir e-posta adresi girin", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(MainActivity.this, "Şifre en az 6 karakter olmalıdır", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Giriş başarılı, diğer sayfaya yönlendir
                                    Toast.makeText(MainActivity.this, "Giriş başarılı.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, ReportPage.class);
                                    startActivity(intent);
                                } else {
                                    // Giriş başarısız, hata mesajını göster
                                    Exception exception = task.getException();
                                    if (exception instanceof FirebaseAuthInvalidUserException) {
                                        Toast.makeText(MainActivity.this, "Geçersiz e-posta", Toast.LENGTH_SHORT).show();
                                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(MainActivity.this, "Geçersiz şifre", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Giriş yapılamadı", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
