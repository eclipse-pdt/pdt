/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.console;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.internal.debug.core.launching.DebugConsoleMonitor;
import org.eclipse.php.internal.debug.core.launching.PHPHyperLink;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.ui.console.IHyperlink;

public class PHPConsoleListener implements IPHPConsoleEventListener {

	protected ILaunch fLaunch;
	protected DebugConsoleMonitor fConsoleMonitor;
	protected PHPHyperLink fPHPHyperLink;

	@Override
	public void init(ILaunch launch, DebugConsoleMonitor consoleMonitor, PHPHyperLink link) {
		fLaunch = launch;
		fConsoleMonitor = consoleMonitor;
		fPHPHyperLink = link;
	}

	@Override
	public void handleEvent(DebugError debugError) {
		IHyperlink link = createLink(debugError);
		String message = debugError.toString().trim();
		fPHPHyperLink.addLink(link, message, message.length() - debugError.getErrorTextLength());
		fConsoleMonitor.append(debugError.toString() + '\n');
	}

	protected IHyperlink createLink(DebugError debugError) {
		String fileName = debugError.getFullPathName();
		int lineNumber = debugError.getLineNumber();
		String url = fLaunch.getAttribute(IDebugParametersKeys.ORIGINAL_URL);
		return new PHPFileLink(fileName, lineNumber, url);
	}
}