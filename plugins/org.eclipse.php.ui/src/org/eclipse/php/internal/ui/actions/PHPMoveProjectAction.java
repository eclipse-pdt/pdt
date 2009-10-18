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
package org.eclipse.php.internal.ui.actions;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.MoveProjectAction;

/**
 * Overrides the functionality of the move project We added the name of the
 * project to the path
 * 
 * @author Roy, 2007
 * 
 */
public class PHPMoveProjectAction extends MoveProjectAction {

	public PHPMoveProjectAction(Shell shell) {
		super(shell);
	}

	@Override
	protected Object[] queryDestinationParameters(IProject project) {

		// get the original result
		final Object[] result = super.queryDestinationParameters(project);

		// if the result is somehow null - return it as is
		if (result == null || result.length != 2) {
			return result;
		}

		// else we create a sub-folder to the original path
		assert result.length == 2 && result[1] instanceof String;

		// build an extended path with the appended project name
		StringBuilder builder = new StringBuilder(result[1].toString());
		builder.append(File.separatorChar);
		builder.append(result[0].toString());
		result[1] = builder.toString();
		return result;
	}

}