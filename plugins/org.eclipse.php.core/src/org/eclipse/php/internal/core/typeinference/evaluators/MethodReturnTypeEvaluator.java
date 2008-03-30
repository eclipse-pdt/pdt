package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.MethodReturnTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.*;

public class MethodReturnTypeEvaluator extends AbstractPHPGoalEvaluator {

	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public MethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	private MethodReturnTypeGoal getTypedGoal() {
		return (MethodReturnTypeGoal) this.getGoal();
	}

	private InstanceContext getTypedContext() {
		return (InstanceContext) this.getGoal().getContext();
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] init() {
		MethodReturnTypeGoal typedGoal = getTypedGoal();
		InstanceContext typedContext = getTypedContext();

		final Set<IMethod> methods = new HashSet<IMethod>();

		String methodName = typedGoal.getMethodName();
		IType[] types = getTypes(typedContext.getInstanceType(), typedContext.getSourceModule());

		if (types.length == 0) {
			IModelElement[] elements = PHPMixinModel.getInstance().getFunction(methodName);
			for (IModelElement e : elements) {
				methods.add((IMethod) e);
			}
		} else {
			for (IType type : types) {
				try {
					IModelElement[] elements = PHPMixinModel.getInstance().getMethod(type.getElementName(), methodName);
					if (elements.length == 0) {
						elements = PHPModelUtils.getClassMethod(type, methodName, null);
					}
					for (IModelElement e : elements) {
						methods.add((IMethod) e);
					}
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}
		}

		final List<IGoal> subGoals = new LinkedList<IGoal>();

		for (IMethod method : methods) {
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

				final IContext innerContext = new MethodContext(goal.getContext(), sourceModule, module, decl, parameters, typedGoal.getArguments());

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
		}

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

}
