/*
 * myCBR License 2.0
 *
 * Copyright (c) 2010
 * Thomas Roth-Berghofer, Armin Stahl & German Research Center of Artificial
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

import java.util.List;

import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.similarity.TaxonomyNode;

/**
 * Represents values of symbol attribute descriptions (for example: color could
 * have green, blue, red, ... as <code>SymbolAttribute</code> objects). A symbol
 * attribute depends on a description (so if you have two attribute descriptions
 * for color there would be two <code>SymbolAttribute</code> objects for green).
 * However, if green (for a fixed description) appears in several cases, the
 * corresponding <code>SymbolAttribute</code> object is referenced.
 * <code>SymbolAttribut</code> objects are maintained by
 * <code>SymbolRange</code>
 *
 * @author myCBR Team
 *
 */
public class SymbolAttribute extends SimpleAttribute implements TaxonomyNode {

    /**
     * String representation of this attribute's value.
     */
    private String value;

    /**
     * Creates new symbol with the specified value for the specified
     * description. It is assumed that this value is an allowed value of the
     * given description.
     *
     * @param desc
     *            the description having value as allowed value
     * @param v
     *            the value representing this symbol
     */
    public SymbolAttribute(final SymbolDesc desc, final String v) {
        super(desc);
        this.value = v;
    }

    /**
     * Updates the value of this symbol. It is assumed that the symbol range
     * which maintains this symbol attribute, does not contain another symbol
     * attribute with this value.
     *
     * @param v
     *            the value to be used for this symbol
     */
    public final void setValue(final String v) {
        if (((SymbolDesc) getDesc()).isAllowedValue(v)) {
            return; // value already contained in description
        }
        if(((SymbolDesc) getDesc()).renameValue(this.value, v)) {
            this.value = v;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Returns the value of this symbol. This value is unique within the scope
     * of this attribute's description.
     *
     * @return the value representing this symbol
     */
    public final String getValue() {
        return value;
    }

    /**
     * Returns string representation of this symbol. Returns the result of
     * {@link #getValue()}
     *
     * @return the string representation of this symbol
     */
    public final String toString() {
        return getValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.dfki.mycbr.core.casebase.SimpleAttribute#getValueAsString()
     */
    /**
     * Returns a string representation of this attribute.
     *
     * @return string representation of this attribute
     */
    @Override
    public final String getValueAsString() {
        return getValue();
    }

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.TaxonomyNode#getNodes()
	 */
	@Override
	public List<TaxonomyNode> getNodes() {
		return ((SymbolDesc)getDesc()).getNodes();
	}

}
