package eu.brolien.adsb_iot.gateway.amazon;

import org.springframework.core.env.Environment;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;

import eu.brolien.adsb_iot.gateway.amazon.Util.KeyStorePasswordPair;
import eu.brolien.adsb_iot.gateway.data.Database;
import eu.brolien.adsb_iot.gateway.data.Publisher;

public class IOTConnector {
	
	private final AWSIotMqttClient client;
	private final Publisher adsbPublisher;
	private final AmazonIOTStatusPublisher statusPublisher;
	
	public IOTConnector(Environment environment, Database db) {
		String clientEndpoint = environment.getProperty("amazon_iot.clientEndpoint");       // replace <prefix> and <region> with your own
		String clientId = environment.getProperty("amazon_iot.clientId");                              // replace with your own client ID. Use unique client IDs for concurrent connections.
		String certificateFile = environment.getProperty("amazon_iot.certificateFile");
		String privateKeyFile = environment.getProperty("amazon_iot.privateKeyFile");
		
		KeyStorePasswordPair pair = Util.getKeyStorePasswordPair(certificateFile, privateKeyFile);
		client = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
		
		adsbPublisher = new AmazonIOTADSBPublisher(client, environment);
		statusPublisher = new AmazonIOTStatusPublisher(client, environment, db);
	}
	
	public void connect() throws AWSIotException {	
		
			client.setMaxConnectionRetries(Integer.MAX_VALUE);
			////client.setMaxConnectionRetries(0);
			client.connect();
	}
	
	public Publisher getPublisher() {
		return adsbPublisher;
	}
	
}
