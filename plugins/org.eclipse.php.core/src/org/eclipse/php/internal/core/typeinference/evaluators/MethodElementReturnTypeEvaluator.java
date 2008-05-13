package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;

public class MethodElementReturnTypeEvaluator extends AbstractPHPGoalEvaluator {

	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();
	
	public MethodElementReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}
	
	public IGoal[] init() {
		
		MethodElementReturnTypeGoal goal = (MethodElementReturnTypeGoal) getGoal();
		IMethod method = goal.getMethod();

		final List<IGoal> subGoals = new LinkedList<IGoal>();

			ISourceModule sourceModule = method.getSourceModule();
			ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule, null);
			MethodDeclaration decl = null;

			try {
				decl = PHPModelUtils.getNodeByMethod(module, method);
			} catch (ModelException e) {
				Logger.logException(e);
			}

			if (decl != null) {
				String[] parameters;
				try {
					parameters = method.getParameters();
				} catch (ModelException e) {
					Logger.logException(e);
					parameters = new String[0];
				}
				
				final IContext innerContext = new MethodContext(goal.getContext(), sourceModule, module, decl, parameters, new IEvaluatedType[0]);

				ASTVisitor visitor = new ASTVisitor() {
					public boolean visitGeneral(ASTNode node) throws Exception {
						if (node instanceof ReturnStatement) {
							ReturnStatement statement = (ReturnStatement) node;
							Expression expr = statement.getExpr();
							if (expr == null) {
								evaluated.add(PHPSimpleTypes.VOID);
							} else {
								subGoals.add(new ExpressionTypeGoal(innerContext, expr));
							}
						}
						return super.visitGeneral(node);
					}
				};

				try {
					decl.traverse(visitor);
				} catch (Exception e) {
					Logger.logException(e);
				}
				if (decl.getBody() != null) {
					subGoals.add(new ExpressionTypeGoal(innerContext, decl.getBody()));
				}
			}

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}
	
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}
	
	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}
}
