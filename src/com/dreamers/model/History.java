package com.dreamers.model;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "history")
public class History extends Model {
	
	@Column (name="bng_id")
	public int id;
	
	
	
	public History()
	{
		super();
	}
	
	public History(int id)
	{    
		super();
		this.id=id;
	
	}
	

}
