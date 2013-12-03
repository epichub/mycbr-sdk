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

package de.dfki.mycbr.core.similarity;

import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.SpecialAttribute;
import de.dfki.mycbr.core.model.SpecialDesc;

/**
 * Special values such as "undefined" can be used as values
 * for any attribute description. This class defines how to 
 * compute the similarity of two special values. They are basically
 * treated like symbol attributes.
 * 
 * @author myCBR Team
 *
 */
public class SpecialFct extends SymbolFct {

	/**
	 * @param prj
	 * @param desc
	 * @param name
	 */
	public SpecialFct(Project prj, SpecialDesc desc, String name) {
		super(prj, desc, name);
	}

	/**
	 * Returns the similarity of the given attributes.
	 * Returns null if the attributes do not have the same description,
	 * if they are not of type SymbolAttribute or SpecialValueAttribute,
	 * or if there is something wrong with the index structure provided by 
	 * the given range.
	 * @param att1 first attribute
	 * @param att2 second attribute
	 * @return similarity of the given attributes, invalid similarity if an error occurs
	 * @throws Exception 
	 * 
	 */
	public Similarity calculateSimilarity(SpecialAttribute att1, SpecialAttribute att2) {
		Similarity result = Similarity.INVALID_SIM;
		
		// TODO should not happen
		if (att1==null) {
			att1 = (SpecialAttribute)prj.getSpecialAttribute(Project.UNDEFINED_SPECIAL_ATTRIBUTE);
		} 
		
		if (att2==null) {
			att2 = (SpecialAttribute)prj.getSpecialAttribute(Project.UNDEFINED_SPECIAL_ATTRIBUTE);
		} 
		
		if(att1.getAttributeDesc() != null && att1.getAttributeDesc().equals(att2.getAttributeDesc())) {
			Integer index1 = ((SpecialDesc)desc).getIndexOf(att1);
			Integer index2 = ((SpecialDesc)desc).getIndexOf(att2);
			if (index1!=null && index2!=null) {
				try {
					result = sims[index1][index2];
				} catch(ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
			
		}
		return result;
	}
}
