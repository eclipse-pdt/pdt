/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.refactor.processors;

import org.eclipse.dltk.internal.ui.refactoring.UserInterfaceManager;
import org.eclipse.dltk.internal.ui.refactoring.UserInterfaceStarter;

public class DeleteUserInterfaceManager extends UserInterfaceManager {
	private static final UserInterfaceManager fgInstance = new DeleteUserInterfaceManager();

	public static UserInterfaceManager getDefault() {
		return fgInstance;
	}

	private DeleteUserInterfaceManager() {
		put(ScriptDeleteProcessor.class, UserInterfaceStarter.class, DeleteWizard.class);
	}
}
