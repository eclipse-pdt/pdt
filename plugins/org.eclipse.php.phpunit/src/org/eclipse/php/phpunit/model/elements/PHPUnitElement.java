/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.model.elements;

import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.phpunit.model.connection.MessageElement;

public abstract class PHPUnitElement {

	protected static final String SEPARATOR_LINE = ":"; //$NON-NLS-1$
	protected static final String SEPARATOR_MESSAGE = "!"; //$NON-NLS-1$
	protected static final String SEPARATOR_NAME = "$"; //$NON-NLS-1$

	protected String file = null;
	protected String localFile = null;
	protected boolean isFiltered = false;
	protected int line = 0;
	protected PHPUnitElement parent;
	protected int testId = 0;

	public PHPUnitElement(MessageElement test, final PHPUnitElement parent, RemoteDebugger remoteDebugger) {
		if (test != null) {
			parseFrame(test, remoteDebugger);
		}
		if (parent != null) {
			setParent(parent);
		}
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
		if (testId == 0) {
			setTestId();
		}
		return testId;
	}

	public boolean isFiltered() {
		return isFiltered;
	}

	private void parseFrame(MessageElement test, RemoteDebugger remoteDebugger) {
		final String sFile = test.getFile();
		if (sFile != null) {
			file = sFile;
			if (remoteDebugger != null) {
				localFile = remoteDebugger.convertToLocalFilename(file, null, null);
			}
			if (localFile == null) {
				localFile = file;
			}
			line = test.getLine();
		}
		if (test.getFiltered() != null) {
			isFiltered = true;
		}
	}

	protected void setTestId() {
		testId = toString().hashCode();
	}
}
