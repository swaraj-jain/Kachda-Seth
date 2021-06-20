package com.roeticvampire.randomgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.canhub.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Target;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register2 extends AppCompatActivity {




    private Connection connection=null;


    LinearLayout primaryScreen,secondaryOverlay;
    TextView tap_to_change;
    ImageView imageChooser;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;

    Button register_btn;
    private com.canhub.cropper.CropImage CropImage;
    String nvme, email_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();
        connection= CustomApplication.connection;
        nvme=intent.getStringExtra("name");
        email_id=intent.getStringExtra("email_id");
        setContentView(R.layout.activity_register2);
        primaryScreen=findViewById(R.id.default_layout);
        secondaryOverlay=findViewById(R.id.waitingOverlay);

        secondaryOverlay.setVisibility(View.INVISIBLE);
        tap_to_change=findViewById(R.id.rand);
        imageChooser=findViewById(R.id.profileImage);
        imageChooser.setMinimumHeight(imageChooser.getWidth());

        imageChooser.setImageResource(R.drawable.default_profile_image);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);



        imageChooser.setOnClickListener(v -> {
            if(Build.VERSION.SDK_INT>+Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                }
            }//maga iff
            pickImageFromGallery();

        });





        register_btn=findViewById(R.id.Continue_btn);
        register_btn.setOnClickListener(v->{
        Statement statement=null;
        if(connection==null) return;



           primaryScreen.setAlpha(0.2f);
            secondaryOverlay.setVisibility(View.VISIBLE);






            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = ((BitmapDrawable) imageChooser.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            String imgString=Base64.encodeToString(data, Base64.DEFAULT);

            try {
                statement=connection.createStatement();
                ResultSet resultSet =  statement.executeQuery("insert into RandomPrimaryTable (EMAIL,NAME,SCORE,PROFILEIMG) VALUES('"+email_id+ "','"+nvme+"',0,'"+imgString+"');");

            } catch (java.sql.SQLException throwables) {
                throwables.printStackTrace();
                Log.d("TAG", "onCreate: "+throwables.toString());

            }
            Intent  ntent =new Intent(Register2.this,HomeActivity.class);

            SharedPreferences keySharedPrefs=getSharedPreferences("Personal_details", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = keySharedPrefs.edit();
            editor1.putString("NAME",nvme);
            editor1.putString("EMAIL",email_id);
            editor1.putInt("SCORE",0);
            editor1.putString("PROFILEIMG",imgString);
            editor1.apply();



            startActivity(ntent);
            finish();


        });

    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            CropImage.activity(data.getData()).setAspectRatio(1,1)
                    .start(this);


        }
        if (requestCode == com.canhub.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = com.canhub.cropper.CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Bitmap resultUri = result.getBitmap();
                imageChooser.setImageBitmap(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}