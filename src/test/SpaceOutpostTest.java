package test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import main.Inventory;
import main.InventoryItem;
import main.SpaceOutpost;

public class SpaceOutpostTest {

	SpaceOutpost testOutpost;
	@Before
	public void setUp() throws Exception {
		testOutpost = new SpaceOutpost("Halo");
	}

	@Test
	public void testGetItemsAvailable() {
		HashMap<String, InventoryItem> items = testOutpost.getItemsAvailable();
		assertEquals(items.keySet().contains("Panadol"), true);
	}

	@Test
	public void testCanPurchase() {
		assertEquals(testOutpost.canPurchase("Panadol", 1), true);
		assertEquals(testOutpost.canPurchase("Panadol", 10000), false);
		assertEquals(testOutpost.canPurchase("doesntexist", 1), false);
	}

	@Test
	public void testPurchaseItem() {
		Inventory inventory = new Inventory(1000);
		assertEquals(testOutpost.purchaseItem("Panadol", 1, inventory), true);
		assertEquals(inventory.getItems().get("Panadol").getQuantity(), 1);
		inventory.setMoney(0);
		assertEquals(testOutpost.purchaseItem("Panadol", 1, inventory), false);
		assertEquals(inventory.getItems().get("Panadol").getQuantity(), 1);
	}

	@Test
	public void testGetName() {
		assertEquals(testOutpost.getName(), "Halo");
	}

}
