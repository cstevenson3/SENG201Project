package main;

import java.io.Serializable;

public interface OutOfActionsListener{
	public void crewOutOfActions();
	public void crewMemberOutOfActions(CrewMember crewMember);
}
