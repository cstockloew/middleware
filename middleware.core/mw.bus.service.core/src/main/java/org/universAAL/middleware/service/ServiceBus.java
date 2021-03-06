/*	
	Copyright 2008-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
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
package org.universAAL.middleware.service;

import java.util.HashMap;
import java.util.List;

import org.universAAL.middleware.bus.msg.BusMessage;
import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

/**
 * The service bus is a call-based bus, i.e., the <code>ServiceCaller</code>
 * members who post a message to this bus normally have a service request and
 * would like to receive a response in return, especially if the service result
 * also includes the provision of specific info. Even if no specific info is
 * expected from the <code>ServiceCallee</code> member, at least a hint about
 * the status of the provision of the service is required, e.g. an
 * acknowledgment stating that the request could be forwarded to an appropriate
 * <code>ServiceCallee</code>, or an error message stating that no appropriate
 * service realization could be found. This interface is available as an OSGi
 * service at the OSGi framework. it is implicitly used by the
 * <code>ServiceCaller</code>-s and <code>ServiceCallee</code>-s that are
 * created within the same OSGi bundle context.
 * 
 * @author mtazari - <a href="mailto:Saied.Tazari@igd.fraunhofer.de">Saied
 *         Tazari</a>
 */
public interface ServiceBus {

    public static final String LOG_MATCHING_START = "Matching the request ";
    public static final String LOG_MATCHING_PROFILE = "Matching offer ";
    public static final String LOG_MATCHING_SUCCESS = "Matching successful ";
    public static final String LOG_MATCHING_NOSUCCESS = "Matching not successful ";
    public static final String LOG_MATCHING_PROFILES_END = "Matching of profiles done.";
    public static final String LOG_MATCHING_PROVIDER_END = "Filtering of profiles according to service provider done.";
    public static final String LOG_MATCHING_END = "Matching done.";

    public static final String LOG_MATCHING_MISMATCH = "Mismatch detected: ";
    public static final String LOG_MATCHING_MISMATCH_CODE = "\nmismatch code: ";
    public static final String LOG_MATCHING_MISMATCH_DETAILS = "\ndetailed mismatch message: ";

    /**
     * The call injector allows to directly call a service with a
     * {@link ServiceCall}. There is no matchmaking done and the callee needs to
     * be known. This is not the normal way of invoking a service; normally, a
     * {@link ServiceRequest} should be sent.
     * 
     * This interface is separated from the rest of the bus to have a different
     * shared object and, thus, to allow for low-level security. In OSGi terms,
     * this would be a separate OSGi service that the application needs to have
     * permissions to use.
     * 
     * @author Carsten Stockloew
     * 
     */
    public interface CallInjector {
	/**
	 * Can be used by {@link ServiceCallInjector} to inject a
	 * {@link ServiceCall} to the bus. Compared to the
	 * {@link #brokerRequest(String, BusMessage)} method that is used by the
	 * {@link ServiceCaller}, there is no matchmaking; the call will be sent
	 * directly to a given {@link ServiceCallee}.
	 * 
	 * @param callerID
	 *            the ID of the caller that is sending the call.
	 * @param receiver
	 *            the {@link PeerCard} of the node that hosts the callee.
	 * @param request
	 *            the actual request message.
	 */
	public void brokerCall(String callerID, PeerCard receiver,
		BusMessage call);

	/**
	 * Can be used by {@link ServiceCallInjector} to inject a
	 * {@link ServiceCall} to the bus. Compared to the
	 * {@link #brokerRequest(String, BusMessage)} method that is used by the
	 * {@link ServiceCaller}, there is no matchmaking; the call will be sent
	 * directly to a given {@link ServiceCallee}.
	 * 
	 * @param callerID
	 *            the ID of the caller that is sending the call.
	 * @param receiver
	 *            the URI of the bus member that has registered the matching
	 *            {@link ServiceProfile}. Only the identifier of the node
	 *            that hosts the callee is used from the bus member URI.
	 * @param request
	 *            the actual request message.
	 */
	public void brokerCall(String callerID, String receiver, BusMessage call);
    }

    /**
     * Adds an availability subscription, in other words a listener, to receive
     * events about the availability of services matching the given request.
     * 
     * @param callerID
     *            the ID of the caller that is registering a subscriber.
     * @param subscriber
     *            the object which will be notified when matching services are
     *            advertised or removed from the service bus.
     * @param request
     *            the request to which newly registered or unregistered services
     *            must match in order to notify the subscriber about the
     *            corresponding events.
     */
    public void addAvailabilitySubscription(String callerID,
	    AvailabilitySubscriber subscriber, ServiceRequest request);

    /**
     * Registers (advertises) new services (by providing descriptions of them)
     * that will be provided by the ServiceCallee with the specified ID.
     * 
     * @param calleeID
     *            the ID of the ServiceCallee that is advertising new services
     *            on the service bus.
     * @param realizedServices
     *            the description of the new services in terms of an array of
     *            service profiles.
     * @param throwOnDuplicateReg
     *            Specifies whether this method should throw an exception or
     *            just ignore it when a profile is registered with a process URI
     *            that is already registered.
     * @throws ProfileExistsException
     *             if one of the profiles exists already. In that case, none of
     *             the profiles will be registered.
     */
    public void addNewServiceProfiles(String calleeID,
	    ServiceProfile[] realizedServices, boolean throwOnDuplicateReg);

    /**
     * A method used to retrieve the descriptions of all services advertised on
     * the service bus.
     * 
     * @param callerID
     *            the ID of the caller that is asking the service bus.
     * @return descriptions of all registered services in terms of an array of
     *         service profiles.
     */
    public ServiceProfile[] getAllServices(String callerID);

    /**
     * Get all service profiles that describe services that match the given
     * template in terms of "query by example". This version of the method makes
     * it possible to make more specific queries by specifying restrictions for
     * certain properties of services.
     * 
     * @param callerID
     *            the ID of the caller that is asking the service bus.
     * @param template
     *            the template to be used for making a "query by example"..
     * @return profiles of services registered with the service bus that match
     *         the given template, or null if no such service has been
     *         registered.
     */
    public ServiceProfile[] getMatchingServices(String callerID,
	    Service template);

    /**
     * Get all service profiles that describe services of the given service
     * class. This version of the method makes it easier to make a simple query
     * of all instances of a named service class by specifying its URI.
     * 
     * @param callerID
     *            the ID of the caller that is asking the service bus.
     * @param serviceClassURI
     *            the URI of the desired service class.
     * @return profiles of services registered with the service bus that are
     *         instances of the given service class, or null if no such service
     *         has been registered.
     */
    public ServiceProfile[] getMatchingServices(String callerID,
	    String serviceClassURI);

    /**
     * This version of the method accepts simple keyword-based queries about
     * registered services. The given keywords are checked against all names and
     * textual descriptions used in the service profiles. A match is there only
     * if all the given keywords have at least one occurrence.
     * 
     * @param callerID
     *            the ID of the caller that is asking the service bus.
     * @param keywords
     *            the set of keywords to be used for textual match.
     * @return the profiles of the matched services, or null if no such service
     *         is available.
     */
    public ServiceProfile[] getMatchingServices(String callerID,
	    String[] keywords);

    /**
     * Removes an availability subscription from the bus, which was previously
     * added using <code>addAvailabilitySubscription</code> method.
     * 
     * @param callerID
     *            the ID of the caller that owns the listener
     * @param subscriber
     *            the listeners registered by the caller
     * @param requestURI
     *            the URI of the request used previously for subscription
     */
    public void removeAvailabilitySubscription(String callerID,
	    AvailabilitySubscriber subscriber, String requestURI);

    /**
     * Removes specified service profiles that were previously registered by the
     * ServiceCalee with the specified ID.
     * 
     * @param calleeID
     *            the ID of the ServiceCalee that owns the service profiles.
     * @param realizedServices
     *            the service profiles to be removed.
     */
    public void removeMatchingProfiles(String calleeID,
	    ServiceProfile[] realizedServices);

    /**
     * Can be used by ServiceCallers to send a request to the bus. The bus will
     * then try to find matching services using the set of registered service
     * profiles; for each match, the provider ServiceCallee will be asked by the
     * bus to invoke the corresponding service utility and inform the bus about
     * the results by calling {@link #brokerReply(String, BusMessage)}. The bus
     * will then inform the original requester (the ServiceCaller that has
     * called this method) about the result.
     * 
     * @param callerID
     *            the ID of the caller that is sending the request.
     * @param request
     *            the actual request message.
     */
    public void brokerRequest(String callerID, BusMessage request);

    /**
     * Can be used by ServiceCallees to send a response to the bus which will be
     * delivered to the caller who initiated the initial request.
     * 
     * @param calleeID
     *            the ID of the service callee which processed the request.
     * @param response
     *            the actual response message.
     */
    public void brokerReply(String calleeID, BusMessage response);

    /**
     * Unregisters a service caller from the bus.
     * 
     * @param callerID
     *            the ID of the caller to be unregistered.
     * @param caller
     *            the ServiceCaller object to be unregistered.
     */
    public void unregister(String callerID, ServiceCaller caller);

    /**
     * Unregisters a service callee from the bus.
     * 
     * @param calleeID
     *            the ID of the callee to be unregistered.
     * @param callee
     *            the ServiceCallee object to be unregistered.
     */
    public void unregister(String calleeID, ServiceCallee callee);

    /**
     * Get all service profiles that describe services of the given service
     * class. This version of the method makes it easier to make a simple query
     * of all instances of a named service class by specifying its URI.
     * 
     * @param serviceClassURI
     *            the URI of the desired service class.
     * @return Map containing as a key calleeID which registered ServiceProfiles
     *         in the service bus that are instances of the given service class.
     *         Returned profiles are stored in value part of map in a List.
     */
    public HashMap<String, List<ServiceProfile>> getMatchingServices(
	    String serviceClassURI);
}
