/*
 * myCBR License 2.0
 *
 * Copyright (c) 2010
 * Thomas Roth-Berghofer, Armin Stahl & German Research Center For Artificial
 * Intelligence DFKI GmbH
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

import java.util.Date;

import de.dfki.mycbr.core.model.DateDesc;

/**
 * Represents date attributes. The format of this date attribute is given by the
 * range that maintains this attribute.
 *
 * @author myCBR Team
 *
 */
public final class DateAttribute extends SimpleAttribute {

    /**
     *
     */
    private Date date;

    /**
     * Initializes this.
     *
     * @param desc
     *            the date description of this attribute
     * @param d
     *            the value of this attribute
     */
    DateAttribute(final DateDesc desc, final Date d) {
        super(desc);
        this.date = d;
    }

    /**
     * Returns the value of this attribute.
     *
     * @return the date representing the value
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Returns a string representation of this date.
     * @return the string representation of this
     */
    @Override
    public String getValueAsString() {
        return getDate().toString();
    }

}
