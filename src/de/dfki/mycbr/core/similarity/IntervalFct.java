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

import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Attribute;
import de.dfki.mycbr.core.casebase.IntervalAttribute;
import de.dfki.mycbr.core.casebase.MultipleAttribute;
import de.dfki.mycbr.core.casebase.SpecialAttribute;
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.IntervalDesc;
import de.dfki.mycbr.core.similarity.config.MultipleConfig;

/**
 * Not implemented yet.
 * 
 * @author myCBR Team
 *
 */
public class IntervalFct extends Observable implements ISimFct {

	private Project prj;
	private String name;
	private IntervalDesc desc;
	protected MultipleConfig mc = MultipleConfig.DEFAULT_CONFIG;
	
	public IntervalFct(Project prj,IntervalDesc desc2, String name) { 
		this.prj = prj;
		this.desc = desc2;
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#calculateSimilarity(de.dfki.mycbr.core.casebase.Attribute, de.dfki.mycbr.core.casebase.Attribute)
	 */
	@Override
	public Similarity calculateSimilarity(Attribute value1, Attribute value2)
			throws Exception {
		if (value1 instanceof SpecialAttribute || value2 instanceof SpecialAttribute) {
			return prj.calculateSpecialSimilarity(value1, value2);
		} else if (value1 instanceof MultipleAttribute<?> && value2 instanceof MultipleAttribute<?>) {
			return prj.calculateMultipleAttributeSimilarity(this, (MultipleAttribute<?>)value1, (MultipleAttribute<?>)value2);
		} else if (value1 instanceof IntervalAttribute && value2 instanceof IntervalAttribute) {
			IntervalAttribute v1 = (IntervalAttribute) value1;
			IntervalAttribute v2 = (IntervalAttribute) value2;
			if (v1.equals(v2)) {
				return Similarity.get(1.0);
			} 
			return Similarity.get(0.0);
		} else {
			return Similarity.INVALID_SIM;
		}
		
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getDesc()
	 */
	@Override
	public AttributeDesc getDesc() {
		return desc;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getMultipleConfig()
	 */
	@Override
	public MultipleConfig getMultipleConfig() {
		return mc;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getProject()
	 */
	@Override
	public Project getProject() {
		return prj;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#isSymmetric()
	 */
	@Override
	public boolean isSymmetric() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#setMultipleConfig(de.dfki.mycbr.core.similarity.config.MultipleConfig)
	 */
	@Override
	public void setMultipleConfig(MultipleConfig mc) {
		this.mc = mc;
		setChanged();
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#setName(java.lang.String)
	 */
	@Override
	/**
	 * Sets the name of this function to name.
	 * Does nothing if there is another function with this name.
	 * @param name the name of this function
	 */
	public void setName(String name) {
		if (desc.getFct(name) == null) {
			desc.renameFct(this.name, name);
			this.name = name;
			setChanged();
			notifyObservers();
		}
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#setSymmetric(boolean)
	 */
	@Override
	public void setSymmetric(boolean symmetric) {}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#clone(de.dfki.mycbr.core.model.AttributeDesc, boolean)
	 */
	@Override
	public void clone(AttributeDesc descNEW, boolean active) {
		if (descNEW instanceof IntervalDesc && !name.equals(Project.DEFAULT_FCT_NAME)) {
			IntervalFct f = ((IntervalDesc)descNEW).addIntervalFct(name, active);
			f.mc = this.mc;
		}
	}
}
