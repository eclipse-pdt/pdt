package org.eclipse.php.internal.ui.search;

import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * Class names occurrences finder.
 * 
 * @author shalom
 *
 */
public class ClassNameOccurrencesFinder extends AbstractOccurrencesFinder {
	public static final String ID = "ClassNameOccurrencesFinder"; //$NON-NLS-1$
	private String className;

	/**
	 * @param root the AST root
	 * @param node the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		if (node.getType() == ASTNode.IDENTIFIER) {
			className = ((Identifier) node).getName();
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
		fDescription = Messages.format(BASE_DESCRIPTION, className);
		fASTRoot.accept(this);
	}

	public boolean visit(StaticConstantAccess staticDispatch) {
		checkIdentifier(staticDispatch.getClassName());
		return false;
	}

	public boolean visit(StaticFieldAccess staticDispatch) {
		checkIdentifier(staticDispatch.getClassName());
		return false;
	}

	public boolean visit(StaticMethodInvocation staticDispatch) {
		checkIdentifier(staticDispatch.getClassName());
		return false;
	}

	public boolean visit(ClassName className) {
		if (className.getName().getType() == ASTNode.IDENTIFIER) {
			Identifier identifier = (Identifier) className.getName();
			checkIdentifier(identifier);
		}
		return false;
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		checkIdentifier(classDeclaration.getName());
		checkSuper(classDeclaration.getSuperClass(), classDeclaration.interfaces());

		return true;
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		checkIdentifier(interfaceDeclaration.getName());
		checkSuper(null, interfaceDeclaration.interfaces());

		return true;
	}

	public boolean visit(CatchClause catchStatement) {
		checkIdentifier(catchStatement.getClassName());
		return true;
	}

	public boolean visit(FormalParameter formalParameter) {
		checkIdentifier(formalParameter.getParameterType());
		return true;
	}

	/**
	 * check for constructor name (as PHP4 uses)
	 */
	public boolean visit(MethodDeclaration methodDeclaration) {
		final ASTNode parent = methodDeclaration.getParent();
		if (parent.getType() == ASTNode.BLOCK && parent.getParent().getType() == ASTNode.CLASS_DECLARATION) {
			ClassDeclaration classDeclaration = (ClassDeclaration) parent.getParent();
			final Identifier functionName = methodDeclaration.getFunction().getFunctionName();
			if (checkForNameEquality(classDeclaration.getName()) && checkForNameEquality(functionName)) {
				fResult.add(new OccurrenceLocation(functionName.getStart(), functionName.getLength(), getOccurrenceReadWriteType(methodDeclaration), fDescription));
			}
		}
		return true;
	}

	/**
	 * Checks if the supers are with the name of the class
	 * @param superClass
	 * @param interfaces
	 */
	private void checkSuper(Identifier superClass, List<Identifier> interfaces) {
		if (superClass != null) {
			checkIdentifier(superClass);
		}

		if (interfaces != null) {
			for (Identifier identifier : interfaces) {
				checkIdentifier(identifier);
			}
		}
	}

	/**
	 * @param identifier
	 */
	private void checkIdentifier(Identifier identifier) {
		if (checkForNameEquality(identifier)) {
			fResult.add(new OccurrenceLocation(identifier.getStart(), identifier.getLength(), getOccurrenceReadWriteType(identifier), fDescription));
		}
	}

	private boolean checkForNameEquality(Identifier identifier) {
		return identifier != null && className.equalsIgnoreCase(identifier.getName());
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
		return className;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}
