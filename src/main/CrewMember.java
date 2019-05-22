package main;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

public class CrewMember implements Serializable{
	private static final String CREW_MEMBER_DIRECTORY = "crew_members/";
	
	public static ArrayList<String> getAllRoles() {
		return Utilities.namesInDirectory(CREW_MEMBER_DIRECTORY);
	}
	private static String getDirectory(String name) {
		return CREW_MEMBER_DIRECTORY + name + ".properties";
	}
	
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
	
	private int repairSkill;
	private int searchSkill;
	
	private int health;
	private int hunger;
	private int tiredness;

	private int actionsPerDay;
	private int actionsRemaining;

	private ArrayList<Disease> diseases;
	private float diseaseOdds;
	
	private transient ArrayList<OutOfActionsListener> outOfActionsListeners;
	
	public CrewMember(String propertiesFile){
		Properties properties = Utilities.loadPropertiesFile(getDirectory(propertiesFile));
		setName(properties.getProperty("name"));
		setRole(properties.getProperty("role"));
		
		int maxHealth = Integer.parseInt(properties.getProperty("maxHealth"));
		setMaxHealth(maxHealth);
		setHealth(maxHealth);
		
		int maxHunger = Integer.parseInt(properties.getProperty("maxHunger"));
		setMaxHunger(maxHunger);
		setHunger(maxHunger);
		
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
		
		outOfActionsListeners = new ArrayList<OutOfActionsListener>();
	}
	
	public void addOutOfActionsListener(OutOfActionsListener listener) {
		if(outOfActionsListeners == null) {
			outOfActionsListeners = new ArrayList<OutOfActionsListener>();
		}
		outOfActionsListeners.add(listener);
	}

	public boolean canPilot(){
		return true;
	}
	
	public int getActionsPerDay() {
		return actionsPerDay;
	}

	public int getActionsRemaining() {
		return actionsRemaining;
	}

	public ArrayList<Disease> getDiseases() {
		return diseases;
	}
	
	public int getHealth() {
		return health;
	}

	public int getHunger() {
		return hunger;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getMaxHunger() {
		return maxHunger;
	}

	public int getMaxTiredness() {
		return maxTiredness;
	}

	public String getName() {
		return name;
	}

	public int getRepairSkill() {
		return repairSkill;
	}

	public String getRole() {
		return role;
	}

	public String getRoleDescription() {
		return role + ": repair skill (" + repairSkill + ") - search skill (" + searchSkill + ")";
	}

	public int getSearchSkill() {
		return searchSkill;
	}

	public String getStatus(){
		String result = name + " (" + role + ") status: health (" + getHealth() + ") - hunger (" + getHunger() + ") - tiredness (" + getTiredness() + ")";
		for(Disease disease:diseases) {
			result += "\n" + name + " is afflicted by " + disease.getName();
		}
		return result;
	}

	public int getTiredness() {
		return tiredness;
	}

	public int getTirednessDecreasePerSleep() {
		return tirednessDecreasePerSleep;
	}

	public int getTirednessIncreasePerDay() {
		return tirednessIncreasePerDay;
	}
	
	public boolean nextDay() {
		setTiredness(tiredness + tirednessIncreasePerDay);
		for(Disease disease:diseases){
			setHealth(health - disease.getDailyHealthDecrease());
		}
		if(health <= 0){
			return true;
		}
		actionsRemaining = actionsPerDay;
		boolean diseaseRoll = Math.random() < diseaseOdds;
		if(diseaseRoll){
			diseases.add(Disease.getRandomDisease());
		}
		return false;
	}

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

	public boolean repair(Ship ship) throws OutOfActionsException {
		if(actionsRemaining >= 1){
			ship.increaseShieldHealth(repairSkill);
			useActions(1);
			return true;
		}else{
			return false;
		}
	}

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

	public void setActionsPerDay(int actionsPerDay) {
		this.actionsPerDay = Utilities.clamp(actionsPerDay, 0, Integer.MAX_VALUE);
	}

	public void setActionsRemaining(int actionsRemaining) {
		this.actionsRemaining = Utilities.clamp(actionsRemaining, 0, actionsPerDay);
	}

	public void setDiseases(ArrayList<Disease> diseases) {
		this.diseases = diseases;
	}

	public void setHealth(int health) {
		this.health = Utilities.clamp(health, 0, maxHealth);
	}

	public void setHunger(int hunger) {
		this.hunger = Utilities.clamp(hunger, 0, maxHunger);
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = Utilities.clamp(maxHealth, 1, Integer.MAX_VALUE);
	}

	public void setMaxHunger(int maxHunger) {
		this.maxHunger = Utilities.clamp(maxHunger, 1, Integer.MAX_VALUE);
	}

	public void setMaxTiredness(int maxTiredness) {
		this.maxTiredness = Utilities.clamp(maxTiredness, 1, Integer.MAX_VALUE);
	}

	public void setName(String name) {
		if(name == null){
			this.name = "unnammed";
		}else{
			this.name = name;
		}
	}
	
	public void setRepairSkill(int repairSkill) {
		this.repairSkill = Utilities.clamp(repairSkill, 0, Integer.MAX_VALUE);
	}

	public void setRole(String role) {
		if(role == null){
			this.role = "No role";
		}else{
			this.role = role;
		}
	}

	public void setSearchSkill(int searchSkill) {
		this.searchSkill = Utilities.clamp(searchSkill, 0, Integer.MAX_VALUE);
	}

	public void setTiredness(int tiredness) {
		this.tiredness = Utilities.clamp(tiredness, 0, maxTiredness);
	}

	private void setTirednessDecreasePerSleep(int value) {
		tirednessDecreasePerSleep = Utilities.clamp(value, 0, maxTiredness);
	}

	private void setTirednessIncreasePerDay(int value) {
		tirednessIncreasePerDay = Utilities.clamp(value, 0, maxTiredness);
	}

	public void sleep() throws OutOfActionsException {
		useActions(1);
		setTiredness(tiredness - tirednessDecreasePerSleep);
	}
	
	public void callAllOutOfActionsListeners() {
		for(OutOfActionsListener listener : outOfActionsListeners) {
			listener.crewMemberOutOfActions(this);
		}
	}
	
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
	public boolean outOfActionsToday() {
		return actionsRemaining <= 0;
	}
	public void clearListeners() {
		outOfActionsListeners = new ArrayList<OutOfActionsListener>();
	}
}