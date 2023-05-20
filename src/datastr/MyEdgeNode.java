package datastr;

public class MyEdgeNode {
	private int indexOfElement;
	private float weight;
	private MyEdgeNode next = null;
	
	//TODO pārbaudes set funkcijās
	public int getIndexOfElement() {
		return indexOfElement;
	}
	public void setIndexOfElement(int indexOfElement) {
		this.indexOfElement = indexOfElement;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public MyEdgeNode getNext() {
		return next;
	}
	public void setNext(MyEdgeNode next) {
		this.next = next;
	}
	public MyEdgeNode(int indexOfElement, float weight) {
		setIndexOfElement(indexOfElement);
		setWeight(weight);
	}
	
	public String toString() {
		return "-->["+ indexOfElement + "] " + weight + " km";
	}
	
	
	
	
}
