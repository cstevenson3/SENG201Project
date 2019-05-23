package main;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * A crew, consisting of crew members
 * 
 * @author Cameron Stevenson
 *
 */
public class Crew implements Serializable{
	
	/**
	 * The crew members
	 */
	private HashMap<String, CrewMember> crewMembers;
	
	/**
	 * Init
	 * @param crewMembers The crew members
	 */
	public Crew(HashMap<String, CrewMember> crewMembers){
		this.crewMembers = crewMembers;
	}

	/**
	 * Get the crew members
	 * @return A HashMap from names to the associated crew members
	 */
	public HashMap<String, CrewMember> getMembers() {
		return crewMembers;
	}

	/**
	 * Remove a crew member from the crew
	 */
	public void removeMember(String name) {
		crewMembers.remove(name);
	}
}
