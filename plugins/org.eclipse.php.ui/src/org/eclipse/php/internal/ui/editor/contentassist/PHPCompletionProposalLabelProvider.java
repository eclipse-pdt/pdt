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
import org.eclipse.dltk.internal.core.ArchiveProjectFragment;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.ui.util.PHPPluginImages;

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
		appendParameterList(nameBuffer, methodProposal);
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
		IMethod method = (IMethod) methodProposal.getModelElement();

		if (method instanceof FakeConstructor) {
			IType type = (IType) method.getParent();
			if (type instanceof AliasType) {
				AliasType aliasType = (AliasType) type;
				nameBuffer.append(aliasType.getAlias());
				nameBuffer.append("()"); //$NON-NLS-1$
				return nameBuffer.toString();
			}
		}

		// method name
		nameBuffer.append(methodProposal.getName());

		// parameters
		nameBuffer.append('(');
		appendParameterList(nameBuffer, methodProposal);
		nameBuffer.append(')'); //$NON-NLS-1$

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

	public String createTypeProposalLabel(CompletionProposal typeProposal) {
		StringBuffer nameBuffer = new StringBuffer();

		IType type = (IType) typeProposal.getModelElement();
		if (type instanceof AliasType) {
			AliasType aliasType = (AliasType) type;
			nameBuffer.append(aliasType.getAlias());
			return nameBuffer.toString();
		}
		nameBuffer.append(typeProposal.getName());

		if (type.getParent() != null) {
			nameBuffer.append(" - "); //$NON-NLS-1$
			IModelElement parent = type.getParent();
			nameBuffer.append(parent.getElementName());
		}

		return nameBuffer.toString();
	}

	protected String createLabelWithTypeAndDeclaration(
			CompletionProposal proposal) {
		StringBuffer nameBuffer = new StringBuffer();

		nameBuffer.append(proposal.getName());
		IField field = (IField) proposal.getModelElement();
		if (field.getParent() != null) {

			nameBuffer.append(" - "); //$NON-NLS-1$
			IModelElement parent = field.getParent();
			nameBuffer.append(parent.getElementName());
		}

		return nameBuffer.toString();
	}

	@Override
	protected String createTypeProposalLabel(String fullName) {
		return super.createTypeProposalLabel(fullName);
	}

	@Override
	public ImageDescriptor createImageDescriptor(CompletionProposal proposal) {
		if (proposal.getModelElement() instanceof ArchiveProjectFragment) {
			return DLTKPluginImages.DESC_OBJS_JAR;
		}
		return super.createImageDescriptor(proposal);
	}

	@Override
	public ImageDescriptor createTypeImageDescriptor(CompletionProposal proposal) {
		if (PHPFlags.isTrait(proposal.getFlags())) {
			return decorateImageDescriptor(PHPPluginImages.DESC_OBJS_TRAIT,
					proposal);
		} else {
			return super.createTypeImageDescriptor(proposal);
		}
	}

	public String createFieldProposalLabel(CompletionProposal proposal) {
		return super.createFieldProposalLabel(proposal);
	}

	@Override
	public ImageDescriptor createFieldImageDescriptor(
			CompletionProposal proposal) {
		return super.createFieldImageDescriptor(proposal);
	}
}
