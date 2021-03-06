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
package org.universAAL.middleware.rdf;

/**
 * Class for handling multiple values of a property in RDF. As compared to
 * {@link ClosedCollection}, the OpenCollection defines that the collection of
 * objects is open, i.e. there can be more objects.
 * 
 * Example: If you have some sensors in a room that can determine that a person
 * is in a room but you do not know if there are more persons in that room then
 * you can express this with an OpenCollection, e.g. the LivingRoom contains at
 * least the people {Joe, Juan} (but maybe also Carlos or Pablo).
 * 
 * @author Carsten Stockloew
 * @see ClosedCollection
 */
public final class OpenCollection extends ObjectCollection {
    private static final long serialVersionUID = 1L;
}
