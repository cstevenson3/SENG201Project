package test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import main.Crew;
import main.CrewMember;

public class CrewTest {

	
	@Test
	public void testCrew() {
		CrewMember crewMember1 = new CrewMember("Engineer");
		CrewMember crewMember2 = new CrewMember("Explorer");
		HashMap<String, CrewMember> crewMembers = new HashMap<String, CrewMember>();
		crewMembers.put("Dave", crewMember1);
		crewMembers.put("Garry", crewMember2);
		Crew crew = new Crew(crewMembers);
		
		assertEquals(crew.getMembers(), crewMembers);
		
		HashMap<String, CrewMember> crewMembersWithoutDave = new HashMap<String, CrewMember>();
		crewMembersWithoutDave.put("Garry", crewMember2);
		crew.removeMember("Dave");
		assertEquals(crew.getMembers(), crewMembersWithoutDave);
	}

}
