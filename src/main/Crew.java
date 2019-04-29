package main;

import java.io.Serializable;
import java.util.HashMap;

public class Crew implements Serializable{
	private HashMap<String, CrewMember> crewMembers;
	
	public Crew(HashMap<String, CrewMember> crewMembers){
		this.crewMembers = crewMembers;
	}

	public HashMap<String, CrewMember> getMembers() {
		return crewMembers;
	}
}
