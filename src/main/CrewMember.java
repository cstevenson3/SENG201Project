package main;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

/**
 * A crew member
 * @author Cameron Stevenson
 *
 */
public class CrewMember implements Serializable{
	
	/**
	 * Where the description files reside
	 */
	private static final String CREW_MEMBER_DIRECTORY = "crew_members/";
	
	/**
	 * Get all role names available to choose from
	 * @return An ArrayList of role names
	 */
	public static ArrayList<String> getAllRoles() {
		ArrayList<String> roles = Utilities.namesInDirectory(CREW_MEMBER_DIRECTORY);
		roles.remove("test");
		return roles;
	}
	
	/**
	 * Get the file location of the description of a role
	 * @param name The role name
	 * @return The file location of that role's description
	 */
	private static String getDirectory(String name) {
		return CREW_MEMBER_DIRECTORY + name + ".properties";
	}
	
	/**
	 * Check if a role name is valid
	 * @param role The role name
	 * @return true If the role exists, false otherwise
	 */
	public static boolean isValidRole(String role) {
		File file = new File(getDirectory(role));
		return file.exists();
	}
	
	
	private String name;
	private String role;
	
	private int maxHealth;
	private int maxHunger;
	private int maxTiredness;
	private int tirednessIncreasePerDay;
	private int tirednessDecreasePerSleep;
	
	/**
	 * The amount of shield this crew member repairs
	 */
	private int repairSkill;
	
	/**
	 * How many rolls this crew member gets when rolling search odds on a planet
	 */
	private int searchSkill;
	
	private int health;
	private int hunger;
	private int tiredness;

	private int actionsPerDay;
	private int actionsRemaining;

	/**
	 * The diseases this crew member is afflicted with
	 */
	private ArrayList<Disease> diseases;
	private float diseaseOdds;
	
	/**
	 * The listeners who want to know when this crew member runs out of actions
	 */
	private transient ArrayList<CrewMemberUpdateListener> crewMemberUpdateListeners;
	
	/**
	 * Init
	 * @param propertiesFile The role name of this crew member
	 */
	public CrewMember(String propertiesFile){
		Properties properties = Utilities.loadPropertiesFile(getDirectory(propertiesFile));
		setName(properties.getProperty("name"));
		setRole(properties.getProperty("role"));
		
		int maxHealth = Integer.parseInt(properties.getProperty("maxHealth"));
		setMaxHealth(maxHealth);
		setHealth(maxHealth);
		
		int maxHunger = Integer.parseInt(properties.getProperty("maxHunger"));
		setMaxHunger(maxHunger);
		setHunger(0);
		
		int maxTiredness = Integer.parseInt(properties.getProperty("maxTiredness"));
		setMaxTiredness(maxTiredness);
		setTiredness(0);
		
		setTirednessIncreasePerDay(Integer.parseInt(properties.getProperty("tirednessIncreasePerDay")));
		setTirednessDecreasePerSleep(Integer.parseInt(properties.getProperty("tirednessDecreasePerSleep")));
		
		setRepairSkill(Integer.parseInt(properties.getProperty("repairSkill")));
		setSearchSkill(Integer.parseInt(properties.getProperty("searchSkill")));
		
		int actionsPerDay = Integer.parseInt(properties.getProperty("actionsPerDay"));
		setActionsPerDay(actionsPerDay);
		setActionsRemaining(actionsPerDay);
		
		diseases = new ArrayList<Disease>();
		diseaseOdds = Float.parseFloat(properties.getProperty("diseaseOdds"));
		
		crewMemberUpdateListeners = new ArrayList<CrewMemberUpdateListener>();
	}
	
	/**
	 * Add a listener to alert when out of actions
	 * @param listener The listener to be added
	 */
	public void addCrewMemberUpdateListener(CrewMemberUpdateListener listener) {
		if(crewMemberUpdateListeners == null) {
			crewMemberUpdateListeners = new ArrayList<CrewMemberUpdateListener>();
		}
		crewMemberUpdateListeners.add(listener);
	}

	/**
	 * Can pilot, stub for if in the future restrictions are placed on who can pilot
	 * @return If this crew member can pilot
	 */
	public boolean canPilot(){
		return true;
	}
	
	/**
	 * Get how many actions this crew member can perform per day
	 * @return actions allowed per day
	 */
	public int getActionsPerDay() {
		return actionsPerDay;
	}

	/**
	 * Get how many actions this crew member still has left today
	 * @return actions remaining today
	 */
	public int getActionsRemaining() {
		return actionsRemaining;
	}

	/**
	 * Get the diseases this crew member is afflicted with
	 * @return An ArrayList of diseases this crew member is afflicted with
	 */
	public ArrayList<Disease> getDiseases() {
		return diseases;
	}
	
	/**
	 * Get the health of the crew member
	 * @return Health of the crew member
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Get the hunger of the crew member
	 * @return Hunger of the crew member
	 */
	public int getHunger() {
		return hunger;
	}

	/**
	 * Get the maximum health of the crew member
	 * @return Maximum health of the crew member
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * Get the maximum hunger of the crew member
	 * @return Maximum hunger of the crew member
	 */
	public int getMaxHunger() {
		return maxHunger;
	}

	/**
	 * Get the maximum tiredness of the crew member
	 * @return Maximum tiredness of the crew member
	 */
	public int getMaxTiredness() {
		return maxTiredness;
	}

	/**
	 * Get the name of the crew member
	 * @return Name of the crew member
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the repair skill of the crew member
	 * @return Repair skill of the crew member
	 */
	public int getRepairSkill() {
		return repairSkill;
	}

	/**
	 * Get the role name of the crew member
	 * @return Role name of the crew member
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Get the role description of the crew member
	 * @return Role description of the crew member
	 */
	public String getRoleDescription() {
		return role + ": repair skill (" + repairSkill + ") - search skill (" + searchSkill + ")";
	}

	/**
	 * Get the search skill of the crew member
	 * @return Search skill of the crew member
	 */
	public int getSearchSkill() {
		return searchSkill;
	}

	/**
	 * Get the status of the crew member
	 * @return Status of the crew member (health, hunger, tiredness, and diseases afflicted with)
	 */
	public String getStatus(){
		String result = name + " (" + role + ") status: health (" + getHealth() + ") - hunger (" + getHunger() + ") - tiredness (" + getTiredness() + ")";
		for(Disease disease:diseases) {
			result += "\n" + name + " is afflicted by " + disease.getName();
		}
		return result;
	}

	/**
	 * Get the tiredness of the crew member
	 * @return Tiredness of the crew member
	 */
	public int getTiredness() {
		return tiredness;
	}

	/**
	 * Get the tiredness decrease per sleep of the crew member
	 * @return Tiredness decrease per sleep of the crew member
	 */
	public int getTirednessDecreasePerSleep() {
		return tirednessDecreasePerSleep;
	}

	/**
	 * Get the tiredness increase per day of the crew member
	 * @return Tiredness increase per day of the crew member
	 */
	public int getTirednessIncreasePerDay() {
		return tirednessIncreasePerDay;
	}
	
	/**
	 * Update the crew member overnight
	 */
	public void nextDay() {
		setTiredness(tiredness + tirednessIncreasePerDay);
		for(Disease disease:diseases){
			setHealth(health - disease.getDailyHealthDecrease());
		}
		if(health <= 0){
			callAllCrewMemberDeadListeners();
			return;
		}
		actionsRemaining = actionsPerDay;
		boolean diseaseRoll = Math.random() < diseaseOdds;
		if(diseaseRoll){
			Disease toAdd = Disease.getRandomDisease();
			diseases.add(toAdd);
			callAllCrewMemberCaughtDiseaseListeners(toAdd);
		}
	}

	/**
	 * Tell all listeners that the crew member has caught a disease
	 * @param disease The disease caught
	 */
	private void callAllCrewMemberCaughtDiseaseListeners(Disease disease) {
		for(CrewMemberUpdateListener listener : crewMemberUpdateListeners) {
			listener.crewMemberCaughtDisease(this, disease);
		}	
	}

	/**
	 * Tell all listeners that the crew member has died
	 */
	private void callAllCrewMemberDeadListeners() {
		for(CrewMemberUpdateListener listener : crewMemberUpdateListeners) {
			listener.crewMemberDead(this);
		}	
	}

	/**
	 * Use an action to pilot the ship
	 * @throws OutOfActionsException if the crew member is out of actions
	 * @throws InvalidCrewRoleException if the crew member cannot pilot
	 */
	public void pilot() throws OutOfActionsException, InvalidCrewRoleException{
		if(actionsRemaining >= 1){
			//check if correct role
			if(canPilot()){
				useActions(1);
			}else{
				throw new InvalidCrewRoleException();
			}
		}else{
			throw new OutOfActionsException();
		}
	}

	/**
	 * Use an action to repair the ship
	 * @throws OutOfActionsException if the crew member is out of actions
	 */
	public void repair(Ship ship) throws OutOfActionsException {
		useActions(1);
		ship.increaseShieldHealth(repairSkill);
	}

	/**
	 * Use an action to search a planet
	 * @param planet The planet to be search
	 * @return The item found if one was found, otherwise null
	 * @throws OutOfActionsException if the crew member is out of actions
	 */
	public InventoryItem search(Planet planet) throws OutOfActionsException {
		useActions(1);
		InventoryItem result = null;
		int searchesDone = 0;
		while(true){
			if(searchesDone >= searchSkill){
				break;
			}
			result = planet.search();
			if(result != null){
				break;
			}
			searchesDone ++;
		}
		return result;
	}

	/**
	 * Set how many actions the crew member has per day
	 * @param actionsPerDay How many actions the crew member has per day
	 */
	public void setActionsPerDay(int actionsPerDay) {
		this.actionsPerDay = Utilities.clamp(actionsPerDay, 0, Integer.MAX_VALUE);
	}

	/**
	 * Set how many actions remaining the crew member has today
	 * @param actionsRemaining How many actions remaining the crew member has today
	 */
	public void setActionsRemaining(int actionsRemaining) {
		this.actionsRemaining = Utilities.clamp(actionsRemaining, 0, actionsPerDay);
	}

	/**
	 * Set the diseases the crew member is afflicted by
	 * @param diseases An ArrayList of diseaes
	 */
	public void setDiseases(ArrayList<Disease> diseases) {
		this.diseases = diseases;
	}

	/**
	 * Set the crew member's health
	 * @param health Health
	 */
	public void setHealth(int health) {
		this.health = Utilities.clamp(health, 0, maxHealth);
	}

	/**
	 * Set the crew member's hunger
	 * @param health Hunger
	 */
	public void setHunger(int hunger) {
		this.hunger = Utilities.clamp(hunger, 0, maxHunger);
	}

	/**
	 * Set the crew member's maximum health
	 * @param maxHealth Maximum health
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = Utilities.clamp(maxHealth, 1, Integer.MAX_VALUE);
	}

	/**
	 * Set the crew member's maximum hunger
	 * @param maxHunger Maximum hunger
	 */
	public void setMaxHunger(int maxHunger) {
		this.maxHunger = Utilities.clamp(maxHunger, 1, Integer.MAX_VALUE);
	}

	/**
	 * Set the crew member's maximum tiredness
	 * @param maxTiredness Maximum tiredness
	 */
	public void setMaxTiredness(int maxTiredness) {
		this.maxTiredness = Utilities.clamp(maxTiredness, 1, Integer.MAX_VALUE);
	}

	/**
	 * Set the crew member's name. Will default to "unnamed" if name==null
	 * @param name Name
	 */
	public void setName(String name) {
		if(name == null){
			this.name = "unnammed";
		}else{
			this.name = name;
		}
	}
	
	/**
	 * Set the crew member's repair skill
	 * @param repairSkill Repair skill
	 */
	public void setRepairSkill(int repairSkill) {
		this.repairSkill = Utilities.clamp(repairSkill, 0, Integer.MAX_VALUE);
	}

	/**
	 * Set the crew member's role
	 * @param role The name of the role
	 */
	public void setRole(String role) {
		if(role == null){
			this.role = "No role";
		}else{
			this.role = role;
		}
	}

	/**
	 * Set the crew member's search skill
	 * @param searchSkill number of searches the crew member can make with one action
	 */
	public void setSearchSkill(int searchSkill) {
		this.searchSkill = Utilities.clamp(searchSkill, 0, Integer.MAX_VALUE);
	}

	/**
	 * Set the crew member's tiredness
	 * @param tiredness Tiredness
	 */
	public void setTiredness(int tiredness) {
		this.tiredness = Utilities.clamp(tiredness, 0, maxTiredness);
	}

	/**
	 * Set the crew member's tiredness decrease per sleep
	 * @param value How much the crew member's tiredness should decrease when they sleep
	 */
	private void setTirednessDecreasePerSleep(int value) {
		tirednessDecreasePerSleep = Utilities.clamp(value, 0, maxTiredness);
	}

	/**
	 * Set the crew member's tiredness increase per day
	 * @param value How much the crew member's tiredness should increase when the day changes
	 */
	private void setTirednessIncreasePerDay(int value) {
		tirednessIncreasePerDay = Utilities.clamp(value, 0, maxTiredness);
	}

	/**
	 * Use one action to make the crew member sleep
	 * @throws OutOfActionsException if the crew member does not have enough actions to sleep
	 */
	public void sleep() throws OutOfActionsException {
		useActions(1);
		setTiredness(tiredness - tirednessDecreasePerSleep);
	}
	
	/**
	 * Tell all listeners that this crew member has run out of actions
	 */
	public void callAllOutOfActionsListeners() {
		for(CrewMemberUpdateListener listener : crewMemberUpdateListeners) {
			listener.crewMemberOutOfActions(this);
		}
	}
	
	/**
	 * Have the crew member use up actions
	 * @param amount How many actions should be used
	 * @throws OutOfActionsException if this crew member does not have enough actions remaining
	 */
	public void useActions(int amount) throws OutOfActionsException{
		if(actionsRemaining - amount < 0){
			throw new OutOfActionsException();
		}else{
			setActionsRemaining(actionsRemaining - amount);
			if(actionsRemaining == 0) {
				callAllOutOfActionsListeners();
			}
		}
	}
	
	/**
	 * Use an action to consume some item
	 * @param itemName The name of the item
	 * @param quantity The quantity which should be consumed
	 * @param inventory The inventory to take the item from
	 * @throws ItemNotFoundException if the item isn't found in the inventory
	 * @throws InsufficientQuantityException if the inventory doesn't contain enough of the item
	 * @throws NotConsumableException if the item isn't consumable
	 * @throws OutOfActionsException if the crew member is out of actions
	 */
	public void consume(String itemName, int quantity, Inventory inventory) throws ItemNotFoundException, InsufficientQuantityException, NotConsumableException, OutOfActionsException  {
		useActions(1);
		try {
			InventoryItem toConsume = inventory.consume(itemName, quantity);
			switch(toConsume.getType()) {
			case "MedicalItem":
				MedicalItem medicalItem = (MedicalItem) toConsume;
				setHealth(health + medicalItem.getHealthIncrease());
				for(Disease disease:diseases) {
					if(medicalItem.cures(disease)) {
						diseases.remove(disease);
					}
				}
				break;
			case "FoodItem":
				FoodItem foodItem = (FoodItem) toConsume;
				setHunger(hunger - foodItem.getHungerDecrease());
				break;
			case "ShipPartItem":
				throw new NotConsumableException();
			}
		} catch (ItemNotFoundException e) {
			throw e;
		} catch (InsufficientQuantityException e) {
			throw e;
		}
	}
	
	/**
	 * Is the crew member out of actions today
	 * @return true if no actions remaining, false otherwise
	 */
	public boolean outOfActionsToday() {
		return actionsRemaining <= 0;
	}
	
	/**
	 * Remove all listeners previously added
	 */
	public void clearListeners() {
		crewMemberUpdateListeners = new ArrayList<CrewMemberUpdateListener>();
	}
}