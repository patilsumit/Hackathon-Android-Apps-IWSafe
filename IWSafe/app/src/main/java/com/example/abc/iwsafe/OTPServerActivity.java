package com.example.abc.iwsafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Arti on 3/10/2018.
 */
class OTPServerActivity extends AsyncTask<String,Void,String> {
    Context context;
    public OTPServerActivity(Context context){
        this.context=context;
    }

    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String[] strings) {
        String otp = strings[0];
        String mobileNo=strings[1];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?otp=" + URLEncoder.encode(otp, "UTF-8");
            data += "&contact=" + URLEncoder.encode(mobileNo, "UTF-8");

            link = "https://womensafty.000webhostapp.com/PHP/otpverify.php" + data;

            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "OTP verified.", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(context,StartButtonActivity.class);
                    context.startActivity(intent);

                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "OTP could not be verified.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

    }
 }

