package DemoClassify;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;
import models.Review;
import models.Hotel;



public class HotelAnalisys {
	
	private static File file;
	private static List<Review> reviewList = new ArrayList<>();
	
	public static void loadFile(String corpus) throws IOException, JSONException{
		file = new File(corpus);
		System.out.println(file.toString());
		FileReader fr = new FileReader (file);
		BufferedReader br = new BufferedReader(fr);
		String linea;
		while((linea=br.readLine())!=null){
			JSONObject obj = new JSONObject(linea);
			Review r = new Review();
			r.fromJSON(obj);
			reviewList.add(r);
			
		}
	}
	
	public static void main(String[] args) throws IOException, JSONException {
		System.out.println("Analizando ficheros");
		loadFile("data/Hotel.json");
		Hotel myHotel = new Hotel("Hotel Example", reviewList);
		myHotel.getRatingsPercentages();
		
	}
	

}