package test;

import static org.junit.Assert.*;
import main.CrewMember;
import main.OutOfActionsException;

import org.junit.Test;

public class CrewMemberTest {

	@Test
	public void testLoadFromProperties() {
		CrewMember cm = new CrewMember("test");
		assertEquals(cm.getName(), "testMember");
		
		assertEquals(cm.getHealth(), 123);
		assertEquals(cm.getHunger(), 0);
		assertEquals(cm.getTiredness(), 0);
		
		assertEquals(cm.getMaxHealth(), 123);
		assertEquals(cm.getMaxHunger(), 123);
		assertEquals(cm.getMaxTiredness(), 123);
		
		assertEquals(cm.getTirednessIncreasePerDay(), 123);
		assertEquals(cm.getTirednessDecreasePerSleep(), 123);
		
		assertEquals(cm.getRepairSkill(), 123);
		assertEquals(cm.getSearchSkill(), 123);
		
		assertEquals(cm.getActionsPerDay(), 123);
		assertEquals(cm.getActionsRemaining(), 123);
	}
	
	@Test
	public void testActionsBehaviour(){
		CrewMember cm = new CrewMember("test");
		assertEquals(cm.getActionsRemaining(), 123);
		try {
			cm.useActions(2);
		} catch (OutOfActionsException e) {
			fail();
		}
		assertEquals(cm.getActionsRemaining(), 121);
		try {
			cm.useActions(1000);
			fail();
		} catch (OutOfActionsException e) {
			
		}
		assertEquals(cm.getActionsRemaining(), 121);
		
		cm.setActionsRemaining(-1000);
		assertEquals(cm.getActionsRemaining(), 0);
		
		cm.setActionsRemaining(1000);
		assertEquals(cm.getActionsRemaining(), 123);
		
		try {
			cm.useActions(2);
		} catch (OutOfActionsException e) {
			fail();
		}
		cm.nextDay();
		assertEquals(cm.getActionsRemaining(), 123);
	}
}
