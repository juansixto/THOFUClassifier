package DemoClassify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;
import edu.stanford.nlp.stats.Counter;

public class ClassifyTrainer {
	
	public final static String PROP_FILE_PATH = ("data/THOFUDemo.prop");


	ColumnDataClassifier cdc;
	Classifier<String,String> cl;
	int Tp = 0;
	int Fp = 0;
	int Tn = 0;
	int Fn = 0;

	public ClassifyTrainer (String prop)
	{
		this.cdc = new ColumnDataClassifier(prop);
	}

	public void setTrainingExamples(String string) {
		this.cl =this.cdc.makeClassifier(this.cdc.readTrainingExamples(string));
	}

	public List<myResult> setTest(String string) throws IOException {
		List<myResult> ListRating = new ArrayList<myResult>();

		for (String line : ObjectBank.getLineIterator(string)) {
			Datum<String,String> d = this.cdc.makeDatumFromLine(line, 0);

			Counter<String> record = this.cl.scoresOf(d);
			String resultado = record.toString();
			resultado = resultado.substring(4, 9);

			double i=Double.parseDouble(resultado);
			String res = Double.toString(i);
			myResult mr = new myResult(res,"");
			ListRating.add(mr);
			if(line.substring(0,4).contains(this.cl.classOf(d)))
			{
				if(this.cl.classOf(d).contains("GD")){
					this.Tp++;
				} else { 
					this.Tn++; 
				}
			}
			else{
				if(this.cl.classOf(d).contains("GD")){
					this.Fp++; }
				else {
					this.Fn++;
				}
			}

		}
		return ListRating;
	}
	public String getClassify(String item) {
		String resp = "No resp";
		Datum<String,String> d = this.cdc.makeDatumFromLine(item,0);

		resp = (this.cl.classOf(d)).toString();

		if(resp.contains("PR")){
			resp = "NEGATIVE";
		}
		else if(resp.contains("GD")){
			resp = "POSITIVE";
		}


		return resp;
	}



}
