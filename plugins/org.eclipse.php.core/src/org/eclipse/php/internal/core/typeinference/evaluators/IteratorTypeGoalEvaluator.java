package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.evaluators.phpdoc.PHPDocClassVariableEvaluator;
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

							if (subgoal.getContext() instanceof MethodContext) {

								MethodContext methodContext = (MethodContext) subgoal
										.getContext();

								if (isArrayType(methodContext, type)) {
									MultiTypeType mType = new MultiTypeType();
									mType.addType((IEvaluatedType) result);
									this.result = mType;
									return IGoal.NO_GOALS;
								}
							}

							if (isImplementedIterator(superTypes)) {
								subGoals.add(new MethodElementReturnTypeGoal(
										subgoal.getContext(),
										new IType[] { type }, "current")); //$NON-NLS-1$
								subGoals.add(new PHPDocMethodReturnTypeGoal(
										subgoal.getContext(),
										new IType[] { type }, "current")); //$NON-NLS-1$
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

	/**
	 * Check if IType is a typed array in the methodContext:
	 * 
	 * <pre>
	 *   /*
	 *    *  @param SomeClass[] $elements
	 *    *\/
	 *    public function foo(array $elements);
	 * </pre>
	 * 
	 * 
	 * @param methodContext
	 * @param type
	 * @return boolean
	 */
	private boolean isArrayType(MethodContext methodContext, IType type) {

		PHPMethodDeclaration methodDeclaration = (PHPMethodDeclaration) methodContext
				.getMethodNode();

		PHPDocBlock[] docBlocks = new PHPDocBlock[0];

		try {
			IModelElement element = methodContext.getSourceModule()
					.getElementAt(methodDeclaration.getNameStart());
			if (element instanceof IMethod) {
				IMethod method = (IMethod) element;
				if (method.getDeclaringType() != null) {
					docBlocks = PHPModelUtils.getTypeHierarchyMethodDoc(
							method.getDeclaringType(), method.getElementName(),
							true, null);
				} else {
					docBlocks = new PHPDocBlock[] { methodDeclaration
							.getPHPDoc() };
				}
			} else {
				docBlocks = new PHPDocBlock[] { methodDeclaration.getPHPDoc() };
			}

		} catch (CoreException e) {
		}

		if (docBlocks.length > 0) {
			for (int i = 0; i < docBlocks.length; i++) {
				PHPDocTag[] tags = docBlocks[i].getTags();
				for (int j = 0; j < tags.length; j++) {
					PHPDocTag tag = tags[j];
					if (tag.getTagKind() == PHPDocTag.PARAM) {
						SimpleReference[] refs = tag.getReferences();
						if (refs != null
								&& refs.length > 1
								&& refs[1]
										.getName()
										.equals(type.getElementName()
												+ PHPDocClassVariableEvaluator.BRACKETS)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	private boolean isImplementedIterator(IType[] superClasses) {
		if (superClasses == null)
			return false;
		for (IType superClass : superClasses) {
			if (superClass.getFullyQualifiedName().equalsIgnoreCase("Iterator")) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}

}