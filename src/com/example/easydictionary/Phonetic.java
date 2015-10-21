package com.example.easydictionary;



import java.util.ArrayList;
import java.util.List;

import com.activeandroid.query.Select;
import com.dreamers.model.BanglaWord;
import com.dreamers.model.EnglishWord;
import com.dreamers.model.Favourite;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Phonetic extends Activity {
	ListView list;
	LinearLayout search;
	AutoCompleteTextView searchBar;
	ArrayList<String> data;
	MyAdapter adapter;ArrayAdapter<String>suggest;
	ImageView favourite;
	int favour=0;
	Typeface banglaFont;
	ArrayList<String> suggestion=new ArrayList<String>();
	TextView bangla;
	ArrayList<String> engWordList=new ArrayList<String>();
	ArrayList<String> partList=new ArrayList<String>();

	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.phonetic_search);
		search=(LinearLayout)findViewById(R.id.search);
		searchBar=(AutoCompleteTextView)findViewById(R.id.searchBar);
		favourite=(ImageView)findViewById(R.id.favourite);
		list=(ListView) findViewById(R.id.listView1);
		banglaFont = Typeface.createFromAsset(getAssets(),"font/solaimanlipinormal.ttf");
		
		bangla=(TextView)findViewById(R.id.bangla);
		bangla.setTypeface(banglaFont);
		
		data=new ArrayList<String>();
        adapter=new MyAdapter(this, engWordList,partList);
		
		list.setAdapter(adapter);
		
suggestion=(ArrayList<String>) getIntent().getSerializableExtra("data");
		
		suggest  = new ArrayAdapter<String>(this, R.layout.custom_item, R.id.autoCompleteItem, suggestion);
		
		searchBar.setAdapter(suggest);
		
		
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
			
			if(word.equals(""))
			{
			Toast.makeText(getApplicationContext(), "Please write the word you want to search.", Toast.LENGTH_LONG).show();	
			}
			
			else
			{
				
				
				//-----------Searching the word---------
				//word="আমার";
				 Select select = new Select();
				//	BanglaWord bng=	 select.from(BanglaWord.class).where("phonetic LIKE '"+word+"'").executeSingle();
					List<BanglaWord> bng=	 select.from(BanglaWord.class).where("phonetic LIKE '"+word+"'").execute();
				    //Toast.makeText(getApplicationContext(), bng.size()+"", Toast.LENGTH_LONG).show();
				    int id=0;
				    String banglaText="";
					if(bng.size()>0)
					{
					
				    for (BanglaWord single : bng) {
			        	
				    	 id=single.code;
					    
					    favour=id;
			           banglaText=single.banglaWord;
			                    
			          
			        }
				    
				   engWordList.clear();
				   partList.clear();
				
					ArrayList<EnglishWord> allWord = select.from(EnglishWord.class).where("bng_id = "+id+"").execute();
					
					
					 for (EnglishWord single : allWord) {
				        	
				        	engWordList.add(single.engWord);
				        	partList.add(single.partOf);
				           
				                    
				          
				        }
				    bangla.setText(banglaText);
				   // Toast.makeText(getApplicationContext(), id+"  "+allWord.size()+"", Toast.LENGTH_LONG).show();
                    SharedPreferences pref=getSharedPreferences("database",0);
					
					int count=pref.getInt("count", 0);
					
					count++;
					
					if(count==11)
						count=1;
					
					SharedPreferences.Editor editor=pref.edit();
					editor.putInt(count+"", id);
					editor.putInt("count", count);
					editor.commit();
					
				    
					
					
				//-------------------------------------
				
				
				
				
				
				
				
				
				
				
				adapter.notifyDataSetChanged();
			}
					else
					{
						Toast.makeText(getApplicationContext(), "Word not found", Toast.LENGTH_LONG).show();	
							
					}
				
				
				
			} // else
			
			
			
			
			}
		});
		
	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	public class	AsyncTaskRunner extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String string)
	{
		Intent intent= new Intent(Phonetic.this,Phonetic.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		   
	
	}
	
	
	
	}
}
