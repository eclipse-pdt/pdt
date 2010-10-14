package org.eclipse.php.internal.core.codeassist;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;

public class AliasType extends SourceType {
	private String alias;

	public AliasType(ModelElement sourceModule, String name, String alias) {
		super(sourceModule, name);
		this.alias = alias;
	}

	@Override
	public ISourceRange getSourceRange() throws ModelException {
		return ((IType) parent).getSourceRange();
	}

	@Override
	public int getFlags() throws ModelException {
		return ((IType) parent).getFlags();
	}

	public String getAlias() {
		return alias;
	}
}
