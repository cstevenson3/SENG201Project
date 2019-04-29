package test;

import static org.junit.Assert.*;
import main.FoodItem;
import main.InventoryItem;

import org.junit.Test;

public class FoodItemTest {

	@Test
	public void testClone() {
		FoodItem item1 = new FoodItem("Burger");
		FoodItem item2 = item1.clone();
		System.out.println(item2);
		InventoryItem item3 = (InventoryItem)item1;
		FoodItem item4 = (FoodItem)item3;
		System.out.println(item4);
		InventoryItem item5 = item3.clone();
		FoodItem item6 = (FoodItem)item5;
		System.out.println(item6);
	}

}
