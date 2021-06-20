package com.roeticvampire.randomgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class loginActivity1 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText user_email_input, user_password_input;
    Button login_btn;
    LinearLayout primaryScreen,secondaryOverlay;
    TextView forgot_password;
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in1);
        primaryScreen=findViewById(R.id.primaryView);
        secondaryOverlay=findViewById(R.id.waitingOverlay);
        connection=CustomApplication.connection;
        mAuth = FirebaseAuth.getInstance();
        forgot_password=findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(v->{
            FirebaseAuth.getInstance().sendPasswordResetEmail(user_email_input.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(loginActivity1.this,"Password reset email sent.",Toast.LENGTH_SHORT);
                }
            });
        });
        user_email_input =findViewById(R.id.input_email);
        user_password_input =findViewById(R.id.login_password);
        login_btn=findViewById(R.id.Continue_btn);
        login_btn.setOnClickListener(v -> {

            String email_id= user_email_input.getText().toString();
            String password= user_password_input.getText().toString();



            //now we gotta check the regex for both our Strings
            if(verifyCredentials(email_id,password)){
                if(connection==null) return;
                primaryScreen.setAlpha(0.2f);
                secondaryOverlay.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email_id,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //Successful login, so we need to import the user details, that we saved back in register2 activity
                            primaryScreen.setAlpha(1f);
                            secondaryOverlay.setVisibility(View.INVISIBLE);
                            Intent intent =new Intent(loginActivity1.this,HomeActivity.class);
                            Statement st=null;
                            try {
                                st=connection.createStatement();
                                ResultSet resultSet =  st.executeQuery("SELECT * FROM RandomPrimaryTable WHERE EMAIL LIKE '"+email_id+"';");
                                while(resultSet.next()) {
                                    String name =resultSet.getString(2);
                                    String profileImg=resultSet.getString(4);
                                    int score=resultSet.getInt(3);
                                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                    //+++++++++++++++++++++++++++++++++++++++++++++++RISK WALA REGION HAI YE HUE HUE HUE HUE++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                    SharedPreferences keySharedPrefs=getSharedPreferences("Personal_details", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor1 = keySharedPrefs.edit();
                                    editor1.putString("NAME",name);
                                    editor1.putString("EMAIL",email_id);
                                    editor1.putInt("SCORE",score);
                                    editor1.putString("PROFILEIMG",profileImg);
                                    editor1.apply();

                                    Log.d("alp","name: "+name+" ,score: "+score+" ,email: "+email_id);
                                    Intent gn=new Intent(loginActivity1.this, HomeActivity.class);
                                    startActivity(gn);
                                    finish();

                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            //startActivity(intent);
                            //finish();

                        }
                        else {
                            primaryScreen.setAlpha(1f);
                            secondaryOverlay.setVisibility(View.INVISIBLE);
                            Toast.makeText(loginActivity1.this, "Wrong email or password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            else{
                user_email_input.setError("Invalid Email or password");
                Toast.makeText(loginActivity1.this,"Invalid Email or password",Toast.LENGTH_SHORT);

            }
        });


    }

    private boolean verifyCredentials(String email_id, String password) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if( !pat.matcher(email_id).matches())
            return false;
        return password.length() <= 15 && password.length() >= 6;
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}