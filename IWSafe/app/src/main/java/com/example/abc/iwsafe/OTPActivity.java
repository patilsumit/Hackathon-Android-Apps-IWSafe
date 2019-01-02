package com.example.abc.iwsafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Arti on 3/10/2018.
 */

public class OTPActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        final Button submit=(Button)findViewById(R.id.btnSubmit);


        //mobileNo.setText(tempMobileNo);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    OTPSubmit(view);
            }
        });
    }

    public void OTPSubmit(View view) {
        EditText otp = (EditText) findViewById(R.id.etOTP);
        TextView mobileNo = (TextView) findViewById(R.id.tvMobileNo);
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String tempMobileNo=pref.getString("Uname",null);
        //TextView mobileNo = (TextView) findViewById(R.id.tvMobileNo);

        String tempOTP = otp.getText().toString();
        //String tempMobileNo = mobileNo.getText().toString();

        //if (tempOTP != null && tempMobileNo != null) {
            Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();
            new OTPServerActivity(this).execute(tempOTP, tempMobileNo);
        //} else {
            //Toast.makeText(this, "Fill the empty field.", Toast.LENGTH_SHORT).show();
        //}
    }
}
