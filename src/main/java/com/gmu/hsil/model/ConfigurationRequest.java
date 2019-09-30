package com.gmu.hsil.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigurationRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@SerializedName("consumer_key")
	@Expose
	private String consumer_key;

	@SerializedName("consumer_secret")
	@Expose
	private String consumer_secret;

	@SerializedName("token")
	@Expose
	private String token;

	@SerializedName("token_secret")
	@Expose
	private String token_secret;

	@SerializedName("twitter_keywords")
	@Expose
	private List<KeywordRequest> twitter_keywords = null;
	
	@SerializedName("bounding_box")
	@Expose
	private List<Bbox> bounding_box = null;
	
	@SerializedName("stock_quote")
	@Expose
	private String stock_quote;
	
	@SerializedName("kafka_topic")
	@Expose
	private String kafka_topic;

	public String getKafka_topic() {
		return kafka_topic;
	}
	public void setKafka_topic(String kafka_topic) {
		this.kafka_topic = kafka_topic;
	}
	
	public String getStock_quote() {
		return stock_quote;
	}
	public void setStock_quote(String stock_quote) {
		this.stock_quote = stock_quote;
	}
	public String getConsumer_key() {
		return consumer_key;
	}
	public void setConsumer_key(String consumer_key) {
		this.consumer_key = consumer_key;
	}
	public String getConsumer_secret() {
		return consumer_secret;
	}
	public void setConsumer_secret(String consumer_secret) {
		this.consumer_secret = consumer_secret;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getToken_secret() {
		return token_secret;
	}
	public void setToken_secret(String token_secret) {
		this.token_secret = token_secret;
	}
	public List<KeywordRequest> getTwitter_keywords() {
		return twitter_keywords;
	}
	public void setTwitter_keywords(List<KeywordRequest> twitter_keywords) {
		this.twitter_keywords = twitter_keywords;
	}
	
	public List<Bbox> getBounding_box() {
		return bounding_box;
	}
	public void setBounding_box(List<Bbox> bounding_box) {
		this.bounding_box = bounding_box;
	}
	@Override
	public String toString() {
		return "ConfigurationRequest [consumer_key=" + consumer_key + ", consumer_secret=" + consumer_secret
				+ ", token=" + token + ", token_secret=" + token_secret + ", twitter_keywords=" + twitter_keywords
				+ ", bounding_box=" + bounding_box + "]";
	}

}
