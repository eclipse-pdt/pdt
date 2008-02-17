package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.goals.FixedAnswerEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.typeinference.goals.ScalarGoal;

public class ScalarEvaluator extends FixedAnswerEvaluator {

	public ScalarEvaluator(IGoal goal, Scalar scalar) {
		super(goal, evaluateScalar(scalar));
	}

	public ScalarEvaluator(ScalarGoal goal) {
		super(goal, evaluateScalarType(goal.getScalarType()));
	}

	private static Object evaluateScalar(Scalar scalar) {
		int scalarType = scalar.getScalarType();
		return evaluateScalarType(scalarType);
	}

	private static Object evaluateScalarType(int scalarType) {
		int simpleType = SimpleType.TYPE_NONE;
		switch (scalarType) {
			case Scalar.TYPE_INT:
			case Scalar.TYPE_REAL:
				simpleType = SimpleType.TYPE_NUMBER;
				break;
			case Scalar.TYPE_STRING:
			case Scalar.TYPE_SYSTEM:
				simpleType = SimpleType.TYPE_STRING;
				break;
		}
		return new SimpleType(simpleType);
	}

}
