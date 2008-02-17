package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;

public class FieldAccessEvaluator extends GoalEvaluator {

	private final static int STATE_INIT = 0;
	private final static int STATE_WAITING_RECEIVER = 1;
	private final static int STATE_GOT_RECEIVER = 2;
	private final static int STATE_WAITING_FIELD = 3;
	private final static int STATE_UNKNOWN = -1;

	private int state = STATE_INIT;
	private IEvaluatedType receiverType;
	private List<IEvaluatedType> result = new LinkedList<IEvaluatedType>();

	public FieldAccessEvaluator(IGoal goal) {
		super(goal);
	}

	private IGoal[] produceNextSubgoal(IGoal previousGoal, Object previousResult) {

		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		FieldAccess expression = (FieldAccess) typedGoal.getExpression();
		Expression receiver = expression.getDispatcher();
		Expression field = expression.getField();

		if (!(field instanceof SimpleReference)) {
			return null;
		}

		// just starting to evaluate method, evaluate method receiver first:
		if (state == STATE_INIT) {

			if (receiver == null) {
				state = STATE_GOT_RECEIVER;
			} else {
				state = STATE_WAITING_RECEIVER;
				return new IGoal[] { new ExpressionTypeGoal(goal.getContext(), receiver) };
			}
		}

		// receiver must been evaluated now:
		if (state == STATE_WAITING_RECEIVER) {
			receiverType = (IEvaluatedType) previousResult;
			if (receiverType == null) {
				state = STATE_UNKNOWN;
				return null;
			}
			state = STATE_GOT_RECEIVER;
		}

		// we've evaluated receiver, lets evaluate the method return type now:
		if (state == STATE_GOT_RECEIVER) {
			state = STATE_WAITING_FIELD;
			if (receiverType == UnknownType.INSTANCE) {
				receiverType = null;
			}

			if (receiverType instanceof PHPClassType) {
				return new IGoal[] { new ClassVariableDeclarationGoal(new InstanceContext((ISourceModuleContext) goal.getContext(), receiverType), (SimpleReference) field) };
			}
			if (receiverType instanceof AmbiguousType) {
				List<IGoal> subGoals = new LinkedList<IGoal>();
				AmbiguousType ambiguousType = (AmbiguousType) receiverType;
				for (IEvaluatedType type : ambiguousType.getPossibleTypes()) {
					if (type instanceof PHPClassType) {
						subGoals.add(new ClassVariableDeclarationGoal(new InstanceContext((ISourceModuleContext) goal.getContext(), type), (SimpleReference) field));
					}
				}
				return subGoals.toArray(new IGoal[subGoals.size()]);
			}
		}
		if (state == STATE_WAITING_FIELD) {
			result.add((IEvaluatedType) previousResult);
		}

		return null;
	}

	public Object produceResult() {
		if (state == STATE_UNKNOWN) {
			return null;
		} else {
			return PHPTypeInferenceUtils.combineTypes(result);
		}
	}

	public IGoal[] init() {
		IGoal[] goals = produceNextSubgoal(null, null);
		if (goals != null) {
			return goals;
		}
		return IGoal.NO_GOALS;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		IGoal[] goals = produceNextSubgoal(subgoal, result);
		if (goals != null) {
			return goals;
		}
		return IGoal.NO_GOALS;
	}
}
