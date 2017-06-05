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
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ProposalInfo;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.core.ast.nodes.ITypeBinding;
import org.eclipse.php.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.core.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ImportRewrite;
import org.eclipse.php.internal.core.typeinference.FakeMethod;
import org.eclipse.php.internal.ui.corext.codemanipulation.StubUtility;
import org.eclipse.php.internal.ui.text.PHPTextMessages;

/**
 * Method declaration proposal.
 */
public class PHPMethodDeclarationCompletionProposal extends AbstractMethodDeclarationCompletionProposal
		implements ICompletionProposalExtension4 {

	public static void evaluateProposals(IType type, String prefix, int offset, int length, int relevance,
			Set<String> suggestedMethods, Collection<IScriptCompletionProposal> result) throws CoreException {
		IMethod[] methods = type.getMethods();
		if (PHPFlags.isClass(type.getFlags())) {
			String constructorName = "__construct"; //$NON-NLS-1$
			if (constructorName.startsWith(prefix) && !hasMethod(methods, constructorName) // $NON-NLS-1$
					&& suggestedMethods.add(constructorName)) {
				result.add(new PHPMethodDeclarationCompletionProposal(type, constructorName, null, offset, length,
						relevance + 500));
			}
		}

		if (prefix.length() > 0 && !prefix.startsWith("$") && !hasMethod(methods, prefix) // $NON-NLS-1$
				&& suggestedMethods.add(prefix)) {
			result.add(new PHPMethodDeclarationCompletionProposal(type, prefix, "void", offset, length, relevance)); //$NON-NLS-1$
		}
	}

	private static boolean hasMethod(IMethod[] methods, String name) {
		for (int i = 0; i < methods.length; i++) {
			IMethod curr = methods[i];
			if (curr.getElementName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private final IType fType;
	private final String fReturnType;
	private final String fMethodName;

	public PHPMethodDeclarationCompletionProposal(IType type, String methodName, String returnType, int start,
			int length, int relevance) {
		super("", type.getSourceModule(), start, length, null, getDisplayName(methodName, returnType), relevance); //$NON-NLS-1$
		Assert.isNotNull(type);
		Assert.isNotNull(methodName);

		fType = type;
		fMethodName = methodName;
		fReturnType = returnType;

		setProposalInfo(new ProposalInfo(type));

		if (returnType == null) {
			ImageDescriptor desc = new ScriptElementImageDescriptor(DLTKPluginImages.DESC_METHOD_PUBLIC,
					ScriptElementImageDescriptor.CONSTRUCTOR, ScriptElementImageProvider.SMALL_SIZE);
			setImage(DLTKUIPlugin.getImageDescriptorRegistry().get(desc));
		} else {
			setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PRIVATE));
		}
	}

	private static StyledString getDisplayName(String methodName, String returnTypeSig) {
		StyledString buf = new StyledString();
		buf.append(methodName);
		buf.append('(');
		buf.append(')');
		if (returnTypeSig != null) {
			buf.append(" : "); //$NON-NLS-1$
			buf.append(returnTypeSig);
			buf.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			buf.append(PHPTextMessages.MethodCompletionProposal_method_label, StyledString.QUALIFIER_STYLER);
		} else {
			buf.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			buf.append(PHPTextMessages.MethodCompletionProposal_constructor_label, StyledString.QUALIFIER_STYLER);
		}
		return buf;
	}

	@Override
	protected MethodDeclaration getMethodDeclaration(ASTRewrite rewrite, ImportRewrite importRewrite, int offset,
			ITypeBinding declaringType) throws CoreException {
		boolean isInterface = PHPFlags.isInterface(fType.getFlags());
		// if (addComments) {
		// String comment = CodeGeneration.getMethodComment(project,
		// declTypeName, fMethodName, empty, empty,
		// fReturnTypeSig, empty, null, lineDelim);
		// if (comment != null) {
		// buf.append(comment);
		// buf.append(lineDelim);
		// }
		// }
		int flags = 0;
		if (fReturnType != null) {
			if (!isInterface) {
				flags = Flags.AccPrivate;
			} else {
				flags = Flags.AccPublic;
			}
		} else {
			flags = Flags.AccPublic;
		}

		FakeMethod method = new FakeMethod((ModelElement) fType, fMethodName, flags);
		method.setType(fReturnType);
		NamespaceDeclaration namesapce = importRewrite.getProgram().getNamespaceDeclaration(offset);
		return StubUtility.createMethodStub(fSourceModule, namesapce, rewrite, importRewrite, method, isInterface);

	}

	@Override
	public CharSequence getPrefixCompletionText(IDocument document, int completionOffset) {
		return new String();
	}

	@Override
	public boolean isAutoInsertable() {
		return false;
	}

}
