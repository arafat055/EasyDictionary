package com.example.easydictionary;



import java.util.ArrayList;

import com.activeandroid.query.Select;
import com.dreamers.model.BanglaWord;
import com.dreamers.model.EnglishWord;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FavouriteList extends Activity {
	
	
	
	ArrayList<String> engWordList=new ArrayList<String>();
	ArrayList<String> partList=new ArrayList<String>();
	String word;
	ListView list;
	boolean update=false;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.favourite);
		list=(ListView)findViewById(R.id.listView1);
		
		engWordList=(ArrayList<String>) getIntent().getSerializableExtra("word");
		partList=(ArrayList<String>) getIntent().getSerializableExtra("part");
		MyAdapter adapter=new MyAdapter(this, engWordList,partList);
		
		list.setAdapter(adapter);
		
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	


}
