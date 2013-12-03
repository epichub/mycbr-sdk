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

package de.dfki.mycbr.junittest.load;

import java.io.File;
import java.util.LinkedList;

import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.io.DataBaseImporter;
/**
 * tests the import functionality from a PGSQL data base in myCBR v3.0
 * 
 * @author myCBR Team
 * 
 */
public class TestDataBase {
	
	private static DataBaseImporter dbimport;
	
	public static void main(String args[]) {
		try {
			// First lod the myCBR project
			Project project = new Project("C:\\Users\\kerstin\\Documents\\Development\\workspace\\myCBR2\\projects\\Test\\used_cars_flat.prj");
			while (project.isImporting()) {
				Thread.sleep(1000);
				System.out.print(".");
			}
		
			// Specify the concept the cases should be added to
			Concept modelClass = project.getConceptByID("Car");
			// specify the path to the database mapping
			String dbmapping = "C:\\Users\\kerstin\\Documents\\Development\\workspace\\myCBR2\\projects\\Test\\databasemapping.xml";

			// name the table that contains the cases
			String table = "cars";
			
			// just in case the table exists, it is deleted
			project.deleteCaseBase("CB_" + table);
			
			//Setting parameters for database connection and myCBR model
			dbimport = new DataBaseImporter(1, project, modelClass, dbmapping);
			
			System.out.println("DbImporter initialized");
			dbimport.setSeperatorMultiple(",");
			System.out.println("Reading");
			dbimport.readData(table, null, null);
			dbimport.checkData();
			System.out.println("Do the Import");
			dbimport.doImport();
			
			while (dbimport.isImporting()) {
				Thread.sleep(1000);
				System.out.println("..");
			}
			
			DefaultCaseBase cb = (DefaultCaseBase) project.getCB("CB_" + table);
			
			System.out.println("CaseBase " + cb.getName() + " imported");
			
			LinkedList<Instance> cases = (LinkedList<Instance>) cb.getCases();
			
			System.out.println("CaseBase " + cb.getName() +"contains " + cases.size() + " cases");
			
			project.save();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
			
			
		
	}
}
