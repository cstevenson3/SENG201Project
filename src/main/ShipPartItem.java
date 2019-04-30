package main;

public class ShipPartItem extends InventoryItem{
	
	public ShipPartItem(String name){
		this.setName(name);
	}
	
	@Override
	public String getAttributeDescription() {
		return "A ship part";
	}
	
	@Override
	public String getType() {
		return "ShipPartItem";
	}
}
