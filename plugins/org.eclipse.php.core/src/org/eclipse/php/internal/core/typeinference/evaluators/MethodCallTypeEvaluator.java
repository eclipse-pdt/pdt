/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Michele Locati <michele@locati.it>
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
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
import org.eclipse.php.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.core.compiler.ast.nodes.StaticConstantAccess;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.FactoryMethodReturnTypeGoal;
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

		// we've evaluated receiver, lets evaluate the method return type now

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
			String[] stringArguments = this.getMethodStringArguments(expression, typedGoal.getContext());
			if (stringArguments != null) {
				return new FactoryMethodReturnTypeGoal(typedGoal.getContext(), receiverType, expression.getName(),
						expression.sourceStart(), stringArguments);
			}
			// No string arguments: skip to PHPDoc logic
		}

		// Second logic: check PHPDoc
		if (state == STATE_WAITING_FACTORYMETHOD) {
			state = STATE_WAITING_METHOD_PHPDOC;
			return new PHPDocMethodReturnTypeGoal(typedGoal.getContext(), receiverType, expression.getName(),
					getFunctionCallArgs(expression), expression.sourceStart());
		}

		// Third method: start evaluating 'return' statements here:
		if (state == STATE_WAITING_METHOD_PHPDOC) {
			state = STATE_WAITING_METHOD;
			return new MethodElementReturnTypeGoal(typedGoal.getContext(), receiverType, expression.getName(),
					getFunctionCallArgs(expression), expression.sourceStart());
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

	private String[] getFunctionCallArgs(CallExpression callExpression) {
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
				}
				i++;
			}
		}
		return argNames;
	}

	/**
	 * Get the string arguments of a CallExpression
	 * 
	 * @param callExpression
	 * @param context
	 * @return Return NULL if no string argument has been found, an array of String
	 *         and NULL values otherwise
	 */
	private String[] getMethodStringArguments(CallExpression callExpression, IContext context) {
		CallArgumentsList argumentsContainer = callExpression.getArgs();
		List<ASTNode> arguments = argumentsContainer == null ? null : argumentsContainer.getChilds();
		int numArguments = arguments == null ? null : arguments.size();
		if (numArguments == 0) {
			return null;
		}
		String[] stringArguments = null;
		for (int argumentIndex = 0; argumentIndex < numArguments; argumentIndex++) {
			@SuppressWarnings("null")
			ASTNode argument = arguments.get(argumentIndex);
			String stringArgument = null;
			if (argument instanceof Scalar) {
				stringArgument = this.getMethodStringArgument((Scalar) argument, context);
			} else if (argument instanceof StaticConstantAccess) {
				stringArgument = this.getMethodStringArgument((StaticConstantAccess) argument, context);
			}
			if (stringArgument == null) {
				continue;
			}
			if (stringArguments == null) {
				stringArguments = new String[numArguments];
			}
			stringArguments[argumentIndex] = stringArgument;
		}
		return stringArguments;
	}

	/**
	 * Extract the string contained in a Scalar argument.
	 * 
	 * @param argument
	 *            The argument to be parsed
	 * @param factoryMethodFlags
	 * @return NULL if the argument can't be resolved to a non-empty string
	 */
	private String getMethodStringArgument(Scalar argument, IContext context) {
		if (argument.getScalarType() != Scalar.TYPE_STRING) {
			return null;
		}
		String value = argument.getValue();
		if (value == null || value.length() == 0) {
			return null;
		}

		return ASTUtils.stripQuotes(value);
	}

	/**
	 * Extract the class name from an argument like "ClassName::class"
	 * 
	 * @param argument
	 *            The argument to be parsed
	 * @return NULL if the argument is not a ..::class call, a fully-qualified class
	 *         name otherwise (without the leading '\')
	 */
	private String getMethodStringArgument(StaticConstantAccess argument, IContext context) {
		ConstantReference constantReference = argument.getConstant();
		if (constantReference == null) {
			return null;
		}
		String constantName = constantReference.getName();
		if (constantName == null || !"class".equalsIgnoreCase(constantName)) { //$NON-NLS-1$
			return null;
		}
		Expression dispatcher = argument.getDispatcher();
		if (!(dispatcher instanceof FullyQualifiedReference)) {
			return null;
		}
		String localClassName = ((FullyQualifiedReference) dispatcher).getFullyQualifiedName();
		if (localClassName == null || localClassName.length() == 0) {
			return null;
		}
		if (localClassName.equalsIgnoreCase("self") || localClassName.equalsIgnoreCase("static")) { //$NON-NLS-1$ //$NON-NLS-2$
			if (!(context instanceof IInstanceContext)) {
				return null;
			}
			IEvaluatedType currentClass = ((IInstanceContext) context).getInstanceType();
			if (!(currentClass instanceof PHPClassType)) {
				return null;
			}
			String fullyQualifiedName = currentClass.getTypeName();
			if (fullyQualifiedName == null) {
				return null;
			}
			int len = fullyQualifiedName.length();
			if (len == 0) {
				return null;
			}
			if (fullyQualifiedName.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
				return len == 1 ? null : fullyQualifiedName.substring(1);
			}
			return fullyQualifiedName;
		}
		if (!(context instanceof ISourceModuleContext)) {
			return null;
		}
		ISourceModule sourceModule = ((ISourceModuleContext) context).getSourceModule();
		if (sourceModule == null) {
			return null;
		}
		String fullyQualifiedName = PHPModelUtils.getFullName(localClassName, sourceModule, argument.sourceStart());

		return fullyQualifiedName.isEmpty() ? null : fullyQualifiedName;
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
