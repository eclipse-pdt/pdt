package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.php.internal.core.model.PhpModelAccess;

public class AliasType extends SourceType {
	private String alias;
	private String fullyQualifiedName;
	private boolean resolved = false;
	private IDLTKSearchScope scope;
	private IType realType;

	// public AliasType(ModelElement sourceModule, String name, String alias) {
	// super(sourceModule, name);
	// this.alias = alias;
	// }

	public AliasType(IDLTKSearchScope scope, ModelElement sourceModule,
			String fullyQualifiedName, String alias) {
		super(sourceModule, alias);
		this.scope = scope;
		this.alias = alias;
		this.fullyQualifiedName = fullyQualifiedName;
	}

	@Override
	public ISourceRange getSourceRange() throws ModelException {
		if (realType != null) {
			return realType.getSourceRange();
		} else {
			return new SourceRange(0, alias.length());
		}

	}

	@Override
	public int getFlags() throws ModelException {
		if (realType != null) {
			return realType.getFlags();
		} else {
			return 0;
		}
	}

	public IType resolve() {
		if (!resolved) {
			resolved = true;
			IType[] namespaces = PhpModelAccess.getDefault().findTypes(
					fullyQualifiedName, MatchRule.EXACT, 0, 0, scope, null);
			if (namespaces != null && namespaces.length > 0) {
				realType = namespaces[0];
				// this.parent = (ModelElement) namespaces[0].getParent();
				// this.name = namespaces[0].getElementName();
			}
		}
		if (realType == null) {
			return this;
		}

		return realType;
	}

	public boolean isResolved() {
		return resolved;
	}

	public String getAlias() {
		return alias;
	}
}
