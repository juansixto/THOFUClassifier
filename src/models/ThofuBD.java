package models;
import java.sql.*;

public class ThofuBD {

	private static String Ruta = "data/thofu.db";
	
	public static void main( String args[] )
	  {
	    Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+Ruta);
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	  }
	
	public void insertHotel(int id,String name,String slug,String address,String image,int total_reviews, int excellent_reviews, int very_good_reviews,int average_reviews,int poor_reviews,int very_poor_reviews)
	{
		System.out.println("Hola!");
		name = "'"+name+"'";
		slug = "'"+slug+"'";
		address = "'"+address+"'";
		image = "'"+image+"'";
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+Ruta);
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "INSERT INTO thofu_website_hotel (id,name,slug,address,image,total_reviews, excellent_reviews, very_good_reviews,average_reviews,poor_reviews,very_poor_reviews ) " +
	                   "VALUES ("+id+","+name+","+slug+","+address+","+image+","+total_reviews+","+excellent_reviews+","+ very_good_reviews+","+average_reviews+","+poor_reviews+","+very_poor_reviews+");"; 
	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
	  }
	public void insertClearliness(int id, int[] rats)
	{
		
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+Ruta);
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	      int sum_tot = rats[0]+rats[1]+rats[2]+rats[3]+rats[4];
	      stmt = c.createStatement();
	      String sql = "INSERT INTO thofu_website_clearlinessvotes (id,hotel_id, excellent_reviews, very_good_reviews,average_reviews,poor_reviews,very_poor_reviews, total) " +
	                   "VALUES ("+id+","+id+","+rats[4]+","+rats[3]+","+rats[2]+","+rats[1]+","+rats[0]+","+sum_tot+");"; 
	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
	  }
	
		public void insertVotes(String t, int id, int[] rats)
		{
			
		    Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:"+Ruta);
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");
		      int sum_tot = rats[0]+rats[1]+rats[2]+rats[3]+rats[4];
		      stmt = c.createStatement();
		      String sql = "INSERT INTO thofu_website_"+t+" (id,hotel_id, excellent_reviews, very_good_reviews,average_reviews,poor_reviews,very_poor_reviews, total) " +
		                   "VALUES ("+id+","+id+","+rats[4]+","+rats[3]+","+rats[2]+","+rats[1]+","+rats[0]+","+sum_tot+");"; 
		      stmt.executeUpdate(sql);
		
		      stmt.close();
		      c.commit();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Records created successfully");
		  }
		public void insertClassifier(String t, int id, int neg, int pos){
			Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:"+Ruta);
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");
		      stmt = c.createStatement();
		      String sql = "INSERT INTO thofu_website_"+t+"classifieranalysis (id,hotel_id, positive_votes, negative_votes) " +
		                   "VALUES ("+id+","+id+","+pos+","+neg+");"; 
		      stmt.executeUpdate(sql);
		
		      stmt.close();
		      c.commit();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Records created successfully");
		}
}
