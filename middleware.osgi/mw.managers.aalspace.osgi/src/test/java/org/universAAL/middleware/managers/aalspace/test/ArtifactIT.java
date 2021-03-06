package org.universAAL.middleware.managers.aalspace.test;

import org.universAAL.itests.IntegrationTest;

/**
 * Here developer's of this artifact should code their integration tests.
 * 
 * @author rotgier
 * 
 */
public class ArtifactIT extends IntegrationTest {

    public ArtifactIT() {
	setRunArguments("net.slp.port", "7000");
	setRunArguments("java.net.preferIPv4Stack", "true");
    }

    public void testComposite() {
	logAllBundles();
    }
}