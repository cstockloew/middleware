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

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;

/**
 * A {@link TypeExpression} ({@link LengthRestriction}) that contains all URIs
 * with a given minimum and/or maximum length.
 * 
 * @author Carsten Stockloew
 */
public class URIRestriction extends LengthRestriction {

    /** URI of the data type. */
    public static final String DATATYPE_URI = TypeMapper
	    .getDatatypeURI(Resource.class);

    /** Standard constructor. */
    public URIRestriction() {
	super(DATATYPE_URI);
    }

    /** @see org.universAAL.middleware.owl.TypeExpression#copy() */
    @Override
    public TypeExpression copy() {
	return copyTo(new URIRestriction());
    }
}
