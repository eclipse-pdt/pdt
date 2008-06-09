package org.eclipse.php.internal.core.search;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
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

public class PHPMatchLocator extends MatchLocator {

	/**
	* An ast visitor that visits local type declarations.
	*/
	public class LocalDeclarationVisitor extends ASTVisitor {
		
		MatchingNodeSet nodeSet;
		HashtableOfIntValues occurrencesCounts = new HashtableOfIntValues();

		public LocalDeclarationVisitor(MatchingNodeSet nodeSet) {
			this.nodeSet = nodeSet;
		}

		public boolean visit(TypeDeclaration typeDeclaration) {
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
				reportMatching(typeDeclaration, null, level != null ? level.intValue() : -1, nodeSet, occurrenceCount);
				return false; // don't visit members as this was done during
			} catch (CoreException e) {
				throw new WrappedCoreException(e);
			}
		}

		public boolean visit(MethodDeclaration method) {
			try {
				Integer level = (Integer) nodeSet.matchingNodes.removeKey(method);
				reportMatching(null, method, null, level != null ? level.intValue() : -1, nodeSet);
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
		IModelElement enclosingElement = null;
		if (accuracy > -1) {
			if (parent == null) {
				parent = createSourceModuleHandle();
			}
			enclosingElement = createHandle(method, parent);
			if (enclosingElement == null) {
				enclosingElement = createMethodHandle(method.getName());
			}
			if (enclosingElement != null) { // skip if unable to find method
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
		}

		// handle nodes for the local type first
		LocalDeclarationVisitor localDeclarationVisitor = new LocalDeclarationVisitor(nodeSet);
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
		IModelElement enclosingElement = null;
		if (accuracy > -1) {
			enclosingElement = createHandle(method, parent);
			if (enclosingElement != null) { // skip if unable to find method
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
		}
		
		// handle nodes for the local type first
		LocalDeclarationVisitor localDeclarationVisitor = new LocalDeclarationVisitor(nodeSet);
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
}
