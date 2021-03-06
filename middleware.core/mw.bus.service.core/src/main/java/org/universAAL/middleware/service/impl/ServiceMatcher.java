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
package org.universAAL.middleware.service.impl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.owl.Intersection;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfo;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.PropertyRestriction;
import org.universAAL.middleware.owl.TypeExpression;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.ServiceBus;
import org.universAAL.middleware.service.aapi.AapiServiceRequest;
import org.universAAL.middleware.service.owl.InitialServiceDialog;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.process.ProcessResult;

// TODO: change the log message to match class- and method name.
// BUT: update also the references, e.g. in Log Monitor Tool
public class ServiceMatcher {

    public boolean matches(ServiceWrapper superset, ServiceWrapper subset,
	    HashMap context, Long logID) {
	// special case for UI
	if (subset.getService() instanceof InitialServiceDialog)
	    return matchInitialServiceDialog(superset, subset);

	// match the service
	if (!matchService(superset, subset))
	    return false;

	if (!matchRestrictions(superset, subset, context, logID))
	    return false;

	HashMap cloned = (HashMap) context.clone();

	if (!matchEffects(superset, subset, cloned, logID))
	    return false;

	if (!matchOutputs(superset, subset, cloned, logID))
	    return false;

	// synchronize the context for the effect and output bindings check
	if (cloned.size() > context.size())
	    for (Iterator i = cloned.keySet().iterator(); i.hasNext();) {
		Object key = i.next();
		if (!context.containsKey(key))
		    context.put(key, cloned.get(key));
	    }

	processNonSemanticInput(superset, context);
	processServiceUri(superset, subset, context);

	return true;
    }

    private boolean matchService(ServiceWrapper superset, ServiceWrapper subset) {
	Service subsetService = subset.getService();
	if (subsetService == null)
	    return false;

	Service superService = superset.getService();
	if (superService == null)
	    return false;

	if (!ManagedIndividual.checkMembership(superService.getClassURI(),
		subsetService))
	    return false;
	/*
	 * By checking the membership of offer in requestedServiceClass two
	 * lines before, the compatibility of offer with the request at hand is
	 * guaranteed => we do not need to check class level restrictions.
	 */

	return true;
    }

    private boolean matchInitialServiceDialog(ServiceWrapper superset,
	    ServiceWrapper subset) {
	// special case: UserInterfaceProfile for UI
	Service subsetService = subset.getService();
	if (subsetService == null)
	    return false;

	Service superService = superset.getService();
	if (superService == null)
	    return false;

	if (superService instanceof InitialServiceDialog) {
	    if (!(subsetService instanceof InitialServiceDialog))
		return false;
	    // both elements are initial service dialogs for ui bus

	    // 1. check service class
	    Object superClass = superset
		    .getInitialServiceDialogProperty(InitialServiceDialog.PROP_CORRELATED_SERVICE_CLASS);
	    Object subClass = subset
		    .getInitialServiceDialogProperty(InitialServiceDialog.PROP_CORRELATED_SERVICE_CLASS);

	    if (!(superClass instanceof Resource)
		    || !(subClass instanceof Resource))
		return false;

	    if (!(superClass.toString().equals(subClass.toString()))) {
		OntClassInfo subOntClassInfo = OntologyManagement.getInstance()
			.getOntClassInfo(subClass.toString());

		if (subOntClassInfo == null)
		    // probably the ontology is not available -> we cannot check
		    // for membership
		    return false;

		if (!subOntClassInfo.hasSuperClass(superClass.toString(), true))
		    return false;
	    }

	    // 2. check vendor
	    Object superVendor = superset
		    .getInitialServiceDialogProperty(InitialServiceDialog.PROP_HAS_VENDOR);
	    Object subVendor = subset
		    .getInitialServiceDialogProperty(InitialServiceDialog.PROP_HAS_VENDOR);
	    if (!(String.valueOf(superVendor).equals(String.valueOf(subVendor))))
		return false;
	}
	return true;
    }

    private boolean matchRestrictions(ServiceWrapper superset,
	    ServiceWrapper subset, HashMap context, Long logID) {
	Service subsetService = subset.getService();
	Service supersetService = superset.getService();

	// for checking later if the concrete values provided by the requester
	// are really used as input
	// this captures cases where, e.g., both getCalenderEvents(user) and
	// getCalenderEvent(user, eventID)
	// have no effects and return objects of the same type
	// TODO: the better solution is to allow complex output bindings that
	// state how the outputs are
	// filtered by specifying a chain of restrictions (in addition to simple
	// output bindings that
	// only specify the corresponding property path)
	int expectedSize = context.size();
	if (subsetService.getProfile() != null)
	    expectedSize += subsetService.getNumberOfValueRestrictions();
	// subsetService.getProfile().getNumberOfMandatoryInputs();

	String[] restrProps = supersetService
		.getRestrictedPropsOnInstanceLevel();
	if (restrProps != null && restrProps.length > 0) {
	    for (int i = 0; i < restrProps.length; i++) {
		String restrProp = restrProps[i];

		// request instance level restrictions
		TypeExpression supersetInsRestr = supersetService
			.getInstanceLevelRestrictionOnProp(restrProp);

		if (!(supersetInsRestr instanceof MergedRestriction)) {
		    // makes no sense, because 'restrProps' must have
		    // instance level restrictions
		    continue;
		}

		if (Service.PROP_OWLS_PRESENTS.equals(restrProp)) {
		    // the restricted property is a non-functional parameter

		    supersetInsRestr = (TypeExpression) ((MergedRestriction) supersetInsRestr)
			    .getConstraint(MergedRestriction.allValuesFromID);
		    if (supersetInsRestr instanceof PropertyRestriction) {
			restrProp = ((PropertyRestriction) supersetInsRestr)
				.getOnProperty();
			if (restrProp == null)
			    // strange!
			    continue;
			// some properties of service profiles are managed by
			// the middleware
			Object o = context.get(restrProp);
			if (o == null)
			    // then, it relates to those properties set by the
			    // provider
			    o = superset.getProperty(restrProp);
			if (o == null
				|| !supersetInsRestr.hasMember(o, context, -1,
					null))
			    return false;
		    } else if (supersetInsRestr instanceof Intersection)
			for (Iterator j = ((Intersection) supersetInsRestr)
				.types(); j.hasNext();) {
			    // the same as above, only this time in a loop
			    // over all members of the intersection
			    supersetInsRestr = (TypeExpression) j.next();
			    if (supersetInsRestr instanceof PropertyRestriction) {
				restrProp = ((PropertyRestriction) supersetInsRestr)
					.getOnProperty();
				if (restrProp == null)
				    // strange!
				    continue;
				Object o = context.get(restrProp);
				if (o == null)
				    o = superset.getProperty(restrProp);
				if (o == null
					|| !supersetInsRestr.hasMember(o,
						context, -1, null))
				    return false;
			    }
			}
		    else
			// strange!
			continue;
		    // we are done with this property
		    continue;
		} else {
		    // the restricted property is NOT a non-functional parameter

		    // offer class level restrictions
		    TypeExpression subsetClsRestr = Service
			    .getClassRestrictionsOnProperty(
				    subsetService.getClassURI(), restrProp);

		    // offer instance level restrictions
		    TypeExpression subsetInsRestr = subsetService
			    .getInstanceLevelRestrictionOnProp(restrProp);

		    // request class level restrictions
		    TypeExpression supersetClsRestr = Service
			    .getClassRestrictionsOnProperty(
				    supersetService.getClassURI(), restrProp);

		    Intersection subsetAllRestr = new Intersection();
		    subsetAllRestr.addType(subsetClsRestr);
		    subsetAllRestr.addType(subsetInsRestr);

		    Intersection supersetAllRestr = new Intersection();
		    supersetAllRestr.addType(supersetClsRestr);
		    supersetAllRestr.addType(supersetInsRestr);

		    // System.out.println(" -- requestRestriction:\n" +
		    // reqAllRestr.toStringRecursive());
		    // System.out.println(" -- offerRestriction:\n" +
		    // offAllRestr.toStringRecursive());

		    if (!supersetAllRestr.matches(subsetAllRestr, context, -1,
			    null)) {
			if (logID != null)
			    LogUtils.logTrace(
				    ServiceBusImpl.getModuleContext(),
				    ServiceRealization.class,
				    "matches",
				    new Object[] {
					    ServiceBus.LOG_MATCHING_MISMATCH,
					    "no subset relationship for restricted property",
					    "\nrestricted property: ",
					    restrProp,
					    ServiceBus.LOG_MATCHING_MISMATCH_CODE,
					    Integer.valueOf(1022),
					    ServiceBus.LOG_MATCHING_MISMATCH_DETAILS,
					    " A property is restricted in the request but the restrictions do not"
						    + " match the restrictions of the offer.",
					    logID }, null);
			return false;
		    }
		}
	    }
	}

	if (context.size() < expectedSize) {
	    LogUtils.logTrace(
		    ServiceBusImpl.getModuleContext(),
		    ServiceRealization.class,
		    "matches",
		    new Object[] {
			    ServiceBus.LOG_MATCHING_MISMATCH,
			    "input in offer not defined in request",
			    ServiceBus.LOG_MATCHING_MISMATCH_CODE,
			    Integer.valueOf(1023),
			    ServiceBus.LOG_MATCHING_MISMATCH_DETAILS,
			    " An input parameter is given in the offer, e.g. a filtering value, but the service request"
				    + " does not provide this input parameter. The exact parameter is not determined, only"
				    + " the number of parameters has been found to be problematic.",
			    logID }, null);
	    return false;
	}

	return true;
    }

    private boolean matchEffects(ServiceWrapper superset,
	    ServiceWrapper subset, HashMap context, Long logID) {
	// check effects
	if (!ProcessResult.checkEffects(superset.getEffects(),
		subset.getEffects(), context, logID))
	    return false;

	return true;
    }

    private boolean matchOutputs(ServiceWrapper superset,
	    ServiceWrapper subset, HashMap context, Long logID) {
	// check output bindings
	if (!ProcessResult.checkOutputBindings(superset.getOutputs(),
		subset.getOutputs(), context, logID))
	    return false;

	return true;
    }

    private void processNonSemanticInput(ServiceWrapper superset,
	    HashMap context) {
	// NON_SEMANTIC_INPUT:
	// if service matches then non-semantic input has to be copied to the
	// context
	Hashtable nonSemanticInput = null;
	try {
	    nonSemanticInput = superset.getNonSemanticInput();
	} catch (Exception ex) {
	    LogUtils.logDebug(
		    ServiceBusImpl.getModuleContext(),
		    ServiceRealization.class,
		    "matches",
		    new Object[] { "Exception occured when trying to get non-semantic parameters from AapiServiceRequest" },
		    ex);
	}
	if (nonSemanticInput != null) {
	    context.put(AapiServiceRequest.PROP_NON_SEMANTIC_INPUT,
		    nonSemanticInput);
	}
    }

    @SuppressWarnings("PMD.CollapsibleIfStatements")
    private void processServiceUri(ServiceWrapper superset,
	    ServiceWrapper subset, HashMap context) {
	// uAAL_SERVICE_URI_MATCHED:
	// if URI of offered service matches exactly URI specified in
	// ServiceRequest then it is indicated in the context by means of
	// uAAL_SERVICE_URI_MATCHED property.
	String requestedServiceUri = superset.getService().getURI();
	String offeredURI = subset.getService().getURI();
	if (requestedServiceUri != null) {
	    if (requestedServiceUri.equals(offeredURI)) {
		context.put(ServiceRealization.uAAL_SERVICE_URI_MATCHED,
			Boolean.TRUE);
	    }
	}
    }
}
