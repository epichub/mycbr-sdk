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

package de.dfki.mycbr.junittest;

import junit.framework.Test;
import junit.framework.TestSuite;
import de.dfki.mycbr.junittest.casebase.ConceptWith2PartOfTest;
import de.dfki.mycbr.junittest.casebase.OverrideAttributeTest;
import de.dfki.mycbr.junittest.casebase.OverrideAttributeTest2;
import de.dfki.mycbr.junittest.model.ConceptDescTest;
import de.dfki.mycbr.junittest.model.ConceptTest;
import de.dfki.mycbr.junittest.similarity.AdvancedNumberTest;
import de.dfki.mycbr.junittest.similarity.OrderedSymbolTest;
import de.dfki.mycbr.junittest.similarity.StandardNumberTest;
import de.dfki.mycbr.junittest.similarity.StringFunctionTest;
import de.dfki.mycbr.junittest.similarity.SymbolFunctionTest;
import de.dfki.mycbr.junittest.similarity.TaxonomyTest;
import de.dfki.mycbr.junittest.similarity.TrigramTest;

/**
 * Runs all jUnit tests 
 * 
 * @author myCBR Team
 *
 */
public class AllTests {

	public static Test suite() {
		
		TestSuite suite = new TestSuite("myCBR SDK Tests");
		//$JUnit-BEGIN$
		// package
		suite.addTestSuite(MultipleAttTest.class);
		suite.addTestSuite(MyCBRImportTest.class);
		suite.addTestSuite(RetrievalTest.class);
		suite.addTestSuite(TrigramTest.class);
		
		// case base package
		suite.addTestSuite(ConceptWith2PartOfTest.class); 
		suite.addTestSuite(OverrideAttributeTest.class); 
		suite.addTestSuite(OverrideAttributeTest2.class); 
		
		// model package
		// suite.addTestSuite(BooleanDescTest.class); TODO
		// suite.addTestSuite(ChangeDescTest.class); TODO
		suite.addTestSuite(ConceptDescTest.class);
		suite.addTestSuite(ConceptTest.class);
//		suite.addTestSuite(IntervalDescTest.class); TODO
//		suite.addTestSuite(NumberDescTest.class); TODO
//		suite.addTestSuite(StringDescTest.class); TODO
//		suite.addTestSuite(SymbolDescTest.class); TODO 

		// similarity package
		suite.addTestSuite(AdvancedNumberTest.class);
		suite.addTestSuite(OrderedSymbolTest.class);
		suite.addTestSuite(StandardNumberTest.class);
		suite.addTestSuite(StringFunctionTest.class);
		suite.addTestSuite(SymbolFunctionTest.class);
		suite.addTestSuite(TaxonomyTest.class);
		
		//$JUnit-END$
		return suite;
	}

}
