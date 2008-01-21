package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.Reference;

/**
 * Represents include, include_once, require and require_once expressions
 * <pre>e.g.<pre> include('myFile.php'),
 * include_once($myFile),
 * require($myClass->getFileName()),
 * require_once(A::FILE_NAME)
 */
public class Include extends Reference {

	public static final int IT_REQUIRE = 0;
	public static final int IT_REQUIRE_ONCE = 1;
	public static final int IT_INCLUDE = 2;
	public static final int IT_INCLUDE_ONCE = 3;

	private final Expression expr;
	private final int includeType;

	public Include(int start, int end, Expression expr, int type) {
		super(start, end);

		assert expr != null;
		this.expr = expr;
		this.includeType = type;
	}

	public String getType() {
		switch (getIncludeType()) {
			case IT_REQUIRE:
				return "require"; //$NON-NLS-1$
			case IT_REQUIRE_ONCE:
				return "require_once"; //$NON-NLS-1$
			case IT_INCLUDE:
				return "include"; //$NON-NLS-1$
			case IT_INCLUDE_ONCE:
				return "include_once"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			expr.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.INCLUDE;
	}

	public Expression getExpr() {
		return expr;
	}

	public int getIncludeType() {
		return includeType;
	}

	public String getStringRepresentation() {
		return getType();
	}
}
