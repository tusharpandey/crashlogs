package com.crashlogs;

import android.app.Activity;

/**
 * Created by kiwitech on 13/9/16.
 */
public class CustomException {

    public static void initialize(Activity activity, EmailOptions emailOptions, String email) {
        if(!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {

            if( emailOptions == null ){
                EmailOptions emailOptionsTemp = new EmailOptions.Builder()
                        .setEmail(email)
                        .setActivity(activity)
                        .showShortEmailTitle()
                        .build();

                emailOptions = emailOptionsTemp ;
            }

            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(emailOptions));
        }
    }

}
