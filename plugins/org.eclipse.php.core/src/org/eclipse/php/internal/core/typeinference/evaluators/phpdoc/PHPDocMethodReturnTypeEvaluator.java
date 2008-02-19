package org.eclipse.php.internal.core.typeinference.evaluators.phpdoc;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.mixin.DocMember;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
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

		Set<DocMember> docs = new HashSet<DocMember>();

		if (instanceType instanceof PHPClassType) {
			PHPClassType classType = (PHPClassType) instanceType;
			IModelElement[] elements = PHPMixinModel.getInstance().getMethodDoc(classType.getTypeName(), methodName);
			for (IModelElement e : elements) {
				docs.add((DocMember) e);
			}
		}
		else if (instanceType instanceof AmbiguousType) {
			AmbiguousType ambiguousType = (AmbiguousType) instanceType;
			for (IEvaluatedType type : ambiguousType.getPossibleTypes()) {
				if (type instanceof PHPClassType) {
					PHPClassType classType = (PHPClassType) instanceType;
					IModelElement[] elements = PHPMixinModel.getInstance().getMethodDoc(classType.getTypeName(), methodName);
					for (IModelElement e : elements) {
						docs.add((DocMember) e);
					}
				}
			}
		}
		else {
			IModelElement[] elements = PHPMixinModel.getInstance().getFunctionDoc(methodName);
			for (IModelElement e : elements) {
				docs.add((DocMember) e);
			}
		}

		DocMember docFromSameFile = null;
		for (DocMember doc : docs) {
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

		for (DocMember doc : docs) {
			PHPDocBlock docBlock = doc.getDocBlock();
			for (PHPDocTag tag : docBlock.getTags()) {
				if (tag.getTagKind() == PHPDocTag.RETURN) {
					SimpleReference[] references = tag.getReferences();
					if (references.length > 0) {
						SimpleReference firstReference = references[0];
						if (firstReference instanceof TypeReference) {
							evaluated.add(new PHPClassType(firstReference.getName()));
						}
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
