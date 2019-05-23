package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

/**
 * 
 * CLI handles the command-line interface version of this game
 * 
 * @author Cameron Stevenson
 *
 */
public class CLI implements Serializable{
	
	/**
	 * This file contains information about what game objects should be present
	 */
	private static final String GAME_PROPERTIES_FILE = "gameProperties.properties";
	
	/**
	 * Command line input
	 */
	private transient Scanner scanner;
	
	/**
	 * The copy of the game currently being run
	 */
	private transient GameState gameState;
	
	/**
	 * The CLI needs to know what options to present to the user, these along with
	 * gameState.viewContext track the view state
	 */
	private CLIContext context;
	private CLIContext lastContext;
	
	/**
	 * init the CLI
	 */
	public CLI(){
		scanner = new Scanner(System.in);
		context = CLIContext.MAIN_MENU;
		lastContext = CLIContext.MAIN_MENU;
	}
	
	/**
	 * Request the user input an integer within a certain range
	 * @param min lower inclusive bound of the requested range
	 * @param max upper inclusive bound of the requested range
	 * @return a user-inputted integer in the requested range
	 */
	private int getInt(int min, int max){
		while(true){
			try{
				int input = Integer.parseInt(scanner.nextLine());
				if(min <= input && input <= max){
					return input;
				}else{
					System.out.println("Your input needs be in the range " + min + " to " + max + ", try again");
				}
			}catch(NumberFormatException e){
				System.out.println("Not a valid number, try again");
			}
		}
	}
	
	/**
	 * Handle the input/output involved with moving to the next day.
	 * This was pulled out of the run function since it is used by callbacks involving
	 * running out of actions
	 */
	public void handleMovingToNextDay() {
		try {
			gameState.moveToNextDay();
		} catch (AlienPirateEventException e) {
			if(e.getItemRemoved() == null) {
				System.out.println("Alien pirates raided the ship and took nothing");
			}else {
				System.out.println("Alien pirates raided the ship and took " + e.getItemRemoved());
			}
		} catch (OutOfDaysException e) {
			System.out.println("Out of days, ending game");
			context = CLIContext.END_GAME;
		}
	}
	
	/**
	 * 
	 * This class responds to crew members or the whole crew running out of actions
	 * and handles telling the user and any required actions
	 * 
	 * @author Cameron Stevenson
	 *
	 */
	public class CLIOutOfActionsListener implements CrewMemberUpdateListener, Serializable{

		/**
		 * Respond to the crew running out of actions
		 */
		@Override
		public void crewOutOfActions() {
			System.out.println("All crew actions used today, moving to next day");
			handleMovingToNextDay();
		}

		/**
		 * Respond to a crew member running out of actions
		 */
		@Override
		public void crewMemberOutOfActions(CrewMember crewMember) {
			System.out.println("Crew member " + crewMember.getName() + " has now used up all actions today");
		}

		/**
		 * Respond to a crew member dying
		 */
		@Override
		public void crewMemberDead(CrewMember crewMember) {
			System.out.println("Crew member " + crewMember.getName() + " died");
		}

		/**
		 * Respond to a crew member catching a disease
		 */
		@Override
		public void crewMemberCaughtDisease(CrewMember crewMember, Disease disease) {
			System.out.println("Crew member " + crewMember.getName() + " caught disease: " + disease.getName());
		}
	}
	
	/**
	 * The bulk of the CLI is here. Inside a while loop the CLI checks the view states and 
	 * game state, gives appropriate options to the player, deals with input, updates 
	 * view states and game state and returns to the start of the while loop
	 */
	public void run(){
		
		Properties properties = Utilities.loadPropertiesFile(GAME_PROPERTIES_FILE);
		String gameName = properties.getProperty("gameName");
		
		System.out.println("Welcome to the " + gameName + " command line interface, you will select options by typing their numbers");
		//Thread.sleep(2000);
		
		int intResponse;
		boolean doubleBreak = false;
		
		while(true){
			switch(context){
			case SHOULD_EXIT:
				System.out.println("Exiting now...");
				return;
			case MAIN_MENU:
				System.out.println("You are in the main menu. Your options are:");
				System.out.println("1. View help");
				System.out.println("2. Start new game");
				System.out.println("3. Load saved game");
				System.out.println("4. Quit");
				intResponse = getInt(1, 4);
				switch(intResponse){
				case 1:
					context = CLIContext.HELP;
					break;
				case 2:
					context = CLIContext.START_GAME;
					break;
				case 3:
					context = CLIContext.LOAD_GAME;
					break;
				case 4:
					context = CLIContext.SHOULD_EXIT;
					break;
				default:
					System.out.println("This shouldn't happen");
					System.exit(1);
					break;
				}
				lastContext = CLIContext.MAIN_MENU;
				break;
			case START_GAME:
				int minDays = Integer.parseInt(properties.getProperty("minDays"));
				int maxDays = Integer.parseInt(properties.getProperty("maxDays"));
				
			
				System.out.println("How many days should the game last (" + minDays + "-" + maxDays + ")?");
				int days = getInt(minDays, maxDays);
				
				float piecesPerDay = Float.parseFloat(properties.getProperty("itemsPerDay"));
				
				int piecesRequired = (int) (piecesPerDay * days);
				
				int minCrew = Integer.parseInt(properties.getProperty("minCrew"));
				int maxCrew = Integer.parseInt(properties.getProperty("maxCrew"));
				ArrayList<String> roles = CrewMember.getAllRoles();
				
				System.out.println("You may now choose between " + minCrew + "-" + maxCrew + " crew members from these roles:");
				for(String role:roles){
					CrewMember rolePlaceholder = new CrewMember(role);
					System.out.println(rolePlaceholder.getRoleDescription());
				}
				
				int crewMembersAdded = 0;
				HashMap<String, CrewMember> crewMembers = new HashMap<String, CrewMember>();
				
				while(true){
					if(crewMembersAdded >= maxCrew){
						System.out.println("Crew is full");
						break;
					}
					if(crewMembersAdded >= minCrew){
						System.out.println("Continue adding members?");
						System.out.println("1. Yes");
						System.out.println("2. No");
						intResponse = getInt(1, 2);
						boolean stopAddingCrew = false;
						switch(intResponse){
						case 1:
							stopAddingCrew = false;
							break;
						case 2:
							stopAddingCrew = true;
							break;
						}
						if(stopAddingCrew){
							break;
						}
					}
					System.out.println("Enter a role name:");
					String role = scanner.nextLine();
					if(CrewMember.isValidRole(role)){
						System.out.println("Give this crew member a unique name:");
						while(true){
							String name = scanner.nextLine();
							if(crewMembers.containsKey(name)){
								System.out.println("That name is already taken, try again:");
							}else{
								System.out.println("Adding crew member " + name + " with role " + role);
								CrewMember crewMember = new CrewMember(role);
								crewMember.setName(name);
								crewMember.addCrewMemberUpdateListener(new CLIOutOfActionsListener());
								crewMembers.put(name, crewMember);
								crewMembersAdded += 1;
								break;
							}
						}
					}else{
						System.out.println("This is not a valid role name, try again:");
					}
				}
				
				System.out.println("Name your ship:");
				String shipName = scanner.nextLine();
				
				System.out.println("Starting game");
				
				gameState = new GameState(days, piecesRequired, crewMembers, properties);
				gameState.addCrewOutOfActionsListener(new CLIOutOfActionsListener());
				gameState.setShipName(shipName);
				context = CLIContext.IN_GAME;
				break;
			case LOAD_GAME:
				System.out.println("Here are the saved games:");
				ArrayList<String> saves = GameState.getSavedGameNames();
				for(String save : saves){
					System.out.println(save);
				}
				String saveName = null;
				System.out.println("Choose a save name:");
				while(true){
					saveName = scanner.nextLine();
					if(GameState.isValidSave(saveName)){
						gameState = GameState.loadSave(saveName);
						for(CrewMember member:gameState.getCrew().getMembers().values()) {
							member.addCrewMemberUpdateListener(new CLIOutOfActionsListener());
						}
						gameState.addCrewOutOfActionsListener(new CLIOutOfActionsListener());
						gameState.initCrewMemberOutOfActionsListeners();
						System.out.println("Game loaded");
						context = CLIContext.IN_GAME;
						break;
					}else{
						System.out.println("Not a valid save, choose again:");
					}
				}
				break;
			case HELP:
				System.out.println("Help:");
				//
				if(lastContext == CLIContext.IN_GAME && gameState.getLastViewContext() == GameStateViewContext.PAUSE_MENU){
					System.out.println("Press enter to return to pause menu");
					scanner.nextLine();
					context = CLIContext.IN_GAME;
				}else{
					System.out.println("Press enter to return to main menu");
					scanner.nextLine();
					context = CLIContext.MAIN_MENU;
				}
				break;
			case END_GAME:
				System.out.println("Game finished:");
				System.out.println("Ship name: " + gameState.getShipName());
				System.out.println("Days taken: " + gameState.getDaysElapsed());
				System.out.println("Pieces found: " + gameState.getPiecesCollected() + "/" + gameState.getPiecesRequired());
				System.out.println(gameState.getPiecesCollected() >= gameState.getPiecesRequired() ? "All pieces collected" : "Not all pieces collected");
				System.out.println("Final Score: " + gameState.getScore());
				
				System.out.println("Options:");
				System.out.println("1. Return to main menu");
				System.out.println("2. Exit");
				int result = getInt(1, 2);
				switch(result){
				case 1:
					context = CLIContext.MAIN_MENU;
					break;
				case 2:
					context = CLIContext.SHOULD_EXIT;
					break;
				}
				break;
			case IN_GAME:
				switch(gameState.getViewContext()){
				case CONSUME_MENU:
					System.out.println("You have these medical items:");
					for(MedicalItem medicalItem:gameState.getInventory().getMedicalItems().values()) {
						System.out.println(medicalItem.getAttributeDescription());
					}
					System.out.println("You have these food items:");
					for(FoodItem foodItem:gameState.getInventory().getFoodItems().values()) {
						System.out.println(foodItem.getAttributeDescription());
					}
					System.out.println("1. Return to Crew Menu");
					System.out.println("2. Choose a crew member to consume something");
					intResponse = getInt(1, 2);
					doubleBreak = false;
					switch(intResponse) {
					case 1:
						gameState.setViewContext(GameStateViewContext.CREW_MENU);
						doubleBreak = true;
						break;
					case 2:
						System.out.println("Your crew is:");
						System.out.println(gameState.getCrewStatus());
						System.out.println("Which crew member should consume something?");
						String crewMemberName = getCrewMemberWithAtLeastOneActionRemaining();
						System.out.println("What item should be consumed?");
						String itemName = getValidInventoryItemName();
						System.out.println("How many should be consumed?");
						int quantity = getInt(0, gameState.getInventory().getItems().get(itemName).getQuantity());
						try {
							gameState.haveCrewMemberConsumeItem(crewMemberName, itemName, quantity);
							System.out.println("Crew Member " + crewMemberName + " successfully consumed " + quantity + " " + itemName);
						} catch (ItemNotFoundException e3) {
							System.out.println("The requested item to consume was not found.");
						} catch (InsufficientQuantityException e3) {
							System.out.println("You do not have enough of that item to consume");
						} catch (NotConsumableException e3) {
							System.out.println("This item is not consumable");
						} catch (OutOfActionsException e) {
							System.out.println("This crew member is out of actions, did not consume");
						}
						break;
					}
					if(doubleBreak){
						break;
					}
					break;
				case CREW_MENU:
					System.out.println("You are on day " + (gameState.getDaysElapsed() + 1) + " with " + gameState.getPiecesCollected() + "/" + gameState.getPiecesRequired() + " pieces collected.");
					System.out.println("Crew/Ship Menu Options:");
					System.out.println("1. View Crew");
					System.out.println("2. Open pause menu");
					System.out.println("3. Open planet menu");
					System.out.println("4. Open space outpost menu");
					System.out.println("5. View ship shield health");
					System.out.println("6. Move to next day");
					System.out.println("7. Have a crew member consume an item");
					System.out.println("8. Have a crew member sleep");
					System.out.println("9. Have a crew member repair the ship's shield");
					
					intResponse = getInt(1, 9);
					switch(intResponse){
					case 1:
						System.out.println(gameState.getCrewStatus());
						break;
					case 2:
						gameState.setViewContext(GameStateViewContext.PAUSE_MENU);
						break;
					case 3:
						gameState.setViewContext(GameStateViewContext.PLANET_MENU);
						break;
					case 4:
						gameState.setViewContext(GameStateViewContext.SPACE_OUTPOST_MENU);
						break;	
					case 5:
						System.out.println(gameState.getShipShieldHealth());
						break;
					case 6:
						handleMovingToNextDay();
						break;
					case 7:
						gameState.setViewContext(GameStateViewContext.CONSUME_MENU);
						break;
					case 8:
						System.out.println("Which crew member should sleep?");
						for(CrewMember crewMember:gameState.getCrew().getMembers().values()){
							System.out.println(crewMember.getName() + " at tiredness " + crewMember.getTiredness() + "/" + crewMember.getMaxTiredness());
						}
						String sleepName;
						while(true){
							sleepName = scanner.nextLine();
							if(gameState.getCrew().getMembers().containsKey(sleepName)){
								if(gameState.getCrew().getMembers().get(sleepName).getActionsRemaining() <= 0){
									System.out.println("That crew member is out of actions, try again:");
								}else{
									break;
								}
							}else{
								System.out.println("Could not find that crew member, try again:");
							}
						}
						try {
							gameState.getCrew().getMembers().get(sleepName).sleep();
							System.out.println(sleepName + " has slept");
						} catch (OutOfActionsException e5) {
							System.out.println("Crew member is out of actions and cannot sleep");
						}
						break;
					case 9:
						System.out.println("Which crew member should repair the ship shield?");
						for(CrewMember crewMember:gameState.getCrew().getMembers().values()){
							System.out.println(crewMember.getName() + " with repair skill " + crewMember.getRepairSkill());
						}
						String repairName;
						while(true){
							repairName = scanner.nextLine();
							if(gameState.getCrew().getMembers().containsKey(repairName)){
								if(gameState.getCrew().getMembers().get(repairName).getActionsRemaining() <= 0){
									System.out.println("That crew member is out of actions, try again:");
								}else{
									break;
								}
							}else{
								System.out.println("Could not find that crew member, try again:");
							}
						}
						try {
							gameState.getCrew().getMembers().get(repairName).repair(gameState.getShip());
						} catch (OutOfActionsException e4) {
							System.out.println("This crew member is out of actions, ship not repaired");
						}
						break;
					}
					break;
				case PAUSE_MENU:
					System.out.println("Pause Menu Options:");
					System.out.println("1. Return to game");
					System.out.println("2. Save game");
					System.out.println("3. Return to main menu");
					System.out.println("4. Open help");
					intResponse = getInt(1, 4);
					switch(intResponse){
					case 1:
						gameState.setViewContext(GameStateViewContext.CREW_MENU);
						break;
					case 2:
						String desiredSaveName;
						while(true){
							System.out.println("Name your save:");
							desiredSaveName = scanner.nextLine();
							if(desiredSaveName == null || desiredSaveName.equals("")){
								System.out.println("Save name cannot be empty");
								continue;
							}
							if(GameState.isValidSave(desiredSaveName)){
								System.out.println("Save already exists, overwrite?");
								System.out.println("1. Yes");
								System.out.println("2. No");
								intResponse = getInt(1, 2);
								doubleBreak = false;
								switch(intResponse){
								case 1:
									gameState.saveAs(desiredSaveName);
									doubleBreak = true;
									break;
								case 2:
									break;
								}
								if(doubleBreak){
									break;
								}
							}else{
								gameState.saveAs(desiredSaveName);
								break;
							}
						}
						System.out.println("Game saved as " + desiredSaveName);
						
						break;
					case 3:
						context = CLIContext.MAIN_MENU;
						break;
					case 4:
						context = CLIContext.HELP;
						break;
					}
					gameState.setLastViewContext(GameStateViewContext.PAUSE_MENU);
					break;
				case PLANET_MENU:
					System.out.println("You are currently on " + gameState.getPlanet());
					System.out.println("1. Return to crew menu");
					System.out.println("2. Have a crew member search the planet (1 action)");
					System.out.println("3. Pilot to a different planet (2 crew members required, 1 action each)");
					intResponse = getInt(1, 3);
					switch(intResponse){
					case 1:
						gameState.setViewContext(GameStateViewContext.CREW_MENU);
						break;
					case 2:
						System.out.println("Which crew member should search the planet?");
						for(CrewMember crewMember:gameState.getCrew().getMembers().values()){
							System.out.println(crewMember.getName() + " with search skill " + crewMember.getSearchSkill());
						}
						String searchName;
						while(true){
							searchName = scanner.nextLine();
							if(gameState.getCrew().getMembers().containsKey(searchName)){
								if(gameState.getCrew().getMembers().get(searchName).getActionsRemaining() <= 0){
									System.out.println("That crew member is out of actions, try again:");
								}else{
									break;
								}
							}else{
								System.out.println("Could not find that crew member, try again:");
							}
						}
						InventoryItem itemFound = null;
						try {
							itemFound = gameState.searchCurrentPlanet(searchName);
						} catch (AllPartsFoundException e3) {
							System.out.println("All parts found");
							context = CLIContext.END_GAME;
							break;
						} catch (OutOfActionsException e) {
							System.out.println("This crew member is out of actions, could not search");
						}
						if(itemFound == null){
							System.out.println("Searching this planet found nothing");
						}else{
							System.out.println("Searching this planet found: " + itemFound.getDescription());
						}
						break;
					case 3:
						
						ArrayList<String> validPilots = new ArrayList<String>();
						for(CrewMember member:gameState.getCrew().getMembers().values()){
							if(member.canPilot()){
								validPilots.add(member.getName());
							}
						}
						
						System.out.println("Choose your first pilot from these crew members:");
						for(String allowedPilot : validPilots){
							System.out.println(allowedPilot);
						}
						
						String pilot1;
						while(true){
							pilot1 = scanner.nextLine();
							if(!gameState.getCrew().getMembers().containsKey(pilot1)){
								System.out.println("Could not find that crew member, try again:");
								continue;
							}
							if(gameState.getCrew().getMembers().get(pilot1).getActionsRemaining() <= 0){
								System.out.println("That crew member is out of actions, try again:");
								continue;
							}
							if(!validPilots.contains(pilot1)){
								System.out.println("Not a valid pilot, try again:");
								continue;
							}
							break;
						}
						
						System.out.println("Choose your second pilot from these crew members:");
						for(String allowedPilot : validPilots){
							if(!allowedPilot.equals(pilot1)){
								System.out.println(allowedPilot);
							}
						}
						String pilot2;
						
						while(true){
							pilot2 = scanner.nextLine();
							if(pilot2.equals(pilot1)){
								System.out.println("That pilot is already selected, try another:");
								continue;
							}
							if(!gameState.getCrew().getMembers().containsKey(pilot2)){
								System.out.println("Could not find that crew member, try again:");
								continue;
							}
							if(gameState.getCrew().getMembers().get(pilot2).getActionsRemaining() <= 0){
								System.out.println("That crew member is out of actions, try again:");
								continue;
							}
							if(!validPilots.contains(pilot2)){
								System.out.println("Not a valid pilot, try again:");
								continue;
							}
							break;
						}
						String planetName;
						System.out.println("Which of these planets would you like to travel to?");
						for(Planet planet:gameState.getPlanets().values()){
							if(!gameState.getPlanet().equals(planet.getName() )) {
								System.out.println(planet.getName());
							}
						}
						while(true){
							planetName = scanner.nextLine();
							if(gameState.getPlanets().containsKey(planetName)){
								if(planetName.equals(gameState.getPlanet())) {
									System.out.println("You are already on this planet, try a different one:");
									continue;
								}
								break;
							}else{
								System.out.println("Could not find that planet, try again:");
							}
						}
						try {
						boolean asteroidBeltEvent = false;
						asteroidBeltEvent = gameState.pilotShipTo(planetName, pilot1, pilot2);
						if(asteroidBeltEvent){
							System.out.println("An asteroid belt was encountered, shield health reduced");
						}
						System.out.println("Successfully travelled to " + planetName);
						}catch (OutOfActionsException e1) {
							System.out.println("Either pilot is out of actions, could not travel to " + planetName);
						}catch(InvalidCrewRoleException e2){
							System.out.println("At least one crew member selected cannot pilot, could not travel to " + planetName);
						}catch (ShieldHealthDepletedException e) {
							System.out.println("The ship's shield was destroyed, game ended.");
							context = CLIContext.END_GAME;
						}
						break;
					}
					break;
				case SPACE_OUTPOST_MENU:
					SpaceOutpost spaceOutpost = gameState.getSpaceOutpost();
					System.out.println("You are at space outpost " + spaceOutpost.getName());
					System.out.println(spaceOutpost.getDescription());
					System.out.println("Options:");
					System.out.println("1. Return to crew menu");
					System.out.println("2. Purchase an item");
					System.out.println("3. View your inventory");
					System.out.println("4. View an item's attributes");
					if(gameState.getSpaceOutposts().values().size() > 1){
						System.out.println("5. Move to a different space outpost");
						intResponse = getInt(1, 5);
					}else{
						intResponse = getInt(1, 4);
					}
					switch(intResponse){
					case 1:
						gameState.setViewContext(GameStateViewContext.CREW_MENU);
						break;
					case 2:
						System.out.println("Name the item you would like to purchase:");
						String itemName = "";
						while(true){
							itemName = scanner.nextLine();
							if(spaceOutpost.getItemsAvailable().containsKey(itemName) && spaceOutpost.getItemsAvailable().get(itemName).getQuantity() > 0){
								break;
							}else{
								System.out.println("This item is not available, try another:");
							}
						}
						System.out.println("How many would you like to purchase?");
						int purchaseQuantity = getInt(1, spaceOutpost.getItemsAvailable().get(itemName).getQuantity());
						int totalCost = spaceOutpost.getItemsAvailable().get(itemName).getPrice() * purchaseQuantity;
						System.out.println("Purchase " + purchaseQuantity + " " + itemName + "(s) at a total cost of " + totalCost + "?");
						System.out.println("1. Yes");
						System.out.println("2. No");
						intResponse = getInt(1, 2);
						switch(intResponse){
						case 1:
							if(gameState.purchaseItemFromCurrentSpaceOutpost(itemName, purchaseQuantity)){
								System.out.println("Item purchased successfully");
							}else{
								System.out.println("Item purchase failed, insufficient funds");
							}
							break;
						case 2:
							break;
						}
						break;
					case 3:
						System.out.println(gameState.getInventory().getDescription());
						break;
					case 4:
						System.out.println("Which item would you like to view?");
						itemName = "";
						while(true){
							itemName = scanner.nextLine();
							if(spaceOutpost.getItemsAvailable().containsKey(itemName)){
								break;
							}else{
								System.out.println("This item is not available, try another:");
							}
						}
						System.out.println(spaceOutpost.getItemsAvailable().get(itemName).getAttributeDescription());
						break;
					case 5:
						System.out.println("Space outposts you can move to:");
						for(SpaceOutpost otherOutpost:gameState.getSpaceOutposts().values()){
							System.out.println(otherOutpost.getName());
						}
						System.out.println("Enter the name of the space outpost you would like to move to:");
						String moveTo = scanner.nextLine();
						gameState.setSpaceOutpost(moveTo);
						break;
					}
					break;
				default:
					break;
				}
				lastContext = CLIContext.IN_GAME;
				break;
			}
		}
		//ask for days
	}

	/**
	 * Request the user gives the name of an item which exists in the crew's inventory.
	 * Pulled out of run due to repeated usage
	 * @return the item name
	 */
	private String getValidInventoryItemName() {
		String result = "";
		while(true) {
			result = scanner.nextLine();
			if(!gameState.getInventory().getItems().containsKey(result)) {
				System.out.println("Could not find that item, try again:");
			}
			break;
		}
		return result;
	}

	/**
	 * Get the name of a crew member who has at least one action remaining.
	 * Pulled out of run due to repeated usage
	 * @return the name of the crew member
	 */
	private String getCrewMemberWithAtLeastOneActionRemaining() {
		String result = "";
		while(true) {
			result = scanner.nextLine();
			if(!gameState.getCrew().getMembers().containsKey(result)) {
				System.out.println("Could not find that crew member, try again:");
			}
			if(!(gameState.getCrew().getMembers().get(result).getActionsRemaining() >= 1)) {
				System.out.println("That crew member does not have enough actions, try again:");
			}
			break;
		}
		return result;
	}
}
