package org.eclipse.php.internal.core.mixin;

import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;

/**
 * Fake member that represents PHPDoc
 */
public class DocMember extends SourceField {

	public static final String NAME = "__phpdoc__"; //$NON-NLS-1$
	private PHPDocBlock docBlock;

	public DocMember(ModelElement parent, PHPDocBlock docBlock) {
		super(parent, NAME);
		this.docBlock = docBlock;
	}

	public PHPDocBlock getDocBlock() {
		return docBlock;
	}
}
