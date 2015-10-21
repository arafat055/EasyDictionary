package com.dreamers.jsontest;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Post {

	//@SerializedName("id")
	
	//public String eng_word;
	//public String url;
	//@SerializedName("date")
	//public Date dateCreated;
	//public String body;
	
	public List<Word> word;
	public int version;
	public int success;
	
	public Post() {
		
	}
}
