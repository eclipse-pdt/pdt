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
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.contexts.*;

/**
 * Default implementation of the {@link ICompletionStrategyFactory}
 * @author michael
 */
public class CompletionStrategyFactory implements ICompletionStrategyFactory {

	private static ICompletionStrategyFactory instance;

	/**
	 * Returns active completion strategy factory. By default returns this class instance,
	 * but may be overriden using extension point.
	 * 
	 * @return {@link ICompletionContextResolver}
	 */
	public static ICompletionStrategyFactory getActive() {
		if (instance == null) { // not synchronized since we don't care about creating multiple instances of factories in worst case

			IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.php.core.completionStrategyFactories");
			for (IConfigurationElement element : elements) {
				if (element.getName().equals("factory")) {
					try {
						instance = (ICompletionStrategyFactory) element.createExecutableExtension("class");
					} catch (CoreException e) {
						PHPCorePlugin.log(e);
					}
				}
			}
			if (instance == null) {
				instance = new CompletionStrategyFactory();
			}
		}
		return instance;
	}

	public ICompletionStrategy[] create(ICompletionContext context) {

		Class<? extends ICompletionContext> contextClass = context.getClass();

		if (contextClass == PHPDocTagStartContext.class) {
			return new ICompletionStrategy[] { new PHPDocTagStrategy() };
		}
		if (contextClass == PHPDocParamTagContext.class) {
			return new ICompletionStrategy[] { new PHPDocParamVariableStrategy() };
		}
		if (contextClass == PHPDocReturnTagContext.class) {
			return new ICompletionStrategy[] { new PHPDocReturnTypeStrategy() };
		}
		if (contextClass == ArrayKeyContext.class) {
			// If array has quotes or double-quotes around the key - show only builtin keys:
			if (((ArrayKeyContext) context).hasQuotes()) {
				return new ICompletionStrategy[] { new BuiltinArrayKeysStrategy() };
			}
			// Otherwise - show all global elements also:
			// Example: $array[foo()], $array[$otherVar]
			return new ICompletionStrategy[] { new BuiltinArrayKeysStrategy(), new GlobalElementsCompositeStrategy(false) };
		}
		if (contextClass == FunctionParameterTypeContext.class) {
			return new ICompletionStrategy[] { new GlobalClassesStrategy() };
		}
		if (contextClass == FunctionParameterValueContext.class) {
			return new ICompletionStrategy[] { new GlobalConstantsStrategy() };
		}
		if (contextClass == MethodNameContext.class) {
			return new ICompletionStrategy[] { new MethodNameStrategy() };
		}
		if (contextClass == VariableContext.class) {
			return new ICompletionStrategy[] { new GlobalVariablesStrategy() };
		}
		if (contextClass == ClassStatementContext.class) {
			return new ICompletionStrategy[] { new ClassKeywordsStrategy() };
		}
		if (contextClass == GlobalStatementContext.class) {
			return new ICompletionStrategy[] { new GlobalElementsCompositeStrategy(true) };
		}
		if (contextClass == CatchTypeContext.class) {
			return new ICompletionStrategy[] { new GlobalClassesStrategy() };
		}

		return null;
	}

}
