package com.example.abc.iwsafe;

import android.app.Activity;
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
class RegisterServerActivity extends AsyncTask<String,Void,String>{
    Context context;
    public RegisterServerActivity(Context context){
    this.context=context;
    }

    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String[] strings) {
        String name = strings[0];
        String address = strings[1];
        String city = strings[2];
        String state = strings[3];
        String contactNo = strings[4];
        String guardianName = strings[5];
        String guardianNo = strings[6];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        SharedPreferences pref = context.getSharedPreferences("MyPrefR", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor=pref.edit();
        prefEditor.putString("contact",contactNo);
        prefEditor.commit();

        try {
            data = "?name=" + URLEncoder.encode(name, "UTF-8");
            data += "&address=" + URLEncoder.encode(address, "UTF-8");
            data += "&city=" + URLEncoder.encode(city, "UTF-8");
            data += "&state=" + URLEncoder.encode(state, "UTF-8");
            data += "&contact=" + URLEncoder.encode(contactNo, "UTF-8");
            data += "&guardian_name=" + URLEncoder.encode(guardianName, "UTF-8");
            data += "&guardian_number=" + URLEncoder.encode(guardianNo, "UTF-8");

            link = "https://womensafty.000webhostapp.com/PHP/signup.php" + data;
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
                    Toast.makeText(context, "Data inserted successfully. SignUp successful.", Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit= pref.edit();

                    SharedPreferences pref1 = context.getSharedPreferences("MyPrefR", Context.MODE_PRIVATE);
                    String mobNo=pref1.getString("contact",null);
                    edit.putString("Uname",mobNo);
                    edit.commit();
                    String mobNo1=pref.getString("Uname",null);
                   // Toast.makeText(context.getApplicationContext(),"Contact No = "+mobNo1,Toast.LENGTH_LONG).show();


                    Intent intent=new Intent(context,OTPActivity.class);
                    context.startActivity(intent);
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. SignUp failed.", Toast.LENGTH_SHORT).show();
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
