package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.Logger;

public class PHPModelUtils {

	public static MethodDeclaration getNodeByMethod(ModuleDeclaration rootNode, IMethod method) throws ModelException {

		ISourceRange sourceRange = method.getSourceRange();
		final int modelStart = sourceRange.getOffset();
		final int modelEnd = modelStart + sourceRange.getLength();
		final int modelCutoffStart = modelStart - 100;
		final int modelCutoffEnd = modelEnd + 100;
		final String methodName = method.getElementName();

		final MethodDeclaration[] bestResult = new MethodDeclaration[1];

		ASTVisitor visitor = new ASTVisitor() {

			int bestScore = Integer.MAX_VALUE;

			private boolean interesting(ASTNode s) {
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
				if (s.getName().equals(methodName)) {
					int astStart = s.sourceStart();
					int astEnd = s.sourceEnd();
					int diff1 = modelStart - astStart;
					int diff2 = modelEnd - astEnd;
					int score = diff1 * diff1 + diff2 * diff2;
					if (score < bestScore) {
						bestScore = score;
						bestResult[0] = s;
					}
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
				return true;
			}

			public boolean endvisit(TypeDeclaration s) throws Exception {
				if (!interesting(s)) {
					return false;
				}
				return false /* dummy */;
			}

			public boolean visitGeneral(ASTNode s) throws Exception {
				if (s instanceof Block) {
					return true;
				}
				if (!interesting(s)) {
					return false;
				}
				return true;
			}
		};

		try {
			rootNode.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return bestResult[0];
	}
}
