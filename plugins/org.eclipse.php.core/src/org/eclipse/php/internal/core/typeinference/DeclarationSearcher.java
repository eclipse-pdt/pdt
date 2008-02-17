package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;

class DeclarationSearcher extends ASTVisitor {

	enum DeclarationType { METHOD, CLASS };

	private int bestScore = Integer.MAX_VALUE;
	private int modelStart;
	private int modelEnd;
	private int modelCutoffStart;
	private int modelCutoffEnd;
	private String elementName;
	private Declaration result;
	private DeclarationType declarationType;

	public DeclarationSearcher(ModuleDeclaration moduleDeclaration, IMember modelElement, DeclarationType declarationType) throws ModelException {
		ISourceRange sourceRange = modelElement.getSourceRange();
		modelStart = sourceRange.getOffset();
		modelEnd = modelStart + sourceRange.getLength();
		modelCutoffStart = modelStart - 100;
		modelCutoffEnd = modelEnd + 100;
		elementName = modelElement.getElementName();

		this.declarationType = declarationType;
	}

	public Declaration getResult() {
		return result;
	}

	protected void checkElementDeclaration(Declaration s) {
		if (s.getName().equals(elementName)) {
			int astStart = s.sourceStart();
			int astEnd = s.sourceEnd();
			int diff1 = modelStart - astStart;
			int diff2 = modelEnd - astEnd;
			int score = diff1 * diff1 + diff2 * diff2;
			if (score < bestScore) {
				bestScore = score;
				result = s;
			}
		}
	}

	protected boolean interesting(ASTNode s) {
		if (s.sourceStart() < 0 || s.sourceEnd() < s.sourceStart()) {
			return true;
		}
		if (modelCutoffEnd < s.sourceStart() || modelCutoffStart >= s.sourceEnd()) {
			return false;
		}
		return true;
	}

	public boolean visit(Expression s) throws Exception {
		if (!interesting(s)) {
			return false;
		}
		return true;
	}

	public boolean visit(MethodDeclaration s) throws Exception {
		if (!interesting(s)) {
			return false;
		}
		if (declarationType == DeclarationType.METHOD) {
			checkElementDeclaration(s);
		}
		return true;
	}

	public boolean visit(ModuleDeclaration s) throws Exception {
		if (!interesting(s)) {
			return false;
		}
		return true;
	}

	public boolean visit(TypeDeclaration s) throws Exception {
		if (!interesting(s)) {
			return false;
		}
		if (declarationType == DeclarationType.CLASS) {
			checkElementDeclaration(s);
		}
		return true;
	}

	public boolean visitGeneral(ASTNode s) throws Exception {
		if (!interesting(s)) {
			return false;
		}
		return true;
	}
}