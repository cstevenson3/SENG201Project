package main;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import main.CLI.CLIOutOfActionsListener;

/**
 * GameState represents a single "run" or "save" of a game
 * @author Cameron Stevenson
 *
 */
public class GameState implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Where saves are stored
	 */
	private static final String SAVE_DIRECTORY = "saves/";
	/**
	 * Save file extension
	 */
	private static final String SAVE_EXTENSION = ".ser";
	/**
	 * Days the game lasts
	 */
	private int daysToPlay;
	
	/**
	 * Pieces required to win game
	 */
	private int piecesRequired;
	
	/**
	 * Odds of alien pirates stealing an item overnight
	 */
	private float alienPirateEventOdds;
	
	/**
	 * The crew
	 */
	private Crew crew;
	/**
	 * The ship
	 */
	private Ship ship;
	/**
	 * The name of the current space outpost
	 */
	private String spaceOutpost;
	/**
	 * All space outposts, HashMap from names to space outposts
	 */
	private HashMap<String, SpaceOutpost> spaceOutposts;
	
	/**
	 * All planets, HashMap from names to planets
	 */
	private HashMap<String, Planet> planets;
	
	/**
	 * The current view context
	 */
	private GameStateViewContext viewContext;
	
	
	/**
	 * The previous view context, if necessary
	 */
	private GameStateViewContext lastViewContext;
	
	/**
	 * The number of days elapsed
	 */
	private int daysElapsed;
	
	/**
	 * The number of ship parts collected so far
	 */
	private int piecesCollected;
	
	/**
	 * The crew's inventory
	 */
	private Inventory inventory;
	/**
	 * The listeners interested in the crew running out of actions
	 */
	private transient ArrayList<CrewMemberUpdateListener> crewOutOfActionsListeners;
	
	/**
	 * 
	 * Exists to react to crew members running out of actions and checks if the whole crew has run out
	 * @author Cameron Stevenson
	 *
	 */
	public class GameStateOutOfActionsListener implements CrewMemberUpdateListener, Serializable{

		@Override
		public void crewMemberCaughtDisease(CrewMember crewMember, Disease disease) {
		}

		@Override
		public void crewMemberDead(CrewMember crewMember) {
			crew.removeMember(crewMember.getName());
		}

		@Override
		public void crewMemberOutOfActions(CrewMember crewMember) {
			if(crewOutOfActionsToday()) {
				callAllCrewOutOfActionsListeners();
			}
		}

		@Override
		public void crewOutOfActions() {
		}
	}
	
	/**
	 * Get the names of saved games
	 * @return An ArrayList of saved game names
	 */
	public static ArrayList<String> getSavedGameNames() {
		return Utilities.namesInDirectory(SAVE_DIRECTORY);
	}
	/**
	 * Get the file location of a save
	 * @param saveName The name of a save
	 * @return The file location
	 */
	public static String getSaveDirectory(String saveName){
		return SAVE_DIRECTORY + saveName + ".ser";
	}
	/**
	 * Check if a save name has an existing save
	 * @param saveName The name of the save
	 * @return true if the save exists, false otherwise
	 */
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
	/**
	 * Load a save
	 * @param saveName The name of the save
	 * @return The loaded game state
	 */
	public static GameState loadSave(String saveName) {
		return (GameState) Utilities.readSerializable(getSaveDirectory(saveName));
	}
	
	

	/**
	 * Init GameState with the necessary values to have a valid game
	 * @param daysToPlay The number of days the game should last
	 * @param piecesRequired How many ship parts should be required to win
	 * @param crewMembers The player's requested crew members
	 * @param properties The game's properties file
	 */
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
		
		
		//put ship part items on random planets
		ArrayList<Integer> indices = new ArrayList<Integer>();
		while(indices.size() < piecesRequired) {
			int index = (int) Math.random() * planetNames.size();
			index = index % planetNames.size();
			if(indices.contains(index)) {
				continue;
			}
			indices.add(index);
		}
		
		int planetsAdded = 0;
		planets = new HashMap<String, Planet>();
		for(String planetName:planetNames){
			Planet planet = new Planet(planetName);
			if(indices.contains(planetsAdded)) {
				ShipPartItem toAdd = new ShipPartItem("ShipPart");
				toAdd.setQuantity(1);
				toAdd.setPrice(100);
				planet.setItem(toAdd);
			}
			planets.put(planetName, planet);
			planetsAdded ++;
		}

		
		ship.setPlanet((String)planets.keySet().toArray()[0]);
		
		this.daysElapsed = 0;
		this.piecesCollected = 0;
		
		this.inventory = new Inventory(startMoney);
		
		viewContext = GameStateViewContext.CREW_MENU;
		lastViewContext = GameStateViewContext.CREW_MENU;
		
		
		crewOutOfActionsListeners = new ArrayList<CrewMemberUpdateListener>();
	}

	/**
	 * Add a listener which responds to the crew running out of actions
	 * @param listener The listener
	 */
	public void addCrewOutOfActionsListener(CLIOutOfActionsListener listener) {
		if(crewOutOfActionsListeners == null) {
			crewOutOfActionsListeners = new ArrayList<CrewMemberUpdateListener>();
		}
		crewOutOfActionsListeners.add(listener);
	}

	/**
	 * Call all listeners who respond to the crew running out of actions
	 */
	public void callAllCrewOutOfActionsListeners() {
		for(CrewMemberUpdateListener listener : crewOutOfActionsListeners) {
			listener.crewOutOfActions();
		}
	}

	/**
	 * Check if the whole crew has run out of actions today
	 * @return true if the whole crew has run out of actions today, false otherwise
	 */
	public boolean crewOutOfActionsToday() {
		boolean result = true;
		for(CrewMember crewMember:crew.getMembers().values()) {
			if(!crewMember.outOfActionsToday()) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * Get the crew
	 * @return The crew
	 */
	public Crew getCrew() {
		return crew;
	}

	/**
	 * Get a textual description of the crew's status
	 * @return A textual description of the crew's status
	 */
	public String getCrewStatus() {
		String result = "Crew:\n";
		for(CrewMember crewMember:crew.getMembers().values()){
			result += crewMember.getStatus();
			result += "\n";
		}
		return result;
	}
	
	/**
	 * Get the number of days elapsed
	 * @return The number of days elapsed
	 */
	public int getDaysElapsed() {
		return daysElapsed;
	}

	/**
	 * Get the number of days to play
	 * @return The number of days to play
	 */
	public int getDaysToPlay() {
		return daysToPlay;
	}

	/**
	 * Get the crew's inventory
	 * @return The crew's inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * Get the last view context
	 * @return The last view context
	 */
	public GameStateViewContext getLastViewContext() {
		return lastViewContext;
	}

	/**
	 * Get the number of ship parts collected so far
	 * @return The number of ship parts collected so far
	 */
	public int getPiecesCollected() {
		return piecesCollected;
	}

	/**
	 * Get the number of ship parts required to win the game
	 * @return The number of ship parts required to win the game
	 */
	public int getPiecesRequired() {
		return piecesRequired;
	}

	/**
	 * Get the name of the planet the ship is currently on
	 * @return The name of the planet the ship is currently on
	 */
	public String getPlanet() {
		return ship.getPlanet();
	}

	/**
	 * Get the planets
	 * @return A HashMap from planet names to planets
	 */
	public HashMap<String, Planet> getPlanets() {
		return planets;
	}

	/**
	 * Get an end of game score
	 * @return An end of game score
	 */
	public int getScore() {
		return (int) (100 * piecesCollected / (daysElapsed + 1));
	}

	/**
	 * Get the ship
	 * @return The ship
	 */
	public Ship getShip() {
		return ship;
	}
	
	/**
	 * Get the name of the ship
	 * @return The name of the ship
	 */
	public String getShipName() {
		return ship.getName();
	}

	/**
	 * Get the shield health of the ship
	 * @return The shield health of the ship
	 */
	public int getShipShieldHealth() {
		return ship.getShieldHealth();
	}

	/**
	 * Get the space outpost the ship is currently at
	 * @return The space outpost the ship is currently at
	 */
	public SpaceOutpost getSpaceOutpost(){
		return spaceOutposts.get(spaceOutpost);
	}

	/**
	 * Get all space outposts
	 * @return A HashMap from space outpost names to space outposts
	 */
	public HashMap<String, SpaceOutpost> getSpaceOutposts() {
		return spaceOutposts;
	}

	/**
	 * Get the current view context
	 * @return The current view context
	 */
	public GameStateViewContext getViewContext() {
		return viewContext;
	}

	/**
	 * Have a crew member consume an item
	 * @param crewMemberName The name of the crew member who should consume
	 * @param itemName The name of the item
	 * @param quantity The quantity which should be consumed
	 * @throws ItemNotFoundException if the crew's inventory does not contain the item
	 * @throws InsufficientQuantityException if the crew's inventory does not contain enough of the item
	 * @throws NotConsumableException if the item isn't consumable
	 * @throws OutOfActionsException if the crew member is out of actions
	 */
	public void haveCrewMemberConsumeItem(String crewMemberName, String itemName, int quantity) throws ItemNotFoundException, InsufficientQuantityException, NotConsumableException, OutOfActionsException {
		crew.getMembers().get(crewMemberName).consume(itemName, quantity, inventory);
	}

	/**
	 * Add GameState's crew update listener to each crew member
	 */
	public void initCrewMemberOutOfActionsListeners() {
		if(crewOutOfActionsListeners == null) {
			crewOutOfActionsListeners = new ArrayList<CrewMemberUpdateListener>();
		}
		for(CrewMember member : crew.getMembers().values()) {
			member.addCrewMemberUpdateListener(new GameStateOutOfActionsListener());
		}
	}

	/**
	 * Move on to the next day
	 * @throws AlienPirateEventException if alien pirates raid the ship
	 * @throws OutOfDaysException if the game ends as a result of running out of days
	 */
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

	/**
	 * Have the ship travel to another planet, using two crew members' actions to pilot
	 * @param planetName The planet to travel to
	 * @param pilot1 The first pilot
	 * @param pilot2 The second pilot
	 * @return true if successful, false otherwise
	 * @throws OutOfActionsException if either crew member is out of actions
	 * @throws InvalidCrewRoleException if either crew member cannot pilot
	 * @throws ShieldHealthDepletedException if shield health is depleted as a result of travelling
	 */
	public boolean pilotShipTo(String planetName, String pilot1, String pilot2) throws OutOfActionsException, InvalidCrewRoleException, ShieldHealthDepletedException {
		return ship.pilotToPlanet(planetName, crew.getMembers().get(pilot1), crew.getMembers().get(pilot2));
	}
	
	/**
	 * Purchase an item from the current space outpost
	 * @param itemName The item's name
	 * @param quantity The purchase quantity
	 * @return true if successful, false otherwise
	 */
	public boolean purchaseItemFromCurrentSpaceOutpost(String itemName, int quantity) {
		if(spaceOutposts.get(spaceOutpost).canPurchase(itemName, quantity)){
			return spaceOutposts.get(spaceOutpost).purchaseItem(itemName, quantity, inventory);
		}
		return false;
	}

	/**
	 * Save this game state with the given name
	 * @param desiredSaveName The save name
	 */
	public void saveAs(String desiredSaveName) {
		Utilities.writeSerializable(getSaveDirectory(desiredSaveName), this);
	}
	
	/**
	 * Get a crew member to search the current planet
	 * @param searchName The name of the crew member who should search this planet
	 * @return The item found
	 * @throws AllPartsFoundException if all parts are found as a result of this search
	 * @throws OutOfActionsException if the crew member is out of actions
	 */
	public InventoryItem searchCurrentPlanet(String searchName) throws AllPartsFoundException, OutOfActionsException {
		InventoryItem itemFound = crew.getMembers().get(searchName).search(planets.get(getPlanet()));
		if(itemFound!=null){
			inventory.addItem(itemFound);
		}
		if(itemFound.getType() == ShipPartItem.getTypeString()) { //TODO should have classes determine this type string
			piecesCollected += itemFound.getQuantity();
			if(getPiecesCollected() >= piecesRequired) {
				throw new AllPartsFoundException((ShipPartItem) itemFound);
			}
		}
		return itemFound;
	}

	/**
	 * Set the list of crew members
	 * @param crewMembers A HashMap from crew member names to crew members
	 */
	public void setCrew(HashMap<String, CrewMember> crewMembers) {
		crew = new Crew(crewMembers);
	}

	/**
	 * Set the last view context
	 * @param lastViewContext The last view context
	 */
	public void setLastViewContext(GameStateViewContext lastViewContext) {
		this.lastViewContext = lastViewContext;
	}
	
	/**
	 * Set the ship's name
	 * @param shipName The ship's name
	 */
	public void setShipName(String shipName) {
		ship.setName(shipName);
	}

	/**
	 * Set by name the space outpost the ship should be at
	 * @param spaceOutpost The name of the space outpost the ship should be at
	 */
	public void setSpaceOutpost(String spaceOutpost) {
		this.spaceOutpost = spaceOutpost;
	}

	/**
	 * Set the current view context
	 * @param viewContext The new current view context
	 */
	public void setViewContext(GameStateViewContext viewContext) {
		this.lastViewContext = this.viewContext;
		this.viewContext = viewContext;
	}
}
