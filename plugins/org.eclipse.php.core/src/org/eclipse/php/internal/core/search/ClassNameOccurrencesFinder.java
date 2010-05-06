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
package org.eclipse.php.internal.core.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * Class names occurrences finder.
 * 
 * @author shalom
 * 
 */
public class ClassNameOccurrencesFinder extends AbstractOccurrencesFinder {
	private static final String SELF = "self"; //$NON-NLS-1$
	public static final String ID = "ClassNameOccurrencesFinder"; //$NON-NLS-1$
	private String className;
	private TypeDeclaration originalDeclarationNode;
	private Identifier nameNode;
	private Map<Identifier, String> nodeToFullName = new HashMap<Identifier, String>();

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
			nameNode = getIdentifierForSelf((Identifier) node);
			className = nameNode.getName();
			if (nameNode.getParent() instanceof NamespaceName) {
				nameNode = (NamespaceName) nameNode.getParent();
			}
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
		if (nodeToFullName.containsKey(nameNode)) {
			String fullName = nodeToFullName.get(nameNode);
			for (Iterator<Identifier> iterator = nodeToFullName.keySet()
					.iterator(); iterator.hasNext();) {
				Identifier nameNode = iterator.next();
				if (nodeToFullName.get(nameNode).equals(fullName)) {
					fResult.add(new OccurrenceLocation(nameNode.getStart(),
							nameNode.getLength(), getOccurrenceType(nameNode),
							fDescription));
				}
			}
		}
	}

	public boolean visit(StaticConstantAccess staticDispatch) {
		Expression className = staticDispatch.getClassName();
		if (className instanceof Identifier) {
			dealIdentifier((Identifier) className);
		}
		return false;
	}

	public boolean visit(StaticFieldAccess staticDispatch) {
		Expression className = staticDispatch.getClassName();
		if (className instanceof Identifier) {
			dealIdentifier((Identifier) className);
		}
		return false;
	}

	public boolean visit(StaticMethodInvocation staticDispatch) {
		Expression className = staticDispatch.getClassName();
		if (className instanceof Identifier) {
			dealIdentifier((Identifier) className);
		}
		return true;
	}

	public boolean visit(ClassName className) {
		if (className.getName() instanceof Identifier) {
			Identifier identifier = (Identifier) className.getName();
			dealIdentifier(identifier);
		}
		return false;
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		if (originalDeclarationNode == null
				|| originalDeclarationNode == classDeclaration) {
			dealIdentifier(classDeclaration.getName());
		}
		checkSuper(classDeclaration.getSuperClass(), classDeclaration
				.interfaces());
		return true;
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		if (originalDeclarationNode == null
				|| originalDeclarationNode == interfaceDeclaration) {
			dealIdentifier(interfaceDeclaration.getName());
		}
		checkSuper(null, interfaceDeclaration.interfaces());

		return true;
	}

	public boolean visit(CatchClause catchStatement) {
		Expression className = catchStatement.getClassName();
		if (className instanceof Identifier) {
			dealIdentifier((Identifier) className);
		}
		return true;
	}

	public boolean visit(FormalParameter formalParameter) {
		Expression className = formalParameter.getParameterType();
		if (className instanceof Identifier) {
			dealIdentifier((Identifier) className);
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
			if (checkForNameEquality(functionName)) {
				String fullName = getFullName(classDeclaration.getName(),
						fLastUseParts, fCurrentNamespace);
				nodeToFullName.put(functionName, fullName);
			}
		}
		return true;
	}

	public boolean visit(UseStatementPart part) {
		NamespaceName namespace = part.getName();
		if (namespace instanceof Identifier) {
			dealIdentifier(namespace);
		}
		return false;
	}

	/**
	 * Checks if the supers are with the name of the class
	 * 
	 * @param superClass
	 * @param interfaces
	 */
	private void checkSuper(Expression superClass, List<Identifier> interfaces) {
		if (superClass instanceof Identifier) {
			dealIdentifier((Identifier) superClass);
		}

		if (interfaces != null) {
			for (Identifier identifier : interfaces) {
				dealIdentifier(identifier);
			}
		}
	}

	/**
	 * @param identifier
	 */
	private void dealIdentifier(Identifier identifier) {
		Identifier newIdentifier = getIdentifierForSelf(identifier);
		String fullName = getFullName(newIdentifier, fLastUseParts,
				fCurrentNamespace);
		nodeToFullName.put(identifier, fullName);
	}

	private Identifier getIdentifierForSelf(Identifier identifier) {
		Identifier newIdentifier = identifier;
		if (SELF.equals(identifier.getName())) {
			ASTNode parent = identifier.getParent();
			while (parent != null && !(parent instanceof ClassDeclaration)) {
				parent = parent.getParent();
			}
			if (parent instanceof ClassDeclaration) {
				ClassDeclaration cd = (ClassDeclaration) parent;
				if (cd.getName() != null) {
					newIdentifier = cd.getName();
				}
			}
		}
		return newIdentifier;
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
