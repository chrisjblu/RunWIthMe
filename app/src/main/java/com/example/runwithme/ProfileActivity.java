package com.example.runwithme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import static com.parse.Parse.getApplicationContext;

import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {

    Button logout;
    Button changeBtn;

//    public void buddy(View view){
//
//        Intent intent = new Intent(getApplicationContext(), BuddyActivity.class);
//        startActivity(intent); }

    public void redirect(){
        if(ParseUser.getCurrentUser().get("buddyOrhost").equals("buddy")){

            Intent intent = new Intent(getApplicationContext(), BuddyActivity.class);
            startActivity(intent);
        }

    }

    public void buddy(View view) {


        Switch userswitch = (Switch) findViewById(R.id.userSwitch);
        String userType = "buddy";

        if (userswitch.isChecked()) {
            userType = "host";


        }

        ParseUser.getCurrentUser().put("buddyOrhost", userType);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                redirect();

            }
        });

        Toast.makeText(ProfileActivity.this, "You are Currently Running as "+userType.toString(),Toast.LENGTH_SHORT).show();


    }




    public void getPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectImage = data.getData();
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectImage);
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);

                byte[] byteArray = stream.toByteArray();
                ParseFile file = new ParseFile("image.png", byteArray);

                ParseObject object = new ParseObject("Photo");
                object.put("image", file);
                object.put("username",ParseUser.getCurrentUser().getUsername());

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(ProfileActivity.this, "Profile Picture Changed", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(ProfileActivity.this, "Profile Picture Has not been changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getPhoto();
            }

        }
    }

//    public void profilePic(View view){
//        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//        }else{
//            getPhoto();
//        }
//
//    }

    public void redirectMain(){

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ///test

        TextView uploadpic = (TextView) findViewById(R.id.uploadPic);

        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }else{
                    getPhoto();
                }

            }
        });


        ParseUser user = ParseUser.getCurrentUser();
        TextView userDisplay = (TextView) findViewById(R.id.userDisplay);
        userDisplay.setText(ParseUser.getCurrentUser().getUsername());

        logout = (Button) findViewById(R.id.logoutbtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.getCurrentUser().logOut();


                redirectMain();






            }
        });
    }
}
