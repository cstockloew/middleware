/*	
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
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
package org.universAAL.middleware.context;

import org.universAAL.middleware.context.rdf.ContextEvent;
import org.universAAL.middleware.context.rdf.ContextEventPattern;


/**
 * @author mtazari - <a href="mailto:Saied.Tazari@igd.fraunhofer.de">Saied Tazari</a>
 *
 */
public interface ContextBus {
	public void addNewRegParams(String subscriberID, ContextEventPattern[] newSubscriptions);

	public String register(ContextPublisher publisher);
	public String register(ContextSubscriber subscriber, ContextEventPattern[] initialSubscriptions);

	public void removeMatchingRegParams(String subscriberID, ContextEventPattern[] oldSubscriptions);

	public void sendMessage(String publisherID, ContextEvent event);
	
	public void unregister(String publisherID, ContextPublisher publisher);
	public void unregister(String subscriberID, ContextSubscriber subscriber);
}