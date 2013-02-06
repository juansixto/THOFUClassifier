package Corpus;

public class Document {
	
	private final String classification;
	private final String text;
	private final String rating;
	
	public Document(final String classification, final String text, final String rating) {
		this.classification = classification;
		this.rating = rating;
		this.text = text;
	}
	
	public String getClassification() {
		return this.classification;
	}
	
	public String getText() {
		return this.text;
	}
	public String getRating() {
		return this.rating;
	}
}
