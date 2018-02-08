/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.scope;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.Logger;

public class ScopeParser {
	private final IDocument document;

	@NonNull
	private final ICompletionScope parentScope;

	private static class ScopeDetector extends PHPASTVisitor {
		private ICompletionScope scope;
		private int offset;

		public ScopeDetector(ICompletionScope scope, int offset) {
			this.scope = scope;
			this.offset = offset;
		}

		private void scopeFactory(ICompletionScope.Type type, ASTNode node) {
			scope = new CompletionScope(type, new SourceRange(node), node, scope);
		}

		@Override
		public boolean visit(ClassDeclaration s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.CLASS, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(AnonymousClassDeclaration s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.CLASS, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(TraitDeclaration s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.TRAIT, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(InterfaceDeclaration s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.INTERFACE, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(PHPMethodDeclaration s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.FUNCTION, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(LambdaFunctionDeclaration s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.FUNCTION, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(UseStatement s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.USE, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(UsePart s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.USE_GROUP, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(TraitUseStatement s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.TRAIT_USE, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(TraitPrecedence s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.TRAIT_PRECEDENCE, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visit(NamespaceDeclaration s) throws Exception {
			if (super.visit(s)) {
				scopeFactory(Type.NAMESPACE, s);
				return true;
			}
			return false;
		}

		@Override
		public boolean visitGeneral(ASTNode node) throws Exception {
			if (node.sourceStart() > offset) {
				return false;
			}
			if (node.sourceStart() < offset && node.sourceEnd() < offset) {
				return false;
			}
			return super.visitGeneral(node);
		}

		public ICompletionScope getScope() {
			return scope;
		}
	}

	public ScopeParser(PHPModuleDeclaration declaration, IDocument document) {
		this.document = document;
		ISourceRange sourceRange;
		if (document != null) {
			sourceRange = new SourceRange(0, document.getLength());
		} else if (declaration != null) {
			sourceRange = new SourceRange(declaration);
		} else {
			sourceRange = new SourceRange(0, 0);
		}
		this.parentScope = new CompletionScope(ICompletionScope.Type.FILE, sourceRange, declaration, null);
	}

	public ICompletionScope parse(int offset) {

		ICompletionScope scope = parentScope;
		if (parentScope.getAST() != null) {
			ScopeDetector scopeDetector = new ScopeDetector(parentScope, offset);
			try {
				parentScope.getAST().traverse(scopeDetector);
			} catch (Exception e) {
				Logger.logException(e);
			}
			scope = scopeDetector.getScope();
		}

		return scope;
	}

}
