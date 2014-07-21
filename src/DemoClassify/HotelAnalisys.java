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

import Corpus.Corpus;
import Corpus.TBODCorpusLoader;
import Corpus.TBODCorpusLoader.LabelSet;
import models.Review;
import models.Hotel;
import Corpus.CorpusLoaderException;
import models.ThofuBD;




public class HotelAnalisys {
	
	private static File file;
	private static List<Review> reviewList = new ArrayList<>();
	private static CorpusTestGenerator myCTG;
	private static ClassifyTrainer ct;
	private static ThofuBD db;
	public static int id = 2;
	
	public static int[] loadFile(String corpus) throws IOException, JSONException{
		file = new File(corpus);
		System.out.println(file.toString());
		FileReader fr = new FileReader (file);
		BufferedReader br = new BufferedReader(fr);
		String linea;
		int[] Counter = {0,0,0,0,0,0,0,0};
		while((linea=br.readLine())!=null){
			JSONObject obj = new JSONObject(linea);	
			Review r = new Review();
			r.fromJSON(obj);
			reviewList.add(r);
			if (r.getText().length() > 0) {
				String sentence =myCTG.generateSentence(r.getText());
				String resp = ct.getClassify(sentence);
				System.out.println(resp);
				String roomSentence = myCTG.roomSentences.toString();
				sentence =myCTG.generateSentence(roomSentence);
				resp = ct.getClassify(sentence);
				String roomResp = resp.toString();
				String serviceSentence = myCTG.serviceSentences.toString();
				sentence =myCTG.generateSentence(serviceSentence);
				resp = ct.getClassify(sentence);
				String serviceResp = resp.toString();
				String staffSentence = myCTG.staffSentences.toString();
				sentence =myCTG.generateSentence(staffSentence);
				resp = ct.getClassify(sentence);
				String staffResp = resp.toString();
				String facilitiesSentence = myCTG.facilitiesSentences.toString();
				sentence =myCTG.generateSentence(facilitiesSentence);
				resp = ct.getClassify(sentence);
				String facilitiesResp = resp.toString();
				if (roomResp == "NEGATIVE"){
					Counter[0] += 1;
				}else{
					Counter[1] += 1;
				}
				if (serviceResp == "NEGATIVE"){
					Counter[2] += 1;
				}else{
					Counter[3] += 1;
				}
				if (staffResp == "NEGATIVE"){
					Counter[4] += 1;
				}else{
					Counter[5] += 1;
				}
				if (facilitiesResp == "NEGATIVE"){
					Counter[6] += 1;
				}else{
					Counter[7] += 1;
				}
				System.out.println("Room: "+roomResp);
				System.out.println("Service: "+serviceResp);
				System.out.println("Staff: "+staffResp);
				System.out.println("Facilities: "+facilitiesResp);

			}
			
		}
		return Counter;
	}
	
	public static void main(String[] args) throws IOException, JSONException, CorpusLoaderException {
		final TBODCorpusLoader loader = new TBODCorpusLoader(LabelSet.TWO_LABEL);
		final Corpus corpus = loader.load();
		//Generate the corpus (.train y .test)
		myCTG = new CorpusTestGenerator();
		myCTG.generate(corpus);
		//Create the trainer
		ct = new ClassifyTrainer(ClassifyTrainer.PROP_FILE_PATH);
		ct.setTrainingExamples(CorpusTestGenerator.TRAIN_FILE_PATH);
		ct.setTest(CorpusTestGenerator.TEST_FILE_PATH);
		
		
		
		System.out.println("Analizando ficheros");
		int[] counts = loadFile("data/Hotel.json");
		System.out.println("Valores:" +counts[0]+","+counts[1]+","+counts[2]+","+counts[3]+","+counts[4]+","+counts[5]+","+counts[6]+","+counts[7]);
		Hotel myHotel = new Hotel("Hotel Example", reviewList);
		myHotel.getRatingsPercentages();
		System.out.println(myHotel.getRatingsPercentages());
		int[][] ratings = myHotel.getRatingsPercentages();
		System.out.println("AA"+ratings[0][4]);
		int excelent = ratings[0][4];
		System.out.println(excelent);
		int verygood = ratings[0][3];
		System.out.println(verygood);
		int good = ratings[0][2];
		System.out.println(good);
		int bad = ratings[0][1];
		System.out.println(bad);
		int verybad = ratings[0][0];
		System.out.println(verybad);
		int size = myHotel.getReviews().size();
		db = new ThofuBD();
		db.insertClassifier("rooms",id,counts[0],counts[1]);
		db.insertClassifier("service",id,counts[0],counts[1]);
		db.insertClassifier("staff",id,counts[0],counts[1]);
		db.insertClassifier("facilities",id,counts[0],counts[1]);
		
		db.insertVotes("generalvotes",id, ratings[1]);
		db.insertVotes("locationvotes",id, ratings[2]);
		db.insertVotes("sleepqualityvotes",id, ratings[3]);
		db.insertVotes("roomsvotes",id, ratings[4]);
		db.insertVotes("servicevotes",id, ratings[6]);
		
	}
	

}