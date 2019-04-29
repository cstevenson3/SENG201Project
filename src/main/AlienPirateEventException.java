package main;

public class AlienPirateEventException extends Exception {
	
	private String itemRemoved;
	
	AlienPirateEventException(String itemRemoved){
		super();
		this.itemRemoved = itemRemoved;
	}

	public String getItemRemoved() {
		return itemRemoved;
	}

	public void setItemRemoved(String itemRemoved) {
		this.itemRemoved = itemRemoved;
	}
}
