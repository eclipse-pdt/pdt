package org.eclipse.php.core.codeassist;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.dltk.internal.core.SourceType;

public class FakeGroupType extends SourceType {

	public FakeGroupType(ModelElement parent, String name) {
		super(parent, name);
	}

	public int getFlags() throws ModelException {
		return Modifiers.AccPublic;
	}

	public ISourceRange getSourceRange() throws ModelException {
		return new SourceRange(0, 0);
	}

	public boolean exists() {
		return false;
	}

	public IField[] getFields() throws ModelException {
		return new IField[0];
	}

	public IMethod[] getMethods() throws ModelException {
		return new IMethod[0];
	}

	public IType[] getTypes() throws ModelException {
		return new IType[0];
	}
}
