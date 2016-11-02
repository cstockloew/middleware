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

/**
 * A {@link TypeExpression} ({@link BoundedValueRestriction}) that contains all
 * literals of type long with a given lower bound and/or upper bound.
 * 
 * @author Carsten Stockloew
 */
public final class LongRestriction extends BoundedValueRestriction {

    /** URI of the data type <i>Long</i>. */
    public static final String DATATYPE_URI = TypeMapper
	    .getDatatypeURI(Long.class);

    /** Standard constructor for exclusive use by serializers. */
    public LongRestriction() {
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
    public LongRestriction(long min, boolean minInclusive, long max,
	    boolean maxInclusive) {
	this(Long.valueOf(min), minInclusive, Long.valueOf(max), maxInclusive);
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
    public LongRestriction(Long min, boolean minInclusive, Long max,
	    boolean maxInclusive) {
	super(TypeMapper.getDatatypeURI(Long.class), min, minInclusive, max,
		maxInclusive);
    }

    /** @see BoundedValueRestriction#getNext(Comparable) */
    protected Comparable getNext(Comparable c) {
	return Long.valueOf(((Long) c).longValue() + 1);
    }

    /** @see BoundedValueRestriction#getPrevious(Comparable) */
    protected Comparable getPrevious(Comparable c) {
	return Long.valueOf(((Long) c).longValue() - 1);
    }

    /** @see org.universAAL.middleware.owl.TypeExpression#copy() */
    public TypeExpression copy() {
	return copyTo(new LongRestriction());
    }
}
