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
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents state when staying in a class static member completion (after paamayim-nekudotaim)
 * <br/>Examples:
 * <pre>
 *  1. A::|
 *  2. $lsb::|
 *  3. A::$|
 *  etc...
 * </pre>
 * @author michael
 */
public class ClassStaticMemberContext extends ClassMemberContext {

	private boolean isParent;
	private boolean isDirectParent;
	private boolean isSelf;
	private boolean isDirectSelf;

	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (getTriggerType() != Trigger.CLASS) {
			return false;
		}

		int elementStart = getElementStart();
		int lhsIndex = elementStart - "parent".length() - getTriggerType().getName().length();
		if (lhsIndex >= 0) {
			TextSequence statementText = getStatementText();
			String parentText = statementText.subSequence(lhsIndex, elementStart - getTriggerType().getName().length()).toString();
			if (parentText.equals("parent")) { //$NON-NLS-1$
				isParent = isDirectParent = true;
			}
		}

		lhsIndex = elementStart - "self".length() - getTriggerType().getName().length();
		if (lhsIndex >= 0) {
			TextSequence statementText = getStatementText();
			String parentText = statementText.subSequence(lhsIndex, elementStart - getTriggerType().getName().length()).toString();
			if (parentText.equals("self")) { //$NON-NLS-1$
				isSelf = isDirectSelf = true;
			}
		}

		if (!isParent || !isSelf) {
			IType[] types = getLhsTypes();
			if (types != null && types.length > 0) {
				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
				if (moduleDeclaration != null) {
					IContext context = ASTUtils.findContext(sourceModule, moduleDeclaration, offset);
					if (context instanceof MethodContext) {
						IEvaluatedType instanceType = ((MethodContext) context).getInstanceType();
						if (instanceType instanceof PHPClassType) {
							PHPClassType classType = (PHPClassType) instanceType;
							String typeName = classType.getTypeName();
							String namespace = classType.getNamespace();
							if (namespace != null && namespace.length() > 0) {
								int i = typeName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
								if (i != -1) {
									typeName = typeName.substring(i + 1);
								}
								if (namespace.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
									namespace = namespace.substring(1);
								}
								IType currentNamespace = PHPModelUtils.getCurrentNamespace(types[0]);
								if (!isParent) {
									if (currentNamespace != null && types.length >= 2) {
										IType parentType = types[1];
										isParent = namespace.equals(currentNamespace.getElementName()) && typeName.equals(parentType.getElementName());
									}
								}
								if (!isSelf) {
									if (currentNamespace != null) {
										isSelf = namespace.equals(currentNamespace.getElementName()) && typeName.equals(types[0].getElementName());
									}
								}
							} else {
								if (!isParent) {
									if (types.length >= 2) {
										IType parentType = types[1];
										isParent = typeName.equals(parentType.getElementName());
									}
								}
								if (!isSelf) {
									isSelf = typeName.equals(types[0].getElementName());
								}
							}
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * Returns whether the left hand side expression has the type of parent class
	 */
	public boolean isParent() {
		return isParent;
	}

	/**
	 * Returns whether the left hand side expression has the type of this class
	 */
	public boolean isSelf() {
		return isSelf;
	}

	/**
	 * Returns whether the left hand side is a word 'parent'
	 */
	public boolean isDirectParent() {
		return isDirectParent;
	}

	/**
	 * Returns whether the left hand side is a word 'self'
	 */
	public boolean isDirectSelf() {
		return isDirectSelf;
	}
}
