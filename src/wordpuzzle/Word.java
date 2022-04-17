package wordpuzzle;

public class Word {
	private String word;
	private Word next;
	
	public Word(String dataToAdd) {
		word = dataToAdd;
		next = null;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Word getNext() {
		return next;
	}

	public void setNext(Word next) {
		this.next = next;
	}	
}
