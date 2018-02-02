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

import java.util.Map;

import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;

public class PHPUnitTestCase extends PHPUnitTest {

	protected boolean dataProviderCase;

	public PHPUnitTestCase(final Map<?, ?> test, final PHPUnitTestGroup parent, RemoteDebugger remoteDebugger) {
		super(test, parent, remoteDebugger);
	}

	public PHPUnitTestCase(final Map<?, ?> test, final PHPUnitTestGroup parent, final String sStatus,
			RemoteDebugger remoteDebugger) {
		this(test, parent, remoteDebugger);
		setStatus(sStatus);
	}

	@Override
	protected void processName(String name) {
		int test = name.lastIndexOf(' ');
		// extract data provider name / number
		if (test > 0) {
			int cutTo = name.lastIndexOf('"');
			if (cutTo < 0 || cutTo < test) {
				int cutFrom = name.indexOf('#', test);
				this.name = name.substring(cutFrom);
			} else {
				int cutFrom = name.lastIndexOf('"', cutTo - 1);
				this.name = name.substring(cutFrom + 1, cutTo);
			}

			this.dataProviderCase = true;
		} else {
			this.name = name;
		}
	}

	public boolean isDataProviderCase() {
		return dataProviderCase;
	}

	public String getFilterName() {
		StringBuilder sb = new StringBuilder();
		if (dataProviderCase) {
			sb.append(((PHPUnitTestGroup) getParent()).getFilterName());
			if (getName().charAt(0) != '#') {
				sb.append('"').append(getName()).append('"');
			} else {
				sb.append(getName());
			}
		} else {
			sb.append(((PHPUnitTestGroup) getParent()).getName());
			sb.append(METHOD_SEPARATOR).append(getName());
		}

		return sb.toString();
	}

	public String toString() {
		return this.getFilterName();
	}

}