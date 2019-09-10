package com.gmu.hsil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gmu.hsil.crawlers.TwitterBboxBasedCrawler;
import com.gmu.hsil.crawlers.TwitterKeywordBasedCrawler;
import com.gmu.hsil.model.ConfigurationRequest;

/**
 * 
 * @author Gaurav Bahl
 *
 */
@RestController
public class StartupController {

	@Autowired
	TwitterKeywordBasedCrawler keywordBasedCrawler;
	
	@Autowired
	TwitterBboxBasedCrawler bboxBasedCrawler;

	@PostMapping("/producer/initiate")
	public void initiateRequest(@RequestBody ConfigurationRequest configuration) {
		if(configuration!=null) {
			if(configuration.getTwitter_keywords()!=null && configuration.getTwitter_keywords().size()>0) {
				keywordBasedCrawler.crawl(configuration);
			}
			
			if(configuration.getBounding_box()!=null && configuration.getBounding_box().size()>0) {
				bboxBasedCrawler.crawl(configuration);
			}
		}
	}

	@GetMapping("/health")
	public void healthCheck() {
		System.out.println("Perfect");
	}

}
