package com.example.runwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.runwithme.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.StringTokenizer;


public class ChatActivity extends AppCompatActivity {
    private static final int ACTIVITY_NUM = 2;

    String activeUser = "";

    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter arrayAdapter;

        public void sendChat (View view){


            ParseObject message = new ParseObject("Message");
            final  EditText chatEditText = (EditText) findViewById(R.id.chatEditText);

            final String messageContent = chatEditText.getText().toString(); // chatedit content dissapers, ready for a new message

             message.put("sender", ParseUser.getCurrentUser().getUsername());
             message.put("recipient", activeUser);
             message.put("message",messageContent);


             chatEditText.setText("");


             message.saveInBackground(new SaveCallback() {
                 @Override
                 public void done(ParseException e) {
                     if (e== null){

                         messages.add(messageContent);



                         arrayAdapter.notifyDataSetChanged();
                          // immendiatley displays message on the chat list once its sends
                     }
                 }
             });
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();

         activeUser = intent.getStringExtra("username");

        setTitle("Chat with " +activeUser);

        ListView chatListView = (ListView) findViewById(R.id.chatListView);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,messages);

        chatListView.setAdapter(arrayAdapter);  // applying the adatper to the chatlistview
 // setting up to individual queries & combined them as a list

        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>( "message");

        query1.whereEqualTo("sender", ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("recipent", activeUser);

        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("message");


        query2.whereEqualTo("recipent", ParseUser.getCurrentUser().getUsername());
        query2.whereEqualTo("sender", activeUser);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);

        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e== null){

                    if (objects.size()>0){

                        messages.clear(); // clears the message

                        for(ParseObject message : objects) {

                            String messageContent = message.getString("message");

                            if (!message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())){
                                messageContent = ">" +messageContent;

                            }
                            messages.add(messageContent);

                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }

}
