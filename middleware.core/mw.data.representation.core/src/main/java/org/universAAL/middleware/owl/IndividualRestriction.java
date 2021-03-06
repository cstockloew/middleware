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
package org.universAAL.middleware.owl;

import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.Variable;

/**
 * A {@link TypeExpression} ({@link BoundedValueRestriction}) that contains all
 * individuals with a given lower bound and/or upper bound. The individuals need
 * to be comparable, i.e. they need to be sub classes of
 * {@link ComparableIndividual}.
 * <p>
 * This class is not defined for literals, but individuals.
 * 
 * @author Carsten Stockloew
 */
public final class IndividualRestriction extends BoundedValueRestriction {

    /** URI of the data type. */
    public static final String DATATYPE_URI = ComparableIndividual.MY_URI;

    /** Standard constructor for exclusive use by serializers. */
    public IndividualRestriction() {
	super(DATATYPE_URI);
    }

    /**
     * Creates a new restriction.
     * 
     * @param min
     *            The minimum value, or null if no minimum is defined.
     * @param minInclusive
     *            True, if the minimum value is included. Ignored, if min is
     *            null.
     * @param max
     *            The maximum value, or null if no maximum is defined.
     * @param maxInclusive
     *            True, if the maximum value is included. Ignored, if max is
     *            null.
     */
    public IndividualRestriction(ComparableIndividual min,
	    boolean minInclusive, ComparableIndividual max, boolean maxInclusive) {
	super(ComparableIndividual.MY_URI, min, minInclusive, max, maxInclusive);
    }

    /**
     * Creates a new restriction.
     * 
     * @param min
     *            The minimum value, or a {@link Variable} reference, or null if
     *            no minimum is defined.
     * @param minInclusive
     *            True, if the minimum value is included. Ignored, if min is
     *            null.
     * @param max
     *            The maximum value, or a {@link Variable} reference, or null if
     *            no maximum is defined.
     * @param maxInclusive
     *            True, if the maximum value is included. Ignored, if max is
     *            null.
     */
    public IndividualRestriction(Object min, boolean minInclusive, Object max,
	    boolean maxInclusive) {
	super(ComparableIndividual.MY_URI, min, minInclusive, max, maxInclusive);
    }

    /** @see BoundedValueRestriction#checkType(Object) */
    protected boolean checkType(Object o) {
	if (o instanceof ComparableIndividual)
	    return true;
	return super.checkType(o);
    }

    /** @see BoundedValueRestriction#getNext(Comparable) */
    protected Comparable getNext(Comparable c) {
	return ((ComparableIndividual) c).getNext();
    }

    /** @see BoundedValueRestriction#getPrevious(Comparable) */
    protected Comparable getPrevious(Comparable c) {
	return ((ComparableIndividual) c).getPrevious();
    }

    /** @see org.universAAL.middleware.owl.TypeExpression#copy() */
    public TypeExpression copy() {
	return copyTo(new IndividualRestriction());
    }
}
