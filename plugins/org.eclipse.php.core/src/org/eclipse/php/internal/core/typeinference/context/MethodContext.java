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
package org.eclipse.php.internal.core.typeinference.context;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.IInstanceContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;

/**
 * This is a PHP method or function context.
 * @author michael
 */
public class MethodContext implements IContext, INamespaceContext, IArgumentsContext, IInstanceContext, ISourceModuleContext {

	private final ISourceModule sourceModule;
	private final ModuleDeclaration rootNode;
	private final MethodDeclaration methodNode;
	private final String[] argNames;
	private final IEvaluatedType[] argTypes;
	private IEvaluatedType instanceType;
	private String namespaceName;

	public MethodContext(IContext parent, ISourceModule sourceModule, ModuleDeclaration rootNode, MethodDeclaration methodNode, String[] argNames, IEvaluatedType[] argTypes) {
		this.sourceModule = sourceModule;
		this.rootNode = rootNode;
		this.methodNode = methodNode;
		this.argNames = argNames;
		this.argTypes = argTypes;

		if (parent instanceof IInstanceContext) {
			instanceType = ((IInstanceContext) parent).getInstanceType();
		}
		if (parent instanceof INamespaceContext) {
			namespaceName = ((INamespaceContext) parent).getNamespace();
		}
	}

	public IEvaluatedType getArgumentType(String name) {
		for (int i = 0; i < argNames.length; i++) {
			String argName = argNames[i];
			if (name.equals(argName)) {
				if (i < argTypes.length) {
					return argTypes[i];
				} else {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Returns {@link IEvaluatedType} for the declaring type or <code>null</code> if this is a function context 
	 */
	public IEvaluatedType getInstanceType() {
		return instanceType;
	}
	
	/**
	 * Returns namespace where the method was declared or <code>null</code> if this is a global scope method/function
	 */
	public String getNamespace() {
		return namespaceName;
	}

	/**
	 * Returns root AST node of the file where the method is declared
	 */
	public ModuleDeclaration getRootNode() {
		return rootNode;
	}

	/**
	 * Returns the file {@link ISourceModule} where the method is declared
	 */
	public ISourceModule getSourceModule() {
		return sourceModule;
	}

	/**
	 * Returns AST node of the method declaration
	 */
	public MethodDeclaration getMethodNode() {
		return methodNode;
	}

	public String getLangNature() {
		if (sourceModule != null) {
			IDLTKLanguageToolkit languageToolkit = DLTKLanguageManager.getLanguageToolkit(sourceModule);
			if (languageToolkit != null) {
				return languageToolkit.getNatureId();
			}
		}
		return null;
	}

}
