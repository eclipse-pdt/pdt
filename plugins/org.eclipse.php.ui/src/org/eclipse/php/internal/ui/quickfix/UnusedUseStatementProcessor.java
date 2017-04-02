/*******************************************************************************
 * Copyright (c) 2015 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.quickfix;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.compiler.ast.parser.PhpProblemIdentifier;
import org.eclipse.php.internal.core.corext.util.DocumentUtils;
import org.eclipse.php.internal.ui.text.correction.proposals.ASTRewriteCorrectionProposal;
import org.eclipse.php.internal.ui.text.correction.proposals.AbstractCorrectionProposal;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.php.ui.text.correction.IQuickFixProcessor;
import org.eclipse.php.ui.text.correction.IQuickFixProcessorExtension;
import org.eclipse.text.edits.TextEditGroup;

public class UnusedUseStatementProcessor implements IQuickFixProcessor, IQuickFixProcessorExtension {
	private final static String ORGANIZE_USE_STATEMENTS_ID = "org.eclipse.php.ui.editor.organize.use.statements"; //$NON-NLS-1$

	private class OrganizeUseStatementsProposal extends AbstractCorrectionProposal {

		private final IInvocationContext context;

		public OrganizeUseStatementsProposal(IInvocationContext context) {
			super(Messages.UnusedUseStatementProcessor_CommandName, 10,
					DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE), ORGANIZE_USE_STATEMENTS_ID);
			this.context = context;
		}

		@Override
		public void apply(IDocument document) {
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(context.getCompilationUnit());
			DocumentUtils.sortUseStatements(moduleDeclaration, document);
		}

		@Override
		public String getAdditionalProposalInfo() {
			return null;
		}
	}

	private class RemoveImportProposal extends ASTRewriteCorrectionProposal {

		private final IInvocationContext context;

		public RemoveImportProposal(IInvocationContext context) {
			super(Messages.UnusedUseStatementProcessor_RemoveImport, context.getCompilationUnit(), null, 9,
					DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_DELETE_IMPORT));
			this.context = context;
		}

		@Override
		protected ASTRewrite getRewrite() throws CoreException {
			ASTNode coveringNode = context.getCoveringNode();
			ASTRewrite rewrite = ASTRewrite.create(coveringNode.getAST());
			TextEditGroup editGroup = new TextEditGroup(Messages.UnusedUseStatementProcessor_RemoveImport);
			if (coveringNode != null) {
				if (coveringNode instanceof Identifier) {
					coveringNode = coveringNode.getParent();
				}
				if (coveringNode instanceof NamespaceName) {
					coveringNode = coveringNode.getParent();
				}
				if (coveringNode instanceof UseStatement) {
					rewrite.remove(coveringNode, editGroup);
				} else if (coveringNode instanceof UseStatementPart) {
					UseStatement part = (UseStatement) coveringNode.getParent();
					if (part.parts().size() == 1) {
						rewrite.remove(part, editGroup);
					} else {
						rewrite.remove(coveringNode, editGroup);
					}
				}
			}
			return rewrite;
		};
	}

	@Override
	public boolean hasCorrections(ISourceModule unit, IProblemIdentifier identifier) {
		return identifier == PhpProblemIdentifier.USE_STATEMENTS;
	}

	@Override
	public boolean hasCorrections(ISourceModule unit, int problemId) {
		return false;
	}

	@Override
	public IScriptCompletionProposal[] getCorrections(IInvocationContext context, IProblemLocation[] locations)
			throws CoreException {
		if (locations.length == 0) {
			return null;
		}
		boolean detect = false;
		for (int i = 0; i < locations.length; i++) {
			if (hasCorrections(context.getCompilationUnit(), locations[i].getProblemIdentifier())) {
				detect = true;
				break;
			}
		}
		if (!detect) {
			return null;
		}
		return new IScriptCompletionProposal[] { new OrganizeUseStatementsProposal(context),
				new RemoveImportProposal(context) };
	}

}
