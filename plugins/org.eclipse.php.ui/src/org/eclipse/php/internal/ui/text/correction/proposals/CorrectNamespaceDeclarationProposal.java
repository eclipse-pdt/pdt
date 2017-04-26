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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.NamespaceName;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.corext.codemanipulation.StubUtility;
import org.eclipse.php.internal.ui.text.correction.CorrectionMessages;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

public class CorrectNamespaceDeclarationProposal extends CUCorrectionProposal {

	private IProblemLocation fLocation;
	private IInvocationContext fContext;

	public CorrectNamespaceDeclarationProposal(IInvocationContext context, IProblemLocation location, int relevance) {
		super(CorrectionMessages.CorrectPackageDeclarationProposal_name, context.getCompilationUnit(), relevance,
				DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_NAMESPACE));
		fLocation = location;
		fContext = context;
	}

	@Override
	protected void addEdits(IDocument doc, TextEdit root) throws CoreException {
		super.addEdits(doc, root);

		ISourceModule cu = getCompilationUnit();

		IScriptFolder parentPack = (IScriptFolder) cu.getParent();
		NamespaceName namespace = getNamespaceName(cu);
		if (parentPack.isRootFolder() && namespace != null) {
			String lineDelim = StubUtility.getLineDelimiterUsed(cu.getScriptProject());
			int line;
			try {
				line = doc.getLineOfOffset(namespace.getStart());
				int lineStart = doc.getLineOffset(line);
				int length = doc.getLineLength(line) - lineDelim.length();
				root.addChild(new DeleteEdit(lineStart, length));
				return;
			} catch (BadLocationException e) {
				return;
			}
		}
		if (!parentPack.isRootFolder() && namespace == null) {
			String lineDelim = StubUtility.getLineDelimiterUsed(cu.getScriptProject());
			String str = "namespace " + PHPModelUtils.getNamespaceNameByLocation(parentPack.getElementName()) + ';' //$NON-NLS-1$ //$NON-NLS-2$
					+ lineDelim + lineDelim;
			root.addChild(new InsertEdit(fLocation.getOffset(), str));
			return;
		}

		root.addChild(new ReplaceEdit(fLocation.getOffset(), fLocation.getLength(),
				PHPModelUtils.getNamespaceNameByLocation(parentPack.getElementName())));
	}

	private NamespaceName getNamespaceName(ISourceModule cu) {
		Program astRoot = fContext.getASTRoot();

		ASTNode coveredNode = fLocation.getCoveredNode(astRoot);

		if (coveredNode == null) {
			return null;
		}
		NamespaceName namespace = null;
		if (coveredNode instanceof NamespaceName) {
			namespace = (NamespaceName) coveredNode;
		} else if (coveredNode.getParent() instanceof NamespaceName) {
			namespace = (NamespaceName) coveredNode.getParent();
		} else {
			return null;
		}
		return namespace;
	}

	@Override
	public String getName() {
		ISourceModule cu = getCompilationUnit();
		IScriptFolder parentPack = (IScriptFolder) cu.getParent();
		NamespaceName namespace = getNamespaceName(cu);
		if (parentPack.isRootFolder() && namespace != null) {
			return Messages.format(CorrectionMessages.CorrectPackageDeclarationProposal_remove_description,
					namespace.getName());
		}
		if (!parentPack.isRootFolder() && namespace == null) {
			return (Messages.format(CorrectionMessages.CorrectPackageDeclarationProposal_add_description,
					PHPModelUtils.getNamespaceNameByLocation(ScriptElementLabels.getDefault()
							.getElementLabel(parentPack, ScriptElementLabels.ALL_DEFAULT))));
		}
		return (Messages.format(CorrectionMessages.CorrectPackageDeclarationProposal_change_description,
				PHPModelUtils.getNamespaceNameByLocation(ScriptElementLabels.getDefault().getElementLabel(parentPack,
						ScriptElementLabels.ALL_DEFAULT))));
	}
}