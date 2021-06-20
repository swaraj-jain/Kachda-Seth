package com.roeticvampire.randomgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayActivity extends AppCompatActivity {
    private static final int BIODEGRADABLE=0;
    private static final int NON_BIODEGRADABLE=1;
    int result;
    int score=0;
    int response;
    ImageView currImage;
    TextView bottomTextView;
    Button biod_btn,nonbiod_btn, checkLDB_btn,playAgain_btn;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        currImage=findViewById(R.id.currImageView);
        bottomTextView=findViewById(R.id.bottomTextView);
        biod_btn=findViewById(R.id.biod_btn);
        nonbiod_btn=findViewById(R.id.nonbiod_btn);
        checkLDB_btn=findViewById(R.id.checkldb_btn);
        playAgain_btn=findViewById(R.id.playAgain_btn);
        BitmapDrawable drawable = (BitmapDrawable) currImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        result=OldieMagic(bitmap);
        //_________________________________________________________ OLDIE HELPIE!__________________________________________________________________

        dispatchTakePictureIntent();
        checkLDB_btn.setClickable(false);
        playAgain_btn.setClickable(false);
        biod_btn.setOnClickListener(v->{
            response=BIODEGRADABLE;
            createScores();

        });
        nonbiod_btn.setOnClickListener(v->{
            response=NON_BIODEGRADABLE;
            createScores();
        });







    }

    @SuppressLint("SetTextI18n")
    private void createScores() {
        biod_btn.setClickable(false);
        nonbiod_btn.setClickable(false);
        if(response==result){
            //correct answer
            score=50; //Need a better cooler function for this
            bottomTextView.setText("Congratulations!\nThat was accurate!\nYou've gained "+score+" points");
            updateScorecard();
        }
        else{
            bottomTextView.setText("Oops!\nThat wasn't right!\nWanna try again?");
        }
        playAgain_btn.setClickable(true);
        checkLDB_btn.setClickable(true);




    }

    private void updateScorecard() {
        SharedPreferences sharedpreferences = getSharedPreferences("Personal_details", Context.MODE_PRIVATE);
        score+=sharedpreferences.getInt("SCORE",0);
        SharedPreferences.Editor edit=sharedpreferences.edit();
        edit.putInt("SCORE",score);
        edit.apply();
        Connection connection=CustomApplication.connection;
        if(connection!=null){
            try {
                Statement st=connection.createStatement();
                String email=sharedpreferences.getString("EMAIL","");
                st.executeQuery("UPDATE RandomPrimaryTable Set SCORE = "+score+" where EMAIL Like '"+email+"';");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }

    private int OldieMagic(Bitmap bitmap) {
        //Waiting for you to guide me with all the mess that TF is
        //Until then I;ll assume every image is BIODEGRADABLE;
        return BIODEGRADABLE;


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            currImage.setImageBitmap(imageBitmap);
        }
    }
}