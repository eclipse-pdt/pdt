/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.build;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.php.composer.core.ComposerPluginConstants;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.visitor.AutoloadVisitor;

public class ComposerBuildParticipant implements IBuildParticipant {
	@Override
	public void build(IBuildContext context) throws CoreException {
		if (ComposerPluginConstants.AUTOLOAD_NAMESPACES.equals(context.getSourceModule().getElementName()) == false) {
			return;
		}

		try {
			ISourceModule sourceModule = context.getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
			moduleDeclaration.traverse(new AutoloadVisitor(context.getSourceModule()));
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}
