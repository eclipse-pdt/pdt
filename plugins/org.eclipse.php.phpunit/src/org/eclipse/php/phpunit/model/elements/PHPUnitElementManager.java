/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.model.elements;

import java.util.HashMap;
import java.util.Map;

public class PHPUnitElementManager {

	private static PHPUnitElementManager instance;

	/**
	 * @return Singleton
	 */
	public static PHPUnitElementManager getInstance() {
		if (instance == null) {
			instance = new PHPUnitElementManager();
		}
		return instance;
	}

	private PHPUnitTestGroup root;

	private Map<Integer, PHPUnitElement> testMap;

	private PHPUnitElementManager() {
		initialize();
	}

	public void add(final Integer testId, final PHPUnitElement value) {
		testMap.put(testId, value);
	}

	public PHPUnitElement findTest(final int testId) {
		return testMap.get(testId);
	}

	public PHPUnitTestGroup getRoot() {
		return root;
	}

	public void initialize() {
		if (testMap == null) {
			testMap = new HashMap<>();
		} else {
			testMap.clear();
		}
		testMap.clear();
		root = new PHPUnitTestGroup(null, null, null);
	}

	public void setRoot(final PHPUnitTestGroup root) {
		this.root = root;
	}

}