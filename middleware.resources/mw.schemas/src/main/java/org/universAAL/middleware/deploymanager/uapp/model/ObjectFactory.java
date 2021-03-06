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

package org.universAAL.middleware.deploymanager.uapp.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the org.universAAL.ucc.model.uapp package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Features_QNAME = new QName("http://karaf.apache.org/xmlns/features/v1.0.0", "features");
    private final static QName _OntologyTypeLocationUrl_QNAME = new QName("http://www.universaal.org/aal-uapp/v1.0.2", "url");
    private final static QName _OntologyTypeLocationRuntimeId_QNAME = new QName("http://www.universaal.org/aal-uapp/v1.0.2", "runtimeId");
    private final static QName _OntologyTypeLocationPath_QNAME = new QName("http://www.universaal.org/aal-uapp/v1.0.2", "path");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.universAAL.ucc.model.uapp
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AalUapp }
     *
     */
    public AalUapp createAalUapp() {
        return new AalUapp();
    }

    /**
     * Create an instance of {@link DeploymentUnit }
     *
     */
    public DeploymentUnit createDeploymentUnit() {
        return new DeploymentUnit();
    }

    /**
     * Create an instance of {@link Part }
     *
     */
    public Part createPart() {
        return new Part();
    }

    /**
     * Create an instance of {@link OntologyType }
     *
     */
    public OntologyType createOntologyType() {
        return new OntologyType();
    }

    /**
     * Create an instance of {@link ContactType }
     *
     */
    public ContactType createContactType() {
        return new ContactType();
    }

    /**
     * Create an instance of {@link DeploymentUnit.ContainerUnit }
     *
     */
    public DeploymentUnit.ContainerUnit createDeploymentUnitContainerUnit() {
        return new DeploymentUnit.ContainerUnit();
    }

    /**
     * Create an instance of {@link AalUapp.ApplicationManagement }
     *
     */
    public AalUapp.ApplicationManagement createAalUappApplicationManagement() {
        return new AalUapp.ApplicationManagement();
    }

    /**
     * Create an instance of {@link AalUapp.App }
     *
     */
    public AalUapp.App createAalUappApp() {
        return new AalUapp.App();
    }

    /**
     * Create an instance of {@link AalUapp.App.MenuEntry }
     *
     */
    public AalUapp.App.MenuEntry createAalUappAppMenuEntry() {
        return new AalUapp.App.MenuEntry();
    }

    /**
     * Create an instance of {@link AalUapp.App.Licenses }
     *
     */
    public AalUapp.App.Licenses createAalUappAppLicenses() {
        return new AalUapp.App.Licenses();
    }

    /**
     * Create an instance of {@link AalUapp.ApplicationCapabilities }
     *
     */
    public AalUapp.ApplicationCapabilities createAalUappApplicationCapabilities() {
        return new AalUapp.ApplicationCapabilities();
    }

    /**
     * Create an instance of {@link AalUapp.ApplicationRequirements }
     *
     */
    public AalUapp.ApplicationRequirements createAalUappApplicationRequirements() {
        return new AalUapp.ApplicationRequirements();
    }

    /**
     * Create an instance of {@link AalUapp.ApplicationPart }
     *
     */
    public AalUapp.ApplicationPart createAalUappApplicationPart() {
        return new AalUapp.ApplicationPart();
    }

    /**
     * Create an instance of {@link ExecutionUnit }
     *
     */
    public ExecutionUnit createExecutionUnit() {
        return new ExecutionUnit();
    }

    /**
     * Create an instance of {@link Broker }
     *
     */
    public Broker createBroker() {
        return new Broker();
    }

    /**
     * Create an instance of {@link ArtifactType }
     *
     */
    public ArtifactType createArtifactType() {
        return new ArtifactType();
    }

    /**
     * Create an instance of {@link Part.PartCapabilities }
     *
     */
    public Part.PartCapabilities createPartPartCapabilities() {
        return new Part.PartCapabilities();
    }

    /**
     * Create an instance of {@link Part.PartRequirements }
     *
     */
    public Part.PartRequirements createPartPartRequirements() {
        return new Part.PartRequirements();
    }

    /**
     * Create an instance of {@link CapabilityType }
     *
     */
    public CapabilityType createCapabilityType() {
        return new CapabilityType();
    }

    /**
     * Create an instance of {@link ReqGroupType }
     *
     */
    public ReqGroupType createReqGroupType() {
        return new ReqGroupType();
    }

    /**
     * Create an instance of {@link ProfileType }
     *
     */
    public ProfileType createProfileType() {
        return new ProfileType();
    }

    /**
     * Create an instance of {@link ReqType }
     *
     */
    public ReqType createReqType() {
        return new ReqType();
    }

    /**
     * Create an instance of {@link ReqAtomType }
     *
     */
    public ReqAtomType createReqAtomType() {
        return new ReqAtomType();
    }

    /**
     * Create an instance of {@link VersionType }
     *
     */
    public VersionType createVersionType() {
        return new VersionType();
    }

    /**
     * Create an instance of {@link ToBeDefined }
     *
     */
    public ToBeDefined createToBeDefined() {
        return new ToBeDefined();
    }

    /**
     * Create an instance of {@link LicenseType }
     *
     */
    public LicenseType createLicenseType() {
        return new LicenseType();
    }

    /**
     * Create an instance of {@link FeaturesRoot }
     *
     */
    public FeaturesRoot createFeaturesRoot() {
        return new FeaturesRoot();
    }

    /**
     * Create an instance of {@link Dependency }
     *
     */
    public Dependency createDependency() {
        return new Dependency();
    }

    /**
     * Create an instance of {@link ConfigFile }
     *
     */
    public ConfigFile createConfigFile() {
        return new ConfigFile();
    }

    /**
     * Create an instance of {@link Config }
     *
     */
    public Config createConfig() {
        return new Config();
    }

    /**
     * Create an instance of {@link Bundle }
     *
     */
    public Bundle createBundle() {
        return new Bundle();
    }

    /**
     * Create an instance of {@link Feature }
     *
     */
    public Feature createFeature() {
        return new Feature();
    }

    /**
     * Create an instance of {@link OntologyType.Location }
     *
     */
    public OntologyType.Location createOntologyTypeLocation() {
        return new OntologyType.Location();
    }

    /**
     * Create an instance of {@link ContactType.OtherChannel }
     *
     */
    public ContactType.OtherChannel createContactTypeOtherChannel() {
        return new ContactType.OtherChannel();
    }

    /**
     * Create an instance of {@link DeploymentUnit.ContainerUnit.Karaf }
     *
     */
    public DeploymentUnit.ContainerUnit.Karaf createDeploymentUnitContainerUnitKaraf() {
        return new DeploymentUnit.ContainerUnit.Karaf();
    }

    /**
     * Create an instance of {@link DeploymentUnit.ContainerUnit.Android }
     *
     */
    public DeploymentUnit.ContainerUnit.Android createDeploymentUnitContainerUnitAndroid() {
        return new DeploymentUnit.ContainerUnit.Android();
    }

    /**
     * Create an instance of {@link AalUapp.ApplicationManagement.RemoteManagement }
     *
     */
    public AalUapp.ApplicationManagement.RemoteManagement createAalUappApplicationManagementRemoteManagement() {
        return new AalUapp.ApplicationManagement.RemoteManagement();
    }

    /**
     * Create an instance of {@link AalUapp.App.MenuEntry.Icon }
     *
     */
    public AalUapp.App.MenuEntry.Icon createAalUappAppMenuEntryIcon() {
        return new AalUapp.App.MenuEntry.Icon();
    }

    /**
     * Create an instance of {@link AalUapp.App.Licenses.Sla }
     *
     */
    public AalUapp.App.Licenses.Sla createAalUappAppLicensesSla() {
        return new AalUapp.App.Licenses.Sla();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FeaturesRoot }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://karaf.apache.org/xmlns/features/v1.0.0", name = "features")
    public JAXBElement<FeaturesRoot> createFeatures(FeaturesRoot value) {
        return new JAXBElement<FeaturesRoot>(_Features_QNAME, FeaturesRoot.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.universaal.org/aal-uapp/v1.0.2", name = "url", scope = OntologyType.Location.class)
    public JAXBElement<Object> createOntologyTypeLocationUrl(Object value) {
        return new JAXBElement<Object>(_OntologyTypeLocationUrl_QNAME, Object.class, OntologyType.Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.universaal.org/aal-uapp/v1.0.2", name = "runtimeId", scope = OntologyType.Location.class)
    public JAXBElement<String> createOntologyTypeLocationRuntimeId(String value) {
        return new JAXBElement<String>(_OntologyTypeLocationRuntimeId_QNAME, String.class, OntologyType.Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.universaal.org/aal-uapp/v1.0.2", name = "path", scope = OntologyType.Location.class)
    public JAXBElement<String> createOntologyTypeLocationPath(String value) {
        return new JAXBElement<String>(_OntologyTypeLocationPath_QNAME, String.class, OntologyType.Location.class, value);
    }

}
