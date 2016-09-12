package com.crashlogs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CustomExceptionActivity extends AppCompatActivity{

    protected EmailOptions mEmailOptions ;
    protected String email ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {

            if( mEmailOptions == null ){
                EmailOptions emailOptions = new EmailOptions.Builder()
                        .setEmail(email)
                        .setActivity(this)
                        .showShortEmailTitle()
                        .build();

                mEmailOptions = emailOptions ;
            }

            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(mEmailOptions));
        }
    }
}
