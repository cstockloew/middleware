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

import java.util.ArrayList;
import java.util.List;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.Resource;

/**
 * Ontological representation of ConfigurationParameter in the aalconfiguration
 * ontology. Methods included in this class are the mandatory ones for
 * representing an ontological concept in Java classes for the universAAL
 * platform. In addition getters and setters for properties are included.
 * 
 * @author amedrano
 * @author Generated by the OntologyUML2Java transformation of AAL Studio
 */
public class ConfigurationParameter extends Entity {
    public static final String MY_URI = AALConfigurationOntology.NAMESPACE
	    + "ConfigurationParameter";
    public static final String PROP_DEFAULT_VALUE = AALConfigurationOntology.NAMESPACE
	    + "defaultValue";

    public static final String PROP_RDF_TYPE2 = PROP_RDF_TYPE + "2";

    public static final String PROP_VALUE = org.universAAL.middleware.interfaces.configuration.configurationDefinitionTypes.ConfigurationParameter.PROP_CONFIG_VALUE;

    public ConfigurationParameter() {
	super();
    }

    public ConfigurationParameter(String uri) {
	super(uri);
    }

    public String getClassURI() {
	return MY_URI;
    }

    public int getPropSerializationType(String propURI) {
	if (PROP_DEFAULT_VALUE.equals(propURI))
	    return PROP_SERIALIZATION_FULL;
	if (PROP_VALUE.equals(propURI))
	    return PROP_SERIALIZATION_FULL;
	return super.getPropSerializationType(propURI);
    }

    public boolean isWellFormed() {
	return super.isWellFormed() && hasProperty(PROP_DEFAULT_VALUE)
		&& hasProperty(PROP_VALUE);
    }

    public Object getValue() {
	return getProperty(PROP_VALUE);
    }

    private boolean checkValue(Object newPropValue) {
	Resource test = this.copy(false);
	test.changeProperty(PROP_VALUE, newPropValue);
	// check Restrictions
	MergedRestriction mr = getParameterRestriction();
	if (mr != null)
	    return mr.hasMember(test);
	else
	    return false;
    }

    public boolean setValue(Object newPropValue) {
	if (newPropValue != null && checkValue(newPropValue)) {
	    return changeProperty(PROP_VALUE, newPropValue);
	}
	return false;
    }

    public Object getDefaultValue() {
	return getProperty(PROP_DEFAULT_VALUE);
    }

    public void setDefaultValue(Object newPropValue) {
	if (newPropValue != null)
	    changeProperty(PROP_DEFAULT_VALUE, newPropValue);
    }

    public void changeParameterRestriction(MergedRestriction r) {
	if (r == null)
	    return;
	Object o = props.get(PROP_RDF_TYPE2);
	if (o instanceof List) {
	    ((List) o).remove(getParameterRestriction());
	    ((List) o).add(r);
	} else {
	    List l = new ArrayList(2);
	    if (o instanceof Resource && !r.equals(o))
		l.add(o);
	    l.add(r);
	    props.put(PROP_RDF_TYPE2, l);
	}
    }

    public MergedRestriction getParameterRestriction() {
	Object o = props.get(PROP_RDF_TYPE2);
	if (o instanceof List) {
	    for (Object t : (List) o) {
		if (t instanceof MergedRestriction
			&& ((MergedRestriction) t).getOnProperty().equals(
				ConfigurationParameter.PROP_VALUE)) {
		    return (MergedRestriction) t;
		}
	    }
	} else if (o instanceof MergedRestriction
		&& ((MergedRestriction) o).getOnProperty().equals(
			ConfigurationParameter.PROP_VALUE)) {
	    return (MergedRestriction) o;
	}
	return null;
    }
}
