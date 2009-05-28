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
package org.eclipse.php.internal.core.search;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.compiler.env.lookup.Scope;
import org.eclipse.dltk.compiler.util.HashtableOfIntValues;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchMatch;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.SearchRequestor;
import org.eclipse.dltk.core.search.matching.MatchLocator;
import org.eclipse.dltk.core.search.matching.PatternLocator;
import org.eclipse.dltk.internal.core.search.matching.MatchingNodeSet;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;

public class PHPMatchLocator extends MatchLocator {

	/**
	* An ast visitor that visits local type declarations.
	*/
	public class LocalDeclarationVisitor extends ASTVisitor {

		IModelElement parent;
		MatchingNodeSet nodeSet;
		HashtableOfIntValues occurrencesCounts = new HashtableOfIntValues();

		public LocalDeclarationVisitor(IModelElement parent, MatchingNodeSet nodeSet) {
			this.parent = parent;
			this.nodeSet = nodeSet;
		}

		public boolean visit(Expression expression) {
			if (expression instanceof CallExpression) {
				try {
					FieldDeclaration constantDecl = ASTUtils.getConstantDeclaration((CallExpression) expression);
					if (constantDecl != null) {
						Integer level = (Integer) nodeSet.matchingNodes.removeKey(constantDecl);
						reportMatching(null, constantDecl, parent, level != null ? level.intValue() : -1, nodeSet);
						return false;
					}
				} catch (CoreException e) {
					throw new WrappedCoreException(e);
				}
			}
			return true;
		}

		public boolean visit(TypeDeclaration typeDeclaration) {
			if (typeDeclaration instanceof NamespaceDeclaration && ((NamespaceDeclaration)typeDeclaration).isGlobal()) {
				return false;
			}
			try {
				char[] simpleName = typeDeclaration.getName().toCharArray();
				int occurrenceCount = occurrencesCounts.get(simpleName);
				if (occurrenceCount == HashtableOfIntValues.NO_VALUE) {
					occurrenceCount = 1;
				} else {
					occurrenceCount = occurrenceCount + 1;
				}
				occurrencesCounts.put(simpleName, occurrenceCount);
				Integer level = (Integer) nodeSet.matchingNodes.removeKey(typeDeclaration);
				reportMatching(typeDeclaration, parent, level != null ? level.intValue() : -1, nodeSet, occurrenceCount);
				return false; // don't visit members as this was done during
			} catch (CoreException e) {
				throw new WrappedCoreException(e);
			}
		}

		public boolean visit(MethodDeclaration method) {
			try {
				Integer level = (Integer) nodeSet.matchingNodes.removeKey(method);
				reportMatching(null, method, parent, level != null ? level.intValue() : -1, nodeSet);
				return false; // don't visit members as this was done during
			} catch (CoreException e) {
				throw new WrappedCoreException(e);
			}
		}
	}

	public PHPMatchLocator(SearchPattern pattern, SearchRequestor requestor, IDLTKSearchScope scope, IProgressMonitor progressMonitor) {
		super(pattern, requestor, scope, progressMonitor);
	}

	protected void reportMatching(ModuleDeclaration module, MethodDeclaration method, IModelElement parent, int accuracy, MatchingNodeSet nodeSet) throws CoreException {
		if (parent == null) {
			parent = createSourceModuleHandle();
		}
		IModelElement enclosingElement = createHandle(method, parent);
		if (enclosingElement == null) {
			enclosingElement = createMethodHandle(method.getName());
		}
		if (accuracy > -1 && enclosingElement != null) { // skip if unable to find method
			if (encloses(enclosingElement)) {
				SearchMatch match = null;
				if (DLTKCore.DEBUG) {
					System.out.println("TODO: AST Add constructor support."); //$NON-NLS-1$
				}
				match = this.patternLocator.newDeclarationMatch(method, enclosingElement, accuracy, this);
				if (match != null) {
					report(match);
				}
			}
		}

		// handle nodes for the local type first
		LocalDeclarationVisitor localDeclarationVisitor = new LocalDeclarationVisitor(enclosingElement, nodeSet);
		try {
			method.getBody().traverse(localDeclarationVisitor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}

		// references in this method
		ASTNode[] nodes = nodeSet.matchingNodes(method.sourceStart(), method.sourceEnd());
		if (nodes != null) {
			if (parent == null) {
				parent = createSourceModuleHandle();
			}
			if ((this.matchContainer & PatternLocator.METHOD_CONTAINER) != 0) {
				if (enclosingElement == null)
					enclosingElement = createHandle(method, parent);
				if (encloses(enclosingElement)) {
					for (int i = 0, l = nodes.length; i < l; i++) {
						ASTNode node = nodes[i];
						Integer level = (Integer) nodeSet.matchingNodes.removeKey(node);
						if (DLTKCore.DEBUG) {
							System.out.println("TODO: Searching. Add scope support."); //$NON-NLS-1$
						}
						this.patternLocator.matchReportReference(node, enclosingElement, (Scope) null, level.intValue(), this);
					}
					return;
				}
			}
			for (int i = 0, l = nodes.length; i < l; i++)
				nodeSet.matchingNodes.removeKey(nodes[i]);
		}
	}

	protected void reportMatching(TypeDeclaration type, MethodDeclaration method, IModelElement parent, int accuracy, boolean typeInHierarchy, MatchingNodeSet nodeSet) throws CoreException {
		IModelElement enclosingElement = createHandle(method, parent);
		if (accuracy > -1 && enclosingElement != null) { // skip if unable to find method
			if (encloses(enclosingElement)) {
				SearchMatch match = null;
				if (DLTKCore.DEBUG) {
					System.out.println("TODO: AST Add constructor support."); //$NON-NLS-1$
				}
				match = this.patternLocator.newDeclarationMatch(method, enclosingElement, accuracy, this);
				if (match != null) {
					report(match);
				}
			}
		}

		// handle nodes for the local type first
		LocalDeclarationVisitor localDeclarationVisitor = new LocalDeclarationVisitor(enclosingElement, nodeSet);
		try {
			method.getBody().traverse(localDeclarationVisitor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}

		// references in this method
		if (typeInHierarchy) {
			ASTNode[] nodes = nodeSet.matchingNodes(method.sourceStart(), method.sourceEnd());
			if (nodes != null) {
				if ((this.matchContainer & PatternLocator.CLASS_CONTAINER) != 0) {
					if (enclosingElement == null)
						enclosingElement = createHandle(method, parent);
					if (encloses(enclosingElement)) {
						for (int i = 0, l = nodes.length; i < l; i++) {
							ASTNode node = nodes[i];
							Integer level = (Integer) nodeSet.matchingNodes.removeKey(node);
							if (DLTKCore.DEBUG) {
								System.out.println("TODO: Searching. Add scope support."); //$NON-NLS-1$
							}
							this.patternLocator.matchReportReference(node, enclosingElement, (Scope) null, level.intValue(), this);
						}
						return;
					}
				}
				for (int i = 0, l = nodes.length; i < l; i++)
					nodeSet.matchingNodes.removeKey(nodes[i]);
			}
		}
	}

	protected void reportMatching(TypeDeclaration type, IModelElement parent, int accuracy, MatchingNodeSet nodeSet, int occurrenceCount) throws CoreException {
		if (parent != null && parent.getElementType() == IModelElement.METHOD) {
			// All PHP elements declared inside of function are automatically belonging
			// to the global scope. When parent = null parent implementation uses source module: 
			parent = null;
		}
		super.reportMatching(type, parent, accuracy, nodeSet, occurrenceCount);
	}

}
