package com.gmu.hsil.crawlers;

import com.gmu.hsil.model.ConfigurationRequest;

public interface Crawler {

	public void crawl(ConfigurationRequest configuration); 
}
