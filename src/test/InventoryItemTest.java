package test;

import static org.junit.Assert.*;
import main.InventoryItem;
import main.FoodItem;

import org.junit.Test;

public class InventoryItemTest {

	@Test
	public void testFindAndLoadItem() {
		InventoryItem item = InventoryItem.findAndLoadItem("Burger");
		System.out.println(item.getAttributeDescription());
		assertEquals(item instanceof FoodItem, true);
	}

}
