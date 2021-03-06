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
package org.universAAL.middleware.context.impl;

import org.universAAL.middleware.bus.member.BusMember;
import org.universAAL.middleware.bus.model.AbstractBus;
import org.universAAL.middleware.bus.model.BusStrategy;
import org.universAAL.middleware.bus.msg.BusMessage;
import org.universAAL.middleware.bus.msg.MessageType;
import org.universAAL.middleware.connectors.exception.CommunicationConnectorException;
import org.universAAL.middleware.connectors.util.ChannelMessage;
import org.universAAL.middleware.container.Container;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextBus;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.context.owl.ContextBusOntology;
import org.universAAL.middleware.modules.CommunicationModule;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.util.Constants;
import org.universAAL.middleware.util.ResourceComparator;

/**
 * @author mtazari - <a href="mailto:Saied.Tazari@igd.fraunhofer.de">Saied
 *         Tazari</a>
 * 
 */
public class ContextBusImpl extends AbstractBus implements ContextBus {

    private static Object[] busFetchParams;
    private static ContextBusImpl theContextBus;
    private static ContextBusOntology contextBusOntology = null;

    public static Object[] getContextBusFetchParams() {
	return busFetchParams.clone();
    }

    public synchronized void assessContentSerialization(Resource content) {
	if (Constants.debugMode()) {
	    LogUtils.logDebug(
		    context,
		    ContextBusImpl.class,
		    "assessContentSerialization",
		    new Object[] { "Assessing message content serialization:" },
		    null);
	    // System.out.println(new RuntimeException().getStackTrace()[1]);

	    String str = BusMessage.trySerializationAsContent(content);
	    LogUtils.logDebug(
		    context,
		    ContextBusImpl.class,
		    "assessContentSerialization",
		    new Object[] { "\n      1. serialization dump\n", str,
			    "\n      2. deserialize & compare with the original resource\n" },
		    null);
	    new ResourceComparator().printDiffs(content,
		    (Resource) BusMessage.deserializeAsContent(str));
	}
    }

    public static synchronized void startModule(Container c, ModuleContext mc,
	    Object[] contextBusShareParams, Object[] contextBusFetchParams) {
	if (theContextBus == null) {
	    contextBusOntology = new ContextBusOntology();
	    OntologyManagement.getInstance().register(mc, contextBusOntology);
	    theContextBus = new ContextBusImpl(mc);
	    busFetchParams = contextBusFetchParams;
	    c.shareObject(mc, theContextBus, contextBusShareParams);
	}
    }

    public static void stopModule() {
	if (theContextBus != null) {
	    OntologyManagement.getInstance().unregister(theContextBus.context,
		    contextBusOntology);
	    contextBusOntology = null;
	    theContextBus.dispose();
	    theContextBus = null;
	}
    }

    private ContextBusImpl(ModuleContext mc) {
	super(mc, "mw.bus.context.osgi");
	busStrategy.setBus(this);
    }

    protected BusStrategy createBusStrategy(CommunicationModule commModule) {
	return new ContextStrategy(commModule);
    }

    public void addNewRegParams(String memberID,
	    ContextEventPattern[] registrParams) {
	if (memberID != null && registrParams != null) {
	    Object o = registry.getBusMemberByID(memberID);
	    if (o instanceof ContextSubscriber) {
		((ContextStrategy) busStrategy).addRegParams(
			(ContextSubscriber) o, registrParams);
	    } else if (o instanceof ContextPublisher) {
		((ContextStrategy) busStrategy).addRegParams(
			(ContextPublisher) o, registrParams);
	    }
	    registry.addRegParams(memberID, registrParams);
	}
    }

    public ContextEventPattern[] getAllProvisions(String publisherID) {
	if (publisherID != null) {
	    Object o = registry.getBusMemberByID(publisherID);
	    if (o instanceof ContextPublisher) {
		return ((ContextStrategy) busStrategy)
			.getAllProvisions((ContextPublisher) o);
	    }
	}
	return null;
    }

    public void removeMatchingRegParams(String memberID,
	    ContextEventPattern[] oldRegistrParams) {
	if (memberID != null && oldRegistrParams != null) {
	    Object o = registry.getBusMemberByID(memberID);
	    if (o instanceof ContextSubscriber) {
		((ContextStrategy) busStrategy).removeMatchingRegParams(
			(ContextSubscriber) o, oldRegistrParams);
	    } else if (o instanceof ContextPublisher) {
		((ContextStrategy) busStrategy).removeMatchingRegParams(
			(ContextPublisher) o, oldRegistrParams);
	    }
	    registry.removeRegParams(memberID, oldRegistrParams);
	}
    }

    public void brokerContextEvent(String publisherID, ContextEvent msg) {
	assessContentSerialization(msg);
	if (publisherID != null) {
	    super.brokerMessage(publisherID, new BusMessage(MessageType.event,
		    msg, this));
	}
    }

    public void unregister(String publisherID, ContextPublisher publisher) {
	super.unregister(publisherID, publisher);
	((ContextStrategy) busStrategy).removeRegParams(publisher);
    }

    public void unregister(String subscriberID, ContextSubscriber subscriber) {
	super.unregister(subscriberID, subscriber);
	((ContextStrategy) busStrategy).removeRegParams(subscriber);
    }

    @Override
    public void unregister(String memberID, BusMember m) {
	if (m instanceof ContextPublisher)
	    unregister(memberID, (ContextPublisher) m);
	else if (m instanceof ContextSubscriber)
	    unregister(memberID, (ContextSubscriber) m);
    }

    public void handleSendError(ChannelMessage message,
	    CommunicationConnectorException e) {
	// TODO Auto-generated method stub
    }
}
