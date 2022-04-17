package wordpuzzle;

public class Competitor {
	private String name;
	private int Score;
	private Competitor next;
	private Competitor prev;
		
	public Competitor(String dataToAdd) {
		String[] array = dataToAdd.split(";");
		name = array[0];
		Score = Integer.parseInt(array[1]);
		prev = null;
		next = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

	public Competitor getNext() {
		return next;
	}

	public void setNext(Competitor next) {
		this.next = next;
	}

	public Competitor getPrev() {
		return prev;
	}

	public void setPrev(Competitor prev) {
		this.prev = prev;
	}
}

