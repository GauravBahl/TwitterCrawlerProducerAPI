package com.gmu.hsil.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyReader {
	
	@Autowired
	PropertyConfiguration config;
	
	public Map<String, String> getProperties(){
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(PropertyMapper.TOKEN, config.getToken());
		properties.put(PropertyMapper.TOKEN_SECRET, config.getTokenSecret());
		properties.put(PropertyMapper.CONSUMER_KEY, config.getConsumerKey());
		properties.put(PropertyMapper.CONSUMER_SECRET, config.getConsumerSecret());
		properties.put(PropertyMapper.TWITTER_KEYWORDS, config.getKeywords());
		return properties;
				
	}
	
	

}
