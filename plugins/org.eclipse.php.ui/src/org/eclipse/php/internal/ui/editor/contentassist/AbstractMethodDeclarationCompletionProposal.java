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

import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.*;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.text.correction.ASTResolving;
import org.eclipse.swt.graphics.Image;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public abstract class AbstractMethodDeclarationCompletionProposal extends PHPTypeCompletionProposal
		implements ICompletionProposalExtension4 {

	public AbstractMethodDeclarationCompletionProposal(String replacementString, ISourceModule sourceModule,
			int replacementOffset, int replacementLength, Supplier<Image> image, StyledString displayString,
			int relevance) {
		this(replacementString, sourceModule, replacementOffset, replacementLength, image, displayString, relevance,
				null);
	}

	public AbstractMethodDeclarationCompletionProposal(String replacementString, ISourceModule cu,
			int replacementOffset, int replacementLength, Supplier<Image> image, StyledString displayString,
			int relevance, String fullyQualifiedTypeName) {
		this(replacementString, cu, replacementOffset, replacementLength, image, displayString, relevance, false,
				fullyQualifiedTypeName);
	}

	public AbstractMethodDeclarationCompletionProposal(String replacementString, ISourceModule sourceModule,
			int replacementOffset, int replacementLength, Supplier<Image> image, StyledString displayString,
			int relevance, boolean indoc, String fullyQualifiedTypeName) {
		super(replacementString, sourceModule, replacementOffset, replacementLength, image, displayString, relevance,
				indoc, fullyQualifiedTypeName);
	}

	@Override
	protected boolean updateReplacementString(IDocument document, char trigger, int offset, ImportRewrite importRewrite)
			throws CoreException, BadLocationException {
		Document recoveredDocument = new Document();
		Program unit = getRecoveredAST(document, offset, recoveredDocument);
		if (importRewrite == null || !importRewrite.getProgram().equals(unit)) {
			importRewrite = ImportRewrite.create(unit, true);
		}

		ITypeBinding declaringType = null;
		ASTNode node = NodeFinder.perform(unit, offset, 1);
		node = ASTResolving.findParentType(node);
		ListRewrite rewriter = null;
		ASTRewrite rewrite = ASTRewrite.create(unit.getAST());
		if (node instanceof AnonymousClassDeclaration) {
			declaringType = ((AnonymousClassDeclaration) node).resolveTypeBinding();
			rewriter = rewrite.getListRewrite(((AnonymousClassDeclaration) node).getBody(), Block.STATEMENTS_PROPERTY);
		} else if (node instanceof TypeDeclaration) {
			TypeDeclaration declaration = (TypeDeclaration) node;
			declaringType = declaration.resolveTypeBinding();
			rewriter = rewrite.getListRewrite(((TypeDeclaration) node).getBody(), Block.STATEMENTS_PROPERTY);
		}

		MethodDeclaration stub = getMethodDeclaration(rewrite, importRewrite, offset, declaringType);
		if (stub != null && rewriter != null) {
			int indentWidth = FormatterUtils.getFormatterCommonPreferences().getIndentationSize(document);
			int tabWidth = FormatterUtils.getFormatterCommonPreferences().getTabSize(document);

			rewriter.insertFirst(stub, null);

			ITrackedNodePosition position = rewrite.track(stub);
			try {
				rewrite.rewriteAST(recoveredDocument, fSourceModule.getScriptProject().getOptions(true))
						.apply(recoveredDocument);

				String generatedCode = recoveredDocument.get(position.getStartPosition(), position.getLength()).trim();
				int generatedIndent = IndentManipulation.measureIndentUnits(
						getIndentAt(recoveredDocument, position.getStartPosition(), tabWidth, indentWidth), tabWidth,
						indentWidth);

				String indent = getIndentAt(document, getReplacementOffset(), tabWidth, indentWidth);
				setReplacementString(IndentManipulation.changeIndent(generatedCode, generatedIndent, tabWidth,
						indentWidth, indent, TextUtilities.getDefaultLineDelimiter(document)));

				int replacementLength = getReplacementLength();
				if (document.get(getReplacementOffset() + replacementLength, 1).equals(")")) { //$NON-NLS-1$
					setReplacementLength(replacementLength + 1);
				}

				int oldLen = document.getLength();
				importRewrite.rewriteImports(new NullProgressMonitor()).apply(document, TextEdit.UPDATE_REGIONS);
				setReplacementOffset(getReplacementOffset() + document.getLength() - oldLen);

			} catch (MalformedTreeException exception) {
				PHPUiPlugin.log(exception);
			} catch (BadLocationException exception) {
				PHPUiPlugin.log(exception);
			}
		}
		return true;
	}

	protected abstract MethodDeclaration getMethodDeclaration(ASTRewrite rewrite, ImportRewrite importRewrite,
			int offset, ITypeBinding declaringType) throws CoreException;

	protected static String getIndentAt(IDocument document, int offset, int tabWidth, int indentWidth) {
		try {
			IRegion region = document.getLineInformationOfOffset(offset);
			return IndentManipulation.extractIndentString(document.get(region.getOffset(), region.getLength()),
					tabWidth, indentWidth);
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}
	}

	protected Program getRecoveredAST(IDocument document, int offset, Document recoveredDocument) {
		try {
			// Program ast = SharedASTProvider.getAST(fSourceModule,
			// SharedASTProvider.WAIT_ACTIVE_ONLY, null);
			// if (ast != null) {
			// recoveredDocument.set(document.get());
			// return ast;
			// }

			char[] content = document.get().toCharArray();

			// clear prefix to avoid compile errors
			int index = offset - 1;
			while (index >= 0 && Character.isJavaIdentifierPart(content[index])) {
				content[index] = ' ';
				index--;
			}

			recoveredDocument.set(new String(content));

			final ASTParser parser = ASTParser.newParser(fSourceModule);
			parser.setSource(content);
			return (Program) parser.createAST(new NullProgressMonitor());
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}
		return null;
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
