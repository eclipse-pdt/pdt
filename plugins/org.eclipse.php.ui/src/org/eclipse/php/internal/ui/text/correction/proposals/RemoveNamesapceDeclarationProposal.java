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
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.corext.codemanipulation.StubUtility;
import org.eclipse.php.internal.ui.text.correction.CorrectionMessages;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.TextEdit;

public class RemoveNamesapceDeclarationProposal extends CUCorrectionProposal {

	private IProblemLocation fLocation;

	public RemoveNamesapceDeclarationProposal(ISourceModule cu, IProblemLocation location, int relevance) {
		super(CorrectionMessages.CorrectPackageDeclarationProposal_name, cu, relevance,
				DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_NAMESPACE));
		fLocation = location;
	}

	@Override
	protected void addEdits(IDocument doc, TextEdit root) throws CoreException {
		super.addEdits(doc, root);
		ISourceModule cu = getCompilationUnit();
		String lineDelim = StubUtility.getLineDelimiterUsed(cu.getScriptProject());
		int line;
		try {
			line = doc.getLineOfOffset(fLocation.getOffset());
			int lineStart = doc.getLineOffset(line);
			int length = doc.getLineLength(line) - lineDelim.length();
			root.addChild(new DeleteEdit(lineStart, length));
			return;
		} catch (BadLocationException e) {
			return;
		}
	}

	@Override
	public String getName() {
		String namespace = PHPModelUtils.getNamespaceNameByLocation(getCompilationUnit());
		return Messages.format(CorrectionMessages.CorrectPackageDeclarationProposal_remove_description, namespace);

	}
}
