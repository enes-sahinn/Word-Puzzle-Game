package wordpuzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class WordPuzzleGame {
	
	public static int consoleWidth = 120;
	public static int consoleHeight = 31;
	public static int boardWidth = 15;
	public static int boardHeight = 15; 
	
	private static Console cn;
	private String[][] puzzleBoard;
	private String[][] controlBoard;
	
	public KeyListener keylis;
	public int keypr; 
	public int keyCode;
	// Colors
	public static final Color Green = new Color(0, 255, 0);
	public static final Color White = new Color(255, 255, 255);
	public static final Color Black = new Color(0, 0, 0);
	public static final Color Yellow = new Color(255, 255, 0);
	public static final Color Red = new Color(255, 0, 0);
	public static final Color Cyan = new Color(0, 255, 255);
	public static final Color SkyBlue = new Color(135,206,235);  

	private File file;
	private Scanner scn;
	
	public SingleLinkedList sll = new SingleLinkedList();
	public DoubleLinkedList dll = new DoubleLinkedList(); // High Score Table
	public MultiLinkedList mll = new MultiLinkedList();
	
	Player player1 = new Player();
	Player player2 = new Player();
	int turn; // 1-Player1, 2-Player2.
	Cursor cursor = new Cursor(1, 3);
	
	char inputChar;
	int direction;
	int wordcount;
	int x, y; // To save surcor position.
	boolean quickEndGame = true;
	
	public WordPuzzleGame() throws Exception {
		cn = Enigma.getConsole("Word-Puzzle Game", consoleWidth, consoleHeight, 25, 50);
		direction = 0;
		wordcount = 0;
		turn = 1;
	}
	
	public void createPuzzleBoard() throws FileNotFoundException {
		puzzleBoard = new String[boardHeight][boardWidth];
		file = new File("puzzle.txt");
		scn = new Scanner(file);
		int lineCount = 0;
		while (scn.hasNextLine()) {
			String[] currentLine = scn.nextLine().split("\t");
			for (int i = 0; i < currentLine.length; i++) {
				puzzleBoard[lineCount][i] = currentLine[i];
			}
			lineCount++;
		} scn.close();
	}
	
	public void createControlBoard() throws FileNotFoundException {
		controlBoard = new String[boardHeight][boardWidth];
		file = new File("solution.txt");
		scn = new Scanner(file);
		int lineCount = 0;
		while (scn.hasNextLine()) {
			String[] currentLine = scn.nextLine().split("\t");
			for (int i = 0; i < currentLine.length; i++) {
				controlBoard[lineCount][i] = currentLine[i];
			}
			lineCount++;
		} scn.close();
	}
	
	public void readWord_ListFile() throws FileNotFoundException {		 
		file = new File("word_list.txt");
		scn = new Scanner(file, "UTF8");
		while (scn.hasNextLine()) {
			sll.add(scn.nextLine());
		}
		scn.close();
	}
	
	public void sllToMll() {	
		if (sll.getHead() == null) {
			System.out.println("Single linked list is empty!");
		} 
		else {
			Node temp = sll.getHead();
			while (temp != null) {
				mll.addData(temp.getData());
				temp = temp.getNext();
			}
		}
	}
	
	public void createHighScoreTable() throws FileNotFoundException {
		file = new File("high_score_table.txt");
		scn = new Scanner(file);
		while (scn.hasNextLine()) {
			dll.add(scn.nextLine());
		}
		scn.close();
	}
	
	public void signAsCompleted(String word) {
		Node temp = sll.getHead();
		while (temp != null && !temp.getData().equalsIgnoreCase(word)) {
			temp = temp.getNext();
		}
		if (temp != null) {
			temp.setIsCompleted("X");
		}
	}
	
	public void key() {
		keylis = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (keypr ==  0) {keypr = 1;}
				keyCode = e.getKeyCode();
			}
			public void keyReleased(KeyEvent e) {}
		};
		cn.getTextWindow().addKeyListener(keylis);
	}
	
	public int lockDirecion() {  // 0 - no direction, 1 - down, 2 - right.
		if (cursor.getDirY() == 1) {
			direction = 1;
		}
		else if (cursor.getDirX() == 1) {
			direction = 2;
		}
		return direction;
	}
	
	public void add1Point() {
		cn.setTextAttributes(new TextAttributes(Cyan, Black));
		if (turn == 1) {
			player1.setScore(player1.getScore() + 1);
			x = cursor.getPosX();
			y = cursor.getPosY();
			cn.getTextWindow().setCursorPosition(0, 18);
			cn.getWriter().print("Player1 gets 1 point.");
			cn.getTextWindow().setCursorPosition(x, y);
		} else {
			player2.setScore(player2.getScore() + 1);
			x = cursor.getPosX();
			y = cursor.getPosY();
			cn.getTextWindow().setCursorPosition(0, 18);
			cn.getWriter().print("Player2 gets 1 point.");
			cn.getTextWindow().setCursorPosition(x, y);
		}	
	}
	
	public void add10Point() {
		if (turn == 1) {
			player1.setScore(player1.getScore() + 9);
		} else {
			player2.setScore(player2.getScore() + 9);
		}
		wordcount++;
	}
	
	public void addExtra10Point(String completedWord) {
		x = cursor.getPosX();
		y = cursor.getPosY();
		cn.getTextWindow().setCursorPosition(0, 18);
		String word = completedWord.toUpperCase().replace('Ý', 'I');
		if (turn == 1) {
			cn.getWriter().println("The word \"" + word + "\" is placed correctly. PLAYER1 gets 10 points.");
		} else {
			cn.getWriter().println("The word \"" + word + "\" is placed correctly. PLAYER2 gets 10 points.");
		}
		cn.getWriter().println("What is the meaning of \"" + word + "\" in Turkish? Please enter your option.\n");
		Random rnd = new Random();
		int number = rnd.nextInt(3) + 1;
		if (number == 1) {
			cn.getWriter().print("        A-) " + sll.TurkishMean(completedWord) + "    B-) " + sll.randomTurkishMean() + "    C-) " + sll.randomTurkishMean());
		}
		else if (number == 2) {
			cn.getWriter().print("        A-) " + sll.randomTurkishMean() + "    B-) " + sll.TurkishMean(completedWord) + "    C-) " + sll.randomTurkishMean());
		}
		else {
			cn.getWriter().print("        A-) " + sll.randomTurkishMean() + "    B-) " + sll.randomTurkishMean() + "    C-) " + sll.TurkishMean(completedWord));
		}
		cn.getWriter().print("\n\nAnswer: ");
		cn.setTextAttributes(new TextAttributes(Yellow, Black));
		scn = new Scanner(System.in);
		String answer = scn.next();
		cn.setTextAttributes(new TextAttributes(Cyan, Black));
		boolean isAnswerTrue = false;
		if (answer.equalsIgnoreCase("A") && number == 1) {
			isAnswerTrue = true;
		}
		else if (answer.equalsIgnoreCase("B") && number == 2) {
			isAnswerTrue = true;
		}
		else if (answer.equalsIgnoreCase("C") && number == 3) {
			isAnswerTrue = true;
		}
		if (isAnswerTrue) {
			if (turn == 1) {
				player1.setScore(player1.getScore() + 10);
			} else {
				player2.setScore(player2.getScore() + 10);
			}
		} // For cleaning console.
		cn.getTextWindow().setCursorPosition(0, 18);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 85; j++) {
				cn.getTextWindow().setCursorPosition(j, 18 + i);
				cn.getWriter().print(" ");
			}
		}
		cn.getTextWindow().setCursorPosition(0, 18);
		if (isAnswerTrue && turn == 1) {
			cn.getWriter().print("Answer is correct. Player1 gets extra 10 points.");
		}
		else if (isAnswerTrue && turn == 2) {
			cn.getWriter().print("Answer is correct. Player2 gets extra 10 points.");
		}
		else if (!isAnswerTrue && turn == 1) {
			cn.getWriter().print("Answer is wrong. Player1 do not get extra 10 points.");
		}
		else if (!isAnswerTrue && turn == 2) {
			cn.getWriter().print("Answer is wrong. Player2 do not get extra 10 points.");
		}
		cn.getTextWindow().setCursorPosition(x, y);		
	}
	
	public void keyEventHandler() {
		if (keypr == 1) {
			x = cursor.getPosX();
			y = cursor.getPosY();
			cn.getTextWindow().setCursorPosition(0, 18);
			for (int i = 0; i < 60; i++) {
				cn.getWriter().print(" ");
			}
			cn.getTextWindow().setCursorPosition(x, y);
			
			if 		(keyCode == KeyEvent.VK_UP && direction == 0)    {cursor.goUp();}
			else if (keyCode == KeyEvent.VK_LEFT && direction == 0)  {cursor.goLeft();}
			else if (keyCode == KeyEvent.VK_DOWN && direction == 0)  {cursor.goDown();} 
			else if (keyCode == KeyEvent.VK_RIGHT && direction == 0) {cursor.goRight();}
			else if (keyCode == KeyEvent.VK_SPACE)                   {quickEndGame = false;} // To end game fast.
			else if ((keyCode >= 65 && keyCode <= 90) || (keyCode >= 97 && keyCode <= 122)){
				if (keyCode == 73) {
					keyCode = 105;  // Convert "ý" to "i".
				}
				try {  // bandwidth kelimesine özel koþul (bulunduðu kare 1 ve üstündeki kare harf ise)
					if (cursor.getPosX() == 1 && puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("1") && !puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("1") && !puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("0")) {
						inputChar = (char)keyCode;
						puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1] = String.valueOf(inputChar);
						if (!puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("1") && !puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("0") && direction != 2) {
							cursor.goDown();
							lockDirecion();
							
							if (mll.isWordExist(takeWordToCheck())) {  // girdiði harf doðrumu
								add1Point(); // 1 puan almasý için
								if (puzzleBoard[cursor.getPosY() - 1][cursor.getPosX() - 1].equals("0") && isWordCompleted()) { // kelime doðru bitmiþse
										add10Point();   // +10 puan
										signAsCompleted(takeWordToCheck()); // X olarak iþaretle
										mll.delete(takeWordToCheck()); // mll den kaldýr 
										addExtra10Point(takeWordToCheck());  // ekstra 10 puan için soru sor.
								}								
							} else {
								if (puzzleBoard[cursor.getPosY() - 1][cursor.getPosX() - 1].equals("0")) { // girdiði harf yanlýþsa ve bu kelimenin son harfiyse
									isWordCompleted(); // yanlýþ olan harfleri sil
								}
								if (turn == 1) {turn = 2;}
								else           {turn = 1;}
								direction = 0;
							}
						}
					} // disconnect kelimesine özel koþul (bulunduðu kare 1 ve solundaki kare harf ise)
					else if (cursor.getPosY() == 2 && puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("1") && !puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("1") && !puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("0")) {	
						inputChar = (char)keyCode;
						puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1] = String.valueOf(inputChar);
						if (!puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("1") && !puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("0") && direction != 1) {
							cursor.goRight();
							lockDirecion();
					
							if (mll.isWordExist(takeWordToCheck())) {  
								add1Point();
								if (puzzleBoard[cursor.getPosY() - 2][cursor.getPosX()].equals("0") && isWordCompleted()) {
										add10Point();
										signAsCompleted(takeWordToCheck());
										mll.delete(takeWordToCheck());
										addExtra10Point(takeWordToCheck());	
								}
							} else {
								if (puzzleBoard[cursor.getPosY() - 2][cursor.getPosX()].equals("0")) {
									isWordCompleted();	
								}	
								if (turn == 1) {turn = 2;}
								else           {turn = 1;}
								direction = 0;
							}
						}
					} // Bulunduðu kare 1 ve üstündeki veya solundaki kare harf ise koþulu.					
					else if ((puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("1") && ((!puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("1") && !puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("0")) || 
							(!puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("1") && !puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("0"))))) {
						
						inputChar = (char)keyCode;
						puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1] = String.valueOf(inputChar);
						// Üstündeki kare harf ve yön saða kitlenmediyse koþulu 
						if (!puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("1") && !puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("0") && direction != 2 && cursor.getPosX() != 10 && cursor.getPosX() != 11 && cursor.getPosX() != 12) {
							cursor.goDown();
							lockDirecion();
							
							if (mll.isWordExist(takeWordToCheck())) {  
								add1Point();
								if (puzzleBoard[cursor.getPosY() - 1][cursor.getPosX() - 1].equals("0") && isWordCompleted()) {
										add10Point();
										signAsCompleted(takeWordToCheck());
										mll.delete(takeWordToCheck());
										addExtra10Point(takeWordToCheck());
								}
							} else {
								if (puzzleBoard[cursor.getPosY() - 1][cursor.getPosX() - 1].equals("0")) {
									isWordCompleted();
								}
								if (turn == 1) {turn = 2;}
								else           {turn = 1;}
								direction = 0;
							}
						} // Solundaki kare harf ve yön aþaðý kitlenmediyse koþulu
						else if (!puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("1") && !puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("0") && direction != 1) {
							cursor.goRight();
							lockDirecion();
							
							if (mll.isWordExist(takeWordToCheck())) {  
								add1Point();
								if (cursor.getPosX() == 15 && (puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("T") && isWordCompleted()) ){
										String word = takeWordToCheck() + "T";
										add10Point();
										signAsCompleted(word);
										mll.delete(word);
										addExtra10Point(word);
								  
								} 
								else if (puzzleBoard[cursor.getPosY() - 2][cursor.getPosX()].equals("0") && isWordCompleted()) {
										add10Point();
										signAsCompleted(takeWordToCheck());
										mll.delete(takeWordToCheck());
										addExtra10Point(takeWordToCheck());
								}
							} else {
								if (cursor.getPosX() == 15) {
									isWordCompleted();		
								} 
								else if (puzzleBoard[cursor.getPosY() - 2][cursor.getPosX()].equals("0")) {
									isWordCompleted();
								}	
								if (turn == 1) {turn = 2;}
								else           {turn = 1;}
								direction = 0;
							}
						}	
					} // Yön aþaðý ve altýndaki kare "0" ise koþulu  
					if (direction == 1 && puzzleBoard[cursor.getPosY() - 1][cursor.getPosX() - 1].equals("0")) {
						direction = 0;
					} // Exception almamak için yön saða ve cursor en saðda ise koþulu(alttakinden önce olmasý önemli yoksa exception alýr).
					else if (cursor.getPosX() == 15 && direction == 2) {
						direction = 0;
					} // Yön saða ve saðýndaki "0" ise koþulu
					else if (direction == 2 && puzzleBoard[cursor.getPosY() - 2][cursor.getPosX()].equals("0")) {
						direction = 0;
					}	
				} catch (ArrayIndexOutOfBoundsException e) {} // To do not take exception while checking.		
			}
			cursor.move(boardWidth, boardHeight); 
			if (!puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("1") && !puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("0")) {
				direction = 0;  // sadece 1 durumda exception aldýðý için direction ý sýfýrlamýyordu onun için.
			}
			cursor.stop();
			keypr = 0;
		}
	}
	
	public String takeWordToCheck() {
		String word = "";
		if (direction == 1) {
			if (cursor.getPosX() == 1) {
				word+= "b";
				while (!puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("b")) {
					cursor.goUp();
					cursor.move(boardWidth, boardHeight);
				}
			}
			else if (cursor.getPosX() == 13) {
				word+= "t";
				while (!puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("t")) {
					cursor.goUp();
					cursor.move(boardWidth, boardHeight);
				}
			}
			else {
				while (!puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("0")) {
					cursor.goUp();
					cursor.move(boardWidth, boardHeight);
				}
			}	
			while (!puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("0") && !puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("1")) {
				word += puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1];
				cursor.goDown();
				cursor.move(boardWidth, boardHeight);
			} // cursorý önceki yerine getirmek için
			if (!puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("0") && !puzzleBoard[cursor.getPosY() - 3][cursor.getPosX() - 1].equals("1")) {
				cursor.goUp();
				cursor.move(boardWidth, boardHeight);
				cursor.goDown();
			}	
		}
		else if (direction == 2) {
			while (!puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("0")) {
				cursor.goLeft();
				cursor.move(boardWidth, boardHeight);
			} // Bulunduðu kare harf ve posX 15 deðilse                                                                                                       sonsuz döngüyü engellemek için    
			while (!puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("0") && !puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("1") && cursor.getPosX() != 15) {
				word += puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1];
				cursor.goRight();
				cursor.move(boardWidth, boardHeight);
			} // cursorý önceki yerine getirmek için
			if (cursor.getPosX() != 15) {
				if (!puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("0") && !puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 2].equals("1")) {
					cursor.goLeft();
					cursor.move(boardWidth, boardHeight);
					cursor.goRight();
				}	
			}
		}
		return word.toLowerCase();		
	}
	
	public boolean isWordCompleted() {
		boolean isWordCompleted = true;
		for (int i = 0; i < puzzleBoard.length; i++) {
			for (int j = 0; j < puzzleBoard[i].length; j++) {
				if (!puzzleBoard[i][j].equals("1") && !puzzleBoard[i][j].equals("0")) {
					if (!puzzleBoard[i][j].equalsIgnoreCase(controlBoard[i][j])) {
						isWordCompleted = false;
						puzzleBoard[i][j] = "1"; // Delete letter if it is wrong letter after completing.
						if (turn == 1) {
							player1.setScore(player1.getScore() - 2);
						} else {
							player2.setScore(player2.getScore() - 2);
						}
					}
				}
			}
		}
		return isWordCompleted;
	}
	
	public boolean isGameOver() {
		boolean isGameOver = true;
		for (int i = 0; i < puzzleBoard.length; i++) {
			for (int j = 0; j < puzzleBoard[i].length; j++) {
				if (!puzzleBoard[i][j].equalsIgnoreCase(controlBoard[i][j])) {
					isGameOver = false;
					break;
				}
			}
		}
		return isGameOver;
	}
	
	public void endOfGameScreen() {
		cn.setTextAttributes(new TextAttributes(Cyan, Black));
		cn.getTextWindow().setCursorPosition(0, 18);
		for (int i = 0; i < 60; i++) {
			cn.getWriter().print(" ");
		}
		cn.getTextWindow().setCursorPosition(0, 18);
		if (player1.getScore() > player2.getScore()) {
			cn.getWriter().println("Player1 won the game.");
		} 
		else if (player1.getScore() < player2.getScore()) {
			cn.getWriter().println("Player2 won the game.");
		} else {
			cn.getWriter().println("The game is a draw.");
		}
		cn.getWriter().println("Uncompleted Words -> Setup Branch Working ...");

		cn.getTextWindow().setCursorPosition(8, 21);
		cn.getWriter().print("PLAYER1");
		cn.getTextWindow().setCursorPosition(5, 22);
		cn.getWriter().print("-------------");
		cn.getTextWindow().setCursorPosition(5, 24);
		cn.getWriter().print("SCORE: " + player1.getScore());
		cn.getTextWindow().setCursorPosition(5, 23);
		cn.getWriter().print("NAME: ");
		cn.getTextWindow().setCursorPosition(38, 21);
		cn.getWriter().print("PLAYER2");
		cn.getTextWindow().setCursorPosition(35, 22);
		cn.getWriter().print("-------------");
		cn.getTextWindow().setCursorPosition(35, 24);
		cn.getWriter().print("SCORE: " + player2.getScore());
		cn.getTextWindow().setCursorPosition(35, 23);
		cn.getWriter().print("NAME: ");
		cn.getTextWindow().setCursorPosition(11, 23);
		cn.setTextAttributes(new TextAttributes(Yellow, Black));
		if (player1.getScore() >= dll.tenthPlayerScore()) {
			scn = new Scanner(System.in);
			String player1name = scn.nextLine();
			dll.add(player1name + ";" + player1.getScore());
		} else {
			cn.getWriter().print("Insufficent Score");
		}
		cn.getTextWindow().setCursorPosition(41, 23);
		if (player2.getScore() >= dll.tenthPlayerScore()) {
			scn = new Scanner(System.in);
			String player2name = scn.nextLine();
			dll.add(player2name + ";" + player2.getScore());
		} else {
			cn.getWriter().print("Insufficent Score");
		}
		// High Score Table
		cn.getTextWindow().setCursorPosition(82, 7);
		cn.getWriter().print("HIGH SCORE TABLE");
		cn.getTextWindow().setCursorPosition(82, 8);
		cn.getWriter().print("****************");
		int lineCount = 9;
		Competitor temp = dll.getHead();
		for (int i = 1; i <= 10; i++) {
			cn.getTextWindow().setCursorPosition(82, lineCount);
			cn.getWriter().print(i + "-) " + temp.getName() + " -> " + temp.getScore());
			lineCount++;
			temp = temp.getNext();
		}
		// Game Over
		cn.setTextAttributes(new TextAttributes(Red, Black));
		for (int i = 0; i <= 4; i+=4) {
			for (int j = 0; j < 15; j++) {
				cn.getTextWindow().setCursorPosition(70 + j, 20 + i);
				cn.getWriter().print("-");
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j <= 14; j+=14) {
				cn.getTextWindow().setCursorPosition(70 + j, 21 + i);
				cn.getWriter().print("|");
			}
		}
		cn.setTextAttributes(new TextAttributes(White, Red));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 13; j++) {
				cn.getTextWindow().setCursorPosition(71 + j, 21 + i);
				cn.getWriter().print(" ");
			}
		}
		cn.getTextWindow().setCursorPosition(73, 22);
		cn.getWriter().print("GAME OVER");
	}
	
	public void draw() throws InterruptedException {
		// draw numbers 
		cn.setTextAttributes(new TextAttributes(Red, Black));
		for (int i = 0; i < boardWidth; i++) {
			cn.getTextWindow().setCursorPosition(1 + i, 1);
			cn.getWriter().print(i % 10);
		}
		for (int i = 0; i < boardHeight; i++) {
			cn.getTextWindow().setCursorPosition(0, 2 + i);
			cn.getWriter().print(i % 10);
		}
		// draw puzzle board
		for (int i = 0; i < puzzleBoard.length; i++) {
			for (int j = 0; j < puzzleBoard[i].length; j++) {
				cn.getTextWindow().setCursorPosition(1 + j, 2 + i);
				if (puzzleBoard[i][j].equals("1")) {
					cn.setTextAttributes(new TextAttributes(White, Black));
					cn.getWriter().print(" ");
				}
				else if (puzzleBoard[i][j].equals("0")) {
					cn.setTextAttributes(new TextAttributes(White, Green));
					cn.getWriter().print(" ");
				}
				else {
					cn.setTextAttributes(new TextAttributes(White, Black));
					if (puzzleBoard[i][j].equals("i")) {
						cn.getWriter().print("I");
					} else {
						cn.getWriter().print(puzzleBoard[i][j].toUpperCase());
					}	
				}		
			}
		}	
		cn.setTextAttributes(new TextAttributes(Cyan, Black));
		cn.getTextWindow().setCursorPosition(0, 0);
		cn.getWriter().print("WORD = " + wordcount);
		cn.getTextWindow().setCursorPosition(99, 0);
		cn.getWriter().print("    ");
		cn.getTextWindow().setCursorPosition(86, 0);
		cn.getWriter().print("    ");
		cn.getTextWindow().setCursorPosition(66, 0);
		cn.getWriter().print("SCORE-->   PLAYER1: " + player1.getScore());
		cn.getTextWindow().setCursorPosition(90, 0);
		cn.getWriter().print("PLAYER2: " + player2.getScore());
		
		cn.setTextAttributes(new TextAttributes(Red, Black));
		if (turn == 1) {
			cn.getTextWindow().setCursorPosition(77, 0);
			cn.getWriter().print("PLAYER1: " + player1.getScore());
			cn.getTextWindow().setCursorPosition(82, 1);
			cn.getWriter().print("^");
			cn.getTextWindow().setCursorPosition(82, 2);
			cn.getWriter().print("|");
			cn.getTextWindow().setCursorPosition(79, 3);
			cn.getWriter().print("CURRENT");
			cn.getTextWindow().setCursorPosition(79, 4);
			cn.getWriter().print("PLAYER");

			cn.getTextWindow().setCursorPosition(95, 1);
			cn.getWriter().print(" ");
			cn.getTextWindow().setCursorPosition(95, 2);
			cn.getWriter().print(" ");
			cn.getTextWindow().setCursorPosition(92, 3);
			cn.getWriter().print("       ");
			cn.getTextWindow().setCursorPosition(92, 4);
			cn.getWriter().print("      ");
		}
		else {
			cn.getTextWindow().setCursorPosition(90, 0);
			cn.getWriter().print("PLAYER2: " + player2.getScore());
			cn.getTextWindow().setCursorPosition(95, 1);
			cn.getWriter().print("^");
			cn.getTextWindow().setCursorPosition(95, 2);
			cn.getWriter().print("|");
			cn.getTextWindow().setCursorPosition(92, 3);
			cn.getWriter().print("CURRENT");
			cn.getTextWindow().setCursorPosition(92, 4);
			cn.getWriter().print("PLAYER");
			
			cn.getTextWindow().setCursorPosition(82, 1);
			cn.getWriter().print(" ");
			cn.getTextWindow().setCursorPosition(82, 2);
			cn.getWriter().print(" ");
			cn.getTextWindow().setCursorPosition(79, 3);
			cn.getWriter().print("       ");
			cn.getTextWindow().setCursorPosition(79, 4);
			cn.getWriter().print("      ");
		}
		cn.setTextAttributes(new TextAttributes(SkyBlue, Black));
		for (int i = 0; i <= 15; i+=15) {
			for (int j = 0; j < 58; j++) {
				cn.getTextWindow().setCursorPosition(19 + j, 1 + i);
				cn.getWriter().print("-");
			}
		}
		for (int i = 0; i <= 60; i+=60) {
			for (int j = 0; j < 14; j++) {
				cn.getTextWindow().setCursorPosition(18 + i, 2 + j);
				cn.getWriter().print("|");
			}
		}
		for (int i = 0; i <= 60; i+=60) {
			for (int j = 0; j <= 15; j+=15) {
				cn.getTextWindow().setCursorPosition(18 + i, 1 + j);
				cn.getWriter().print("+");
			}
		} // draw word list
		cn.setTextAttributes(new TextAttributes(Red, Black));
		cn.getTextWindow().setCursorPosition(43, 1);
		cn.getWriter().print("WORD");
		cn.getTextWindow().setCursorPosition(48, 1);
		cn.getWriter().print("LIST");
		cn.setTextAttributes(new TextAttributes(SkyBlue, Black));
		Node temp = sll.getHead();
		String word = "";
		for (int i = 0; i <= 45; i+=15) {
			for (int j = 0; j < 12; j++) {
				cn.getTextWindow().setCursorPosition(19 + i, 3 + j);
				word = temp.getData().toUpperCase();
				word = word.replace('Ý', 'I');
				cn.getWriter().print("[" );
				cn.setTextAttributes(new TextAttributes(Red, Black));
				cn.getWriter().print(temp.getIsCompleted());
				cn.setTextAttributes(new TextAttributes(SkyBlue, Black));
				cn.getWriter().print("]" + word);
				temp = temp.getNext();	
				if (!temp.getData().equals("protect") && !temp.getData().equals("prototype") && !temp.getData().equals("window") && !temp.getData().equals("replace") && !temp.getData().equals("texture")) {
					temp = temp.getNext();
				}	
				if (temp.getData().equals("configurator") || temp.getData().equals("cryptography") || temp.getData().equals("indentation") || temp.getData().equals("intervention") || temp.getData().equals("interprocess")) {
					temp = temp.getNext();
				}
			}
		}
		// draw "X"
		if (puzzleBoard[cursor.getPosY() - 2][cursor.getPosX() - 1].equals("0")) {
			cn.setTextAttributes(new TextAttributes(Yellow, Green));
			cn.getTextWindow().setCursorPosition(cursor.getPosX(), cursor.getPosY());
			cn.getWriter().print("X");
		}
		else {
			cn.setTextAttributes(new TextAttributes(Yellow, Black));
			cn.getTextWindow().setCursorPosition(cursor.getPosX(), cursor.getPosY());
			cn.getWriter().print("X");
		}
		cn.setTextAttributes(new TextAttributes(White, Black));
		Thread.sleep(150);
	}
	
	public void run() throws FileNotFoundException, InterruptedException {
		createPuzzleBoard();
		createControlBoard();
		readWord_ListFile();
		createHighScoreTable();
		sllToMll();
		key();
		while (!isGameOver() && quickEndGame) {
			keyEventHandler();
			draw();
		}
		endOfGameScreen();
	}
}
