package main;

/**
 * 
 * The possible view states which GameState is not aware of (GameState represents a single
 * save so only exists when the game is running)
 * 
 * @author Cameron Stevenson
 *
 */
public enum CLIContext{
	SHOULD_EXIT,
	MAIN_MENU,
	START_GAME,
	LOAD_GAME,
	HELP,
	END_GAME,
	IN_GAME
}
