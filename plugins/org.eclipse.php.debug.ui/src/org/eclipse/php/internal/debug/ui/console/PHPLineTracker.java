/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.console;

import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.debug.ui.console.IConsoleLineTrackerExtension;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.debug.core.launching.HyperlinkEntry;
import org.eclipse.php.internal.debug.core.launching.PHPHyperLink;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.ui.console.IHyperlink;

/**
 * Processes task hyperlinks as lines are appended to the console
 */
public class PHPLineTracker implements IConsoleLineTrackerExtension {

	private IConsole fConsole;
	private PHPHyperLink fPHPHyperLink;

	/**
	 * Constructor for PHPLineTracker.
	 */
	public PHPLineTracker() {
		super();
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleLineTracker#init(org.eclipse.debug.ui.console.IConsole)
	 */
	public void init(IConsole console) {
		fConsole = console;
		fPHPHyperLink = ((PHPProcess) fConsole.getProcess()).getPHPHyperLink();
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleLineTracker#lineAppended(org.eclipse.jface.text.IRegion)
	 */
	public void lineAppended(IRegion line) {
		String message = "";
		try {
			message = fConsole.getDocument().get(line.getOffset(), line.getLength());
		} catch (BadLocationException e) {
			Logger.logException("PHPLineTracker error getting message", e);
			return;
		}
		HyperlinkEntry hyperLink = fPHPHyperLink.getHyperlinkEntry(message);
		if (hyperLink != null) {
			IHyperlink link = hyperLink.getLink();
			if (link != null) {
				fConsole.addLink(link, line.getOffset(), hyperLink.getHyperLength());
			}
		}
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleLineTracker#dispose()
	 */
	public void dispose() {
		fConsole = null;
		fPHPHyperLink = null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.console.IConsoleLineTrackerExtension#consoleClosed()
	 */
	public void consoleClosed() {
		fPHPHyperLink.dispose();
	}
}
