/*
 * Copyright (C) 2011 www.itcsolutions.eu
 *
 * This file is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1, or (at your
 * option) any later version.
 *
 * This file is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 *
 */

/**
 *
 * @author Catalin - www.itcsolutions.eu
 * @version 2011
 *
 */
package com.example.abc.iwsafe;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends Activity implements AdapterView.OnItemClickListener {
	String MySms = null;
	private EditText edittext1, edittext2;
	private TextView t,t1,t2,t3,t4;
	Button button;
	Button btnupdate;
	Button btndisplay;
	String log;
	int count=0;
	Button buttonSend;
	Button btndelete;

	//GPS strat
	private LocationManager locationMangaer=null;
	private LocationListener locationListener=null; 

	private Button btnGetLocation = null;
	private EditText editLocation = null; 

	//private ProgressBar pb =null;

	private static final String TAG = "Debug";
	private Boolean flag = false;
	ArrayList<String> smsMessagesList = new ArrayList<String>();
	ListView smsListView;
	ArrayAdapter arrayAdapter;
	//gps end

	//private ListView lv;

	String name, phone, name1,phone1;
	DatabaseHandler db = new DatabaseHandler(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.second);
		t=new TextView(this); 
		t1=new TextView(this); 
		t2=new TextView(this);
		smsListView = (ListView) findViewById(R.id.SMSList);
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
		smsListView.setAdapter(arrayAdapter);
		smsListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
		edittext1=new EditText(this);
		edittext1 = (EditText) findViewById(R.id.editText1);

		edittext2=new EditText(this);
		edittext2 = (EditText) findViewById(R.id.editText2);



		locationMangaer = (LocationManager) 
				getSystemService(Context.LOCATION_SERVICE);


		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		refereshListView();
		

		addListenerOnButton();
	}
	public void refereshListView()
	{
		arrayAdapter.clear();
		List<Contact> contacts = db.getAllContacts();

		for (Contact cn : contacts) {

			String NameNumber = null;
			NameNumber = cn.getName()+" : "+cn.getPhoneNumber();
			arrayAdapter.add(NameNumber);
		}
	}

	public void addListenerOnButton() {

		//String MySms = null;
		button = (Button) findViewById(R.id.button1);
		//button.setBackgroundResource(R.drawable.ic_launcher);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				//count++;
				System.out.println(count);
				if(count < 4)
				{
					// Inserting Contacts
					name= edittext1.getText().toString();
					phone=edittext2.getText().toString();
					if(name.length()>0 && (phone.length()==10 || phone.length()==11))
					{

						Log.d("Insert: ", "Inserting ..");
						db.addContact(new Contact(name, phone));
						count++;
				
					}
					arrayAdapter.clear();
					List<Contact> contacts = db.getAllContacts();

					for (Contact cn : contacts) {

						String NameNumber = null;
						NameNumber = cn.getName()+" : "+cn.getPhoneNumber();
						arrayAdapter.add(NameNumber);
					}

				}


			}

		});

		




		btnupdate = (Button) findViewById(R.id.button2);

		btnupdate.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				// Updating Contacts
				int idd;
				name= edittext1.getText().toString();
				phone=edittext2.getText().toString();
				Log.d("Update: ", "Updating ..");
				//db.updateContact(new Contact(name, phone));
				//Log.d("Updated: ", "Updated ..");				
				List<Contact> contacts2 = db.getAllContacts();       
				for (Contact cn : contacts2) {
					if(cn.getName().equals(name))
					{
						idd = cn.getID();
						db.updateContact2(idd, name, phone);
						break;
					}

				}
refereshListView();
			}


		});


		
		
		
		btndelete = (Button) findViewById(R.id.button4);

		btndelete.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				// Deleting Contacts
				int idd;
				name= edittext1.getText().toString();
				phone=edittext2.getText().toString();
				Log.d("Delete: ", "Deleting ..");			
				List<Contact> contacts2 = db.getAllContacts();       
				for (Contact cn : contacts2) {
					if(cn.getName().equals(name))
					{
						idd = cn.getID();
						db.deleteContact2(idd);
						break;
					}

				}

			refereshListView();
			}


		});

		
		
		
		
		//-----------------------------------------------------------------
	}








	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		try {
			String[] smsMessages = smsMessagesList.get(pos).split(":");
			String address = smsMessages[0];
			String smsMessage = "";
			for (int i = 1; i < smsMessages.length; ++i) {
				smsMessage += smsMessages[i];
			}
			edittext1.setText(address);
			edittext2.setText(smsMessage);
			String smsMessageStr = address + "\n";
			smsMessageStr += smsMessage;
			Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

