package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.AlienPirateEventException;

public class AlienPirateEventExceptionTest {

	@Test
	public void testAlienPirateEventException() {
		AlienPirateEventException apee = new AlienPirateEventException("Item");
		assertEquals(apee.getItemRemoved(), "Item");
		apee.setItemRemoved("Item2");
		assertEquals(apee.getItemRemoved(), "Item2");
	}

}
