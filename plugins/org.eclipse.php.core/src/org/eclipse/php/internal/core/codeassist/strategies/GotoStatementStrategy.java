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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.GotoStatementContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.GotoLabel;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes labels for goto statement
 */
public class GotoStatementStrategy extends GlobalElementStrategy {

	public GotoStatementStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public GotoStatementStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();

		GotoStatementContext gotoStatementContext = (GotoStatementContext) context;

		String prefix = gotoStatementContext.getPrefix();
		if (prefix.startsWith("$")) { //$NON-NLS-1$
			return;
		}
		try {
			ModuleDeclaration rootNode = SourceParserUtil
					.getModuleDeclaration(gotoStatementContext
							.getSourceModule());
			ASTNode node;
			if (gotoStatementContext.getCurrentElement() != null) {
				node = PHPModelUtils.getNodeByElement(rootNode,
						gotoStatementContext.getCurrentElement());
			} else {
				node = rootNode;
			}
			GotoStatementVisitor vistor = new GotoStatementVisitor(node, prefix);
			node.traverse(vistor);
			List<String> getoLabels = vistor.getGotoLabels();
			SourceRange range = getReplacementRange(context);
			for (String label : getoLabels) {
				reporter.reportKeyword(label, "", range); //$NON-NLS-1$
			}
		} catch (Exception e) {
		}

	}

	private class GotoStatementVisitor extends PHPASTVisitor {

		private List<String> getoLabels = new ArrayList<String>();
		private ASTNode node;
		private String prefix;

		public GotoStatementVisitor(ASTNode node, String prefix) {
			this.node = node;
			this.prefix = prefix.toLowerCase();
		}

		public boolean visit(GotoLabel s) throws Exception {
			if (s.getLabel().toLowerCase().startsWith(prefix)) {
				getoLabels.add(s.getLabel());
			}
			return false;
		}

		public boolean visit(TypeDeclaration s) throws Exception {
			if (node != s) {
				return false;
			}
			return true;
		}

		public boolean visit(MethodDeclaration s) throws Exception {
			if (node != s) {
				return false;
			}
			return true;
		}

		public List<String> getGotoLabels() {
			return getoLabels;
		}
	}

}
