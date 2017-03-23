/*******************************************************************************
 * Copyright (c) 2006, 2015, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename.logic;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.RefactoringPlugin;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;

/**
 * This visitor locates the identifiers we need to change given a class property
 * 
 * @author Roy, 2007
 */
public class RenameClassMember extends AbstractRename {

	private int nodeType;

	private static final String RENAME_CLASS_MEMBER = PhpRefactoringCoreMessages.getString("RenameClassPropertyName.0"); //$NON-NLS-1$
	// can be null
	private ITypeBinding type;

	private List<IType> traitList;
	private List<IType> traitListIncludingSuperClass;

	private Boolean isTraitMethod;

	public RenameClassMember(IFile file, String oldName, String newName, boolean searchTextual, int type) {
		super(file, oldName, newName, searchTextual);
		this.nodeType = type;
	}

	public RenameClassMember(IFile file, String oldName, String newName, boolean searchTextual,
			ITypeBinding iTypeBinding, int type, ASTNode identifier) {
		this(file, oldName, newName, searchTextual, type);
		this.type = iTypeBinding;

		PHPVersion phpVersion = PHPVersion.PHP5_4;
		if (file.getProject() != null) {
			phpVersion = ProjectOptions.getPHPVersion(file.getProject());
		}
		if (iTypeBinding != null && phpVersion.isGreaterThan(PHPVersion.PHP5_3)) {
			String memberName = oldName;
			if (identifier instanceof Identifier && (identifier.getParent().getType() == ASTNode.TRAIT_ALIAS
					|| identifier.getParent().getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE)) {
				memberName = getRealName(identifier);

			}
			isTraitMethod = isTraitMethod(iTypeBinding, memberName);
			traitList = iTypeBinding.getTraitList(isTraitMethod, memberName, false);
			traitListIncludingSuperClass = iTypeBinding.getTraitList(isTraitMethod, memberName, true);
		}

		// if (isTraitMethod == null) {
		// isTraitMethod = isChangeMethod();
		// }

	}

	private boolean isTraitMethod(ITypeBinding typeBinding, String memberName) {
		if (typeBinding != null && typeBinding.isTrait() && typeBinding.getPHPElement() != null) {
			try {
				IModelElement[] members = ((IType) typeBinding.getPHPElement()).getChildren();
				for (IModelElement modelElement : members) {
					if (modelElement.getElementName().equals(memberName)) {
						if (modelElement instanceof IMethod) {
							return true;
						} else {
							return false;
						}
					}
				}
			} catch (ModelException e) {
			}

		}
		return isChangeMethod();
	}

	private String getRealName(ASTNode node) {
		String memberName = this.oldName;
		if (node instanceof Identifier) {
			Identifier identifier = (Identifier) node;
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
		}
		return memberName;
	}

	/**
	 * context + Rename foo on: ... public foo() { }; ...
	 */
	public boolean visit(MethodDeclaration methodDeclaration) {
		if (isChangeMethod()) {
			try {
				Identifier identifier = methodDeclaration.getFunction().getFunctionName();
				if (!identifier.getName().equals(oldName) || methodDeclaration.resolveMethodBinding() == null) {
					return super.visit(methodDeclaration);
				}
				ITypeBinding declClass = methodDeclaration.resolveMethodBinding().getDeclaringClass();
				if (declClass != null) {
					if (declClass.equals(type) || traitEqual(declClass, identifier)) {
						addChange(identifier.getStart());
					} else if (type != null
							&& (declClass.isSubTypeCompatible(type) || type.isSubTypeCompatible(declClass))) {
						if (methodDeclaration.getModifier() != Modifiers.AccPrivate) {
							addChange(identifier.getStart());
						}
					} else if (traitInSuperEqual(declClass, identifier)) {
						addChange(identifier.getStart());
					}
				}
			} catch (Exception e) {
				RefactoringPlugin.logException(e);
			}

		}

		return super.visit(methodDeclaration);
	}

	private boolean traitEqual(ITypeBinding declClass, ASTNode node) {
		if (declClass != null && traitList != null && !traitList.isEmpty()) {
			if (node instanceof Variable) {
				node = getIdentifer((Variable) node);
			}
			if (node instanceof Identifier) {
				Identifier identifier = (Identifier) node;
				String memberName = getRealName(identifier);
				List<IType> traitList1 = declClass.getTraitList(isChangeMethod(), memberName, false);
				for (IType trait1 : traitList1) {
					for (IType trait : traitList) {
						if (trait1.equals(trait)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean traitInSuperEqual(ITypeBinding declClass, ASTNode node) {
		if (declClass != null && traitListIncludingSuperClass != null && !traitListIncludingSuperClass.isEmpty()) {
			if (node instanceof Variable) {
				node = getIdentifer((Variable) node);
			}
			if (node instanceof Identifier) {
				Identifier identifier = (Identifier) node;
				String memberName = getRealName(identifier);
				List<IType> traitList1 = declClass.getTraitList(isChangeMethod(), memberName, true);
				for (IType trait1 : traitList1) {
					for (IType trait : traitListIncludingSuperClass) {
						if (trait1.equals(trait)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean isChangeMethod() {
		return nodeType == ASTNode.METHOD_DECLARATION || nodeType == ASTNode.FUNCTION_DECLARATION
				|| nodeType == ASTNode.FUNCTION_NAME || (isTraitMethod != null && isTraitMethod);
	}

	@Override
	public boolean visit(ConstantDeclaration classConstantDeclaration) {
		if (isChangeConstant()) {
			final List<Identifier> variableNames = classConstantDeclaration.names();
			for (int j = 0; j < variableNames.size(); j++) {
				// safe cast to identifier
				assert variableNames.get(j) instanceof Identifier;
				final Identifier variable = (Identifier) variableNames.get(j);
				handleIdentifier(variable);
			}
		}

		return super.visit(classConstantDeclaration);
	}

	private boolean isChangeConstant() {
		return nodeType == ASTNode.STATIC_CONSTANT_ACCESS || nodeType == ASTNode.CONSTANT_DECLARATION
				|| (isTraitMethod != null && !isTraitMethod);
	}

	/**
	 * context + Rename var on: ... public $a; ...
	 */
	@Override
	public boolean visit(FieldsDeclaration fieldsDeclaration) {
		if (isChangeField()) {
			// Work around for getting type binding of class field.
			// Get the TypeDeclaration of the field at first and then get the
			// type binding.
			TypeDeclaration typeDecl = RefactoringUtility.getType(fieldsDeclaration);
			ITypeBinding declClass = null;
			if (typeDecl != null) {
				declClass = typeDecl.resolveTypeBinding();
			}

			if (declClass != null) {
				final Variable[] variableNames = fieldsDeclaration.getVariableNames();
				for (int j = 0; j < variableNames.length; j++) {
					// safe cast to identifier
					if (declClass.equals(type) || traitEqual(declClass, variableNames[j].getName())) {
						assert variableNames[j].getName() instanceof Identifier;
						final Identifier variable = (Identifier) variableNames[j].getName();
						handleIdentifier(variable);
					} else if (type != null
							&& (declClass.isSubTypeCompatible(type) || type.isSubTypeCompatible(declClass))) {
						if (fieldsDeclaration.getModifier() != Modifiers.AccPrivate) {
							final Identifier variable = (Identifier) variableNames[j].getName();
							handleIdentifier(variable);
						}
					} else if (traitInSuperEqual(declClass, (Identifier) variableNames[j].getName())) {
						final Identifier variable = (Identifier) variableNames[j].getName();
						handleIdentifier(variable);
					}
				}
			}
		}
		return super.visit(fieldsDeclaration);
	}

	private boolean isChangeField() {
		return nodeType == ASTNode.FIELD_DECLARATION || nodeType == ASTNode.VARIABLE
				|| (isTraitMethod != null && !isTraitMethod);
	}

	/**
	 * Rename foo() on: $a->foo();
	 */
	public boolean visit(MethodInvocation methodInvocation) {
		if (isChangeMethod() && methodInvocation.getDispatcher() != null) {
			ITypeBinding declClass = methodInvocation.getDispatcher().resolveTypeBinding();
			if (declClass != null) {
				if (declClass.equals(type)
						|| traitEqual(declClass, methodInvocation.getMethod().getFunctionName().getName())) {
					handleDispatch(methodInvocation);
				} else if (type != null
						&& (declClass.isSubTypeCompatible(type) || type.isSubTypeCompatible(declClass))) {
					IMethodBinding methodBinding = methodInvocation.resolveMethodBinding();
					if (methodBinding != null && methodBinding.getModifiers() != Modifiers.AccPrivate) {
						handleDispatch(methodInvocation);
					}
				} else if (traitInSuperEqual(declClass, methodInvocation.getMethod().getFunctionName().getName())) {
					handleDispatch(methodInvocation);
				}
			}
		}
		return super.visit(methodInvocation);
	}

	private void handleDispatch(Dispatch dispatch) {
		VariableBase variable = (VariableBase) dispatch.getMember();
		Identifier identifier = null;
		if (variable instanceof Variable) {
			identifier = getIdentifer((Variable) variable);
		}
		if (variable instanceof FunctionInvocation) {
			Variable functionName = (Variable) ((FunctionInvocation) variable).getFunctionName().getName();
			identifier = getIdentifer(functionName);
		}
		if (identifier != null && identifier.getName().equals(oldName)) {
			// ITypeBinding typeBinding = dispatch.getDispatcher()
			// .resolveTypeBinding();

			// if (typeBinding != null && type != null) {
			// if (typeBinding.equals(type)
			// || type.isSubTypeCompatible(typeBinding)
			// || typeBinding.isSubTypeCompatible(type)) {
			addChange(identifier.getStart());
			// }
			// }
		}
	}

	private Identifier getIdentifer(Variable node) {
		Expression temp = node.getName();

		while (temp != null && !(temp instanceof Identifier)) {
			if (temp instanceof Variable) {
				temp = ((Variable) temp).getName();
			} else {
				return null;
			}
		}
		return (Identifier) temp;
	}

	private void handleIdentifier(Identifier identifier) {
		if (identifier.getName().equals(oldName)) {
			addChange(identifier.getStart());
		}
	}

	/**
	 * Rename var on: $a->var;
	 */
	public boolean visit(FieldAccess fieldAccess) {
		if (isChangeField()) {
			ITypeBinding declClass = fieldAccess.getDispatcher().resolveTypeBinding();
			if (declClass != null) {
				if (declClass.equals(type) || traitEqual(declClass, fieldAccess.getMember())) {
					handleDispatch(fieldAccess);
				} else if (type != null
						&& (declClass.isSubTypeCompatible(type) || type.isSubTypeCompatible(declClass))) {
					IVariableBinding binding = fieldAccess.resolveFieldBinding();

					if (binding != null && binding.getModifiers() != Modifiers.AccPrivate) {
						handleDispatch(fieldAccess);
					}
				} else if (traitInSuperEqual(declClass, fieldAccess.getMember())) {
					handleDispatch(fieldAccess);
				}
			}
		}
		return super.visit(fieldAccess);
	}

	public boolean visit(StaticConstantAccess classConstantAccess) {
		handlStaticDispatch(classConstantAccess);
		return super.visit(classConstantAccess);
	}

	/**
	 * Rename foo() on: MyClass::foo();
	 */
	public boolean visit(StaticMethodInvocation methodInvocation) {
		if (isChangeMethod()) {
			handlStaticDispatch(methodInvocation);
		}
		return super.visit(methodInvocation);
	}

	private void handlStaticDispatch(StaticDispatch dispatch) {
		ASTNode member = dispatch.getMember();
		Identifier identifier = null;
		VariableBase variable = null;
		if (member instanceof Variable) {
			variable = (VariableBase) dispatch.getMember();
		}

		if (member instanceof FunctionInvocation) {
			variable = (VariableBase) member;
		}

		if (variable instanceof Variable) {
			identifier = getIdentifer((Variable) variable);
		}
		if (variable instanceof FunctionInvocation) {
			Expression functionName = ((FunctionInvocation) variable).getFunctionName().getName();
			if (functionName instanceof Variable) {
				identifier = getIdentifer((Variable) functionName);
			}
			if (functionName instanceof Identifier) {
				identifier = (Identifier) functionName;
			}
		}

		if (member instanceof Identifier) {
			identifier = (Identifier) member;
		}
		if (identifier != null && identifier.getName().equals(oldName)) {
			ITypeBinding typeBinding = dispatch.getClassName().resolveTypeBinding();
			if (typeBinding != null) {
				if (typeBinding.equals(type)
						|| (type != null
								&& (type.isSubTypeCompatible(typeBinding) || typeBinding.isSubTypeCompatible(type)))
						|| traitEqual(typeBinding, identifier) || traitInSuperEqual(typeBinding, identifier)) {
					addChange(identifier.getStart());
				}
			}
		}
	}

	/**
	 * Rename var on: MyClass::var;
	 */
	public boolean visit(StaticFieldAccess fieldAccess) {
		if (isChangeField()) {
			handlStaticDispatch(fieldAccess);
		}
		return super.visit(fieldAccess);
	}

	public boolean visit(TraitAlias node) {
		if (type != null) {
			Expression expression = node.getTraitMethod();
			if (expression.getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
				checkIdentifier(((FullyQualifiedTraitMethodReference) expression).getFunctionName(),
						(node.getModifier() & Modifiers.AccPrivate) != 0);
			} else if (expression.getType() == ASTNode.IDENTIFIER) {
				checkIdentifier((Identifier) expression, (node.getModifier() & Modifiers.AccPrivate) != 0);
			}
			if (node.getFunctionName() != null) {
				checkIdentifier(node.getFunctionName(), (node.getModifier() & Modifiers.AccPrivate) != 0);
			}
		}
		return false;
	}

	private void checkIdentifier(Identifier identifier, boolean isPrivate) {
		if (identifier.getName().equals(oldName)) {
			ITypeBinding declClass = resolveDispatcherType(identifier);
			if (declClass != null) {
				if (declClass.equals(type) || traitEqual(declClass, identifier)) {
					addChange(identifier.getStart());
				} else if (type != null
						&& (declClass.isSubTypeCompatible(type) || type.isSubTypeCompatible(declClass))) {
					if (!isPrivate) {
						addChange(identifier.getStart());
					}
				} else if (traitInSuperEqual(declClass, identifier)) {
					addChange(identifier.getStart());
				}
			}
		}
	}

	private ITypeBinding resolveDispatcherType(Identifier identifier) {
		ITypeBinding typeBinding = null;
		ASTNode parent = identifier.getParent();
		if (parent.getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
			FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) parent;
			typeBinding = reference.getClassName().resolveTypeBinding();
			ITypeBinding temp = getTypeBinding(typeBinding, reference.getFunctionName().getName());
			if (temp != null) {
				return temp;
			}
		} else if (parent.getType() == ASTNode.TRAIT_ALIAS) {
			TraitAlias traitAlias = (TraitAlias) parent;
			List<NamespaceName> nameList = ((TraitUseStatement) traitAlias.getParent().getParent()).getTraitList();
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
				// if (typeBinding != null && typeBinding.isTrait()
				// && typeBinding.getPHPElement() != null) {
				// try {
				// IModelElement[] members = ((IType) typeBinding
				// .getPHPElement()).getChildren();
				// for (IModelElement modelElement : members) {
				// if (modelElement.getElementName()
				// .equals(memberName)) {
				// return typeBinding;
				// }
				// }
				// } catch (ModelException e) {
				// }
				//
				// }
			}
			return null;
		}
		return typeBinding;
	}

	private ITypeBinding getTypeBinding(ITypeBinding typeBinding, String memberName) {
		if (typeBinding != null && typeBinding.isTrait() && typeBinding.getPHPElement() != null) {
			try {
				IModelElement[] members = ((IType) typeBinding.getPHPElement()).getChildren();
				for (IModelElement modelElement : members) {
					if (modelElement.getElementName().equals(memberName)
							|| modelElement.getElementName().equals("$" + memberName)) { //$NON-NLS-1$
						return typeBinding;
					}
				}
			} catch (ModelException e) {
			}

		}
		return null;
	}

	public String getRenameDescription() {
		return RenameClassMember.RENAME_CLASS_MEMBER;
	}

	public void close() {
		groups.clear();
	}
}
