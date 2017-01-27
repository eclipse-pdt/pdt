/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.handler;

import org.eclipse.php.composer.core.launch.execution.ExecutionResponseAdapter;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.ui.console.*;

public class ConsoleResponseHandler extends ExecutionResponseAdapter {

	public ConsoleResponseHandler() {
	}

	private MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];

		// no console found, so create a new one
		MessageConsole console = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { console });
		return console;
	}

	@Override
	public void executionAboutToStart() {
		MessageConsole console = findConsole("Composer"); //$NON-NLS-1$
		console.clearConsole();
	}

	@Override
	public void executionMessage(String message) {
		MessageConsole console = findConsole("Composer"); //$NON-NLS-1$
		MessageConsoleStream out = console.newMessageStream();
		out.println(message);
	}

	public void executionFailed(String response, Exception e) {
		Logger.log(Logger.ERROR, response);
		Logger.logException(e);
	}

}
