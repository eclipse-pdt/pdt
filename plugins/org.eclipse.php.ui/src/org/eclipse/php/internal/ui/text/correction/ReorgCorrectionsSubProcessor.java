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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.ScriptFolder;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.util.INamespaceResolver;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.OrganizeUseStatementsAction;
import org.eclipse.php.internal.ui.corext.refactoring.changes.CreateSourceFolderChange;
import org.eclipse.php.internal.ui.corext.refactoring.changes.MoveSourceModuleChange;
import org.eclipse.php.internal.ui.corext.refactoring.changes.RenameSourceModuleChange;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.text.correction.proposals.ChangeCorrectionProposal;
import org.eclipse.php.internal.ui.text.correction.proposals.CorrectMainTypeNameProposal;
import org.eclipse.php.internal.ui.text.correction.proposals.CorrectNamespaceDeclarationProposal;
import org.eclipse.php.internal.ui.text.correction.proposals.RemoveUnusedUseStatementProposal;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.swt.graphics.Image;
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

	public static void getWrongTypeNameProposals(IInvocationContext context, IProblemLocation problem,
			Collection proposals) {
		ISourceModule cu = context.getCompilationUnit();
		boolean isLinked = cu.getResource().isLinked();

		Program root = context.getASTRoot();

		ASTNode coveredNode = problem.getCoveredNode(root);
		if (!(coveredNode instanceof Identifier))
			return;

		ASTNode parentType = coveredNode.getParent();
		if (!(parentType instanceof TypeDeclaration))
			return;

		Identifier currTypeName = (Identifier) coveredNode;
		Identifier newTypeName = new Identifier(currTypeName.getStart(), currTypeName.getEnd(), coveredNode.getAST(),
				PHPModelUtils.getTypeNameByFileName(cu));

		proposals.add(new CorrectMainTypeNameProposal(cu, context, currTypeName, newTypeName, 5));
		String newCUName = PHPModelUtils.getRenamedSouceModuleName(cu, currTypeName.getName());
		RenameSourceModuleChange change = new RenameSourceModuleChange(cu, newCUName);

		ISourceModule sourceModule = ((ScriptFolder) cu.getParent()).getSourceModule(newCUName);
		if (!sourceModule.exists() && !isLinked) {
			// rename CU
			String label = Messages.format(CorrectionMessages.ReorgCorrectionsSubProcessor_renamecu_description,
					BasicElementLabels.getResourceName(newCUName));
			Image image = DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE);
			proposals.add(new ChangeCorrectionProposal(label, change, 6, image));
		}
	}

	public static void getWrongNamespaceDeclNameProposals(IInvocationContext context, IProblemLocation problem,
			Collection proposals) {
		ISourceModule cu = context.getCompilationUnit();
		boolean isLinked = cu.getResource().isLinked();
		String namespaceName = PHPToolkitUtil.getNamespaceResolver(cu.getScriptProject().getProject())
				.resolveNamespace(cu.getParent().getPath());
		int relevance = namespaceName.equals("") ? 7 : 5; //$NON-NLS-1$
		// correct package declaration
		proposals.add(new CorrectNamespaceDeclarationProposal(context, problem, relevance));

		// move to package
		Program program = context.getASTRoot();
		INamespaceResolver resolver = PHPToolkitUtil.getNamespaceResolver(cu.getScriptProject().getProject());
		String newNamespaceName = ""; //$NON-NLS-1$
		ASTNode coveredNode = problem.getCoveredNode(program);
		if (coveredNode != null) {
			NamespaceDeclaration namespace = program.getNamespaceDeclaration(coveredNode.getStart());
			if (namespace != null) {
				newNamespaceName = namespace.getName().getName();
			}
		}
		IPath resolveLocation = resolver.resolveLocation(cu.getPath(), newNamespaceName);
		if (resolveLocation == null) {
			return;
		}
		IFolder loc = ResourcesPlugin.getWorkspace().getRoot().getFolder(resolveLocation);
		IScriptFolder newPack = (IScriptFolder) ModelManager.create(loc, cu.getScriptProject());
		if (newPack == null) {
			// XXX: calculated dir outside buildpath
			return;
		}

		ISourceModule newCU = newPack.getSourceModule(cu.getElementName());
		if (!newCU.exists() && !isLinked) {
			String packageLabel = resolver.resolveNamespace(newPack.getPath());
			String label;
			if (packageLabel.isEmpty()) {
				label = Messages.format(CorrectionMessages.ReorgCorrectionsSubProcessor_movecu_default_description,
						BasicElementLabels.getFileName(cu));
			} else {

				label = Messages.format(CorrectionMessages.ReorgCorrectionsSubProcessor_movecu_description,
						new Object[] { BasicElementLabels.getFileName(cu), packageLabel });
			}
			CompositeChange composite = new CompositeChange(label);
			composite.add(new CreateSourceFolderChange(newPack));
			composite.add(new MoveSourceModuleChange(cu, newPack));

			proposals.add(new ChangeCorrectionProposal(label, composite, 6,
					DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_MOVE)));
		}

	}

}
