package org.eclipse.php.core.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Desciption: This class is a factory for ProjectPreferencePropagators.
 * It is a singletone that contains a Map of ProjectPreference propagators based on the node qualifier
 * (which is basically the plugin identifier for that preference)
 * @author Eden K.,2006
 *
 */
public class PreferencePropagatorFactory {
	
	private static PreferencePropagatorFactory instance = new PreferencePropagatorFactory();
//	 map of <String (Node Qualifier), ProjectPreferencePropagator>
	private final Map propagatorsMap = new HashMap();

	
	private PreferencePropagatorFactory(){};
	
	/**
	 * @return the singelton of ProjectPreferencePropagatorFactory
	 */
	public static PreferencePropagatorFactory getInstance(){		
		return instance;
	}
	
	/**
	 * 
	 * @param nodeQualifier
	 * @return the ProjectPreferencesPropagator given a nodeQualifier and a preference store
	 */
	public PreferencesPropagator getPreferencePropagator(String nodeQualifier, IPreferenceStore store){
		if (propagatorsMap.containsKey(nodeQualifier)){
			return (PreferencesPropagator) propagatorsMap.get(nodeQualifier);
		} else {
			PreferencesPropagator propagator = new PreferencesPropagator(nodeQualifier, store);
			propagatorsMap.put(nodeQualifier, propagator);
			return propagator;			
		}
	}	
}
