package com.gmu.hsil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringKafkaTwitterStreamApplication {

	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(SpringKafkaTwitterStreamApplication.class, args);
//		Crawler service = appContext.getBean(TwitterBboxBasedCrawler.class);
//		service.crawl(null);
		
	}
	
	

}
