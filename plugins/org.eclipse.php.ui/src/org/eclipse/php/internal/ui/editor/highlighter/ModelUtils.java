/*******************************************************************************
 * Copyright (c) 2006, 2009 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighter;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.index.IPHPDocAwareElement;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

public abstract class ModelUtils {

	static public IField getField(FieldAccess fieldAccess) {
		ITypeBinding type = fieldAccess.getDispatcher().resolveTypeBinding();
		String fieldName = getFieldName(fieldAccess.getMember());
		if (type != null && fieldName != null) {
			IVariableBinding[] fields = type.getDeclaredFields();
			for (IVariableBinding field : fields) {
				if (field.getName().substring(1).toLowerCase()
						.equals(fieldName.toLowerCase())) {
					return (IField) field.getPHPElement();
				}
			}
		}
		return null;
	}

	static public IMethod getMethod(MethodInvocation methodInvocation) {
		ITypeBinding type = methodInvocation.getDispatcher()
				.resolveTypeBinding();
		String methodName = getFunctionName(methodInvocation.getMethod()
				.getFunctionName());
		if (type != null && methodName != null) {
			IMethodBinding[] methods = type.getDeclaredMethods();
			for (IMethodBinding method : methods) {
				if (method.getName().toLowerCase()
						.equals(methodName.toLowerCase())) {
					return (IMethod) method.getPHPElement();
				}
			}
		}
		return null;
	}

	static public IMethod getMethod(MethodDeclaration methodDeclaration) {
		ITypeBinding type = methodDeclaration.resolveMethodBinding()
				.getDeclaringClass();

		String methodName = methodDeclaration.getFunction().getFunctionName()
				.getName();
		if (type != null && methodName != null) {
			IMethodBinding[] methods = type.getDeclaredMethods();
			for (IMethodBinding method : methods) {
				if (method.getName().toLowerCase()
						.equals(methodName.toLowerCase())) {
					return (IMethod) method.getPHPElement();
				}
			}
		}
		return null;
	}

	static public Collection<ISourceRange> getDeprecatedElements(
			IModelElement element) {
		Collection<ISourceRange> elements = new LinkedList<ISourceRange>();

		try {
			if (ModelUtils.isDeprecated(element)) {
				elements.add(((IMember) element).getNameRange());
			}

			IModelElement[] children = ((IParent) element).getChildren();
			for (IModelElement child : children) {
				if (ModelUtils.isDeprecated(child)) {
					elements.add(((IMember) child).getNameRange());
				}
			}
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elements;
	}

	static public boolean isDeprecated(IModelElement element) {
		if (element instanceof IPHPDocAwareElement) {
			return ((IPHPDocAwareElement) element).isDeprecated();
		}
		return isDeprecated(getPHPDoc(element));
	}

	static public PHPDocBlock getPHPDoc(IModelElement element) {
		PHPDocBlock doc = null;
		if (element instanceof IField) {
			doc = PHPModelUtils.getDocBlock((IField) element);
		} else if (element instanceof IMethod) {
			doc = PHPModelUtils.getDocBlock((IMethod) element);
		} else if (element instanceof IType) {
			doc = PHPModelUtils.getDocBlock((IType) element);
		}
		return doc;
	}

	static private boolean isDeprecated(PHPDocBlock doc) {
		return doc != null && doc.getTags(PHPDocTag.DEPRECATED).length > 0;
	}

	static private String getFieldName(VariableBase variable) {
		if (variable instanceof Variable) {
			return getName((Variable) variable);
		}
		return null;
	}

	static public String getFunctionName(FunctionName func) {
		Expression name = func.getName();
		if (name instanceof Variable) {
			return getName((Variable) name);
		} else if (name instanceof Identifier) {
			return ((Identifier) name).getName();
		}
		return null;
	}

	static private String getName(Variable variable) {
		Expression varName = variable.getName();
		if (varName instanceof Identifier) {
			return ((Identifier) varName).getName();
		} else if (variable.getName() instanceof Variable) {
			return getName((Variable) varName);
		}
		return null;
	}

	static public boolean isExternalElement(IModelElement element) {
		element = element.getAncestor(IModelElement.PROJECT_FRAGMENT);
		if (element instanceof ExternalProjectFragment) {
			ExternalProjectFragment fragment = (ExternalProjectFragment) element;
			if (fragment.isExternal()) {
				return true;
			}
		}
		return false;
	}
}
