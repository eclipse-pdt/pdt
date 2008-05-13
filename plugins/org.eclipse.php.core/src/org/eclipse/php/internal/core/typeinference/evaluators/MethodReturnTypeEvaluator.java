package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.MethodReturnTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;

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

		final List<IGoal> subGoals = new ArrayList<IGoal>(methods.size());
		for (IMethod method : methods) {
			subGoals.add(new MethodElementReturnTypeGoal(method));
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
