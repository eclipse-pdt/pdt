package org.eclipse.php.internal.core.codeassist;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;

public class AliasType extends SourceType {
	private String alias;
	IType type;

	public AliasType(ModelElement sourceModule, String name, String alias) {
		super((ModelElement) sourceModule.getParent(), name);
		this.alias = alias;
		this.type = (IType) sourceModule;
	}

	@Override
	public ISourceRange getSourceRange() throws ModelException {
		return ((IType) type).getSourceRange();
	}

	@Override
	public int getFlags() throws ModelException {
		return ((IType) type).getFlags();
	}

	public String getAlias() {
		return alias;
	}

	public IModelElement getType() {
		return type;
	}
}
