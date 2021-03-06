/*	
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institute for Computer Graphics Research
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.middleware.owl.supply;

import org.universAAL.middleware.owl.ComparableIndividual;

/**
 * An enumeration originally designed for rating the perceived quality of a
 * service but kept at a more generally shared level, because it can be used not
 * only for rating services but generally as a rating system. It is based on the
 * German marks system for students' work, which has the following scale:
 * <ul>
 * <li>"very good" = {1, 1.3 | 1-}
 * <li>"good" = {1.7 | 2+, 2, 2.3 | 2-}
 * <li>"satisfying" = {2.7 | 3+, 3, 3.3 | 3-}
 * <li>"sufficient" = {3.7 | 4+, 4, 4.3 | 4-}
 * <li>"poor" = {4.7 | 5+, 5}
 * </ul>
 * 
 * @author mtazari - <a href="mailto:Saied.Tazari@igd.fraunhofer.de">Saied
 *         Tazari</a>
 * @author Carsten Stockloew
 */
public final class Rating extends ComparableIndividual {

    public static final String MY_URI = uAAL_VOCABULARY_NAMESPACE + "Rating";

    public static final int POOR = 0;
    public static final int ALMOST_POOR = 1;
    public static final int ALMOST_SUFFICIENT = 2;
    public static final int SUFFICIENT = 3;
    public static final int RICH_SUFFICIENT = 4;
    public static final int ALMOST_SATISFYING = 5;
    public static final int SATISFYING = 6;
    public static final int RICH_SATISFYING = 7;
    public static final int ALMOST_GOOD = 8;
    public static final int GOOD = 9;
    public static final int RICH_GOOD = 10;
    public static final int ALMOST_EXCELLENT = 11;
    public static final int EXCELLENT = 12;

    private static final String[] names = { "poor", "almost_poor",
	    "almost_sufficient", "sufficient", "rich_sufficient",
	    "almost_satisfying", "satisfying", "rich_satisfying",
	    "almost_good", "good", "rich_good", "almost_excellent", "excellent" };

    public static final Rating poor = new Rating(POOR);
    public static final Rating almostPoor = new Rating(ALMOST_POOR);
    public static final Rating almostSufficient = new Rating(ALMOST_SUFFICIENT);
    public static final Rating sufficient = new Rating(SUFFICIENT);
    public static final Rating richSufficient = new Rating(RICH_SUFFICIENT);
    public static final Rating almostSatisfying = new Rating(ALMOST_SATISFYING);
    public static final Rating satisfying = new Rating(SATISFYING);
    public static final Rating richSatisfying = new Rating(RICH_SATISFYING);
    public static final Rating almostGood = new Rating(ALMOST_GOOD);
    public static final Rating good = new Rating(GOOD);
    public static final Rating richGood = new Rating(RICH_GOOD);
    public static final Rating almostExcellent = new Rating(ALMOST_EXCELLENT);
    public static final Rating excellent = new Rating(EXCELLENT);

    /** The current value of this object. */
    private int order;

    // prevent the usage of the default constructor
    private Rating() {
    }

    private Rating(int order) {
	super(uAAL_VOCABULARY_NAMESPACE + names[order]);
	this.order = order;
    }

    /** @see org.universAAL.middleware.owl.ManagedIndividual#getClassURI() */
    public String getClassURI() {
	return MY_URI;
    }

    public static Rating getMaxValue() {
	return excellent;
    }

    public static Rating getMinValue() {
	return poor;
    }

    public static Rating getRatingByOrder(int order) {
	switch (order) {
	case POOR:
	    return poor;
	case ALMOST_POOR:
	    return almostPoor;
	case ALMOST_SUFFICIENT:
	    return almostSufficient;
	case SUFFICIENT:
	    return sufficient;
	case RICH_SUFFICIENT:
	    return richSufficient;
	case ALMOST_SATISFYING:
	    return almostSatisfying;
	case SATISFYING:
	    return satisfying;
	case RICH_SATISFYING:
	    return richSatisfying;
	case ALMOST_GOOD:
	    return almostGood;
	case GOOD:
	    return good;
	case RICH_GOOD:
	    return richGood;
	case ALMOST_EXCELLENT:
	    return almostExcellent;
	case EXCELLENT:
	    return excellent;
	default:
	    return null;
	}
    }

    public static Rating valueOf(String name) {
	for (int i = POOR; i <= EXCELLENT; i++)
	    if (names[i].equals(name))
		return getRatingByOrder(i);
	return null;
    }

    public int compareTo(Object other) {
	return (this == other) ? 0 : (order < ((Rating) other).order) ? -1 : 1;
    }

    public ComparableIndividual getNext() {
	return getRatingByOrder(order + 1);
    }

    public ComparableIndividual getPrevious() {
	return getRatingByOrder(order - 1);
    }

    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_OPTIONAL;
    }

    /** @see org.universAAL.middleware.rdf.Resource#isWellFormed() */
    public boolean isWellFormed() {
	return true;
    }

    /** Get a human-readable description for this Rating value. */
    public String name() {
	return names[order];
    }

    public int ord() {
	return order;
    }

    /**
     * Overrides the default method to prevent properties from being added.
     * 
     * @see org.universAAL.middleware.rdf.Resource#setProperty(String, Object)
     */
    public boolean setProperty(String propURI, Object o) {
	// do nothing
	return false;
    }
}
