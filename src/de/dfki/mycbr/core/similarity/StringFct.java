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

import java.util.HashSet;
import java.util.Observable;

import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Attribute;
import de.dfki.mycbr.core.casebase.MultipleAttribute;
import de.dfki.mycbr.core.casebase.SpecialAttribute;
import de.dfki.mycbr.core.casebase.StringAttribute;
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.similarity.config.MultipleConfig;
import de.dfki.mycbr.core.similarity.config.StringConfig;

/**
 * Calculates the similarity of two strings 
 * for a given description. At the moment only syntactic equality 
 * is supported. 
 * 
 * @author myCBR Team
 */ 
public class StringFct extends Observable implements ISimFct {

	private StringDesc desc;
	private StringConfig config;
	
	// if config == NGRAM
	private int n = 3;
	
	private boolean caseSensitive = true;
	
	private String name;
	private Project prj;
	private MultipleConfig mc = MultipleConfig.DEFAULT_CONFIG;
	
	/**
	 * Initializes this with the given description.
	 * @param desc the description of the 
	 */
	public StringFct (Project prj, StringConfig config, StringDesc desc, String name) { 
		this.prj = prj;
		this.desc = desc;
		this.config = config;
		this.name = name;
	}

	/**
	 * Calculates the similarity of the given attributes.
	 * Returns null if an error occurs.
	 * @return 1.00 if the given attributes are equal, invalid similarity if
	 * an error occurs, else 0.00
	 * @throws Exception 
	 */
	@Override
	public Similarity calculateSimilarity(Attribute value1, Attribute value2) throws Exception {
		
		if (value1 instanceof MultipleAttribute<?> && value2 instanceof MultipleAttribute<?>) {
			return prj
			.calculateMultipleAttributeSimilarity(this,((MultipleAttribute<?>)value1), (MultipleAttribute<?>)value2);
		} else if (!( value1 instanceof SpecialAttribute || value2 instanceof SpecialAttribute )) {
			String v1 = ((StringAttribute)value1).toString();
			String v2 = ((StringAttribute)value2).toString();
			
			if (!caseSensitive) {
				v1 = v1.toLowerCase();
				v2 = v2.toLowerCase();
			}
			
			switch(config) {
				case EQUALITY: return v1.equals(v2) ? Similarity.get(1.00) : Similarity.get(0.00);
				case NGRAM: return nGramSimilarity(v1,v2);
				default: return Similarity.INVALID_SIM;
			}
			
		} else {
			return prj.calculateSpecialSimilarity(value1, value2);
		}
	}

	/**
	 * Computes the NGRAM similarity of the given strings.
	 * The similarity results in the ration of common NGRAMs divided 
	 * by all occurring NGRAMs. An NGRAM is a substring of length n
	 * of the given string.
	 * @param v1 the first string
	 * @param v2 the second string
	 * @return the NGRAM similarity of the given strings
	 */
	@SuppressWarnings("unchecked")
	private Similarity nGramSimilarity(String v1, String v2) {
		
		HashSet<String> ngramsV1 = new HashSet<String>();
		HashSet<String> ngramsV2 = new HashSet<String>();
		
		// get all n grams of first string
		for (int i = 0; i<v1.length()-n+1; i++) {
			ngramsV1.add(v1.substring(i, i+n));
		}
		
		// get all n grams of second string
		for (int i = 0; i<v2.length()-n+1; i++) {
			ngramsV2.add(v2.substring(i, i+n));
		}
		
		// get # all occurring NGRAMS (without repetition)
		HashSet<String> union = (HashSet<String>)ngramsV1.clone();
		union.addAll(ngramsV2);
		float f2 = union.size();
		
		// get # common NGRAMS (without repetition)
		ngramsV2.retainAll(ngramsV1);
		float f1 = ngramsV2.size();
		
		// compute ratio
		Similarity res = Similarity.get(new Double(f1/f2));
		
		return res;
	}

	/**
	 * Returns whether this function is symmetric.
	 * Equality and NGRAM similarity is always symmetric.
	 * @return true
	 */
	public boolean isSymmetric() {
		return true;
	}
 
	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#setSymmetric(boolean)
	 */
	@Override
	/**
	 * Does nothing.
	 * Not implemented because at the moment only symmetric string
	 * functions are supported.
	 */
	public void setSymmetric(boolean symmetric) {}
	
	/**
	 * Returns the configuration of this function.
	 * @return the configuration describing how to compute similarity
	 */
	public StringConfig getConfig() {
		return config;
	}

	/**
	 * Returns the parameter n for computing NGRAM similarity.
	 * N specifies the length of the substrings to be compared.
	 * @return the parameter n
	 */
	public int getN() {
		return n;
	}
	
	/**
	 * Sets the parameter n for computing NGRAM similarity.
	 * N specifies the length of the substrings to be compared.
	 * @param n the new parameter n
	 */
	public void setN(int n) {
		this.n = n;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Returns whether this function is case sensitive.
	 * @return true, if function is case sensitive, false otherwise
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	
	/**
	 * Specifies whether this function is case sensitive.
	 * @param caseSensitive true, if function is case sensitive, false otherwise
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		if (caseSensitive != this.caseSensitive) {
			setChanged();
		}
		this.caseSensitive = caseSensitive;
		notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getName()
	 */
	@Override
	/**
	 * Returns the name of this function
	 * @return the name of this function
	 */
	public String getName() {
		return name;
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
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getDescription()
	 */
	@Override
	/**
	 * Returns the description of the attributes which can be compared using this function.
	 * 
	 * @return the description this function belongs to 
	 */
	public AttributeDesc getDesc() {
		return desc;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getProject()
	 */
	@Override
	public Project getProject() {
		return prj;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#getMultipleConfig()
	 */
	@Override
	public MultipleConfig getMultipleConfig() {
		return mc;
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#setMultipleConfig()
	 */
	@Override
	public void setMultipleConfig(MultipleConfig mc) {
		this.mc = mc;
		setChanged();
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#clone(de.dfki.mycbr.core.model.AttributeDesc, boolean)
	 */
	@Override
	public void clone(AttributeDesc descNEW, boolean active) {
		if (descNEW instanceof StringDesc && !name.equals(Project.DEFAULT_FCT_NAME)) {
			StringFct f = desc.addStringFct(config, name, active);
			f.caseSensitive = this.caseSensitive;
			f.mc = this.mc;
			f.n = this.n;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
}
