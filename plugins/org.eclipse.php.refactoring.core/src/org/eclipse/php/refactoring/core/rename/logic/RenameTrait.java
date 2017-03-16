/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename.logic;

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;

/**
 * This visitor locates the identifiers we need to change given a class /
 * interface name
 * 
 * @author Roy, 2007
 */
public class RenameTrait extends AbstractRename {

	private static final String RENAME_CLASS = PhpRefactoringCoreMessages.getString("RenameClassName.0"); //$NON-NLS-1$
	private ASTNode originalDeclaration;
	private IType[] types;

	public RenameTrait(IFile file, ASTNode originalNode, String oldName, String newName, boolean searchTextual,
			IType[] types) {
		super(file, oldName, newName, searchTextual);
		if (originalNode != null) {
			originalDeclaration = RefactoringUtility.getTypeOrClassInstance(originalNode);
		}
		this.types = types;
	}

	public boolean visit(StaticConstantAccess staticDispatch) {
		Expression className = staticDispatch.getClassName();
		Identifier identifier = null;
		if (className instanceof Variable) {
			identifier = (Identifier) ((Variable) className).getName();
		}

		if (className instanceof Identifier) {
			identifier = (Identifier) className;
		}
		if (identifier != null) {
			checkIdentifier(identifier);
		}
		return false;
	}

	public boolean visit(StaticFieldAccess staticDispatch) {
		checkIdentifier((Identifier) staticDispatch.getClassName());
		return false;
	}

	public boolean visit(StaticMethodInvocation staticDispatch) {
		checkIdentifier((Identifier) staticDispatch.getClassName());
		return false;
	}

	public boolean visit(ClassName className) {
		if (className.getName() instanceof Identifier) {
			Identifier identifier = (Identifier) className.getName();

			if (identifier instanceof NamespaceName) {
				NamespaceName namespaceName = (NamespaceName) identifier;
				if (namespaceName.segments() != null && namespaceName.segments().size() > 0) {
					identifier = namespaceName.segments().get(namespaceName.segments().size() - 1);
				}
			}

			checkIdentifier(identifier);
		}
		return false;
	}

	public boolean visit(TraitDeclaration classDeclaration) {
		ITypeBinding originalType = null;

		if (originalDeclaration != null) {
			if (originalDeclaration instanceof TraitDeclaration) {
				originalType = ((TraitDeclaration) originalDeclaration).resolveTypeBinding();
			} else if (originalDeclaration instanceof NamespaceName) {
				originalType = ((NamespaceName) originalDeclaration).resolveTypeBinding();
			}
		}

		ITypeBinding currType = classDeclaration.resolveTypeBinding();
		if (originalDeclaration == null || originalDeclaration.getStart() == classDeclaration.getStart()
				|| (originalType != null && originalType.equals(currType))
				|| (originalType != null && originalType.isSubTypeCompatible(currType))) {
			checkIdentifier(classDeclaration.getName());
		}

		return true;
	}

	public boolean visit(TraitUseStatement part) {
		for (NamespaceName namespace : part.getTraitList()) {
			if (namespace instanceof Identifier) {
				checkIdentifier(namespace);
			}
		}
		return true;
	}

	public boolean visit(TraitPrecedenceStatement tps) {
		if (tps.getPrecedence().getMethodReference().getClassName() instanceof Identifier) {
			checkIdentifier(tps.getPrecedence().getMethodReference().getClassName());
		}
		for (NamespaceName namespace : tps.getPrecedence().getTrList()) {
			if (namespace instanceof Identifier) {
				checkIdentifier(namespace);
			}
		}
		return false;
	}

	public boolean visit(TraitAliasStatement tas) {
		int type = tas.getAlias().getTraitMethod().getType();
		if (type == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
			FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) tas.getAlias()
					.getTraitMethod();
			if (reference.getClassName() instanceof Identifier) {
				checkIdentifier(reference.getClassName());
			}
		}
		return false;
	}

	/**
	 * @param identifier
	 */
	protected void checkIdentifier(Identifier identifier) {
		if (checkForNameEquality(identifier)) {
			if (types == null || types.length == 0) {
				addChange(identifier);
				return;
			} else {
				try {
					IModelElement[] elements = identifier.getProgramRoot().getSourceModule()
							.codeSelect(identifier.getStart(), 0);
					for (int i = 0; i < elements.length; i++) {
						for (int j = 0; j < types.length; j++) {
							if (elements[i] instanceof IType) {
								if (elements[i].equals(types[j])) {
									addChange(identifier);
									return;
								}
							}
						}
					}
				} catch (ModelException e) {
				}
			}
		}
	}

	private boolean checkForNameEquality(Identifier identifier) {
		return identifier != null && identifier.getName() != null && identifier.getName().equalsIgnoreCase(oldName);
	}

	public String getRenameDescription() {
		return RenameTrait.RENAME_CLASS;
	}
}
