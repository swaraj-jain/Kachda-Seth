package com.roeticvampire.randomgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText name_input,email_input,password_input,password2_input;
    Connection connection;
    ImageButton register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        email_input=findViewById(R.id.input_email);
        name_input=findViewById(R.id.input_name);
        password2_input=findViewById(R.id.input_password2);
        password_input=findViewById(R.id.input_password);
        register_btn=findViewById(R.id.RegisterButton);
        connection=CustomApplication.connection;

        register_btn.setOnClickListener(v->{
            if(verifyCredentials())
                createUser();
        });



    }

    private boolean verifyCredentials() {
        if(connection==null) {
            Toast.makeText(this, "Network issue detected. Please try again.", Toast.LENGTH_SHORT).show();
            return false;
        }


        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if( !pat.matcher(email_input.getText().toString()).matches()) {
            email_input.setError("Invalid Email");
            return false;
        }
        //email,name,username, password,password2
        Statement st=null;
        try {
            st=connection.createStatement();
            ResultSet resultSet =  st.executeQuery("SELECT * FROM RandomPrimaryTable WHERE EMAIL LIKE '"+email_input.getText().toString()+"';");
            boolean poss=resultSet.next();
            Toast.makeText(this, "Email already in use.", Toast.LENGTH_SHORT).show();
            if(poss) return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace(); return false;
        }

        if(!(name_input.getText().toString().length()>0)||(name_input.getText().toString().length()>32)||!(password_input.getText().toString().length()>0)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password_input.getText().toString().equals(password2_input.getText().toString())) {
            password2_input.setError("Passwords do not match!");
            return false;
        }


        return true;
    }

    private void createUser() {
        mAuth.createUserWithEmailAndPassword(email_input.getText().toString(),password_input.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //we go activity2 baby
                Intent intent =new Intent(RegisterActivity.this,Register2.class);
                intent.putExtra("name",name_input.getText().toString());
                intent.putExtra("email_id",email_input.getText().toString());

                startActivity(intent);
                finish();
            }
        });
    }
}