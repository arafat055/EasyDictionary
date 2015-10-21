package com.dreamers.model;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "english_word")
public class EnglishWord extends Model {
	
	//@Column (name="code")
	//public int code;
	@Column (name="bng_id")
	public int banglaId;
	
	@Column (name="eng_word")
	public String engWord;
	
	@Column (name="parts")
	public String partOf;
	
	
	public EnglishWord()
	{
		super();
	}
	
	public EnglishWord(int bng, String eng,String part)
	{    
		super();
	//	code=id;
		banglaId=bng;
		engWord=eng;
		partOf=part;
	}
	

}
