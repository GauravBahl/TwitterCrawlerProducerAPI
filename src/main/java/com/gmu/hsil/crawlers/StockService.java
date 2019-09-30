package com.gmu.hsil.crawlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmu.hsil.model.ConfigurationRequest;
import com.gmu.hsil.model.Metadata;
import com.gmu.hsil.model.StockMetadata;
import com.gmu.hsil.model.StockTimeSeriesData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

@Service
public class StockService {

	Gson gson = new Gson();

	@Autowired
	KafkaService kafkaService;

	private final static String TOPIC = "stocks";

	private Date currentTime = null;

	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
	
	Timer timer = new Timer();

	public void getStocks(ConfigurationRequest configuration) {
		String stock_quote = configuration.getStock_quote();

		KeyedMessage<String, String> message = null;
		Producer<String, String> kafkaProducer = kafkaService.getKafkaProducer();

		try {
			if(stock_quote!=null) {

				currentTime = new Date();

				/*
				 * 1. Should not run after 4 PM
				 * 2. Should start running at 9:31 AM
				 * 3. Should run every minute
				 * 4. Should get the last minute data
				 * */

//				while(true) {
//
//					if(dateFormat.parse(dateFormat.format(currentTime)).after(dateFormat.parse("09:31"))
//							&& dateFormat.parse(dateFormat.format(currentTime)).before(dateFormat.parse("16:00"))) {
//
//						timer.schedule(new TimerTask() {
//							public void run(){
//								try {
//									crawlStocks(stock_quote, message, kafkaProducer);
//								} catch (UnirestException e) {
//									e.printStackTrace();
//								}
//							}
//						}, 60); // 60 second intervals
//
//					}
//				}
				
				crawlStocks(stock_quote, message, kafkaProducer);

			} 

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void crawlStocks(String stock_quote, KeyedMessage<String, String> message,
			Producer<String, String> kafkaProducer) throws UnirestException {

		String alpha_vantage_url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY"
				+ "&symbol=STOCK_QUOTE&interval=1min&apikey=CZCVV0A72YEA83O0&outputsize=full";

		alpha_vantage_url = alpha_vantage_url.replace("STOCK_QUOTE", stock_quote);

		System.out.println(alpha_vantage_url);


		HttpResponse<String> response = Unirest.get(alpha_vantage_url)
				.header("Accept", "*/*")
				.header("Host", "www.alphavantage.co")
				.asString();

		StockMetadata stockMetadata = gson.fromJson(response.getBody(), StockMetadata.class);
		
		StockTimeSeriesData stockFields = new StockTimeSeriesData();
		
		JsonObject jsonObject = stockMetadata.getTimeSeries();
		
		Set<String> keys = jsonObject.keySet();
		List<String> list = new ArrayList<>(keys);
		
		Collections.reverse(list);
		
		for(String time : list) {
			//System.out.println(time);
			JsonObject jsonElement = (JsonObject) jsonObject.get(time);
			//System.out.println(jsonElement);
			sendToKafka(stockMetadata, message, kafkaProducer, stockFields, jsonElement,time);
		}
		
		//sendToKafka(stockMetadata, message, kafkaProducer, stockFields, jsonObject);

	}

	private void sendToKafka(StockMetadata stockMetadata, KeyedMessage<String, String> message,
			Producer<String, String> kafkaProducer, 
			StockTimeSeriesData stockFields, JsonObject asJsonObject, String time) {
		
		
		Metadata metaData = stockMetadata.getMetaData();
		stockFields.setTime(time);
		stockFields.setSymbol(metaData.getSymbol());
		stockFields.setInterval(metaData.getInterval());

//		String timeFieldKey = "Time Series (X)";
//		timeFieldKey = timeFieldKey.replace("X", metaData.getInterval());

		//JsonElement jsonElement = stockMetadata.getTimeSeries().get(metaData.getLastRefreshed());
		
		//JsonObject asJsonObject = jsonElement.getAsJsonObject();

		stockFields.setOpen(asJsonObject.get("1. open").getAsString());
		stockFields.setHigh(asJsonObject.get("2. high").getAsString());
		stockFields.setLow(asJsonObject.get("3. low").getAsString());
		stockFields.setClose(asJsonObject.get("4. close").getAsString());
		stockFields.setVolume(asJsonObject.get("5. volume").getAsString());

		String stockJson = gson.toJson(stockFields);

		System.out.println(stockJson);
		message = new KeyedMessage<String, String>(TOPIC, stockJson);
		kafkaProducer.send(message);
		
		
	}

}