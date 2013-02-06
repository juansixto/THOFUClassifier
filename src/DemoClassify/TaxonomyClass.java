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
	private final static String ITEM = "Item";
	private final static String FEAT_WORDS = "featWords";
	private final static String SEPARATOR = ",";

	private final List<List<String>> feats = new ArrayList<List<String>>();


	public TaxonomyClass(){
		this.generateTaxonomy();
	}

	public void generateTaxonomy(){

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(TAXONOMY_PATH);
		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren(ITEM);
			for ( int i = 0; i < list.size(); i++ )
			{
				Element item = (Element) list.get(i);
				List<Element> list_features = item.getChildren();


				List<String> myList = new ArrayList<String>();
				for ( int j = 0; j < list_features.size(); j++ )
				{
					Element feature = (Element)list_features.get( j );
					String Sfeats = feature.getAttributeValue(FEAT_WORDS);

					StringTokenizer st = new StringTokenizer(Sfeats, SEPARATOR);
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

	public boolean searchFeature(String word, int n){
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

}
