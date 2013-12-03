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
 * When any part of a myCBR model is renamed, a rename action
 * can be used by that concept to notify its obsevers about the
 * rename action.
 * 
 * @author myCBR Team
 *
 */
public class RenameAction implements Action {

	private String oldName;
	private Observable o;
	
	public RenameAction(Observable o, String oldName) {
		this.o = o;
		this.oldName = oldName;
	}
	
	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.action.Action#getData()
	 */
	@Override
	public Object getData() {
		return oldName;
	}

	public String getOldName() {
		return oldName;
	}
	
	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.action.Action#getType()
	 */
	@Override
	public ActionType getType() {
		return ActionType.RenameAction;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.action.Action#getObservable()
	 */
	@Override
	public Observable getObservable() {
		return o;
	}

}
