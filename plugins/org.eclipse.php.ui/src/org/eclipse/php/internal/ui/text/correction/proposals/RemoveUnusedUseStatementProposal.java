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
package org.eclipse.php.internal.ui.text.correction.proposals;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.parser.PhpProblemIdentifier;
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
		if (!(location.getProblemIdentifier() instanceof PhpProblemIdentifier))
			return message;
		PhpProblemIdentifier id = (PhpProblemIdentifier) location.getProblemIdentifier();
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

			ASTNode coveredNode = fLocation.getCoveredNode(astRoot);
			ASTNode current = coveredNode;
			while (!(coveredNode instanceof UseStatement)) {
				coveredNode = coveredNode.getParent();
			}
			UseStatement use = (UseStatement) coveredNode;
			List<UseStatementPart> parts = use.parts();
			if (parts.size() == 1) {
				int line = doc.getLineOfOffset(coveredNode.getStart());
				int lineStart = doc.getLineOffset(line);
				int length = doc.getLineLength(line);
				root.addChild(new DeleteEdit(lineStart, length));
				return;
			}
			String currentNamespace = null;
			if (current instanceof NamespaceName) {
				currentNamespace = getNamespaceName((NamespaceName) current);
			} else {
				currentNamespace = ((Identifier) current).getName();
			}
			for (UseStatementPart part : parts) {
				int index = parts.indexOf(part);
				String namespace = getNamespaceName(part.getName());
				if (currentNamespace.equals(namespace)) {
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
			}

		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}
	}

	private static String getNamespaceName(NamespaceName namespace) {
		StringBuilder namespaces = new StringBuilder(""); //$NON-NLS-1$
		List<Identifier> segments = namespace.segments();
		for (Identifier segment : segments) {
			if (segments.indexOf(segment) == 0) {
				namespaces.append(segment.getName());
			} else {
				namespaces.append(NamespaceReference.NAMESPACE_DELIMITER).append(segment.getName());
			}
		}
		return namespaces.toString();
	}

}