/*******************************************************************************
 * Copyright 2014 Universidad Politécnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.middleware.managers.configuration.core.owl;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.rdf.LangString;

/**
 * Ontological representation of Entity in the aalconfigurationontologu
 * ontology. Methods included in this class are the mandatory ones for
 * representing an ontological concept in Java classes for the universAAL
 * platform. In addition getters and setters for properties are included.
 * 
 * @author
 * @author Generated by the OntologyUML2Java transformation of AAL Studio
 */
public class Entity extends ManagedIndividual {
    public static final String MY_URI = AALConfigurationOntology.NAMESPACE
	    + "Entity";
    public static final String PROP_VERSION = AALConfigurationOntology.NAMESPACE
	    + "version";

    private static final long INITAL_VERSION = Long.MIN_VALUE;

    public Entity() {
	super();
	setVersion(INITAL_VERSION);
    }

    public Entity(String uri) {
	super(uri);
    }

    public String getClassURI() {
	return MY_URI;
    }

    public int getPropSerializationType(String propURI) {
	if (PROP_VERSION.equals(propURI))
	    return PROP_SERIALIZATION_FULL;
	if (PROP_RDFS_COMMENT.equals(propURI))
	    return PROP_SERIALIZATION_FULL;
	return PROP_SERIALIZATION_FULL;
    }

    public boolean isWellFormed() {
	return super.isWellFormed() && hasProperty(PROP_VERSION);
    }

    public long getVersion() {
	Long l = (Long) getProperty(PROP_VERSION);
	return (l == null) ? INITAL_VERSION : l.longValue();
    }

    public boolean isNewerThan(Entity other) {
	if (other == null) {
	    return true;
	}
	long myVersion = getVersion(), otherVersion = other.getVersion();
	if (myVersion == INITAL_VERSION && otherVersion == Long.MAX_VALUE) {
	    // it has overloaded.
	    return true;
	} else {
	    return myVersion > otherVersion;
	}
    }

    public void incrementVersion() {
	setVersion(getVersion() + 1);
    }

    public void setVersion(long newPropValue) {
	changeProperty(PROP_VERSION, new Long(newPropValue));
    }

    /**
     * @param description
     * @param loc
     */
    public void setDescription(String description, Locale loc) {
	if (description == null || description.isEmpty())
	    return;
	addMultiLangProp(PROP_RDFS_COMMENT, new LangString(description,
		loc == null ? null : loc2ID(loc)));
    }

    /**
     * @param loc
     * @return
     */
    public String getDescription(Locale loc) {
	return getMultiLangProp(PROP_RDFS_COMMENT,
		loc == null ? null : loc2ID(loc), true).getString();
    }

    private static String loc2ID(Locale loc) {
	return loc.getLanguage();
    }

    /**
     * @param loc
     * @return
     */
    public boolean containsDescriptionIn(Locale loc) {
	Object o = getProperty(PROP_RDFS_COMMENT);
	String l = loc2ID(loc);
	if (o == null)
	    return false;
	if (o instanceof List) {
	    Iterator it = ((List) o).iterator();
	    while (it.hasNext()) {
		o = it.next();
		if (o instanceof LangString) {
		    LangString ls = (LangString) o;
		    if (ls.getLang().equals(l))
			return true;
		}
	    }
	} else {
	    if (o instanceof LangString) {
		LangString ls = (LangString) o;
		if (l.equals(ls.getLang()))
		    return true;
	    }
	}
	return false;
    }
}
