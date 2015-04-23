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

package junittest.model;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.StringDesc;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;


/**
 * @author myCBR Team
 *
 */
public class MoveAttDescTest {

	@Test
	public void testMoveAttDesc() {
		try {
			Project p = new Project();
			Concept A = p.createTopConcept("top1");
			Concept B = p.createTopConcept("top2");
			StringDesc a = new StringDesc(A, "a");
			new StringDesc(B, "a");
			StringDesc c = new StringDesc(B, "c");
			Exception exp = null;
			try {
				a.setOwner(B);
			} catch (Exception e) {
				exp = e;
				// tried to move attdesc but owner has attdesc with that name
			}
			assertTrue("there should have been an exception!", exp != null);
			
			exp = null;
			try {
				c = new StringDesc(B, "c");
			} catch (Exception e) {
				exp = e;
				// tried to add attdesc but owner has attdesc with that name
			}
			assertTrue("there should have been an exception!", exp != null);
			
			c.setOwner(A);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
