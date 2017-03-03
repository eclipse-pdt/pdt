/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModuleInfoCache.ISourceModuleInfo;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.visitor.ValidatorVisitor;

public class ValidatorBuildParticipantFactory extends AbstractBuildParticipantType implements IExecutableExtension {

	private String natureId = null;

	public IBuildParticipant createBuildParticipant(IScriptProject project) throws CoreException {
		if (natureId != null) {
			return new ParserBuildParticipant();
		}
		return null;
	}

	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		natureId = config.getAttribute("nature"); //$NON-NLS-1$
	}

	private static class ParserBuildParticipant implements IBuildParticipant {

		public void build(IBuildContext context) throws CoreException {
			if (!isValidatorEnabled(context)) {
				return;
			}
			try {
				ModelManager.getModelManager().getIndexManager().waitUntilReady();
				ModuleDeclaration moduleDeclaration = getModuleDeclaration(context);
				if (moduleDeclaration != null) {
					moduleDeclaration.traverse(new ValidatorVisitor(context));
				}
			} catch (Exception e) {
				PHPCorePlugin.log(e);
			}
		}

		private ModuleDeclaration getModuleDeclaration(IBuildContext context) {
			IModuleDeclaration moduleDeclaration = (ModuleDeclaration) context
					.get(IBuildContext.ATTR_MODULE_DECLARATION);
			if (moduleDeclaration == null) {
				ISourceModuleInfo cacheEntry = ModelManager.getModelManager().getSourceModuleInfoCache()
						.get(context.getSourceModule());
				moduleDeclaration = SourceParserUtil.getModuleFromCache(cacheEntry, context.getProblemReporter());
			}
			return (ModuleDeclaration) moduleDeclaration;
		}

		private boolean isValidatorEnabled(IBuildContext context) throws CoreException {
			if (Boolean.TRUE.equals(context.get(ParserBuildParticipantFactory.IN_LIBRARY_FOLDER))) {
				return false;
			}
			return true;
		}

	}

}
