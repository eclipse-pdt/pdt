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

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.typeinference.FakeMethod;

/**
 * This strategy completes global types (classes, interfaces, namespaces)
 * @author michael
 */
public class GlobalTypesStrategy extends GlobalElementStrategy {

	public GlobalTypesStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GlobalTypesStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		
		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		SourceRange replacementRange = getReplacementRange(abstractContext);

		IType[] types = getTypes(abstractContext);
		IElementFilter elementFilter = getElementFilter();
		String suffix = getSuffix(abstractContext);
		String nsSuffix = getNSSuffix(abstractContext);
		
		for (IType type : types) {
			try {
				int flags = type.getFlags();
				if (!PHPFlags.isInternal(flags) && (elementFilter == null || !elementFilter.filter(type))) {
					reporter.reportType(type, PHPFlags.isNamespace(flags) ? nsSuffix : suffix, replacementRange);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Runs the query to retrieve all global types
	 * @param context
	 * @return
	 * @throws BadLocationException
	 */
	protected IType[] getTypes(AbstractCompletionContext context) throws BadLocationException {
		int mask = 0;
		if (context.getCompletionRequestor().isContextInformationMode()) {
			mask |= CodeAssistUtils.EXACT_NAME;
		}
		String prefix = context.getPrefix();
		return CodeAssistUtils.getGlobalTypes(context.getSourceModule(), prefix, mask);
	}
	
	/**
	 * Adds the self function with the relevant data to the proposals array
	 * @param context
	 * @param reporter
	 * @throws BadLocationException 
	 */
	protected void addSelf(AbstractCompletionContext context, ICompletionReporter reporter) throws BadLocationException {
		
		String prefix = context.getPrefix();
		SourceRange replaceRange = getReplacementRange(context);

		if (CodeAssistUtils.startsWithIgnoreCase("self", prefix)) {
			if (!context.getCompletionRequestor().isContextInformationMode() || prefix.length() == 4) { // "self".length()
				
				String suffix = getSuffix(context);
				
				// get the class data for "self". In case of null, the self function will not be added
				IType selfClassData = CodeAssistUtils.getSelfClassData(context.getSourceModule(), context.getOffset());
				if (selfClassData != null) {
					try {
						IMethod ctor = null;
						for (IMethod method : selfClassData.getMethods()) {
							if (method.isConstructor()) {
								ctor = method;
								break;
							}
						}
						if (ctor != null) {
							FakeMethod ctorMethod = new FakeMethod((ModelElement) selfClassData, "self") {
								public boolean isConstructor() throws ModelException {
									return true;
								}
							};
							ctorMethod.setParameters(ctor.getParameters());
							reporter.reportMethod(ctorMethod, suffix, replaceRange);
						} else {
							reporter.reportMethod(new FakeMethod((ModelElement) selfClassData, "self"), "()", replaceRange);
						}
					} catch (ModelException e) {
						if (DLTKCore.DEBUG_COMPLETION) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public SourceRange getReplacementRange(ICompletionContext context) throws BadLocationException {
		SourceRange replacementRange = super.getReplacementRange(context);
		if (replacementRange.getLength() > 0) {
			return new SourceRange(replacementRange.getOffset(), replacementRange.getLength() - 1);
		}
		return replacementRange;
	}

	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return "\\".equals(nextWord) ? "" : "\\"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return "::".equals(nextWord) ? "" : "::"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
