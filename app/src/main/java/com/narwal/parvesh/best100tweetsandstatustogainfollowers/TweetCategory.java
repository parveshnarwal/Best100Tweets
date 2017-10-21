package com.narwal.parvesh.best100tweetsandstatustogainfollowers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by Parvesh on 24-Dec-16.
 */

public class TweetCategory extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // full screen
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
       //         WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);

        setTitle("Select Category");

        setContentView(R.layout.tweet_categories);

        gridView = (GridView) findViewById(R.id.gvCategory);

        gridView.setAdapter(new TweetCategoryAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);

                if(position == 0){
                    //go to new tweets sections
                    Intent newtweetView = new Intent(TweetCategory.this, NewTweets.class);
                    startActivity(newtweetView);

                }

                else if(position == 7){
                    //go to share your tweet
                    Intent email = Services.OpenGmail();
                    Intent.createChooser(email, "Share Via");
                    if(email != null){
                        startActivity(email);
                    }
                }

                else{
                    Intent tweetView = new Intent(TweetCategory.this, TweetView.class);
                    tweetView.putExtras(bundle);
                    startActivity(tweetView);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }
}

