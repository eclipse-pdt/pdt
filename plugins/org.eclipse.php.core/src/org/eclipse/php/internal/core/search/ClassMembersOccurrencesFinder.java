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

import java.util.List;

import org.eclipse.dltk.core.*;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.project.ProjectOptions;

/**
 * Class members occurrences finder.
 * 
 * @author shalom
 */
public class ClassMembersOccurrencesFinder extends AbstractOccurrencesFinder {

	public static final String ID = "ClassMembersOccurrencesFinder"; //$NON-NLS-1$
	private String classMemberName; // The member's name
	private String typeDeclarationName; // Class or Interface name // TODO - use
	// Binding
	private boolean isMethod;
	private ITypeBinding dispatcherType; // might be null
	private ASTNode erroneousNode;
	private boolean isIncludesuper;
	private List<IType> traitList;

	public void setIncludesuper(boolean isIncludesuper) {
		this.isIncludesuper = isIncludesuper;
	}

	/**
	 * @param root
	 *            the AST root
	 * @param node
	 *            the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		fProblems = getProblems(root);
		typeDeclarationName = null;
		isMethod = false;

		PHPVersion phpVersion = PHPVersion.PHP5_4;
		if (root.getSourceModule().getScriptProject() != null
				&& root.getSourceModule().getScriptProject().getProject() != null) {
			phpVersion = ProjectOptions.getPhpVersion(root.getSourceModule()
					.getScriptProject().getProject());
		}

		if (node.getType() == ASTNode.IDENTIFIER) {
			Identifier identifier = (Identifier) node;

			classMemberName = identifier.getName();

			ASTNode parent = identifier.getParent();
			int type = parent.getType();
			isMethod = type == ASTNode.FUNCTION_DECLARATION
					|| parent.getLocationInParent() == FunctionName.NAME_PROPERTY
					|| parent.getLocationInParent() == FunctionInvocation.FUNCTION_PROPERTY
					|| type == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE
					|| type == ASTNode.TRAIT_ALIAS;

			dispatcherType = resolveDispatcherType(identifier);
			if (dispatcherType != null
					&& phpVersion.isGreaterThan(PHPVersion.PHP5_3)) {
				String memberName = getRealName(identifier);
				// if (dispatcherType.isTrait()) {
				// traitList = new LinkedList<IType>();
				// traitList.add((IType) dispatcherType.getPHPElement());
				// } else {
				traitList = dispatcherType.getTraitList(isMethod, memberName,
						false);
				// }
			}

			while (typeDeclarationName == null && parent != fASTRoot) {
				if (type == ASTNode.CLASS_DECLARATION
						|| type == ASTNode.INTERFACE_DECLARATION) {
					typeDeclarationName = ((TypeDeclaration) parent).getName()
							.getName();
					break;
				}
				parent = parent.getParent();
				type = parent.getType();
			}
			if (hasProblems(node.getStart(), node.getEnd())) {
				erroneousNode = node;
			}
			return null;
		}

		fDescription = "OccurrencesFinder_occurrence_description"; //$NON-NLS-1$
		return fDescription;
	}

	/*
	 * Tries to resolve the type of the dispatcher.
	 */
	private ITypeBinding resolveDispatcherType(Identifier identifier) {
		ITypeBinding typeBinding = null;
		ASTNode parent = identifier.getParent();
		if (parent.getType() == ASTNode.VARIABLE) {
			Variable var = (Variable) parent;
			ASTNode varParent = var.getParent();
			while (varParent.getType() == ASTNode.ARRAY_ACCESS) {
				varParent = varParent.getParent();
			}
			if (varParent.getType() == ASTNode.FIELD_ACCESS
					&& ((FieldAccess) varParent).getDispatcher() != null) {
				typeBinding = ((FieldAccess) varParent).getDispatcher()
						.resolveTypeBinding();
			} else if (varParent.getType() == ASTNode.STATIC_FIELD_ACCESS) {
				typeBinding = ((StaticFieldAccess) varParent).getClassName()
						.resolveTypeBinding();
			} else if (varParent.getType() == ASTNode.FUNCTION_NAME) {
				FunctionName fn = (FunctionName) varParent;
				if (fn.getParent().getType() == ASTNode.FUNCTION_INVOCATION) {
					FunctionInvocation fi = (FunctionInvocation) fn.getParent();
					if (fi.getParent().getType() == ASTNode.METHOD_INVOCATION
							&& ((MethodInvocation) fi.getParent())
									.getDispatcher() != null) {
						typeBinding = ((MethodInvocation) fi.getParent())
								.getDispatcher().resolveTypeBinding();
					}
				}
			} else if (varParent.getType() == ASTNode.SINGLE_FIELD_DECLARATION) {
				return resolveDeclaringClassType(var.getParent());
			}
		} else if (parent.getType() == ASTNode.FUNCTION_NAME) {
			FunctionName fn = (FunctionName) parent;
			if (fn.getParent().getType() == ASTNode.FUNCTION_INVOCATION) {
				FunctionInvocation fi = (FunctionInvocation) fn.getParent();
				if (fi.getParent().getType() == ASTNode.STATIC_METHOD_INVOCATION) {
					typeBinding = ((StaticMethodInvocation) fi.getParent())
							.getClassName().resolveTypeBinding();
				}
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
		} else if (parent.getType() == ASTNode.CONSTANT_DECLARATION) {
			ConstantDeclaration ccd = (ConstantDeclaration) parent;
			return resolveDeclaringClassType(ccd);
		} else if (parent.getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
			FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) parent;
			typeBinding = reference.getClassName().resolveTypeBinding();
			ITypeBinding temp = getTypeBinding(typeBinding, reference
					.getFunctionName().getName());
			if (temp != null) {
				return temp;
			}
		} else if (parent.getType() == ASTNode.TRAIT_ALIAS) {
			TraitAlias traitAlias = (TraitAlias) parent;
			List<NamespaceName> nameList = ((TraitUseStatement) traitAlias
					.getParent().getParent()).getTraitList();
			String memberName = null;
			if (identifier == traitAlias.getFunctionName()) {
				Expression expression = traitAlias.getTraitMethod();
				if (expression.getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
					FullyQualifiedTraitMethodReference fqtm = (FullyQualifiedTraitMethodReference) expression;
					memberName = fqtm.getFunctionName().getName();
				} else {
					memberName = ((Identifier) expression).getName();
				}
			} else {
				memberName = identifier.getName();
			}
			for (NamespaceName namespaceName : nameList) {
				typeBinding = namespaceName.resolveTypeBinding();
				ITypeBinding temp = getTypeBinding(typeBinding, memberName);
				if (temp != null) {
					return temp;
				}
			}
			return null;
		}
		if (typeBinding != null
				&& (typeBinding.isClass() || typeBinding.isTrait())
				&& typeBinding.getPHPElement() != null) {
			return typeBinding;
		}
		return null;
	}

	private ITypeBinding getTypeBinding(ITypeBinding typeBinding,
			String memberName) {
		if (typeBinding != null && typeBinding.isTrait()
				&& typeBinding.getPHPElement() != null) {
			try {
				IModelElement[] members = ((IType) typeBinding.getPHPElement())
						.getChildren();
				for (IModelElement modelElement : members) {
					if (modelElement.getElementName().equals(memberName)
							|| modelElement.getElementName().equals(
									"$" + memberName)) { //$NON-NLS-1$
						if (modelElement instanceof IMethod) {
							if (dispatcherType == null
									&& typeBinding.getPHPElement() != null) {
								isMethod = true;
							}
						} else {
							if (dispatcherType == null
									&& typeBinding.getPHPElement() != null) {
								isMethod = false;
							}
						}
						return typeBinding;
					}
				}
			} catch (ModelException e) {
			}

		}
		return null;
	}

	private boolean isDispatcherTypeEquals(Identifier identifier,
			boolean includeSuper) {

		ITypeBinding type = resolveDispatcherType(identifier);
		if (type != null && traitList != null && !traitList.isEmpty()) {
			// if (type.isTrait()) {
			// for (IType trait : traitList) {
			// if (type.getPHPElement().equals(trait)) {
			// return true;
			// }
			// }
			// } else {
			String memberName = getRealName(identifier);
			List<IType> traitList1 = type.getTraitList(isMethod, memberName,
					false);
			for (IType trait1 : traitList1) {
				for (IType trait : traitList) {
					if (trait1.equals(trait)) {
						return true;
					}
				}
			}
			// }

		}
		return type != null
				&& (type.equals(dispatcherType) || (includeSuper && (type
						.isSubTypeCompatible(dispatcherType) || dispatcherType
						.isSubTypeCompatible(type))));
	}

	private String getRealName(Identifier identifier) {
		String memberName = this.classMemberName;
		if (identifier.getParent().getType() == ASTNode.TRAIT_ALIAS) {
			TraitAlias traitAlias = (TraitAlias) identifier.getParent();
			if (identifier == traitAlias.getFunctionName()) {
				Expression expression = traitAlias.getTraitMethod();
				if (expression.getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
					FullyQualifiedTraitMethodReference fqtm = (FullyQualifiedTraitMethodReference) expression;
					memberName = fqtm.getFunctionName().getName();
				} else {
					memberName = ((Identifier) expression).getName();
				}
			}
		}
		return memberName;
	}

	/*
	 * Resolve the class declaration type for the given node. This method
	 * traverse upward to find a defining ClassDeclaration and then resolves its
	 * IType.
	 */
	protected ITypeBinding resolveDeclaringClassType(ASTNode node) {
		IType[] types = new IType[2];
		ASTNode parent = node.getParent();
		TypeDeclaration typeDeclaration = null;
		NamespaceDeclaration namespaceDeclaration = null;
		while (typeDeclaration == null && parent != null) {
			if (parent.getType() == ASTNode.CLASS_DECLARATION
					|| parent.getType() == ASTNode.INTERFACE_DECLARATION) {
				typeDeclaration = (TypeDeclaration) parent;
			}
			parent = parent.getParent();
		}
		while (namespaceDeclaration == null && parent != null) {
			if (parent.getType() == ASTNode.NAMESPACE) {
				namespaceDeclaration = (NamespaceDeclaration) parent;
			}
			parent = parent.getParent();
		}
		if (typeDeclaration != null) {
			if (namespaceDeclaration != null
					&& namespaceDeclaration.getName() != null) {
				final ISourceModule source = namespaceDeclaration
						.getProgramRoot().getSourceModule();
				types[0] = source != null ? source.getType(namespaceDeclaration
						.getName().getName()) : null;
			}
			ITypeBinding typeBinding = typeDeclaration.resolveTypeBinding();
			if (typeBinding != null) {
				return typeBinding;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences
	 * ()
	 */
	protected void findOccurrences() {
		if (isMethod) {
			fDescription = Messages.format(BASE_DESCRIPTION, classMemberName
					+ BRACKETS);
		} else {
			fDescription = Messages.format(BASE_DESCRIPTION, classMemberName);
		}

		if (erroneousNode != null) {
			// Add just this node in order to handle re-factoring properly
			fResult.add(new OccurrenceLocation(erroneousNode.getStart(),
					erroneousNode.getLength(),
					getOccurrenceType(erroneousNode), fDescription));
		} else {
			fASTRoot.accept(this);
		}
	}

	/**
	 * context + Mark var on: ... public $a; ...
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
			checkDispatch(methodInvocation.getMethod().getFunctionName()
					.getName());
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
				if (isDispatcherTypeEquals(constant, isIncludesuper)) {
					addOccurrence(new OccurrenceLocation(constant.getStart(),
							constant.getLength(), getOccurrenceType(constant),
							fDescription));
				}
			} else {
				addOccurrence(new OccurrenceLocation(constant.getStart(),
						constant.getLength(), getOccurrenceType(constant),
						fDescription));
			}
		}
		return true;
	}

	/**
	 * Mark foo() on: MyClass::foo();
	 */
	public boolean visit(StaticMethodInvocation methodInvocation) {
		if (isMethod) {
			checkDispatch(methodInvocation.getMethod().getFunctionName()
					.getName());
		}
		return super.visit(methodInvocation);
	}

	/**
	 * Mark var on: MyClass::var;
	 */
	public boolean visit(FullyQualifiedTraitMethodReference fieldAccess) {
		// if (isMethod) {
		checkDispatch(fieldAccess.getFunctionName());
		// }

		return super.visit(fieldAccess);
	}

	public boolean visit(StaticFieldAccess fieldAccess) {
		if (!isMethod) {
			checkDispatch(fieldAccess.getField().getName());
		}

		return super.visit(fieldAccess);
	}

	public boolean visit(TraitAlias node) {
		if (dispatcherType != null) {
			Expression expression = node.getTraitMethod();
			if (expression.getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
				visit((FullyQualifiedTraitMethodReference) expression);
			} else {
				checkDispatch(expression);
			}
			if (node.getFunctionName() != null) {
				checkDispatch(node.getFunctionName());
			}
			return false;
		}

		return super.visit(node);
	}

	/**
	 * @param dispatch
	 * @throws RuntimeException
	 */
	private void checkDispatch(ASTNode node) {
		while (node.getType() == ASTNode.ARRAY_ACCESS) {
			node = ((ArrayAccess) node).getName();
		}
		if (node.getType() == ASTNode.IDENTIFIER) {
			Identifier id = (Identifier) node;
			if (id.getName().equals(classMemberName)) {
				if (dispatcherType != null) {
					if (isDispatcherTypeEquals(id, isIncludesuper)) {
						if (id.getParent() instanceof Variable) {
							addOccurrence(new OccurrenceLocation(id.getParent()
									.getStart(), id.getParent().getLength(),
									getOccurrenceType(node), fDescription));
						} else {
							addOccurrence(new OccurrenceLocation(
									node.getStart(), node.getLength(),
									getOccurrenceType(node), fDescription));
						}
					}
				} else {
					addOccurrence(new OccurrenceLocation(node.getStart(),
							node.getLength(), getOccurrenceType(node),
							fDescription));
				}
			}
		}
		if (node instanceof Variable
		/* && node.getParent().getType() != ASTNode.FUNCTION_NAME */) {
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
					final Identifier functionName = classMethodDeclaration
							.getFunction().getFunctionName();
					ITypeBinding type = resolveDispatcherType(functionName);
					if (classMemberName.equals(functionName.getName())) {
						if (dispatcherType != null) {
							if (isDispatcherTypeEquals(functionName,
									isIncludesuper)) {
								addOccurrence(new OccurrenceLocation(
										functionName.getStart(),
										functionName.getLength(),
										getOccurrenceType(functionName),
										fDescription));
							}
						} else {
							addOccurrence(new OccurrenceLocation(
									functionName.getStart(),
									functionName.getLength(),
									getOccurrenceType(functionName),
									fDescription));
						}
					}
				}
			} else if (statement.getType() == ASTNode.FIELD_DECLARATION) {
				if (!isMethod) {
					FieldsDeclaration classVariableDeclaration = (FieldsDeclaration) statement;
					final Variable[] variableNames = classVariableDeclaration
							.getVariableNames();
					for (int j = 0; j < variableNames.length; j++) {
						// safe cast to identifier
						assert variableNames[j].getName().getType() == ASTNode.IDENTIFIER;

						final Identifier variable = (Identifier) variableNames[j]
								.getName();
						if (classMemberName.equals(variable.getName())) {
							if (dispatcherType != null) {
								if (isDispatcherTypeEquals(variable,
										isIncludesuper)) {
									addOccurrence(new OccurrenceLocation(
											variable.getStart() - 1,
											variable.getLength() + 1,
											F_WRITE_OCCURRENCE, fDescription));
								}
							} else {
								addOccurrence(new OccurrenceLocation(
										variable.getStart() - 1,
										variable.getLength() + 1,
										F_WRITE_OCCURRENCE, fDescription));
							}
						}
					}
				}
			} else if (statement.getType() == ASTNode.CONSTANT_DECLARATION) {
				ConstantDeclaration classVariableDeclaration = (ConstantDeclaration) statement;
				List<Identifier> variableNames = classVariableDeclaration
						.names();
				for (Identifier name : variableNames) {
					if (classMemberName.equals(name.getName())) {
						if (dispatcherType != null) {
							if (isDispatcherTypeEquals(name, isIncludesuper)) {
								addOccurrence(new OccurrenceLocation(
										name.getStart(), name.getLength(),
										getOccurrenceType(name), fDescription));
							}
						} else {
							addOccurrence(new OccurrenceLocation(
									name.getStart(), name.getLength(),
									getOccurrenceType(name), fDescription));
						}
					}
				}
			}
		}
		// }
		body.accept(this);
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
		if (node.getParent().getType() == ASTNode.CONSTANT_DECLARATION
				|| isInAssignment(node)) {
			return IOccurrencesFinder.F_WRITE_OCCURRENCE;
		}
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/**
	 * Check if the given node is a variable in a field access that exists in an
	 * assignment expression.
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
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return classMemberName;
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
