package com.gmu.hsil.crawlers;

import java.util.concurrent.BlockingQueue;

import org.springframework.stereotype.Service;

import com.gmu.hsil.model.ConfigurationRequest;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterAuthService {

	public BasicClient getTwitterClient(StatusesFilterEndpoint endpoint, BlockingQueue<String> queue, ConfigurationRequest config) {

		Authentication auth =
				new OAuth1(config.getConsumer_key(), 
						config.getConsumer_secret(), 
						config.getToken(), config.getToken_secret());


		BasicClient client = new ClientBuilder()
				.hosts(Constants.STREAM_HOST)
				.endpoint(endpoint)
				.authentication(auth)
				.processor(new StringDelimitedProcessor(queue))
				.build();

		return client;
	}


	public TwitterStream getTwitterClient(ConfigurationRequest configuration){
		
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(configuration.getConsumer_key())
		.setOAuthConsumerSecret(configuration.getConsumer_secret())
		.setOAuthAccessToken(configuration.getToken())
		.setOAuthAccessTokenSecret(configuration.getToken_secret());


		TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();

		return twitterStream;

	}


}
