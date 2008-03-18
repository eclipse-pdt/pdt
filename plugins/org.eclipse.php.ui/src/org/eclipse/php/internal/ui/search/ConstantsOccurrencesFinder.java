package org.eclipse.php.internal.ui.search;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * Constants occurrences finder.
 * 
 * @author shalom
 */
public class ConstantsOccurrencesFinder extends AbstractOccurrencesFinder {
	public static final String ID = "ConstantsOccurrencesFinder"; //$NON-NLS-1$
	private String constantName;

	/**
	 * @param root the AST root
	 * @param node the selected node (must be an {@link Scalar} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		if (node.getType() == ASTNode.SCALAR) {
			constantName = ((Scalar) node).getStringValue();
			if (isQuoted(constantName)) {
				constantName = constantName.substring(1, constantName.length() - 1);
			}
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
		fDescription = Messages.format("Occurrance of ''{0}()", constantName);
		fASTRoot.accept(this);
	}

	/**
	 * Visit the scalars in the program.
	 */
	public boolean visit(Scalar scalar) {
		String scalarValue = scalar.getStringValue();
		if (scalar.getScalarType() == Scalar.TYPE_STRING && scalarValue != null) {
			if (!isQuoted(scalarValue)) {
				if (constantName.equals(scalarValue)) {
					// Usage of the scalar
					fResult.add(new OccurrenceLocation(scalar.getStart(), scalar.getLength(), getOccurrenceReadWriteType(scalar), fDescription));
				}
			} else {
				scalarValue = scalarValue.substring(1, scalarValue.length() - 1);
				if (constantName.equals(scalarValue)) {
					ASTNode parent = scalar.getParent();
					if (parent.getType() == ASTNode.FUNCTION_INVOCATION) {
						// Check if this is the definition function of the scalar (define).
						FunctionInvocation functionInvocation = (FunctionInvocation) parent;
						Expression name = functionInvocation.getFunctionName().getName();
						if (name.getType() == ASTNode.IDENTIFIER) {
							String functionName = ((Identifier) name).getName();
							if ("define".equalsIgnoreCase(functionName)) {//$NON-NLS-1$
								fResult.add(new OccurrenceLocation(scalar.getStart(), scalar.getLength(), IOccurrencesFinder.F_WRITE_OCCURRENCE, fDescription));
							} else if ("constant".equalsIgnoreCase(functionName)) { //$NON-NLS-1$
								fResult.add(new OccurrenceLocation(scalar.getStart(), scalar.getLength(), IOccurrencesFinder.F_READ_OCCURRENCE, fDescription));
							}
						}
					}
				}
			}
		}
		return true;
	}

	private static boolean isQuoted(String str) {
		if (str == null || str.length() < 3) {
			return false;
		}
		char first = str.charAt(0);
		char last = str.charAt(str.length() - 1);
		return (first == '\'' || first == '\"') && (last == '\'' || last == '\"');
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#getOccurrenceReadWriteType(org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceReadWriteType(ASTNode node) {
		// Default return is F_READ_OCCURRENCE, although the implementation of the Scalar visit might also use F_WRITE_OCCURRENCE
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return constantName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}
