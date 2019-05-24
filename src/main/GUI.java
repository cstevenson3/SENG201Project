package main;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

/**
 * A Object called GUI that acts as the manager for interaction between a user and the Game via a graphical Interface
 * @author Teren Gubb
 *
 */
public class GUI {

	private static final String GAME_PROPERTIES_FILE = "gameProperties.properties";
	
	private GUIContext context;
	private GameState gameState;
	private GUIContext lastContext;
	private Properties properties;
	private int numberOfDays;
	private int numberOfCrew;
	private String shipName;
	private int piecesRequired;
	private int crewFinished;
	private HashMap<String, CrewMember> crewMembers;
	private boolean alienPirateBool;
	
	public GUI() {
		properties = Utilities.loadPropertiesFile(GAME_PROPERTIES_FILE);
		context = GUIContext.MAIN_MENU;
		lastContext = GUIContext.MAIN_MENU;
		crewMembers = new HashMap<String, CrewMember>();
		crewFinished = 0;
		render();
	}
	
	//setters
	
	/**
	 * Takes a GUIContext enumerator and sets the private variable context as it.
	 * @param newContext new GUIContext
	 */
	public void setGUIContext(GUIContext newContext) {
		context = newContext;
	}
	
	/**
	 * Takes a GUIContext enumerator and sets the private variable lastContext as it.
	 * @param newLastContext new GUIContext
	 */
	public void setLastGUIContext(GUIContext newLastContext) {
		lastContext = newLastContext;
	}
	
	/**
	 * Takes a GameState object and sets the private variable gameState as it.
	 * @param incomingGameState new GameState
	 */
	public void setGameStateContext(GameState incomingGameState) {
		gameState = incomingGameState; 
	}
	
	/**
	 * Takes an integer and sets the private variable numberOfDays as it.
	 * @param incomingNumberOfDays integer for number of days
	 */
	public void setNumberOfDays(int incomingNumberOfDays) {
		numberOfDays = incomingNumberOfDays;
	}
	
	/**
	 * Takes an integer and sets the private variable setNumberOfCrew as it.
	 * @param incomingNumberOfCrew integer for total crew
	 */
	public void setNumberOfCrew(int incomingNumberOfCrew) {
		numberOfCrew = incomingNumberOfCrew;
	}
	
	/**
	 * Takes a String and sets the private variable shipName to it.
	 * @param incomingShipName The ship name
	 */
	public void setShipName(String incomingShipName) {
		shipName = incomingShipName;
	}
	
	/**
	 * Takes an Integer and sets the private variable piecesRequired to it.
	 * @param incomingPiecesRequired The ship parts required to win
	 */
	public void setPiecesRequired(int incomingPiecesRequired) {
		piecesRequired = incomingPiecesRequired;
	}
	
	/**
	 * resets the crewMembers HashMap if the start is back out of.
	 */
	public void resetCrewMembers() {
		crewMembers = new HashMap<String, CrewMember>();
		crewFinished = 0;
	}
	
	/**
	 * causes the crewFinished variable to increase by one.
	 */
	public void addCrewFinished() {
		crewFinished = crewFinished + 1;
	}
	
	/**
	 * makes a GameState to initialize the game.
	 */
	public void makeGameState() {
		
		gameState = new GameState(numberOfDays, piecesRequired, crewMembers, properties);
		gameState.setShipName(shipName);
	}
	
	//getters
	
	/**
	 * returns the Integer numberOfDays.
	 * @return a integer for number of days
	 */
	public int getNumberOfDays() {
		return numberOfDays;
	}
	
	/**
	 * returns the Integer piecesRequired.
	 * @return a integer for pieces required
	 */
	public int getPiecesRequired() {
		return piecesRequired;
	}
	
	/**
	 * returns the Integer numberOfCrew.
	 * @return a integer for number of crew
	 */
	public int getNumberOfCrew() {
		return numberOfCrew;
	}
	
	/**
	 * returns a type Properties when prompted.
	 * @return a Properties file
	 */
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * returns the integer crewFinished.
	 * @return a integer for current finished crew
	 */
	public int getCrewFinished() {
		return crewFinished;
	}
	
	/**
	 * returns the current GameState.
	 * @return GameState type
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	/**
	 * returns the current GUIContext.
	 * @return GUIContext enumerator
	 */
	public GUIContext getGUIContext() {
		return context;
	}
	
	//Methods for the crewMembers HashMap
	
	/**
	 * takes a Crew Member name and returns a boolean of whether it resides in the crew member ArrayList. 
	 * @param incomingName The potential crew member name
	 * @return boolean for if the name exists or not.
	 */
	public boolean containsCrewMember(String incomingName) {
		return crewMembers.containsKey(incomingName);
	}
	
	/**
	 * adds an incomingName and incomingCrewMember object to the crewMembers ArrayList.
	 * @param incomingName The crew member's name
	 * @param incomingCrewMember The crew member
	 */
	public void addCrewMember(String incomingName, CrewMember incomingCrewMember) {
		crewMembers.put(incomingName, incomingCrewMember);
	}
	
	//Methods for IN_GAME functions
	
	/**
	 * facilitates the change of days for the game.
	 */
	public void handleMovingToNextDay() {
		try {
			gameState.moveToNextDay();
		} catch (AlienPirateEventException e) {
			alienPirateBool = true;
		} catch (OutOfDaysException e) {
			context = GUIContext.END_GAME;
		}
	}
	
	
	public class GUIOutOfActionsListener implements CrewMemberUpdateListener, Serializable{

		@Override
		public void crewOutOfActions() {
			System.out.println("All crew actions used today, moving to next day");
			handleMovingToNextDay();
		}

		@Override
		public void crewMemberOutOfActions(CrewMember crewMember) {
			System.out.println("Crew member " + crewMember.getName() + " has now used up all actions today");
		}

		@Override
		public void crewMemberDead(CrewMember crewMember) {
			
		}

		@Override
		public void crewMemberCaughtDisease(CrewMember crewMember, Disease disease) {
			
		}
		
	}
	
	/**
	 * handles the change between the main states of both the game and the menu system.
	 */
	public void render() {
		switch(context) {
		case MAIN_MENU:
			renderMainMenuWindow();
			break;
		case HELP:
			renderHelpMainMenuWindow();
			break;
		case LOAD_GAME:
			renderLoadGameWindow();
			break;
		case START_GAME:
			renderStartGameWindow();
			break;
		case CREW_CREATION:
			renderCrewMemberCreatorWindow();
			break;
		case END_GAME:
			renderEndGameWindow();
			break;
		case SHOULD_EXIT:
			break;
		case IN_GAME:
			switch(gameState.getViewContext()) {
			case CONSUME_MENU:
				renderConsumeMenuWindow();
				break;
			case CREW_MENU:
				renderCrewMenuWindow();
				break;
			case PAUSE_MENU:
				renderPauseMenuWindow();
				break;
			case PLANET_MENU:
				renderPlanetMenuWindow();
				break;
			case SPACE_OUTPOST_MENU:
				renderSpaceOutpostMenuWindow();
				break;
			default:
				break;
			}
		default:
			break;
		}
	}
	
	//window Render Methods that are called by the render() function.
	
	/**
	 * creates a MainMenuWindow object, and hands over control.
	 */
	public void renderMainMenuWindow() {
		MainMenuWindow mainMenu = new MainMenuWindow(this);
	}
	
	/**
	 * hands control back to the GUI, and closes the MainMenuWindow.
	 * @param mainMenu The main menu window
	 */
	public void closeMainMenuWindow(MainMenuWindow mainMenu) {
		mainMenu.closeWindow();
		render();
	}
	
	/**
	 * creates a HelpMainMenuWindow object, and hands over control.
	 */
	public void renderHelpMainMenuWindow() {
		HelpMainMenuWindow helpMainMenu = new HelpMainMenuWindow(this);
	}
	
	/**
	 * hands control back to the GUI, and closes the HelpMainMenuWindow.
	 * @param helpMainMenu The main menu help window
	 */
	public void closeHelpMainMenuWindow(HelpMainMenuWindow helpMainMenu) {
		helpMainMenu.closeWindow();
		render();
	}
	
	/**
	 * creates a LoadGameWindow object, and hands over control.
	 */
	public void renderLoadGameWindow() {
		LoadGameWindow loadGame = new LoadGameWindow(this);
	}
	
	/**
	 * hands control back to the GUI, and closes the LoadGameWindow.
	 * @param loadGame The load game window
	 */
	public void closeLoadGameWindow(LoadGameWindow loadGame) {
		loadGame.closeWindow();
		render();
	}
	
	/**
	 * creates a StartGameWindow, and hands over control.
	 */
	public void renderStartGameWindow() {
		StartGameWindow startGame = new StartGameWindow(this);
	}
	
	 /**
	  * hands control back to the GUI, and closes the StartGameWindow.
	  * @param startGame The start game window
	  */
	public void closeStartGameWindow(StartGameWindow startGame) {
		startGame.closeWindow();
		render();
	}
	
	 /**
	  * creates a CrewMemberCreatorWindow, and hands over control.
	  */
	public void renderCrewMemberCreatorWindow() {
		CrewMemberCreatorWindow crewMemberCreator = new CrewMemberCreatorWindow(this);
	}
	
	/**
	 * hands control back to the GUI, and closes the CrewMemberCreatorWindow.
	 * @param crewMemberCreator The crew member creation window
	 */
	public void closeCrewMemberCreatorWindow(CrewMemberCreatorWindow crewMemberCreator) {
		crewMemberCreator.closeWindow();
		render();
	}
	
	/**
	 * creates a EndGameWindow, and hands over control.
	 */
	public void renderEndGameWindow() {
		EndGameWindow endGame = new EndGameWindow(this);
	}
	
	/**
	 * hands control back to the GUI, and closes the EndGameWindow.
	 * @param endGame The end game window
	 */
	public void closeEndGameWindow(EndGameWindow endGame) {
		endGame.closeWindow();
		render();
	}
	
	/**
	 * creates a CrewMenuWindow, and hands over control.
	 */
	public void renderCrewMenuWindow() {
		CrewMenuWindow crewMenu = new CrewMenuWindow(this);
	}
	
	/**
	 * hands control back to the GUI, and closes the CrewMenuWindow.
	 * @param crewMenu The crew menu window
	 */
	public void closeCrewMenuWindow(CrewMenuWindow crewMenu) {
		crewMenu.closeWindow();
		render();
	}
	
	/**
	 * creates a ConsumeMenuWindow, and hands over control.
	 */
	public void renderConsumeMenuWindow() {
		ConsumeMenuWindow consumeMenu = new ConsumeMenuWindow(this);
	}
	
	/**
	 * hands control back to the GUI, and closes the ConsumeMenuWindow.
	 * @param consumeMenu The consume menu window
	 */
	public void closeConsumeMenuWindow(ConsumeMenuWindow consumeMenu) {
		consumeMenu.closeWindow();
		render();
	}
	
	/**
	 * creates a PauseMenuWindow, and hands over control.
	 */
	public void renderPauseMenuWindow() {
		PauseMenuWindow pauseMenu = new PauseMenuWindow(this);
	}
	
	/**
	 * hands control back to the GUI, and closes the PauseMenuWindow.
	 * @param pauseMenu The pause menu window
	 */
	public void closePauseMenuWindow(PauseMenuWindow pauseMenu) {
		pauseMenu.closeWindow();
		render();
	}
	
	 /**
	  * creates a SpaceOutpostMenuWindow, and hands over control.
	  */
	public void renderSpaceOutpostMenuWindow() {
		SpaceOutpostMenuWindow spaceOutpostMenu = new SpaceOutpostMenuWindow(this);
	}
	
	 /**
	  * hands control back to the GUI, and closes the SpaceOutpostMenuWindow.
	  * @param spaceOutpostMenu The space outpost menu window
	  */
	public void closeSpaceOutpostMenuWindow(SpaceOutpostMenuWindow spaceOutpostMenu) {
		spaceOutpostMenu.closeWindow();
		render();
	}
	
	/**
	 * creates a PlanetMenuWindow, and hands over control.
	 */
	public void renderPlanetMenuWindow() {
		PlanetMenuWindow PlanetMenu = new PlanetMenuWindow(this);
	}
	
	/**
	 * hands control back to the GUI, and closes the PlanetMenuWindow.
	 * @param planetMenu The planet menu window
	 */
	public void closePlanetMenuWindow(PlanetMenuWindow planetMenu) {
		planetMenu.closeWindow();
		render();
	}
	
	/**
	 * closes the SaveGameWindow, and renders the game.
	 * @param saveGame The save game window
	 */
	public void closeSaveGameWindow(SaveGameWindow saveGame) {
		saveGame.closeWindow();
		render();
	}
}
