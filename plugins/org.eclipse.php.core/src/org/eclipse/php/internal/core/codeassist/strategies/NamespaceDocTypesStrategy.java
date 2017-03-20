/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.NamespacePHPDocVarStartContext;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes namespace classes and interfaces
 * 
 * @author michael
 */
public class NamespaceDocTypesStrategy extends AbstractCompletionStrategy {

	public NamespaceDocTypesStrategy(ICompletionContext context, IElementFilter elementFilter) {
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
		String nsSuffix = getNSSuffix(concreteContext);
		ISourceRange replaceRange = getReplacementRange(concreteContext);

		for (IType type : getTypes(concreteContext)) {
			try {
				int flags = type.getFlags();
				int extraInfo = getExtraInfo();
				boolean isNamespace = PHPFlags.isNamespace(flags);
				reporter.reportType(type, isNamespace ? nsSuffix : suffix, replaceRange,
						isNamespace ? extraInfo : extraInfo | ProposalExtraInfo.CLASS_IN_NAMESPACE);
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
	}

	public IType[] getTypes(NamespacePHPDocVarStartContext context) throws BadLocationException {
		String prefix = context.getPrefix();
		prefix = PHPModelUtils.extractElementName(prefix);

		List<IType> result = new LinkedList<IType>();
		for (IType ns : context.getNamespaces()) {
			try {
				for (IType type : ns.getTypes()) {
					if (StringUtils.startsWithIgnoreCase(type.getElementName(), prefix)) {
						result.add(type);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		String lastNamespace = null;
		for (IType ns : context.getPossibleNamespaces()) {
			if (context.getNsPrefix() == null) {
				if (!ns.getElementName().equals(lastNamespace)) {
					result.add(ns);
				}
				lastNamespace = ns.getElementName();
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

	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}