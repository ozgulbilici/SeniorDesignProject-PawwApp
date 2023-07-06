package com.finalproject.seniordesignproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject.seniordesignproject.R;

public class ForgotPassword extends AppCompatActivity {

    ImageView imageViewLockReset;
    TextView textViewTrouble;
    TextView textViewWrite;
    EditText editTextMail3;
    Button sendLoginLinkButton;
    TextView textViewCreateNewAccount;
    Button backToLoginButton;
    DatabaseHelper2 databaseHelper2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        databaseHelper2 = new DatabaseHelper2(this);

        sendLoginLinkButton = findViewById(R.id.SendLoginLinkButton);
        backToLoginButton = findViewById(R.id.BackToLoginButton);
        textViewTrouble = findViewById(R.id.textViewTrouble);
        textViewWrite = findViewById(R.id.textViewWrite);
        textViewCreateNewAccount = findViewById(R.id.textViewCreateNewAccount);
        editTextMail3 = findViewById(R.id.editTextMail3);
        imageViewLockReset = findViewById(R.id.imageViewLockReset);


        sendLoginLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextMail3.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPassword.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(ForgotPassword.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    boolean emailExists = databaseHelper2.isEmailExists(email);

                    if (emailExists) {
                        sendResetInstructions(email);
                        Toast.makeText(ForgotPassword.this, "Reset instructions have been sent to your email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPassword.this, "This email does not exist in our database", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textViewCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, SignPage.class);
                startActivity(intent);
            }
        });

        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void sendResetInstructions(String email) {
        // Şifre sıfırlama talimatlarını e-posta ile göndermek için gereken işlemleri burada gerçekleştirmelisiniz.
        // E-posta gönderimi için tercih ettiğiniz bir e-posta servisini veya kütüphaneyi kullanabilirsiniz.
        // Bu örnekte sendResetInstructions() fonksiyonu, sadece ekrana bir mesaj yazdırmaktadır.
        // Gerçek uygulamada bu fonksiyonu, e-posta gönderme işlemlerini gerçekleştiren kod ile değiştirmelisiniz.
        String message = "Reset instructions have been sent to: " + email;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
