package com.example.ningotputrasijabat_163303030209_tugas01_7tipagib;

import android.app.Activity;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetails extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_layout);
		
		TextView tid = (TextView)findViewById(R.id.tvid);
		TextView tnama = (TextView)findViewById(R.id.tvname);
		TextView taddress = (TextView)findViewById(R.id.tvaddress);
		TextView temail = (TextView)findViewById(R.id.tvemail);
		TextView tphone = (TextView)findViewById(R.id.tvphone);
		TextView tdob = (TextView)findViewById(R.id.tvdob);
		TextView tcreated_at = (TextView)findViewById(R.id.tvcreated_at);
		TextView tupdated_at = (TextView)findViewById(R.id.tvupdated_at);
		
		tid.setText(getIntent().getStringExtra("id"));
		tnama.setText(getIntent().getStringExtra("name"));
		taddress.setText(getIntent().getStringExtra("address"));
		temail.setText(getIntent().getStringExtra("email"));
		tphone.setText(getIntent().getStringExtra("phone"));
		tdob.setText(getIntent().getStringExtra("dob"));
		tcreated_at.setText(getIntent().getStringExtra("created_at"));
		tupdated_at.setText(getIntent().getStringExtra("updated_at"));
		
	}

}
