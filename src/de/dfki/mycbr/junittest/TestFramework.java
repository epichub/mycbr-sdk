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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.model.BooleanDesc;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.DateDesc;
import de.dfki.mycbr.core.model.DoubleDesc;
import de.dfki.mycbr.core.model.FloatDesc;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.IntervalDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.similarity.AmalgamationFct;
import de.dfki.mycbr.core.similarity.config.AmalgamationConfig;

/**
 * @author myCBR Team
 * 
 */
public class TestFramework {

	public Project prj;
	public Concept carDesc;
	public IntegerDesc doorDesc;
	public SymbolDesc colorDesc;
	public IntegerDesc ccmDesc;
	public StringDesc commentDesc;
	public DateDesc regDesc;
	public FloatDesc weightDesc;
	public BooleanDesc newDesc;
	public IntervalDesc tempDesc;
	public StringDesc dealerDesc;
	public DoubleDesc price;

	public SymbolDesc equipDesc;
	public SimpleDateFormat df;
	public List<Object> equipList;
	public AmalgamationFct amalgam;
	
	public TestFramework() throws Exception {
		prj = new Project();
		carDesc = prj.createTopConcept("Car");

		doorDesc = new IntegerDesc(carDesc, "doors", 0, 10);

		price = new DoubleDesc(carDesc, "price", 0, 100000.00);

		HashSet<String> colors = new HashSet<String>();
		String[] colorsArray = { "green", "black", "blue", "white", "yellow",
				"red", "pink", "brown", "darkgreen", "darkblue" };
		colors.addAll(Arrays.asList(colorsArray));
		colorDesc = new SymbolDesc(carDesc,"color", colors);

		ccmDesc = new IntegerDesc(carDesc,"ccm", 500, 1000);

		commentDesc = new StringDesc(carDesc,"comment");

		df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			regDesc = new DateDesc(carDesc,"date", df.parse("1990-01-01"), df
					.parse("2011-01-01"), df);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		weightDesc = new FloatDesc(carDesc,"weight", 0.5f, 5.f);

		newDesc = new BooleanDesc(carDesc,"new");

		tempDesc = new IntervalDesc(carDesc,"temp", -100, 100);

		HashSet<String> equips = new HashSet<String>();
		String[] equipsArray = { "radio", "sunroof", "air_conditioning",
				"electric_window_lift" };
		equips.addAll(Arrays.asList(equipsArray));

		equipList = new LinkedList<Object>();
		equipList.add("radio");
		equipList.add("sunroof");
		equipList.add("air conditioning");

		equipDesc = new SymbolDesc(carDesc,"equip", equips);
		equipDesc.setMultiple(true);

		dealerDesc = new StringDesc(carDesc,"dealer");

		amalgam = carDesc.addAmalgamationFct(
				AmalgamationConfig.EUCLIDEAN, "weightedSum", true);
		
		prj.save();

	}
}
