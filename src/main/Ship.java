package main;

import java.io.Serializable;
import java.util.Properties;

/**
 * Ships have shield health and are located on a planet
 * @author Cameron Stevenson
 *
 */
public class Ship implements Serializable{
	
	/**
	 * Where the properties files describing ships are found
	 */
	private static final String SHIP_DIRECTORY = "ships/";
	
	/**
	 * The odds of hitting an asteroid belt when travelling
	 */
	private float asteroidBeltOdds;
	
	/**
	 * The name of the ship
	 */
	private String name;
	
	/**
	 * Maximum shield health
	 */
	private int maxShieldHealth;
	
	/**
	 * Current shield health
	 */
	private int shieldHealth;
	
	/**
	 * How much an asteroid belt event decreases shield health
	 */
	private int asteroidBeltEventShieldHealthDecrease;
	
	/**
	 * The name of the planet this ship is on
	 */
	private String planet;
	
	/**
	 * Init a ship from a properties file describing it
	 * @param propertiesFile The name of the ship type
	 */
	public Ship(String propertiesFile){
		Properties properties = Utilities.loadPropertiesFile(getDirectory(propertiesFile));
		try{
			setName(properties.getProperty("name"));
			setMaxShieldHealth(Integer.parseInt(properties.getProperty("maxShieldHealth")));
			setShieldHealth(Integer.parseInt(properties.getProperty("maxShieldHealth")));
			setAsteroidBeltOdds(Float.parseFloat(properties.getProperty("asteroidBeltOdds")));
			setAsteroidBeltEventShieldHealthDecrease(Integer.parseInt(properties.getProperty("asteroidBeltEventShieldHealthDecrease")));
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			System.out.println("Something is wrong with the properties file at " + SHIP_DIRECTORY + propertiesFile + ".properties" + ", please fix:");
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * Set the health decrease when encountering an asteroid belt
	 * @param value The health decrease when encountering an asteroid belt
	 */
	private void setAsteroidBeltEventShieldHealthDecrease(int value) {
		asteroidBeltEventShieldHealthDecrease = Utilities.clamp(value, 0, maxShieldHealth);
	}

	/**
	 * Set the odds of encountering an asteroid belt when travelling
	 * @param value The odds of encountering an asteroid belt when travelling
	 */
	private void setAsteroidBeltOdds(float value) {
		asteroidBeltOdds = Utilities.clamp(value, 0, 1);
	}

	/**
	 * Get the file location of a ship type description
	 * @param name The ship type name
	 * @return The file location
	 */
	private String getDirectory(String name){
		return SHIP_DIRECTORY + name + ".properties";
	}
	
	/**
	 * Decrease the ship's shield health
	 * @param amount the amount the shield health should be decreased (absolute value is taken)
	 * @return true if shield health has reached zero
	 */
	public boolean decreaseShieldHealth(int amount){
		int absAmount = Math.abs(amount);
		
		int newShieldHealth = shieldHealth - absAmount;
		if (newShieldHealth <= 0){
			this.shieldHealth = 0;
			return true;
		}else{
			this.shieldHealth = newShieldHealth;
			return false;
		}
	}
	
	/**
	 * Increase the ship's shield health
	 * @param amount the amount the shield health should be increased (absolute value is taken)
	 * 
	 */
	public void increaseShieldHealth(int amount){
		int absAmount = Math.abs(amount);
		
		int newShieldHealth = shieldHealth + absAmount;
		if (newShieldHealth > maxShieldHealth){
			this.shieldHealth = maxShieldHealth;
		}else{
			this.shieldHealth = newShieldHealth;
		}
	}
	
	/**
	 * Uses two pilots' actions to pilot the ship to a new planet
	 * @param planet name of the planet
	 * @param pilot1 the first pilot
	 * @param pilot2 the second pilot
	 * @throws OutOfActionsException if either pilot does not have enough actions to complete this task
	 * @throws InvalidCrewRoleException if either pilot is not able to pilot
	 * @throws ShieldHealthDepletedException if shield health is depleted as a result of the asteroid belt
	 * @return true if successful, false otherwise
	 */
	public boolean pilotToPlanet(String planet, CrewMember pilot1, CrewMember pilot2) throws OutOfActionsException, InvalidCrewRoleException, ShieldHealthDepletedException{
		if(getPlanet() == planet){
			//do nothing
		}else{
			try{
				pilot1.pilot();
				pilot2.pilot();
				setPlanet(planet);
				return asteroidBeltEvent();
			}catch(OutOfActionsException e1){
				throw e1;
			}catch(InvalidCrewRoleException e2){
				throw e2;
			}
		}
		return false;
	}

	/**
	 * Check if an asteroid belt is encountered when travelling
	 * @return true if an asteroid belt is encountered, false otherwise
	 * @throws ShieldHealthDepletedException if shield health is depleted
	 */
	private boolean asteroidBeltEvent() throws ShieldHealthDepletedException{
		boolean roll = Math.random() < asteroidBeltOdds;
		if(roll){
			decreaseShieldHealth(asteroidBeltEventShieldHealthDecrease);
			if(shieldHealth <= 0){
				throw new ShieldHealthDepletedException();
			}
		}
		return roll;
	}

	/**
	 * Get the name of the ship
	 * @return The name of the ship
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the ship
	 * @param name The name of the ship
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the shield health of the ship
	 * @return The shield health of the ship
	 */
	public int getShieldHealth() {
		return shieldHealth;
	}

	/**
	 * Set the shield health of the ship
	 * @param shieldHealth The shield health of the ship
	 */
	public void setShieldHealth(int shieldHealth) {
		this.shieldHealth = shieldHealth;
	}

	/**
	 * Get the maximum shield health of the ship
	 * @return The maximum shield health of the ship
	 */
	public int getMaxShieldHealth() {
		return maxShieldHealth;
	}

	/**
	 * Set the maximum shield health of the ship
	 * @param maxShieldHealth The maximum shield health of the ship
	 */
	public void setMaxShieldHealth(int maxShieldHealth){
		this.maxShieldHealth = Utilities.clamp(maxShieldHealth, 0, Integer.MAX_VALUE);
		this.shieldHealth = Utilities.clamp(this.shieldHealth, 0, this.maxShieldHealth);
	}
	
	/**
	 * Returns the name of the planet the ship is on
	 * @return the name of the planet the ship is on
	 */
	public String getPlanet(){
		return this.planet;
	}
	
	/**
	 * Set the planet the ship is on
	 * @param planet the name of the planet
	 */
	public void setPlanet(String planet){
		this.planet = planet;
	}
}
