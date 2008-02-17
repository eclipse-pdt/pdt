package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.ClassVariableDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.IWeightedGoal;

/**
 * This evaluator finds class field declartion either using "var" or in class constructor.
 */
public class ClassVariableDeclarationEvaluator extends GoalEvaluator implements IWeightedGoal {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public ClassVariableDeclarationEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ClassVariableDeclarationGoal typedGoal = (ClassVariableDeclarationGoal) goal;
		InstanceContext context = (InstanceContext) typedGoal.getContext();
		SimpleReference field = typedGoal.getField();

		List<IGoal> subGoals = new LinkedList<IGoal>();

		String typeName = context.getInstanceType().getTypeName();

		IModelElement[] elements = PHPMixinModel.getInstance().getClass(typeName);

		for (IModelElement element : elements) {
			if (element instanceof IType) {
				IType type = (IType) element;
				ISourceModule sourceModule = type.getSourceModule();
				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);
				try {
					TypeDeclaration typeDeclaration = PHPModelUtils.getNodeByClass(moduleDeclaration, type);
					ClassVariableDeclarationSearcher searcher = new ClassVariableDeclarationSearcher(sourceModule, moduleDeclaration, field.getName(), typeName);
					typeDeclaration.traverse(searcher);

					Map<IContext, LinkedList<ASTNode>> contextToDeclarationMap = searcher.getContextToDeclarationMap();
					Iterator<IContext> contextIt = contextToDeclarationMap.keySet().iterator();
					while (contextIt.hasNext()) {
						IContext c = contextIt.next();
						for (ASTNode declaration : contextToDeclarationMap.get(c)) {
							subGoals.add(new ExpressionTypeGoal(c, declaration));
						}
					}
				} catch (Exception e) {
					Logger.logException(e);
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

	public int getWeight() {
		return IWeightedGoal.LITE;
	}

	class ClassVariableDeclarationSearcher extends ASTVisitor {

		private String variableName;
		private Object className;
		private Stack<IContext> contextStack = new Stack<IContext>();
		private Map<IContext, LinkedList<ASTNode>> contextToDeclarations = new HashMap<IContext, LinkedList<ASTNode>>();
		private ISourceModule sourceModule;
		private ModuleDeclaration moduleDeclaration;
		private int level = 0;
		private Stack<ASTNode> nodesStack = new Stack<ASTNode>();
		private boolean primaryDeclarationOverriden;

		public ClassVariableDeclarationSearcher(ISourceModule sourceModule, ModuleDeclaration moduleDeclaration, String variableName, String className) {
			this.sourceModule = sourceModule;
			this.moduleDeclaration = moduleDeclaration;
			this.variableName = variableName;
			this.className = className;
		}

		public Map<IContext, LinkedList<ASTNode>> getContextToDeclarationMap() {
			// If primary declaration was overriden in the constructor - remove it from the results
			if (primaryDeclarationOverriden) {
				Iterator<IContext> contextIt = contextToDeclarations.keySet().iterator();
				while (contextIt.hasNext()) {
					IContext c = contextIt.next();
					if (c instanceof InstanceContext) {
						contextToDeclarations.get(c).clear();
						break;
					}
				}
			}
			return contextToDeclarations;
		}

		private void increaseConditionalLevel() {
			++level;
		}

		private void decreaseConditionalLevel() {
			--level;
		}

		public boolean visit(PHPFieldDeclaration e) throws Exception {
			if (!primaryDeclarationOverriden) {
				if (e.getName().equals('$' + variableName)) {
					Expression variableValue = e.getVariableValue();
					if (variableValue != null) {
						LinkedList<ASTNode> declList = contextToDeclarations.get(contextStack.peek());
						assert declList.isEmpty(); // there's only one field declaration in the class
						declList.add(variableValue);
					}
				}
			}
			return visitGeneral(e);
		}

		public boolean visit(Assignment e) throws Exception {
			Expression left = e.getVariable();
			if (left instanceof FieldAccess) { // class variable ($this->a)
				FieldAccess fieldAccess = (FieldAccess) left;
				Expression dispatcher = fieldAccess.getDispatcher();
				if (dispatcher instanceof VariableReference && "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
					Expression field = fieldAccess.getField();
					if (field instanceof SimpleReference && ((SimpleReference) field).getName().equals(variableName)) {
						LinkedList<ASTNode> declList = contextToDeclarations.get(contextStack.peek());
						// remove all declarations of this variable from the inner blocks
						while (declList.size() > level) {
							declList.removeLast();
						}
						declList.addLast(e.getValue());

						// Override primary field declaration:
						if (level == 0) {
							primaryDeclarationOverriden = true;
						}
					}
				}
			}
			return visitGeneral(e);
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
		public boolean visit(MethodDeclaration e) throws Exception {
			List<String> argumentsList = new LinkedList<String>();
			List<IEvaluatedType> argTypes = new LinkedList<IEvaluatedType>();
			List<Argument> args = e.getArguments();
			for (Argument a : args) {
				argumentsList.add(a.getName());
				argTypes.add(UnknownType.INSTANCE);
			}
			MethodContext context = new MethodContext(contextStack.peek(), sourceModule, moduleDeclaration, e, argumentsList.toArray(new String[argumentsList.size()]), argTypes.toArray(new IEvaluatedType[argTypes.size()]));
			contextStack.push(context);
			contextToDeclarations.put(context, new LinkedList<ASTNode>());

			String name = e.getName();
			if (name.equals("__construct") || name.equals(className)) {
				return visitGeneral(e);
			}
			return false;
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

		public boolean visit(Block s) throws Exception {
			ASTNode parent = nodesStack.peek();
			if (parent instanceof CatchClause || parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				increaseConditionalLevel();
			}
			return visitGeneral(s);
		}

		public boolean endvisit(Block s) throws Exception {
			ASTNode parent = nodesStack.peek();
			if (parent instanceof CatchClause || parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				decreaseConditionalLevel();
			}
			endvisitGeneral(s);
			return true;
		}

		public boolean visit(Expression e) throws Exception {
			if (e instanceof Assignment) {
				return visit((Assignment) e);
			}
			if (e instanceof Block) {
				return visit((Block) e);
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof ConditionalExpression) {
				increaseConditionalLevel();
			}
			return visitGeneral(e);
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

		public boolean visit(Statement e) throws Exception {
			if (e instanceof PHPFieldDeclaration) {
				return visit((PHPFieldDeclaration) e);
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				increaseConditionalLevel();
			}
			return visitGeneral(e);
		}

		public boolean endvisit(Statement s) throws Exception {
			ASTNode parent = nodesStack.peek();
			if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				decreaseConditionalLevel();
			}
			return visitGeneral(s);
		}

		public boolean visitGeneral(ASTNode node) throws Exception {
			nodesStack.push(node);
			return true;
		}

		public void endvisitGeneral(ASTNode node) throws Exception {
			nodesStack.pop();
		}
	}
}
