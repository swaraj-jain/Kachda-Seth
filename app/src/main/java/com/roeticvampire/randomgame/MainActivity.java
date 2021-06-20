package com.roeticvampire.randomgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button register_btn,login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register_btn=findViewById(R.id.sign_up_btn);
        login_btn=findViewById(R.id.login_btn);
        register_btn.setOnClickListener(v->{
            Intent intent =new Intent(MainActivity.this, RegisterActivity.class);

            startActivity(intent);
        });
        login_btn.setOnClickListener(v->{
            Intent intent =new Intent(MainActivity.this, loginActivity1.class);

            startActivity(intent);
        });

    }
}