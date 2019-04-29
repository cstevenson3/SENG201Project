package main;

import java.io.Serializable;
import java.util.Properties;

public class Ship implements Serializable{
	private static final String SHIP_DIRECTORY = "ships/";
	private float asteroidBeltOdds;
	
	private String name;
	
	private int maxShieldHealth;
	private int shieldHealth;
	
	private int asteroidBeltEventShieldHealthDecrease;
	
	private String planet;
	
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
	
	private void setAsteroidBeltEventShieldHealthDecrease(int value) {
		asteroidBeltEventShieldHealthDecrease = Utilities.clamp(value, 0, maxShieldHealth);
	}

	private void setAsteroidBeltOdds(float value) {
		asteroidBeltOdds = Utilities.clamp(value, 0, 1);
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getShieldHealth() {
		return shieldHealth;
	}

	public void setShieldHealth(int shieldHealth) {
		this.shieldHealth = shieldHealth;
	}

	public int getMaxShieldHealth() {
		return maxShieldHealth;
	}

	public void setMaxShieldHealth(int maxShieldHealth){
		if (maxShieldHealth < 0){
			maxShieldHealth = 0;
		}
		this.maxShieldHealth = maxShieldHealth;
		if(shieldHealth > maxShieldHealth){
			shieldHealth = maxShieldHealth;
		}
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
