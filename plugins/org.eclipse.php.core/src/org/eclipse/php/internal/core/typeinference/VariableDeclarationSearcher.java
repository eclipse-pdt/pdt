package org.eclipse.php.internal.core.typeinference;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;

/**
 * This abstract visitor finds global/local variable declarations 
 * @author michael
 *
 */
public class VariableDeclarationSearcher extends ContextFinder {

	/**
	 * The format of this map is the following:
	 * <pre>
	 * { scope => { variableName => (decl1, decl2 ...) } }   
	 * </pre>
	 * Where decl1, decl2 are variable declarations according to block structure level (if, switch, for, etc...) 
	 */
	protected Map<IContext, LinkedHashMap<String, LinkedList<ASTNode>>> contextToDecl = new HashMap<IContext, LinkedHashMap<String, LinkedList<ASTNode>>>();

	/**
	 * Stack of processed AST nodes
	 */
	protected Stack<ASTNode> nodesStack = new Stack<ASTNode>();

	protected int blockLevel = 0;

	public VariableDeclarationSearcher(ISourceModule sourceModule) {
		super(sourceModule);
	}

	public IContext getContext() {
		return null;
	}

	/**
	 * Returns whether to proceed with processing of this node
	 * @param node
	 * @return
	 */
	protected boolean interesting(ASTNode node) {
		return true;
	}

	private void increaseBlockLevel() {
		++blockLevel;
	}

	private void decreaseBlockLevel() {
		--blockLevel;
	}

	private void insertDeclarationAfterBlockLevel(LinkedList<ASTNode> declList, ASTNode decl) {
		// remove all declarations of this variable from the inner blocks
		while (declList.size() > blockLevel) {
			declList.removeLast();
		}
		while (declList.size() < blockLevel) {
			declList.addLast(null);
		}
		declList.addLast(decl);
	}

	protected void postProcessVarAssignment(Assignment node) {
	}

	/**
	 * Returns reference to the declarations list for given variable.
	 * Current scope is used.
	 * @param variableName
	 * @return
	 */
	protected LinkedList<ASTNode> getDeclList(String variableName) {
		return getDeclList(contextStack.peek(), variableName);
	}

	/**
	 * Returns reference to the declarations list for given variable and scope
	 * @param context
	 * @param variableName
	 * @return
	 */
	protected LinkedList<ASTNode> getDeclList(IContext context, String variableName) {
		LinkedHashMap<String, LinkedList<ASTNode>> declMap = contextToDecl.get(context);
		LinkedList<ASTNode> decl;
		if (!declMap.containsKey(variableName)) {
			declMap.put(variableName, decl = new LinkedList<ASTNode>());
		} else {
			decl = declMap.get(variableName);
		}
		return decl;
	}

	public boolean visit(Assignment node) throws Exception {
		if (!interesting(node)) {
			return visitGeneral(node);
		}
		Expression variable = node.getVariable();
		if (variable instanceof VariableReference) {

			VariableReference variableReference = (VariableReference) variable;
			LinkedList<ASTNode> declList = getDeclList(variableReference.getName());
			insertDeclarationAfterBlockLevel(declList, node);

			postProcessVarAssignment(node);
		}
		return visitGeneral(node);
	}

	public boolean visit(Block s) throws Exception {
		ASTNode parent = nodesStack.peek();
		if (parent instanceof CatchClause || parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
			increaseBlockLevel();
			chackForDeclarationInParent(parent);
		}
		return visitGeneral(s);
	}

	public boolean visit(Statement node) throws Exception {
		if (!interesting(node)) {
			return visitGeneral(node);
		}
		if (node instanceof GlobalStatement) {
			GlobalStatement globalStatement = (GlobalStatement) node;

			for (Expression variable : globalStatement.getVariables()) {
				if (variable instanceof VariableReference) {

					VariableReference variableReference = (VariableReference) variable;
					LinkedList<ASTNode> declList = getDeclList(variableReference.getName());

					// remove all declarations, since global statement overrides them
					for (int i = 0; i < declList.size(); ++i) {
						declList.set(i, null);
					}
					return visitGeneral(node);
				}
			}
		} else if (node instanceof FormalParameter) {
			FormalParameter parameter = (FormalParameter) node;
			LinkedList<ASTNode> declList = getDeclList(parameter.getName());
			declList.clear();
			declList.addLast(node);
			return visitGeneral(node);
		}

		ASTNode parent = nodesStack.peek();
		if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
			increaseBlockLevel();
			chackForDeclarationInParent(parent);
		}
		return visitGeneral(node);
	}

	public boolean visit(Expression node) throws Exception {
		if (!interesting(node)) {
			return visitGeneral(node);
		}
		if (node instanceof Assignment) {
			return visit((Assignment) node);
		}
		if (node instanceof Block) {
			return visit((Block) node);
		}
		ASTNode parent = nodesStack.peek();
		if (parent instanceof ConditionalExpression) {
			increaseBlockLevel();
			chackForDeclarationInParent(parent);
		}
		return visitGeneral(node);
	}

	public boolean endvisit(Block s) throws Exception {
		ASTNode parent = nodesStack.peek();
		if (parent instanceof CatchClause || parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
			decreaseBlockLevel();
		}
		endvisitGeneral(s);
		return true;
	}

	public boolean endvisit(Statement s) throws Exception {
		ASTNode parent = nodesStack.peek();
		if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
			decreaseBlockLevel();
		}
		endvisitGeneral(s);
		return true;
	}

	public boolean endvisit(Expression e) throws Exception {
		if (e instanceof Block) {
			return endvisit((Block) e);
		}
		ASTNode parent = nodesStack.peek();
		if (parent instanceof ConditionalExpression) {
			decreaseBlockLevel();
		}
		endvisitGeneral(e);
		return true;
	}

	public boolean visit(TypeDeclaration node) throws Exception {
		boolean visit = super.visit(node);
		if (!(node instanceof NamespaceDeclaration)) {
			contextToDecl.put(contextStack.peek(), new LinkedHashMap<String, LinkedList<ASTNode>>());
		}
		return visit;
	}

	public boolean visit(MethodDeclaration node) throws Exception {
		boolean visit = super.visit(node);
		contextToDecl.put(contextStack.peek(), new LinkedHashMap<String, LinkedList<ASTNode>>());
		return visit;
	}

	public boolean visit(ModuleDeclaration node) throws Exception {
		boolean visit = super.visit(node);
		contextToDecl.put(contextStack.peek(), new LinkedHashMap<String, LinkedList<ASTNode>>());
		return visit;
	}

	public boolean visitGeneral(ASTNode node) throws Exception {
		nodesStack.push(node);
		return interesting(node);
	}

	public void endvisitGeneral(ASTNode node) throws Exception {
		nodesStack.pop();
	}

	protected void chackForDeclarationInParent(ASTNode parent) {
		if (parent instanceof IfStatement || parent instanceof WhileStatement) {
			Expression condition = null;
			if (parent instanceof IfStatement) {
				condition = ((IfStatement) parent).getCondition();
			} else {
				condition = ((WhileStatement) parent).getCondition();
			}
			if (condition instanceof InstanceOfExpression) {
				InstanceOfExpression i = (InstanceOfExpression) condition;
				Expression variable = i.getExpr();
				if (variable instanceof VariableReference) {
					VariableReference variableReference = (VariableReference) variable;
					LinkedList<ASTNode> declList = getDeclList(variableReference.getName());
					insertDeclarationAfterBlockLevel(declList, i.getClassName());
				}
			}
		} else if (parent instanceof CatchClause) {
			CatchClause catchClause = (CatchClause) parent;
			LinkedList<ASTNode> declList = getDeclList(catchClause.getVariable().getName());
			insertDeclarationAfterBlockLevel(declList, catchClause);
		} else if (parent instanceof ForEachStatement) {
			ForEachStatement foreachStatement = (ForEachStatement) parent;
			if (foreachStatement.getValue() instanceof SimpleReference) {
				String variableName = ((SimpleReference) foreachStatement.getValue()).getName();
				LinkedList<ASTNode> declList = getDeclList(variableName);
				insertDeclarationAfterBlockLevel(declList, foreachStatement);
			}
		}
	}
}