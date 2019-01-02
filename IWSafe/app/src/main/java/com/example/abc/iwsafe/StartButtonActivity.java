package com.example.abc.iwsafe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.test.mock.MockPackageManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Arti on 3/11/2018.
 */

public class StartButtonActivity  extends Activity {

    private static final int REQUEST_CODE_PERMISSION = 2;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
    ImageButton startBtn,addContact,callBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startbtn);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        startBtn = (ImageButton) findViewById(R.id.button);
        addContact=(ImageButton)findViewById(R.id.btnAddContact);
        callBtn=(ImageButton)findViewById(R.id.btnCall);
        //lat=(TextView)findViewById(R.id.textViewLat);
        //lon=(TextView)findViewById(R.id.textViewLong);
        // show location button click event
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber();
            }

            private void callPhoneNumber() {
                try
                {
                    if(Build.VERSION.SDK_INT > 22)
                    {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling

                            ActivityCompat.requestPermissions(StartButtonActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                            return;
                        }

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + "9099073865"));
                        startActivity(callIntent);

                    }
                    else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + "9099073865"));
                        startActivity(callIntent);
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        startBtn.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {
                sendmessage();
                SendLocation();
                return true;
                // create class object
                /*gps = new GPSTracker(StartButtonActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                   // lat.setText(String.valueOf(latitude));

                    //lon.setText(String.valueOf(longitude));
                    // \n is for new line

                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }*/

            }
        });

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendcontact();
            }
        });

    }

    private void SendLocation() {
        gps = new GPSTracker(StartButtonActivity.this);
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            String tempLat = String.valueOf(latitude);
            String tempLon = String.valueOf(longitude);
            SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            String tempMobileNo=pref.getString("Uname",null);
           // Toast.makeText(this, tempMobileNo, Toast.LENGTH_SHORT).show();
            //if (tempOTP != null && tempMobileNo != null) {
            Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();
            //sendmessage();
            new StartButtonServerActivity(this).execute(tempLat, tempLon,tempMobileNo);

        }else{
            gps.showSettingsAlert();

        }

    }
    protected void sendmessage()
    {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gps = new GPSTracker(StartButtonActivity.this);
                    DatabaseHandler db = new DatabaseHandler(StartButtonActivity.this);
                    List<Contact> contacts = db.getAllContacts();
                    // check if GPS enabled
                    if(gps.canGetLocation())
                    {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        // \n is for new line
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                                + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                        String message = "I am in Trouble Please HELP ME  MY LOCATION : https://www.google.com/maps/preview/@"+gps.getLatitude()+","+gps.getLongitude()+",20z";
                        SmsManager smsManager = SmsManager.getDefault();
                        for (Contact cn : contacts) {
                            Toast.makeText(getApplicationContext(),"message",Toast.LENGTH_LONG).show();
                            smsManager.sendTextMessage(cn.getPhoneNumber(), null, message, null, null);
                        }
                        Toast.makeText(getApplicationContext(), "SMS sent.",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
    protected void sendcontact() {
        Intent intent = new Intent(this,SecondActivity.class);
        //start the second Activity
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
