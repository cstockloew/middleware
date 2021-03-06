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
package org.universAAL.middleware.managers.api;

import java.util.HashMap;
import java.util.Map;

import org.universAAL.middleware.deploymanager.uapp.model.Part;
import org.universAAL.middleware.interfaces.PeerCard;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano Lenzi</a>
 * @version $LastChangedRevision$ ( $LastChangedDate: 2014-01-20 11:22:38
 *          +0100 (Mo, 20 Jan 2014) $ )
 */
public class InstallationResultsDetails {

    private InstallationResults results;
    private Map<String, Map<String, InstallationResults>> details = new HashMap<String, Map<String, InstallationResults>>();

    public InstallationResultsDetails(InstallationResults status) {
	this.results = status;
    }

    public void setDetailedResult(String id, String partId,
	    InstallationResults result) {
	Map<String, InstallationResults> detail = details.get(id);
	if (detail == null) {
	    detail = new HashMap<String, InstallationResults>();
	    details.put(id, detail);
	}
	detail.put(partId, result);
    }

    public void setDetailedResult(PeerCard peer, Part part,
	    InstallationResults result) {
	setDetailedResult(peer.getPeerID(), part.getPartId(), result);
    }

    public InstallationResults getDetailedResult(String id, String partId) {
	Map<String, InstallationResults> detail = details.get(id);
	if (detail == null) {
	    return InstallationResults.UNKNOWN;
	}
	return detail.get(partId);
    }

    public InstallationResults getDetailedResult(PeerCard peer, Part part) {
	return getDetailedResult(peer.getPeerID(), part.getPartId());
    }

    public InstallationResults getGlobalResult() {
	return results;
    }

    public void setGlobalResult(InstallationResults global) {
	this.results = global;
    }

}
