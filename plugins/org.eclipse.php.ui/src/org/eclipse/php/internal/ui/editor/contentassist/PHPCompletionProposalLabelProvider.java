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
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;

public class PHPCompletionProposalLabelProvider extends
		CompletionProposalLabelProvider {

	private static final String ENCLOSING_TYPE_SEPARATOR = new String(
			new char[] { NamespaceReference.NAMESPACE_SEPARATOR }); //$NON-NLS-1$

	protected String createMethodProposalLabel(CompletionProposal methodProposal) {
		StringBuffer nameBuffer = new StringBuffer();

		// method name
		nameBuffer.append(methodProposal.getName());

		// parameters
		nameBuffer.append('(');
		appendUnboundedParameterList(nameBuffer, methodProposal);
		nameBuffer.append(')');

		IMethod method = (IMethod) methodProposal.getModelElement();
		nameBuffer.append(" - "); //$NON-NLS-1$

		IModelElement parent = method.getParent();
		if (parent instanceof IType) {
			IType type = (IType) parent;
			nameBuffer.append(type
					.getTypeQualifiedName(ENCLOSING_TYPE_SEPARATOR)); //$NON-NLS-1$
		} else {
			nameBuffer.append(parent.getElementName());
		}

		return nameBuffer.toString();
	}

	protected String createOverrideMethodProposalLabel(
			CompletionProposal methodProposal) {
		StringBuffer nameBuffer = new StringBuffer();

		// method name
		nameBuffer.append(methodProposal.getName());

		// parameters
		nameBuffer.append('(');
		appendUnboundedParameterList(nameBuffer, methodProposal);
		nameBuffer.append(')'); //$NON-NLS-1$

		IMethod method = (IMethod) methodProposal.getModelElement();
		nameBuffer.append(" - "); //$NON-NLS-1$

		IModelElement parent = method.getParent();
		if (parent instanceof IType) {
			IType type = (IType) parent;
			nameBuffer.append(type
					.getTypeQualifiedName(ENCLOSING_TYPE_SEPARATOR)); //$NON-NLS-1$
		} else {
			nameBuffer.append(parent.getElementName());
		}

		return nameBuffer.toString();
	}

	protected String createTypeProposalLabel(CompletionProposal typeProposal) {
		StringBuffer nameBuffer = new StringBuffer();

		nameBuffer.append(typeProposal.getName());

		IType type = (IType) typeProposal.getModelElement();
		nameBuffer.append(" - "); //$NON-NLS-1$
		IModelElement parent = type.getParent();
		nameBuffer.append(parent.getElementName());

		return nameBuffer.toString();
	}

	protected String createLabelWithTypeAndDeclaration(
			CompletionProposal proposal) {
		StringBuffer nameBuffer = new StringBuffer();

		nameBuffer.append(proposal.getName());

		IField field = (IField) proposal.getModelElement();
		nameBuffer.append(" - "); //$NON-NLS-1$
		IModelElement parent = field.getParent();
		nameBuffer.append(parent.getElementName());

		return nameBuffer.toString();
	}
}
