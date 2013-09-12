package models;

import org.json.JSONException;
import org.json.JSONObject;



public class Review {

	private String title;
	private String text;
	private String date;
	private double rating;
	private double value;
	private double location;
	private double sleepQ;
	private double rooms;
	private double cleanliness;
	private double service;
	private User user;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getValue() {
		return value;
	}
	public void setValue(float f) {
		this.value = f;
	}
	public double getLocation() {
		return location;
	}
	public void setLocation(double location) {
		this.location = location;
	}
	public double getSleepQ() {
		return sleepQ;
	}
	public void setSleepQ(double sleepQ) {
		this.sleepQ = sleepQ;
	}
	public double getRooms() {
		return rooms;
	}
	public void setRooms(double rooms) {
		this.rooms = rooms;
	}
	public double getCleanliness() {
		return cleanliness;
	}
	public void setCleanliness(double cleanliness) {
		this.cleanliness = cleanliness;
	}
	public double getService() {
		return service;
	}
	public void setService(double service) {
		this.service = service;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	public Review(){
		this.title = "";
		this.text = "";
		this.rating = 0;
		this.user = new User();
	}
	public Review(String title, String text, int rating){
		this.title = title;
		this.text = text;
		this.rating = rating;
		this.user = new User();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String toString(){
		return (this.rating + " --> " + this.title + " --> " + this.text); 
	}
	public void print(){
		System.out.println(this.rating + " --> " + this.title + " --> " + this.text);
		System.out.println("Value:" + this.value);
		System.out.println("Location:" + this.location);
		System.out.println("Sleep Quality:" + this.sleepQ);
		System.out.println("Rooms:" + this.rooms);
		System.out.println("Cleanliness:" + this.cleanliness);
		System.out.println("Service:" + this.service);
		this.user.print();
		System.out.println("==========================================================");
	}
	public 	JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("title", this.title);
			obj.put("text", this.text);
			obj.put("date", this.date);
			obj.put("value", this.value);
			obj.put("rating", this.rating);
			obj.put("location", this.location);
			obj.put("sleepQ", this.sleepQ);
			obj.put("rooms", this.rooms);
			obj.put("cleanliness", this.cleanliness);
			obj.put("service", this.service);
			obj.put("User", this.user.getNick());
			obj.put("Reviews", this.user.getReviews());
			obj.put("Location", this.user.getLocation());
			obj.put("hotelReviews", this.user.getHotelReviews());
			obj.put("Cities", this.user.getCities());
			obj.put("Helpful", this.user.getHelpful());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}
	
	public boolean fromJSON(JSONObject obj){
		try {
			this.title = obj.getString("title");
			this.text = obj.getString("text");
			this.date = obj.getString("date");
			this.value = obj.getDouble("value");
			this.rating = obj.getDouble("rating");
			this.location = obj.getDouble("location");
			this.sleepQ = obj.getDouble("sleepQ");
			this.rooms = obj.getDouble("rooms");
			this.cleanliness = obj.getDouble("cleanliness");
			this.service = obj.getDouble("service");
			this.user.setNick(obj.getString("User"));
			this.user.setReviews(obj.getInt("Reviews"));
			this.user.setLocation(obj.getString("Location"));
			this.user.setHotelReviews(obj.getInt("hotelReviews"));
			this.user.setCities(obj.getInt("Cities"));
			this.user.setHelpful(obj.getInt("Helpful"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	public boolean hasRates() {
		if(this.value > 0) return true;
		else if(this.location > 0) return true;
		else if(this.sleepQ > 0) return true;
		else if(this.rooms > 0) return true;
		else if(this.cleanliness > 0) return true;
		else if(this.service > 0) return true;
		else
		return false;
	}
	

}
