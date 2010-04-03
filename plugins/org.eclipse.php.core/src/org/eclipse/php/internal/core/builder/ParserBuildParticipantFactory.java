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
package org.eclipse.php.internal.core.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.compiler.problem.ProblemCollector;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.ISourceModuleInfoCache.ISourceModuleInfo;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.core.builder.IScriptBuilder;
import org.eclipse.dltk.internal.core.ModelManager;

public class ParserBuildParticipantFactory extends AbstractBuildParticipantType
		implements IExecutableExtension {

	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project)
			throws CoreException {
		if (natureId != null) {
			final ISourceParser parser = DLTKLanguageManager
					.getSourceParser(natureId);
			if (parser != null) {
				return new ParserBuildParticipant(parser);
			}
		}
		return null;
	}

	private String natureId = null;

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		natureId = config.getAttribute("nature"); //$NON-NLS-1$
	}

	private static class ParserBuildParticipant implements IBuildParticipant {

		private final ISourceParser parser;

		public ParserBuildParticipant(ISourceParser parser) {
			this.parser = parser;
		}

		public void build(IBuildContext context) throws CoreException {
			IModuleDeclaration moduleDeclaration = (ModuleDeclaration) context
					.get(IBuildContext.ATTR_MODULE_DECLARATION);
			if (moduleDeclaration != null) {
				// do nothing if already have AST - optimization for reconcile
				return;
			}
			// get cache entry
			final ISourceModuleInfo cacheEntry = ModelManager.getModelManager()
					.getSourceModuleInfoCache().get(context.getSourceModule());
			// if full build,do not use cache,or the error marker will not be
			// refreshed
			if (context.getBuildType() != IScriptBuilder.FULL_BUILD) {

				// check if there is cached AST
				moduleDeclaration = SourceParserUtil.getModuleFromCache(
						cacheEntry, context.getProblemReporter());
				if (moduleDeclaration != null) {
					// use AST from cache
					context.set(IBuildContext.ATTR_MODULE_DECLARATION,
							moduleDeclaration);
					return;
				}
			}

			// create problem collector
			final ProblemCollector problemCollector = new ProblemCollector();
			// parse
			moduleDeclaration = parser.parse((IModuleSource) context
					.getSourceModule(), problemCollector);
			// put result to the cache
			SourceParserUtil.putModuleToCache(cacheEntry, moduleDeclaration,
					problemCollector);
			// report errors to the build context
			problemCollector.copyTo(context.getProblemReporter());
		}
	}

}
