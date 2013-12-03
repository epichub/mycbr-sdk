/*
 * myCBR License 2.0
 *
 * Copyright (c) 2010
 * Thomas Roth-Berghofer, Armin Stahl & German Research Center For Artificial
 * Intelligence DFKI GmbH.
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

import de.dfki.mycbr.core.model.IntegerDesc;

/**
 * Represents integers in query/cases. To avoid unnecessary objects, objects of
 * this class should only be created by integer ranges. For a fixed integer #
 * description, the integer attributes will then be referenced.
 *
 * @author myCBR Team
 */
public final class IntegerAttribute extends SimpleAttribute implements
        Comparable<IntegerAttribute> {

    /**
     *
     */
    private Integer value;

    /**
     * Creates a new Integer attribute for the given descirption with the given
     * value.
     *
     * @param desc
     *            the description this attribute belongs to
     * @param v
     *            the value of this attribute
     */
    IntegerAttribute(final IntegerDesc desc, final int v) {
        super(desc);
        this.value = v;
    }

    /**
     * Returns the numeric value of this attribute.
     *
     * @return the value of this attribute
     */
    public int getValue() {
        return value;
    }

    /**
     * Compares this attribute to the given one. Returns 0 if the values are
     * identical, -1 if this value is smaller than the given attribute's value
     * and 1 otherwise.
     *
     * @param o the attribute to compare this to
     * @return comparison result of the respective attributes
     */
    public int compareTo(final IntegerAttribute o) {
        if (this.value == o.value) {
            return 0;
        } else if (this.value.floatValue() < o.value.floatValue()) {
            return -1;
        } else {
            return 1;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.dfki.mycbr.core.casebase.SimpleAttribute#getValueAsString()
     */
    @Override
    /**
     * Returns a string representation of this attribute's value.
     * @return string representation of the value
     */
    public String getValueAsString() {
        return Integer.toString(getValue());
    }
}
