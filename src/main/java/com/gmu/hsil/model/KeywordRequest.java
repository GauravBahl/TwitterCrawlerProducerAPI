package com.gmu.hsil.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeywordRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("keywords_list")
	@Expose
	private List<String> keywords_list;

	public List<String> getKeywords_list() {
		return keywords_list;
	}

	public void setKeywords_list(List<String> keywords_list) {
		this.keywords_list = keywords_list;
	}

	@Override
	public String toString() {
		return "KeywordRequest [keywords_list=" + keywords_list + "]";
	}
	
	

}
