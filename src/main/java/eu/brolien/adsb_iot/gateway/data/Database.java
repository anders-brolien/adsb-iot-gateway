package eu.brolien.adsb_iot.gateway.data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Database {
	
	private static final int FIVE_MINUTES = 1000 * 60 * 5;
	private final Map<String,ADSBData> db = new HashMap<>();
	private Publisher publisher;
	private final AtomicLong _startup = new AtomicLong();
	private final AtomicLong _messages = new AtomicLong();
	
	public Database() {
		_startup.set(System.currentTimeMillis());
	}
	
	public void update(ADSBData[] data) {
		_messages.addAndGet(data.length);
		
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
	
	public long getStartup() {
		return _startup.get();
	}
	
	public long getMessages() {
		return _messages.get();
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}


}
