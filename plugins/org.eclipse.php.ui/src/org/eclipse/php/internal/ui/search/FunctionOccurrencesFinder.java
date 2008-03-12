package org.eclipse.php.internal.ui.search;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * Functions occurrences finder.
 * 
 * @author shalom
 */
public class FunctionOccurrencesFinder extends AbstractOccurrencesFinder {

	public static final String ID = "FunctionOccurrencesFinder"; //$NON-NLS-1$
	private String functionName;

	/**
	 * @param root the AST root
	 * @param node the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		if (node.getType() == ASTNode.IDENTIFIER) {
			functionName = ((Identifier) node).getName();
			return null;
		}
		fDescription = "OccurrencesFinder_occurrence_description";
		return fDescription;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences()
	 */
	protected void findOccurrences() {
		fDescription = Messages.format("Occurrance of ''{0}()", functionName);
		fASTRoot.accept(this);
	}

	/**
	 * Visit the function declaration
	 */
	public boolean visit(FunctionDeclaration functionDeclaration) {
		// check the function name
		Identifier name = functionDeclaration.getFunctionName();
		if (functionName.equalsIgnoreCase(name.getName())) {
			fResult.add(new OccurrenceLocation(name.getStart(), name.getLength(), getOccurrenceReadWriteType(name), fDescription));
		}
		return true;
	}

	/**
	 * skip static call invocation, and add to changes list the global calls
	 */
	public boolean visit(FunctionInvocation functionInvocation) {
		final Expression functionName = functionInvocation.getFunctionName().getName();
		final int invocationParent = functionInvocation.getParent().getType();
		if (functionName.getType() == ASTNode.IDENTIFIER && invocationParent != ASTNode.METHOD_INVOCATION && invocationParent != ASTNode.STATIC_METHOD_INVOCATION) {
			final Identifier identifier = (Identifier) functionName;
			if (this.functionName.equalsIgnoreCase(identifier.getName())) {
				fResult.add(new OccurrenceLocation(functionName.getStart(), functionName.getLength(), getOccurrenceReadWriteType(functionName), fDescription));
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#getOccurrenceReadWriteType(org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceReadWriteType(ASTNode node) {
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return functionName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}