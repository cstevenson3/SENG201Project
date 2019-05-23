package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Disease;
import main.MedicalItem;

public class MedicalItemTest {

	MedicalItem testItem;
	@Before
	public void setUp() throws Exception {
		testItem = new MedicalItem("Space Plague Cure");
		testItem.setQuantity(10);
	}


	@Test
	public void testGetType() {
		assertEquals(testItem.getType(), MedicalItem.getTypeString());
	}

	@Test
	public void testCuresDisease() {
		Disease spacePlague = new Disease("Space Plague");
		assertEquals(testItem.cures(spacePlague), true);
		MedicalItem panadol = new MedicalItem("Panadol");
		testItem.setQuantity(10);
		assertEquals(panadol.cures(spacePlague), false);
	}

	@Test
	public void testCuresString() {
		assertEquals(testItem.cures("Space Plague"), true);
		MedicalItem panadol = new MedicalItem("Panadol");
		testItem.setQuantity(10);
		assertEquals(panadol.cures("Space Plague"), false);
	}

	@Test
	public void testGetHealthIncrease() {
		testItem.setHealthIncrease(12);
		assertEquals(testItem.getHealthIncrease(), 12);
	}

	@Test
	public void testSetHealthIncrease() {
		testItem.setHealthIncrease(12);
		assertEquals(testItem.getHealthIncrease(), 12);
	}

	@Test
	public void testClone() {
		MedicalItem clone = testItem.clone();
		assertEquals(testItem.getHealthIncrease(), clone.getHealthIncrease());
	}

	@Test
	public void testExists() {
		assertEquals(MedicalItem.exists("Panadol"), true);
		assertEquals(MedicalItem.exists("doesntexist"), false);
	}

}
