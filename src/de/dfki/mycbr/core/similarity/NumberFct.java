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
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.SimpleAttDesc;
import de.dfki.mycbr.core.similarity.config.DistanceConfig;
import de.dfki.mycbr.core.similarity.config.MultipleConfig;

/**
 * Defines how to compute the similarity of two integers, floats or doubles.
 * The similarity of two numbers is influenced by the range of possible
 * values and their distance.
 * 
 * @author myCBR Team
 *
 */
public abstract class NumberFct extends Observable implements ISimFct {
	
	protected MultipleConfig mc = MultipleConfig.DEFAULT_CONFIG;

	protected double max;
	protected double min;
	protected double diff;
	
	protected Boolean isSymmetric = true;
	
	protected DistanceConfig distanceFunction = DistanceConfig.DIFFERENCE;
	
	protected Project prj;
	protected String name;
	
	protected SimpleAttDesc desc;
	
	public NumberFct(Project p, SimpleAttDesc d, String n) {
		this.prj = p;
		this.desc = d;
		this.name = n;
		d.addObserver(this);
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
		return isSymmetric;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#setMultipleConfig(de.dfki.mycbr.core.similarity.config.MultipleConfig)
	 */
	@Override
	public void setMultipleConfig(MultipleConfig mc) {
		this.mc = mc;
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
	public void setSymmetric(boolean symmetric) {
		this.isSymmetric = symmetric; // TODO update
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Sets the mode of this function to mode
	 * @param df the new mode of this function
	 */
	public void setDistanceFct(DistanceConfig df) {
		if (df != distanceFunction) {
			if (df.equals(DistanceConfig.QUOTIENT)) {
				if (min <= 0 && max >= 0) {
					return; // cannot use quotient, when 0 is included in range
				}
			}
			this.distanceFunction = df;
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Returns the mode of this function
	 * @return the mode of this function
	 */
	public DistanceConfig getDistanceFct() {
		return distanceFunction;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getDescription()
	 */
	@Override
	/**
	 * Returns the description of the attributes which can 
	 * be compared using this function.
	 * @return description this function belongs to
	 */
	public AttributeDesc getDesc() {
		return desc;
	}

}
