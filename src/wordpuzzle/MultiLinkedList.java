package wordpuzzle;

public class MultiLinkedList {
	private Letter head;

	public MultiLinkedList() {
		head = null;
	}
	
	public void addData(String dataToAdd) {
		if (head == null) {
			Letter newNode = new Letter(dataToAdd.substring(0, 1));  // Substring method for taking first letter of word.
			head = newNode;
			Word newNode2 = new Word(dataToAdd);
			head.setRight(newNode2);
		}
		else {
			boolean isFoundLetter = false;  // Boolean to check the existence of the letters
			Letter temp = head;
			while (temp != null && !isFoundLetter) {
				if (temp.getLetter().equals(dataToAdd.substring(0, 1))) {
					isFoundLetter = true;  										
					break;
				}			
					temp = temp.getDown();
			}
			if (isFoundLetter) {
				Word newWord = new Word(dataToAdd);
				if (temp.getRight() == null) {                    
					temp.setRight(newWord);
				} 
				else {
					Word temp2 = temp.getRight();
					while (temp2.getNext() != null && dataToAdd.compareTo(temp2.getNext().getWord()) > 0) {
						temp2 = temp2.getNext();   
					}
					if (temp2.getNext() != null) {
						newWord.setNext(temp2.getNext());    
						temp2.setNext(newWord);		
					}
					else {
						temp2.setNext(newWord);
					}	 			
				}
			}
			else {    // Adding letters 
				Letter newLetter = new Letter(dataToAdd.substring(0, 1));
				temp = head;
				while (temp.getDown() != null && dataToAdd.substring(0, 1).compareTo(temp.getDown().getLetter()) > 0) {
					temp = temp.getDown();
				}
				if (temp.getDown() != null) {
					newLetter.setDown(temp.getDown());
					temp.setDown(newLetter);
				}
				else {
					temp.setDown(newLetter);
				}
				Word newWord = new Word(dataToAdd);                 
				newLetter.setRight(newWord);
			}		
		}
	}
	
	public void display() {
		if (head == null) {
			System.out.println("Linked list is empty!");
		} else {
			Letter temp = head;
			while (temp != null) {
				System.out.print(temp.getLetter() + " --> ");
				if (temp.getRight() == null) {
					System.out.println();
				} else {
					Word temp2 = temp.getRight();
					while (temp2 != null) {
						System.out.print(temp2.getWord() + ", ");
						temp2 = temp2.getNext();
					}
					System.out.println();
				}
				temp = temp.getDown();
			}
		}
	}
	
	public void delete(String wordToDelete) {
		if (head == null) {
			System.out.println("Linked list is empty!");
		}
		else {
			Letter temp = head;
			while (temp != null) {
				if (temp.getLetter().equalsIgnoreCase(wordToDelete.substring(0, 1))) {
					if (temp.getRight() != null) {
						if (temp.getRight().getWord().equalsIgnoreCase(wordToDelete)) {
							temp.setRight(temp.getRight().getNext());
							break;
						}
						else {
							Word temp2 = temp.getRight();
							Word prev = null;
							while (temp2 != null) {
								if (temp2.getWord().equalsIgnoreCase(wordToDelete)) {
									prev.setNext(temp2.getNext());
									temp2 = prev;
									break;
								}
								prev = temp2;
								temp2 = temp2.getNext();
							}						
						}
					}
				}
				temp = temp.getDown();
			}
		}
	}
	
	public boolean isWordExist(String word) {
		boolean isWordExist = false;
		if (head == null) {
			System.out.println("Linked list is empty!");
		}
		Letter temp = head;
		while (temp != null) {
			if (temp.getLetter().equals(word.substring(0, 1))) {
				Word temp2 = temp.getRight();
				while (temp2 != null) {
					if (temp2.getWord().length() >= word.length()) {
						if (temp2.getWord().substring(0, word.length()).equals(word)) {
							isWordExist = true;
							break;
						}
					}
					temp2 = temp2.getNext();
				}
			}
			temp = temp.getDown();
		}
		return isWordExist;
	}

	public int letterSize() {
		int count = 0;
		Letter temp = head;
		while (temp != null) {
			count++;
			temp = temp.getDown();
		}
		return count;
	}

	public Letter getHead() {
		return head;
	}

	public void setHead(Letter head) {
		this.head = head;
	}	
}
