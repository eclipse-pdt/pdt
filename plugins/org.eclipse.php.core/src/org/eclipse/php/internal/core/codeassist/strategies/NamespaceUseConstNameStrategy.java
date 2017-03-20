/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
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
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.NamespaceUseConstNameContext;

public class NamespaceUseConstNameStrategy extends AbstractCompletionStrategy {

	public NamespaceUseConstNameStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof NamespaceUseConstNameContext)) {
			return;
		}

		NamespaceUseConstNameContext concreteContext = (NamespaceUseConstNameContext) context;
		String suffix = "";//$NON-NLS-1$
		ISourceRange replaceRange = getReplacementRange(concreteContext);

		for (IField field : getFields(concreteContext)) {
			reporter.reportField(field, suffix, replaceRange, false, 0, getExtraInfo());
		}
	}

	public IField[] getFields(NamespaceUseConstNameContext context) throws BadLocationException {
		String prefix = context.getPrefix();

		List<IField> result = new LinkedList<IField>();
		for (IType ns : context.getNamespaces()) {
			try {
				for (IField field : ns.getFields()) {
					if (StringUtils.startsWithIgnoreCase(field.getElementName(), prefix)) {
						result.add(field);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		return result.toArray(new IField[result.size()]);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "::".equals(nextWord) ? "" : "::"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	protected int getExtraInfo() {
		return ProposalExtraInfo.DEFAULT | ProposalExtraInfo.NO_INSERT_NAMESPACE | ProposalExtraInfo.NO_INSERT_USE;
	}
}
