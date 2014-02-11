/*******************************************************************************
 * Copyright (c) 2014 Dawid Pakuła
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial api and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.validation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildParticipant;

public class TodoTaskBuildParticipantFactory extends
		AbstractBuildParticipantType {

	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project)
			throws CoreException {
		return new PHPTodoTaskValidator();
	}
}
