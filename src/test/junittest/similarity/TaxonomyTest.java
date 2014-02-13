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

import junit.framework.TestCase;

import org.junit.Test;

import de.dfki.mycbr.core.casebase.SymbolAttribute;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.core.similarity.TaxonomyFct;
import de.dfki.mycbr.core.similarity.config.TaxonomyConfig;
import test.junittest.TestFramework;

/**
 * @author myCBR Team
 * 
 */
public class TaxonomyTest extends TestCase {

	private TestFramework frame;
	private TaxonomyFct tf;

	@Test
	public void testTaxonomy() {
		try {
			frame = new TestFramework();
		
			frame.colorDesc.removeSymbol("darkgreen");
			frame.colorDesc.removeSymbol("darkblue");
	
			tf = frame.colorDesc.addTaxonomyFct(
					"f1", true);
			
			SymbolAttribute red = (SymbolAttribute) frame.colorDesc
					.getAttribute("red");
			SymbolAttribute blue = (SymbolAttribute) frame.colorDesc
					.getAttribute("blue");
			SymbolAttribute green = (SymbolAttribute) frame.colorDesc
					.getAttribute("green");
			SymbolAttribute white = (SymbolAttribute) frame.colorDesc
					.getAttribute("white");
			SymbolAttribute pink = (SymbolAttribute) frame.colorDesc
					.getAttribute("pink");
			SymbolAttribute brown = (SymbolAttribute) frame.colorDesc
					.getAttribute("brown");
			SymbolAttribute yellow = (SymbolAttribute) frame.colorDesc
					.getAttribute("yellow");
			SymbolAttribute black = (SymbolAttribute) frame.colorDesc
					.getAttribute("black");

			tf.setParent(blue, red);
			tf.setParent(green, blue);
			tf.setParent(pink, white);
			tf.setParent(brown, pink);
			tf.setParent(yellow, brown);
			tf.setParent(red, pink);
			
			tf.setNodeSimilarity(frame.colorDesc, Similarity.get(0.5));
			tf.setNodeSimilarity(white, Similarity.get(0.75));
			tf.setNodeSimilarity(pink, Similarity.get(0.88));
			tf.setNodeSimilarity(brown, Similarity.get(0.9));
			tf.setNodeSimilarity(red, Similarity.get(0.9));
			tf.setNodeSimilarity(blue, Similarity.get(0.95));
			
			tf.setSymmetric(true);
			
			tf.updateTable();
			Similarity s = tf.calculateSimilarity(yellow, black);
			assertTrue("sim(\"" + yellow + "\",\"" + black
					+ "\") should be 0.5 but is: " + s, s
					.equals(Similarity.get(0.5)));
			
			s = tf.calculateSimilarity(yellow, yellow);
			assertTrue("sim(\"" + yellow + "\",\"" + yellow
					+ "\") should be 1.0 but is: " + s, s
					.equals(Similarity.get(1.00)));

		
			s = tf.calculateSimilarity(pink, yellow);
			assertTrue("sim(\"" + pink + "\",\"" + yellow
					+ "\") should be 1.0 but is: " + s, s
					.equals(Similarity.get(1.00)));
						
			s = tf.calculateSimilarity(pink, black);
			assertTrue("sim(\"" + pink + "\",\"" + black
					+ "\") should be 0.5 but is: " + s, s
					.equals(Similarity.get(0.5)));
			
			tf.setQueryConfig(TaxonomyConfig.INNER_NODES_OPTIMISTIC);

			s = tf.calculateSimilarity(pink, yellow);
			assertTrue("sim(\"" + pink + "\",\"" + yellow
					+ "\") should be 1.0 but is: " + s, s
					.equals(Similarity.get(1.00)));
			s = tf.calculateSimilarity(pink, black);
			assertTrue("sim(\"" + pink + "\",\"" + black
					+ "\") should be 0.5 but is: " + s, s
					.equals(Similarity.get(0.5)));

			tf.setQueryConfig(TaxonomyConfig.INNER_NODES_PESSIMISTIC);

			s = tf.calculateSimilarity(pink, yellow);
			assertTrue("sim(\"" + pink + "\",\"" + yellow
					+ "\") should be 0.88 but is: " + s, s
					.equals(Similarity.get(0.88)));

			s = tf.calculateSimilarity(pink, black);
			assertTrue("sim(\"" + pink + "\",\"" + black
					+ "\") should be 0.5 but is: " + s, s
					.equals(Similarity.get(0.5)));

			tf.setQueryConfig(TaxonomyConfig.INNER_NODES_AVERAGE);

			s = tf.calculateSimilarity(pink, yellow);
			assertTrue("sim(\"" + pink + "\",\"" + yellow
					+ "\") should be 0.94 but is: " + s, s
					.equals(Similarity.get(0.94)));

			s = tf.calculateSimilarity(pink, black);
			assertTrue("sim(\"" + pink + "\",\"" + black
					+ "\") should be 0.5 but is: " + s, s
					.equals(Similarity.get(0.5)));

			tf.setCaseConfig(TaxonomyConfig.INNER_NODES_ANY);
			
			s = tf.calculateSimilarity(yellow, pink);
			assertTrue("sim(\"" + yellow + "\",\"" + pink
					+ "\") should be 1.0 but is: " + s, s
					.equals(Similarity.get(1.00)));

			s = tf.calculateSimilarity(black, pink);
			assertTrue("sim(\"" + black + "\",\"" + pink
					+ "\") should be 0.5 but is: " + s, s
					.equals(Similarity.get(0.5)));

			tf.setCaseConfig(TaxonomyConfig.INNER_NODES_OPTIMISTIC);

			s = tf.calculateSimilarity(yellow, pink);
			assertTrue("sim(\"" + yellow + "\",\"" + pink
					+ "\") should be 1.00 but is: " + s, s
					.equals(Similarity.get(1.00)));

			s = tf.calculateSimilarity(black, pink);
			assertTrue("sim(\"" + black + "\",\"" + pink
					+ "\") should be 0.5 but is: " + s, s
					.equals(Similarity.get(0.5)));

			tf.setCaseConfig(TaxonomyConfig.INNER_NODES_PESSIMISTIC);
			s = tf.calculateSimilarity(yellow, pink);
			assertTrue("sim(\"" + yellow + "\",\"" + pink
					+ "\") should be 0.88 but is: " + s, s
					.equals(Similarity.get(0.88)));

			s = tf.calculateSimilarity(black, pink);
			assertTrue("sim(\"" + black + "\",\"" + pink
					+ "\") should be 0.5 but is: " + s, s
					.equals(Similarity.get(0.5)));

			tf.setCaseConfig(TaxonomyConfig.INNER_NODES_AVERAGE);

			s = tf.calculateSimilarity(yellow, pink);
			assertTrue("sim(\"" + yellow + "\",\"" + pink
					+ "\") should be 0.94 but is: " + s, s
					.equals(Similarity.get(0.94)));

			s = tf.calculateSimilarity(black, pink);
			assertTrue("sim(\"" + black + "\",\"" + pink
					+ "\") should be 0.5 but is: " + s, s
					.equals(Similarity.get(0.5)));

			tf.setQueryConfig(TaxonomyConfig.INNER_NODES_ANY);

			s = tf.calculateSimilarity(brown, blue);
			assertTrue("sim(\"" + brown + "\",\"" + blue
					+ "\") should be 0.88 but is: " + s, s
					.equals(Similarity.get(0.88)));

			s = tf.calculateSimilarity(pink, blue);
			assertTrue("sim(\"" + pink + "\",\"" + blue
					+ "\") should be 1.0 but is: " + s, s
					.equals(Similarity.get(1.00)));
			tf.setQueryConfig(TaxonomyConfig.INNER_NODES_OPTIMISTIC);

			s = tf.calculateSimilarity(brown, blue);
			assertTrue("sim(\"" + brown + "\",\"" + blue
					+ "\") should be 0.88 but is: " + s, s
					.equals(Similarity.get(0.88)));
			s = tf.calculateSimilarity(pink, blue);
			assertTrue("sim(\"" + pink + "\",\"" + blue
					+ "\") should be 1.0 but is: " + s, s
					.equals(Similarity.get(1.00)));

			tf.setQueryConfig(TaxonomyConfig.INNER_NODES_PESSIMISTIC);

			s = tf.calculateSimilarity(brown, blue);
			assertTrue("sim(\"" + brown + "\",\"" + blue
					+ "\") should be 0.88 but is: " + s, s
					.equals(Similarity.get(0.88)));

			s = tf.calculateSimilarity(pink, blue);
			assertTrue("sim(\"" + pink + "\",\"" + blue
					+ "\") should be 0.88 but is: " + s, s
					.equals(Similarity.get(0.88)));

			tf.setQueryConfig(TaxonomyConfig.INNER_NODES_AVERAGE);

			s = tf.calculateSimilarity(brown, blue);
			assertTrue("sim(\"" + brown + "\",\"" + blue
					+ "\") should be 0.88 but is: " + s, s
					.equals(Similarity.get(0.88)));
			s = tf.calculateSimilarity(brown, brown);
			assertTrue("sim(\"" + brown + "\",\"" + brown
					+ "\") should be 1.0 but is: " + s, s
					.equals(Similarity.get(1.00)));

			frame.colorDesc.removeSymbol("brown");
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Excpetion in TaxonomyTest: testTaxonomy",false);
		}
	}

}
