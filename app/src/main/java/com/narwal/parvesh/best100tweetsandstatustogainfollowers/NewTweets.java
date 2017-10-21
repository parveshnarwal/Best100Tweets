package com.narwal.parvesh.best100tweetsandstatustogainfollowers;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Parvesh on 29-Apr-17.
 */

public class NewTweets extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView  newTweetsList;
    private ImageView loadingImage;
    private TextView loadingText;
    private ImageView errorImage;

    ClipData myClip;
    ClipboardManager cMgr;

    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_tweets_view);

        setUpActivity();

        if(isNetworkAvailable()){
            getTweets();

            newTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String tweetText = newTweetsList.getItemAtPosition(position).toString();
                    myClip = ClipData.newPlainText("text", tweetText);
                    cMgr.setPrimaryClip(myClip);


                    Toast.makeText(NewTweets.this, "Tweet/Status is copied to clipboard.", Toast.LENGTH_SHORT).show();
                }
            });

        }

        else{
            loadingText.setVisibility(TextView.INVISIBLE);
            loadingImage.setVisibility(ImageView.INVISIBLE);

            errorImage.setVisibility(ImageView.VISIBLE);

            newTweetsList.setVisibility(ListView.INVISIBLE);
        }



    }

    private void getTweets() {

        final ArrayAdapter<String> tweetListAdapter = new ArrayAdapter<String>(this, R.layout.single_new_tweet_row, arrayList);

        newTweetsList.setAdapter(tweetListAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String list_of_tweets = dataSnapshot.getValue().toString();
                arrayList.add(list_of_tweets);

                Collections.reverse(arrayList);

                tweetListAdapter.notifyDataSetChanged();

                //tweets loading changes UI accordingly

                loadingText.setVisibility(TextView.INVISIBLE);
                loadingImage.setVisibility(ImageView.INVISIBLE);

                errorImage.setVisibility(ImageView.INVISIBLE);

                newTweetsList.setVisibility(ListView.VISIBLE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadingText.setVisibility(TextView.INVISIBLE);
                loadingImage.setVisibility(ImageView.INVISIBLE);

                errorImage.setVisibility(ImageView.VISIBLE);

                newTweetsList.setVisibility(ListView.INVISIBLE);

            }
        });

    }


    private void setUpActivity() {
        newTweetsList = (ListView) findViewById(R.id.lv_new_tweet_list);

        loadingImage = (ImageView) findViewById(R.id.ivLoading);
        errorImage = (ImageView) findViewById(R.id.ivError);
        loadingText = (TextView) findViewById(R.id.tvLoading);

        cMgr = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("new_tweets_list");

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
