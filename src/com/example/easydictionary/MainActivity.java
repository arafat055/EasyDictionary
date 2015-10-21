package com.example.easydictionary;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity {
	
	boolean update=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		
		
		
		
		AsyncTaskRunner runner = new AsyncTaskRunner();
		runner.execute();
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
			
			
			
			
			SharedPreferences pref=getSharedPreferences("database",0);
			String value=pref.getString("loaded", "no");
			
			if(value.equals("no"))
			{
				update=true;
			}
			else
				update=false;
			
			
			
			
			
			
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String string)
	{
		if(!update)
		{
		Intent intent= new Intent(MainActivity.this,OptionActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
		else
		{
			Intent intent= new Intent(MainActivity.this,UpdateActivity.class);
			intent.putExtra("msg", "Load your database for the first time. To load press the button below");
			intent.putExtra("load", 1);
			
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
	
	}
	
	
	
	}
}
