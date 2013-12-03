/*
 * myCBR License 2.0
 *
 * Copyright (c) 2010
 * Thomas Roth-Berghofer, Armin Stahl & German Research Center for Artificial
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

import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.model.SpecialDesc;
import de.dfki.mycbr.core.similarity.Similarity;

/**
 * Represents special values in queries or cases. The semantics can be defined
 * separately. There is one SpecialValueAttribute for each attribute description
 * that wraps one SpecialValue object. This assures consistency since we expect
 * that each value occurring in query or case knows its attribute description
 * and is of type <code>SimpleAttribute</code>. Objects of this class are
 * maintained by ranges defined in AttributeDesc objects.
 *
 * @author myCBR Team
 *
 */
public final class SpecialAttribute extends SymbolAttribute {

    /**
     *
     */
    private Project prj;

    /**
     * Wraps the special value as SimpleAttribute object,
     * so that it can be used as value in
     * query and case.
     *
     * @param p
     *            the project for this attribute
     * @param v
     *            the special value to be wrapped
     */
    SpecialAttribute(final Project p, SpecialDesc d, final String v) {
        super(d, v);
        this.prj = p;
    }

    /**
     * Returns similarity of this attribute to the given attribute. Calls
     * {@link Project#calculateSpecialSimilarity(Attribute, Attribute)}
     * to calculate the requested similarity
     *
     * @param att the attribute this should be compared with
     * @return similarity of the given attributes
     * @throws Exception if something went wrong during similarity calculations
     */
    public Similarity calculateSimilarity(final SimpleAttribute att)
                                                            throws Exception {
        return prj.calculateSpecialSimilarity(this, att);
    }

}
