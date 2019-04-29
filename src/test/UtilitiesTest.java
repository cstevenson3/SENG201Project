package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.Utilities;

import org.junit.Test;

public class UtilitiesTest {

	@Test
	public void testNamesInDirectory() {
		ArrayList<String> names = Utilities.namesInDirectory("directoryTest");
		assertEquals(names.get(0), "a");
	}

}
