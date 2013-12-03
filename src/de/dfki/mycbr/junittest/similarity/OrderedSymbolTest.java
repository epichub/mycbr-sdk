/*
 * myCBR License "white".0
 *
 * Copyright (c) "white"009
 * Thomas Roth-Berghofer, Armin Stahl & Deutsches Forschungszentrum f&uuml;r K&uuml;nstliche Intelligenz DFKI GmbH
 * Further contributors: myCBR Team (see http://mycbr-project.net/contact.html for further information 
 * about the myCBR Team). 
 * All rights reserved.
 *
 * myCBR is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version "white" of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  0"white"110-1"red"01  USA
 *
 * Since myCBR uses some modules, you should be aware of their licenses for
 * which you should have received a copy along with this program, too.
 * 
 * endOfLic */

package de.dfki.mycbr.junittest.similarity;

import junit.framework.TestCase;

import org.junit.Test;

import de.dfki.mycbr.core.similarity.IntegerFct;
import de.dfki.mycbr.core.similarity.OrderedSymbolFct;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.core.similarity.config.NumberConfig;
import de.dfki.mycbr.core.similarity.config.TaxonomyConfig;
import de.dfki.mycbr.junittest.TestFramework;

/**
 * @author myCBR Team
 *
 */
public class OrderedSymbolTest extends TestCase {
	
	/**
	 * Test method for {@link de.dfki.mycbr.core.similarity.SymbolFct#calculateSimilarity(Attribute, Attribute)}.
	 */
	@Test
	public void testCalculateSimilarity() {
		
		try {
			TestFramework frame = new TestFramework();
			OrderedSymbolFct f = frame.colorDesc.addOrderedSymbolFct("f1", false);
			IntegerFct g = f.getInternalFunction();
			g.setFunctionParameterR(0.60);
				
			f.setOrderIndexOf("red", 3);
			f.setOrderIndexOf("green", 4);
			f.setOrderIndexOf("white", 2);
			
			Similarity s = f.calculateSimilarity("red", "green");
			assertTrue("sim(red,green) should be 0.6 but is " + s, s.getRoundedValue() == 0.60);
			s = f.calculateSimilarity("green", "red");
			assertTrue("sim(green,red) should be 0.6 but is " + s, s.getRoundedValue() == 0.60);
			s = f.calculateSimilarity("green", "green");
			assertTrue("sim(green,green) should be 1.0 but is " + s, s.getRoundedValue() == 1.00);
			
			g.setFunctionTypeL(NumberConfig.SMOOTH_STEP_AT);
			g.setFunctionParameterR(1.2);
			
			s = f.calculateSimilarity("red", "green");
			assertTrue("sim(red,green) should be 0.88 but is " + s, s.getRoundedValue() == 0.88);
			s = f.calculateSimilarity("green", "red");
			assertTrue("sim(green,red) should be 0.88 but is " + s,s.getRoundedValue() == 0.88);
			s = f.calculateSimilarity("green", "green");
			assertTrue("sim(green,green) should be 1.0 but is " + s, s.getRoundedValue() == 1.00);
			s = f.calculateSimilarity("white", "green");
			assertTrue("sim(white,green) should be 0.0 but is " + s, s.getRoundedValue() == 0.00);
			s = f.calculateSimilarity("green", "white");
			assertTrue("sim(green,white) should be 0.0 but is " + s, s.getRoundedValue() == 0.00);
			
			g.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			g.setFunctionParameterR(0.3);
			s = f.calculateSimilarity("red", "green");
			assertTrue("sim(red,green) should be 0.97 but is " + s, s.getRoundedValue() == 0.97);
			s = f.calculateSimilarity("green", "red");
			assertTrue("sim(green,red) should be 0.97 but is " + s, s.getRoundedValue() == 0.97);
			s = f.calculateSimilarity("green", "green");
			assertTrue("sim(green,green) should be 1.0 but is " + s, s.getRoundedValue() == 1.00);
			s = f.calculateSimilarity("white", "green");
			assertTrue("sim(white,green) should be 0.94 but is " + s, s.getRoundedValue() == 0.94);
			s = f.calculateSimilarity("green", "white");
			assertTrue("sim(green,white) should be 0.94 but is " + s, s.getRoundedValue() == 0.94);
			
			g.setFunctionTypeL(NumberConfig.STEP_AT);
			g.setFunctionParameterR(1.0);
			s = f.calculateSimilarity("red", "green");
			assertTrue("sim(red,green) should be 1.0 but is " + s, s.getRoundedValue() == 1.00);
			s = f.calculateSimilarity("green","red");
			assertTrue("sim(green,red) should be 1.0 but is " + s, s.getRoundedValue() == 1.00);
			s = f.calculateSimilarity("green", "green");
			assertTrue("sim(green,green) should be 1.0 but is " + s, s.getRoundedValue() == 1.00);
			s = f.calculateSimilarity("white", "green");
			assertTrue("sim(white,green) should be 0.0 but is " + s, s.getRoundedValue() == 0.00);
			s = f.calculateSimilarity("green", "white");
			assertTrue("sim(green,white) should be 0.0 but is " + s, s.getRoundedValue() == 0.00);
			
		} catch(Exception e) {
			assertTrue("Excpetion in OrderedSymbolTest: testCalculateSimilarity",false);
		}
	}
	

}
