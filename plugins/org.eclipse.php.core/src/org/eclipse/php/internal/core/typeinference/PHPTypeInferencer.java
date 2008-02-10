package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ti.DefaultTypeInferencer;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPGoalEvaluatorFactory;

public class PHPTypeInferencer extends DefaultTypeInferencer {

	public PHPTypeInferencer() {
		super(new PHPGoalEvaluatorFactory());
	}


}
