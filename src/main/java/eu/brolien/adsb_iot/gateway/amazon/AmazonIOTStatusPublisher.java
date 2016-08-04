package eu.brolien.adsb_iot.gateway.amazon;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.brolien.adsb_iot.gateway.data.Database;
import eu.brolien.adsb_iot.gateway.data.StatusData;

public class AmazonIOTStatusPublisher {
	
	private final static String topic = "adsb/status";
	private final static AWSIotQos qos = AWSIotQos.QOS0;
	private final static long timeout = 3000;                    // milliseconds

	
    private static final Logger log = LoggerFactory.getLogger(AmazonIOTStatusPublisher.class);

	private final AWSIotMqttClient client;
	private final String device;
	private final double lat;
	private final double lon;
	private final Database database;


	public class MyMessage extends AWSIotMessage {
	    public MyMessage(String topic, AWSIotQos qos, String payload) {
	        super(topic, qos, payload);
	    }

	    @Override
	    public void onSuccess() {
	    }

	    @Override
	    public void onFailure() {
	    	log.error("error publishing: " + getStringPayload());
	    }

	    @Override
	    public void onTimeout() {
	    	log.error("timeout publishing: " + getStringPayload());
	    }
	}

	AmazonIOTStatusPublisher(AWSIotMqttClient client, Environment environment, Database database) {
		 this.client = client;
		 this.lat = environment.getProperty("lat", Double.class, 57.74); 
		 this.lon = environment.getProperty("lon", Double.class, 11.93);   
		 this.device = environment.getProperty("amazon_iot.clientId"); 
		 this.database = database;
		 
		 Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::publish, 1, 1, TimeUnit.MINUTES);
		 
	}
	
	private void publish() {
		ObjectMapper mapper = new ObjectMapper();		
		try {
			StatusData status = new StatusData();
			status.setLat(lat);
			status.setLon(lon);
			status.setStartup(database.getStartup());
			status.setMessages(database.getMessages());
			status.setDevice(device);
			status.setTimestamp(System.currentTimeMillis());
			String payload = mapper.writeValueAsString(status);
			log.info("Publish: " + payload);
			MyMessage message = new MyMessage(topic, qos, payload);
			client.publish(message, timeout);
		} catch (AWSIotException | JsonProcessingException e) {
			log.error("", e);
		}		
	}

	

}
