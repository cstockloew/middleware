/**
 * 
 *  OCO Source Materials 
 *      � Copyright IBM Corp. 2012 
 *
 *      See the NOTICE file distributed with this work for additional 
 *      information regarding copyright ownership 
 *       
 *      Licensed under the Apache License, Version 2.0 (the "License"); 
 *      you may not use this file except in compliance with the License. 
 *      You may obtain a copy of the License at 
 *       	http://www.apache.org/licenses/LICENSE-2.0 
 *       
 *      Unless required by applicable law or agreed to in writing, software 
 *      distributed under the License is distributed on an "AS IS" BASIS, 
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *      See the License for the specific language governing permissions and 
 *      limitations under the License. 
 *
 */
package org.universAAL.middleware.service.data.factory;

import org.universAAL.middleware.service.data.ILocalServiceSearchResultsData;
import org.universAAL.middleware.service.data.ILocalServicesIndexData;
import org.universAAL.middleware.service.data.ILocalWaitingCallersData;
import org.universAAL.middleware.service.data.LocalServiceSearchResultsDataMap;
import org.universAAL.middleware.service.data.LocalServicesIndexDataMap;
import org.universAAL.middleware.service.data.LocalWaitingDataMap;

/**
 * 
 * @author <a href="mailto:noamsh@il.ibm.com">noamsh </a>
 * 
 *         Apr 20, 2012
 * 
 */
public class ServiceStrategyDataFactory extends
	AbstractServiceStrategyDataFactory {

    public ILocalServicesIndexData createLocalServicesIndexData() {
	return new LocalServicesIndexDataMap();
    }

    public ILocalServiceSearchResultsData createLocalServiceSearchResultsData() {
	return new LocalServiceSearchResultsDataMap();
    }

    public ILocalWaitingCallersData createLocalWaitingCallersData() {
	return new LocalWaitingDataMap();
    }
}
