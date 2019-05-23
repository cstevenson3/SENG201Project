package main;

/**
 * ShipPartItem represents an InventoryItem which counts towards the ship parts which need to be collected
 * @author Cameron Stevenson
 *
 */
public class ShipPartItem extends InventoryItem{
	
	/**
	 * Init ShipPartItem with the name of the part
	 * @param name The name of the part
	 */
	public ShipPartItem(String name){
		this.setName(name);
	}
	
	/**
	 * Get a textual description
	 * @return A textual description
	 */
	@Override
	public String getAttributeDescription() {
		return "A ship part";
	}
	
	/**
	 * Get the type of this InventoryItem
	 * @return "ShipPartItem"
	 */
	@Override
	public String getType() {
		return "ShipPartItem";
	}

	/**
	 * Get the type ShipPartitem
	 * @return "ShipPartItem"
	 */
	public static String getTypeString() {
		return "ShipPartItem";
	}
}
