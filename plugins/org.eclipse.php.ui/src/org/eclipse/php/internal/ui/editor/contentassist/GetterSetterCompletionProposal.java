/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.corext.util.Strings;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ProposalInfo;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.core.ast.nodes.ITypeBinding;
import org.eclipse.php.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ImportRewrite;
import org.eclipse.php.internal.ui.text.PHPTextMessages;
import org.eclipse.php.ui.util.CodeGenerationUtils;

public class GetterSetterCompletionProposal extends AbstractMethodDeclarationCompletionProposal
		implements ICompletionProposalExtension4 {

	public static void evaluateProposals(IType type, String prefix, int offset, int length, int relevance,
			Set<String> suggestedMethods, Collection<IScriptCompletionProposal> result) throws CoreException {
		if (prefix.length() == 0) {
			relevance--;
		}

		IField[] fields = type.getFields();
		IMethod[] methods = type.getMethods();
		for (int i = 0; i < fields.length; i++) {
			IField curr = fields[i];
			String getterName = CodeGenerationUtils.getGetterName(curr);
			int flags = curr.getFlags();
			if (Strings.startsWithIgnoreCase(getterName, prefix) && !hasMethod(methods, getterName)) {
				suggestedMethods.add(getterName);
				int getterRelevance = relevance;
				if (PHPFlags.isStatic(flags) && PHPFlags.isConstant(flags))
					getterRelevance = relevance - 1;
				result.add(new GetterSetterCompletionProposal(curr, offset, length, true, getterRelevance));
			}

			if (!PHPFlags.isConstant(flags)) {
				String setterName = CodeGenerationUtils.getSetterName(curr);
				if (Strings.startsWithIgnoreCase(setterName, prefix) && !hasMethod(methods, setterName)) {
					suggestedMethods.add(setterName);
					result.add(new GetterSetterCompletionProposal(curr, offset, length, false, relevance));
				}
			}
		}
	}

	private static boolean hasMethod(IMethod[] methods, String name) {
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getElementName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private final IField fField;
	private final boolean fIsGetter;

	public GetterSetterCompletionProposal(IField field, int start, int length, boolean isGetter, int relevance)
			throws CoreException {
		super("", field.getSourceModule(), start, length, DLTKPluginImages.get(DLTKPluginImages.IMG_FIELD_PUBLIC), //$NON-NLS-1$
				getDisplayName(field, isGetter), relevance);
		Assert.isNotNull(field);

		fField = field;
		fIsGetter = isGetter;
		setProposalInfo(new ProposalInfo(field));
	}

	private static StyledString getDisplayName(IField field, boolean isGetter) throws CoreException {
		StyledString buf = new StyledString();
		String fieldTypeName = field.getType();
		String fieldNameLabel = BasicElementLabels.getJavaElementName(field.getElementName());
		if (isGetter) {
			if (fieldTypeName == null) {
				fieldTypeName = ""; //$NON-NLS-1$
			} else {
				fieldTypeName = " : " + fieldTypeName; //$NON-NLS-1$
			}
			buf.append(BasicElementLabels
					.getJavaElementName(CodeGenerationUtils.getGetterName(field) + "()" + fieldTypeName)); //$NON-NLS-1$
			buf.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			buf.append(Messages.format(PHPTextMessages.GetterSetterCompletionProposal_getter_label, fieldNameLabel),
					StyledString.QUALIFIER_STYLER);
		} else {
			if (fieldTypeName == null) {
				fieldTypeName = fieldNameLabel;
			}
			buf.append(BasicElementLabels
					.getJavaElementName(CodeGenerationUtils.getSetterName(field) + '(' + fieldTypeName + ") : void")); //$NON-NLS-1$
			buf.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			buf.append(Messages.format(PHPTextMessages.GetterSetterCompletionProposal_setter_label, fieldNameLabel),
					StyledString.QUALIFIER_STYLER);
		}
		return buf;
	}

	@Override
	protected MethodDeclaration getMethodDeclaration(ASTRewrite rewrite, ImportRewrite importRewrite, int offset,
			ITypeBinding declaringType) throws CoreException {
		int flags = Flags.AccPublic | (fField.getFlags() & Flags.AccStatic);

		MethodDeclaration stub;
		if (fIsGetter) {
			String getterName = CodeGenerationUtils.getGetterName(fField);
			stub = CodeGenerationUtils.createGetterStub(fField, getterName, true, flags, fField.getDeclaringType(),
					rewrite);
		} else {
			String getterName = CodeGenerationUtils.getSetterName(fField);
			stub = CodeGenerationUtils.createSetterStub(fField, getterName, true, flags, rewrite);
		}
		return stub;
	}

}
