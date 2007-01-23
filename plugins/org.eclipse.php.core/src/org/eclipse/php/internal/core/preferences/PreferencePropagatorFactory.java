package org.eclipse.php.internal.core.preferences;

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
	 * Returns a single instance of the PreferencePropagatorFactory.
	 * @return the singelton of ProjectPreferencePropagatorFactory
	 */
	public static PreferencePropagatorFactory getInstance(){		
		return instance;
	}
	
	/**
	 * Returns a single instance of a {@link PreferencesPropagator} according to the given
	 * nodeQualifier and the preferences store.
	 * 
	 * @param nodeQualifier
	 * @return the ProjectPreferencesPropagator given a nodeQualifier and a preference store
	 */
	public static PreferencesPropagator getPreferencePropagator(String nodeQualifier, IPreferenceStore store){
		PreferencePropagatorFactory factory = getInstance();
		if (factory.propagatorsMap.containsKey(nodeQualifier)){
			return (PreferencesPropagator) factory.propagatorsMap.get(nodeQualifier);
		} else {
			PreferencesPropagator propagator = new PreferencesPropagator(nodeQualifier, store);
			factory.propagatorsMap.put(nodeQualifier, propagator);
			return propagator;			
		}
	}	
}
