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

package de.dfki.mycbr.core.similarity;

import java.util.Observable;

import test.junittest.model.NumberDescTest;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Attribute;
import de.dfki.mycbr.core.casebase.DoubleAttribute;
import de.dfki.mycbr.core.casebase.IntegerAttribute;
import de.dfki.mycbr.core.casebase.SimpleAttribute;
import de.dfki.mycbr.core.casebase.SymbolAttribute;
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.DoubleDesc;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.SimpleAttDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.similarity.config.MultipleConfig;

/**
 * @author myCBR Team
 *
 */
public class SymbolNumericCombinedFct extends Observable implements ISimFct {

	private Project prj;
	private NumberFct numberFct;
	private SymbolFct symbolFct;
	private DoubleAttribute doubleAttr1;
	private DoubleAttribute doubleAttr2;

	public SymbolNumericCombinedFct(Project p, SymbolDesc symbolDesc, DoubleDesc doubleDesc) {
		this.prj = p;
		AmalgamationFct amalgamFct = doubleDesc.getOwner().getActiveAmalgamFct();
		this.numberFct = (NumberFct)amalgamFct.getActiveFct(doubleDesc);
		this.symbolFct = (SymbolFct)amalgamFct.getActiveFct(symbolDesc);
		this.symbolFct.setSymmetric(false);
	}

	public void setSimilarity(String symbol1, String symbol2, double sim) {
		symbolFct.setSimilarity(symbol1, symbol2, sim);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.dfki.mycbr.core.similarity.ISimFct#calculateSimilarity(de.dfki.mycbr
	 * .core.casebase.Attribute, de.dfki.mycbr.core.casebase.Attribute)
	 */
	@Override
	public Similarity calculateSimilarity(Attribute value1, Attribute value2)
			throws Exception {
		
		Similarity result = Similarity.INVALID_SIM;
		
		if ((value1 instanceof SymbolAttribute) && (value2 instanceof SymbolAttribute)) {

			// Use SymbolFct to get ratio
			Similarity symbolRatio = symbolFct.calculateSimilarity(value1, value2);
			double integerRatio = (doubleAttr1.getValue() / doubleAttr2.getValue());
			
			// Get Attributes for numberFct
			Attribute attr1 = null;
			Attribute attr2 = null;
			if (numberFct.getDesc() instanceof IntegerDesc) {
				IntegerDesc iDesc = (IntegerDesc) numberFct.getDesc();
				attr1 = iDesc.getAttribute(symbolRatio.getValue());
				attr2 = iDesc.getAttribute(integerRatio);
			} else if (numberFct.getDesc() instanceof DoubleDesc) {
				DoubleDesc dDesc = (DoubleDesc) numberFct.getDesc();
				attr1 = dDesc.getAttribute(symbolRatio.getValue());
				attr2 = dDesc.getAttribute(integerRatio);
			}
			
			result = numberFct.calculateSimilarity(attr1, attr2);
			String log = String.format("SymbolNumericCombined(%s,%s): symbolRatio=%s integerRatio=%s result=%s", value1.getValueAsString(), value2.getValueAsString(),
					symbolRatio.getValue(), integerRatio, result.getValue());
			System.out.println(log);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.dfki.mycbr.core.similarity.ISimFct#isSymmetric()
	 */
	@Override
	public boolean isSymmetric() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.dfki.mycbr.core.similarity.ISimFct#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getDesc()
	 */
	@Override
	public AttributeDesc getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.dfki.mycbr.core.similarity.ISimFct#setSymmetric(boolean)
	 */
	@Override
	public void setSymmetric(boolean symmetric) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getMultipleConfig()
	 */
	@Override
	public MultipleConfig getMultipleConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.dfki.mycbr.core.similarity.ISimFct#setMultipleConfig(de.dfki.mycbr
	 * .core.similarity.config.MultipleConfig)
	 */
	@Override
	public void setMultipleConfig(MultipleConfig mc) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getProject()
	 */
	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.dfki.mycbr.core.similarity.ISimFct#clone(de.dfki.mycbr.core.model.
	 * AttributeDesc, boolean)
	 */
	@Override
	public void clone(AttributeDesc descNEW, boolean active) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the intDesc
	 */
	public DoubleAttribute getDoubleAttr2() {
		return doubleAttr2;
	}

	/**
	 * @param intDesc the intDesc to set
	 */
	public void setDoubleAttr2(DoubleAttribute doubleAttr) {
		this.doubleAttr2 = doubleAttr;
	}

	/**
	 * @return the intDesc
	 */
	public DoubleAttribute getDoubleAttr1() {
		return doubleAttr1;
	}

	/**
	 * @param intDesc the intDesc to set
	 */
	public void setDoubleAttr1(DoubleAttribute doubleAttr) {
		this.doubleAttr1 = doubleAttr;
	}
	
	
}
