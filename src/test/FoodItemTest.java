package test;

import static org.junit.Assert.*;

import org.junit.Before;

import main.FoodItem;
import main.InventoryItem;

import org.junit.Test;

public class FoodItemTest {

	FoodItem testItem;
	@Before
	public void setUp() {
		testItem = new FoodItem("Burger");
	}
	
	@Test
	public void testGetHungerDecrease(){
		testItem.setHungerDecrease(12);
		assertEquals(testItem.getHungerDecrease(),12);
	}
	
	@Test
	public void testSetHungerDecrease(){
		testItem.setHungerDecrease(12);
		assertEquals(testItem.getHungerDecrease(),12);
	}
	
	@Test
	public void testExists() {
		assertEquals(FoodItem.exists("Burger"), true);
		assertEquals(FoodItem.exists("doesntexist"), false);
	}
	
	@Test
	public void testGetType() {
		assertEquals(testItem.getType(), FoodItem.getTypeString());
	}
	
	@Test
	public void testClone() {
		FoodItem item1 = new FoodItem("Burger");
		FoodItem item2 = item1.clone();
		InventoryItem item3 = (InventoryItem)item1;
		assertEquals(item3 instanceof FoodItem, true);
		FoodItem item4 = (FoodItem)item3;
		assertEquals(item3 instanceof FoodItem, true);
		InventoryItem item5 = item3.clone();
		FoodItem item6 = (FoodItem)item5;
		assertEquals(item6 instanceof FoodItem, true);
		
		assertEquals(item4.getName(), item1.getName());
		assertEquals(item4.getQuantity(), item1.getQuantity());
	}

}
