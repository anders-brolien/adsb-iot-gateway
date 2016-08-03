package eu.brolien.adsb_iot.gateway.consumer;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import eu.brolien.adsb_iot.gateway.data.ADSBData;
import eu.brolien.adsb_iot.gateway.data.Database;

public class Consumer {
	private static final String DUMP1090_URL = "DUMP1090_URL";

	
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
	
	private final String URL;
	private final Database db;
	
	public Consumer(Environment environment, Database db) {		
		URL = environment.getProperty(DUMP1090_URL);
		this.db = db;
	}
	
	
	private void fetch() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ADSBData[] data = restTemplate.getForObject(URL, ADSBData[].class);	
			long now = System.currentTimeMillis();
			for (ADSBData d : data) {
				d.setTimestamp(now);
			}
			db.update(data);
		} catch (Exception e) {
			log.error("", e);
		}
	}


	public void start() {		
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::fetch, 1, 5, TimeUnit.SECONDS);		
	}

}
