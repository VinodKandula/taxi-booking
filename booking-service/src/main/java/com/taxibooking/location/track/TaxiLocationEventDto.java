/**
 * 
 */
package com.taxibooking.location.track;

/**
 * @author vinodkandula
 *
 */
public class TaxiLocationEventDto {

	private long id;
	private String state;
	private double lat;
	private double lng;
	private long timestamp;
	
	public TaxiLocationEventDto(long id, String state, double lat, double lng, long timestamp) {
		super();
		this.id = id;
		this.state = state;
		this.lat = lat;
		this.lng = lng;
		this.timestamp = timestamp;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
