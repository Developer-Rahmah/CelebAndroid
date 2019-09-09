package com.celebritiescart.celebritiescart.helpers;


import android.os.AsyncTask;

import java.util.Calendar;

public class SendEmailAsync extends AsyncTask<String, Void, String> {
    private String emailBody;

    public SendEmailAsync(String body) {
        emailBody = body;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

            GMailSender sender = new GMailSender("umar@dynamiconlinetechnologies.com", "890umarbilal123");
            sender.sendMail("Error Celebrities Cart "+mydate,
                    emailBody,
                    "umar@dynamiconlinetechnologies.com",
                    "umar@dynamiconlinetechnologies.com");

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } catch (Exception e) {



        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {

    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}