/*
 * myCBR License 2.0
 *
 * Copyright (c) 2010
 * Thomas Roth-Berghofer, Armin Stahl & Deutsches Forschungszentrum f&uuml;r
 * K&uuml;nstliche Intelligenz DFKI GmbH
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

package de.dfki.mycbr.core.casebase;

import java.util.Observable;

/**
 * General abstract class representing values of attribute descriptions
 * in cases or instances.
 * Known subclasses: SimpleAttribute and Instance.
 *
 * @author myCBR Team
 *
 */
public abstract class Attribute extends Observable {

    /**
     * Returns a string representation of this attribute's value. Mostly used
     * for saving cases to internal XML file.
     *
     * @return the value of this attribute as string
     */
    public abstract String getValueAsString();

}
