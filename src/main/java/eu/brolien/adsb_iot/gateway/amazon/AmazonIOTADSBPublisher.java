package eu.brolien.adsb_iot.gateway.amazon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.brolien.adsb_iot.gateway.data.ADSBData;
import eu.brolien.adsb_iot.gateway.data.Publisher;

class AmazonIOTADSBPublisher implements Publisher{
	
	private final static String topic = "adsb/data";
	private final static AWSIotQos qos = AWSIotQos.QOS0;
	private final static long timeout = 3000;                    // milliseconds

	
    private static final Logger log = LoggerFactory.getLogger(AmazonIOTADSBPublisher.class);

	private final AWSIotMqttClient client;
	private final String device;

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

	 AmazonIOTADSBPublisher(AWSIotMqttClient client, Environment environment) {
		 this.client = client;
		 this.device = environment.getProperty("amazon_iot.clientId"); 
	}

	@Override
	public void publish(ADSBData data) {
		ObjectMapper mapper = new ObjectMapper();		
		try {
			if (data.getFlight().isEmpty()) {
				data.setFlight("-");
			}
			data.setDevice(device);
			String payload = mapper.writeValueAsString(data);
			log.trace("Publish: " + payload);
			MyMessage message = new MyMessage(topic, qos, payload);
			
			if (client.getConnectionStatus() == client.getConnectionStatus().CONNECTED) {
				//only publish if connected..
				client.publish(message, timeout);
			} 
			
		} catch (AWSIotException | JsonProcessingException e) {
			log.error("", e);
		}		
	}

}
