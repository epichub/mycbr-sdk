package de.dfki.mycbr.core.explanation;

import java.util.HashSet;
import java.util.Observable;

/**
 * Concept explanations further explain any kind of concept using
 * a textual description and links related to that concept. 
 */
public class ConceptExplanation extends Observable {

    /**
     *
     */
    private String description = "";

    /**
     *
     */
    private HashSet<String> links = new HashSet<String>();

    /**
     *
     */
    private IExplainable explainable;

    /**
     *
     * @param exp the object this explanation belongs to
     */
    ConceptExplanation(final IExplainable exp, ExplanationManager em) {
        this.explainable = exp;
        addObserver(em);
    }

    /**
     *
     * @param exp the object to be explained
     * @param desc the description for this
     */
    ConceptExplanation(final IExplainable exp, final String desc) {
        this.explainable = exp;
        this.description = desc;
    }

    /**
     *
     * @return the object which is explained by this
     */
    public final IExplainable getExplainable() {
        return explainable;
    }

    /**
     * Set the value of description.
     *
     * @param newVar
     *            the new value of description
     */
    public final void setDescription(final String newVar) {
        description = newVar;
        setChanged();
        notifyObservers();
    }

    /**
     * Get the value of description.
     *
     * @return the value of description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Add a new link to the links for this explanation.
     * @param link
     *            the new link to be added
     */
    public final void addLink(final String link) {
        links.add(link);
        setChanged();
        notifyObservers();
    }

    /**
     * Removes the given link from the list of links for this explanation.
     * @param link the link to be removed from the list of known links
     */
    public final void removeLink(final String link) {
        links.remove(link);
        setChanged();
        notifyObservers();
    }

    /**
     * Get the value of links.
     *
     * @return the value of links
     */
    public final HashSet<String> getLinks() {
        return links;
    }

}
