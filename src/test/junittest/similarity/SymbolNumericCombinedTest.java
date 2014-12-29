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

package test.junittest.similarity;

import java.util.Arrays;
import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.Test;

import de.dfki.mycbr.core.casebase.Attribute;
import de.dfki.mycbr.core.casebase.DoubleAttribute;
import de.dfki.mycbr.core.casebase.IntegerAttribute;
import de.dfki.mycbr.core.model.DoubleDesc;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.similarity.AmalgamationFct;
import de.dfki.mycbr.core.similarity.DoubleFct;
import de.dfki.mycbr.core.similarity.IntegerFct;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.core.similarity.SymbolNumericCombinedFct;
import de.dfki.mycbr.core.similarity.StringFct;
import de.dfki.mycbr.core.similarity.SymbolFct;
import de.dfki.mycbr.core.similarity.config.DistanceConfig;
import de.dfki.mycbr.core.similarity.config.StringConfig;
import test.junittest.TestFramework;

/**
 * @author myCBR Team
 *
 */
public class SymbolNumericCombinedTest extends TestCase {

	@Test
	public void testCalculateSimilarityL() {

		try {
			TestFramework frame = new TestFramework();
			HashSet<String> meatTypes = new HashSet<String>();
			String[] mealsArray = { "bacon", "salami", "mortadella"};
			meatTypes.addAll(Arrays.asList(mealsArray));
			SymbolDesc symbolDesc = new SymbolDesc(frame.carDesc,"meatTypes", meatTypes);
			symbolDesc.addSymbolFct("MySymbolFct", true);
			DoubleDesc doubleDesc = new DoubleDesc(frame.carDesc, "amount", 0, 9999);
			DoubleFct doubleFct = doubleDesc.addDoubleFct("MyDoubleFct", true);
			doubleFct.setSymmetric(true);
			doubleFct.setDistanceFct(DistanceConfig.DIFFERENCE);
			SymbolNumericCombinedFct f = new SymbolNumericCombinedFct(frame.prj, symbolDesc, doubleDesc);
			f.setSimilarity("salami", "bacon", 0.64);
			f.setSimilarity("bacon", "salami", 1.00);
			f.setSimilarity("salami", "salami", 1.00);
			f.setSimilarity("bacon", "bacon", 1.00);
//			f.setSimilarity("bacon", "mortadella", 0.70);
//			f.setSimilarity("salami", "mortadella", 0.60);
//			f.setSimilarity("mortadella", "bacon", 1.50);
//			f.setSimilarity("mortadella", "salami", 1.30);
//			f.setSimilarity("mortadella", "mortadella", 1.00);
			
			

			DoubleAttribute amount1 = (DoubleAttribute)doubleDesc.getAttribute(100);
			DoubleAttribute amount2 = (DoubleAttribute)doubleDesc.getAttribute(30);
			f.setDoubleAttr1(amount1);
			f.setDoubleAttr2(amount2);
			assertTrue(f.calculateSimilarity(symbolDesc.getAttribute("salami"),	symbolDesc.getAttribute("bacon")).equals(Similarity.get(0.50)));
			assertTrue(f.calculateSimilarity(symbolDesc.getAttribute("bacon"), symbolDesc.getAttribute("salami")).equals(Similarity.get(0.80)));
			assertTrue(f.calculateSimilarity(symbolDesc.getAttribute("bacon"), symbolDesc.getAttribute("bacon")).equals(Similarity.get(1.00)));
			assertTrue(f.calculateSimilarity(symbolDesc.getAttribute("salami"),	symbolDesc.getAttribute("salami")).equals(Similarity.get(1.00)));
			//assertTrue(f.calculateSimilarity(symbolDesc.getAttribute("salami"),	symbolDesc.getAttribute("mortadella")).equals(Similarity.get(1.30)));

		} catch (Exception exp) {
			assertTrue(
					"Excpetion in SymbolNumericCombinedTest: testCalculateSimilarityL",
					false);
		}

	}

}
