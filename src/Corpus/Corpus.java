package Corpus;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class Corpus implements Collection<Document>, Iterable<Document>{
	
	private final Collection<Document> documents;
	
	public Corpus(Collection<Document> documents) {
		final Vector<Document> tmp = new Vector<Document>();
		tmp.addAll(documents);
		this.documents = Collections.unmodifiableCollection(tmp);
	}

	@Override
	public boolean add(Document arg0) {
		return this.documents.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends Document> arg0) {
		return this.documents.addAll(arg0);
	}

	@Override
	public void clear() {
		this.documents.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return this.documents.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return this.documents.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return this.documents.isEmpty();
	}

	@Override
	public Iterator<Document> iterator() {
		return this.documents.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		return this.documents.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return this.documents.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return this.documents.removeAll(arg0);
	}

	@Override
	public int size() {
		return this.documents.size();
	}

	@Override
	public Object[] toArray() {
		return this.documents.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return this.documents.toArray(arg0);
	}
	
}
