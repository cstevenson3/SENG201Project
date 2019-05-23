package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShipTest {

	Ship testShip;
	@Before
	public void setUp() throws Exception {
		testShip = new Ship("default");
		testShip.setName("big");
	}

	@Test
	public void testDecreaseShieldHealth() {
		testShip.setShieldHealth(100);
		assertEquals(testShip.decreaseShieldHealth(30), false);
		assertEquals(testShip.getShieldHealth(), 70);
		assertEquals(testShip.decreaseShieldHealth(100), true);
		assertEquals(testShip.getShieldHealth(), 0);
	}

	@Test
	public void testIncreaseShieldHealth() {
		testShip.setShieldHealth(10);
		testShip.increaseShieldHealth(30);
		assertEquals(testShip.getShieldHealth(), 40);
		testShip.increaseShieldHealth(100);
		assertEquals(testShip.getShieldHealth(), 100);
	}

	@Test
	public void testPilotToPlanet() {
		CrewMember pilot1 = new CrewMember("Engineer");
		CrewMember pilot2 = new CrewMember("Engineer");
		try {
			testShip.pilotToPlanet("Gentoo", pilot1, pilot2);
		} catch (OutOfActionsException e) {
			fail();
		} catch (InvalidCrewRoleException e) {
			fail();
		} catch (ShieldHealthDepletedException e) {
			
		}
		
		pilot1.setActionsRemaining(0);
		
		try {
			testShip.pilotToPlanet("Gentoo", pilot1, pilot2);
			fail();
		} catch (OutOfActionsException e) {
			
		} catch (InvalidCrewRoleException e) {
			fail();
		} catch (ShieldHealthDepletedException e) {
			
		}
	}

	@Test
	public void testGetName() {
		assertEquals(testShip.getName(), "big");
	}

	@Test
	public void testSetName() {
		testShip.setName("big2");
		assertEquals(testShip.getName(), "big2");
	}

	@Test
	public void testGetShieldHealth() {
		assertEquals(testShip.getShieldHealth(), 100);
	}

	@Test
	public void testSetShieldHealth() {
		testShip.setShieldHealth(80);
		assertEquals(testShip.getShieldHealth(), 80);
	}

	@Test
	public void testGetMaxShieldHealth() {
		assertEquals(testShip.getMaxShieldHealth(), 100);
	}

	@Test
	public void testSetMaxShieldHealth() {
		testShip.setMaxShieldHealth(80);
		assertEquals(testShip.getMaxShieldHealth(), 80);
		assertEquals(testShip.getShieldHealth(), 80);
	}

	@Test
	public void testGetPlanet() {
		testShip.setPlanet("Xandar");
		testShip.getPlanet();
	}

	@Test
	public void testSetPlanet() {
		testShip.setPlanet("Xandar");
		testShip.getPlanet();
	}

}
