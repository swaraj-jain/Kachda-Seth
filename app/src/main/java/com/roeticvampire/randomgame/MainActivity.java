package com.roeticvampire.randomgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ImageButton register_btn,login_btn;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register_btn = findViewById(R.id.sign_up_btn);
        login_btn = findViewById(R.id.login_btn);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(() -> {
            if(user!=null){
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
            //the current activity will get finished.
        }, 1000);

        register_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

            startActivity(intent);
        });
        login_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, loginActivity1.class);

            startActivity(intent);
        });

    }
}