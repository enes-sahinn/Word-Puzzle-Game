package wordpuzzle;

public class Node {
	private String data;
	private String turkishMean;
	private String isCompleted;
	private Node next;
	
	public Node(String dataToAdd) {
		String[] array = dataToAdd.split(",");
		data = array[0];
		turkishMean = array[1];
		isCompleted = " ";
		next = null;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public String getTurkishMean() {
		return turkishMean;
	}

	public void setTurkishMean(String turkishMean) {
		this.turkishMean = turkishMean;
	}

	public String getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}
}
