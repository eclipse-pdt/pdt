/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.mixin;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemFactory;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.mixin.IMixinParser;
import org.eclipse.dltk.core.mixin.IMixinRequestor;
import org.eclipse.php.core.PHPMixinBuildVisitorExtension;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPMixinParser implements IMixinParser {

	public static final String CLASS_SUFFIX = "%"; //$NON-NLS-1$
	public static final String INCLUDE_SUFFIX = "#"; //$NON-NLS-1$

	private IMixinRequestor requestor;
	private IConfigurationElement[] extensions;

	public PHPMixinParser() {
		// Load parser extensions
		extensions = Platform.getExtensionRegistry().getConfigurationElementsFor(PHPCorePlugin.ID, "phpMixinBuildVisitors");
	}

	public void parserSourceModule(boolean signature, ISourceModule module) {

		IProblemReporter problemReporter = null;
		try {
			IResource resource = module.getCorrespondingResource();
			if (resource != null) {
				IProblemFactory problemFactory = DLTKLanguageManager.getProblemFactory(PHPNature.ID);
				problemReporter = problemFactory.createReporter(resource);
			}
		} catch (CoreException e) {
		}
		
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(module, problemReporter);
		PHPMixinBuildVisitor visitor = new PHPMixinBuildVisitor(moduleDeclaration, module, signature, requestor);
		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		for (IConfigurationElement extension : extensions) {
			try {
				PHPMixinBuildVisitorExtension visitorExtension = (PHPMixinBuildVisitorExtension) extension.createExecutableExtension("class");
				visitorExtension.setSourceModule(module);
				visitorExtension.setModuleAvailable(signature);
				visitorExtension.setRequestor(requestor);

				moduleDeclaration.traverse(visitorExtension);
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	public void setRequirestor(IMixinRequestor requestor) {
		this.requestor = requestor;
	}

}
