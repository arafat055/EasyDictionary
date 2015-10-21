package com.dreamers.jsontest;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Word {

	//@SerializedName("id")
	public int id;
	public String bng_word;
	//public String eng_word;
	//public String url;
	//@SerializedName("date")
	//public Date dateCreated;
	//public String body;
	public String phonetic;
	public String synonym;
	public String antonym;
	public String pronunciation;
	
	public List<Tag> eng_word;
	public int success;
	public Word() {
		
	}
}
