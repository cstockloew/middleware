/*******************************************************************************
 * Copyright 2014 Universidad Politécnica de Madrid
 * Copyright 2014 Fraunhofer-Gesellschaft - Institute for Computer Graphics Research
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

package org.universAAL.middleware.managers.configuration.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.universAAL.middleware.brokers.message.configuration.ConfigurationMessage;
import org.universAAL.middleware.brokers.message.configuration.ConfigurationMessage.ConfigurationMessageType;
import org.universAAL.middleware.interfaces.configuration.configurationEditionTypes.ConfigurableEntityEditor;
import org.universAAL.middleware.managers.configuration.core.owl.Entity;
import org.universAAL.middleware.owl.TypeExpression;
import org.universAAL.middleware.rdf.Resource;

/**
 * Abastract class for all Remote Entities. Implementations for Get description,
 * and get entity (that is updated when the entity is propagated). handy method
 * for sending requests.
 * 
 * @author amedrano
 */
public abstract class RemoteConfigurationEntity extends
	GenericConfigurationEntity implements ConfigurableEntityEditor {

    protected Entity entity;

    /**
     * 
     */
    public RemoteConfigurationEntity(
	    ConfigurationManagerImpl configurationManagerImpl, Entity remote) {
	super(configurationManagerImpl, remote.getURI());
	updateRemoteEntity(remote);
    }

    public void updateRemoteEntity(Entity remote) {
	this.entity = remote;
    }

    /** {@ inheritDoc} */
    public String getDescription(Locale loc) {

	if (entity.containsDescriptionIn(loc)) {
	    return entity.getDescription(loc);
	} else {
	    sendRequestFor(entity, loc);
	}
	return null;
    }

    protected Entity getEntity() {
	return entity;
    }

    /** {@ inheritDoc} */
    @Override
    public void updated(Entity e) {
	if (e.isNewerThan(entity)) {
	    entity = e;
	    super.updated(e);
	}
    }

    /**
     * used to force a remote update.
     * 
     * @param e
     * @param loc
     */
    protected void sendRequestFor(Entity e, Locale loc) {
	// create request
	// TODO create filters
	List<TypeExpression> filter = new ArrayList<TypeExpression>();

	Resource root = new Resource();
	root.changeProperty(ConfigurationManagerImpl.PROP_PARAM, filter);
	root.changeProperty(ConfigurationManagerImpl.PROP_LOCALE, loc);
	ConfigurationMessage cm = new ConfigurationMessage(
		ConfigurationMessageType.QUERY, confManager.shared
			.getAalSpaceManager().getMyPeerCard(),
		confManager.shared.getMessageContentSerializer()
			.serialize(root));

	// TODO add receivers.

	// send
	confManager.shared.getControlBroker().sendConfigurationMessage(cm);
    }

}