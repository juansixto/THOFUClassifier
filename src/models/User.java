package models;

public class User {
	
	private String nick;
	private String location;
	private String rank;
	private int reviews;
	private int hotelReviews;
	private int cities;
	private int helpful;
	
	public User() {
		this.nick = "";
		this.location = "";
		this.rank = "";
		this.reviews = 0;
		this.hotelReviews = 0;
		this.cities = 0;
		this.helpful = 0;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public int getReviews() {
		return reviews;
	}
	public void setReviews(int reviews) {
		this.reviews = reviews;
	}
	public int getHotelReviews() {
		return hotelReviews;
	}
	public void setHotelReviews(int hotelReviews) {
		this.hotelReviews = hotelReviews;
	}
	public int getCities() {
		return cities;
	}
	public void setCities(int cities) {
		this.cities = cities;
	}
	public int getHelpful() {
		return helpful;
	}
	public void setHelpful(int helpful) {
		this.helpful = helpful;
	}
	public void print() {
		System.out.println("Nick:" + this.nick);
		System.out.println("Location:" + this.location);
		System.out.println("Reviews:" + this.reviews);
		System.out.println("Hotel Reviews:" + this.hotelReviews);
		System.out.println("Cities:" + this.cities);
		System.out.println("Helpful:" + this.helpful);

	}
	
	

}
