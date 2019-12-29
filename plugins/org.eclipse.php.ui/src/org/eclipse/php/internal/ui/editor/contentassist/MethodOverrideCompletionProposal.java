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
 *     Dawid Pakuła - Adapt JDT code to PDT
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.*;
import org.eclipse.php.internal.core.ast.rewrite.ImportRewrite.ImportRewriteContext;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.actions.CodeGenerationSettings;
import org.eclipse.php.internal.ui.preferences.PHPPreferencesSettings;
import org.eclipse.php.internal.ui.text.correction.ASTResolving;
import org.eclipse.php.ui.util.CodeGenerationUtils;

final public class MethodOverrideCompletionProposal extends PHPOverrideCompletionProposal {

	protected IScriptProject fScriptProject;
	protected String fMethodName;

	public MethodOverrideCompletionProposal(IScriptProject scriptProject, ISourceModule cu, String methodName,
			String[] paramTypes, int start, int length, StyledString displayName, String completionProposal) {
		super(scriptProject, cu, methodName, paramTypes, start, length, displayName, completionProposal);
		fScriptProject = scriptProject;
		fMethodName = methodName;
	}

	@Override
	protected boolean updateReplacementString(IDocument document, char trigger, int offset)
			throws CoreException, BadLocationException {

		Document recovered = new Document(document.get());

		int index = offset - 1;
		while (index >= 0 && Character.isJavaIdentifierPart(recovered.getChar(index)))
			index--;
		final int length = offset - index - 1;
		recovered.replace(index + 1, length, " "); //$NON-NLS-1$

		Program program = getAST(index, recovered);
		ASTNode node = NodeFinder.perform(program.getRoot(), index, 1);
		node = ASTResolving.findParentType(node);
		ImportRewrite importRewrite = ImportRewrite.create(program, true);
		ImportRewriteContext context = new ImportRewriteContext() {

			@Override
			public int findInContext(NamespaceDeclaration namespace, String qualifier, String name, int kind) {
				return RES_NAME_CONFLICT;
			}
		};
		ITypeBinding declaringType = null;
		Block descriptor = null;
		if (node instanceof AnonymousClassDeclaration) {
			declaringType = ((AnonymousClassDeclaration) node).resolveTypeBinding();
			descriptor = ((AnonymousClassDeclaration) node).getBody();
		} else if (node instanceof TypeDeclaration) {
			declaringType = ((TypeDeclaration) node).resolveTypeBinding();
			descriptor = ((TypeDeclaration) node).getBody();
		}
		if (declaringType != null) {
			ASTRewrite rewrite = ASTRewrite.create(program.getAST());
			IMethodBinding methodToOverride = Bindings.findMethodInHierarchy(declaringType, fMethodName);
			if (methodToOverride != null) {
				final CodeGenerationSettings settings = PHPPreferencesSettings
						.getCodeGenerationSettings(fScriptProject.getProject());
				settings.createComments = true;
				MethodDeclaration stub = CodeGenerationUtils.createImplementationStub(program, rewrite,
						methodToOverride, declaringType.getName(), settings, declaringType.isInterface());
				ListRewrite rewriter = rewrite.getListRewrite(descriptor, Block.STATEMENTS_PROPERTY);
				rewriter.insertFirst(stub, null); // $NON-NLS-1$

				ITrackedNodePosition position = rewrite.track(stub);
				rewrite.rewriteAST(recovered, null).apply(recovered);
				String generatedCode = recovered.get(position.getStartPosition(), position.getLength());
				int generatedIndent = IndentManipulation.measureIndentUnits(
						getIndentAt(recovered, position.getStartPosition(), settings), settings.tabWidth,
						settings.indentWidth);

				String indent = getIndentAt(document, getReplacementOffset(), settings);
				setReplacementString(IndentManipulation.changeIndent(generatedCode, generatedIndent, settings.tabWidth,
						settings.indentWidth, indent, TextUtilities.getDefaultLineDelimiter(document)));
				int replacementLength = getReplacementLength();
				if (document.get(getReplacementOffset() + replacementLength, 1).equals(")")) { //$NON-NLS-1$
					setReplacementLength(replacementLength + 1);
				}
			}
		}
		return true;
	}

	private String getIndentAt(IDocument document, int offset, CodeGenerationSettings settings) {
		try {
			IRegion region = document.getLineInformationOfOffset(offset);
			return IndentManipulation.extractIndentString(document.get(region.getOffset(), region.getLength()),
					settings.tabWidth, settings.indentWidth);
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}
	}

	private Program getAST(int offset, IDocument recovered) throws ModelException, BadLocationException {
		return CodeGenerationUtils.getASTRoot(fSourceModule, recovered, fScriptProject.getProject());
	}

	@Override
	public boolean isAutoInsertable() {
		return false;
	}

	@Override
	protected boolean insertCompletion() {
		return false;
	}

	@Override
	public IContextInformation getContextInformation() {
		return null;
	}

}
