package main;

import java.io.Serializable;

/**
 * 
 * An interface for listening to updates in crew members
 * @author Cameron Stevenson
 *
 */
public interface CrewMemberUpdateListener{
	/**
	 * Called when the crew is out of actions today
	 */
	public void crewOutOfActions();
	/**
	 * Called when a crew member is out of actions today
	 * @param crewMember The crew member
	 */
	public void crewMemberOutOfActions(CrewMember crewMember);
	/**
	 * Called when a crew member dies
	 * @param crewMember The crew member
	 */
	public void crewMemberDead(CrewMember crewMember);
	/**
	 * Called when a crew member catches a disease
	 * @param crewMember The crew member
	 */
	public void crewMemberCaughtDisease(CrewMember crewMember, Disease disease);
}
