package eu.brolien.adsb_iot.gateway.data;

import java.util.HashMap;
import java.util.Map;

public class Database {
	
	private static final int FIVE_MINUTES = 1000 * 60 * 5;
	private final Map<String,ADSBData> db = new HashMap<>();
	private final Publisher publisher;
	
	public Database(Publisher publisher) {
		this.publisher = publisher;
	}
	
	public void update(ADSBData[] data) {
		
		for (ADSBData d : data) {
			ADSBData old = db.put(d.getHex(), d);
			
			boolean change = true;
			
			if (old != null) {
				if (old.getMessages() == d.getMessages()) {
					change = false;
				}				
			}
			if (change) {
				publisher.publish(d);
			}
		}
		cleanup();
	}

	private void cleanup() {
		long now = System.currentTimeMillis();
		db.values().removeIf(v -> now - v.getTimestamp() > FIVE_MINUTES);		
	}
	

}
