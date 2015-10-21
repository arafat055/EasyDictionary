package com.dreamers.model;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "favourite")
public class Favourite extends Model {
	
	@Column (name="bng_id")
	public int code;
	
	
	
	public Favourite()
	{
		super();
	}
	
	public Favourite(int id)
	{    
		super();
		code=id;
	
	}
	

}
