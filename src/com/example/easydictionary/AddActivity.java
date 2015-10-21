package com.example.easydictionary;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.dreamers.model.BanglaWord;
import com.dreamers.model.EnglishWord;

public class AddActivity extends Activity {
	
	ArrayList<Integer> bngId=new ArrayList<Integer>();
	
	ArrayList<String> banglaWordList=new ArrayList<String>();
	
	
	ListView list;
	boolean update=false;
	Typeface banglaFont;
	EditText banglaWord,parts,englishWord,synonym,antonym;
	Button button;
	String a=" ";
	String b=" ";
	String c=" ";
	String d=" ";
	String e=" ";
	String f=" ";	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addword);
		
		banglaWord=(EditText)findViewById(R.id.bng);
		englishWord=(EditText)findViewById(R.id.eng);
		synonym=(EditText)findViewById(R.id.synonym);
		antonym=(EditText)findViewById(R.id.antonym);
		parts=(EditText)findViewById(R.id.part);
		//banglaWord.setText("গরু");
		button=(Button)findViewById(R.id.button1);
		
		bngId=(ArrayList<Integer>) getIntent().getSerializableExtra("id");
		banglaWordList=(ArrayList<String>) getIntent().getSerializableExtra("word");
		banglaFont = Typeface.createFromAsset(getAssets(),"font/solaimanlipinormal.ttf");
		banglaWord.setTypeface(banglaFont);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				int count =bngId.get((bngId.size()-1));
				
			a=banglaWord.getText().toString();
			b=englishWord.getText().toString();
				 c=parts.getText().toString();
				 d=synonym.getText().toString();
				 e=antonym.getText().toString();
			 f="";
				if(a.equals("")||b.equals("")||c.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please insert all the field", Toast.LENGTH_LONG).show();
				}
				
				else
				{
					if(banglaWordList.contains(a))
					{
						int id=banglaWordList.indexOf(a);
					int	i=bngId.get(id);
						EnglishWord ew=new EnglishWord(id, b, c);
						ew.save();
						
						Toast.makeText(getApplicationContext(), "Data has been added  "+d, Toast.LENGTH_LONG).show();
						
					}
					else
					{
						count++;
						BanglaWord bw=new BanglaWord(count,a,"",d,e,f);
						bw.save();
						EnglishWord ew=new EnglishWord(count, b, c);
						ew.save();
						
						Toast.makeText(getApplicationContext(), "Data has been added", Toast.LENGTH_LONG).show();
						
						
					}
					
				}
				
				BanglaWord bw=new BanglaWord();
				
			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	


}
