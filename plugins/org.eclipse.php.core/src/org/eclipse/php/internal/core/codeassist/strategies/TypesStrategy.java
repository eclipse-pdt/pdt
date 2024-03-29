/*******************************************************************************
 * Copyright (c) 2009, 2017, 2019 IBM Corporation and others.
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
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.*;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.UseStatementContext;
import org.eclipse.php.internal.core.model.PHPModelAccess;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes global types (classes, interfaces, namespaces)
 * 
 * @author michael
 */
public class TypesStrategy extends ElementsStrategy {

	protected final int trueFlag;
	protected final int falseFlag;
	protected static final IType[] EMPTY = {};
	private boolean aliasAdded = false;

	public TypesStrategy(ICompletionContext context, int trueFlag, int falseFlag) {
		super(context, null);
		this.trueFlag = trueFlag;
		this.falseFlag = falseFlag;
	}

	public TypesStrategy(ICompletionContext context) {
		this(context, 0, 0);
	}

	@Override
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
		if (StringUtils.isBlank(abstractContext.getPrefix())) {
			return;
		}

		ISourceRange replacementRange = getReplacementRange(abstractContext);
		ISourceRange memberReplacementRange = getReplacementRangeForMember(abstractContext);

		String nsUseGroupPrefix = null;
		if (context instanceof UseStatementContext) {
			nsUseGroupPrefix = ((UseStatementContext) context).getGroupPrefixBeforeOpeningCurly();
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=501654
			if (nsUseGroupPrefix != null && nsUseGroupPrefix.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				nsUseGroupPrefix = nsUseGroupPrefix.substring(1);
			}
		}

		IType[] types = getTypes(abstractContext);
		// now we compute type suffix in PHPCompletionProposalCollector
		String suffix = ""; //$NON-NLS-1$
		String nsSuffix = getNSSuffix(abstractContext);
		int extraInfo = getExtraInfo();
		if (abstractContext.isAbsoluteName()) {
			extraInfo |= ProposalExtraInfo.FULL_NAME;
			extraInfo |= ProposalExtraInfo.NO_INSERT_USE;
			extraInfo |= ProposalExtraInfo.ABSOLUTE_NAME;
		}

		if (abstractContext.isAbsolute()) {
			extraInfo |= ProposalExtraInfo.FULL_NAME;
			extraInfo |= ProposalExtraInfo.NO_INSERT_USE;
		}

		if (nsUseGroupPrefix != null) {
			extraInfo |= ProposalExtraInfo.NO_INSERT_USE;
		}

		String namespace = getCompanion().getCurrentNamespace();
		for (IType type : types) {
			try {
				int flags = type.getFlags();
				boolean isNamespace = PHPFlags.isNamespace(flags);
				int relevance = getRelevance(namespace, type);
				if (nsUseGroupPrefix != null) {
					reporter.reportType(type, nsUseGroupPrefix, isNamespace ? nsSuffix : suffix,
							isNamespace ? replacementRange : memberReplacementRange, extraInfo, relevance);
				} else {
					boolean isAbsoluteType = abstractContext.isAbsoluteName() || abstractContext.isAbsolute();
					if (isNamespace || isAbsoluteType) {
						reporter.reportType(type, isNamespace ? nsSuffix : suffix, replacementRange,
								extraInfo | ProposalExtraInfo.MEMBER_IN_NAMESPACE, relevance);
					} else {
						reporter.reportType(type, isNamespace ? nsSuffix : suffix,
								isNamespace ? replacementRange : memberReplacementRange, extraInfo, relevance);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		addAlias(reporter, suffix);

	}

	protected void addAlias(ICompletionReporter reporter, String suffix) throws BadLocationException {
		if (aliasAdded) {
			return;
		}
		aliasAdded = true;
		ICompletionContext context = getContext();
		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		if (!abstractContext.getCompletionRequestor().isContextInformationMode()) {
			// get types for alias
			String prefix = abstractContext.getPrefix();
			boolean exactMatch = false;
			if (prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) == 0) {
				return;
			} else if (prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
				prefix = prefix.substring(0, prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
				exactMatch = true;
			} else {

			}

			if (prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {
				ModuleDeclaration moduleDeclaration = getCompanion().getModuleDeclaration();
				final int offset = getCompanion().getOffset();
				IType namespace = PHPModelUtils.getCurrentNamespace(getCompanion().getSourceModule(), offset);

				final Map<String, UsePart> result = PHPModelUtils.getAliasToNSMap(prefix, moduleDeclaration, offset,
						namespace, exactMatch);
				reportAlias(reporter, suffix, abstractContext, getCompanion().getSourceModule(), result);

			}
		}
	}

	protected void reportAliasForNS(ICompletionReporter reporter, String suffix,
			AbstractCompletionContext abstractContext, ISourceModule module, final Map<String, UsePart> result)
			throws BadLocationException {
		ISourceRange replacementRange = getReplacementRange(abstractContext);
		IDLTKSearchScope scope = createSearchScope();
		for (Entry<String, UsePart> entry : result.entrySet()) {
			String fullName = entry.getValue().getFullUseStatementName();
			IType[] elements = PHPModelAccess.getDefault().findTypes(null,
					fullName + NamespaceReference.NAMESPACE_SEPARATOR, MatchRule.PREFIX, 0, 0, scope, null);
			for (int i = 0; i < elements.length; i++) {
				String elementName = elements[i].getElementName();
				reportAlias(reporter, scope, module, replacementRange, elements[i], elementName,
						elementName.replace(fullName, entry.getKey()), suffix);
			}
		}
	}

	protected void reportAlias(ICompletionReporter reporter, String suffix, AbstractCompletionContext abstractContext,
			ISourceModule module, final Map<String, UsePart> result) throws BadLocationException {
		ISourceRange replacementRange = getReplacementRange(abstractContext);
		String prefix = abstractContext.getPrefix();
		IDLTKSearchScope scope = createSearchScope();
		for (Entry<String, UsePart> entry : result.entrySet()) {
			if (entry.getValue().getAlias() != null) {
				String name = entry.getKey();
				String fullName = entry.getValue().getFullUseStatementName();
				try {
					IType[] elements = PHPModelAccess.getDefault().findTypes(fullName, MatchRule.EXACT, 0, 0, scope,
							null);
					for (int i = 0; i < elements.length; i++) {
						if (PHPModelUtils.getFullName(elements[i]).equalsIgnoreCase(fullName)) {
							reportAlias(reporter, scope, module, replacementRange, elements[i],
									elements[i].getElementName(), name, suffix);
						}

					}
					IType[] namespaces = PHPModelAccess.getDefault().findNamespaces(null, fullName, MatchRule.EXACT, 0,
							0, scope, null);
					for (int i = 0; i < namespaces.length; i++) {
						String elementName = namespaces[i].getElementName();
						String nsname = prefix.replace(name, fullName);
						if (nsname.startsWith(elementName + NamespaceReference.NAMESPACE_DELIMITER)
								&& nsname.lastIndexOf(NamespaceReference.NAMESPACE_DELIMITER) == elementName.length()) {
							// namespace strategy will handle this case
							continue;
						}

						IType[] typesOfNS = namespaces[i].getTypes();

						for (int j = 0; j < typesOfNS.length; j++) {
							reportAlias(reporter, scope, module, replacementRange, typesOfNS[j],
									// https://bugs.eclipse.org/bugs/show_bug.cgi?id=469779
									// elementName +
									// NamespaceReference.NAMESPACE_DELIMITER +
									typesOfNS[j].getElementName(), (elementName + NamespaceReference.NAMESPACE_DELIMITER
											+ typesOfNS[j].getElementName()).replace(fullName, name),
									suffix);
						}

					}
				} catch (ModelException e) {
					PHPCorePlugin.log(e);
				}
			}
		}
	}

	protected void reportAlias(ICompletionReporter reporter, IDLTKSearchScope scope, ISourceModule module,
			ISourceRange replacementRange, IType type, String fullName, String alias, String suffix) {
		reporter.reportType(new AliasType((ModelElement) type, fullName, alias), suffix, replacementRange,
				getExtraInfo());
	}

	/**
	 * Runs the query to retrieve all global types
	 * 
	 * @param context
	 * @return
	 * @throws BadLocationException
	 */
	protected IType[] getTypes(AbstractCompletionContext context) throws BadLocationException {

		String prefix = context.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			return EMPTY;
		}

		IDLTKSearchScope scope = createSearchScope();
		if (context.getCompletionRequestor().isContextInformationMode()) {
			return PHPModelAccess.getDefault().findTypes(context.getQualifier(true), context.getMemberName(),
					MatchRule.EXACT, trueFlag, falseFlag, scope, null);
		}

		List<IType> result = new LinkedList<>();
		String memberName = context.getMemberName();
		String namespaceName = context.getQualifier(false);
		boolean isAbsoluteType = context.isAbsoluteName() || context.isAbsolute();

		StringBuilder sb = new StringBuilder();
		String altNamespacePrefix = null;
		if (namespaceName != null) {
			sb.append(namespaceName).append(NamespaceReference.NAMESPACE_SEPARATOR);
		} else if (!isAbsoluteType && getCompanion().getCurrentNamespace() != null && useCurrentNamespace) {
			altNamespacePrefix = new StringBuilder().append(getCompanion().getCurrentNamespace())
					.append(NamespaceReference.NAMESPACE_SEPARATOR).append(memberName).toString();
		}
		String namespacePrefix = sb.append(memberName).toString();
		if (memberName.length() > 1 && memberName.toUpperCase().equals(memberName)) {
			// Search by camel-case

			IType[] types = PHPModelAccess.getDefault().findTypes(context.getQualifier(true), memberName,
					MatchRule.CAMEL_CASE, trueFlag, falseFlag, scope, null);

			result.addAll(Arrays.asList(types));
			if ((falseFlag & PHPFlags.AccNameSpace) == 0 || isAbsoluteType || namespaceName != null) {
				if (namespacePrefix.length() > 0) {
					IType[] namespaces = PHPModelAccess.getDefault().findNamespaces(null, namespacePrefix.toString(),
							MatchRule.CAMEL_CASE, trueFlag, 0, scope, null);
					result.addAll(CodeAssistUtils.removeDuplicatedElements(namespaces));
				}
			}
		}
		IType[] types = PHPModelAccess.getDefault().findTraitOrTypes(context.getQualifier(true), memberName,
				MatchRule.PREFIX, trueFlag, falseFlag, scope, null);

		result.addAll(Arrays.asList(types));
		if ((falseFlag & PHPFlags.AccNameSpace) == 0 || isAbsoluteType || namespaceName != null
				|| altNamespacePrefix != null) {
			if (namespacePrefix.length() > 0) {
				IType[] namespaces = PHPModelAccess.getDefault().findNamespaces(null, namespacePrefix, MatchRule.PREFIX,
						trueFlag, 0, scope, null);
				result.addAll(CodeAssistUtils.removeDuplicatedElements(namespaces));
			}
			if (altNamespacePrefix != null) {
				IType[] namespaces = PHPModelAccess.getDefault().findNamespaces(null, altNamespacePrefix,
						MatchRule.PREFIX, trueFlag, 0, scope, null);
				result.addAll(CodeAssistUtils.removeDuplicatedElements(namespaces));
			}

		}
		return result.toArray(new IType[result.size()]);
	}

	/**
	 * Adds the self function with the relevant data to the proposals array
	 * 
	 * @param context
	 * @param reporter
	 * @throws BadLocationException
	 */
	protected void addSelf(AbstractCompletionContext context, ICompletionReporter reporter)
			throws BadLocationException {

		String prefix = context.getPrefix();
		ISourceRange replaceRange = getReplacementRange(context);

		if (StringUtils.startsWithIgnoreCase("self", prefix)) { //$NON-NLS-1$
			if (!context.getCompletionRequestor().isContextInformationMode() || prefix.length() == 4) { // "self".length()

				String suffix = getSuffix(context);

				// get the class data for "self". In case of null, the self
				// function will not be added
				IType selfClassData = CodeAssistUtils.getSelfClassData(getCompanion().getSourceModule(),
						getCompanion().getOffset());
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
							ISourceRange sourceRange = selfClassData.getSourceRange();
							FakeMethod ctorMethod = new FakeMethod((ModelElement) selfClassData, "self", //$NON-NLS-1$
									sourceRange.getOffset(), sourceRange.getLength(), sourceRange.getOffset(),
									sourceRange.getLength()) {
								@Override
								public boolean isConstructor() throws ModelException {
									return true;
								}
							};
							ctorMethod.setParameters(ctor.getParameters());
							reporter.reportMethod(ctorMethod, suffix, replaceRange, ProposalExtraInfo.FULL_NAME);
						} else {
							ISourceRange sourceRange = selfClassData.getSourceRange();
							reporter.reportMethod(
									new FakeMethod((ModelElement) selfClassData, "self", sourceRange.getOffset(), //$NON-NLS-1$
											sourceRange.getLength(), sourceRange.getOffset(), sourceRange.getLength()),
									"()", //$NON-NLS-1$
									replaceRange, ProposalExtraInfo.FULL_NAME);
						}
					} catch (ModelException e) {
						PHPCorePlugin.log(e);
					}
				}
			}
		}
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

}
