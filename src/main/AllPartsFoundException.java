package main;

public class AllPartsFoundException extends Exception {
	ShipPartItem lastPartFound;
	public AllPartsFoundException(ShipPartItem lastPartFound) {
		this.lastPartFound = lastPartFound;
	}
}
