package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hotel {

	private String name;
	private  List<Review> reviews = new ArrayList<>();
	private int rating;
	private int value;
	private int location;
	private int sleepQuality;
	private int rooms;
	private int cleanliness;
	private int service;
	
	public Hotel(String name, List<Review> reviews) {
		super();
		this.name = name;
		this.reviews = reviews;
		this.rating = 0;
		this.value = 0;
		this.location = 0;
		this.sleepQuality = 0;
		this.rooms = 0;
		this.cleanliness = 0;
		this.service = 0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getSleepQuality() {
		return sleepQuality;
	}
	public void setSleepQuality(int sleepQuality) {
		this.sleepQuality = sleepQuality;
	}
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	public int getCleanliness() {
		return cleanliness;
	}
	public void setCleanliness(int cleanliness) {
		this.cleanliness = cleanliness;
	}
	public int getService() {
		return service;
	}
	public void setService(int service) {
		this.service = service;
	}
	public int[][] getRatingsPercentages(){
		double[] rating = new double[this.reviews.size()];
		double[] values = new double[this.reviews.size()];
		double[] location = new double[this.reviews.size()];
		double[] sleepQ = new double[this.reviews.size()];
		double[] rooms = new double[this.reviews.size()];
		double[] clearliness = new double[this.reviews.size()];
		double[] service = new double[this.reviews.size()];
		for(int i = 0; i < this.reviews.size();i++){
			rating[i] = this.reviews.get(i).getRating();
			values[i] = this.reviews.get(i).getValue();
			location[i] = this.reviews.get(i).getLocation();
			sleepQ[i] = this.reviews.get(i).getSleepQ();
			rooms[i] = this.reviews.get(i).getRooms();
			clearliness[i] = this.reviews.get(i).getCleanliness();
			service[i] = this.reviews.get(i).getService();
		}
		
		int[][] ratings = new int[7][]; 
		System.out.println("===================================");
		System.out.println("Reviews Number: " + this.reviews.size());
		System.out.println("Rating Numbers:");
		ratings[0] = printRatings(rating);
		System.out.println("!!!!"+printRatings(rating));
		System.out.println("Value Numbers:");
		ratings[1] = printRatings(values);
		System.out.println("Location Numbers:");
		ratings[2] = printRatings(location);
		System.out.println("SleepQ Numbers:");
		ratings[3] = printRatings(sleepQ);
		System.out.println("Rooms Numbers:");
		ratings[4] = printRatings(rooms);
		System.out.println("Clearliness Numbers:");
		ratings[5] = printRatings(clearliness);
		System.out.println("Service Numbers:");
		ratings[6] = printRatings(service);
	
		
		System.out.println("===================================");
		System.out.println("Ratings;"+ratings);
		return ratings;
	}
	
	public int[] printRatings(double[] items){
		Arrays.sort(items);
		int[] classif = {0,0,0,0,0};
		for(int i = 0; i < items.length; i++){
			switch((int)items[i]) {
			case 1:
				classif[0]++;
				break;
			case 2:
				classif[1]++;
				break;
			case 3:
				classif[2]++;
				break;
			case 4:
				classif[3]++;
				break;
			case 5:
				classif[4]++;
				break;
				
			}		
		}
		System.out.println("1 Star: " + classif[0] + graphicPercent(classif[0], items.length));
		System.out.println("2 Star: " + classif[1] + graphicPercent(classif[1], items.length));
		System.out.println("3 Star: " + classif[2] + graphicPercent(classif[2], items.length));
		System.out.println("4 Star: " + classif[3] + graphicPercent(classif[3], items.length));
		System.out.println("5 Star: " + classif[4] + graphicPercent(classif[4], items.length));
		System.out.println("###"+classif[0]);
		return classif;
	}
	
	public String graphicPercent(int c, int t){
		String resp = " |";
		if(c<100) resp = "  |";
		if(c<10) resp = "   |";
		int perc = (c*100/t);
		for(int i = 0; i < perc; i++){
			resp = resp+"=";
		}
		return resp;
		
	}
}
