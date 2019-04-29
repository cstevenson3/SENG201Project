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
}
