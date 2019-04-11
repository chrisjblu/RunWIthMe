package com.example.runwithme.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.example.runwithme.ChatActivity;
import com.example.runwithme.MapsActivity;
import com.example.runwithme.ProfileActivity;
import com.example.runwithme.R;
import com.example.runwithme.UserListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import androidx.annotation.NonNull;

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableShiftingMode(true);
        bottomNavigationViewEx.enableItemShiftingMode(true);
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){

                    case R.id.ic_house:
                        ///Acitivty_Num  = 0
                        break;

                    case R.id.ic_chat:
                        ///Acitivty_Num  = 1
                        Intent intent2 = new Intent(context, UserListActivity.class);
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_map:
                        Intent intent3= new Intent(context, MapsActivity.class); ///Acitivty_Num  = 2
                        context.startActivity(intent3);
                        break;

                    case R.id.ic_premium:
                        ///Acitivty_Num  = 3
                        break;

                    case R.id.ic_profile:
                        ///Acitivty_Num  = 4
                        Intent intent5 = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent5);
                        break;

                }

                return false;
            }
        });

    }




}
