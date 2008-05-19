package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;

public class PHPCompletionProposalLabelProvider extends CompletionProposalLabelProvider {

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
			nameBuffer.append(type.getTypeQualifiedName("::")); //$NON-NLS-1$
		} else {
			nameBuffer.append(parent.getElementName());
		}

		return nameBuffer.toString();
	}

	protected String createOverrideMethodProposalLabel(CompletionProposal methodProposal) {
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
			nameBuffer.append(type.getTypeQualifiedName("::")); //$NON-NLS-1$
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
		if (parent instanceof IType) {
			IType type2 = (IType) parent;
			nameBuffer.append(type2.getElementName()); // XXX, fqn may be
			// better idea
		} else {
			nameBuffer.append(parent.getElementName());
		}

		return nameBuffer.toString();
	}

}
