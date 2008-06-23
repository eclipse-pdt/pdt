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
package org.eclipse.php.internal.ui.search;

import java.util.List;

import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.ui.Logger;

/**
 * Class members occurrences finder.
 * 
 * @author shalom
 */
public class ClassMembersOccurrencesFinder extends AbstractOccurrencesFinder {

	public static final String ID = "ClassMembersOccurrencesFinder"; //$NON-NLS-1$
	private String classMemberName; // The member's name
	private String typeDeclarationName; // Class or Interface name // TODO - use Binding
	private boolean isMethod;
	private IType dispatcherType; // might be null 

	/**
	 * @param root the AST root
	 * @param node the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		typeDeclarationName = null;
		isMethod = false;
		if (node.getType() == ASTNode.IDENTIFIER) {
			Identifier identifier = (Identifier) node;
			dispatcherType = resolveDispatcherType(identifier);
			classMemberName = identifier.getName();
			// IBinding binding = identifier.resolveBinding(); // FIXME - This should be implemented...
			ASTNode parent = identifier.getParent();
			int type = parent.getType();
			isMethod = type == ASTNode.FUNCTION_DECLARATION || parent.getLocationInParent() == FunctionName.NAME_PROPERTY || parent.getLocationInParent() == FunctionInvocation.FUNCTION_PROPERTY;
			while (typeDeclarationName == null && parent != fASTRoot) {
				if (type == ASTNode.CLASS_DECLARATION || type == ASTNode.INTERFACE_DECLARATION) {
					typeDeclarationName = ((TypeDeclaration) parent).getName().getName();
					break;
				}
				parent = parent.getParent();
				type = parent.getType();
			}
			return null;
		}
		fDescription = "OccurrencesFinder_occurrence_description";
		return fDescription;
	}

	/*
	 * Tries to resolve the type of the dispatcher.
	 */
	private IType resolveDispatcherType(Identifier identifier) {
		ITypeBinding typeBinding = null;
		ASTNode parent = identifier.getParent();
		if (parent.getType() == ASTNode.VARIABLE) {
			Variable var = (Variable) parent;
			if (var.getParent().getType() == ASTNode.FIELD_ACCESS) {
				typeBinding = ((FieldAccess) var.getParent()).getDispatcher().resolveTypeBinding();
			} else if (var.getParent().getType() == ASTNode.FUNCTION_NAME) {
				FunctionName fn = (FunctionName) var.getParent();
				if (fn.getParent().getType() == ASTNode.FUNCTION_INVOCATION) {
					FunctionInvocation fi = (FunctionInvocation) fn.getParent();
					if (fi.getParent().getType() == ASTNode.METHOD_INVOCATION) {
						typeBinding = ((MethodInvocation) fi.getParent()).getDispatcher().resolveTypeBinding();
					}
				}
			} else if (var.getParent().getType() == ASTNode.SINGLE_FIELD_DECLARATION) {
				return resolveDeclaringClassType(var.getParent());
			}
		} else if (parent.getType() == ASTNode.STATIC_CONSTANT_ACCESS) {
			StaticConstantAccess sca = (StaticConstantAccess) parent;
			typeBinding = sca.getClassName().resolveTypeBinding();
		} else if (parent.getType() == ASTNode.STATIC_FIELD_ACCESS) {
			StaticFieldAccess sfa = (StaticFieldAccess) parent;
			typeBinding = sfa.getClassName().resolveTypeBinding();
		} else if (parent.getType() == ASTNode.METHOD_DECLARATION) {
			MethodDeclaration md = (MethodDeclaration) parent;
			return resolveDeclaringClassType(md);
		} else if (parent.getType() == ASTNode.FUNCTION_DECLARATION) {
			FunctionDeclaration fd = (FunctionDeclaration) parent;
			return resolveDeclaringClassType(fd);
		} else if (parent.getType() == ASTNode.CLASS_CONSTANT_DECLARATION) {
			ClassConstantDeclaration ccd = (ClassConstantDeclaration) parent;
			return resolveDeclaringClassType(ccd);
		}
		if (typeBinding != null && typeBinding.isClass()) {
			return (IType) typeBinding.getPHPElement();
		}
		return null;
	}

	/*
	 * Resolve the class declaration type for the given node.
	 * This method traverse upward to find a defining ClassDeclaration and then resolves its IType.
	 */
	protected IType resolveDeclaringClassType(ASTNode node) {
		ASTNode parent = node.getParent();
		ClassDeclaration declaration = null;
		while (declaration == null && parent != null) {
			if (parent.getType() == ASTNode.CLASS_DECLARATION) {
				declaration = (ClassDeclaration) parent;
			}
			parent = parent.getParent();
		}
		if (declaration != null) {
			try {
				return (IType) declaration.getProgramRoot().getSourceModule().getType(declaration.getName().getName());
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences()
	 */
	protected void findOccurrences() {
		if (isMethod) {
			fDescription = Messages.format(BASE_DESCRIPTION, classMemberName + BRACKETS);
		} else {
			fDescription = Messages.format(BASE_DESCRIPTION, classMemberName);
		}
		fASTRoot.accept(this);
	}

	/**
	 * context +
	 * Mark var on: ... public $a; ...
	 */
	public boolean visit(ClassDeclaration classDeclaration) {
		checkTypeDeclaration(classDeclaration);
		return false;
	}

	/**
	 * context
	 */
	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		checkTypeDeclaration(interfaceDeclaration);
		return false;
	}

	/**
	 * Mark foo() on: $a->foo();
	 */
	public boolean visit(MethodInvocation methodInvocation) {
		if (isMethod) {
			checkDispatch(methodInvocation.getMethod().getFunctionName().getName());
		}
		return super.visit(methodInvocation);
	}

	/**
	 * Mark var on: $a->var;
	 */
	public boolean visit(FieldAccess fieldAccess) {
		if (!isMethod) {
			checkDispatch(fieldAccess.getField().getName());
		}
		return super.visit(fieldAccess);
	}

	/**
	 * Mark CON on: MyClass::CON;
	 */
	public boolean visit(StaticConstantAccess classConstantAccess) {
		Identifier constant = classConstantAccess.getConstant();
		if (classMemberName.equals(constant.getName())) {
			if (dispatcherType != null) {
				if (dispatcherType.equals(resolveDispatcherType(constant))) {
					fResult.add(new OccurrenceLocation(constant.getStart(), constant.getLength(), getOccurrenceType(constant), fDescription));
				}
			} else {
				fResult.add(new OccurrenceLocation(constant.getStart(), constant.getLength(), getOccurrenceType(constant), fDescription));
			}
		}
		// }
		return true;
	}

	/**
	 * Mark foo() on: MyClass::foo();
	 */
	public boolean visit(StaticMethodInvocation methodInvocation) {
		if (isMethod) {
			checkDispatch(methodInvocation.getMethod().getFunctionName().getName());
		}
		return super.visit(methodInvocation);
	}

	/**
	 * Mark var on: MyClass::var;
	 */
	public boolean visit(StaticFieldAccess fieldAccess) {
		if (!isMethod) {
			checkDispatch(fieldAccess.getField().getName());
		}

		return super.visit(fieldAccess);
	}

	/**
	 * @param dispatch
	 * @throws RuntimeException
	 */
	private void checkDispatch(ASTNode node) {
		if (node.getType() == ASTNode.IDENTIFIER) {
			Identifier id = (Identifier) node;
			if (id.getName().equalsIgnoreCase(classMemberName)) {
				if (dispatcherType != null) {
					if (dispatcherType.equals(resolveDispatcherType(id))) {
						fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(), getOccurrenceType(node), fDescription));
					}
				} else {
					fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(), getOccurrenceType(node), fDescription));
				}
			}
		}
		if (node.getType() == ASTNode.VARIABLE) {
			Variable id = (Variable) node;
			checkDispatch(id.getName());
		}
	}

	private void checkTypeDeclaration(TypeDeclaration typeDeclaration) {
		assert typeDeclaration != null;

		Block body = typeDeclaration.getBody();

		// definitions of the class property
		List<Statement> statements = body.statements();
		for (Statement statement : statements) {
			if (statement.getType() == ASTNode.METHOD_DECLARATION) {
				final MethodDeclaration classMethodDeclaration = (MethodDeclaration) statement;
				if (isMethod) {
					final Identifier functionName = classMethodDeclaration.getFunction().getFunctionName();
					if (classMemberName.equalsIgnoreCase(functionName.getName())) {
						if (dispatcherType != null) {
							if (dispatcherType.equals(resolveDispatcherType(functionName))) {
								fResult.add(new OccurrenceLocation(functionName.getStart(), functionName.getLength(), getOccurrenceType(functionName), fDescription));
							}
						} else {
							fResult.add(new OccurrenceLocation(functionName.getStart(), functionName.getLength(), getOccurrenceType(functionName), fDescription));
						}
					}
				}
			} else if (statement.getType() == ASTNode.FIELD_DECLARATION) {
				if (!isMethod) {
					FieldsDeclaration classVariableDeclaration = (FieldsDeclaration) statement;
					final Variable[] variableNames = classVariableDeclaration.getVariableNames();
					for (int j = 0; j < variableNames.length; j++) {
						// safe cast to identifier
						assert variableNames[j].getName().getType() == ASTNode.IDENTIFIER;

						final Identifier variable = (Identifier) variableNames[j].getName();
						if (classMemberName.equals(variable.getName())) {
							if (dispatcherType != null) {
								if (dispatcherType.equals(resolveDispatcherType(variable))) {
									fResult.add(new OccurrenceLocation(variable.getStart() - 1, variable.getLength() + 1, F_WRITE_OCCURRENCE, fDescription));
								}
							} else {
								fResult.add(new OccurrenceLocation(variable.getStart() - 1, variable.getLength() + 1, F_WRITE_OCCURRENCE, fDescription));
							}
						}
					}
				}
			} else if (statement.getType() == ASTNode.CLASS_CONSTANT_DECLARATION) {
				ClassConstantDeclaration classVariableDeclaration = (ClassConstantDeclaration) statement;
				List<Identifier> variableNames = classVariableDeclaration.names();
				for (Identifier name : variableNames) {
					if (classMemberName.equals(name.getName())) {
						if (dispatcherType != null) {
							if (dispatcherType.equals(resolveDispatcherType(name))) {
								fResult.add(new OccurrenceLocation(name.getStart(), name.getLength(), getOccurrenceType(name), fDescription));
							}
						} else {
							fResult.add(new OccurrenceLocation(name.getStart(), name.getLength(), getOccurrenceType(name), fDescription));
						}
					}
				}
			}
		}
		//		}
		body.accept(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#getOccurrenceReadWriteType(org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		// Default return is F_READ_OCCURRENCE, although the implementation of the Scalar visit might also use F_WRITE_OCCURRENCE
		if (node.getParent().getType() == ASTNode.CLASS_CONSTANT_DECLARATION || isInAssignment(node)) {
			return IOccurrencesFinder.F_WRITE_OCCURRENCE;
		}
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/**
	 * Check if the given node is a variable in a field access that exists in an assignment expression.
	 * 
	 * @param node
	 * @return
	 */
	protected boolean isInAssignment(ASTNode node) {
		if (node.getParent().getType() == ASTNode.VARIABLE) {
			Variable var = (Variable) node.getParent();
			if (var.getParent().getType() == ASTNode.FIELD_ACCESS) {
				FieldAccess fAccess = (FieldAccess) var.getParent();
				if (fAccess.getLocationInParent() == Assignment.LEFT_HAND_SIDE_PROPERTY) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return classMemberName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}
