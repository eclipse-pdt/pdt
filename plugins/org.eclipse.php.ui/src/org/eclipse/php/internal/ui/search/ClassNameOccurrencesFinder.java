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
	private TypeDeclaration originalDeclarationNode;

	/**
	 * @param root
	 *            the AST root
	 * @param node
	 *            the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		if (node instanceof Identifier) {
			className = ((Identifier) node).getName();
			ASTNode parent = node.getParent();
			if (parent instanceof TypeDeclaration) {
				originalDeclarationNode = (TypeDeclaration) parent;
			}
			return null;
		}
		fDescription = "OccurrencesFinder_occurrence_description"; //$NON-NLS-1$
		return fDescription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences
	 * ()
	 */
	protected void findOccurrences() {
		fDescription = Messages.format(BASE_DESCRIPTION, className);
		fASTRoot.accept(this);
	}

	public boolean visit(StaticConstantAccess staticDispatch) {
		Expression className = staticDispatch.getClassName();
		if (className instanceof Identifier) {
			checkIdentifier((Identifier) className);
		}
		return false;
	}

	public boolean visit(StaticFieldAccess staticDispatch) {
		Expression className = staticDispatch.getClassName();
		if (className instanceof Identifier) {
			checkIdentifier((Identifier) className);
		}
		return false;
	}

	public boolean visit(StaticMethodInvocation staticDispatch) {
		Expression className = staticDispatch.getClassName();
		if (className instanceof Identifier) {
			checkIdentifier((Identifier) className);
		}
		return false;
	}

	public boolean visit(ClassName className) {
		if (className.getName() instanceof Identifier) {
			Identifier identifier = (Identifier) className.getName();
			checkIdentifier(identifier);
		}
		return false;
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		if (originalDeclarationNode == null
				|| originalDeclarationNode == classDeclaration) {
			checkIdentifier(classDeclaration.getName());
		}
		checkSuper(classDeclaration.getSuperClass(), classDeclaration
				.interfaces());
		return true;
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		if (originalDeclarationNode == null
				|| originalDeclarationNode == interfaceDeclaration) {
			checkIdentifier(interfaceDeclaration.getName());
		}
		checkSuper(null, interfaceDeclaration.interfaces());

		return true;
	}

	public boolean visit(CatchClause catchStatement) {
		Expression className = catchStatement.getClassName();
		if (className instanceof Identifier) {
			checkIdentifier((Identifier) className);
		}
		return true;
	}

	public boolean visit(FormalParameter formalParameter) {
		Expression className = formalParameter.getParameterType();
		if (className instanceof Identifier) {
			checkIdentifier((Identifier) className);
		}
		return true;
	}

	/**
	 * check for constructor name (as PHP4 uses)
	 */
	public boolean visit(MethodDeclaration methodDeclaration) {
		final ASTNode parent = methodDeclaration.getParent();
		if (parent.getType() == ASTNode.BLOCK
				&& parent.getParent().getType() == ASTNode.CLASS_DECLARATION) {
			ClassDeclaration classDeclaration = (ClassDeclaration) parent
					.getParent();
			final Identifier functionName = methodDeclaration.getFunction()
					.getFunctionName();
			if (checkForNameEquality(classDeclaration.getName())
					&& checkForNameEquality(functionName)) {
				fResult.add(new OccurrenceLocation(functionName.getStart(),
						functionName.getLength(),
						getOccurrenceType(methodDeclaration), fDescription));
			}
		}
		return true;
	}

	/**
	 * Checks if the supers are with the name of the class
	 * 
	 * @param superClass
	 * @param interfaces
	 */
	private void checkSuper(Expression superClass, List<Identifier> interfaces) {
		if (superClass instanceof Identifier) {
			checkIdentifier((Identifier) superClass);
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
			fResult.add(new OccurrenceLocation(identifier.getStart(),
					identifier.getLength(), getOccurrenceType(identifier),
					fDescription));
		}
	}

	private boolean checkForNameEquality(Identifier identifier) {
		return identifier != null && className != null
				&& className.equalsIgnoreCase(identifier.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#
	 * getOccurrenceReadWriteType
	 * (org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		// Default return is F_READ_OCCURRENCE, although the implementation of
		// the Scalar visit might also use F_WRITE_OCCURRENCE
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return className;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}
