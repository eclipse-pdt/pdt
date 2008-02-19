package org.eclipse.php.internal.core.typeinference.evaluators.phpdoc;

import java.util.*;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.mixin.DocMember;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocClassVariableGoal;

/**
 * This evaluator finds class field declartion either using "var" or in method body using field access.
 */
public class PHPDocClassVariableEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public PHPDocClassVariableEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		PHPDocClassVariableGoal typedGoal = (PHPDocClassVariableGoal) goal;
		InstanceContext context = (InstanceContext) typedGoal.getContext();
		String variableName = typedGoal.getVariableName();

		List<IGoal> subGoals = new LinkedList<IGoal>();

		IEvaluatedType instanceType = context.getInstanceType();

		Set<DocMember> docs = new HashSet<DocMember>();

		if (instanceType instanceof PHPClassType) {
			PHPClassType classType = (PHPClassType) instanceType;
			IModelElement[] elements = PHPMixinModel.getInstance().getVariableDoc(variableName, null, classType.getTypeName());
			for (IModelElement e : elements) {
				docs.add((DocMember) e);
			}
		} else if (instanceType instanceof AmbiguousType) {
			AmbiguousType ambiguousType = (AmbiguousType) instanceType;
			for (IEvaluatedType doc : ambiguousType.getPossibleTypes()) {
				if (doc instanceof PHPClassType) {
					PHPClassType classType = (PHPClassType) instanceType;
					IModelElement[] elements = PHPMixinModel.getInstance().getVariableDoc(variableName, null, classType.getTypeName());
					for (IModelElement e : elements) {
						docs.add((DocMember) e);
					}
				}
			}
		}

		DocMember docFromSameFile = null;
		for (DocMember doc : docs) {
			if (doc.getSourceModule().equals(context.getSourceModule())) {
				docFromSameFile = doc;
				break;
			}
		}
		// If doc from the same file was found  - use it
		if (docFromSameFile != null) {
			docs.clear();
			docs.add(docFromSameFile);
		}

		for (DocMember doc : docs) {
			PHPDocBlock docBlock = doc.getDocBlock();
			for (PHPDocTag tag : docBlock.getTags()) {
				if (tag.getTagKind() == PHPDocTag.VAR) {
					SimpleReference[] references = tag.getReferences();
					if (references.length > 0) {
						SimpleReference firstReference = references[0];
						if (firstReference instanceof TypeReference) {
							IEvaluatedType type = PHPSimpleTypes.fromString(firstReference.getName());
							if (type == null) {
								type = new PHPClassType(firstReference.getName());
							}
							evaluated.add(type);
						}
					}
				}
			}
		}

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		evaluated.add((IEvaluatedType) result);
		return IGoal.NO_GOALS;
	}
}
