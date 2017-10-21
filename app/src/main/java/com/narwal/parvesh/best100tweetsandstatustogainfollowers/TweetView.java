package com.narwal.parvesh.best100tweetsandstatustogainfollowers;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static android.view.GestureDetector.*;

/**
 * Created by Parvesh on 08-Jan-17.
 */

public class TweetView extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    TextView tweetArea;
    ImageButton left;
    ImageButton right;
    int categoryIndex;
    int tweetIndex;
    ImageButton copy;
    ImageButton share;
    ClipboardManager clipboard;
    LinearLayout linearLayout;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mToggle;

    NavigationView navigationView;

    String tweets[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryIndex = getIntent().getExtras().getInt("position") - 1;

        setContentView(R.layout.tweet_view);

        initActivity();

        setUpNavDrawer();

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/loveyalikesister.ttf");
        tweetArea.setTypeface(type);
        setActivityTitleAndTextView(categoryIndex);
    }

    private void setUpNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.menu);

        mToggle = new ActionBarDrawerToggle(this,  drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int position = -1;

                switch (item.getItemId()){
                    case R.id.newtweets:
                        Intent newTweets = new Intent(TweetView.this, NewTweets.class);
                        startActivity(newTweets);
                        break;
                    case R.id.funny:
                        position = 1;
                        break;
                    case R.id.cute:
                        position = 2;
                        break;
                    case R.id.quotes:
                        position = 3;
                        break;
                    case R.id.romantic:
                        position = 4;
                        break;
                    case R.id.flirty:
                        position = 5;
                        break;
                    case R.id.confused:
                        position = 6;
                        break;
                    case R.id.shareyourtweet:
                        //go to share your tweet
                        Intent submitYourTweet = new Intent(TweetView.this, Submit.class);
                        startActivity(submitYourTweet);
                        break;
                }

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);

                Intent tweetView = new Intent(TweetView.this, TweetView.class);
                tweetView.putExtras(bundle);

                if(position != -1) startActivity(tweetView);

                return true;
            }
        });



    }

    private void initActivity() {
        tweetArea = (TextView) findViewById(R.id.tvTweetArea);
        left = (ImageButton) findViewById(R.id.ibLeft);
        right = (ImageButton) findViewById(R.id.ibRight);
        copy = (ImageButton) findViewById(R.id.ibCopy);
        share = (ImageButton) findViewById(R.id.ibShare);
        linearLayout = (LinearLayout) findViewById(R.id.linearL);


        left.setOnClickListener(this);
        right.setOnClickListener(this);
        copy.setOnClickListener(this);
        share.setOnClickListener(this);
        clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        linearLayout.setOnTouchListener(new OnSwipeTouchListener(TweetView.this){
            public void onSwipeTop() {

            }
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            public void onSwipeRight() {
                setTweet(R.id.ibRight);
            }
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            public void onSwipeLeft() {
                setTweet(R.id.ibLeft);
            }
            public void onSwipeBottom() {
            }
        });

    }

    private void setActivityTitleAndTextView(int index) {
        switch (index) {
            case 0:
                setTitle("Funny Tweet");
                tweets = getResources().getStringArray(R.array.funny_tweets);
                break;
            case 1:
                setTitle("Cute Tweet");
                tweets = getResources().getStringArray(R.array.cute_tweets);
                break;
            case 2:
                setTitle("Quotes Tweet");
                tweets = getResources().getStringArray(R.array.quotes_tweets);
                break;
            case 3:
                setTitle("Romantic Tweet");
                tweets = getResources().getStringArray(R.array.romantic_tweets);
                break;
            case 4:
                setTitle("Flirty Tweet");
                tweets = getResources().getStringArray(R.array.flirty_tweets);
                break;
            case 5:
                setTitle("Confused Tweet");
                tweets = getResources().getStringArray(R.array.confused_tweets);
                break;
        }

        //get total tweets here in array

        int totalTweets = tweets.length - 1;

        Random random = new Random();

        tweetIndex = random.nextInt(totalTweets);

        tweetArea.setText(tweets[tweetIndex]);
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        setTweet(v.getId());
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void setTweet(int id) {
        switch (id) {
            case R.id.ibLeft:
                tweetIndex--;
                if (tweetIndex < 0) tweetIndex = (tweets.length - 1);
                tweetArea.setText(tweets[tweetIndex]);
                break;

            case R.id.ibRight:
                tweetIndex++;
                if (tweetIndex > (tweets.length - 1)) tweetIndex = 0;
                tweetArea.setText(tweets[tweetIndex]);
                break;

            case R.id.ibCopy:
                ClipData myClip;
                String tweetText = tweetArea.getText().toString();
                myClip = ClipData.newPlainText("text", tweetText);
                clipboard.setPrimaryClip(myClip);

//                int sdk = android.os.Build.VERSION.SDK_INT;
//                if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
//                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                    clipboard.setText(tweetText);
//                } else {
//                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                    android.content.ClipData clip = android.content.ClipData.newPlainText("text",tweetText);
//                    clipboard.setPrimaryClip(clip);
//                }



                Toast.makeText(this, "Tweet/Status is copied to clipboard!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ibShare:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, tweetArea.getText().toString());
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent, "Share via");
                startActivity(sendIntent);
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent tweetCategories = new Intent(TweetView.this, TweetCategory.class);
        startActivity(tweetCategories);

    }
}
