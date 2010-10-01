package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.ast.nodes.UseStatementPart;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;

public class AbstractSemanticApplyWithNS extends AbstractSemanticApply {
	private ISourceModule sourceModule = null;
	protected NamespaceDeclaration fCurrentNamespace;
	protected Map<String, UseStatementPart> fLastUseParts = new HashMap<String, UseStatementPart>();

	public AbstractSemanticApplyWithNS(ISourceModule sourceModule) {
		this.sourceModule = sourceModule;
	}

	/**
	 * @param identifier
	 */
	// private void dealIdentifier(Identifier identifier) {
	// String fullName = AbstractOccurrencesFinder.getFullName(identifier,
	// fLastUseParts, fCurrentNamespace);
	// IType[] types = PhpModelAccess.getDefault().findTypes(fullName,
	// MatchRule.EXACT, 0, 0, createSearchScope(), null);
	// if (types != null && types.length == 1 && types[0] != null) {
	// if (ModelUtils.isExternalElement(types[0])) {
	// highlight(identifier);
	// }
	// }
	// // nodeToFullName.put(identifier, fullName);
	// }

	/**
	 * Creates search scope
	 */
	protected IDLTKSearchScope createSearchScope() {
		// ISourceModule sourceModule = ((AbstractCompletionContext)
		// context)
		// .getSourceModule();
		IScriptProject scriptProject = sourceModule.getScriptProject();
		if (scriptProject != null) {
			return SearchEngine.createSearchScope(scriptProject);
		}
		IProjectFragment projectFragment = (IProjectFragment) sourceModule
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		if (projectFragment != null) {
			return SearchEngine.createSearchScope(projectFragment);
		}
		// XXX: add language model here
		return SearchEngine.createSearchScope(sourceModule);
	}

	public boolean visit(UseStatement useStatement) {
		List<UseStatementPart> useParts = useStatement.parts();
		for (UseStatementPart part : useParts) {
			String name = null;
			if (part.getAlias() != null) {
				name = part.getAlias().getName();
			} else {
				name = part.getName().getName();
				int index = name
						.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
				if (index >= 0) {
					name = name.substring(index + 1);
				}
			}
			fLastUseParts.put(name, part);
		}
		return true;
	}

	public boolean visit(NamespaceDeclaration namespaceDeclaration) {
		fCurrentNamespace = namespaceDeclaration;
		fLastUseParts.clear();
		return true;
	}

	public void endVisit(NamespaceDeclaration namespaceDeclaration) {
		fCurrentNamespace = null;
		fLastUseParts.clear();
	}

}
