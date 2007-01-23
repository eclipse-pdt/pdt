package org.eclipse.php.internal.core.util.preferences;

import java.util.HashMap;

/**
 * The IXMLPreferencesStorable should be implemented by any class that should save or load its properties
 * as XML. 
 * This class works with the XMLPreferencesWriter and XMLPreferencesReader.
 *
 */
public interface IXMLPreferencesStorable {

	/**
	 * Returns hash map, that represent this object.
	 * @return HashMap
	 */
	public HashMap storeToMap();

	/**
	 * Restores the object from the map.
	 * @param HashMap map
	 */
	public void restoreFromMap(HashMap map);
}
