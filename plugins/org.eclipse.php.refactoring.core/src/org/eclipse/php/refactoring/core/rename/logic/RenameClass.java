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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.*;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;

/**
 * This visitor locates the identifiers we need to change given a class /
 * interface name
 * 
 * @author Roy, 2007
 */
public class RenameClass extends AbstractRename {

	private static final String RENAME_CLASS = PhpRefactoringCoreMessages.getString("RenameClassName.0"); //$NON-NLS-1$
	private ASTNode originalDeclaration;
	private IType[] types;

	public RenameClass(IFile file, ASTNode originalNode, String oldName, String newName, boolean searchTextual,
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

	public boolean visit(UseStatement useStatement) {
		List<UseStatementPart> parts = useStatement.parts();
		for (UseStatementPart useStatementPart : parts) {
			List<Identifier> idList = useStatementPart.getName().segments();
			if (!idList.isEmpty()) {
				checkIdentifier(idList.get(idList.size() - 1));
			}

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

	public boolean visit(ClassDeclaration classDeclaration) {
		ITypeBinding originalType = null;

		if (originalDeclaration != null) {
			if (originalDeclaration instanceof TypeDeclaration) {
				originalType = ((TypeDeclaration) originalDeclaration).resolveTypeBinding();
			}
			if (originalDeclaration instanceof ClassInstanceCreation) {
				originalType = ((ClassInstanceCreation) originalDeclaration).resolveTypeBinding();
			}
		}

		ITypeBinding currType = classDeclaration.resolveTypeBinding();
		if (originalDeclaration == null || currType == null
				|| originalDeclaration.getStart() == classDeclaration.getStart()
				|| (originalType != null && originalType.equals(currType))
				|| (originalType != null && originalType.isSubTypeCompatible(currType))) {
			checkIdentifier(classDeclaration.getName());
		}

		checkSuper((Identifier) classDeclaration.getSuperClass(), classDeclaration.interfaces());

		return true;
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		if (originalDeclaration == null || originalDeclaration.getStart() == interfaceDeclaration.getStart()) {
			checkIdentifier(interfaceDeclaration.getName());
		}
		checkSuper(null, interfaceDeclaration.interfaces());

		return true;
	}

	public boolean visit(CatchClause catchStatement) {
		for (Expression className : catchStatement.getClassNames()) {
			checkIdentifier((Identifier) className);
		}
		return true;
	}

	public boolean visit(FormalParameter formalParameter) {
		checkIdentifier((Identifier) formalParameter.getParameterType());
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
				checkIdentifier(functionName);
			}
		}
		return true;
	}

	public boolean visit(FunctionDeclaration function) {
		boolean result = super.visit(function);
		if (this.searchTextual) {
			return result;
		}
		IMethod method = ModelUtils.getFunctionMethod(function);
		if (method != null) {
			IType currentNamespace = PHPModelUtils.getCurrentNamespace(method);
			ISourceModule sm = method.getSourceModule();
			PHPDocBlock doc = ModelUtils.getPHPDoc(method);
			if (doc == null) {
				return result;
			}
			PHPDocTag[] tags = doc.getTags();
			for (PHPDocTag tag : tags) {
				List<TypeReference> matchRefs = new ArrayList<TypeReference>();
				for (TypeReference ref : tag.getTypeReferences()) {
					if (ref.getName().equals(oldName)) {
						IType[] elements = ModelUtils.getTypes(oldName, sm, doc.sourceStart(), currentNamespace);
						for (int i = 0; i < elements.length; i++) {
							for (int j = 0; j < types.length; j++) {
								if (elements[i].equals(types[j])) {
									matchRefs.add(ref);
								}
							}
						}
					}
				}
				for (TypeReference ref : matchRefs) {
					addChange(ref.sourceStart(), getRenameDescription());
				}
			}
		}
		return result;
	}

	/**
	 * Checks if the supers are with the name of the class
	 * 
	 * @param superClass
	 * @param interfaces
	 */
	private void checkSuper(Identifier superClass, List<Identifier> interfaces) {
		if (superClass != null) {
			if (superClass instanceof NamespaceName) {
				NamespaceName namespaceName = (NamespaceName) superClass;
				if (namespaceName.segments() != null && namespaceName.segments().size() > 0) {
					superClass = namespaceName.segments().get(namespaceName.segments().size() - 1);
				}

			}
			checkIdentifier(superClass);
		}

		if (interfaces != null) {
			for (Identifier identifier : interfaces) {

				if (identifier instanceof NamespaceName) {
					NamespaceName namespaceName = (NamespaceName) identifier;
					if (namespaceName.segments() != null && namespaceName.segments().size() > 0) {
						identifier = namespaceName.segments().get(namespaceName.segments().size() - 1);
					}

				}
				checkIdentifier(identifier);
			}
		}
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
							} else if (elements[i] instanceof IMethod) {
								if (((IMethod) elements[i]).getDeclaringType().equals(types[j])) {
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
		return identifier != null && identifier.getName() != null && identifier.getName().equals(oldName);
	}

	public String getRenameDescription() {
		return RenameClass.RENAME_CLASS;
	}
}
