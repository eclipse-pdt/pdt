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
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.MethodReturnTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;

public class MethodReturnTypeEvaluator extends GoalEvaluator {

	private final List<ASTNode> possibilities = new LinkedList<ASTNode>();
	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();
	private IEvaluatedType rdocResult = null;
	private MethodContext innerContext;

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
		if (rdocResult != null) {
			return rdocResult;
		}
		if (!evaluated.isEmpty()) {
			return PHPTypeInferenceUtils.combineTypes(evaluated);
		}
		return null;
	}

	public IGoal[] init() {
		MethodReturnTypeGoal typedGoal = getTypedGoal();
		InstanceContext typedContext = getTypedContext();

		IEvaluatedType instanceType = typedContext.getInstanceType();
		if (instanceType instanceof AmbiguousType) {
			instanceType = ((AmbiguousType) instanceType).getPossibleTypes()[0];
		}

		String methodName = typedGoal.getMethodName();

		MethodDeclaration decl = null;
		List<IMethod> methods = new ArrayList<IMethod>();

		if (instanceType instanceof PHPClassType) {
			PHPClassType classType = (PHPClassType) instanceType;
			IModelElement[] elements = PHPMixinModel.getInstance().getMethod(classType.getTypeName(), methodName);
			for (IModelElement e : elements) {
				methods.add((IMethod) e);
			}
		} else {
			IModelElement[] elements = PHPMixinModel.getInstance().getFunction(methodName);
			for (IModelElement e : elements) {
				methods.add((IMethod) e);
			}
		}

		IMethod resultMethod = null;
		// in case of ambiguity, prefer methods from the same module
		IMethod resultMethodFromSameModule = null;
		for (Object element : methods) {
			IMethod method = (IMethod) element;
			if (method == null) {
				continue;
			}
			String elementName = method.getElementName();
			if (elementName.equals(methodName)) {
				if (method.getSourceModule().equals(typedContext.getSourceModule())) {
					resultMethodFromSameModule = method;
				}
				resultMethod = method;
			}
		}
		if (resultMethodFromSameModule != null) {
			resultMethod = resultMethodFromSameModule;
		}
		if (resultMethod == null) {
			return IGoal.NO_GOALS;
		}

		ISourceModule sourceModule = resultMethod.getSourceModule();
		ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule, null);
		try {
			decl = PHPModelUtils.getNodeByMethod(module, resultMethod);
		} catch (ModelException e) {
			Logger.logException(e);
		}

		String[] parameters;
		try {
			parameters = resultMethod.getParameters();
		} catch (ModelException e) {
			Logger.logException(e);
			parameters = new String[0];
		}

		innerContext = new MethodContext(goal.getContext(), sourceModule, module, decl, parameters, typedGoal.getArguments());

		ASTVisitor visitor = new ASTVisitor() {
			public boolean visitGeneral(ASTNode node) throws Exception {
				if (node instanceof ReturnStatement) {
					ReturnStatement statement = (ReturnStatement) node;
					Expression expr = statement.getExpr();
					if (expr == null) {
						evaluated.add(new PHPClassType("void"));
					} else {
						possibilities.add(expr);
					}
				}
				return super.visitGeneral(node);
			}
		};

		if (decl != null) {
			try {
				decl.traverse(visitor);
			} catch (Exception e) {
				Logger.logException(e);
			}
			if (decl.getBody() != null) {
				possibilities.add(decl.getBody());
			}
		}

		IGoal[] newGoals = new IGoal[possibilities.size()];
		int i = 0;
		for (Object element : possibilities) {
			ASTNode st = (ASTNode) element;
			ExpressionTypeGoal subgoal = new ExpressionTypeGoal(innerContext, st);
			newGoals[i++] = subgoal;
		}
		return newGoals;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

}
