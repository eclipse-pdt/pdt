/*******************************************************************************
 * Copyright (c) 2009, 2015, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Michele Locati
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.IInstanceContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.core.compiler.ast.nodes.StaticConstantAccess;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.FactoryMethodMethodReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

public class MethodCallTypeEvaluator extends GoalEvaluator {

	private final static int STATE_INIT = 0;
	private final static int STATE_WAITING_RECEIVER = 1;
	private final static int STATE_GOT_RECEIVER = 2;
	private final static int STATE_WAITING_FACTORYMETHOD = 3;
	private final static int STATE_WAITING_METHOD_PHPDOC = 4;
	private final static int STATE_WAITING_METHOD = 5;

	private int state = STATE_INIT;
	private IEvaluatedType receiverType;
	private IEvaluatedType result;
	private PHPVersion phpVersion;
	private boolean phpVersionResolved = false;

	public MethodCallTypeEvaluator(ExpressionTypeGoal goal) {
		super(goal);
	}

	private IGoal produceNextSubgoal(IGoal previousGoal, IEvaluatedType previousResult, GoalState goalState) {

		ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
		CallExpression expression = (CallExpression) typedGoal.getExpression();

		// just starting to evaluate method, evaluate method receiver first:
		if (state == STATE_INIT) {
			ASTNode receiver = expression.getReceiver();
			if (receiver == null) {
				state = STATE_GOT_RECEIVER;
			} else {
				state = STATE_WAITING_RECEIVER;
				return new ExpressionTypeGoal(goal.getContext(), receiver);
			}
		}

		// receiver must been evaluated now, lets evaluate method return type:
		if (state == STATE_WAITING_RECEIVER) {
			receiverType = previousResult;
			previousResult = null;
			if (receiverType == null) {
				return null;
			}
			state = STATE_GOT_RECEIVER;
		}

		// Check the result of factory method/PHPDoc logic
		if ((state == STATE_WAITING_FACTORYMETHOD || state == STATE_WAITING_METHOD_PHPDOC)
				&& goalState != GoalState.PRUNED && previousResult != null && previousResult != UnknownType.INSTANCE) {
			result = previousResult;
			previousResult = null;
			// BUGS 404031 & 525480, stop when it's not a "generic" simple element
			if (!PHPTypeInferenceUtils.isGenericSimple(result)) {
				return null;
			}
		}

		// First logic: check factory methods
		if (state == STATE_GOT_RECEIVER) {
			state = STATE_WAITING_FACTORYMETHOD;
			IContext context = typedGoal.getContext();
			return new FactoryMethodMethodReturnTypeGoal(context, receiverType, expression.getName(),
					getFunctionCallArgs(context, expression), expression.sourceStart());
		}

		// Second logic: check PHPDoc
		if (state == STATE_WAITING_FACTORYMETHOD) {
			state = STATE_WAITING_METHOD_PHPDOC;
			IContext context = typedGoal.getContext();
			return new PHPDocMethodReturnTypeGoal(context, receiverType, expression.getName(),
					getFunctionCallArgs(context, expression), expression.sourceStart());
		}

		// Third method: start evaluating 'return' statements here:
		if (state == STATE_WAITING_METHOD_PHPDOC) {
			if (goalState != GoalState.PRUNED && previousResult != null && previousResult != UnknownType.INSTANCE) {
				result = previousResult;
				previousResult = null;
				// BUGS 404031 & 525480, stop when it's not a "generic"
				// simple element
				if (!PHPTypeInferenceUtils.isGenericSimple(result)) {
					return null;
				}
			}
			state = STATE_WAITING_METHOD;
			IContext context = typedGoal.getContext();
			return new MethodElementReturnTypeGoal(context, receiverType, expression.getName(),
					getFunctionCallArgs(context, expression), expression.sourceStart());
		}

		if (state == STATE_WAITING_METHOD) {
			if (goalState != GoalState.PRUNED && previousResult != null && previousResult != UnknownType.INSTANCE) {
				if (result != null) {
					result = PHPTypeInferenceUtils
							.combineTypes(Arrays.asList(new IEvaluatedType[] { result, previousResult }));
				} else {
					result = previousResult;
					previousResult = null;
				}
			}
		}
		return null;
	}

	@Nullable
	private String[] getFunctionCallArgs(@Nullable IContext context, @NonNull CallExpression callExpression) {
		CallArgumentsList args = callExpression.getArgs();
		String[] argNames = null;
		if (args != null && args.getChilds() != null) {
			List<ASTNode> childs = args.getChilds();
			int i = 0;
			argNames = new String[childs.size()];
			for (ASTNode o : childs) {
				if (o instanceof Scalar) {
					Scalar arg = (Scalar) o;
					argNames[i] = ASTUtils.stripQuotes(arg.getValue());
				} else if (o instanceof StaticConstantAccess) {
					argNames[i] = this.getFunctionCallArg(context, (StaticConstantAccess) o);
				}
				i++;
			}
		}
		return argNames;
	}

	@Nullable
	private String getFunctionCallArg(@Nullable IContext context, @NonNull StaticConstantAccess argument) {
		if (!"class".equalsIgnoreCase(argument.getConstant().getName())) {
			return null;
		}
		PHPVersion phpVersion = this.getPHPVersion();
		if (phpVersion == null || phpVersion.isLessThan(PHPVersion.PHP5_5)) {
			return null;
		}
		if (!(context instanceof ISourceModuleContext)) {
			return null;
		}
		ISourceModule sourceModule = ((ISourceModuleContext) context).getSourceModule();
		if (sourceModule == null) {
			return null;
		}
		Expression dispatcher = argument.getDispatcher();
		if (!(dispatcher instanceof FullyQualifiedReference)) {
			return null;
		}
		String localClassName = ((FullyQualifiedReference) dispatcher).getFullyQualifiedName();
		if (localClassName.equalsIgnoreCase("self") || localClassName.equalsIgnoreCase("static")) { //$NON-NLS-1$ //$NON-NLS-2$
			if (!(context instanceof IInstanceContext)) {
				return null;
			}
			IEvaluatedType currentClass = ((IInstanceContext) context).getInstanceType();
			if (!(currentClass instanceof PHPClassType)) {
				return null;
			}
			String fullyQualifiedName = currentClass.getTypeName();
			return fullyQualifiedName.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR
					? fullyQualifiedName.substring(1)
					: fullyQualifiedName;
		}
		return PHPModelUtils.getFullName(localClassName, sourceModule, argument.sourceStart());
	}

	@Nullable
	private PHPVersion getPHPVersion() {
		if (this.phpVersionResolved) {
			return this.phpVersion;
		}
		IContext context = this.goal.getContext();
		if (context instanceof ISourceModuleContext) {
			ISourceModule sourceModule = ((ISourceModuleContext) context).getSourceModule();
			if (sourceModule != null) {
				this.phpVersion = ProjectOptions.getPHPVersion(sourceModule.getScriptProject().getProject());
			}
		}
		this.phpVersionResolved = true;
		return this.phpVersion;
	}

	@Override
	public Object produceResult() {
		return result;
	}

	@Override
	public IGoal[] init() {
		IGoal goal = produceNextSubgoal(null, null, null);
		if (goal != null) {
			return new IGoal[] { goal };
		}
		return IGoal.NO_GOALS;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		IGoal goal = produceNextSubgoal(subgoal, (IEvaluatedType) result, state);
		if (goal != null) {
			return new IGoal[] { goal };
		}
		return IGoal.NO_GOALS;
	}

}
