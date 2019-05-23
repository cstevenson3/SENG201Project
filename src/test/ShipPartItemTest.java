package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.ShipPartItem;

public class ShipPartItemTest {

	ShipPartItem testItem;
	@Before
	public void setUp() throws Exception {
		testItem = new ShipPartItem("ShipPart");
	}

	@Test
	public void testGetType() {
		assertEquals(testItem.getType(), ShipPartItem.getTypeString());
	}
}
