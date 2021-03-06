/*	
	Copyright 2007-2014 CNR-ISTI, http://isti.cnr.it
	Institute of Information Science and Technologies 
	of the Italian National Research Council 

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

package org.universAAL.middleware.connectors.deploy.karaf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Definition of the Feature.
 *             
 * 
 * <p>Java class for feature complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="feature">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="config" type="{http://karaf.apache.org/xmlns/features/v1.0.0}config"/>
 *         &lt;element name="configfile" type="{http://karaf.apache.org/xmlns/features/v1.0.0}configFile"/>
 *         &lt;element name="feature" type="{http://karaf.apache.org/xmlns/features/v1.0.0}dependency"/>
 *         &lt;element name="bundle" type="{http://karaf.apache.org/xmlns/features/v1.0.0}bundle"/>
 *       &lt;/choice>
 *       &lt;attribute name="name" use="required" type="{http://karaf.apache.org/xmlns/features/v1.0.0}featureName" />
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" default="0.0.0" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="resolver" type="{http://karaf.apache.org/xmlns/features/v1.0.0}resolver" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "feature", propOrder = {
    "detailsOrConfigOrConfigfile"
})
public class Feature implements Serializable
{

    private final static long serialVersionUID = 12343L;
    @XmlElements({
        @XmlElement(name = "details", type = String.class),
        @XmlElement(name = "config", type = Config.class),
        @XmlElement(name = "configfile", type = ConfigFile.class),
        @XmlElement(name = "feature", type = Dependency.class),
        @XmlElement(name = "bundle", type = Bundle.class)
    })
    protected List<Serializable> detailsOrConfigOrConfigfile;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "resolver")
    protected String resolver;

    /**
     * Gets the value of the detailsOrConfigOrConfigfile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detailsOrConfigOrConfigfile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetailsOrConfigOrConfigfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link Config }
     * {@link ConfigFile }
     * {@link Dependency }
     * {@link Bundle }
     * 
     * 
     */
    public List<Serializable> getDetailsOrConfigOrConfigfile() {
        if (detailsOrConfigOrConfigfile == null) {
            detailsOrConfigOrConfigfile = new ArrayList<Serializable>();
        }
        return this.detailsOrConfigOrConfigfile;
    }

    public boolean isSetDetailsOrConfigOrConfigfile() {
        return ((this.detailsOrConfigOrConfigfile!= null)&&(!this.detailsOrConfigOrConfigfile.isEmpty()));
    }

    public void unsetDetailsOrConfigOrConfigfile() {
        this.detailsOrConfigOrConfigfile = null;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "0.0.0";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    public boolean isSetVersion() {
        return (this.version!= null);
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    public boolean isSetDescription() {
        return (this.description!= null);
    }

    /**
     * Gets the value of the resolver property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResolver() {
        return resolver;
    }

    /**
     * Sets the value of the resolver property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResolver(String value) {
        this.resolver = value;
    }

    public boolean isSetResolver() {
        return (this.resolver!= null);
    }

}
