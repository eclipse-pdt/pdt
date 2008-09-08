/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.task.ITodoTaskPreferences;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.validators.core.AbstractBuildParticipantType;
import org.eclipse.dltk.validators.core.IBuildParticipant;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPTodoParserType extends AbstractBuildParticipantType {

	private static final String ID = "org.eclipse.dltk.php.todo"; //$NON-NLS-1$
	private static final String NAME = "PHP TODO task parser"; //$NON-NLS-1$

	public PHPTodoParserType() {
		super(ID, NAME);
	}

	protected IBuildParticipant createBuildParticipant(IScriptProject project) {
		final ITodoTaskPreferences prefs = new PHPTodoTaskPreferences(PHPCorePlugin.getDefault().getPluginPreferences());
		if (prefs.isEnabled()) {
			final PHPTodoTaskAstParser parser = new PHPTodoTaskAstParser(prefs);
			if (parser.isValid()) {
				return parser;
			}
		}
		return null;
	}

	public String getNature() {
		return PHPNature.ID;
	}

}
