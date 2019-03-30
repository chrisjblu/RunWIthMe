package com.example.runwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.runwithme.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MapsActivity extends AppCompatActivity {

    Button logout;
    Button changeBtn;

    private static final int ACTIVITY_NUM = 2;

//    public void buddy(View view){
//
//        Intent intent = new Intent(getApplicationContext(), BuddyActivity.class);
//        startActivity(intent); }

    public void redirect(){
        if(ParseUser.getCurrentUser().get("buddyOrhost").equals("buddy")){

            Intent intent = new Intent(getApplicationContext(), BuddyActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(getApplicationContext(), HostActivity.class);
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

        Toast.makeText(MapsActivity.this, "You are Currently Running as "+userType.toString(),Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setupBottomNavigationView();
    }

    ///bottom navigation view setup
    ///bottom navigation view setup
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(MapsActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
