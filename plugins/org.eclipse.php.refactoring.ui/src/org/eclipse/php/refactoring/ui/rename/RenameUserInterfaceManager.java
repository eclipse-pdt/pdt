/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.rename;

import org.eclipse.php.refactoring.core.rename.*;
import org.eclipse.php.refactoring.ui.wizard.*;

/**
 * Holds the user interface manager for rename refactoring
 * 
 * @author Roy, 2007
 * @inspiredby JDT
 */
public class RenameUserInterfaceManager extends UserInterfaceManager {
	private static final UserInterfaceManager fgInstance = new RenameUserInterfaceManager();

	public static UserInterfaceManager getDefault() {
		return fgInstance;
	}

	private RenameUserInterfaceManager() {
		put(RenameGlobalVariableProcessor.class, RenameUserInterfaceStarter.class, RenameGlobalVariableWizard.class);
		put(RenameFunctionProcessor.class, RenameUserInterfaceStarter.class, RenameFunctionWizard.class);
		put(RenameLocalVariableProcessor.class, RenameUserInterfaceStarter.class, RenameLocalVariableWizard.class);
		put(RenameClassProcessor.class, RenameUserInterfaceStarter.class, RenameClassNameWizard.class);
		put(RenameTraitProcessor.class, RenameUserInterfaceStarter.class, RenameTraitNameWizard.class);
		put(RenameGlobalConstantProcessor.class, RenameUserInterfaceStarter.class, RenameDefinedWizard.class);
		put(RenameClassMemberProcessor.class, RenameUserInterfaceStarter.class, RenameClassPropertyWizard.class);
		put(RenameFileProcessor.class, RenameUserInterfaceStarter.class, RenameFileWizard.class);
		put(RenameFolderProcessor.class, RenameUserInterfaceStarter.class, RenameFileWizard.class);
	}
}