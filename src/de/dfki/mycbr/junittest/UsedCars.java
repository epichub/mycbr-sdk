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

import java.util.Map;

import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Attribute;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import junit.framework.TestCase;

/**
 * @author myCBR Team
 * 
 */
public class UsedCars extends TestCase {

	public void test() {
		String projectsPath = "C:\\mycbr\\projects\\Web\\";
		String projectName = "used_cars_flat";
		String conceptName = "Car";

		Project project = null;
		Concept modelClass = null;

		try {
			// load project
			double start = System.currentTimeMillis();
			project = new Project(projectsPath + projectName + ".zip");
			while (project.isImporting()) {
				
			}
			System.out.println((System.currentTimeMillis()-start)/1000);
			modelClass = project.getConceptByID(conceptName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create case bases and assign the case bases that will be used for
		// submitting a query from a case.
		DefaultCaseBase cb = (DefaultCaseBase) project.getCaseBases().get(
				"CaseBase0");
		String caseID = "Car870";
		
		Retrieval r = new Retrieval(modelClass, cb);
		r.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
		Instance query = r.getQueryInstance();

		Instance caze = modelClass.getInstance(caseID);

		for (Map.Entry<AttributeDesc, Attribute> e : caze.getAttributes()
				.entrySet()) {
			query.addAttribute(e.getKey(), e.getValue());
		}
		r.start();
		while (!r.isFinished()) {

		}
		@SuppressWarnings("unchecked")
		Pair<Instance, Similarity>[] result = new Pair[r.size()];
    	int index = 0;
    	for(Pair<Instance,Similarity> e: r.getResult()) {
    		result[index++] = e; //new Pair<Instance, Similarity>(e.getKey(), e.getValue());
    	}
		for ( int i = 0; i < 957; i++ ) {
			System.out.println(result[i].getFirst().getName() + " " + result[i].getSecond().getRoundedValue());
			//assertTrue(result[i].getSecond().getValue()!=0.0);
		}
	}
}
