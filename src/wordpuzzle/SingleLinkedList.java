package wordpuzzle;

import java.util.Random;

public class SingleLinkedList {
	private Node head;													
	
	public SingleLinkedList() {
		head= null;
	}
	
	public void add(String dataToAdd) {
		if (head == null) {
			Node newNode = new Node(dataToAdd);
			head = newNode;		
		}
		else if (dataToAdd.compareTo(head.getData()) < 0) {
			Node newNode = new Node(dataToAdd);
			newNode.setNext(head);
			head = newNode;
		}
		else {
			Node newNode = new Node(dataToAdd);
			Node temp = head;
			while (temp.getNext() != null && dataToAdd.compareTo(temp.getNext().getData()) > 0) {
				temp = temp.getNext();
			}
			newNode.setNext(temp.getNext());
			temp.setNext(newNode);
		}		
	}
	
	public int size() {
		int counter = 0;
		Node temp = head;
		while (temp != null) {
			counter++;
			temp = temp.getNext();
		}
		return counter;
	}
	
	public void display1() {
		System.out.println("From head to tail:");
		Node temp = head;
		while (temp != null) {
			System.out.print(temp.getData() + " ");
			System.out.println();
			temp = temp.getNext();
		}
	}
	
	public String TurkishMean(String word) {
		String turkishMean = "";
		Node temp = head;
		while (temp != null) {
			if (temp.getData().equalsIgnoreCase(word)) {
				turkishMean = temp.getTurkishMean();
				break;
			}
			temp = temp.getNext();
		}
		return turkishMean;
	}
	
	public String randomTurkishMean() {
		Random rnd = new Random();
		int number = rnd.nextInt(size()) + 1;
		Node temp = head;
		for (int i = 0; i < number - 1; i++) {
			temp = temp.getNext();
		}
		return temp.getTurkishMean();
	}

	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}	
}
