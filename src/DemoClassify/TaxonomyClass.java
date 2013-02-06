package DemoClassify;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



public class TaxonomyClass {

	private final static String TAXONOMY_PATH = "data/THOFUTaxonomy.xml";
	
/*	NOT USED, DELETE?
 
	private final static String TAXONOMY_NODE = "taxonomy";
	private final static String ITEM_NODE = "Item";
	private final static String FEATURE_NODE = "feature";
	private final static String WORDS_NODE = "featWords";
*/
	
	private final List<List<String>> feats = new ArrayList<List<String>>();


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
	        List<Element> list = rootNode.getChildren( "Item" );
	        for ( int i = 0; i < list.size(); i++ )
	        {
	            Element item = (Element) list.get(i);
	 
	            //String nameItem = item.getAttributeValue("name"); NOT USED, DELETE?
	 
	 
	            List<Element> list_features = item.getChildren();
	 
	 
	            List<String> myList = new ArrayList<String>();
	            for ( int j = 0; j < list_features.size(); j++ )
	            {
	                Element feature = (Element)list_features.get( j );

	                //String Sname = feature.getAttributeValue("name");  NOT USED, DELETE?

	                String Sfeats = feature.getAttributeValue("featWords");
	                
	                StringTokenizer st = new StringTokenizer(Sfeats, ",");
	                while(st.hasMoreTokens()) {

	                	   String Sfeat = st.nextToken();               	 
	                	   
	                	   myList.add(Sfeat);
	                	   }
	               }
	            this.feats.add(myList);
	        }
	    }catch ( IOException io ) {
	        System.out.println( io.getMessage() );
	    }catch ( JDOMException jdomex ) {
	        System.out.println( jdomex.getMessage() );
	    }
	}
	
	public boolean SearchFeature(String word, int n){
		boolean resp = false;
		List<String> mList = new ArrayList<String>();
		mList = this.feats.get(n);
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
		List<String> mList = new ArrayList<String>();
		mList = this.feats.get(0);
		mList.addAll(this.feats.get(1));
		mList.addAll(this.feats.get(2));
		mList.addAll(this.feats.get(3));
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
