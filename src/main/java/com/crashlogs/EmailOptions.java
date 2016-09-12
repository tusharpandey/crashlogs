package com.crashlogs;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;

public class EmailOptions implements Serializable {
    public Activity activity;
    public String email ;
    public String emailTitle ;

    public static class Builder {
        private Activity activity ;
        private String email;
        private String emailTitle;

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder showCustomEmailTitle(String emailTitle) {
            this.emailTitle = emailTitle;
            return this;
        }

        public Builder showDetailEmailTitle() {
            this.emailTitle = Utility.getAppLable(activity) + " | " + Utility.getDeviceName() + " | " + Utility.getAndroidVersion() + " | " ;
            return this;
        }

        public Builder showShortEmailTitle() {
            this.emailTitle = Utility.getAppLable(activity) + " | " ;
            return this;
        }

        public EmailOptions build() {
            try {
                return new EmailOptions(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null ;
        }
    }

    private EmailOptions(Builder builder) throws Exception{
        activity = builder.activity;
        email = builder.email;
        emailTitle = builder.emailTitle;

        if(activity == null){
            Log.i("Error : ","activity instance is null");
            throw new Exception();
        }

        if(TextUtils.isEmpty(email)){
            Log.i("Error : ","email is blank or null");
            throw new Exception();
        }
    }
}