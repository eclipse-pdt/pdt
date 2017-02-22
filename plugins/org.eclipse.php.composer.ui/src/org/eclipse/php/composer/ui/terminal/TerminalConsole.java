/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Michał Niewrzał (Rogue Wave Software Inc.) - initial implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.terminal;

import java.util.Map;

import org.eclipse.tm.internal.terminal.provisional.api.ITerminalConnector;
import org.eclipse.tm.terminal.connector.process.ProcessLauncherDelegate;
import org.eclipse.tm.terminal.view.core.interfaces.ITerminalService.Done;
import org.eclipse.ui.console.AbstractConsole;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.part.IPageBookViewPage;

public class TerminalConsole extends AbstractConsole {

	private final ITerminalConnector terminalConnector;
	private final int index;
	private TerminalConsolePage terminalConsolePage;
	private Done done;

	public TerminalConsole(String title, int index, Map<String, Object> properties, Done done) {
		super(title, null);
		this.terminalConnector = new ProcessLauncherDelegate().createTerminalConnector(properties);
		this.index = index;
		this.done = done;
	}

	public IPageBookViewPage createPage(IConsoleView view) {
		view.setFocus();
		this.terminalConsolePage = new TerminalConsolePage(this, done);
		return terminalConsolePage;
	}

	public ITerminalConnector getTerminalConnector() {
		return terminalConnector;
	}

	public TerminalConsolePage getTerminalConsolePage() {
		return terminalConsolePage;
	}

	public int getIndex() {
		return index;
	}
}
