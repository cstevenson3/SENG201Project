package main;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import main.CLI.CLIOutOfActionsListener;

public class GameState implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIRECTORY = "saves/";
	private static final String SAVE_EXTENSION = ".ser"; //default serialization extension
	private int daysToPlay;
	private int piecesRequired;
	private float alienPirateEventOdds;
	private Crew crew;
	private Ship ship;
	private String spaceOutpost;
	
	private HashMap<String, SpaceOutpost> spaceOutposts;
	private HashMap<String, Planet> planets;
	
	private GameStateViewContext viewContext;
	private GameStateViewContext lastViewContext;
	
	private int daysElapsed;
	private int piecesCollected;
	
	private Inventory inventory;
	
	private transient ArrayList<OutOfActionsListener> crewOutOfActionsListeners;
	
	
	public class GameStateOutOfActionsListener implements OutOfActionsListener, Serializable{

		@Override
		public void crewOutOfActions() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void crewMemberOutOfActions(CrewMember crewMember) {
			if(crewOutOfActionsToday()) {
				callAllCrewOutOfActionsListeners();
			}
		}
		
	}
	
	public void initCrewMemberOutOfActionsListeners() {
		if(crewOutOfActionsListeners == null) {
			crewOutOfActionsListeners = new ArrayList<OutOfActionsListener>();
		}
		for(CrewMember member : crew.getMembers().values()) {
			member.addOutOfActionsListener(new GameStateOutOfActionsListener());
		}
	}
	
	
	public GameState(int daysToPlay, int piecesRequired, HashMap<String, CrewMember> crewMembers, Properties properties){
		this.daysToPlay = Utilities.clamp(daysToPlay, 1, Integer.MAX_VALUE);
		this.piecesRequired = Utilities.clamp(piecesRequired, 1, Integer.MAX_VALUE);
		
		
		this.crew = new Crew(crewMembers);
		initCrewMemberOutOfActionsListeners();
		
		this.ship = new Ship("default");
		this.alienPirateEventOdds = Float.parseFloat(properties.getProperty("alienPirateEventOdds"));
		
		String spaceOutpostNamesCSV = properties.getProperty("spaceOutpostNames");
		String planetNamesCSV = properties.getProperty("planetNames");
		
		ArrayList<String> spaceOutpostNames = new ArrayList<String>(Arrays.asList(spaceOutpostNamesCSV.split(",")));
		ArrayList<String> planetNames = new ArrayList<String>(Arrays.asList(planetNamesCSV.split(",")));
		
		int startMoney = Integer.parseInt(properties.getProperty("startMoney"));
		
		spaceOutposts = new HashMap<String, SpaceOutpost>();
		for(String spaceOutpostName:spaceOutpostNames){
			SpaceOutpost spaceOutpost = new SpaceOutpost(spaceOutpostName);
			spaceOutposts.put(spaceOutpostName, spaceOutpost);
		}
		
		spaceOutpost = (String)spaceOutposts.keySet().toArray()[0]; //the specs seem to indicate a single space outpost exists (referred to as "the nearest space outpost"), however the option to add more exists
		
		planets = new HashMap<String, Planet>();
		for(String planetName:planetNames){
			Planet planet = new Planet(planetName);
			planets.put(planetName, planet);
		}
		ship.setPlanet((String)planets.keySet().toArray()[0]);
		
		this.daysElapsed = 0;
		this.piecesCollected = 0;
		
		this.inventory = new Inventory(startMoney);
		
		viewContext = GameStateViewContext.CREW_MENU;
		lastViewContext = GameStateViewContext.CREW_MENU;
		
		
		crewOutOfActionsListeners = new ArrayList<OutOfActionsListener>();
	}

	public static ArrayList<String> getSavedGameNames() {
		return Utilities.namesInDirectory(SAVE_DIRECTORY);
	}
	
	public static String getSaveDirectory(String saveName){
		return SAVE_DIRECTORY + saveName + ".ser";
	}

	public static boolean isValidSave(String saveName) {
		if(saveName == null || saveName == ""){
			return false;
		}
		File file = new File(getSaveDirectory(saveName));
		if(file.exists()){
			//maybe check serialization is ok
			return true;
		}else{
			return false;
		}
	}

	public String getShipName() {
		return ship.getName();
	}

	public int getDaysElapsed() {
		return daysElapsed;
	}

	public int getPiecesCollected() {
		return piecesCollected;
	}
	
	public int getPiecesRequired() {
		return piecesRequired;
	}

	public int getScore() {
		//TODO arbitrary score
		return (int) (100 * piecesCollected / (daysElapsed + 1));
	}

	public void setCrew(HashMap<String, CrewMember> crewMembers) {
		crew = new Crew(crewMembers);
	}

	public void setShipName(String shipName) {
		ship.setName(shipName);
	}

	public GameStateViewContext getViewContext() {
		return viewContext;
	}

	public void setLastViewContext(GameStateViewContext lastViewContext) {
		this.lastViewContext = lastViewContext;
	}

	public GameStateViewContext getLastViewContext() {
		return lastViewContext;
	}

	public void setViewContext(GameStateViewContext viewContext) {
		this.viewContext = viewContext;
	}

	public void saveAs(String desiredSaveName) {
		Utilities.writeSerializable(getSaveDirectory(desiredSaveName), this);
	}

	public String getPlanet() {
		return ship.getPlanet();
	}

	public String getCrewStatus() {
		String result = "Crew:\n";
		for(CrewMember crewMember:crew.getMembers().values()){
			result += crewMember.getStatus();
			result += "\n";
		}
		return result;
	}

	public int getShipShieldHealth() {
		return ship.getShieldHealth();
	}
	
	public SpaceOutpost getSpaceOutpost(){
		return spaceOutposts.get(spaceOutpost);
	}

	public HashMap<String, SpaceOutpost> getSpaceOutposts() {
		return spaceOutposts;
	}

	public void setSpaceOutpost(String spaceOutpost) {
		this.spaceOutpost = spaceOutpost;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public boolean purchaseItemFromCurrentSpaceOutpost(String itemName, int quantity) {
		if(spaceOutposts.get(spaceOutpost).canPurchase(itemName, quantity)){
			return spaceOutposts.get(spaceOutpost).purchaseItem(itemName, quantity, inventory);
		}
		return false;
	}

	public void moveToNextDay() throws AlienPirateEventException, OutOfDaysException{
		daysElapsed += 1;
		if(daysElapsed >= daysToPlay){
			throw new OutOfDaysException();
		}
		for(CrewMember crewMember:crew.getMembers().values()){
			crewMember.nextDay();
		}
		if(Math.random() < alienPirateEventOdds){
			String removedItem = inventory.removeRandomItem();
			throw new AlienPirateEventException(removedItem);
		}
	}

	public Crew getCrew() {
		return crew;
	}

	public Ship getShip() {
		return ship;
	}

	public HashMap<String, Planet> getPlanets() {
		return planets;
	}
	
	public boolean crewOutOfActionsToday() {
		boolean result = true;
		for(CrewMember crewMember:crew.getMembers().values()) {
			if(!crewMember.outOfActionsToday()) {
				result = false;
			}
		}
		return result;
	}

	public InventoryItem searchCurrentPlanet(String searchName) throws AllPartsFoundException, OutOfActionsException {
		InventoryItem itemFound = crew.getMembers().get(searchName).search(planets.get(getPlanet()));
		if(itemFound!=null){
			inventory.addItem(itemFound);
		}
		if(itemFound.getType() == ShipPartItem.getTypeString()) { //TODO should have classes determine this type string
			if(getPiecesCollected() >= piecesRequired) {
				throw new AllPartsFoundException((ShipPartItem) itemFound);
			}
		}
		return itemFound;
	}

	public boolean pilotShipTo(String planetName, String pilot1, String pilot2) throws OutOfActionsException, InvalidCrewRoleException, ShieldHealthDepletedException {
		return ship.pilotToPlanet(planetName, crew.getMembers().get(pilot1), crew.getMembers().get(pilot2));
	}

	public static GameState loadSave(String saveName) {
		return (GameState) Utilities.readSerializable(getSaveDirectory(saveName));
	}

	public void haveCrewMemberConsumeItem(String crewMemberName, String itemName, int quantity) throws ItemNotFoundException, InsufficientQuantityException, NotConsumableException, OutOfActionsException {
		crew.getMembers().get(crewMemberName).consume(itemName, quantity, inventory);
	}
	
	public void callAllCrewOutOfActionsListeners() {
		for(OutOfActionsListener listener : crewOutOfActionsListeners) {
			listener.crewOutOfActions();
		}
	}

	public void addCrewOutOfActionsListener(CLIOutOfActionsListener listener) {
		if(crewOutOfActionsListeners == null) {
			crewOutOfActionsListeners = new ArrayList<OutOfActionsListener>();
		}
		crewOutOfActionsListeners.add(listener);
	}
}
