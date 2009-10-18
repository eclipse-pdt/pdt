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
package org.eclipse.php.internal.debug.core.launching;

import org.eclipse.ui.console.IHyperlink;

public class HyperlinkEntry {
	private IHyperlink fLink;
	private String fMessage;
	private int fHyperLength;

	public HyperlinkEntry(IHyperlink link, String message, int hyperLength) {
		fLink = link;
		fMessage = message;
		fHyperLength = hyperLength;
	}

	public IHyperlink getLink() {
		return fLink;
	}

	public String getMessage() {
		return fMessage;
	}

	public int getHyperLength() {
		return fHyperLength;
	}

}
