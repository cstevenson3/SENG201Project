package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import main.FoodItem;
import main.InsufficientQuantityException;
import main.Inventory;
import main.InventoryItem;
import main.ItemNotFoundException;
import main.MedicalItem;
import main.NotConsumableException;
import main.ShipPartItem;

public class InventoryTest {

	Inventory testInventory;
	ArrayList<String> itemNames;
	@Before
	public void setUp() throws Exception {
		testInventory = new Inventory(10);
		FoodItem burger = new FoodItem("Burger");
		burger.setQuantity(1);
		MedicalItem panadol = new MedicalItem("Panadol");
		panadol.setQuantity(1);
		ShipPartItem shipPart = new ShipPartItem("ShipPart");
		shipPart.setQuantity(1);
		testInventory.addItem(burger);
		testInventory.addItem(panadol);
		testInventory.addItem(shipPart);
		itemNames = new ArrayList<String>();
		itemNames.add("Burger");
		itemNames.add("Panadol");
		itemNames.add("ShipPart");
	}

	@Test
	public void testGetMoney() {
		assertEquals(testInventory.getMoney(), 10);
	}

	@Test
	public void testDecreaseMoney() {
		assertEquals(testInventory.decreaseMoney(5), true);
		assertEquals(testInventory.getMoney(), 5);
		assertEquals(testInventory.decreaseMoney(10), false);
		assertEquals(testInventory.getMoney(), 5);
	}

	@Test
	public void testRemoveRandomItem() {
		for(int i = 0; i < 10; i++) {
			String itemName = testInventory.removeRandomItem();
			assertEquals(itemName == null || itemNames.contains(itemName), true);
		}
	}

	@Test
	public void testGetMedicalItems() {
		HashMap<String, MedicalItem> medicalItems = testInventory.getMedicalItems();
		assertEquals(medicalItems.size(), 1);
		assertEquals(medicalItems.keySet().toArray()[0], "Panadol");
	}

	@Test
	public void testGetFoodItems() {
		HashMap<String, FoodItem> foodItems = testInventory.getFoodItems();
		assertEquals(foodItems.size(), 1);
		assertEquals(foodItems.keySet().toArray()[0], "Burger");
	}

	@Test
	public void testGetItems() {
		HashMap<String, InventoryItem> items = testInventory.getItems();
		for(String itemName : itemNames) {
			assertEquals(items.containsKey(itemName), true);
		}
	}

	@Test
	public void testConsume() {
		try {
			testInventory.consume("Burger", 1);
		} catch (ItemNotFoundException e) {
			fail();
			e.printStackTrace();
		} catch (InsufficientQuantityException e) {
			fail();
		} catch (NotConsumableException e) {
			fail();
		}
		
		try {
			testInventory.consume("Burger", 1);
			fail();
		} catch (ItemNotFoundException e) {
			
		} catch (InsufficientQuantityException e) {
			fail();
		} catch (NotConsumableException e) {
			fail();
		}
		
		try {
			testInventory.consume("doesntexist", 1);
			fail();
		} catch (ItemNotFoundException e) {
			
		} catch (InsufficientQuantityException e) {
			fail();
		} catch (NotConsumableException e) {
			fail();
		}
		
		try {
			testInventory.consume("Panadol", 3);
		} catch (ItemNotFoundException e) {
			fail();
		} catch (InsufficientQuantityException e) {
		} catch (NotConsumableException e) {
			fail();
		}
		
		try {
			testInventory.consume("ShipPart", 1);
			fail();
		} catch (ItemNotFoundException e) {
			fail();
		} catch (InsufficientQuantityException e) {
			fail();
		} catch (NotConsumableException e) {
		}
	}

}
