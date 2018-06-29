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
package org.eclipse.php.internal.ui.text.correction;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.OrganizeUseStatementsAction;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.text.correction.proposals.ChangeCorrectionProposal;
import org.eclipse.php.internal.ui.text.correction.proposals.RemoveUnusedUseStatementProposal;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReorgCorrectionsSubProcessor {

	public static void removeImportStatementProposals(IInvocationContext context, IProblemLocation problem,
			Collection proposals) {
		RemoveUnusedUseStatementProposal proposal = new RemoveUnusedUseStatementProposal(context, problem, 5);
		proposals.add(proposal);

		final ISourceModule cu = context.getCompilationUnit();
		String name = CorrectionMessages.ReorgCorrectionsSubProcessor_organizeimports_description;
		ChangeCorrectionProposal proposal1 = new ChangeCorrectionProposal(name, null,
				IProposalRelevance.ORGANIZE_IMPORTS, DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE)) {
			@Override
			public void apply(IDocument document) {
				IEditorInput input = new FileEditorInput((IFile) cu.getResource());
				IWorkbenchPage p = PHPUiPlugin.getActivePage();
				if (p == null) {
					return;
				}
				IEditorPart part = p.findEditor(input);
				if (part instanceof PHPStructuredEditor) {
					OrganizeUseStatementsAction action = new OrganizeUseStatementsAction(part);
					action.run(cu);
				}
			}
		};
		proposals.add(proposal1);
	}

}
