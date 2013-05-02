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
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.*;
import org.eclipse.php.internal.core.codeassist.templates.contexts.GlobalMethodStatementContextForTemplate;
import org.eclipse.php.internal.core.codeassist.templates.contexts.GlobalStatementContextForTemplate;
import org.eclipse.php.internal.core.codeassist.templates.strategies.LocalMethodVariablesStrategyForTemplate;

/**
 * Default implementation of the {@link ICompletionStrategyFactory}
 * 
 * @author michael
 */
public class CompletionStrategyFactory implements ICompletionStrategyFactory {

	private static ICompletionStrategyFactory[] instances;

	/**
	 * Returns active completion strategy factory. By default returns this class
	 * instance, but may be overriden using extension point.
	 * 
	 * @return array of active {@link ICompletionContextResolver}'s
	 */
	public static ICompletionStrategyFactory[] getActive() {
		if (instances == null) { // not synchronized since we don't care about
			// creating multiple instances of factories
			// in worst case

			List<ICompletionStrategyFactory> factories = new LinkedList<ICompletionStrategyFactory>();
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(
							"org.eclipse.php.core.completionStrategyFactories"); //$NON-NLS-1$
			for (IConfigurationElement element : elements) {
				if (element.getName().equals("factory")) { //$NON-NLS-1$
					try {
						factories.add((ICompletionStrategyFactory) element
								.createExecutableExtension("class")); //$NON-NLS-1$
					} catch (CoreException e) {
						PHPCorePlugin.log(e);
					}
				}
			}
			factories.add(new CompletionStrategyFactory());
			instances = (ICompletionStrategyFactory[]) factories
					.toArray(new ICompletionStrategyFactory[factories.size()]);
		}
		return instances;
	}

	public ICompletionStrategy[] create(ICompletionContext[] contexts) {
		// don't allow creation of strategies of the same class:
		Set<Class<? super ICompletionStrategy>> processed = new HashSet<Class<? super ICompletionStrategy>>();

		List<ICompletionStrategy> result = new LinkedList<ICompletionStrategy>();

		for (ICompletionContext context : contexts) {
			ICompletionStrategy[] strategies = createStrategies(context,
					contexts);

			for (ICompletionStrategy strategy : strategies) {
				if (!processed.contains(strategy.getClass())) {
					result.add(strategy);
				}
			}
		}
		return (ICompletionStrategy[]) result
				.toArray(new ICompletionStrategy[result.size()]);
	}

	protected ICompletionStrategy[] createStrategies(
			ICompletionContext context, ICompletionContext[] allContexts) {

		Class<? extends ICompletionContext> contextClass = context.getClass();

		if (contextClass == PHPDocTagStartContext.class) {
			return new ICompletionStrategy[] { new PHPDocTagStrategy(context) };
		}
		if (contextClass == PHPDocVarStartContext.class) {
			return new ICompletionStrategy[] { new GlobalClassesStrategy(
					context) {
				@Override
				protected int getExtraInfo() {
					return ProposalExtraInfo.TYPE_ONLY;
				}
			} };
		}
		if (contextClass == PHPDocThrowsStartContext.class) {
			return new ICompletionStrategy[] { new ExceptionClassStrategy(
					context) };
		}
		if (contextClass == PHPDocParamTagContext.class) {
			return new ICompletionStrategy[] { new PHPDocParamVariableStrategy(
					context) };
		}
		if (contextClass == PHPDocReturnTagContext.class
				|| contextClass == PHPDocPropertyTagContext.class) {
			return new ICompletionStrategy[] { new PHPDocReturnTypeStrategy(
					context) };
		}
		if (contextClass == ArrayKeyContext.class) {
			// If array has quotes or double-quotes around the key - show only
			// builtin keys:
			if (((ArrayKeyContext) context).hasQuotes()) {
				return new ICompletionStrategy[] {
						new BuiltinArrayKeysStrategy(context),
						new ArrayStringKeysStrategy(context) };
			}
			// Otherwise - show all global elements also:
			// Example: $array[foo()], $array[$otherVar]
			return new ICompletionStrategy[] {
					new BuiltinArrayKeysStrategy(context),
					new GlobalElementsCompositeStrategy(context, false),
					new LocalMethodVariablesStrategyForArray(context),
					new BuiltinArrayKeysStrategy(context),
					new ArrayStringKeysStrategy(context) };
		}
		if (contextClass == FunctionParameterTypeContext.class) {
			return new ICompletionStrategy[] {
					new FunctionParameterTypeStrategy(context),
					new FunctionParameterKeywordTypeStrategy(context) };
		}
		if (contextClass == FunctionParameterValueContext.class) {
			return new ICompletionStrategy[] {
					new GlobalConstantsStrategy(context),
					new MethodParameterKeywordStrategy(context) };
		}
		if (contextClass == MethodNameContext.class) {
			return new ICompletionStrategy[] { new MethodNameStrategy(context) };
		}
		if (contextClass == ClassStatementContext.class) {
			if (((AbstractCompletionContext) context).isInUseTraitStatement()) {
				int type = ((AbstractCompletionContext) context)
						.getUseTraitStatementContext();
				if (type == AbstractCompletionContext.TRAIT_NAME) {
					return new ICompletionStrategy[] { new InUseTraitStrategy(
							context) };
				} else if (type == AbstractCompletionContext.TRAIT_KEYWORD) {
					return new ICompletionStrategy[] { new InUseTraitKeywordStrategy(
							context) };
				} else {
					return new ICompletionStrategy[] {};
				}
			} else {
				return new ICompletionStrategy[] {
						new ClassKeywordsStrategy(context),
						new GlobalConstantsStrategy(context),
				// new GlobalTypesStrategy(context)
				};
			}
		}
		if (contextClass == GlobalStatementContext.class) {
			return new ICompletionStrategy[] { new GlobalElementsCompositeStrategy(
					context, true) };
		}
		if (contextClass == GlobalMethodStatementContext.class) {
			return new ICompletionStrategy[] { new LocalMethodElementsCompositeStrategy(
					context) };
		}
		if (contextClass == CatchTypeContext.class) {
			return new ICompletionStrategy[] { new CatchTypeStrategy(context) };
		}
		if (contextClass == ClassInstantiationContext.class) {
			return new ICompletionStrategy[] {
					new ClassInstantiationStrategy(context),
					new GlobalVariablesStrategy(context, false) };
		}
		if (contextClass == InstanceOfContext.class) {
			return new ICompletionStrategy[] { new InstanceOfStrategy(context) };
		}
		if (contextClass == ExceptionClassInstantiationContext.class) {
			return new ICompletionStrategy[] { new ExceptionClassInstantiationStrategy(
					context) };
		}
		// if (contextClass ==
		// ClassStaticMemberContext.class&&((ClassStaticMemberContext)context).isInUseTraitStatement())
		// {
		// return new ICompletionStrategy[] {
		// new ClassFieldsStrategy(context),
		// new ClassMethodsStrategy(context) };
		// }
		if (contextClass == ClassStaticMemberContext.class
				|| contextClass == ClassObjMemberContext.class) {
			if (((AbstractCompletionContext) context).isInUseTraitStatement()) {
				int type = ((AbstractCompletionContext) context)
						.getUseTraitStatementContext();
				if (type == AbstractCompletionContext.TRAIT_NAME) {
					return new ICompletionStrategy[] { new InUseTraitStrategy(
							context) };
				} else if (type == AbstractCompletionContext.TRAIT_KEYWORD) {
					return new ICompletionStrategy[] { new InUseTraitKeywordStrategy(
							context) };
				} else {
					return new ICompletionStrategy[] {
							new ClassFieldsStrategy(context),
							new ClassMethodsStrategy(context) };
				}
			} else {
				return new ICompletionStrategy[] {
						new ClassFieldsStrategy(context),
						new ClassMethodsStrategy(context) };
			}
		}
		if (contextClass == ClassDeclarationKeywordContext.class) {
			return new ICompletionStrategy[] { new ClassDeclarationKeywordsStrategy(
					context) };
		}
		if (contextClass == InterfaceDeclarationKeywordContext.class) {
			return new ICompletionStrategy[] { new InterfaceDeclarationKeywordsStrategy(
					context) };
		}
		if (contextClass == ClassExtendsContext.class) {
			return new ICompletionStrategy[] {
					new NonFinalClassesStrategy(context),
					new NamespaceNonFinalClassesStrategy(context) };
		}
		if (contextClass == ClassImplementsContext.class
				|| contextClass == InterfaceExtendsContext.class) {
			return new ICompletionStrategy[] { new GlobalInterfacesStrategy(
					context) };
		}
		if (contextClass == NamespaceMemberContext.class) {
			return new ICompletionStrategy[] { new NamespaceElementsCompositeStrategy(
					context, allContexts,
					((NamespaceMemberContext) context).isGlobal()) };
		}
		if (contextClass == NamespaceNameContext.class
				|| contextClass == NamespaceDeclContext.class) {
			return new ICompletionStrategy[] { new NamespacesStrategy(context) };
		}
		if (contextClass == QuotesContext.class) {
			return new ICompletionStrategy[] { new NamespacesStrategy(context,
					true) };
		}
		if (contextClass == GotoStatementContext.class) {
			return new ICompletionStrategy[] { new GotoStatementStrategy(
					context) };
		}
		if (contextClass == TypeCastingContext.class) {
			return new ICompletionStrategy[] { new TypeCastingStrategy(context) };
		}
		if (contextClass == UseNameContext.class) {
			UseNameContext useNameContext = (UseNameContext) context;
			if (useNameContext.isUseTrait()) {
				return new ICompletionStrategy[] { new UseTraitNameStrategy(
						context) };
			} else {
				return new ICompletionStrategy[] { new UseNameStrategy(context) };
			}

		}
		if (contextClass == NamespaceUseNameContext.class) {
			NamespaceUseNameContext useNameContext = (NamespaceUseNameContext) context;
			if (useNameContext.isUseTrait()) {
				return new ICompletionStrategy[] { new NamespaceUseTraitNameStrategy(
						context) };
			} else {
				return new ICompletionStrategy[] { new NamespaceUseNameStrategy(
						context) };
			}
		}
		if (contextClass == IncludeStatementContext.class) {
			return new ICompletionStrategy[] { new IncludeStatementStrategy(
					context) };
		}
		// Context for template
		if (contextClass == GlobalStatementContextForTemplate.class) {
			return new ICompletionStrategy[] { new GlobalVariablesStrategy(
					context, true) };
		}
		if (contextClass == GlobalMethodStatementContextForTemplate.class) {
			return new ICompletionStrategy[] { new LocalMethodVariablesStrategyForTemplate(
					context) };
		}
		if (contextClass == NamespacePHPDocVarStartContext.class) {
			return new ICompletionStrategy[] { new NamespaceDocTypesCompositeStrategy(
					context) };
		}
		return new ICompletionStrategy[] {};
	}

}
