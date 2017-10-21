package com.narwal.parvesh.best100tweetsandstatustogainfollowers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * Created by Parvesh on 29-Apr-17.
 */

public  class Services {

    public  static Intent OpenGmail(){

        try{
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "parveshtext@gmail.com" });
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Tweetbase: Share Your Tweet");
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

            emailIntent.setType("message/rfc822");

            return  emailIntent;
        }
        catch (Exception e){
            return  null;
        }

    }


}
