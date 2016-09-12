package com.crashlogs;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class CustomExceptionHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler defaultUEH;
    private final String sendErrorLogsTo ;
    private Activity activity;
    private final String emailTitle ;

    public CustomExceptionHandler(EmailOptions emailOptions) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.activity = emailOptions.activity;
        sendErrorLogsTo = emailOptions.email ;
        emailTitle = emailOptions.emailTitle;
    }

    public void uncaughtException(Thread t, Throwable e) {

        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();
        String filename = "error" + System.nanoTime() + ".stacktrace";

        Log.e("Hi", "url != null");

        StackTraceElement[] arr = e.getStackTrace();
        String report = e.toString() + "\n\n";
        report += "--------- Stack trace ---------\n\n";
        for (int i = 0; i < arr.length; i++) {
            report += "    " + arr[i].toString() + "\n";
        }
        report += "-------------------------------\n\n";

        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if (cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                report += "    " + arr[i].toString() + "\n";
            }
        }
        report += "-------------------------------\n\n";

        try {
            sendToServer(report, filename);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        defaultUEH.uncaughtException(t, e);
    }

    private void sendToServer(String stacktrace, String filename) {
        AsyncTaskClass async = new AsyncTaskClass(stacktrace, filename,emailTitle);
        async.execute("");
    }

    public class AsyncTaskClass extends AsyncTask<String, String, String> {
        String stacktrace;
        final String filename;
        String applicationName;

        AsyncTaskClass(final String stacktrace, final String filename,
                       String applicationName) {
            try {
                this.applicationName = applicationName ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.stacktrace = stacktrace;
            this.filename = filename;
        }

        @Override
        protected String doInBackground(String... params) {

            String urlString = "http://www.tharra.org/sendemail/UrbanftErroLogs.php?";
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("data", stacktrace);
                postDataParams.put("to", sendErrorLogsTo);
                postDataParams.put("subject", applicationName);
                Log.e("params",postDataParams.toString());

                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                conn.getResponseCode();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }
}

