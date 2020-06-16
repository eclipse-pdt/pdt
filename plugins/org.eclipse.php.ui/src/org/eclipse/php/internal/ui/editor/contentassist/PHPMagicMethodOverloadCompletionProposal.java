/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.core.ast.nodes.ITypeBinding;
import org.eclipse.php.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.core.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ImportRewrite;
import org.eclipse.php.internal.ui.corext.codemanipulation.StubUtility;

public class PHPMagicMethodOverloadCompletionProposal extends AbstractMethodDeclarationCompletionProposal {

	private IMethod fMagicMethod;

	public PHPMagicMethodOverloadCompletionProposal(IMethod magicMethod, IScriptProject jproject, ISourceModule cu,
			String methodName, String[] paramTypes, int start, int length, StyledString displayName,
			String completionProposal) {
		super(completionProposal, cu, start, length, null, displayName, 0, null);
		this.fMagicMethod = magicMethod;
	}

	@Override
	protected MethodDeclaration getMethodDeclaration(ASTRewrite rewrite, ImportRewrite importRewrite, int offset,
			ITypeBinding declaringType) throws CoreException {
		if (declaringType == null) {
			return null;
		}
		NamespaceDeclaration namesapce = importRewrite.getProgram().getNamespaceDeclaration(offset);
		return StubUtility.createMethodStub(fSourceModule, namesapce, rewrite, importRewrite, fMagicMethod,
				declaringType.isInterface());
	}

}
