/*
 * myCBR License 2.0
 *
 * Copyright (c) 2009
 * Thomas Roth-Berghofer, Armin Stahl & German Research Center for Artificial Intelligence DFKI GmbH
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

import java.util.HashMap;


/**
 * Represents similarity values. Similarity values should be within the range [0.00, 1.00]
 * and are rounded to 2 digits after the decimal point.
 * If the value is not within the given range, the similarity value is represented by the string NaN.
 * The double value of this similarity is usually set to -1.00 and referred to as an invalid similarity.
 * 
 * @author myCBR Team
 */
public class Similarity {

	private double value = -1.00;
	private double roundedValue = -1.00;
	
	private static HashMap<Double, Similarity> sims = new HashMap<Double, Similarity>();
	public static final Similarity INVALID_SIM = get(-1.00);
	
	/**
	 * Initializes this with d
	 * @param d value of this similarity
	 */
	public static Similarity get(Double d) {
		Similarity res = sims.get(d);
		if (res == null) {
			if (d >=0 && d <=1 || d == -1.00) {
				res = new Similarity(d);
				sims.put(d,res);
			} else {
				res = get(-1.00);
			}
		}
		return res;
	}

	private Similarity(double d) {
		this.value = d;
		updateRoundedValue();
	}
	
	/**
	 * 
	 */
	private void updateRoundedValue() {
		try {
			roundedValue = Math.round( value * 100. ) / 100.;
			if ((roundedValue > 1) || (roundedValue < 0 && roundedValue != -1)) {
				roundedValue = -1.0;
			}
		} catch (NumberFormatException e) {			
			System.err.println("Error. Could not round value:" + value);
		}
	}

	/**
	 * Rounds the value of this similarity to
	 * two decimal digits
	 */
	public double getRoundedValue() {
		return roundedValue;
	}

	/**
	 * Get the value of this similarity
	 * @return the value of this similarity
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Returns string representation of this similarity.
	 * If the given value lies within the range [0.00;1.00]
	 * returns {@link Double#toString()} else NaN.
	 * 
	 * @return String representing the value of this similarity
	 */
	public String toString() {
		if ( (value < 0.00) || (value> 1.00) ) {
			return "NaN";
		}
		return Double.toString(getRoundedValue());
	}
	
	/**
	 * Compares this to another similarity.
	 * @return true, if double value is equal, false otherwise
	 */
	public boolean equals(Similarity sim) {
		return (value == sim.getValue());
	}

}
