package com.gmu.hsil.crawlers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmu.hsil.model.Bbox;
import com.gmu.hsil.model.ConfigurationRequest;
import com.google.gson.Gson;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

@Service
public class TwitterBboxBasedCrawler implements Crawler{

	private static final Logger LOGGER = LogManager.getLogger(TwitterBboxBasedCrawler.class);

	Gson gson = new Gson();

	@Autowired
	TwitterAuthService authService;
	
	@Autowired
	KafkaService kafkaService;
	
	private static String TOPIC = "boundingbox";


	@Override
	public void crawl(ConfigurationRequest configuration) {
		
		if(configuration.getKafka_topic()!=null) {
			TOPIC = configuration.getKafka_topic();
		}
		
		ExecutorService executorService = Executors.newFixedThreadPool(1);

		TwitterStream twitterStream = authService.getTwitterClient(configuration);

		FilterQuery tweetFilterQuery = new FilterQuery();  
		List<Bbox> bounding_box = configuration.getBounding_box();

		for(Bbox bb : bounding_box) {

			double lngSW = bb.getLngSW();
			double latSW = bb.getLatSW();
			double lngNE = bb.getLngNE();
			double latNE = bb.getLatNE();
			
//			tweetFilterQuery.locations(new double[][]{new double[] {-77.448120,38.752477},
//				{-77.099991,38.896911}
//			});
			
			//-77.448120,38.752477,-77.099991,38.896911
			
			tweetFilterQuery.locations(new double[][] {
				new double[] {lngSW, latSW},{lngNE,latNE}
			});
			
			tweetFilterQuery.language(new String[]{"en"}); 
			
			Producer<String, String> kafkaProducer = kafkaService.getKafkaProducer();
			
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					twitterBoundingBoxCrawler(twitterStream,
							tweetFilterQuery, kafkaProducer);	
				}
			});

		}


	}

	private void twitterBoundingBoxCrawler(TwitterStream twitterStream,
			FilterQuery tweetFilterQuery, 
			Producer<String, String> kafkaProducer) {
		
		twitterStream.addListener(new StatusListener() {

			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatus(Status status) {
				
				String json = gson.toJson(status);
				KeyedMessage<String, String> message  = 
						new KeyedMessage<String, String>(TOPIC, json);
				kafkaProducer.send(message);
				//LOGGER.info(json);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub

			}
		});
		
		twitterStream.filter(tweetFilterQuery);

	}

}
