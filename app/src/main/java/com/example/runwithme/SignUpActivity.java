package com.example.runwithme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    EditText usernameTf;
    EditText passwordTf;
    public void onClick(View view){
        if(view.getId() == R.id.logoIV || view.getId() == R.id.mainLayout){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyevent) {

        if(i == KeyEvent.KEYCODE_ENTER && keyevent.getAction()== keyevent.ACTION_DOWN){
            signupButton(view);

        }
        return false;
    }


    public void signupButton(View view){

        usernameTf = findViewById(R.id.userTf);
        passwordTf = findViewById(R.id.passTf);



        if(usernameTf.getText().toString().matches("")|| passwordTf.getText().toString().matches("")){

            Toast.makeText(this, "Username & Password are required", Toast.LENGTH_LONG).show();


        }/// If user did not enter any username or password
        else{

            ParseUser user = new ParseUser();
            user.setUsername(usernameTf.getText().toString());
            user.setPassword(passwordTf.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){

                        Log.i("Signup", "Success");
                        Toast.makeText(SignUpActivity.this, "User has been Created", Toast.LENGTH_LONG).show();                   }
                    else{
                        Toast.makeText(SignUpActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });


        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameTf = findViewById(R.id.userTf);
        passwordTf = findViewById(R.id.passTf);
        passwordTf.setOnKeyListener(this);

        ImageView logoIV = (ImageView) findViewById(R.id.logoIV);
        ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        logoIV.setOnClickListener(this);
        mainLayout.setOnClickListener(this);
    }
}
