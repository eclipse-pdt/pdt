/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.util.preferences;

import java.util.Map;

/**
 * The IXMLPreferencesStorable should be implemented by any class that should
 * save or load its properties as XML. This class works with the
 * XMLPreferencesWriter and XMLPreferencesReader.
 */
public interface IXMLPreferencesStorable {

	/**
	 * Returns map, that represent this object.
	 * 
	 * @return map
	 */
	public Map<String, Object> storeToMap();

	/**
	 * Restores the object from the map.
	 * 
	 * @param map
	 */
	public void restoreFromMap(Map<String, Object> map);

}
