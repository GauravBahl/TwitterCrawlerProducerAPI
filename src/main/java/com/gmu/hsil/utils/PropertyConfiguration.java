package com.gmu.hsil.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:twitter-crawler-config.properties")
public class PropertyConfiguration {

	@Value("${token}")
	private String token;
	
	@Value("${tokenSecret}")
	private String tokenSecret;
	
	@Value("${consumerKey}")
	private String consumerKey;
	
	@Value("${consumerSecret}")
	private String consumerSecret;
	
	@Value("${twitterKeywords}")
	private String keywords;

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	
}
