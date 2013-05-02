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

import java.util.*;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.*;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.NamespaceMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.UseNameContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes global types (classes, interfaces, namespaces)
 * 
 * @author michael
 */
public class GlobalTypesStrategy extends GlobalElementStrategy {

	private static final String SPLASH = "\\"; //$NON-NLS-1$
	protected final int trueFlag;
	protected final int falseFlag;
	protected static final IType[] EMPTY = {};
	private boolean aliasAdded = false;
	private boolean addClassInNamespace = false;

	public GlobalTypesStrategy(ICompletionContext context, int trueFlag,
			int falseFlag) {
		super(context, null);
		this.trueFlag = trueFlag;
		this.falseFlag = falseFlag;
	}

	public GlobalTypesStrategy(ICompletionContext context, int trueFlag,
			int falseFlag, boolean addClassInNamespace) {
		super(context, null);
		this.trueFlag = trueFlag;
		this.falseFlag = falseFlag;
		this.addClassInNamespace = addClassInNamespace;
	}

	public GlobalTypesStrategy(ICompletionContext context) {
		this(context, 0, 0);
	}

	public SourceRange getReplacementRange(ICompletionContext context)
			throws BadLocationException {
		SourceRange replacementRange = super.getReplacementRange(context);
		boolean insertMode = isInsertMode();
		if (replacementRange.getLength() > 0 && insertMode) {
			return new SourceRange(replacementRange.getOffset(),
					replacementRange.getLength() - 1);
		}
		return replacementRange;
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		if (abstractContext.getCompletionRequestor() instanceof IPHPCompletionRequestor) {
			IPHPCompletionRequestor phpCompletionRequestor = (IPHPCompletionRequestor) abstractContext
					.getCompletionRequestor();
			if (phpCompletionRequestor.filter(CompletionFlag.STOP_REPORT_TYPE)) {
				return;
			}
		}
		if (abstractContext.getPrefixWithoutProcessing().trim().length() == 0) {
			return;
		}
		boolean isUseContext = context instanceof UseNameContext;
		SourceRange replacementRange = getReplacementRange(abstractContext);

		IType[] types = getTypes(abstractContext);
		// now we compute type suffix in PHPCompletionProposalCollector
		String suffix = "";//$NON-NLS-1$ 
		String nsSuffix = getNSSuffix(abstractContext);
		int extraInfo = getExtraInfo();
		if ((abstractContext.getOffset() - abstractContext.getPrefix().length()
				- 1 >= 0)
				&& (abstractContext.getDocument().getChar(
						abstractContext.getOffset()
								- abstractContext.getPrefix().length() - 1) == '\'' || abstractContext
						.getDocument().getChar(
								abstractContext.getOffset()
										- abstractContext.getPrefix().length()
										- 1) == '\"')) {
			extraInfo = extraInfo | ProposalExtraInfo.NO_INSERT_NAMESPACE;
		}
		if ("namespace".equals(abstractContext.getPreviousWord(1)) //$NON-NLS-1$
				|| isUseContext) {
			extraInfo = extraInfo | ProposalExtraInfo.NO_INSERT_NAMESPACE;
		}

		for (IType type : types) {
			try {
				int flags = type.getFlags();
				boolean isNamespace = PHPFlags.isNamespace(flags);
				if (!isNamespace && isUseContext) {
					reporter.reportType(type, isNamespace ? nsSuffix : suffix,
							replacementRange, extraInfo
									| ProposalExtraInfo.CLASS_IN_NAMESPACE);
				} else {
					reporter.reportType(type, isNamespace ? nsSuffix : suffix,
							replacementRange, extraInfo);
				}
				if (addClassInNamespace && isNamespace) {
					IType[] subTypes = type.getTypes();
					for (IType subType : subTypes) {
						int subFlags = type.getFlags();
						reporter.reportType(subType, PHPFlags
								.isNamespace(subFlags) ? nsSuffix : suffix,
								replacementRange, extraInfo
										| ProposalExtraInfo.CLASS_IN_NAMESPACE);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		addAlias(reporter, suffix);

	}

	protected void addAlias(ICompletionReporter reporter, String suffix)
			throws BadLocationException {
		if (aliasAdded) {
			return;
		}
		aliasAdded = true;
		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		if (!abstractContext.getCompletionRequestor()
				.isContextInformationMode()) {
			// get types for alias
			String prefix = abstractContext.getPrefixWithoutProcessing();
			boolean exactMatch = false;
			if (prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) == 0) {
				return;
			} else if (prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
				prefix = prefix.substring(0,
						prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
				exactMatch = true;
			} else {

			}

			if (prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {
				IModuleSource module = reporter.getModule();
				org.eclipse.dltk.core.ISourceModule sourceModule = (org.eclipse.dltk.core.ISourceModule) module
						.getModelElement();
				ModuleDeclaration moduleDeclaration = SourceParserUtil
						.getModuleDeclaration(sourceModule);
				final int offset = abstractContext.getOffset();
				IType namespace = PHPModelUtils.getCurrentNamespace(
						sourceModule, offset);

				final Map<String, UsePart> result = PHPModelUtils
						.getAliasToNSMap(prefix, moduleDeclaration, offset,
								namespace, exactMatch);
				reportAlias(reporter, suffix, abstractContext, module, result);

			}
		}
	}

	protected void reportAliasForNS(ICompletionReporter reporter,
			String suffix, AbstractCompletionContext abstractContext,
			IModuleSource module, final Map<String, UsePart> result)
			throws BadLocationException {
		SourceRange replacementRange = getReplacementRange(abstractContext);
		IDLTKSearchScope scope = createSearchScope();
		for (Iterator iterator = result.keySet().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			String fullName = result.get(name).getNamespace()
					.getFullyQualifiedName();
			IType[] elements = PhpModelAccess.getDefault().findTypes(null,
					fullName + NamespaceReference.NAMESPACE_SEPARATOR,
					MatchRule.PREFIX, 0, 0, scope, null);
			for (int i = 0; i < elements.length; i++) {
				String elementName = elements[i].getElementName();
				reportAlias(reporter, scope, module, replacementRange,
						elements[i], elementName,
						elementName.replace(fullName, name), suffix);
			}
		}
	}

	protected void reportAlias(ICompletionReporter reporter, String suffix,
			AbstractCompletionContext abstractContext, IModuleSource module,
			final Map<String, UsePart> result) throws BadLocationException {
		SourceRange replacementRange = getReplacementRange(abstractContext);
		String prefix = abstractContext.getPrefixWithoutProcessing();
		IDLTKSearchScope scope = createSearchScope();
		for (Iterator iterator = result.keySet().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			String fullName = result.get(name).getNamespace()
					.getFullyQualifiedName();
			if (fullName.startsWith("\\")) { //$NON-NLS-1$
				fullName = fullName.substring(1);
			}
			IType[] elements = PhpModelAccess.getDefault().findTypes(null,
					fullName, MatchRule.PREFIX, 0, 0, scope, null);
			try {
				for (int i = 0; i < elements.length; i++) {
					String elementName = elements[i].getElementName();
					if (!PHPFlags.isNamespace(elements[i].getFlags())) {
						reportAlias(reporter, scope, module, replacementRange,
								elements[i], elementName,
								elementName.replace(fullName, name), suffix);
					} else {
						String nsname = prefix.replace(name, fullName);
						if (nsname.startsWith(elementName + SPLASH)
								&& nsname.lastIndexOf(SPLASH) == elementName
										.length()) {
							// namespace strategy will handle this case
							continue;
						}
						IType[] typesOfNS = elements[i].getTypes();

						for (int j = 0; j < typesOfNS.length; j++) {
							reportAlias(
									reporter,
									scope,
									module,
									replacementRange,
									typesOfNS[j],
									elementName + SPLASH
											+ typesOfNS[j].getElementName(),
									(elementName + SPLASH + typesOfNS[j]
											.getElementName()).replace(
											fullName, name), suffix);
						}
					}
				}

				elements = PhpModelAccess.getDefault().findTypes(fullName,
						MatchRule.EXACT, 0, 0, scope, null);

				for (int i = 0; i < elements.length; i++) {
					String elementName = elements[i].getElementName();
					if (!PHPFlags.isNamespace(elements[i].getFlags())) {
						reportAlias(reporter, scope, module, replacementRange,
								elements[i], elementName, name, suffix);
					} else {
						String nsname = prefix.replace(name, fullName);
						if (nsname.startsWith(elementName + SPLASH)
								&& nsname.lastIndexOf(SPLASH) == elementName
										.length()) {
							// namespace strategy will handle this case
							continue;
						}
						IType[] typesOfNS = elements[i].getTypes();

						for (int j = 0; j < typesOfNS.length; j++) {
							reportAlias(
									reporter,
									scope,
									module,
									replacementRange,
									typesOfNS[j],
									elementName + SPLASH
											+ typesOfNS[j].getElementName(),
									(elementName + SPLASH + typesOfNS[j]
											.getElementName()).replace(
											fullName, name), suffix);
						}
					}
				}
			} catch (ModelException e) {
				e.printStackTrace();
			}
		}
	}

	protected void reportAlias(ICompletionReporter reporter,
			IDLTKSearchScope scope, IModuleSource module,
			SourceRange replacementRange, IType type, String fullName,
			String alias, String suffix) {
		reporter.reportType(
				new AliasType((ModelElement) type, fullName, alias), suffix,
				replacementRange, getExtraInfo());
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
		if (prefix.startsWith("$")) { //$NON-NLS-1$
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
		IType[] types = PhpModelAccess.getDefault().findTypes(null, prefix,
				MatchRule.PREFIX, trueFlag, falseFlag, scope, null);
		if (context instanceof NamespaceMemberContext) {
			for (IType type : types) {
				if (PHPModelUtils.getFullName(type).startsWith(prefix)) {
					result.add(type);
				}
			}
		} else {
			result.addAll(Arrays.asList(types));
		}

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

		if (CodeAssistUtils.startsWithIgnoreCase("self", prefix)) { //$NON-NLS-1$
			if (!context.getCompletionRequestor().isContextInformationMode()
					|| prefix.length() == 4) { // "self".length()

				String suffix = getSuffix(context);

				// get the class data for "self". In case of null, the self
				// function will not be added
				IType selfClassData = CodeAssistUtils.getSelfClassData(
						context.getSourceModule(), context.getOffset());
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
									(ModelElement) selfClassData, "self", //$NON-NLS-1$
									sourceRange.getOffset(),
									sourceRange.getLength(),
									sourceRange.getOffset(),
									sourceRange.getLength()) {
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
							reporter.reportMethod(
									new FakeMethod(
											(ModelElement) selfClassData,
											"self", sourceRange.getOffset(), //$NON-NLS-1$
											sourceRange.getLength(),
											sourceRange.getOffset(),
											sourceRange.getLength()), "()", //$NON-NLS-1$
									replaceRange);
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
		return SPLASH.equals(nextWord) ? "" : SPLASH; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
	protected int getExtraInfo() {
		return ProposalExtraInfo.DEFAULT;
	}

	public void setAddClassInNamespace(boolean addClassInNamespace) {
		this.addClassInNamespace = addClassInNamespace;
	}
}
