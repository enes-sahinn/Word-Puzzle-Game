package wordpuzzle;

public class Player {
	private String name;	
	private int score;

	public Player() {
		score = 0;
	}
	
	public void addScore() {
		this.score += score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
