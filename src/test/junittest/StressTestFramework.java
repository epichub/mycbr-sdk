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

package test.junittest;

import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.*;
import de.dfki.mycbr.core.similarity.AmalgamationFct;
import de.dfki.mycbr.core.similarity.IntegerFct;
import de.dfki.mycbr.core.similarity.config.AmalgamationConfig;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author myCBR Team
 * 
 */
public class StressTestFramework {

	public Project prj;

	public AmalgamationFct amalgam;

	public String initStressTestFramework(int attributes, int cases) throws Exception {
		prj = new Project();
        String projectName = "Test" + String.valueOf(attributes*cases);
        prj.setName(projectName);
        Concept mainDesc = prj.createTopConcept("main");
        DefaultCaseBase cb = prj.createDefaultCB("casebase");


        // creating the specified number of int attributes
        for (int attr = 0; attr <= attributes; attr++){
            IntegerDesc attDesc = new IntegerDesc(mainDesc, "attr"+String.valueOf(attr), 0, 100);
            IntegerFct f = attDesc.addIntegerFct("f" + String.valueOf(attr), true);
            f.setSymmetric(true);
            f.setFunctionParameterR(0.60);
        }

        for (int caze = 0; caze <= cases; caze++){
            String caseName = "case" + String.valueOf(caze);
            // add Case
            Instance i = mainDesc.addInstance(caseName);
            for (String attName : mainDesc.getAttributeDescs().keySet()){
                 i.addAttribute(mainDesc.getAttributeDesc(attName), (int) (Math.random()*100));
            }
            cb.addCase(i);
        }

		amalgam = mainDesc.addAmalgamationFct(
				AmalgamationConfig.EUCLIDEAN, "weightedSum", true);

        prj.setPath((System.getProperty("user.dir") + "/src/test/projects/StressTest/tmp/"));
		System.out.println("Path: " + prj.getPath());
        prj.save();

        // project path
        return prj.getPath()+projectName+".prj";
	}
}
