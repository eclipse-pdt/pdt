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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.FakeMethod;

/**
 * This strategy completes global types (classes, interfaces, namespaces)
 * 
 * @author michael
 */
public class GlobalTypesStrategy extends GlobalElementStrategy {

	protected final int trueFlag;
	protected final int falseFlag;
	protected static final IType[] EMPTY = {};

	public GlobalTypesStrategy(ICompletionContext context, int trueFlag,
			int falseFlag) {
		super(context, null);
		this.trueFlag = trueFlag;
		this.falseFlag = falseFlag;
	}

	public GlobalTypesStrategy(ICompletionContext context) {
		this(context, 0, 0);
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

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		SourceRange replacementRange = getReplacementRange(abstractContext);

		IType[] types = getTypes(abstractContext);
		// now we compute type suffix in PHPCompletionProposalCollector
		String suffix = "";//$NON-NLS-1$ 
		String nsSuffix = getNSSuffix(abstractContext);

		for (IType type : types) {
			try {
				int flags = type.getFlags();
				reporter.reportType(type,
						PHPFlags.isNamespace(flags) ? nsSuffix : suffix,
						replacementRange, getExtraInfo());
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
	}

	/**
	 * Runs the query to retrieve all global types
	 * 
	 * @param context
	 * @return
	 * @throws BadLocationException
	 */
	protected IType[] getTypes(AbstractCompletionContext context)
			throws BadLocationException {

		String prefix = context.getPrefix();
		if (prefix.startsWith("$")) {
			return EMPTY;
		}

		IDLTKSearchScope scope = createSearchScope();
		if (context.getCompletionRequestor().isContextInformationMode()) {
			return PhpModelAccess.getDefault().findTypes(prefix,
					MatchRule.EXACT, trueFlag, falseFlag, scope, null);
		}

		List<IType> result = new LinkedList<IType>();
		if (prefix.length() > 1 && prefix.toUpperCase().equals(prefix)) {
			// Search by camel-case
			IType[] types = PhpModelAccess.getDefault().findTypes(prefix,
					MatchRule.CAMEL_CASE, trueFlag, falseFlag, scope, null);
			result.addAll(Arrays.asList(types));
		}
		IType[] types = PhpModelAccess.getDefault().findTypes(prefix,
				MatchRule.PREFIX, trueFlag, falseFlag, scope, null);
		result.addAll(Arrays.asList(types));

		return (IType[]) result.toArray(new IType[result.size()]);
	}

	/**
	 * Adds the self function with the relevant data to the proposals array
	 * 
	 * @param context
	 * @param reporter
	 * @throws BadLocationException
	 */
	protected void addSelf(AbstractCompletionContext context,
			ICompletionReporter reporter) throws BadLocationException {

		String prefix = context.getPrefix();
		SourceRange replaceRange = getReplacementRange(context);

		if (CodeAssistUtils.startsWithIgnoreCase("self", prefix)) {
			if (!context.getCompletionRequestor().isContextInformationMode()
					|| prefix.length() == 4) { // "self".length()

				String suffix = getSuffix(context);

				// get the class data for "self". In case of null, the self
				// function will not be added
				IType selfClassData = CodeAssistUtils.getSelfClassData(context
						.getSourceModule(), context.getOffset());
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
							ISourceRange sourceRange = selfClassData
									.getSourceRange();
							FakeMethod ctorMethod = new FakeMethod(
									(ModelElement) selfClassData, "self",
									sourceRange.getOffset(), sourceRange
											.getLength(), sourceRange
											.getOffset(), sourceRange
											.getLength()) {
								public boolean isConstructor()
										throws ModelException {
									return true;
								}
							};
							ctorMethod.setParameters(ctor.getParameters());
							reporter.reportMethod(ctorMethod, suffix,
									replaceRange);
						} else {
							ISourceRange sourceRange = selfClassData
									.getSourceRange();
							reporter.reportMethod(new FakeMethod(
									(ModelElement) selfClassData, "self",
									sourceRange.getOffset(), sourceRange
											.getLength(), sourceRange
											.getOffset(), sourceRange
											.getLength()), "()", replaceRange);
						}
					} catch (ModelException e) {
						PHPCorePlugin.log(e);
					}
				}
			}
		}
	}

	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "\\".equals(nextWord) ? "" : "\\"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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

	/**
	 * 
	 * @return
	 */
	protected Object getExtraInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
