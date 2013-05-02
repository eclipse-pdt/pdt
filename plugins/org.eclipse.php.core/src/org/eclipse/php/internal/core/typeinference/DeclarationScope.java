package org.eclipse.php.internal.core.typeinference;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;

/**
 * Variable declaration scope. Each scope contains mapping between variable name
 * and its possible declarations.
 * 
 * @author michael
 */
public class DeclarationScope {

	private Map<String, LinkedList<Declaration>> decls = new HashMap<String, LinkedList<Declaration>>();
	private IContext context;
	private Stack<Statement> innerBlocks = new Stack<Statement>();

	public DeclarationScope(IContext context) {
		this.context = context;
	}

	/**
	 * Returns context associated with this scope
	 * 
	 * @return
	 */
	public IContext getContext() {
		return context;
	}

	/**
	 * This must be called when entering inner conditional block.
	 */
	public void enterInnerBlock(Statement s) {
		if (!innerBlocks.isEmpty() && innerBlocks.peek() == s) {
			return;
		}
		innerBlocks.push(s);
	}

	/**
	 * This must be called when exiting inner conditional block.
	 */
	public void exitInnerBlock() {
		if (!innerBlocks.isEmpty()) {
			innerBlocks.pop();
		}
	}

	public int getInnerBlockLevel() {
		return innerBlocks.size();
	}

	/**
	 * Returns all declarations for all variables
	 * 
	 * @return
	 */
	public Map<String, LinkedList<Declaration>> getAllDeclarations() {
		return decls;
	}

	/**
	 * Returns all possible variable declarations for the given variable name in
	 * current scope.
	 * 
	 * @param varName
	 */
	public Declaration[] getDeclarations(String varName) {
		List<Declaration> result = new LinkedList<Declaration>();
		LinkedList<Declaration> varDecls = decls.get(varName);
		if (varDecls != null) {
			for (Declaration decl : varDecls) {
				if (decl != null) {
					result.add(decl);
				}
			}
		}
		return (Declaration[]) result.toArray(new Declaration[result.size()]);
	}

	/**
	 * Adds possible variable declaration
	 * 
	 * @param varName
	 *            Variable name
	 * @param declNode
	 *            AST declaration statement node
	 */
	public void addDeclaration(String varName, ASTNode declNode) {
		LinkedList<Declaration> varDecls = decls.get(varName);
		if (varDecls == null) {
			varDecls = new LinkedList<Declaration>();
			decls.put(varName, varDecls);
		}

		int level = innerBlocks.size();

		// TODO check ArrayCreation,ArrayVariableReference
		// skip all inner conditional blocks statements, since we've reached
		// a re-declaration here
		while (varDecls.size() > level + 1) {
			varDecls.removeLast();
		}

		// preserve place for declarations in outer conditional blocks
		// in case we haven't reached any declaration untill now
		while (varDecls.size() < level) {
			varDecls.addLast(null);
		}
		if (declNode instanceof Assignment
				&& (((Assignment) declNode).getVariable() instanceof ArrayVariableReference)) {
			int index = varDecls.size() - 1;
			while (index >= 0) {
				Declaration decl = varDecls.get(index);
				if (decl instanceof ArrayDeclaration) {
					ArrayDeclaration arrayDeclaration = (ArrayDeclaration) decl;
					arrayDeclaration.addDeclaration(declNode);
					return;
				}
				index--;
			}
		}
		if (varDecls.size() > level) {
			Declaration decl = varDecls.get(level);
			// The case that the node is inside another node
			if (decl != null) {
				if (level > 0) {
					Statement block = innerBlocks.get(level - 1);
					if (isInSameBlock(block, decl.getNode(), declNode)) {
						// replace existing declaration with a new one (leave
						// 'isGlobal' flag the same)
						decl.setNode(declNode);
						return;
					}
				} else { // The node is the top node.
							// replace existing declaration with a new one
							// (leave
							// 'isGlobal' flag the same)
					if (decl instanceof ArrayDeclaration) {
						decl = new Declaration(
								declNode instanceof GlobalStatement, declNode);
						varDecls.set(level, decl);
					} else {
						decl.setNode(declNode);
					}

					return;
				}
			}
		}
		// add new declaration
		if (declNode instanceof Assignment
				&& (((Assignment) declNode).getValue() instanceof ArrayCreation)) {
			varDecls.addLast(new ArrayDeclaration(
					declNode instanceof GlobalStatement, declNode));

		} else {
			varDecls.addLast(new Declaration(
					declNode instanceof GlobalStatement, declNode));
		}

	}

	public static boolean isInSameBlock(Statement block, ASTNode oldNode,
			ASTNode newNode) {
		if (block instanceof IfStatement) {
			IfStatement ifStatement = (IfStatement) block;
			Statement oldBlock = getBlock(ifStatement, oldNode);
			Statement newBlock = getBlock(ifStatement, newNode);
			if (oldBlock != null && oldBlock == newBlock) {
				return isInSameBlock(newBlock, oldNode, newNode);
			} else {
				return false;
			}
		}
		return true;
	}

	private static Statement getBlock(IfStatement ifStatement, ASTNode node) {
		Statement falseStatement = ifStatement.getFalseStatement();
		Statement trueStatement = ifStatement.getTrueStatement();
		if (trueStatement != null
				&& trueStatement.sourceStart() <= node.sourceStart()
				&& trueStatement.sourceEnd() >= node.sourceEnd()) {
			return trueStatement;
		} else if (falseStatement != null
				&& falseStatement.sourceStart() <= node.sourceStart()
				&& falseStatement.sourceEnd() >= node.sourceEnd()) {
			return falseStatement;
		}
		return null;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder("Variable Declarations (") //$NON-NLS-1$
				.append(context).append("): \n\n"); //$NON-NLS-1$
		Iterator<String> i = decls.keySet().iterator();
		while (i.hasNext()) {
			String varName = i.next();
			buf.append(varName).append(" => { \n\n"); //$NON-NLS-1$
			LinkedList<Declaration> varDecls = decls.get(varName);
			if (varDecls != null) {
				for (Declaration declNode : varDecls) {
					buf.append(declNode.toString()).append(", \n\n"); //$NON-NLS-1$
				}
			}
			buf.append("}, \n\n"); //$NON-NLS-1$
		}
		return buf.toString();
	}
}