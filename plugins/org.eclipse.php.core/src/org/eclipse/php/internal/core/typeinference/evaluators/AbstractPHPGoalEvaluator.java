package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

public abstract class AbstractPHPGoalEvaluator extends GoalEvaluator {

	public AbstractPHPGoalEvaluator(IGoal goal) {
		super(goal);
	}

	/**
	 * Returns all model elements for the given class type. If current file is not <code>null</code> this method tries to find
	 * only type declared in the very file.
	 * @param instanceType Evaluated type of the class
	 * @param currentModule Current file module
	 * @return
	 */
	protected IType[] getTypes(IEvaluatedType instanceType, ISourceModule currentModule) {
		Set<IType> types = new HashSet<IType>();

		if (instanceType instanceof PHPClassType) {
			PHPClassType classType = (PHPClassType) instanceType;
			IModelElement[] elements = PHPMixinModel.getInstance().getClass(classType.getTypeName());
			for (IModelElement e : elements) {
				types.add((IType) e);
			}
		} else if (instanceType instanceof AmbiguousType) {
			AmbiguousType ambiguousType = (AmbiguousType) instanceType;
			for (IEvaluatedType type : ambiguousType.getPossibleTypes()) {
				if (type instanceof PHPClassType) {
					PHPClassType classType = (PHPClassType) type;
					IModelElement[] elements = PHPMixinModel.getInstance().getClass(classType.getTypeName());
					for (IModelElement e : elements) {
						types.add((IType) e);
					}
				}
			}
		}

		if (currentModule != null) {
			IType typeFromSameFile = null;
			for (IType type : types) {
				if (type.getSourceModule().equals(currentModule)) {
					typeFromSameFile = type;
					break;
				}
			}
			// If type from the same file was found  - use it
			if (typeFromSameFile != null) {
				return new IType[] { typeFromSameFile };
			}
		}

		return types.toArray(new IType[types.size()]);
	}
}
