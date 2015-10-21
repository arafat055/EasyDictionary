package com.dreamers.model;



import android.content.Context;


import com.activeandroid.ActiveAndroid;

public class MyApplication extends  com.activeandroid.app.Application {
    
	Context context;
	@Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        
        //Notice this initialization code here
        ActiveAndroid.initialize(this);
        
      
       
    }
	
    
  
   
}
