package org.eclipse.php.internal.core.typeinference.evaluators.phpdoc;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.mixin.PHPDocField;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

public class PHPDocMethodReturnTypeEvaluator extends GoalEvaluator {

	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public PHPDocMethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		PHPDocMethodReturnTypeGoal typedGoal = (PHPDocMethodReturnTypeGoal) goal;
		IContext context = goal.getContext();
		String methodName = typedGoal.getMethodName();

		InstanceContext typedGontext = (InstanceContext) context;
		IEvaluatedType instanceType = typedGontext.getInstanceType();

		Set<PHPDocField> docs = new HashSet<PHPDocField>();

		if (instanceType instanceof PHPClassType || instanceType instanceof AmbiguousType) {

			List<IType> types = new LinkedList<IType>();
			if (instanceType instanceof AmbiguousType) {
				AmbiguousType ambiguousType = (AmbiguousType) instanceType;
				for (IEvaluatedType type : ambiguousType.getPossibleTypes()) {
					if (type instanceof PHPClassType) {
						PHPClassType classType = (PHPClassType) type;
						IModelElement[] classes = PHPMixinModel.getInstance().getClass(classType.getTypeName());
						for (IModelElement c : classes) {
							types.add((IType) c);
						}
					}
				}
			} else {
				PHPClassType classType = (PHPClassType) instanceType;
				IModelElement[] classes = PHPMixinModel.getInstance().getClass(classType.getTypeName());
				for (IModelElement c : classes) {
					types.add((IType) c);
				}
			}

			for (IType type : types) {
				try {
					for (PHPDocField doc : PHPModelUtils.getClassMethodDoc(type, methodName, null)) {
						docs.add(doc);
					}
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}
		} else {
			IModelElement[] elements = PHPMixinModel.getInstance().getFunctionDoc(methodName);
			for (IModelElement e : elements) {
				docs.add((PHPDocField) e);
			}
		}

		PHPDocField docFromSameFile = null;
		for (PHPDocField doc : docs) {
			if (doc.getSourceModule().equals(typedGontext.getSourceModule())) {
				docFromSameFile = doc;
				break;
			}
		}
		// If doc from the same file was found  - use it
		if (docFromSameFile != null) {
			docs.clear();
			docs.add(docFromSameFile);
		}

		for (PHPDocField doc : docs) {
			PHPDocBlock docBlock = doc.getDocBlock();
			for (PHPDocTag tag : docBlock.getTags()) {
				if (tag.getTagKind() == PHPDocTag.RETURN) {
					SimpleReference[] references = tag.getReferences();
					if (references.length == 1) {
						IEvaluatedType type = PHPSimpleTypes.fromString(references[0].getName());
						if (type == null) {
							type = new PHPClassType(references[0].getName());
						}
						evaluated.add(type);
					}
				}
			}
		}

		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
