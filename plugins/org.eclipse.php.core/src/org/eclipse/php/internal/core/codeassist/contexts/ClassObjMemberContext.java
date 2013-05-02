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
 * This context represents state when staying in an object member completion <br/>
 * Examples:
 * 
 * <pre>
 *  1. $this-&gt;|
 *  2. $a-&gt;|
 *  3. $a-&gt;fo|
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public class ClassObjMemberContext extends ClassMemberContext {

	private boolean isThis;
	private boolean isDirectThis;
	private boolean isParent;

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (getTriggerType() != Trigger.OBJECT) {
			return false;
		}

		int elementStart = getElementStart();
		int lhsIndex = elementStart - "$this".length() //$NON-NLS-1$
				- getTriggerType().getName().length();
		if (lhsIndex >= 0) {
			TextSequence statementText = getStatementText();
			String parentText = statementText.subSequence(lhsIndex,
					elementStart - getTriggerType().getName().length())
					.toString();
			if (parentText.equals("$this")) { //$NON-NLS-1$
				isThis = isDirectThis = true;
			}
		}

		if (!isThis) {
			lhsIndex = elementStart - "$parent".length() //$NON-NLS-1$
					- getTriggerType().getName().length();
			if (lhsIndex >= 0) {
				TextSequence statementText = getStatementText();
				String parentText = statementText.subSequence(lhsIndex,
						elementStart - getTriggerType().getName().length())
						.toString();
				if (parentText.equals("$parent")) { //$NON-NLS-1$
					isParent = true;
				}
			}

			IType[] types = getLhsTypes();
			if (types != null && types.length > 0) {
				ModuleDeclaration moduleDeclaration = SourceParserUtil
						.getModuleDeclaration(sourceModule);
				if (moduleDeclaration != null) {
					IContext context = ASTUtils.findContext(sourceModule,
							moduleDeclaration, offset);
					if (context instanceof MethodContext) {
						IEvaluatedType instanceType = ((MethodContext) context)
								.getInstanceType();
						if (instanceType instanceof PHPClassType) {
							PHPClassType classType = (PHPClassType) instanceType;
							String namespace = classType.getNamespace();
							if (namespace != null && namespace.length() > 0) {
								String typeName = classType.getTypeName();
								int i = typeName
										.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
								if (i != -1) {
									typeName = typeName.substring(i + 1);
								}
								if (namespace.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
									namespace = namespace.substring(1);
								}
								IType currentNamespace = PHPModelUtils
										.getCurrentNamespace(types[0]);
								if (currentNamespace != null) {
									isThis = namespace.equals(currentNamespace
											.getElementName())
											&& typeName.equals(types[0]
													.getElementName());
								}
							} else {
								isThis = classType.getTypeName().equals(
										types[0].getElementName());
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Returns whether the left hand side expression has the type of this class
	 */
	public boolean isThis() {
		return isThis;
	}

	/**
	 * Returns whether the left hand side is a variable '$this'
	 */
	public boolean isDirectThis() {
		return isDirectThis;
	}

	public boolean isParent() {
		return isParent;
	}
}
