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
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.ast.nodes.ConstantDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.ConstantDeclarationGoal;

public class ConstantDeclarationEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> evaluatedTypes = new LinkedList<IEvaluatedType>();

	public ConstantDeclarationEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ConstantDeclarationGoal typedGoal = (ConstantDeclarationGoal) goal;
		String constantName = typedGoal.getConstantName();
		String typeName = typedGoal.getTypeName();

		IDLTKSearchScope scope = null;
		IScriptProject scriptProject = null;
		ISourceModuleContext sourceModuleContext = (ISourceModuleContext) goal
				.getContext();
		if (sourceModuleContext != null) {
			scriptProject = sourceModuleContext.getSourceModule()
					.getScriptProject();
			scope = SearchEngine.createSearchScope(scriptProject);
		}

		if (scope == null) {
			scope = SearchEngine.createWorkspaceScope(PHPLanguageToolkit
					.getDefault());
		}
		IType[] types = PhpModelAccess.getDefault().findTypes(typeName,
				MatchRule.EXACT, 0, Modifiers.AccNameSpace, scope, null);
		Set<IModelElement> elements = new HashSet<IModelElement>();
		for (IType type : types) {
			try {
				IField field = type.getField(constantName);
				if (field.exists() && PHPFlags.isConstant(field.getFlags())) {
					elements.add(field);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		Map<ISourceModule, SortedSet<ISourceRange>> offsets = new HashMap<ISourceModule, SortedSet<ISourceRange>>();

		Comparator<ISourceRange> sourceRangeComparator = new Comparator<ISourceRange>() {
			public int compare(ISourceRange o1, ISourceRange o2) {
				return o1.getOffset() - o2.getOffset();
			}
		};

		for (IModelElement element : elements) {
			if (element instanceof IField) {
				IField field = (IField) element;
				ISourceModule sourceModule = field.getSourceModule();
				if (!offsets.containsKey(sourceModule)) {
					offsets.put(sourceModule, new TreeSet<ISourceRange>(
							sourceRangeComparator));
				}
				try {
					offsets.get(sourceModule).add(field.getSourceRange());
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
			ModuleDeclaration moduleDeclaration = SourceParserUtil
					.getModuleDeclaration(sourceModule);
			SortedSet<ISourceRange> fileOffsets = offsets.get(sourceModule);

			if (!fileOffsets.isEmpty()) {
				ConstantDeclarationSearcher searcher = new ConstantDeclarationSearcher(
						fileOffsets, constantName);
				try {
					moduleDeclaration.traverse(searcher);
					for (Scalar scalar : searcher.getDeclarations()) {
						subGoals.add(new ExpressionTypeGoal(goal.getContext(),
								scalar));
					}
				} catch (Exception e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluatedTypes);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			evaluatedTypes.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

	class ConstantDeclarationSearcher extends ASTVisitor {

		private String constantName;
		private Iterator<ISourceRange> offsetsIt;
		private int currentStart;
		private int currentEnd;
		private boolean stopProcessing;
		private List<Scalar> declarations = new LinkedList<Scalar>();

		public ConstantDeclarationSearcher(SortedSet<ISourceRange> offsets,
				String constantName) {
			this.constantName = constantName;
			offsetsIt = offsets.iterator();
			setNextRange();
		}

		public List<Scalar> getDeclarations() {
			return declarations;
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

		private boolean interesting(ASTNode node) {
			return !stopProcessing && node.sourceStart() <= currentStart
					&& node.sourceEnd() >= currentEnd;
		}

		@SuppressWarnings("unchecked")
		public boolean visit(CallExpression node) throws Exception {
			if (!interesting(node)) {
				return false;
			}
			if ("define".equalsIgnoreCase(node.getName())) { //$NON-NLS-1$
				// report global constant:
				List args = node.getArgs().getChilds();
				if (args.size() == 2) {
					ASTNode firstArg = (ASTNode) args.get(0);
					ASTNode secondArg = (ASTNode) args.get(0);
					if (firstArg instanceof Scalar
							&& secondArg instanceof Scalar) {
						Scalar constantName = (Scalar) firstArg;
						Scalar constantValue = (Scalar) secondArg;
						if (this.constantName.equals(stripQuotes(constantName
								.getValue()))) {
							declarations.add(constantValue);
						}
					}
				}
			}
			return visitGeneral(node);
		}

		public boolean visit(ConstantDeclaration node) throws Exception {
			if (!interesting(node)) {
				return false;
			}
			Expression value = node.getConstantValue();
			if (value instanceof Scalar) {
				declarations.add((Scalar) value);
			}
			return visitGeneral(node);
		}

		public boolean visit(Expression node) throws Exception {
			if (!interesting(node)) {
				return false;
			}
			if (node instanceof CallExpression) {
				return visit((CallExpression) node);
			}
			return visitGeneral(node);
		}

		public boolean endvisit(Statement s) throws Exception {
			if (s instanceof ConstantDeclaration) {
				return visit((ConstantDeclaration) s);
			}
			return visitGeneral(s);
		}

		public boolean visitGeneral(ASTNode node) throws Exception {
			return interesting(node);
		}
	}

	/**
	 * Strips single or double quotes from the start and from the end of the
	 * given string
	 * 
	 * @param name
	 *            String
	 * @return
	 */
	private static String stripQuotes(String name) {
		int len = name.length();
		if (len > 1
				&& (name.charAt(0) == '\'' && name.charAt(len - 1) == '\'' || name
						.charAt(0) == '"'
						&& name.charAt(len - 1) == '"')) {
			name = name.substring(1, len - 1);
		}
		return name;
	}
}
