package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.FoodItem;
import main.InventoryItem;
import main.Planet;

public class PlanetTest {

	Planet testPlanet;
	@Before
	public void setUp() throws Exception {
		testPlanet = new Planet("Xandar");
	}

	@Test
	public void testSearch() {
		testPlanet.setItemSearchOdds(0);
		assertEquals(testPlanet.search(), null);
		testPlanet.setItemSearchOdds(1);
		assertEquals(testPlanet.search() instanceof InventoryItem, true);
	}

	@Test
	public void testGetName() {
		assertEquals(testPlanet.getName(), "Xandar");
	}

	@Test
	public void testSetName() {
		testPlanet.setName("XandarTwo");
		assertEquals(testPlanet.getName(), "XandarTwo");
	}

	@Test
	public void testGetItem() {
		assertEquals(testPlanet.getItem() instanceof InventoryItem, true);
	}

	@Test
	public void testSetItem() {
		FoodItem burger = new FoodItem("Burger");
		testPlanet.setItem(burger);
		assertEquals(testPlanet.getItem(), burger);
	}

	@Test
	public void testGetItemSearchOdds() {
		testPlanet.setItemSearchOdds((float) 0.5);
		assertEquals(testPlanet.getItemSearchOdds() < 0.51 && testPlanet.getItemSearchOdds() > 0.49, true);
	}

	@Test
	public void testSetItemSearchOdds() {
		testPlanet.setItemSearchOdds((float) 0.5);
		assertEquals(testPlanet.getItemSearchOdds() < 0.51 && testPlanet.getItemSearchOdds() > 0.49, true);
	}

}
