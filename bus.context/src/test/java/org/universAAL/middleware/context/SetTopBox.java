/**
 * 
 */
package org.universAAL.middleware.context;

import org.universAAL.middleware.owl.ManagedIndividual;

/**
 * @author mtazari
 *
 */
public class SetTopBox extends ManagedIndividual {
	public static final String MY_URI;
	public static String HAS_ACTION;
	
	static {
		MY_URI = "http://ontology.aal-persona.org/fake.owl#SetTopBox";
		HAS_ACTION = "http://ontology.aal-persona.org/fake.owl#hasAction";
		register(SetTopBox.class);
	}
	
	public SetTopBox(String uri) {
		super(uri);
	}

	public boolean isWellFormed() {
		return true;
	}
	
	public void setProperty(String key, Object value) {
		if (HAS_ACTION.equals(key)  &&  value instanceof String)
			props.put(key, value);
		else
			super.setProperty(key, value);
	}

	public int getPropSerializationType(String propURI) {
		// TODO Auto-generated method stub
		return 0;
	}
}