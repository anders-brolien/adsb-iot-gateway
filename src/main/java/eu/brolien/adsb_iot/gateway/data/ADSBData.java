package eu.brolien.adsb_iot.gateway.data;

/**
 * "hex": "3c66b1"
"squawk": "6456"
"flight": "DLH7VA "
"lat": 57.6259
"lon": 11.578131
"validposition": 1
"altitude": 33475
"vert_rate": -512
"track": 355
"validtrack": 1
"speed": 479
"messages": 131
"seen": 4
 * @author andersbrolien
 *
 */

public class ADSBData {
	
	private String hex;
	private String squawk;
	private String flight;
	private double lat;
	private double lon;
	private int validposition;
	private long altitude;
	private long vert_rate;
	private long track;
	private int validtrack;
	private long speed;
	private int messages;
	private int seen;
	private long timestamp;
	private String device;

	public String getHex() {
		return hex;
	}
	public void setHex(String hex) {
		this.hex = hex;
	}
	public String getSquawk() {
		return squawk;
	}
	public void setSquawk(String squawk) {
		this.squawk = squawk;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
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
	public int getValidposition() {
		return validposition;
	}
	public void setValidposition(int validposition) {
		this.validposition = validposition;
	}
	public long getAltitude() {
		return altitude;
	}
	public void setAltitude(long altitude) {
		this.altitude = altitude;
	}
	public long getVert_rate() {
		return vert_rate;
	}
	public void setVert_rate(long vert_rate) {
		this.vert_rate = vert_rate;
	}
	public long getTrack() {
		return track;
	}
	public void setTrack(long track) {
		this.track = track;
	}
	public int getValidtrack() {
		return validtrack;
	}
	public void setValidtrack(int validtrack) {
		this.validtrack = validtrack;
	}
	public long getSpeed() {
		return speed;
	}
	public void setSpeed(long speed) {
		this.speed = speed;
	}
	public int getMessages() {
		return messages;
	}
	public void setMessages(int messages) {
		this.messages = messages;
	}
	public int getSeen() {
		return seen;
	}
	public void setSeen(int seen) {
		this.seen = seen;
	}
	
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
	@Override
	public String toString() {
		return "ADSBData [hex=" + hex + ", squawk=" + squawk + ", flight=" + flight + ", lat=" + lat + ", lon=" + lon
				+ ", validposition=" + validposition + ", altitude=" + altitude + ", vert_rate=" + vert_rate
				+ ", track=" + track + ", validtrack=" + validtrack + ", speed=" + speed + ", messages=" + messages
				+ ", seen=" + seen + ", timestamp=" + timestamp + ", device=" + device + "]";
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
