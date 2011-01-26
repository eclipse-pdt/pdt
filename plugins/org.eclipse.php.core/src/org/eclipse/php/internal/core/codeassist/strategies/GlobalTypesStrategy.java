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

import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes global types (classes, interfaces, namespaces)
 * 
 * @author michael
 */
public class GlobalTypesStrategy extends GlobalElementStrategy {

	protected final int trueFlag;
	protected final int falseFlag;
	protected static final IType[] EMPTY = {};
	private boolean aliasAdded = false;

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
			final String prefix = abstractContext.getPrefixWithoutProcessing();

			if (prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {
				IModuleSource module = reporter.getModule();
				org.eclipse.dltk.core.ISourceModule sourceModule = (org.eclipse.dltk.core.ISourceModule) module
						.getModelElement();
				ModuleDeclaration moduleDeclaration = SourceParserUtil
						.getModuleDeclaration(sourceModule);
				final int offset = abstractContext.getOffset();
				IType namespace = PHPModelUtils.getCurrentNamespace(
						sourceModule, offset);

				final Map<String, UsePart> result = getAliasToNSMap(prefix,
						moduleDeclaration, offset, namespace);
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
		IDLTKSearchScope scope = createSearchScope();
		for (Iterator iterator = result.keySet().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			String fullName = result.get(name).getNamespace()
					.getFullyQualifiedName();
			IType[] elements = PhpModelAccess.getDefault().findTypes(fullName,
					MatchRule.EXACT, 0, 0, scope, null);
			try {
				for (int i = 0; i < elements.length; i++) {
					if (!PHPFlags.isNamespace(elements[i].getFlags())) {
						reportAlias(reporter, scope, module, replacementRange,
								elements[i], fullName, name, suffix);
					} else {
						IType[] typesOfNS = elements[i].getTypes();
						for (int j = 0; j < typesOfNS.length; j++) {

						}
					}
				}

			} catch (ModelException e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, UsePart> getAliasToNSMap(final String prefix,
			ModuleDeclaration moduleDeclaration, final int offset,
			IType namespace) {
		final Map<String, UsePart> result = new HashMap<String, UsePart>();
		try {
			int start = 0;
			if (namespace != null) {
				start = namespace.getSourceRange().getOffset();
			}
			final int searchStart = start;

			moduleDeclaration.traverse(new ASTVisitor() {

				public boolean visit(Statement s) throws Exception {
					if (s instanceof UseStatement) {
						UseStatement useStatement = (UseStatement) s;
						for (UsePart usePart : useStatement.getParts()) {
							if (usePart.getAlias() != null
									&& usePart.getAlias().getName() != null) {
								// TODO case non-sensitive
								String name = usePart.getAlias().getName();
								if (name.startsWith(prefix)) {
									result.put(name, usePart);
								}
							} else {
								String name = usePart.getNamespace()
										.getFullyQualifiedName();
								int index = name
										.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
								if (index >= 0) {
									name = name.substring(index + 1);
								}
								if (name.startsWith(prefix)) {
									result.put(name, usePart);

								}
							}
						}
					}
					return visitGeneral(s);
				}

				public boolean visitGeneral(ASTNode node) throws Exception {
					if (node.sourceStart() > offset
							|| node.sourceEnd() < searchStart) {
						return false;
					}
					return super.visitGeneral(node);
				}
			});
		} catch (Exception e) {
			Logger.logException(e);
		}
		return result;
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
		IType[] types = PhpModelAccess.getDefault().findTypes(null, prefix,
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
									(ModelElement) selfClassData, "self",
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
											"self", sourceRange.getOffset(),
											sourceRange.getLength(),
											sourceRange.getOffset(),
											sourceRange.getLength()), "()",
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
