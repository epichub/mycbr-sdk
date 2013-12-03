/*
 * myCBR License 2.0
 *
 * Copyright (c) 2009
 * Thomas Roth-Berghofer, Armin Stahl & Deutsches Forschungszentrum f&uuml;r K&uuml;nstliche Intelligenz DFKI GmbH
 * Further contributors: myCBR Team (see http://mycbr-project.net/contact.html for further information 
 * about the myCBR Team). 
 * All rights reserved.
 *
 * myCBR is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Since myCBR uses some modules, you should be aware of their licenses for
 * which you should have received a copy along with this program, too.
 * 
 * endOfLic */

package de.dfki.mycbr.core.action;

import java.util.Observable;

/**
 * When a concept or description is deleted, a delete action
 * can be used to notify observers about the deletion.
 * 
 * @author myCBR Team
 *
 */
public class DeleteAction implements Action {

	private Observable observable;
	private Object deletedObject;
	
	public DeleteAction(Observable observable, Object deletedObject) {
		this.deletedObject = deletedObject;
		this.observable = observable;
	}
	
	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.action.Action#getData()
	 */
	@Override
	public Object getData() {
		return deletedObject;
	}

	public Object getDeletedObject() {
		return deletedObject;
	}
	
	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.action.Action#getObservable()
	 */
	@Override
	public Observable getObservable() {
		return observable;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.action.Action#getType()
	 */
	@Override
	public ActionType getType() {
		return ActionType.DeleteAction;
	}

}
