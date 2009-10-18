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
package org.eclipse.php.internal.debug.core.zend.model;

public class StartLock {
	private boolean fRunStart = false;
	private boolean fStarted = false;

	StartLock() {
	}

	public boolean isRunStart() {
		return fRunStart;
	}

	public void setRunStart(boolean runStart) {
		fRunStart = runStart;
	}

	public boolean isStarted() {
		return fStarted;
	}

	public void setStarted(boolean started) {
		fStarted = started;
	}
}
