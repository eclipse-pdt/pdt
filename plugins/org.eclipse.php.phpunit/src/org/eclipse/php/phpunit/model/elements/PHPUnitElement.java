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
import org.eclipse.php.phpunit.model.connection.PHPUnitMessageParser;

abstract public class PHPUnitElement {

	protected static final String SEPARATOR_LINE = ":"; //$NON-NLS-1$
	protected static final String SEPARATOR_MESSAGE = "!"; //$NON-NLS-1$
	protected static final String SEPARATOR_NAME = "$"; //$NON-NLS-1$

	protected String file = null;
	protected String localFile = null;
	protected boolean isFiltered = false;
	protected int line = 0;
	protected PHPUnitElement parent;
	protected int testId = 0;

	public PHPUnitElement(final Map<?, ?> test, final PHPUnitElement parent, RemoteDebugger remoteDebugger) {
		if (test == null) {
			file = ""; //$NON-NLS-1$
			line = 0;
		} else
			parseFrame(test, remoteDebugger);
		if (parent != null)
			setParent(parent);
	}

	/**
	 * @param parent
	 */
	public void setParent(final PHPUnitElement parent) {
		this.parent = parent;
	}

	public String getFile() {
		return file;
	}

	public String getLocalFile() {
		return localFile;
	}

	public int getLine() {
		return line;
	}

	public PHPUnitElement getParent() {
		return parent;
	}

	public int getTestId() {
		if (testId == 0)
			setTestId();
		return testId;
	}

	public boolean isFiltered() {
		return isFiltered;
	}

	private void parseFrame(final Map<?, ?> test, RemoteDebugger remoteDebugger) {
		final String sFile = (String) test.get(PHPUnitMessageParser.PROPERTY_FILE);
		if (sFile != null) {
			file = sFile;
			if (remoteDebugger != null) {
				localFile = remoteDebugger.convertToLocalFilename(file, null, null);
			}
			if (localFile == null) {
				localFile = file;
			}
			line = Integer.parseInt((String) test.get(PHPUnitMessageParser.PROPERTY_LINE));
		}
		if ((String) test.get(PHPUnitMessageParser.PROPERTY_FILTERED) != null) {
			isFiltered = true;
		}
	}

	protected void setTestId() {
		String hash;
		if (this.parent != null && this instanceof PHPUnitTestCase && ((PHPUnitTestCase) this).dataProviderCase) {
			hash = this.parent.toString() + "@" + this.toString(); //$NON-NLS-1$
		} else {
			hash = this.toString();
		}
		testId = hash.hashCode();
	}
}
