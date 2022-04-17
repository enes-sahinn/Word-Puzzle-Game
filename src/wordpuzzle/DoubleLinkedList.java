package wordpuzzle;

public class DoubleLinkedList {
	private Competitor head;													
	private Competitor tail;
	
	public DoubleLinkedList() {
		head= null;
		tail = null;
	}
	
	public void add(String dataToAdd) {
		Competitor newCompetitor = new Competitor(dataToAdd);
		if (head == null && tail == null) {
			head = newCompetitor;
			tail = newCompetitor;		
		}
		else if (newCompetitor.getScore() > head.getScore()) {
			newCompetitor.setNext(head);
			head.setPrev(newCompetitor);
			head = newCompetitor;
		}
		else {
			Competitor temp = head;	      
			while (temp.getNext() != null && newCompetitor.getScore() < temp.getNext().getScore()) {
				temp = temp.getNext();
			}
			newCompetitor.setPrev(temp);
			newCompetitor.setNext(temp.getNext());
			if (temp.getNext() != null) {
				temp.getNext().setPrev(newCompetitor);
			}
			else {
				tail = newCompetitor;
			}
			temp.setNext(newCompetitor);
		}		
	}
	
	public int size() {
		int counter = 0;
		Competitor temp = head;
		while (temp != null) {
			counter++;
			temp = temp.getNext();
		}
		return counter;
	}
	
	public int tenthPlayerScore() {
		Competitor temp = head;
		for (int i = 0; i < 9; i++) {
			temp = temp.getNext();
		}
		return temp.getScore();
	}
	
	public Competitor getHead() {
		return head;
	}

	public void setHead(Competitor head) {
		this.head = head;
	}

	public Competitor getTail() {
		return tail;
	}

	public void setTail(Competitor tail) {
		this.tail = tail;
	}
	
	
}
