/**
 * 
 */
package org.eclipse.php.internal.ui.search;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * A local variable occurrence finder.
 * 
 * @author shalom
 */
public class LocalVariableOccurrencesFinder extends AbstractOccurrencesFinder {

	public static final String ID = "LocalVariableOccurrencesFinder"; //$NON-NLS-1$

	private Identifier fIdentifier;
	private FunctionDeclaration fFunctionDeclaration;

	/**
	 * @param root the AST root
	 * @param node the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		if (isIdendifier(node)) {
			fIdentifier = (Identifier) node;
			ASTNode parent = node.getParent();
			while (parent != null && fFunctionDeclaration == null) {
				final int type = parent.getType();
				if (type == ASTNode.FUNCTION_DECLARATION) {
					fFunctionDeclaration = (FunctionDeclaration) parent;
				}
				parent = parent.getParent();
			}
			return null;
		}
		// return "OccurrencesFinder_no_occurrence";
		fDescription = "OccurrencesFinder_occurrence_description";
		return fDescription;
	}

	/*
	 * Check if the node is an identifier node.
	 */
	private final boolean isIdendifier(ASTNode node) {
		return node != null && node.getType() == ASTNode.IDENTIFIER;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences()
	 */
	protected void findOccurrences() {
		fDescription = Messages.format("Occurrance of ''${0}''", fIdentifier.getName());
		fFunctionDeclaration.accept(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.Variable)
	 */
	public boolean visit(Variable variable) {
		Expression name = variable.getName();
		if (name.getType() == ASTNode.IDENTIFIER) {
			if (((Identifier) name).getName().equals(this.fIdentifier.getName())) {
				addOccurrence(variable);
			}
		}
		return true;
	}

	private void addOccurrence(ASTNode node) {
		int readWriteType = getOccurrenceType(node);
		String desc = fDescription;
		if (readWriteType == IOccurrencesFinder.F_WRITE_OCCURRENCE) {
			desc = Messages.format(BASE_WRITE_DESCRIPTION, '$' + fIdentifier.getName());
		}
		fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(), readWriteType, desc));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#getOccurrenceReadWriteType(org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		Variable variable = (Variable) node;
		ASTNode parent = variable.getParent();
		boolean isArrayAccess = false;
		int parentType = parent.getType();
		if (parentType == ASTNode.ARRAY_ACCESS) {
			parent = parent.getParent();
			parentType = parent.getType();
			isArrayAccess = true;
		}
		if (parentType == ASTNode.ASSIGNMENT) {
			Assignment assignment = (Assignment) parent;
			VariableBase leftHandSide = assignment.getLeftHandSide();
			if (!isArrayAccess) {
				if (leftHandSide == node) {
					return IOccurrencesFinder.F_WRITE_OCCURRENCE;
				}
			} else {
				if (leftHandSide.getType() == ASTNode.ARRAY_ACCESS) {
					if (((ArrayAccess) leftHandSide).getName() == node) {
						return IOccurrencesFinder.F_WRITE_OCCURRENCE;
					}
				}
			}
		}
		if (parentType == ASTNode.FOR_EACH_STATEMENT || 
				parentType == ASTNode.FORMAL_PARAMETER || 
				parentType == ASTNode.CATCH_CLAUSE || 
				parentType == ASTNode.PREFIX_EXPRESSION || 
				parentType == ASTNode.POSTFIX_EXPRESSION) {
			return IOccurrencesFinder.F_WRITE_OCCURRENCE;
		}
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return fIdentifier.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}
