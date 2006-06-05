/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.debug.ui.console;
 
import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.debug.ui.console.IConsoleLineTrackerExtension;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.debug.core.launching.PHPHyperLink;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.debug.ui.console.FileLink;
import org.eclipse.php.debug.core.launching.PHPProcess;
import org.eclipse.php.debug.core.launching.HyperlinkEntry;

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
        fPHPHyperLink = ((PHPProcess)fConsole.getProcess()).getPHPHyperLink();
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
	        FileLink link = (FileLink)hyperLink.getLink();
	        if (link != null){
	            fConsole.addLink((IHyperlink)link ,line.getOffset(), hyperLink.getHyperLength());
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
