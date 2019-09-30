package com.gmu.hsil.crawlers;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmu.hsil.model.ConfigurationRequest;
import com.gmu.hsil.model.KeywordRequest;
import com.google.gson.Gson;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.httpclient.BasicClient;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;


/**
 * 
 * @author Gaurav Bahl
 *
 * Picks configuration from "twitter-crawler-config.properties"
 * 
 */
@Service
public class TwitterKeywordBasedCrawler implements Crawler{

	private static final Logger LOGGER = LogManager.getLogger(TwitterKeywordBasedCrawler.class);
	
	Gson gson = new Gson();
	
	@Autowired
	TwitterAuthService authService;
	
	@Autowired
	KafkaService kafkaService;
	
	private static String TOPIC = "twitter";


	@Override
	public void crawl(ConfigurationRequest configuration) {

		List<KeywordRequest> keywordsList = (List<KeywordRequest>) configuration.getTwitter_keywords();
		
		if(configuration.getKafka_topic()!=null) {
			TOPIC = configuration.getKafka_topic();
		}

		for(KeywordRequest keyword : keywordsList) {
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			List<String> keywords = keyword.getKeywords_list();

			executorService.execute(new Runnable() {
				@Override
				public void run() {
					BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
					StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
					BasicClient client = authService.getTwitterClient(endpoint, queue, configuration);
					client.connect();
					queue.clear();
					twitterKeywordBasedCrawler(keywords, endpoint, queue);	
				}
			});
		}

	}

	private void twitterKeywordBasedCrawler(List<String> keywords, StatusesFilterEndpoint endpoint, 
			BlockingQueue<String> queue) {
		endpoint.trackTerms(keywords);
		KeyedMessage<String, String> message = null;
		Producer<String, String> kafkaProducer = kafkaService.getKafkaProducer();
		
		while(true) {
			try {
				while(!queue.isEmpty()) {
					String take = queue.take();
					//LOGGER.info(take);
					message = new KeyedMessage<String, String>(TOPIC, take);
					kafkaProducer.send(message);
				}
			}catch(Exception e) {
				e.printStackTrace();	
			}
		}
	}

}
