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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runwithme.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import static com.parse.Parse.getApplicationContext;

import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    Button logout;
    Button changeBtn;


    private static final int ACTIVITY_NUM = 4;

    public void following(View view){
        if(ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), FollowActivity.class);
            startActivity(intent);
        }
    }// Button The redirects user to following page










    public void redirectMain(){

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ///test




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
        }); /// Logout Button on Profile

        setupBottomNavigationView();
    }
    ///bottom navigation view setup
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ProfileActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
