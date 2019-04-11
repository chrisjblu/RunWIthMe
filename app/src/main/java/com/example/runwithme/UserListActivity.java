package com.example.runwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.runwithme.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

        ArrayList<String> users = new ArrayList<>();

    private static final int ACTIVITY_NUM = 2;


    ArrayAdapter<String> arrayAdapter ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);



        setTitle("User List");

        ListView userListView = (ListView) findViewById(R.id.UserListView);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l ) {

                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("username", users.get(i));

                startActivity(intent);

            }
        });

        users.clear();

     arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);

     userListView.setAdapter(arrayAdapter);

     ParseQuery<ParseUser> query = ParseUser.getQuery();

     query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e== null ){
                    if( objects.size() >0){
                        for (ParseUser user : objects) {
                            users.add(user.getUsername()); // adding objects to the user arraylist
                        }

                        arrayAdapter.notifyDataSetChanged();
                    }

                }

            }
        });
    }

    ///bottom navigation view setup
    ///bottom navigation view setup
    private void setupBottomNavigationView() {
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(UserListActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
 }


