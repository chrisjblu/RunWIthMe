package com.example.runwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.runwithme.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class PremiumActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 4;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    /// Declaring all TextViews to see on premium page
        TextView text1 = (TextView) findViewById(R.id.textview1);
        TextView text2= (TextView) findViewById(R.id.textview2);
        TextView text3 = (TextView) findViewById(R.id.textview3);
        TextView text4 = (TextView) findViewById(R.id.textview4);
        TextView text5 = (TextView) findViewById(R.id.textview5);


        setupBottomNavigationView();

    }
    ///bottom navigation view setup
    ///bottom navigation view setup
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(PremiumActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


}
