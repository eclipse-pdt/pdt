/*******************************************************************************
 * Copyright (c) 2017, 2019 Alex Xu and others.
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
package org.eclipse.php.internal.ui.text.correction.proposals;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.UseStatement;
import org.eclipse.php.core.ast.nodes.UseStatementPart;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPProblemIdentifier;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.text.correction.CorrectionMessages;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.TextEdit;

public class RemoveUnusedUseStatementProposal extends CUCorrectionProposal {

	private IProblemLocation fLocation;
	private IInvocationContext fContext;

	public RemoveUnusedUseStatementProposal(IInvocationContext context, IProblemLocation location, int relevance) {
		super(getMessage(location), context.getCompilationUnit(), relevance,
				DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_DELETE_IMPORT));
		fLocation = location;
		fContext = context;
	}

	private static String getMessage(IProblemLocation location) {
		String message = ""; //$NON-NLS-1$
		if (!(location.getProblemIdentifier() instanceof PHPProblemIdentifier)) {
			return message;
		}
		PHPProblemIdentifier id = (PHPProblemIdentifier) location.getProblemIdentifier();
		switch (id) {
		case UnusedImport:
		case ImportNotFound:
			message = CorrectionMessages.RemoveUnusedUseStatementProposal_removeunusedusestatement_description;
			break;
		case DuplicateImport:
			message = CorrectionMessages.RemoveUnusedUseStatementProposal_removeduplicateusestatement_description;
			break;
		case UnnecessaryImport:
			message = CorrectionMessages.RemoveUnusedUseStatementProposal_removeunnecessaryusestatement_description;
			break;
		default:
			break;
		}
		return message;

	}

	@Override
	protected void addEdits(IDocument doc, TextEdit root) throws CoreException {
		super.addEdits(doc, root);
		try {
			Program astRoot = fContext.getASTRoot();
			if (astRoot == null) {
				return;
			}

			ASTNode coveredNode = fLocation.getCoveredNode(astRoot);
			// "currentUseStatementPart" will be non-null inside group use
			// statements:
			UseStatementPart currentUseStatementPart = null;
			while (coveredNode != null && !(coveredNode instanceof UseStatement)) {
				if (coveredNode instanceof UseStatementPart) {
					currentUseStatementPart = (UseStatementPart) coveredNode;
				}
				coveredNode = coveredNode.getParent();
			}
			if (coveredNode == null || currentUseStatementPart == null) {
				return;
			}
			UseStatement currentUseStatement = (UseStatement) coveredNode;
			List<UseStatementPart> parts = currentUseStatement.parts();
			// Remove group use statements with only a use part or remove single
			// use statements:
			if (parts.size() == 1) {
				int currentStartOffset = coveredNode.getStart();
				int currentEndOffset = coveredNode.getEnd();
				int lineStart = doc.getLineOfOffset(currentStartOffset);
				int lineEnd = doc.getLineOfOffset(currentEndOffset);
				int lineStartOffset = doc.getLineOffset(lineStart);
				// End offset *after* the line's delimiter:
				int lineEndOffset = doc.getLineOffset(lineEnd) + doc.getLineLength(lineEnd);
				if (currentStartOffset > lineStartOffset
						&& StringUtils.isBlank(doc.get(lineStartOffset, currentStartOffset - lineStartOffset))) {
					currentStartOffset = lineStartOffset;
				}
				if (currentEndOffset < lineEndOffset
						&& StringUtils.isBlank(doc.get(currentEndOffset, lineEndOffset - currentEndOffset))) {
					currentEndOffset = lineEndOffset;
				}
				root.addChild(new DeleteEdit(currentStartOffset, currentEndOffset - currentStartOffset));
				return;
			}
			int index = 0;
			for (UseStatementPart part : parts) {
				if (part == currentUseStatementPart) {
					int start = part.getStart();
					int length = 0;
					if (index == 0) {
						UseStatementPart next = parts.get(1);
						length = next.getStart() - part.getStart();
					} else {
						UseStatementPart prev = parts.get(index - 1);
						start = prev.getEnd();
						length = part.getEnd() - start;
					}
					root.addChild(new DeleteEdit(start, length));
					return;
				}
				index++;
			}

		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}
	}

}