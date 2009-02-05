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
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.PHPCorePlugin;

/**
 * Default implementation of the {@link ICompletionContextResolver}
 * @author michael
 */
public class CompletionContextResolver implements ICompletionContextResolver {

	private static ICompletionContextResolver instance;
	private Collection<ICompletionContext> contexts;

	/**
	 * Constructs default completion context resolver
	 */
	public CompletionContextResolver() {
		contexts = new LinkedList<ICompletionContext>();
		initCompletionContexts(contexts);
	}

	/**
	 * Returns active completion context resolver. By default returns this class instance,
	 * but may be overriden using extension point.
	 * 
	 * @return {@link ICompletionContextResolver}
	 */
	public static ICompletionContextResolver getActive() {
		if (instance == null) { // not synchronized since we don't care about creating multiple instances of resolvers in worst case

			IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.php.core.completionContextResolvers");
			for (IConfigurationElement element : elements) {
				if (element.getName().equals("resolver")) {
					try {
						instance = (ICompletionContextResolver) element.createExecutableExtension("class");
					} catch (CoreException e) {
						PHPCorePlugin.log(e);
					}
				}
			}
			if (instance == null) {
				instance = new CompletionContextResolver();
			}
		}
		return instance;
	}

	/**
	 * Initializes given collection with known completion contexts
	 * @param contexts
	 */
	protected void initCompletionContexts(Collection<ICompletionContext> contexts) {
		contexts.addAll(Arrays.asList(new ICompletionContext[] {
			new PHPDocTagStartContext(),
			new PHPDocParamTagContext(),
			new PHPDocReturnTagContext(),
			new ArrayKeyContext(),
			new CatchTypeContext(),
			new CatchVariableContext(),
			new ClassExtendsContext(),
			new ClassImplementsContext(),
			new ClassInstantiationContext(),
			new ClassObjMemberContext(),
			new ClassStatementContext(),
			new ClassStaticMemberContext(),
			new FunctionParameterTypeContext(),
			new FunctionParameterValueContext(),
			new FunctionParameterVariableContext(),
			new MethodNameContext(),
			new GlobalStatementContext(),
			new InstanceOfContext(),
			new InterfaceExtendsContext(),
			new PHPDocTagStartContext(),
			new UseAliasContext(),
			new UseNameContext(),
			new VariableContext(),
		}));
	}

	public ICompletionContext[] resolve(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		List<ICompletionContext> result = new LinkedList<ICompletionContext>();
		// find correct completion contexts according to known information:
		for (ICompletionContext context : contexts) {
			try {
				if (context.isValid(sourceModule, offset, requestor)) {
					result.add(context);
				}
			} catch (Exception e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace(); // allow processing of other contexts
				}
			}
		}
		
		// remove exclusive contexts:
		if (result.size() > 1) {
			List<ICompletionContext> filteredResult = new LinkedList<ICompletionContext>();
			for (ICompletionContext context : result) {
				if (!context.isExclusive()) {
					filteredResult.add(context);
				}
			}
			result = filteredResult;
		}
		
		return result.toArray(new ICompletionContext[result.size()]);
	}
}
