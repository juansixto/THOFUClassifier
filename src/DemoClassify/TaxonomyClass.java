package DemoClassify;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



public class TaxonomyClass {

	private final static String TAXONOMY_PATH = "data/THOFUTaxonomy.xml";
	private final static String TAXONOMY_NODE = "taxonomy";
	private final static String ITEM_NODE = "Item";
	private final static String FEATURE_NODE = "feature";
	private final static String WORDS_NODE = "featWords";
	
	private final List<List<String>> feats = new ArrayList();


	public TaxonomyClass(){
		this.GenerateTaxonomy();
	}

	public void GenerateTaxonomy(){
		
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(TAXONOMY_PATH);
		try {
			//Se crea el documento a traves del archivo
	        Document document = (Document) builder.build(xmlFile);
	 
	        //Se obtiene la raiz 'tables'
	        Element rootNode = document.getRootElement();
	 
	        //Se obtiene la lista de hijos de la raiz 'Item'
	        List list = rootNode.getChildren( "Item" );
	        for ( int i = 0; i < list.size(); i++ )
	        {
	            Element item = (Element) list.get(i);
	 
	            String nameItem = item.getAttributeValue("name");
	 
	 
	            List list_features = item.getChildren();
	 
	 
	            List<String> myList = new ArrayList();
	            for ( int j = 0; j < list_features.size(); j++ )
	            {
	                Element feature = (Element)list_features.get( j );

	                String Sname = feature.getAttributeValue("name");

	                String Sfeats = feature.getAttributeValue("featWords");
	                
	                StringTokenizer st = new StringTokenizer(Sfeats, ",");
	                while(st.hasMoreTokens()) {

	                	   String Sfeat = st.nextToken();               	 
	                	   
	                	   myList.add(Sfeat);
	                	   }
	               }
	            feats.add(myList);
	        }
	    }catch ( IOException io ) {
	        System.out.println( io.getMessage() );
	    }catch ( JDOMException jdomex ) {
	        System.out.println( jdomex.getMessage() );
	    }
	}
	
	public boolean SearchFeature(String word, int n){
		boolean resp = false;
		List<String> mList = new ArrayList();
		mList = feats.get(n);
		  for ( int j = 0; j < mList.size(); j++ )
          {
			  if(word.contentEquals(mList.get(j))){
				  resp = true;
				  break;
			  }
          }
		
		
		return resp;
	}
	
	public boolean SearchAllFeature(String word){
		boolean resp = false;
		List<String> mList = new ArrayList();
		mList = feats.get(0);
		mList.addAll(feats.get(1));
		mList.addAll(feats.get(2));
		mList.addAll(feats.get(3));
		  for ( int j = 0; j < mList.size(); j++ )
          {
			  if(word.contentEquals(mList.get(j))){
				  resp = true;
				  break;
			  }
          }
		
		
		return resp;
	}
}
