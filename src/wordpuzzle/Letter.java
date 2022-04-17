package wordpuzzle;

public class Letter {
	private String letter;
	private Letter down;
	private Word right;
	
	public Letter(String dataToAdd) {
		letter = dataToAdd;
		down = null;
		right = null;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Letter getDown() {
		return down;
	}

	public void setDown(Letter down) {
		this.down = down;
	}

	public Word getRight() {
		return right;
	}

	public void setRight(Word right) {
		this.right = right;
	}

	
}
