package main;

import java.io.Serializable;

public abstract class InventoryItem implements Cloneable, Serializable{

	private String name;
	private int quantity;
	private int price;
	private static final String[] itemTypes = {"FoodItem","MedicalItem","ShipPartItem"};
	
	public InventoryItem(){
		this.name = "unnamed";
		this.quantity = 0;
		this.price = 0;
	}
	
	public InventoryItem(String name, int quantity, int price){
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = (name == null) ? "unnamed" : name;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = Utilities.clamp(quantity, 0, Integer.MAX_VALUE);
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = Utilities.clamp(price, 0, Integer.MAX_VALUE);
	}
	
	public InventoryItem clone(){
		InventoryItem clone = (InventoryItem)Utilities.clone(this);
		return clone;
	}

	public String getDescription() {
		return name + ": quantity (" + quantity + ") - price per item (" + price + ")";
	}
	
	public abstract String getAttributeDescription();

	public void increaseQuantity(int quantity) {
		this.quantity += quantity;
	}

	public static InventoryItem findAndLoadItem(String itemName) {
		for(String itemType : itemTypes){
			switch(itemType){
			case "FoodItem":
				if (FoodItem.exists(itemName)){
					return (InventoryItem) (new FoodItem(itemName));
				}
				break;
			case "MedicalItem":
				if (MedicalItem.exists(itemName)){
					return (InventoryItem) (new MedicalItem(itemName));
				}
				break;
			case "ShipPartItem":
				return (InventoryItem) (new ShipPartItem(itemName));
			}
		}
		return null;
	}
	
	public static String[] getItemTypes() {
		return itemTypes;
	}

	public void consume(int quantity) throws InsufficientQuantityException{
		if(this.quantity < quantity) {
			throw new InsufficientQuantityException();
		}
		this.quantity -= quantity;
	}

	public abstract String getType();
}
