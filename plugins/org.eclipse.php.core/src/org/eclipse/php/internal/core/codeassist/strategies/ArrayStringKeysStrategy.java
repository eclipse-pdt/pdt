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
import java.util.Set;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.ArrayKeyContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * This strategy completes builtin array keys, like in _SERVER.
 * 
 * @author michael
 */
public class ArrayStringKeysStrategy extends AbstractCompletionStrategy {

	public ArrayStringKeysStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ArrayStringKeysStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof ArrayKeyContext)) {
			return;
		}

		SourceRange replaceRange = getReplacementRange(context);
		ArrayKeyContext arrayContext = (ArrayKeyContext) context;
		if (arrayContext.hasQuotes()) {// https://bugs.eclipse.org/bugs/show_bug.cgi?id=401766
			replaceRange = new SourceRange(replaceRange.getOffset(),
					replaceRange.getLength() + 1);
		}
		CompletionRequestor requestor = arrayContext.getCompletionRequestor();

		String prefix = arrayContext.getPrefix();
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(arrayContext.getSourceModule());
		try {
			ArrayKeyFinder finder = new ArrayKeyFinder(prefix);
			moduleDeclaration.traverse(finder);
			Set<String> names = finder.getNames();
			int extraObject = ProposalExtraInfo.DEFAULT;
			if (!arrayContext.hasQuotes()) {
				extraObject |= ProposalExtraInfo.ADD_QUOTES;
			}
			for (String name : names) {

				if (!requestor.isContextInformationMode()) {
					reporter.reportField(new FakeField(
							(ModelElement) arrayContext.getSourceModule(),
							name, 0, 0), "", replaceRange, false, 0, //$NON-NLS-1$
							extraObject); // NON-NLS-1
				}

			}
		} catch (Exception e) {
		}
	}

	protected void reportVariables(ICompletionReporter reporter,
			ArrayKeyContext context, String[] variables, String prefix)
			throws BadLocationException {
		reportVariables(reporter, context, variables, prefix, false);
	}

	protected void reportVariables(ICompletionReporter reporter,
			ArrayKeyContext context, String[] variables, String prefix,
			boolean removeDollar) throws BadLocationException {
		CompletionRequestor requestor = context.getCompletionRequestor();
		SourceRange replaceRange = getReplacementRange(context);
		for (String variable : variables) {
			if (removeDollar) {
				variable = variable.substring(1);
			}
			if (variable.startsWith(prefix)) {
				if (!requestor.isContextInformationMode()
						|| variable.length() == prefix.length()) {
					reporter.reportField(
							new FakeField((ModelElement) context
									.getSourceModule(), variable, 0, 0), "", //$NON-NLS-1$
							replaceRange, false); // NON-NLS-1
				}
			}
		}
	}

	class ArrayKeyFinder extends PHPASTVisitor {
		Set<String> names = new HashSet<String>();
		String prefix;

		public ArrayKeyFinder(String prefix) {
			// TODO Auto-generated constructor stub
			this.prefix = prefix;
		}

		public boolean visit(ArrayCreation s) throws Exception {
			return super.visit(s);
		}

		public boolean visit(ArrayElement s) throws Exception {
			if (s.getKey() instanceof Scalar) {
				Scalar scalar = (Scalar) s.getKey();
				if (scalar.getScalarType() == Scalar.TYPE_STRING) {
					String key = ASTUtils.stripQuotes(scalar.getValue());
					if (!scalar.getValue().equals(key)
							&& key.length() > 0
							&& key.toLowerCase().startsWith(
									prefix.toLowerCase())) {
						names.add(key);
					}
				}
			}
			return super.visit(s);
		}

		public boolean visit(ArrayVariableReference s) throws Exception {

			if (s.getIndex() instanceof Scalar) {
				Scalar scalar = (Scalar) s.getIndex();
				if (scalar.getScalarType() == Scalar.TYPE_STRING) {
					String key = ASTUtils.stripQuotes(scalar.getValue());
					if (!scalar.getValue().equals(key)
							&& key.length() > 0
							&& key.toLowerCase().startsWith(
									prefix.toLowerCase())) {
						names.add(key);
					}
				}
			}
			return super.visit(s);
		}

		public boolean visit(ReflectionArrayVariableReference s)
				throws Exception {
			if (s.getIndex() instanceof Scalar) {
				Scalar scalar = (Scalar) s.getIndex();
				if (scalar.getScalarType() == Scalar.TYPE_STRING) {
					String key = ASTUtils.stripQuotes(scalar.getValue());
					if (!scalar.getValue().equals(key)
							&& key.length() > 0
							&& key.toLowerCase().startsWith(
									prefix.toLowerCase())) {
						names.add(key);
					}
				}
			}
			return super.visit(s);
		}

		public boolean visit(PHPArrayDereferenceList s) throws Exception {
			return super.visit(s);
		}

		public Set<String> getNames() {
			return names;
		}

	}
}
