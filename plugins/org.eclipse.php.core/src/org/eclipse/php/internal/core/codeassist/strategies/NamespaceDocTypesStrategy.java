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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.NamespacePHPDocVarStartContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;

/**
 * This strategy completes namespace classes and interfaces
 * 
 * @author michael
 */
public class NamespaceDocTypesStrategy extends AbstractCompletionStrategy {

	public NamespaceDocTypesStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public NamespaceDocTypesStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof NamespacePHPDocVarStartContext)) {
			return;
		}

		NamespacePHPDocVarStartContext concreteContext = (NamespacePHPDocVarStartContext) context;
		// now we compute type suffix in PHPCompletionProposalCollector
		String suffix = "";//$NON-NLS-1$ 
		SourceRange replaceRange = getReplacementRange(concreteContext);

		for (IType type : getTypes(concreteContext)) {
			reporter.reportType(type, suffix, replaceRange, getExtraInfo());
		}
	}

	public IType[] getTypes(NamespacePHPDocVarStartContext context)
			throws BadLocationException {
		String prefix = context.getPrefix();

		List<IType> result = new LinkedList<IType>();
		for (IType ns : context.getNamespaces()) {
			try {
				for (IType type : ns.getTypes()) {
					if (CodeAssistUtils.startsWithIgnoreCase(
							type.getElementName(), prefix)) {
						result.add(type);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		for (IType ns : context.getPossibleNamespaces()) {
			if (context.getNsPrefix() == null) {
				result.add(ns);
			} else {
				String fullName = ns.getElementName();
				String alias = getAlias(ns, context.getNsPrefix());
				if (alias == null) {
					result.add(ns);
				} else {
					result.add(new AliasType((ModelElement) ns, fullName, alias));
				}
			}
		}
		return (IType[]) result.toArray(new IType[result.size()]);
	}

	private String getAlias(IType ns, String currentNSName) {
		String result = ns.getElementName();
		currentNSName = currentNSName + NamespaceReference.NAMESPACE_SEPARATOR;
		if (result.startsWith(currentNSName)) {
			result = result.substring(currentNSName.length());
		} else {
			result = null;
		}
		return result;
	}

	public SourceRange getReplacementRange(ICompletionContext context)
			throws BadLocationException {
		SourceRange replacementRange = super.getReplacementRange(context);
		if (replacementRange.getLength() > 0) {
			return new SourceRange(replacementRange.getOffset(),
					replacementRange.getLength() - 1);
		}
		return replacementRange;
	}

	protected Object getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}