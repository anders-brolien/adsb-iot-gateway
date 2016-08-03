package eu.brolien.adsb_iot.gateway;

import org.springframework.context.ApplicationContext;

import com.amazonaws.services.iot.client.AWSIotException;

import eu.brolien.adsb_iot.gateway.amazon.IOTConnector;
import eu.brolien.adsb_iot.gateway.consumer.Consumer;
import eu.brolien.adsb_iot.gateway.data.Database;

public class Application {


	private final Database database;
	private final Consumer consumer;
	private final IOTConnector connector;
	
	Application(ApplicationContext ctx) {
		
		
		connector = new IOTConnector(ctx.getEnvironment());
		
		database = new Database(connector.getPublisher());
		
		consumer = new Consumer(ctx.getEnvironment(), database);
	}
	
	void start() throws AWSIotException {
		consumer.start();
		connector.connect();
	}




}