/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawd Pakuła - variable initializers
 *******************************************************************************/
package org.eclipse.php.internal.core.language;

import java.util.*;
import java.util.Map.Entry;

import org.eclipse.php.core.PHPVersion;

/**
 * This is a container for predefined PHP variables
 * 
 * @author michael
 */
public class PHPVariables {
	public static int GLOBAL = 1 << 0;

	public static int SUPER_GLOBAL = 2 << 1;

	private static Map<PHPVersion, PHPVariables> instances = new HashMap<PHPVersion, PHPVariables>();

	private Map<Integer, String[]> variables = new HashMap<Integer, String[]>();

	private PHPVariables(IPHPVariablesInitializer initializer) {
		LinkedList<String> tmp = new LinkedList<String>();
		initializer.initializeGlobals(tmp);
		variables.put(GLOBAL, tmp.toArray(new String[tmp.size()]));

		tmp.clear();
		initializer.initializeSuperGlobals(tmp);
		variables.put(SUPER_GLOBAL, tmp.toArray(new String[tmp.size()]));
	}

	public static String[] getVariables(PHPVersion phpVersion) {
		return getVariables(phpVersion, GLOBAL | SUPER_GLOBAL);
	}

	public static String[] getVariables(PHPVersion phpVersion, int type) {
		return getInstance(phpVersion).getByType(type);
	}

	public String[] getByType(int type) {
		List<String> result = new LinkedList<String>();
		for (Entry<Integer, String[]> item : variables.entrySet()) {
			if (item.getKey() == type) {
				return item.getValue().clone();
			}
			if ((type & item.getKey()) != 0) {
				result.addAll(Arrays.asList(item.getValue()));
			}
		}

		return result.toArray(new String[result.size()]);
	}

	private static PHPVariables getInstance(PHPVersion phpVersion) {
		if (!instances.containsKey(phpVersion)) {
			IPHPVariablesInitializer initializer;
			switch (phpVersion) {
			case PHP5_4:
			case PHP5_5:
			case PHP5_6:
			case PHP7_0:
			case PHP7_1:
				initializer = new PHPVariablesInitializerPHP_5_4();
				break;
			case PHP5:
			case PHP5_3:
			default: // php4
				initializer = new PHPVariablesInitializerPHP_5();
			}
			instances.put(phpVersion, new PHPVariables(initializer));
		}

		return instances.get(phpVersion);
	}

}
