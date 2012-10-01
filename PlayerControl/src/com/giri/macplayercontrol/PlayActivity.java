package com.giri.macplayercontrol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Menu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.giri.player.NewClient;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class PlayActivity extends Activity {

	private List<String> mylist = new ArrayList<String>();
	private ListView list;
	private int selectedItem = 0;
	private NewClient newClient = null;

	private void show(int value) {
		Toast.makeText(PlayActivity.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
	}


	private String fetchIPAddrFromTextBox() {
		String myStr = null;
		EditText editText = (EditText) findViewById(R.id.editText1);
		myStr = editText.getText().toString();
		return myStr;
	}

	private void createListView() {
		newClient = new NewClient();

		// setupButtonHandler();

		final EditText myEditText = (EditText) findViewById(R.id.editText1);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				mgr.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
				
				String ip = fetchIPAddrFromTextBox();

				if (ip == null)
					return;

				newClient.setSERVER_IP(ip);
				
				LinkedList<String> ll = new LinkedList<String>();
				ll = newClient.fetchPlayListLinkedList();
				
				mylist = new ArrayList<String>();
						
				list = (ListView) findViewById(R.id.listView1);
						
				for (int i = 0; i < ll.size(); i++) {
					mylist.add(ll.get(i));
				}
				
				list.setAdapter(new ArrayAdapter<String>(PlayActivity.this,android.R.layout.simple_list_item_1, mylist));
			}
		});
	
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				show(arg2);
				newClient.playIndex(arg2);
			}
		});
	}

	@Override
	public void onBackPressed() {
	    moveTaskToBack(true);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		list = (ListView) findViewById(R.id.listView1);

		createListView();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_play, menu);
		return true;
	}
}
