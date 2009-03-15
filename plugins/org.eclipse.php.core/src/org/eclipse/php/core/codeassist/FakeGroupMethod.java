package org.eclipse.php.core.codeassist;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceMethod;
import org.eclipse.dltk.internal.core.SourceRange;

public class FakeGroupMethod extends SourceMethod {
	
	public FakeGroupMethod(ModelElement parent, String name) {
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
}
