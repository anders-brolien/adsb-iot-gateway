package eu.brolien.adsb_iot.gateway.data;

public class StatusData {
	
	private long startup;
	private long messages;
	private double lat;
	private double lon;
	private String device;
	private long timestamp;
	
	public long getStartup() {
		return startup;
	}
	public void setStartup(long startup) {
		this.startup = startup;
	}
	public long getMessages() {
		return messages;
	}
	public void setMessages(long messages) {
		this.messages = messages;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	
	
}
