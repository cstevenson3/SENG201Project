package main;

/**
 * To be thrown when alien pirates steal an item, to alert the CLI/GUI
 * @author Cameron Stevenson
 *
 */
public class AlienPirateEventException extends Exception {
	
	/**
	 * The item which was stolen
	 */
	private String itemRemoved;
	
	/**
	 * Init
	 * @param itemRemoved The item which was stolen
	 */
	AlienPirateEventException(String itemRemoved){
		super();
		this.itemRemoved = itemRemoved;
	}

	/**
	 * Get the item which was stolen
	 * @return The item stolen
	 */
	public String getItemRemoved() {
		return itemRemoved;
	}

	/**
	 * Set the item which was stolen
	 * @param itemRemoved The item stolen
	 */
	public void setItemRemoved(String itemRemoved) {
		this.itemRemoved = itemRemoved;
	}
}
