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

import java.io.IOException;
import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.core.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.ast.rewrite.ImportRewrite;
import org.eclipse.php.internal.core.ast.util.Signature;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.text.edits.TextEdit;

/**
 * If passed compilation unit is not null, the replacement string will be seen
 * as a qualified type name.
 */
public class PHPTypeCompletionProposal extends PHPCompletionProposal implements IPHPCompletionProposalExtension {

	protected final ISourceModule fSourceModule;

	/** The unqualified type name. */
	private final String fUnqualifiedTypeName;

	/** The fully qualified type name. */
	private final String fFullyQualifiedTypeName;

	public PHPTypeCompletionProposal(String replacementString, ISourceModule cu, int replacementOffset,
			int replacementLength, Supplier<Image> image, StyledString displayString, int relevance) {
		this(replacementString, cu, replacementOffset, replacementLength, image, displayString, relevance, null);
	}

	public PHPTypeCompletionProposal(String replacementString, ISourceModule cu, int replacementOffset,
			int replacementLength, Supplier<Image> image, String displayString, int relevance,
			String fullyQualifiedTypeName) {
		this(replacementString, cu, replacementOffset, replacementLength, image, new StyledString(displayString),
				relevance, null);
	}

	public PHPTypeCompletionProposal(String replacementString, ISourceModule cu, int replacementOffset,
			int replacementLength, Supplier<Image> image, StyledString displayString, int relevance,
			String fullyQualifiedTypeName) {
		this(replacementString, cu, replacementOffset, replacementLength, image, displayString, relevance, false,
				fullyQualifiedTypeName);
	}

	public PHPTypeCompletionProposal(String replacementString, ISourceModule sourceModule, int replacementOffset,
			int replacementLength, Supplier<Image> image, StyledString displayString, int relevance, boolean indoc,
			String fullyQualifiedTypeName) {
		super(replacementString, replacementOffset, replacementLength, image, displayString, relevance, indoc);
		this.fSourceModule = sourceModule;
		fFullyQualifiedTypeName = fullyQualifiedTypeName;
		fUnqualifiedTypeName = fullyQualifiedTypeName != null ? Signature.getSimpleName(fullyQualifiedTypeName) : null;
	}

	@Override
	public void apply(IDocument document, char trigger, int offset) {
		try {
			ImportRewrite impRewrite = null;
			if (fSourceModule != null && allowAddingImports()) {
				try {
					Program astRoot = SharedASTProvider.getAST(fSourceModule, SharedASTProvider.WAIT_YES,
							SubMonitor.convert(null, 2));
					impRewrite = ImportRewrite.create(astRoot, true);
				} catch (IOException e) {
					PHPUiPlugin.log(e);
				}
			}

			boolean updateCursorPosition = updateReplacementString(document, trigger, offset, impRewrite);

			if (updateCursorPosition)
				setCursorPosition(getReplacementString().length());

			super.apply(document, trigger, offset);

			if (impRewrite != null) {
				int oldLen = document.getLength();
				impRewrite.rewriteImports(new NullProgressMonitor()).apply(document, TextEdit.UPDATE_REGIONS);
				setReplacementOffset(getReplacementOffset() + document.getLength() - oldLen);
			}
		} catch (CoreException e) {
			PHPUiPlugin.log(e);
		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}
	}

	protected boolean updateReplacementString(IDocument document, char trigger, int offset, ImportRewrite impRewrite)
			throws CoreException, BadLocationException {
		// avoid adding imports when inside imports container
		if (impRewrite != null && fFullyQualifiedTypeName != null) {
			String replacementString = getReplacementString();
			String qualifiedType = fFullyQualifiedTypeName;
			if (replacementString.startsWith(qualifiedType) && !replacementString.endsWith(String.valueOf(';'))) {
				IType[] types = impRewrite.getSourceModule().getTypes();
				if (types.length > 0 && types[0].getSourceRange().getOffset() <= offset) {
					// ignore positions above type.
					NamespaceDeclaration namespace = impRewrite.getProgram().getNamespaceDeclaration(offset);
					setReplacementString(impRewrite.addImport(namespace, getReplacementString()));
					return true;
				}
			}
		}
		return false;
	}

	private boolean allowAddingImports() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_INSERT_FULL_QUALIFIED_NAME_FOR_NAMESPACE, true, null);
	}

	@Override
	protected boolean isValidPrefix(String prefix) {
		String word = getDisplayString();
		if (word.startsWith("$") && !prefix.startsWith("$")) { //$NON-NLS-1$ //$NON-NLS-2$
			word = word.substring(1);
		}
		boolean result = isPrefix(prefix, word);
		if (!result && ProposalExtraInfo.isClassInNamespace(getExtraInfo())) {
			result = isPrefix(prefix, fUnqualifiedTypeName) || isPrefix(prefix, fFullyQualifiedTypeName);
		}
		return result;
	}

	@Override
	public CharSequence getPrefixCompletionText(IDocument document, int completionOffset) {
		return fUnqualifiedTypeName;
	}

}
