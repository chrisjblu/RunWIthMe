package com.example.runwithme;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


/// Login Page

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    EditText userTf;
    EditText passTf;







    public void onClick(View view){
        if(view.getId() == R.id.logoIV || view.getId() == R.id.mainLayout){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyevent) {

        if(i == KeyEvent.KEYCODE_ENTER && keyevent.getAction()== keyevent.ACTION_DOWN){ /// IF you press the enter button you automatically go down to next
            loginButton(view);

        }
        return false;
    }

    public void loginButton(View view){ /// Login Onclick

        //Declaring userTextfield and passwordtextfield
        EditText userTf = findViewById(R.id.userTf);
        EditText passTf = findViewById(R.id.passTf);



        ///  ParseUser currentUser = ParseUser.getCurrentUser();
        ///   currentUser.logOut();

        /// Logging users into Parse Server
        ParseUser.logInInBackground(userTf.getText().toString(), passTf.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                /// If the User exsists log into their profile
                if (user != null) {
                    Log.i("Login", "Successfully");
                    Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_LONG).show();

                    Intent profile = new Intent(MainActivity.this, ProfileActivity.class); // redirecting to the users profile
                    startActivity(profile);
                    finish();


                } else {
                    // if the user does not exsist show message saying they do not exsist; message is made through parse api
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    /// Intent register = new Intent(MainActivity.this, BuddyActivity.class);
                    ///startActivity(register);
                }


            }//Change

        });





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView signuplink = (TextView) findViewById(R.id.signuplink);

        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));

            }
        });



        EditText userTf = findViewById(R.id.userTf);
        EditText passTf = findViewById(R.id.passTf);
        passTf.setOnKeyListener(this);

        ImageView logoIV = (ImageView) findViewById(R.id.logoIV);
        ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        logoIV.setOnClickListener(this);
        mainLayout.setOnClickListener(this);
    }
    }

