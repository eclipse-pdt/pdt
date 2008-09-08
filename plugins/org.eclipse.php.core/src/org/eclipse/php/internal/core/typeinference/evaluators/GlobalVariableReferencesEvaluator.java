/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.dltk.ti.*;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.GlobalVariableReferencesGoal;
import org.eclipse.php.internal.core.typeinference.goals.VariableDeclarationGoal;

/**
 * This evaluator finds all global declarations of the variable and produces {@link VariableDeclarationGoal} as a subgoal.
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
		IModelElement[] elements = PHPMixinModel.getInstance().getVariable(variableName, null, null);
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
					offsets.put(sourceModule, new TreeSet<ISourceRange>(sourceRangeComparator));
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
			if (exploreOtherFiles || (sourceModuleContext != null && sourceModuleContext.getSourceModule().equals(sourceModule))) {

				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
				SortedSet<ISourceRange> fileOffsets = offsets.get(sourceModule);

				if (!fileOffsets.isEmpty()) {
					VariableDeclarationSearcher varSearcher = new VariableDeclarationSearcher(sourceModule, moduleDeclaration, fileOffsets, variableName);
					try {
						moduleDeclaration.traverse(varSearcher);

						Map<IContext, LinkedList<ASTNode>> contextToDeclarationMap = varSearcher.getContextToDeclarationMap();
						Iterator<IContext> contextIt = contextToDeclarationMap.keySet().iterator();
						while (contextIt.hasNext()) {
							IContext c = contextIt.next();
							for (ASTNode declaration : contextToDeclarationMap.get(c)) {
								subGoals.add(new VariableDeclarationGoal(c, declaration));
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
			evaluated.add((IEvaluatedType)result);
		}
		return IGoal.NO_GOALS;
	}

	class VariableDeclarationSearcher extends ASTVisitor {

		private final ISourceModule sourceModule;
		private final ModuleDeclaration moduleDeclaration;
		private final String variableName;
		private Stack<IContext> contextStack = new Stack<IContext>();
		private Map<IContext, LinkedList<ASTNode>> contextToDeclarations = new HashMap<IContext, LinkedList<ASTNode>>();
		private Stack<ASTNode> nodesStack = new Stack<ASTNode>();
		private int level = 0;
		private Iterator<ISourceRange> offsetsIt;
		private int currentStart;
		private int currentEnd;
		private boolean stopProcessing;

		public VariableDeclarationSearcher(ISourceModule sourceModule, ModuleDeclaration moduleDeclaration, SortedSet<ISourceRange> offsets, String variableName) {
			this.sourceModule = sourceModule;
			this.moduleDeclaration = moduleDeclaration;
			this.variableName = variableName;
			offsetsIt = offsets.iterator();
			setNextRange();
		}

		public Map<IContext, LinkedList<ASTNode>> getContextToDeclarationMap() {
			return contextToDeclarations;
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
			return !stopProcessing && node.sourceStart() <= currentStart && node.sourceEnd() >= currentEnd;
		}

		private void increaseConditionalLevel() {
			++level;
		}

		private void decreaseConditionalLevel() {
			--level;
		}

		public boolean visit(Assignment node) throws Exception {
			if (!interesting(node)) {
				return false;
			}
			Expression variable = node.getVariable();
			if (variable instanceof VariableReference) {
				VariableReference variableReference = (VariableReference) variable;
				if (variableName.equals(variableReference.getName())) {

					LinkedList<ASTNode> decl = contextToDeclarations.get(contextStack.peek());

					// remove all declarations of this variable from the inner blocks
					while (decl.size() > level) {
						decl.removeLast();
					}
					decl.addLast(node.getValue());

					setNextRange();
				}
			}
			return visitGeneral(node);
		}

		public boolean visit(Block s) throws Exception {
			ASTNode parent = nodesStack.peek();
			if (parent instanceof CatchClause || parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				increaseConditionalLevel();
			}
			return visitGeneral(s);
		}

		public boolean visit(Statement node) throws Exception {
			if (!interesting(node)) {
				return false;
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				increaseConditionalLevel();
			}
			return visitGeneral(node);
		}

		public boolean visit(Expression node) throws Exception {
			if (!interesting(node)) {
				return false;
			}
			if (node instanceof Assignment) {
				return visit((Assignment) node);
			}
			if (node instanceof Block) {
				return visit((Block) node);
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof ConditionalExpression) {
				increaseConditionalLevel();
			}
			return visitGeneral(node);
		}

		public boolean endvisit(Block s) throws Exception {
			ASTNode parent = nodesStack.peek();
			if (parent instanceof CatchClause || parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				decreaseConditionalLevel();
			}
			endvisitGeneral(s);
			return true;
		}

		public boolean endvisit(Statement s) throws Exception {
			ASTNode parent = nodesStack.peek();
			if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				decreaseConditionalLevel();
			}
			return visitGeneral(s);
		}

		public boolean endvisit(Expression e) throws Exception {
			if (e instanceof Block) {
				return endvisit((Block) e);
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof ConditionalExpression) {
				decreaseConditionalLevel();
			}
			endvisitGeneral(e);
			return true;
		}

		public boolean visit(TypeDeclaration node) throws Exception {
			InstanceContext context = new InstanceContext(sourceModule, moduleDeclaration, new PHPClassType(node.getName()));
			contextStack.push(context);
			contextToDeclarations.put(context, new LinkedList<ASTNode>());
			return visitGeneral(node);
		}

		public boolean endvisit(TypeDeclaration node) throws Exception {
			contextStack.pop();

			endvisitGeneral(node);
			return true;
		}

		@SuppressWarnings("unchecked")
		public boolean visit(MethodDeclaration node) throws Exception {
			List<String> argumentsList = new LinkedList<String>();
			List<IEvaluatedType> argTypes = new LinkedList<IEvaluatedType>();
			List<Argument> args = node.getArguments();
			for (Argument a : args) {
				argumentsList.add(a.getName());
				argTypes.add(UnknownType.INSTANCE);
			}
			MethodContext context = new MethodContext(contextStack.peek(), sourceModule, moduleDeclaration, node, argumentsList.toArray(new String[argumentsList.size()]), argTypes.toArray(new IEvaluatedType[argTypes.size()]));
			contextStack.push(context);
			contextToDeclarations.put(context, new LinkedList<ASTNode>());

			return visitGeneral(node);
		}

		public boolean endvisit(MethodDeclaration node) throws Exception {
			contextStack.pop();

			endvisitGeneral(node);
			return true;
		}

		public boolean visit(ModuleDeclaration node) throws Exception {
			BasicContext context = new BasicContext(sourceModule, node);
			contextStack.push(context);
			contextToDeclarations.put(context, new LinkedList<ASTNode>());

			return visitGeneral(node);
		}

		public boolean endvisit(ModuleDeclaration node) throws Exception {
			contextStack.pop();

			endvisitGeneral(node);
			return true;
		}

		public boolean visitGeneral(ASTNode node) throws Exception {
			nodesStack.push(node);
			return interesting(node);
		}

		public void endvisitGeneral(ASTNode node) throws Exception {
			nodesStack.pop();
		}
	}
}
