package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.goals.IteratorTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

public class IteratorTypeGoalEvaluator extends GoalEvaluator {

	private IEvaluatedType result;

	public IteratorTypeGoalEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		IteratorTypeGoal typedGoal = (IteratorTypeGoal) goal;
		return new IGoal[] { new ExpressionTypeGoal(goal.getContext(),
				typedGoal.getExpression()) };
	}

	public Object produceResult() {
		return result;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE) {
			if (result instanceof PHPClassType) {
				if (subgoal instanceof ExpressionTypeGoal) {
					ISourceModule sourceModule = ((ISourceModuleContext) subgoal
							.getContext()).getSourceModule();
					PHPClassType classType = (PHPClassType) result;
					List<IGoal> subGoals = new LinkedList<IGoal>();
					try {
						IType[] types = PHPModelUtils.getTypes(
								classType.getTypeName(), sourceModule, 0, null);
						for (IType type : types) {
							IType[] superTypes = PHPModelUtils.getSuperClasses(
									type, null);
							if (isImplementedIterator(superTypes)) {
								subGoals.add(new MethodElementReturnTypeGoal(
										subgoal.getContext(),
										new IType[] { type }, "current"));
								subGoals.add(new PHPDocMethodReturnTypeGoal(
										subgoal.getContext(),
										new IType[] { type }, "current"));
							}
						}
						return subGoals.toArray(new IGoal[subGoals.size()]);
					} catch (ModelException e) {
						if (DLTKCore.DEBUG) {
							e.printStackTrace();
						}
					}
				}
				MultiTypeType type = new MultiTypeType();
				type.addType((IEvaluatedType) result);
				this.result = type;
				return IGoal.NO_GOALS;
			}
			this.result = (IEvaluatedType) result;
		}
		return IGoal.NO_GOALS;
	}

	private boolean isImplementedIterator(IType[] superClasses) {
		if (superClasses == null)
			return false;
		for (IType superClass : superClasses) {
			if (superClass.getFullyQualifiedName().equalsIgnoreCase("Iterator")) {
				return true;
			}
		}
		return false;
	}

}