/*	
	Copyright 2008-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer Gesellschaft - Institut f�r Graphische Datenverarbeitung 
	
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
package org.universAAL.middleware.service;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.middleware.sodapop.msg.Message;


/**
 * @author mtazari - <a href="mailto:Saied.Tazari@igd.fraunhofer.de">Saied Tazari</a>
 *
 */
public interface ServiceBus {
	public void addAvailabilitySubscription(String callerID,
			AvailabilitySubscriber subscriber,
			ServiceRequest request);
	
	public void addNewRegParams(String calleeID, ServiceProfile[] realizedServices);
	
	public ServiceProfile[] getAllServices(String callerID);
	public ServiceProfile[] getMatchingService(String callerID, Service s);
	public ServiceProfile[] getMatchingService(String callerID, String[] keywords);

	public String register(ServiceCaller caller);
	public String register(ServiceCallee callee, ServiceProfile[] realizedServices);

	public void removeAvailabilitySubscription(String callerID,
			AvailabilitySubscriber subscriber,
			String requestURI);

	public void removeMatchingRegParams(String calleeID, ServiceProfile[] realizedServices);

	public void sendMessage(String callerID, Message request);
	public void sendReply(String calleeID, Message response);
	
	public void unregister(String callerID, ServiceCaller caller);
	public void unregister(String calleeID, ServiceCallee callee);
}