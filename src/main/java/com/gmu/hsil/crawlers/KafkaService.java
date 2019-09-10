package com.gmu.hsil.crawlers;

import java.util.Properties;

import org.springframework.stereotype.Service;

import kafka.producer.ProducerConfig;

@Service
public class KafkaService {

	private static final String BOOTSTRAP_SERVERS = "localhost:9092";
	private static final Object GROUP_ID = "group1";

	public kafka.javaapi.producer.Producer<String,String> getKafkaProducer(){
		
		Properties properties = new Properties();
		properties.put("metadata.broker.list",BOOTSTRAP_SERVERS);
		properties.put("serializer.class","kafka.serializer.StringEncoder");
		properties.put("group.id",GROUP_ID);

		ProducerConfig producerConfig = new ProducerConfig(properties);
		kafka.javaapi.producer.Producer<String,String> producer = 
				new kafka.javaapi.producer.Producer<String, String>(producerConfig);
		
		return producer;
	}
}
