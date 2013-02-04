package DemoClassify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;
import edu.stanford.nlp.stats.Counter;

public class ClassifyTrainer {
	ColumnDataClassifier cdc;
	Classifier<String,String> cl;
	int Tp = 0;
	int Fp = 0;
	int Tn = 0;
	int Fn = 0;
	String  trFichero = "data/THOFUSecond.train";
    String  tRating = "data/THOFUSecond.test";
   
     

	
	public ClassifyTrainer (String prop)
	{
		 cdc = new ColumnDataClassifier(prop);
	}

	public void SetTrainingExamples(String string) {
		
		cl =cdc.makeClassifier(cdc.readTrainingExamples(string));
		
		
	}

	public List<myResult> SetTest(String string) throws IOException {
		  List<myResult> ListRating = new ArrayList<myResult>();
		  int jj=0;   
		 for (String line : ObjectBank.getLineIterator(string)) {
		      Datum<String,String> d = cdc.makeDatumFromLine(line, 0);
		     
		      Counter<String> record = cl.scoresOf(d);
		      String resultado = record.toString();
		      resultado = resultado.substring(4, 9);
		      
		      double i=Double.parseDouble(resultado);
		      String res = Double.toString(i);
		      myResult mr = new myResult(res,"");
		      ListRating.add(mr);
		      if(line.substring(0,4).contains(cl.classOf(d)))
		      {
		    	  if(cl.classOf(d).contains("GD")){
		    	  Tp++;
		    	  } else { Tn++; }
		      }
		      else{
		    	  if(cl.classOf(d).contains("GD")){
		    	  Fp++; }
		    	  else {
		    	  Fn++;
		    	  }
		      }
		   
		  }
		return ListRating;
	}
	public String GetClassify(String item) {
		String resp = "No resp";
		Datum<String,String> d = cdc.makeDatumFromLine(item,0);
		
	      
		resp = (cl.classOf(d)).toString();
		Counter<String> record = cl.scoresOf(d);
	
	    
		if(resp.contains("PR")){
			resp = "NEGATIVE";
		}
		else if(resp.contains("GD")){
			resp = "POSITIVE";
		}
	
		
		return resp;
	}

		
	
}
