package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.Disease;

public class DiseaseTest {

	Disease testDisease;
	@Before
	public void setUp() throws Exception {
		testDisease = new Disease("Space Plague");
	}

	@Test
	public void testGetName() {
		assertEquals(testDisease.getName(), "Space Plague");
	}

	@Test
	public void testSetName() {
		testDisease.setName("Some Disease");
		assertEquals(testDisease.getName(), "Some Disease");
	}

	@Test
	public void testGetDailyHealthDecrease() {
		testDisease.setDailyHealthDecrease(12);
		assertEquals(testDisease.getDailyHealthDecrease(), 12);
	}

	@Test
	public void testSetDailyHealthDecrease() {
		testDisease.setDailyHealthDecrease(12);
		assertEquals(testDisease.getDailyHealthDecrease(), 12);
	}

	@Test
	public void testGetRandomDisease() {
		assertEquals(Disease.getRandomDisease() instanceof Disease, true);
	}

}
