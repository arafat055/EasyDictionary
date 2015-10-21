package com.example.easydictionary;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.dreamers.model.BanglaWord;

public class BanglaToBangla extends Activity {
	
	boolean update=false;
	Button banglaToBangla,banglaToEnglish;
boolean data=false;
	
	
	ArrayList<String> banglaWordList=new ArrayList<String>();
	ArrayList<Integer> bngId=new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.banga_to_bangla);
		
		banglaToBangla=(Button)findViewById(R.id.bangla_to_bangla);
		banglaToEnglish=(Button)findViewById(R.id.bangla_to_english);
		
		
	    banglaToBangla.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AsyncTaskRunnerBangla().execute();
			}
		});
	    
	    banglaToEnglish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncTaskRunnerBangla2().execute();
				
			}
		});
		
		
		
	}
	

	
	
	// --------------------------Bangla Search
	
	

	
	public class	AsyncTaskRunnerBangla extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	
		try
		{
		
				ArrayList<BanglaWord> fav = new Select().all().from(BanglaWord.class).execute();
				
			
			int size=fav.size();
			
			if(size==0)
			{
				data=false;
			}
			else
			{
				data=true;
			banglaWordList.clear();
			 for (BanglaWord word : fav) {
		        	
		        	//favouriteList.add(id.code);
		           
		                 
		               
		              banglaWordList.add(word.banglaWord);
		             
		        }
			 
			}
		}
		
		catch(Exception e)
		{
			Log.d("ERRORRRRRRRRR", e.toString());
		}
		return null;
	}
	
	protected void onPostExecute(String string)
	{
		
		
		if(data)
		{
		
			Intent intent= new Intent(BanglaToBangla.this,BanglaToBanglaSearch.class);
		intent.putExtra("data", banglaWordList);
		startActivity(intent);
	
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "You have not added any word as favourite", Toast.LENGTH_LONG).show();
		}
		
	
	}
	
	
	
	}


	
	
	//----------------------------------------------------------------


	
	public class	AsyncTaskRunnerBangla2 extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	
		try
		{
		
				ArrayList<BanglaWord> fav = new Select().all().from(BanglaWord.class).execute();
				
			
			int size=fav.size();
			
			if(size==0)
			{
				data=false;
			}
			else
			{
				data=true;
			banglaWordList.clear();
			 for (BanglaWord word : fav) {
		        	
		        	//favouriteList.add(id.code);
		           
		                 
		               
		              banglaWordList.add(word.banglaWord);
		             
		        }
			 
			}
		}
		
		catch(Exception e)
		{
			Log.d("ERRORRRRRRRRR", e.toString());
		}
		return null;
	}
	
	protected void onPostExecute(String string)
	{
		
		
		if(data)
		{
		
			Intent intent= new Intent(BanglaToBangla.this,BanglaSearch.class);
		intent.putExtra("data", banglaWordList);
		startActivity(intent);
	
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "You have not added any word as favourite", Toast.LENGTH_LONG).show();
		}
		
	
	}
	
	
	
	}

	
	
}
