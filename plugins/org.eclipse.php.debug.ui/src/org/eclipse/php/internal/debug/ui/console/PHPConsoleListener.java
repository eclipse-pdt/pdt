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
package org.eclipse.php.internal.debug.ui.console;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.internal.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.internal.debug.core.launching.DebugConsoleMonitor;
import org.eclipse.php.internal.debug.core.launching.PHPHyperLink;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.ui.console.IHyperlink;

public class PHPConsoleListener implements IPHPConsoleEventListener {

	protected ILaunch fLaunch;
	protected DebugConsoleMonitor fConsoleMonitor;
	protected PHPHyperLink fPHPHyperLink;

	public void init(ILaunch launch, DebugConsoleMonitor consoleMonitor,
			PHPHyperLink link) {
		fLaunch = launch;
		fConsoleMonitor = consoleMonitor;
		fPHPHyperLink = link;
	}

	public void handleEvent(DebugError debugError) {
		IHyperlink link = createLink(debugError);
		String message = debugError.toString().trim();
		fPHPHyperLink.addLink(link, message, message.length()
				- debugError.getErrorTextLength());
		fConsoleMonitor.append(debugError.toString() + '\n');
	}

	protected IHyperlink createLink(DebugError debugError) {
		String fileName = debugError.getFullPathName();
		int lineNumber = debugError.getLineNumber();
		return new PHPFileLink(fileName, lineNumber);
	}
}