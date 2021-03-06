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
package org.universAAL.middleware.service.impl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import org.universAAL.middleware.owl.supply.Rating;
import org.universAAL.middleware.rdf.FinalizedResource;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.aapi.AapiServiceRequest;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.profile.ResponseTimeInMilliseconds;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.middleware.util.Constants;
import org.universAAL.middleware.util.IntAggregator;
import org.universAAL.middleware.util.RatingAggregator;

/**
 * The realization of a service to be passed to the service bus as registration
 * parameter.
 * 
 * The ServiceRealization is a resource that has ServiceCalle, ServiceProfile,
 * response times, QoS rankings as properties.
 * 
 * @author mtazari - <a href="mailto:Saied.Tazari@igd.fraunhofer.de">Saied
 *         Tazari</a>
 * 
 */
public class ServiceRealization extends FinalizedResource {
    public static final String MY_URI = uAAL_VOCABULARY_NAMESPACE
	    + "ServiceRealization";

    public static final String uAAL_SERVICE_RESPONSE_TIME = uAAL_VOCABULARY_NAMESPACE
	    + "responseTime";
    public static final String uAAL_SERVICE_QUALITY_OF_SERVICE = uAAL_VOCABULARY_NAMESPACE
	    + "qos";
    public static final String uAAL_SERVICE_PROVIDER = uAAL_VOCABULARY_NAMESPACE
	    + "theCallee";
    public static final String uAAL_SERVICE_PROFILE = uAAL_VOCABULARY_NAMESPACE
	    + "theProfile";
    public static final String uAAL_ASSERTED_SERVICE_CALL = uAAL_VOCABULARY_NAMESPACE
	    + "assertedServiceCall";
    /**
     * This constant is used for indicating that service has matched exactly the
     * URI specified in the Service Request.
     */
    public static final String uAAL_SERVICE_URI_MATCHED = uAAL_VOCABULARY_NAMESPACE
	    + "serviceUriMatched";

    public ServiceRealization() {
	super();
	addType(MY_URI, true);
    }

    public ServiceRealization(String theCallee, ServiceProfile theProfile) {
	super();
	addType(MY_URI, true);
	props.put(uAAL_SERVICE_PROVIDER, theCallee);
	props.put(uAAL_SERVICE_PROFILE, theProfile);
    }

    public ServiceRealization(String instanceURI) {
	super(instanceURI);
	addType(MY_URI, true);
    }

    /**
     * Adds properties of the realization to the hashtable passed as a parameter
     * 
     * @param context
     *            - the hashtable to add properties
     */
    private void addAggregatedProperties(HashMap context) {
	if (context == null)
	    return;

	Rating r = getAvgQoSRating();
	if (r != null)
	    context.put(ServiceProfile.PROP_uAAL_AVERAGE_QOS_RATING, r);
	int t = getAvgResponseTime();
	if (t > -1)
	    context.put(ServiceProfile.PROP_uAAL_AVERAGE_RESPONSE_TIME,
		    new Integer(t));
	r = getMaxQoSRating();
	if (r != null)
	    context.put(ServiceProfile.PROP_uAAL_MAX_QOS_RATING, r);
	t = getMaxResponseTime();
	if (t > -1)
	    context.put(ServiceProfile.PROP_uAAL_MAX_RESPONSE_TIME,
		    new Integer(t));
	r = getMinQoSRating();
	if (r != null)
	    context.put(ServiceProfile.PROP_uAAL_MIN_QOS_RATING, r);
	t = getMinResponseTime();
	if (t > -1)
	    context.put(ServiceProfile.PROP_uAAL_MIN_RESPONSE_TIME,
		    new Integer(t));
	t = getNumberOfQoSRatings();
	if (t > 0)
	    context.put(ServiceProfile.PROP_uAAL_NUMBER_OF_QOS_RATINGS,
		    new Integer(t));
	t = getNumberOfResponseTimeMeasurements();
	if (t > 0)
	    context.put(
		    ServiceProfile.PROP_uAAL_NUMBER_OF_RESPONSE_TIME_MEASUREMENTS,
		    new Integer(t));
    }

    /**
     * add measured response time to the properties of the realization
     * 
     * @param rt
     */
    void addMeasuredResponseTime(int rt) {
	if (rt > 0) {
	    IntAggregator ia = (IntAggregator) props
		    .get(uAAL_SERVICE_RESPONSE_TIME);
	    if (ia == null) {
		ia = new IntAggregator();
		props.put(uAAL_SERVICE_RESPONSE_TIME, ia);
	    }
	    ia.addVote(rt);
	}
    }

    /**
     * Add Quality of Service (QoS) to the properties of the realization
     * 
     * @param r
     *            - the rating of QoS
     */
    void addQoSRating(Rating r) {
	if (r != null) {
	    RatingAggregator ra = (RatingAggregator) props
		    .get(uAAL_SERVICE_QUALITY_OF_SERVICE);
	    if (ra == null) {
		ra = new RatingAggregator();
		props.put(uAAL_SERVICE_QUALITY_OF_SERVICE, ra);
	    }
	    ra.addRating(r);
	}
    }

    /**
     * Adds to the hashtable passed as a parameter an asserted service call
     * representing this ServiceRealization
     * 
     * @param context
     *            - the hashtable to add the asserted service call, contains
     *            input parameters to the service call
     * 
     * @return true iff the operation was successful
     */
    boolean assertServiceCall(HashMap<String, Object> context,
	    ServiceRequest request) {
	ServiceProfile prof = (ServiceProfile) props.get(uAAL_SERVICE_PROFILE);
	if (prof == null)
	    return false;

	String processURI = prof.getProcessURI();
	if (processURI == null)
	    return false;

	ServiceCall result = new ServiceCall(new Resource(processURI));
	result.setScope(request);
	Object user = context.get(Constants.VAR_uAAL_ACCESSING_HUMAN_USER);
	if (user instanceof Resource)
	    result.setInvolvedUser((Resource) user);

	for (Iterator<?> i = prof.getInputs(); i.hasNext();) {
	    ProcessInput in = (ProcessInput) i.next();
	    String uri = null;
	    if (in != null)
		uri = in.getURI();
	    if (uri == null)
		continue;

	    Object o = context.get(uri);
	    if (o != null)
		result.addInput(uri, o);
	    else if (in.getMinCardinality() > 0)
		return false;
	}
	context.put(uAAL_ASSERTED_SERVICE_CALL, result);
	// NON_SEMANTIC_INPUT:
	// if ServiceRequest contains non-semantic input then it has to be
	// propagated to ServiceCall.
	if (context.containsKey(AapiServiceRequest.PROP_NON_SEMANTIC_INPUT)) {
	    result.addNonSemanticInput((Hashtable) context
		    .get(AapiServiceRequest.PROP_NON_SEMANTIC_INPUT));
	}
	return true;
    }

    /**
     * Returns average Quality of Service rating of this ServiceRealization
     * 
     * @return Rating - the average rating
     */
    public Rating getAvgQoSRating() {
	RatingAggregator ra = (RatingAggregator) props
		.get(uAAL_SERVICE_RESPONSE_TIME);
	return (ra == null) ? null : ra.getAverage();
    }

    /**
     * Returns average response time of the measured response times for this
     * ServiceRealization
     * 
     * @return int - the average response time
     */
    public int getAvgResponseTime() {
	IntAggregator ia = (IntAggregator) props
		.get(uAAL_SERVICE_RESPONSE_TIME);
	return (ia == null) ? -1 : ia.getAverage();
    }

    /**
     * Returns maximal Quality of Service rating of this ServiceRealization
     * 
     * @return Rating - the maximal rating
     */
    public Rating getMaxQoSRating() {
	RatingAggregator ra = (RatingAggregator) props
		.get(uAAL_SERVICE_RESPONSE_TIME);
	return (ra == null) ? null : ra.getMax();
    }

    /**
     * Returns maximal response time of the measured response times for this
     * ServiceRealization
     * 
     * @return int - the maximal response time
     */
    public int getMaxResponseTime() {
	IntAggregator ia = (IntAggregator) props
		.get(uAAL_SERVICE_RESPONSE_TIME);
	return (ia == null) ? -1 : ia.getMax();
    }

    /**
     * Returns minimal Quality of Service rating of this ServiceRealization
     * 
     * @return Rating - the minimal rating
     */
    public Rating getMinQoSRating() {
	RatingAggregator ra = (RatingAggregator) props
		.get(uAAL_SERVICE_RESPONSE_TIME);
	return (ra == null) ? null : ra.getMin();
    }

    /**
     * Returns minimal response time of the measured response times for this
     * ServiceRealization
     * 
     * @return int - the minimal response time
     */
    public int getMinResponseTime() {
	IntAggregator ia = (IntAggregator) props
		.get(uAAL_SERVICE_RESPONSE_TIME);
	return (ia == null) ? -1 : ia.getMin();
    }

    /**
     * Returns number of Quality of Service ratings of this ServiceRealization
     * 
     * @return int - the number of QoSRatings
     */
    public int getNumberOfQoSRatings() {
	RatingAggregator ra = (RatingAggregator) props
		.get(uAAL_SERVICE_RESPONSE_TIME);
	return (ra == null) ? 0 : ra.getNumberOfRatings();
    }

    /**
     * Returns number of the measured response times for this ServiceRealization
     * 
     * @return int - the number of measurements
     */
    public int getNumberOfResponseTimeMeasurements() {
	IntAggregator ia = (IntAggregator) props
		.get(uAAL_SERVICE_RESPONSE_TIME);
	return (ia == null) ? 0 : ia.getNumberOfVotes();
    }

    /**
     * Return the ServiceProvider of this ServiceRealization
     * 
     * @return the service provider
     */
    String getProvider() {
	return (String) props.get(uAAL_SERVICE_PROVIDER);
    }

    /**
     * Return the declared response timeout of the ServiceProfile related to
     * this realization
     * 
     * @return int - the response timeout
     */
    public int getResponseTimeout() {
	ServiceProfile prof = (ServiceProfile) props.get(uAAL_SERVICE_PROFILE);
	if (prof != null) {
	    ResponseTimeInMilliseconds timeout = (ResponseTimeInMilliseconds) prof
		    .getProperty(ServiceProfile.PROP_uAAL_RESPONSE_TIMEOUT);
	    if (timeout != null)
		return timeout.getNumberOfMilliseconds();
	}
	return -1;
    }

    /**
     * Return true if the ServiceRequest matches this ServiceRealization +
     * Context
     * 
     * @param request
     *            - the ServiceRequest to match
     * @param context
     *            - the Context to match
     * @param logID
     *            - an id to be used for logging, may be null
     * @return true, if the service request matches.
     */
    public boolean matches(ServiceRequest request, HashMap context, Long logID) {
	if (request == null)
	    return true;
	ServiceProfile prof = (ServiceProfile) props.get(uAAL_SERVICE_PROFILE);
	if (prof == null)
	    return false;

	ServiceWrapper superset = ServiceWrapper.create(request);
	ServiceWrapper subset = ServiceWrapper.create(prof);

	addAggregatedProperties(context);

	return new ServiceMatcher().matches(superset, subset, context, logID);
    }

    /**
     * Return true iff the string passed as a parameter matches this
     * ServiceRealization
     * 
     * @param word
     *            - the string to match
     * @return - true iff the string passed as a parameter matches
     */
    public boolean matches(String word) {
	if (word == null)
	    return true;

	ServiceProfile prof = (ServiceProfile) props.get(uAAL_SERVICE_PROFILE);
	return prof != null
		&& matchStrings(word, prof.getServiceName(),
			prof.getServiceDescription());
    }

    /**
     * Return true iff all the strings in the array of Strings passed as a
     * parameter match this ServiceRealization
     * 
     * @param keywords
     *            - the array of Strings to match
     * @return - true iff all the strings match
     */
    public boolean matchesAll(String[] keywords) {
	if (keywords != null) {
	    ServiceProfile prof = (ServiceProfile) props
		    .get(uAAL_SERVICE_PROFILE);
	    if (prof == null)
		return false;
	    String name = prof.getServiceName(), text = prof
		    .getServiceDescription();
	    for (int i = 0; i < keywords.length; i++)
		if (keywords[i] != null
			&& !matchStrings(keywords[i], name, text))
		    return false;
	}
	return true;
    }

    /**
     * Return true iff one of the strings in the array of Strings passed as a
     * parameter matches this ServiceRealization
     * 
     * @param keywords
     *            - the array of Strings to match
     * @return - true iff one of the strings matches
     */
    public boolean matchesOne(String[] keywords) {
	if (keywords != null) {
	    ServiceProfile prof = (ServiceProfile) props
		    .get(uAAL_SERVICE_PROFILE);
	    if (prof == null)
		return false;
	    String name = prof.getServiceName(), text = prof
		    .getServiceDescription();
	    for (int i = 0; i < keywords.length; i++)
		if (keywords[i] != null
			&& matchStrings(keywords[i], name, text))
		    return true;
	}
	return false;
    }

    /**
     * Returns true iff the 'searched' String appears either in 'name' or in
     * 'text' strings
     * 
     * @param searched
     * @param name
     * @param text
     * @return boolean - true iff the 'searched' Strings appears in the 'name'
     *         or 'text'
     */
    private boolean matchStrings(String searched, String name, String text) {
	if (searched == null || "".equals(searched))
	    return true;
	return (name != null && name.toLowerCase().indexOf(
		searched.toLowerCase()) > -1)
		|| (text != null && text.toLowerCase().indexOf(
			searched.toLowerCase()) > -1);
    }

    /**
     * @see Resource#getPropSerializationType(String)
     */
    public int getPropSerializationType(String propURI) {
	return (uAAL_SERVICE_PROFILE.equals(propURI) || uAAL_SERVICE_PROVIDER
		.equals(propURI)) ? PROP_SERIALIZATION_FULL
		: PROP_SERIALIZATION_OPTIONAL;
    }

    /**
     * @see Resource#isWellFormed()
     */
    public boolean isWellFormed() {
	return props.containsKey(uAAL_SERVICE_PROFILE)
		&& props.containsKey(uAAL_SERVICE_PROVIDER);
    }

    /**
     * @see Resource#setProperty(String, Object)
     */
    public boolean setProperty(String propURI, Object value) {
	if (propURI == null || value == null || props.containsKey(propURI))
	    return false;
	if ((propURI.equals(uAAL_SERVICE_PROFILE)
		&& value instanceof ServiceProfile && ((ServiceProfile) value)
		    .isWellFormed())
		|| (propURI.equals(uAAL_SERVICE_PROVIDER) && value instanceof String)) {
	    props.put(propURI, value);
	    return true;
	}
	return false;
    }
}
