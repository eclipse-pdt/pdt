package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;

public class PHPDocTag extends ASTNode {

	private final int tagKind;
	private ASTNode value;

	public PHPDocTag(int start, int end, int tagKind, ASTNode value) {
		super(start, end);
		this.tagKind = tagKind;
		this.value = value;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if(visit){
			value.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getTagKind() {
		return this.tagKind;
	}

	public ASTNode getValue() {
		return value;
	}
}
