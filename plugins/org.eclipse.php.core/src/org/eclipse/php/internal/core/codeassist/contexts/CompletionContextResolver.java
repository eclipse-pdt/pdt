/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.CompletionCompanion;
import org.eclipse.php.internal.core.codeassist.CompletionRequestorExtension;

/**
 * Default implementation of the {@link ICompletionContextResolver}
 * 
 * @author michael
 */
public class CompletionContextResolver implements ICompletionContextResolver {

	private static ICompletionContextResolver[] instances;

	/**
	 * Constructs default completion context resolver
	 */
	public CompletionContextResolver() {
	}

	/**
	 * Returns active completion context resolver. By default returns this class
	 * instance, but may be overridden using extension point.
	 * 
	 * @return array of active {@link ICompletionContextResolver}'s
	 */
	public static ICompletionContextResolver[] getActive() {
		if (instances == null) { // not synchronized since we don't care about
			// creating multiple instances of resolvers
			// in worst case

			List<ICompletionContextResolver> resolvers = new LinkedList<ICompletionContextResolver>();
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(
							"org.eclipse.php.core.completionContextResolvers"); //$NON-NLS-1$
			for (IConfigurationElement element : elements) {
				if (element.getName().equals("resolver")) { //$NON-NLS-1$
					try {
						resolvers.add((ICompletionContextResolver) element
								.createExecutableExtension("class")); //$NON-NLS-1$
					} catch (CoreException e) {
						PHPCorePlugin.log(e);
					}
				}
			}
			resolvers.add(new CompletionContextResolver()); // add default
			instances = (ICompletionContextResolver[]) resolvers
					.toArray(new ICompletionContextResolver[resolvers.size()]);
		}
		return instances;
	}

	public ICompletionContext[] createContexts() {
		return new ICompletionContext[] { new PHPDocTagStartContext(),
				new PHPDocThrowsStartContext(), new PHPDocParamTagContext(),
				new PHPDocReturnTagContext(), new PHPDocVarStartContext(),
				new ArrayKeyContext(), new CatchTypeContext(),
				new CatchVariableContext(),
				new ClassDeclarationKeywordContext(),
				new ClassExtendsContext(), new ClassImplementsContext(),
				new ClassInstantiationContext(), new ClassObjMemberContext(),
				new ClassStatementContext(), new ClassStaticMemberContext(),
				new FunctionParameterTypeContext(),
				new FunctionParameterValueContext(),
				new FunctionParameterVariableContext(),
				new MethodNameContext(), new GlobalStatementContext(),
				new GlobalMethodStatementContext(), new InstanceOfContext(),
				new InterfaceExtendsContext(),
				new InterfaceDeclarationKeywordContext(),
				new UseAliasContext(), new UseNameContext(),
				new NamespaceMemberContext(), new NamespaceNameContext(),
				new GotoStatementContext(), new NamespaceUseNameContext(),
				new NamespaceDeclContext(), new IncludeStatementContext(),
				new ExceptionClassInstantiationContext(),
				new TypeCastingContext(), new NamespacePHPDocVarStartContext(),
				new QuotesContext() };
	}

	public ICompletionContext[] resolve(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor, CompletionCompanion companion) {
		List<ICompletionContext> result = new LinkedList<ICompletionContext>();
		ICompletionContext[] contexts;
		if (requestor instanceof CompletionRequestorExtension) {
			contexts = ((CompletionRequestorExtension) requestor)
					.createContexts();
		} else {
			contexts = createContexts();
		}
		// find correct completion contexts according to known information:
		for (ICompletionContext context : contexts) {
			context.init(companion);

			try {
				if (context.isValid(sourceModule, offset, requestor)) {
					result.add(context);
				}
			} catch (Exception e) {
				PHPCorePlugin.log(e);
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
