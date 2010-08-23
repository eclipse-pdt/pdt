/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.Declaration;
import org.eclipse.php.internal.core.typeinference.DeclarationScope;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.VariableDeclarationSearcher;
import org.eclipse.php.internal.core.typeinference.goals.GlobalVariableReferencesGoal;

/**
 * This evaluator finds all global declarations of the variable and produces
 * {@link VariableDeclarationGoal} as a subgoal.
 */
public class GlobalVariableReferencesEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public GlobalVariableReferencesEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		GlobalVariableReferencesGoal typedGoal = (GlobalVariableReferencesGoal) goal;

		IContext context = goal.getContext();
		ISourceModuleContext sourceModuleContext = null;
		if (context instanceof ISourceModuleContext) {
			sourceModuleContext = (ISourceModuleContext) context;
		}

		String variableName = typedGoal.getVariableName();

		boolean exploreOtherFiles = true;

		// Find all global variables from mixin
		IScriptProject scriptProject = sourceModuleContext.getSourceModule()
				.getScriptProject();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);

		IField[] elements = PhpModelAccess.getDefault().findFields(
				variableName, MatchRule.EXACT, Modifiers.AccGlobal,
				Modifiers.AccConstant, scope, null);

		// if no element found, return empty array.
		if (elements == null) {
			return new IGoal[] {};
		}

		Map<ISourceModule, SortedSet<ISourceRange>> offsets = new HashMap<ISourceModule, SortedSet<ISourceRange>>();

		Comparator<ISourceRange> sourceRangeComparator = new Comparator<ISourceRange>() {
			public int compare(ISourceRange o1, ISourceRange o2) {
				return o1.getOffset() - o2.getOffset();
			}
		};

		for (IModelElement element : elements) {
			if (element instanceof SourceField) {
				SourceField sourceField = (SourceField) element;
				ISourceModule sourceModule = sourceField.getSourceModule();
				if (!offsets.containsKey(sourceModule)) {
					offsets.put(sourceModule, new TreeSet<ISourceRange>(
							sourceRangeComparator));
				}
				try {
					offsets.get(sourceModule).add(sourceField.getSourceRange());
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}

		List<IGoal> subGoals = new LinkedList<IGoal>();
		Iterator<ISourceModule> sourceModuleIt = offsets.keySet().iterator();
		while (sourceModuleIt.hasNext()) {
			ISourceModule sourceModule = sourceModuleIt.next();
			if (exploreOtherFiles
					|| (sourceModuleContext != null && sourceModuleContext
							.getSourceModule().equals(sourceModule))) {

				ModuleDeclaration moduleDeclaration = SourceParserUtil
						.getModuleDeclaration(sourceModule);
				SortedSet<ISourceRange> fileOffsets = offsets.get(sourceModule);

				if (!fileOffsets.isEmpty()) {
					GlobalReferenceDeclSearcher varSearcher = new GlobalReferenceDeclSearcher(
							sourceModule, fileOffsets, variableName);
					try {
						moduleDeclaration.traverse(varSearcher);

						DeclarationScope[] scopes = varSearcher.getScopes();
						for (DeclarationScope s : scopes) {
							for (Declaration decl : s
									.getDeclarations(variableName)) {
								subGoals.add(new ExpressionTypeGoal(s
										.getContext(), decl.getNode()));
							}
						}
					} catch (Exception e) {
						if (DLTKCore.DEBUG) {
							e.printStackTrace();
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
		if (state != GoalState.RECURSIVE && result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

	class GlobalReferenceDeclSearcher extends VariableDeclarationSearcher {

		private final String variableName;
		private Iterator<ISourceRange> offsetsIt;
		private int currentStart;
		private int currentEnd;
		private boolean stopProcessing;

		public GlobalReferenceDeclSearcher(ISourceModule sourceModule,
				SortedSet<ISourceRange> offsets, String variableName) {
			super(sourceModule);
			this.variableName = variableName;
			offsetsIt = offsets.iterator();
			setNextRange();
		}

		private void setNextRange() {
			if (offsetsIt.hasNext()) {
				ISourceRange range = offsetsIt.next();
				currentStart = range.getOffset();
				currentEnd = currentStart + range.getLength();
			} else {
				stopProcessing = true;
			}
		}

		protected void postProcess(Expression node) {
			if (node instanceof Assignment) {
				Expression variable = ((Assignment) node).getVariable();
				if (variable instanceof VariableReference) {
					VariableReference variableReference = (VariableReference) variable;
					if (variableName.equals(variableReference.getName())) {
						setNextRange();
					}
				}
			}
		}

		protected boolean isInteresting(ASTNode node) {
			return !stopProcessing && node.sourceStart() <= currentStart
					&& node.sourceEnd() >= currentEnd;
		}
	}
}
