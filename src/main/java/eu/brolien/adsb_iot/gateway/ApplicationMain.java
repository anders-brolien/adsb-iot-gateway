package eu.brolien.adsb_iot.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;

import com.amazonaws.services.iot.client.AWSIotException;

@SpringBootApplication(exclude = { EmbeddedServletContainerAutoConfiguration.class, WebMvcAutoConfiguration.class })
public class ApplicationMain {
	
    private static final Logger log = LoggerFactory.getLogger(ApplicationMain.class);

	
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ApplicationMain.class, args);
		
		Application applic = new Application(ctx);
			
		try {
			applic.start();
		} catch (AWSIotException e) {
			log.error("",e);			
		}
	}



}