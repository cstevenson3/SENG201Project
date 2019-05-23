package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.FoodItem;
import main.InsufficientQuantityException;
import main.InventoryItem;

public class InventoryItemTest {

	InventoryItem testItem;
	@Before
	public void setUp() throws Exception {
		testItem = new FoodItem("Burger");
		testItem.setQuantity(10);
	}

	@Test
	public void testGetName() {
		assertEquals(testItem.getName(), "Burger");
	}

	@Test
	public void testSetName() {
		testItem.setName("NotBurger");
		assertEquals(testItem.getName(), "NotBurger");
	}

	@Test
	public void testGetQuantity() {
		assertEquals(testItem.getQuantity(), 10);
	}

	@Test
	public void testSetQuantity() {
		testItem.setQuantity(12);
		assertEquals(testItem.getQuantity(), 12);
	}

	@Test
	public void testGetPrice() {
		testItem.setPrice(12);
		assertEquals(testItem.getPrice(), 12);
	}

	@Test
	public void testSetPrice() {
		testItem.setPrice(12);
		assertEquals(testItem.getPrice(), 12);
	}

	@Test
	public void testClone() {
		InventoryItem cloned = testItem.clone();
		assertEquals(testItem.getName(), cloned.getName());
		assertEquals(testItem.getPrice(), cloned.getPrice());
		assertEquals(testItem.getQuantity(), cloned.getQuantity());
	}


	@Test
	public void testIncreaseQuantity() {
		testItem.increaseQuantity(2);
		assertEquals(testItem.getQuantity(), 12);
	}

	@Test
	public void testFindAndLoadItem() {
		InventoryItem item = InventoryItem.findAndLoadItem("Burger");
		assertEquals(item instanceof FoodItem, true);
	}

	@Test
	public void testConsume() {
		try {
			testItem.consume(5);
		} catch (InsufficientQuantityException e) {
			fail();
		}
		try {
			testItem.consume(5);
		} catch (InsufficientQuantityException e) {
			fail();
		}
		try {
			testItem.consume(5);
			fail();
		} catch (InsufficientQuantityException e) {
		}
	}

	@Test
	public void testGetType() {
		assertEquals(testItem.getType(), FoodItem.getTypeString());
	}
}
