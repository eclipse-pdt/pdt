/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;

public class ASTUtils {
	public static Program createProgramFromSource(ISourceModule source) throws Exception {
		IResource resource = source.getResource();
		IProject project = null;
		if (resource instanceof IFile) {
			project = ((IFile) resource).getProject();
		}
		ASTParser newParser;
		if (project != null) {
			newParser = ASTParser.newParser(project, source);
		} else {
			newParser = ASTParser.newParser(ProjectOptions.getDefaultPHPVersion(),
					ProjectOptions.getDefaultIsSupportingASPTags(), ProjectOptions.getDefaultUseShortTags(), source);
		}
		return newParser.createAST(null);
	}
}
