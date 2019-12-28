/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.php.core.codeassist.CompletionCompanion;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.PHPCorePlugin;
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

			List<ICompletionContextResolver> resolvers = new LinkedList<>();
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor("org.eclipse.php.core.completionContextResolvers"); //$NON-NLS-1$
			for (IConfigurationElement element : elements) {
				if (element.getName().equals("resolver")) { //$NON-NLS-1$
					try {
						resolvers.add((ICompletionContextResolver) element.createExecutableExtension("class")); //$NON-NLS-1$
					} catch (CoreException e) {
						PHPCorePlugin.log(e);
					}
				}
			}
			resolvers.add(new CompletionContextResolver()); // add default
			instances = resolvers.toArray(new ICompletionContextResolver[resolvers.size()]);
		}
		return instances;
	}

	@Override
	public ICompletionContext[] createContexts() {
		return new ICompletionContext[] { new PHPDocTagStartContext(), new PHPDocThrowsStartContext(),
				new PHPDocParamTagContext(), new PHPDocVarTagContext(), new PHPVarCommentContext(),
				new PHPDocReturnTagContext(), new PHPDocMagicTagsContext(), new PHPDocTagInnerContext(),
				new ArrayKeyContext(), new CatchTypeContext(), new CatchVariableContext(),
				new ClassDeclarationKeywordContext(), new ClassExtendsContext(), new ClassImplementsContext(),
				new ClassInstantiationContext(), new ClassObjMemberContext(), new TypeStatementContext(),
				new ClassStaticMemberContext(), new FunctionParameterTypeContext(), new FunctionReturnTypeContext(),
				new FunctionParameterValueContext(), new FunctionParameterVariableContext(), new MethodNameContext(),
				new GlobalStatementContext(), new GlobalMethodStatementContext(), new InstanceOfContext(),
				new InterfaceExtendsContext(), new InterfaceDeclarationKeywordContext(), new UseAliasContext(),
				new UseNameContext(), new UseConstNameContext(), new UseFunctionNameContext(),
				new GotoStatementContext(), new NamespaceDeclContext(), new IncludeStatementContext(),
				new ExceptionClassInstantiationContext(), new TypeCastingContext(), new QuotesContext(),
				new TraitConflictContext() };
	}

	@Override
	public ICompletionContext[] resolve(ISourceModule sourceModule, int offset, CompletionRequestor requestor,
			CompletionCompanion companion) {
		List<ICompletionContext> result = new LinkedList<>();
		ICompletionContext[] contexts;
		if (requestor instanceof CompletionRequestorExtension) {
			contexts = ((CompletionRequestorExtension) requestor).createContexts();
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
			List<ICompletionContext> filteredResult = new LinkedList<>();
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
