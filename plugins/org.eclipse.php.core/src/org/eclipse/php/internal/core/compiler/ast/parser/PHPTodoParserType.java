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
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.validators.core.AbstractBuildParticipantType;
import org.eclipse.dltk.validators.core.IBuildParticipant;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPTodoParserType extends AbstractBuildParticipantType {

	private static final String ID = "org.eclipse.dltk.php.todo"; //$NON-NLS-1$
	private static final String NAME = "PHP TODO task parser"; //$NON-NLS-1$

	public PHPTodoParserType() {
		super(ID, NAME);
	}

	protected IBuildParticipant createBuildParticipant(IScriptProject project) {

		final PHPTodoTaskAstParser parser = new PHPTodoTaskAstParser(project);
		return parser;
	}

	public String getNature() {
		return PHPNature.ID;
	}

}
