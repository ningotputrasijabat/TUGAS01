package com.example.ningotputrasijabat_163303030209_tugas01_7tipagib;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
 
    // URL to get contacts JSON
    private static String url = "http://apilearning.totopeto.com/contacts";
 
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        contactList = new ArrayList<HashMap<String, String>>();
        
        lv = (ListView) findViewById(R.id.list);
 
        new GetContacts().execute();
        
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "Tested!", Toast.LENGTH_SHORT).show();
				HashMap<String, String> hm = contactList.get(arg2);
				
				Intent intent = new Intent(MainActivity.this, ContactDetails.class);
				
				intent.putExtra("id", hm.get("id"));
				intent.putExtra("name", hm.get("name"));
				intent.putExtra("address", hm.get("address"));
				intent.putExtra("email", hm.get("email"));
				intent.putExtra("phone", hm.get("phone"));
				intent.putExtra("dob", hm.get("dob"));
				intent.putExtra("created_at", hm.get("created_at"));
				intent.putExtra("updated_at", hm.get("updated_at"));
				
				startActivity(intent);
				
				//Toast.makeText(MainActivity.this, hm.get("email"), Toast.LENGTH_SHORT).show();
				
			}
		});
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {
    	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
 
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");
 
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
 
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String address = c.getString("address");
                        String email = c.getString("email");
                        String phone = c.getString("phone");
                        String dob = c.getString("dob");
                        String created_at = c.getString("created_at");
                        String updated_at = c.getString("updated_at");
 
                        // Phone node is JSON Object
                        //JSONObject phone = c.getJSONObject("phone");
                        //String mobile = phone.getString("mobile");
                        //String home = phone.getString("home");
                        //String office = phone.getString("office");
 
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("address", address);
                        contact.put("email", email);
                        contact.put("phone", phone);
                        contact.put("dob", dob);
                        contact.put("created_at", created_at);
                        contact.put("updated_at", updated_at);
 
                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
 
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
 
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[]{"name", "email",
                    "phone"}, new int[]{R.id.name,
                    R.id.email, R.id.mobile});
 
            lv.setAdapter(adapter);
        }
 
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
