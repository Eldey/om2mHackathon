package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

/**
 * This class describes a Door. A door has two possible states : - false: the
 * door is closed - true: the door is opened
 * 
 * @author Dr. Nicolas Verstaevel - nicolasv@uow.edu.au
 * @version 0.1
 *
 */
public class Door extends Device<Boolean> {

	public Door(String id, Room room, RequestSender requestSender) {
		super(id, room, requestSender);
		this.setState(false);
	}
	
	@Override
	Boolean stateFromString(String state) {
		return Boolean.parseBoolean(state);
	}
	
	@Override
	String getStringState() {
		if (this.getState()) {
			return "OPEN";
		} else {
			return "CLOSE";
		}
	}

}
