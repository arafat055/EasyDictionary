package com.example.easydictionary;



import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.dreamers.jsontest.Post;
import com.dreamers.jsontest.Tag;
import com.dreamers.jsontest.Word;
import com.dreamers.model.BanglaWord;
import com.dreamers.model.EnglishWord;
import com.dreamers.model.History;

import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends Activity {
	boolean availableProduct=false;
	TextView msgView; LinearLayout update;String error="";
	//HashMap<String,ArrayList<String>>data=new HashMap<String, ArrayList<String>>();
	//HashMap<Integer,String>phonetic=new HashMap<Integer, String>();
	ArrayList<BanglaWord> objBangla=new ArrayList<BanglaWord>();
	ArrayList<EnglishWord> objEnglish=new ArrayList<EnglishWord>();
	
	int load=0;
	int version=0;
	String lastVersion="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.update);
		
		msgView=(TextView)findViewById(R.id.textView1);
		update=(LinearLayout)findViewById(R.id.update);
		
		String msg=getIntent().getExtras().getString("msg");
		load =getIntent().getExtras().getInt("load");
		
		msgView.setText(msg);
		
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(load==1)
				{
				AsyncTaskRunner runner = new AsyncTaskRunner();
				runner.execute();
				}
				
				else
				{
					SharedPreferences pref=getSharedPreferences("database", MODE_PRIVATE);
					version=pref.getInt("version", 0);
					lastVersion=version+"";
				//	Toast.makeText(getApplicationContext(), lastVersion, Toast.LENGTH_LONG).show();
					
				AsyncTaskRunnerUpdate runner = new AsyncTaskRunnerUpdate();
				runner.execute();
				}
				
			}
		});
	}

	
	
	//----------- Fetching Current Project
	class AsyncTaskRunner extends AsyncTask<String, String, String>
	{
		 ProgressDialog progressDialog = new ProgressDialog(UpdateActivity.this);
		 private static final String TAG = "PostFetcher";
			public static final String SERVER_URL = "http://192.168.43.25/easy_dictionary/get_database.php";
			StringBuilder builder=new StringBuilder();
		 int success=5;
		  String x="";
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			try
			{
				
				
				
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(SERVER_URL);
				
				//Perform the request and check the status code
				HttpResponse response = client.execute(post);
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					
					
						//Read the server response and attempt to parse it as JSON
						Reader reader = new InputStreamReader(content);
						
						//GsonBuilder gsonBuilder = new GsonBuilder();
						//gsonBuilder.setDateFormat("M/d/yy hh:mm a");
						Gson gson = new Gson();
						List<Post> posts = Arrays.asList(gson.fromJson(reader, Post.class));
						content.close();

						
						
						// Need to get data from java obeject
						
						for(Post res : posts) {
						
							List<Word> word=res.word;
							version=res.version;
							
						for(Word tag:word)
						{
							//Toast.makeText(PostsActivity.this, tag.eng_word+"  "+tag.bng_word, Toast.LENGTH_SHORT).show();
							
							//phonetic.put(tag.id, tag.phonetic);
							List<Tag> eng_word=tag.eng_word;
							ArrayList<String> engWordList=new ArrayList<String>();
							
							BanglaWord bng=new BanglaWord(tag.id,tag.bng_word,tag.phonetic,tag.synonym,tag.antonym,tag.pronunciation);
							
							
							for(Tag engWord:eng_word)
							{
							 
							//	engWordList.add(engWord.eng_word);
								//engWordList.add(engWord.parts);
								 	
								EnglishWord eW=new EnglishWord(engWord.bng_id,engWord.eng_word,engWord.parts);
								
								objEnglish.add(eW);
								
							}
						//	data.put(tag.bng_word, engWordList);
							
							objBangla.add(bng);
							
							
							
							availableProduct=true;
							
							
						}
									
									
							
						}
						
					
						
						try
						{
						//--------Data inserting into database
						
						
							
							
							
							  
					        ActiveAndroid.beginTransaction();
					        try {
					            for (int i = 0; i < objBangla.size(); i++) {
					                BanglaWord current = objBangla.get(i);
					                
					               current.save();
					               
					               
					               
					             
					            }
					            
					            for(int j=0;j<objEnglish.size();j++)
					            {
					            	EnglishWord currentEnglish=objEnglish.get(j);
					                currentEnglish.save();
					            }
					            
					            
					            for(int k=0;k<10;k++)
					            {
					            	History hs=new History(0);
					            	hs.save();
					            }
					            
					            
					            
					            ActiveAndroid.setTransactionSuccessful();
					            Log.d("ERROR", "Transaction Successful");
					        }
					        
					        catch(Exception e)
					        {
					        	Log.d("ERROR", e.toString());
					        }
					        
					        finally {
					            ActiveAndroid.endTransaction();
					        }
					        
							
							
							
						//--------------------------------------
						
						}
						
						catch(Exception e)
						{
							Log.d("ERROR", e.toString());
						}
						
						
						
						
						
						
						
						
						
						
						
				}
					
						
			} 
			
			catch(Exception e)
			{
				error=error+e;
			}
						
						
						//------------------------------------------------
					
			
			
			return null;
		}
		
		
		
		protected void onPostExecute(String string)
		{
			
			progressDialog.dismiss();
			
			if(availableProduct==true)
			{
				
				
				/*
				
				Select select = new Select();
				ArrayList<BanglaWord> people = select.all().from(BanglaWord.class).execute();
				
				
				 for (BanglaWord bng : people) {
			        	
			        	
			            builder.append("Bangla: ")
			                    .append(bng.banglaWord)
			                    .append(" Phonetic: ")
			                    .append(bng.phonetic);
			                    
			          
			        }
				 
				 */
				 
				 
				
				 msgView.setText("Your database has been updated. Go to home screen by pressing back button");
				 
					SharedPreferences pref=getSharedPreferences("database",0);
					SharedPreferences.Editor editor=pref.edit();
					
					editor.putString("loaded","yes");
					editor.putInt("version",version);
					editor.commit();
				 
				 
				 
			
			}
			
			else
			{
				Toast.makeText(getApplicationContext(), "There is no project details=success= "+error, Toast.LENGTH_LONG).show();
				//view.setText(error);
				 
				 msgView.setText("Sorry a problem occure during loading database. Try letter");
				 
			}
		
		}
		
		protected void onPreExecute()
		{
			
			progressDialog.setMessage("Please wait. Loading..");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		
		
		
			}
	
	
	
	//------------------------------------------------------------------


	
	//----------- Fetching Current Project
	class AsyncTaskRunnerUpdate extends AsyncTask<String, String, String>
	{
		 ProgressDialog progressDialog = new ProgressDialog(UpdateActivity.this);
		 private  final String TAG = "PostFetcher";
			public  String SERVER_URL = "http://192.168.43.25/easy_dictionary/update.php";
			StringBuilder builder=new StringBuilder();
		 int success=5;
		  String x="";
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			try
			{
	List<NameValuePair> pair= new ArrayList<NameValuePair>();
				
				pair.add(new BasicNameValuePair("version", lastVersion));
				
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(pair, "utf-8");
				SERVER_URL += "?" + paramString;
				HttpGet httpGet = new HttpGet(SERVER_URL);


				
				
				
				
				HttpClient client = new DefaultHttpClient();
				//HttpPost post = new HttpPost(SERVER_URL);
				
			//	post.setEntity(new UrlEncodedFormEntity(pair));
				
				//Perform the request and check the status code
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					
					
						//Read the server response and attempt to parse it as JSON
						Reader reader = new InputStreamReader(content);
						
						//GsonBuilder gsonBuilder = new GsonBuilder();
						//gsonBuilder.setDateFormat("M/d/yy hh:mm a");
						Gson gson = new Gson();
						List<Post> posts = Arrays.asList(gson.fromJson(reader, Post.class));
						content.close();

						
						
						// Need to get data from java obeject
						
						for(Post res : posts) {
						
							List<Word> word=res.word;
							version=res.version;
							success=res.success;
							
							if(success==1)
							{
							
							
						for(Word tag:word)
						{
							//Toast.makeText(PostsActivity.this, tag.eng_word+"  "+tag.bng_word, Toast.LENGTH_SHORT).show();
							
							//phonetic.put(tag.id, tag.phonetic);
							List<Tag> eng_word=tag.eng_word;
							ArrayList<String> engWordList=new ArrayList<String>();
							
							BanglaWord bng=new BanglaWord(tag.id,tag.bng_word,tag.phonetic,tag.synonym,tag.antonym,tag.pronunciation);
							
							
							for(Tag engWord:eng_word)
							{
							 
							//	engWordList.add(engWord.eng_word);
								//engWordList.add(engWord.parts);
								 	
								EnglishWord eW=new EnglishWord(engWord.bng_id,engWord.eng_word,engWord.parts);
								
								objEnglish.add(eW);
								
							}
						//	data.put(tag.bng_word, engWordList);
							
							objBangla.add(bng);
							
							
							
							availableProduct=true;
							
							
						}
									
							
							}
							else if(success==2)
							{
								
							}
							
							else
							{
								
							}
							
							
							
							
							
							
							
							
						}
						
					
						
						try
						{
						//--------Data inserting into database
						
						
							
							
							
							  
					        ActiveAndroid.beginTransaction();
					        try {
					            for (int i = 0; i < objBangla.size(); i++) {
					                BanglaWord current = objBangla.get(i);
					                
					               current.save();
					               
					               
					               
					             
					            }
					            
					            for(int j=0;j<objEnglish.size();j++)
					            {
					            	EnglishWord currentEnglish=objEnglish.get(j);
					                currentEnglish.save();
					            }
					            
					            
					            for(int k=0;k<10;k++)
					            {
					            	History hs=new History(0);
					            	hs.save();
					            }
					            
					            
					            
					            ActiveAndroid.setTransactionSuccessful();
					            Log.d("ERROR", "Transaction Successful");
					        }
					        
					        catch(Exception e)
					        {
					        	Log.d("ERROR", e.toString());
					        }
					        
					        finally {
					            ActiveAndroid.endTransaction();
					        }
					        
							
							
							
						//--------------------------------------
						
						}
						
						catch(Exception e)
						{
							Log.d("ERROR", e.toString());
						}
						
						
						
						
						
						
						
						
						
						
						
				}
					
						
			} 
			
			catch(Exception e)
			{
				error=error+e;
			}
						
						
						//------------------------------------------------
					
			
			
			return null;
		}
		
		
		
		protected void onPostExecute(String string)
		{
			
			progressDialog.dismiss();
			
			if(availableProduct==true)
			{
				
				
				/*
				
				Select select = new Select();
				ArrayList<BanglaWord> people = select.all().from(BanglaWord.class).execute();
				
				
				 for (BanglaWord bng : people) {
			        	
			        	
			            builder.append("Bangla: ")
			                    .append(bng.banglaWord)
			                    .append(" Phonetic: ")
			                    .append(bng.phonetic);
			                    
			          
			        }
				 
				 */
				 
				 
				
				 msgView.setText("Your database has been updated. Go to home screen by pressing back button");
				 
					SharedPreferences pref=getSharedPreferences("database",0);
					SharedPreferences.Editor editor=pref.edit();
					
					editor.putString("loaded","yes");
					editor.putInt("version",version);
					editor.commit();
				 
				 
				 
			
			}
			
			else if(success==0)
			{
				Toast.makeText(getApplicationContext(), "There is no project details=success= "+success, Toast.LENGTH_LONG).show();
				//view.setText(error);
				 
				 msgView.setText("Sorry a problem occure during loading database. Try letter");
				 
			}
			else 
			{
			//	Toast.makeText(getApplicationContext(), "There is no project details=success= "+success, Toast.LENGTH_LONG).show();
				//view.setText(error);
				 
				 msgView.setText("No new update is available. You are already up to date.");
				
			}
		
		}
		
		protected void onPreExecute()
		{
			
			progressDialog.setMessage("Please wait. Loading..");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		
		
		
			}
	
	
	
	//------------------------------------------------------------------


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		Intent intent=new Intent(UpdateActivity.this,OptionActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	
		
	}
	
	

}
