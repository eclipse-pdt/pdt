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
package org.eclipse.php.phpunit.ui.launch;

import java.util.ArrayList;
import java.util.List;

public class PHPUnitOptionsList {

	private List<String> phpUnitArgs = new ArrayList<>();
	private String elementToTest;

	public void add(PHPUnitOption option, String value) {
		phpUnitArgs.add(option.getOptionName());
		phpUnitArgs.add(value);
	}

	public void add(String value) {
		phpUnitArgs.add(value);
	}

	public void setElementToTest(String elementToTest) {
		this.elementToTest = elementToTest;
	}

	public String getElementToTest() {
		return elementToTest;
	}

	public List<String> getList() {
		List<String> tmpList = new ArrayList<>(phpUnitArgs);
		if (elementToTest != null && !elementToTest.isEmpty()) {
			tmpList.add(elementToTest);
		}
		return tmpList;
	}

}
