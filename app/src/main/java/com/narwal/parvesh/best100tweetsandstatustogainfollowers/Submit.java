package com.narwal.parvesh.best100tweetsandstatustogainfollowers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Parvesh on 01-May-17.
 */

public class Submit extends AppCompatActivity implements View.OnClickListener {

    private Spinner catList;
    private EditText tweet;
    private Button submit;
    private DatabaseReference databaseReference;

    private final String ADD_TO_NEW_TWEETS = getResources().getString(R.string.new_tweets_pwd);


    private final String[] tweetCategories = new String[]{"Select Category", "Funny", "Cute", "Quote", "Romantic", "Flirty", "Confused"};

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Submit Your Tweet");

        setContentView(R.layout.submit_your_tweet);

        setUpActivity();

    }

    private void setUpActivity() {

        catList = (Spinner) findViewById(R.id.spnrCategory);
        tweet = (EditText) findViewById(R.id.etTweet);
        submit = (Button) findViewById(R.id.btnSubmitTweet);

        submit.setOnClickListener(this);

        //set up spinner

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tweetCategories);
        catList.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        //get and verify category

        int postition = catList.getSelectedItemPosition();

        if(postition != 0){
            //go and add tweet

            String tweetText = tweet.getText().toString().trim();

            if(tweetText.startsWith(ADD_TO_NEW_TWEETS)){
                //posted my me directly add this to new tweet section

                databaseReference = FirebaseDatabase.getInstance().getReference().child("new_tweets_list");

                tweetText = tweetText.replace(ADD_TO_NEW_TWEETS, "").trim();

                databaseReference.push().setValue(tweetText).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Submit.this, "Thank you for your Tweet.", Toast.LENGTH_SHORT).show();
                            tweet.setText("");
                        }

                        else{
                            Toast.makeText(Submit.this, "Sorry! Something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            else{
                //add it in db
                String category = tweetCategories[postition].trim();

                HashMap<String, String> dataMap = new HashMap<String, String>();

                dataMap.put("Tweet", tweetText);
                dataMap.put("Category", category);

                databaseReference = FirebaseDatabase.getInstance().getReference().child("user_submitted_tweets");

                databaseReference.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Submit.this, "Thank you for your Tweet.", Toast.LENGTH_SHORT).show();
                            tweet.setText("");
                        }

                        else{
                            Toast.makeText(Submit.this, "Sorry! Something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }

        }

        else{
            Toast.makeText(this, "Please Select a Valid Category.", Toast.LENGTH_SHORT).show();
        }

    }
}
