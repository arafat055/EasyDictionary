package com.example.easydictionary;



import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.activeandroid.query.Select;

import com.dreamers.model.BanglaWord;
import com.dreamers.model.EnglishWord;
import com.dreamers.model.Favourite;
import com.dreamers.model.History;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BanglaToBanglaSearch extends Activity {
	ListView list;
	LinearLayout search;
	AutoCompleteTextView searchBar;
	ArrayList<String> data;
	MyAdapter3 adapter;
	ArrayAdapter<String>suggest;
	ImageView favourite;
	int favour=0;
	ArrayList<String> engWordList=new ArrayList<String>();
	ArrayList<String> partList=new ArrayList<String>();
	ArrayList<Integer> historyList=new ArrayList<Integer>();
	ArrayList<String> suggestion=new ArrayList<String>();
	Typeface banglaFont;
	TextView custom;
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bangla_search);
		search=(LinearLayout)findViewById(R.id.search);
		searchBar=(AutoCompleteTextView)findViewById(R.id.searchBar);
		favourite=(ImageView)findViewById(R.id.favourite);
		list=(ListView) findViewById(R.id.listView1);
		data=new ArrayList<String>();
        adapter=new MyAdapter3(this, engWordList,partList);
		
        
		list.setAdapter(adapter);
		suggestion=(ArrayList<String>) getIntent().getSerializableExtra("data");
		
		suggest  = new ArrayAdapter<String>(this, R.layout.custom_item, R.id.autoCompleteItem, suggestion);
		
		searchBar.setAdapter(suggest);
		banglaFont = Typeface.createFromAsset(getAssets(),"font/solaimanlipinormal.ttf");
		searchBar.setTypeface(banglaFont);
		favourite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
			/*	String word=searchBar.getText().toString();
				
				if(word.equals(""))
				{
				Toast.makeText(getApplicationContext(), "You have not searched any word yet.", Toast.LENGTH_LONG).show();	
				}
				*/
				
				if(favour==0)
				{
					Toast.makeText(getApplicationContext(), "You have not searched any word yet.", Toast.LENGTH_LONG).show();	
					
				}
				
				else
				{
					Favourite fav=new Favourite(favour);
					fav.save();
					Toast.makeText(getApplicationContext(), "Word added to favourite List", Toast.LENGTH_LONG).show();
					
					
					
				}
				
				
			}
		});
		
		
		
		
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
			String word=searchBar.getText().toString();
		//	word="গরু";
			if(word.equals(""))
			{
			Toast.makeText(getApplicationContext(), "Please write the word you want to search.", Toast.LENGTH_LONG).show();	
			}
			
			else
			{
				
				try{
				//-----------Searching the word---------
			//	word="গরু";
				 Select select = new Select();
					List<BanglaWord> bng=	 select.from(BanglaWord.class).where("bng_word LIKE '"+word+"'").execute();
				    //Toast.makeText(getApplicationContext(), bng.size()+"", Toast.LENGTH_LONG).show();
				    int id=0;
				    String synonym="";
				    String antonym="";
				    String pronunciation="";
				    engWordList.clear();
					   partList.clear();
					if(bng.size()>0)
					{
					
				    for (BanglaWord single : bng) {
			        	
				    	 id=single.code;
					 //   synonym=single.synonym;
					//    antonym=single.synonym;
					//    pronunciation=single.pronunciation;
					    Log.d("ERRRRRRRRRRRRRRRRRRRR",single.synonym+"  ");
					   engWordList.add(single.synonym);
					   engWordList.add(single.antonym);
					   engWordList.add(single.pronunciation);
					   partList.add("সমার্থক শব্দ");
					   partList.add("বিপরীতার্থক শব্দ");
					   partList.add("উচ্চারণ");
					   
					    
					    favour=id;
			           
			                    
			          
			        }
				    
					
					
				   
					
					SharedPreferences pref=getSharedPreferences("database",0);
					
					int count=pref.getInt("count", 0);
					
					count++;
					
					if(count==11)
						count=1;
					
					SharedPreferences.Editor editor=pref.edit();
					editor.putInt(count+"", id);
					editor.putInt("count", count);
					editor.commit();
					
					
					
				//------------------------------------
				
				
				
				
				
				
				
				
				
				
				
				
				adapter.notifyDataSetChanged();
				    
				    
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Word not found", Toast.LENGTH_LONG).show();	
					}
				    
				}
				catch(Exception e)
				{
				    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
					
				}
			}
				
				
				
			}
		});
		
	
		
		//data.add("Cow");
		//data.add("Cow");
		//data.add("Cow");
		//ArrayAdapter<String>adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,data);
		
	}


	
	
	
}
