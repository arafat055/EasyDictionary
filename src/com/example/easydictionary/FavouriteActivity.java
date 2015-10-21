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

public class FavouriteActivity extends Activity {
	
	ArrayList<String> data=new ArrayList<String>();
	
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
		
		data=(ArrayList<String>) getIntent().getSerializableExtra("data");
		
		MyAdapter2 adapter=new MyAdapter2(this, data);
		
		list.setAdapter(adapter);
		
		
		
		list.setOnItemClickListener(new OnItemClickListener() {
        	
       	 @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                  long arg3) 
            {
       		 
             word=data.get(position);
       //Toast.makeText(getApplicationContext(), selectedProductID, Toast.LENGTH_LONG).show();			
       AsyncTaskRunner runner =new AsyncTaskRunner();
       runner.execute();

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
			 
			
			
			
			
			 Select select = new Select();
				BanglaWord bng=	 select.from(BanglaWord.class).where("bng_word LIKE '"+word+"'").executeSingle();
			    int id=bng.code;
			    
			    
			    
			    
			
				ArrayList<EnglishWord> allWord = select.from(EnglishWord.class).where("bng_id = "+id+"").execute();
				
				engWordList.clear();
				partList.clear();
				 for (EnglishWord single : allWord) {
			        	
			        	engWordList.add(single.engWord);
			        	partList.add(single.partOf);
			           
			                    
			          
			        }
			
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String string)
	{
		
		Intent intent= new Intent(FavouriteActivity.this,FavouriteList.class);
		intent.putExtra("word", engWordList);
		intent.putExtra("part", partList);
		startActivity(intent);
		
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		
	
	}
	
	
	
	}
}
