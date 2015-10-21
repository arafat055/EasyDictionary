package com.example.easydictionary;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.dreamers.model.BanglaWord;
import com.dreamers.model.EnglishWord;
import com.dreamers.model.Favourite;

public class OptionActivity extends Activity {
	LinearLayout banglaSearch,update,phonetic,others,favourite,history,addWord;
	boolean data=false;
	
	
	ArrayList<String> banglaWordList=new ArrayList<String>();
	ArrayList<Integer> bngId=new ArrayList<Integer>();
	
	
	@Override
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.options);
		
		banglaSearch=(LinearLayout)findViewById(R.id.search_bangla);
		update=(LinearLayout)findViewById(R.id.update);
		favourite=(LinearLayout)findViewById(R.id.favourite);
		history=(LinearLayout)findViewById(R.id.history);
		others=(LinearLayout)findViewById(R.id.others);
		
		
		phonetic=(LinearLayout)findViewById(R.id.search_phonetic);
		addWord=(LinearLayout)findViewById(R.id.add);
		
		banglaSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(OptionActivity.this,BanglaToBangla.class);
				
				startActivity(intent);
		
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			
				//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				//new AsyncTaskRunnerBangla().execute();
			}
		});
		
	addWord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Intent intent= new Intent(OptionActivity.this,BanglaSearch.class);
				//startActivity(intent);
				
				//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				new AsyncTaskRunnerAdd().execute();
			}
		});
		
		
		
		
update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(OptionActivity.this,UpdateActivity.class);
				intent.putExtra("msg", "Load your database for the first time. To load press the button below");
				intent.putExtra("load", 2);
				
				startActivity(intent);
			
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			}
		});

phonetic.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		//Intent intent= new Intent(OptionActivity.this,Phonetic.class);
		//startActivity(intent);
	
		//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		new AsyncTaskRunnerEnglish().execute();
		
	}
});

others.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	
		
		
		Select select = new Select();
		ArrayList<EnglishWord> people = select.all().from(EnglishWord.class).execute();
		StringBuilder builder=new StringBuilder();
		
		 for (EnglishWord bng : people) {
	        	
			 builder.append("id= ")
			        .append(bng.banglaId)
	                     .append("word: ")
	                    .append(bng.engWord)
	                    .append(" part: ")
	                    .append(bng.partOf).append("\n");
	                    
	          
	        }
		 
		
		/*
		Select col=new Select();
		ArrayList<Favourite> people = col.all().from(Favourite.class).execute();
		 BanglaWord word= new Select().from(BanglaWord.class).where("code="+2).executeSingle();      
         
		StringBuilder builder=new StringBuilder();
	
		 //for (Favourite bng : people) {
	        	
	        	
	            builder.append("id: ")
	                    .append(word.banglaWord)
	                    
	                   .append("\n");
	                    
	          
	      //  }
		 */
		
		Toast.makeText(getApplicationContext(), builder, Toast.LENGTH_LONG).show();
		
	}
});
		

favourite.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		new AsyncTaskRunnerFavourite().execute();
	}
});

history.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		new AsyncTaskRunnerHistory().execute();
	}
});



		//AsyncTaskRunner runner = new AsyncTaskRunner();
		//runner.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	public class	AsyncTaskRunnerFavourite extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	
		try
		{
		Select col=new Select();
				ArrayList<Favourite> fav = new Select("bng_id").from(Favourite.class).execute();
				StringBuilder builder=new StringBuilder();
			
			int size=fav.size();
			
			if(size==0)
			{
				data=false;
			}
			else
			{
				data=true;
			banglaWordList.clear();
			 for (Favourite id : fav) {
		        	
		        	//favouriteList.add(id.code);
		           
		              BanglaWord word= new Select().from(BanglaWord.class).where("code="+id.code).executeSingle();      
		               
		              if(!banglaWordList.contains(word.banglaWord))
		              {
		              banglaWordList.add(word.banglaWord);
		              }
		              else
		              {
		            	  continue;
		              }
		             
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
		
		Intent intent= new Intent(OptionActivity.this,FavouriteActivity.class);
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


	

	public class	AsyncTaskRunnerHistory extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	
		try
		{
			SharedPreferences pref=getSharedPreferences("database",0);
			
			int count=pref.getInt("count", 0);
			
			
			
			if(count==0)
			{
				data=false;
			}
			else
			{
				data=true;
			banglaWordList.clear();
			
			ArrayList<Integer>id=new ArrayList<Integer>();
			
			for(int i=1;i<=count;i++)
			{
				id.add(pref.getInt(i+"", 0));
			}
			
			 for (int i=0;i<id.size();i++) {
		        	
		        	//favouriteList.add(id.code);
		           if(id.get(i)!=0)
		           {
		              BanglaWord word= new Select().from(BanglaWord.class).where("code="+id.get(i)).executeSingle();      
		               
		              if(!banglaWordList.contains(word.banglaWord))
		              {
		              banglaWordList.add(word.banglaWord);
		              }
		           }  
		             
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
		
		Intent intent= new Intent(OptionActivity.this,FavouriteActivity.class);
		intent.putExtra("data", banglaWordList);
		startActivity(intent);
	
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "You have not searched any word yet", Toast.LENGTH_LONG).show();
		}
		
	
	}
	
	
	
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
		
			Intent intent= new Intent(OptionActivity.this,BanglaSearch.class);
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

	
	public class	AsyncTaskRunnerEnglish extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	
		try
		{
		
				ArrayList<BanglaWord> fav = new Select("phonetic").all().from(BanglaWord.class).execute();
				
			
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
		           
		                 
		               
		              banglaWordList.add(word.phonetic);
		             
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
		
			Intent intent= new Intent(OptionActivity.this,Phonetic.class);
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



	public class	AsyncTaskRunnerAdd extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	
		try
		{
		
				ArrayList<BanglaWord> fav = new Select("code","bng_word").all().from(BanglaWord.class).execute();
				
			
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
		              bngId.add(word.code);
		             
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
		
			Intent intent= new Intent(OptionActivity.this,AddActivity.class);
			intent.putExtra("id", bngId);
			intent.putExtra("word", banglaWordList);
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
