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

package de.dfki.mycbr.junittest.similarity;

import junit.framework.TestCase;

import org.junit.Test;

import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.core.similarity.SymbolFct;
import de.dfki.mycbr.junittest.TestFramework;

/**
 * @author myCBR Team
 *
 */
public class SymbolFunctionTest extends TestCase {

	/**
	 * Test method for {@link de.dfki.mycbr.core.similarity.SymbolFct#calculateSimilarity(Attribute, Attribute)}.
	 */
	@Test
	public void testCalculateSimilarity() {
		
		try {
			TestFramework frame = new TestFramework();
			SymbolFct f = frame.colorDesc.addSymbolFct("f1", true);
			// same values
			assertTrue(f.calculateSimilarity(frame.colorDesc.getAttribute("green"), frame.colorDesc.getAttribute("green")).equals(Similarity.get(1.00)));
			// different values
			assertTrue(f.calculateSimilarity(frame.colorDesc.getAttribute("green"), frame.colorDesc.getAttribute("red")).equals(Similarity.get(0.00)));
			f.setSimilarity("green", "red", 0.35);
			assertTrue(f.calculateSimilarity(frame.colorDesc.getAttribute("green"), frame.colorDesc.getAttribute("red")).equals(Similarity.get(0.35)));
			if (f.isSymmetric()) {
				assertTrue(f.calculateSimilarity(frame.colorDesc.getAttribute("red"), frame.colorDesc.getAttribute("green")).equals(Similarity.get(0.35)));	
			} else {
				assertTrue(f.calculateSimilarity(frame.colorDesc.getAttribute("red"), frame.colorDesc.getAttribute("green")).equals(Similarity.get(0.00)));
			}
		} catch(Exception e) {
			assertTrue("Excpetion in SymbolFctTest: testCalculateSimilarity",false);
		}
	}

}
