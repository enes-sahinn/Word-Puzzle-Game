package wordpuzzle;

public class Cursor {
	private int posX;
	private int posY;
	private int dirX;
	private int dirY;
	
	public Cursor(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public void goRight() {
		this.dirX = 1;
		this.dirY = 0;
	}
	
	public void goLeft() {
		this.dirX = -1;
		this.dirY = 0;
	}
	
	public void goUp() {
		this.dirX = 0;
		this.dirY = -1;
	}
	
	public void goDown() {
		this.dirX = 0;
		this.dirY = 1;
	}
	
	public void stop() {
		this.dirX = 0;
		this.dirY = 0;
	}
	
	public boolean isMoving() {
		return (dirX != 0 || dirY != 0);
	}
	
	public void move(int boardWidth, int boardHeight) {
		if ((this.dirX == 1 && this.posX + 1 >= boardWidth + 1) || (this.dirX == -1 && this.posX - 1 <= 0) || 
			(this.dirY == 1 && this.posY + 1 >= boardHeight + 2) || (this.dirY == -1 && this.posY - 1 <= 1)) {
			stop();
		}
		this.posX += this.dirX; // movement
		this.posY += this.dirY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getDirX() {
		return dirX;
	}

	public void setDirX(int dirX) {
		this.dirX = dirX;
	}

	public int getDirY() {
		return dirY;
	}

	public void setDirY(int dirY) {
		this.dirY = dirY;
	}
}
