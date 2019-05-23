package main;

/**
 * To be thrown when all ship parts have been found to alert the CLI/GUI
 * @author Cameron Stevenson
 *
 */
public class AllPartsFoundException extends Exception {
	/**
	 * The last part found in case the CLI/GUI needs to know
	 */
	ShipPartItem lastPartFound;
	
	/**
	 * Init
	 * @param lastPartFound The last part found
	 */
	public AllPartsFoundException(ShipPartItem lastPartFound) {
		this.lastPartFound = lastPartFound;
	}
}
