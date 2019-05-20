package main;

import java.io.Serializable;
import java.util.HashMap;

public class Inventory implements Serializable{
	private HashMap<String, InventoryItem> items;
	private int money;
	
	public Inventory(){
		money = 0;
		items = new HashMap<String, InventoryItem>();
	}
	
	public Inventory(int money){
		this.money = money;
		items = new HashMap<String, InventoryItem>();
	}
	
	public String getDescription(){
		String result = "Inventory:\nMoney: " + money + "\n";
		for(InventoryItem item:items.values()){
			result += item.getDescription();
		}
		return result;
	}

	public int getMoney() {
		return money;
	}

	public boolean decreaseMoney(int amount) {
		if(amount <= money){
			money -= amount;
			return true;
		}else{
			return false;
		}
	}

	public void addItem(InventoryItem inventoryItem) {
		String name = inventoryItem.getName();
		int quantity = inventoryItem.getQuantity();
		if(items.containsKey(name)){
			items.get(name).increaseQuantity(quantity);
		}else{
			items.put(name, inventoryItem);
		}
	}

	public String removeRandomItem() {
		if(items.size()!=0){
			int index = ((int) (Math.random() * 1000)) % items.size();
			String itemName = (String) items.keySet().toArray()[index];
			items.get(itemName).setQuantity(items.get(itemName).getQuantity() - 1);
			return itemName;
		}
		return "nothing";
	}
	
	public HashMap<String, MedicalItem> getMedicalItems() {
		HashMap<String, MedicalItem> result = new HashMap<String, MedicalItem>();
		for(InventoryItem item:items.values()) {
			if(item instanceof MedicalItem) {
				result.put(item.getName(), (MedicalItem) item);
			}
		}
		return result;
	}
	
	public HashMap<String, FoodItem> getFoodItems() {
		HashMap<String, FoodItem> result = new HashMap<String, FoodItem>();
		for(InventoryItem item:items.values()) {
			if(item instanceof FoodItem) {
				result.put(item.getName(), (FoodItem) item);
			}
		}
		return result;
	}
	
	public HashMap<String, InventoryItem> getItems() {
		return items;
	}

	public InventoryItem consume(String itemName, int quantity) throws ItemNotFoundException, InsufficientQuantityException{
		if(!items.containsKey(itemName)) {
			throw new ItemNotFoundException();
		}
		try{
			items.get(itemName).consume(quantity);
			InventoryItem newItem = items.get(itemName).clone();
			newItem.setQuantity(quantity);
			if(items.get(itemName).getQuantity() == 0) {
				items.remove(itemName);
			}
			return newItem;
		}catch (InsufficientQuantityException e) {
			throw e;
		}
	}
}
