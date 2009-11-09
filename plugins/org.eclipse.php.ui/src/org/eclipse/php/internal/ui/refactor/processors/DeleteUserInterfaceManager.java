/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
		put(ScriptDeleteProcessor.class, UserInterfaceStarter.class,
				DeleteWizard.class);
	}
}
