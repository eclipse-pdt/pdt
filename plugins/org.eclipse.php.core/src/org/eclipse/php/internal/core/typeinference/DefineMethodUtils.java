package org.eclipse.php.internal.core.typeinference;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;

public class DefineMethodUtils {

	public static String DEFINE = "define"; //$NON-NLS-1$

	public static PHPCallExpression getDefineNodeByField(
			ModuleDeclaration module, IField field) throws ModelException {
		FunctionInvocationSearcher visitor = new FunctionInvocationSearcher(
				module, field);
		try {
			module.traverse(visitor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
		}
		return visitor.getResult();
	}

	public static PHPDocBlock getDefinePHPDocBlockByField(
			ModuleDeclaration module, IField field) throws ModelException {
		if (module instanceof PHPModuleDeclaration) {
			if (getDefineNodeByField(module, field) == null) {
				return null;
			}
			PHPModuleDeclaration phpModule = (PHPModuleDeclaration) module;
			List<PHPDocBlock> phpDocBlocks = phpModule.getPhpDocBlocks();
			if (phpDocBlocks != null && !phpDocBlocks.isEmpty()) {
				List statements = phpModule.getStatements();
				ISourceRange sourceRange = field.getNameRange();
				ASTNode previousStatement = null;
				for (Iterator iterator = statements.iterator(); iterator
						.hasNext();) {
					ASTNode statement = (ASTNode) iterator.next();
					if (statement.sourceStart() <= sourceRange.getOffset()
							&& statement.sourceEnd() >= (sourceRange
									.getOffset() + sourceRange.getLength())) {
						// define statement
						phpDocBlocks = getPHPDocBlockBetweenStatements(
								previousStatement, statement, phpDocBlocks);
						if (phpDocBlocks.isEmpty()) {
							return null;
						}
						Collections.sort(phpDocBlocks,
								new Comparator<PHPDocBlock>() {
									public int compare(PHPDocBlock o1,
											PHPDocBlock o2) {
										return o1.sourceStart()
												- o2.sourceStart();
									}
								});
						return phpDocBlocks.get(phpDocBlocks.size() - 1);
					}
					previousStatement = statement;
				}
				PHPCallExpression callExpression = getDefineNodeByField(
						phpModule, field);
				callExpression.getReceiver();
			}
		}
		return null;
	}

	private static List<PHPDocBlock> getPHPDocBlockBetweenStatements(
			ASTNode previousStatement, ASTNode statement,
			List<PHPDocBlock> phpDocBlocks) {
		if (previousStatement == null) {
			return getPHPDocBlockBetweenRange(-1, statement.sourceStart(),
					phpDocBlocks);
		} else {
			return getPHPDocBlockBetweenRange(previousStatement.sourceEnd(),
					statement.sourceStart(), phpDocBlocks);
		}
	}

	private static List<PHPDocBlock> getPHPDocBlockBetweenRange(int start,
			int end, List<PHPDocBlock> phpDocBlocks) {
		List<PHPDocBlock> result = new ArrayList<PHPDocBlock>();
		for (Iterator iterator = phpDocBlocks.iterator(); iterator.hasNext();) {
			PHPDocBlock phpDocBlock = (PHPDocBlock) iterator.next();
			if (phpDocBlock.sourceStart() >= start
					&& phpDocBlock.sourceEnd() <= end) {
				result.add(phpDocBlock);
			}
		}
		return result;
	}

	public static class FunctionInvocationSearcher extends ASTVisitor {

		private int bestScore = Integer.MAX_VALUE;
		private int modelStart;
		private int modelEnd;
		private int modelCutoffStart;
		private int modelCutoffEnd;
		private String elementName;
		private PHPCallExpression result;

		public FunctionInvocationSearcher(ModuleDeclaration moduleDeclaration,
				IMember modelElement) throws ModelException {
			ISourceRange sourceRange = modelElement.getSourceRange();
			modelStart = sourceRange.getOffset();
			modelEnd = modelStart + sourceRange.getLength();
			modelCutoffStart = modelStart - 100;
			modelCutoffEnd = modelEnd + 100;
			elementName = modelElement.getElementName();
		}

		public PHPCallExpression getResult() {
			return result;
		}

		protected void checkElementDeclaration(PHPCallExpression s) {
			if (s.getName().equals(DEFINE)) {
				CallArgumentsList args = s.getArgs();
				if (args != null && args.getChilds() != null) {
					ASTNode argument = (ASTNode) args.getChilds().get(0);
					if (argument instanceof Scalar) {
						String constant = ASTUtils
								.stripQuotes(((Scalar) argument).getValue());
						if (constant.equals(elementName)) {
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

				}
			}
		}

		protected boolean interesting(ASTNode s) {
			if (s.sourceStart() < 0 || s.sourceEnd() < s.sourceStart()) {
				return true;
			}
			if (modelCutoffEnd < s.sourceStart()
					|| modelCutoffStart >= s.sourceEnd()) {
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

		public boolean visit(Statement s) throws Exception {
			if (!interesting(s)) {
				return false;
			}
			if (s instanceof ExpressionStatement) {
				if (((ExpressionStatement) s).getExpr() instanceof PHPCallExpression) {
					checkElementDeclaration((PHPCallExpression) ((ExpressionStatement) s)
							.getExpr());

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

		public boolean visitGeneral(ASTNode s) throws Exception {
			if (!interesting(s)) {
				return false;
			}
			return true;
		}
	}
}
