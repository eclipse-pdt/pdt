package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.MethodReturnTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.*;

public class MethodReturnTypeEvaluator extends GoalEvaluator {

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

		IEvaluatedType instanceType = typedContext.getInstanceType();

		String methodName = typedGoal.getMethodName();

		Set<IMethod> methods = new HashSet<IMethod>();

		if (instanceType instanceof PHPClassType) {
			PHPClassType classType = (PHPClassType) instanceType;
			IModelElement[] elements = PHPMixinModel.getInstance().getMethod(classType.getTypeName(), methodName);
			for (IModelElement e : elements) {
				methods.add((IMethod) e);
			}
		} else if (instanceType instanceof AmbiguousType) {
			AmbiguousType ambiguousType = (AmbiguousType) instanceType;
			for (IEvaluatedType type : ambiguousType.getPossibleTypes()) {
				if (type instanceof PHPClassType) {
					PHPClassType classType = (PHPClassType) instanceType;
					IModelElement[] elements = PHPMixinModel.getInstance().getMethod(classType.getTypeName(), methodName);
					for (IModelElement e : elements) {
						methods.add((IMethod) e);
					}
				}
			}
		} else {
			IModelElement[] elements = PHPMixinModel.getInstance().getFunction(methodName);
			for (IModelElement e : elements) {
				methods.add((IMethod) e);
			}
		}

		IMethod methodFromSameFile = null;
		for (IMethod method : methods) {
			if (method.getSourceModule().equals(typedContext.getSourceModule())) {
				methodFromSameFile = method;
				break;
			}
		}
		// If method from the same file was found  - use it
		if (methodFromSameFile != null) {
			methods.clear();
			methods.add(methodFromSameFile);
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
		if (result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

}
