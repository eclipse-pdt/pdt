/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ImportRewrite;
import org.eclipse.php.internal.ui.corext.codemanipulation.StubUtility;

public class PHPOverrideCompletionProposal extends AbstractMethodDeclarationCompletionProposal
		implements ICompletionProposalExtension4 {

	private String fMethodName;

	public PHPOverrideCompletionProposal(IScriptProject jproject, ISourceModule cu, String methodName,
			String[] paramTypes, int start, int length, StyledString displayName, String completionProposal) {
		super(completionProposal, cu, start, length, null, displayName, 0, null);
		Assert.isNotNull(jproject);
		Assert.isNotNull(methodName);
		Assert.isNotNull(paramTypes);
		Assert.isNotNull(cu);

		fMethodName = methodName;

		StringBuffer buffer = new StringBuffer();
		buffer.append(completionProposal);

		setReplacementString(buffer.toString());
	}

	@Override
	protected MethodDeclaration getMethodDeclaration(ASTRewrite rewrite, ImportRewrite importRewrite, int offset,
			ITypeBinding declaringType) throws CoreException {
		if (declaringType == null) {
			return null;
		}
		IMethodBinding methodToOverride = Bindings.findMethodInHierarchy(declaringType, fMethodName);
		if (methodToOverride != null) {
			NamespaceDeclaration namespace = importRewrite.getProgram().getNamespaceDeclaration(offset);
			return StubUtility.createImplementationStub(fSourceModule, namespace, rewrite, importRewrite,
					(IMethod) methodToOverride.getPHPElement(), declaringType.isInterface());
		}
		return null;
	}

	@Override
	public CharSequence getPrefixCompletionText(IDocument document, int completionOffset) {
		return fMethodName;
	}

	@Override
	public boolean isAutoInsertable() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID, PHPCoreConstants.CODEASSIST_AUTOINSERT,
				false, null);
	}
}
