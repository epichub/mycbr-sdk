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

import de.dfki.mycbr.core.model.IntervalDesc;
import de.dfki.mycbr.util.Pair;

/**
 * Represents intervals as values in query/case. Objects of this class should
 * only be created by interval ranges to avoid unnecessary objects.
 *
 * @author myCBR Team
 *
 */
public final class IntervalAttribute extends SimpleAttribute {

    /**
     * the value of this interval.
     */
    private Pair<Number, Number> interval;

    /**
     * Initializes this interval attribute.
     *
     * @param d
     *            the description of this attribute
     * @param min
     *            the lower bound of the underlying interval to be created
     * @param max
     *            the upper bound of the underlying interval to be created
     */
    IntervalAttribute(final IntervalDesc d, final float min, final float max) {
        super(d);
        this.interval = new Pair<Number, Number>(min, max);
    }

    /**
     * Initializes this with the given interval object as value.
     *
     * @param d
     *            the description of this attribute
     * @param i
     *            the value of this attribute
     */
    IntervalAttribute(final IntervalDesc d, final Pair<Number, Number> i) {
        super(d);
        this.interval = i;
    }

    /**
     * Gets the value of this attribute.
     *
     * @return the interval representing the value of this attribute
     */
    public Pair<Number, Number> getInterval() {
        return interval;
    }

    /**
     * Returns the string representation of this attribute.
     *
     * @return the string representing the value of this attribute
     */
    public String toString() {
        return interval.toString();
    }

	@Override
	public boolean equals(Object o) {
		if (o instanceof IntervalAttribute) {
			IntervalAttribute i = (IntervalAttribute) o;
			if (i.getDesc().equals(this) && interval.equals(i.interval)) {
				return true;
			}
		}
		return false;
	}
	
    /*
     * (non-Javadoc)
     *
     * @see de.dfki.mycbr.core.casebase.SimpleAttribute#getValueAsString()
     */
    @Override
    /**
     * Returns a string representation of this attribute's value.
     * @return string representation of the value.
     */
    public String getValueAsString() {
        return toString();
    }

}
