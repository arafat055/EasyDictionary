package com.dreamers.model;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "bangla_word")
public class BanglaWord extends Model {
	
	@Column (name="code")
	public int code;
	@Column (name="bng_word")
	public String banglaWord;
	
	@Column (name="phonetic")
	public String phonetic;
	
	@Column (name="synonym")
	public String synonym;
	
	@Column (name="antonym")
	public String antonym;
	
	@Column (name="pronunciation")
	public String pronunciation;
	
	
	
	public BanglaWord()
	{
		super();
	}
	
	public BanglaWord(int id,String bng, String phon)
	{    
		super();
		code=id;
		banglaWord=bng;
		phonetic=phon;
	}
	public BanglaWord(int id,String bng, String phon,String synonym,String antonym,String pronunciation)
	{    
		super();
		code=id;
		banglaWord=bng;
		phonetic=phon;
		this.synonym=synonym;
		this.antonym=antonym;
		this.pronunciation=pronunciation;
		
	}
	

}
