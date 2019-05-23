package test;

import static org.junit.Assert.*;
import main.*;

import org.junit.Test;

public class AllPartsFoundExceptionTest {

	@Test
	public void testAllPartsFoundException() {
		ShipPartItem spi = new ShipPartItem("ShipPart");
		AllPartsFoundException apfe = new AllPartsFoundException(spi);
		assertEquals(apfe.getLastPartFound(), spi);
	}

}
