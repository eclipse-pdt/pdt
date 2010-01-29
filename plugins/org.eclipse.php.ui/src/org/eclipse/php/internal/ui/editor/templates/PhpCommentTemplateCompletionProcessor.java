/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.templates;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.templates.ScriptTemplateAccess;
import org.eclipse.dltk.ui.templates.ScriptTemplateCompletionProcessor;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.text.template.contentassist.TemplateInformationControlCreator;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.IWorkbenchPartOrientation;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

public class PhpCommentTemplateCompletionProcessor extends
		ScriptTemplateCompletionProcessor {

	private static final ICompletionProposal[] EMPTY_ICOMPLETION_PROPOSAL = new ICompletionProposal[0];
	private static final ICompletionProposal[] EMPTY = {};
	private String contextTypeId = PhpCommentTemplateContextType.PHP_COMMENT_CONTEXT_TYPE_ID;

	private static char[] IGNORE = new char[] { '.', ':', '@', '$' };

	public PhpCommentTemplateCompletionProcessor(
			ScriptContentAssistInvocationContext context) {
		super(context);
	}

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		if (isInDocOrCommentOrString(viewer, offset)) {

			ICompletionProposal[] completionProposals = super
					.computeCompletionProposals(viewer, offset);
			if (completionProposals == null) {
				return EMPTY;
			}
			return filterUsingPrefix(completionProposals, extractPrefix(viewer,
					offset));
		}
		return EMPTY;
	}

	private boolean isInDocOrCommentOrString(ITextViewer viewer, int offset) {
		IModelManager modelManager = StructuredModelManager.getModelManager();
		if (modelManager != null) {
			IStructuredModel structuredModel = null;
			structuredModel = modelManager.getExistingModelForRead(viewer
					.getDocument());
			if (structuredModel != null) {
				try {
					DOMModelForPHP domModelForPHP = (DOMModelForPHP) structuredModel;
					try {
						// Find the structured document region:
						IStructuredDocument document = (IStructuredDocument) domModelForPHP
								.getDocument().getStructuredDocument();
						IStructuredDocumentRegion sdRegion = document
								.getRegionAtCharacterOffset(offset);
						if (sdRegion == null) { // empty file case
							return false;
						}

						ITextRegion textRegion = sdRegion
								.getRegionAtCharacterOffset(offset);
						if (textRegion == null) {
							return false;
						}

						ITextRegionCollection container = sdRegion;

						if (textRegion instanceof ITextRegionContainer) {
							container = (ITextRegionContainer) textRegion;
							textRegion = container
									.getRegionAtCharacterOffset(offset);
						}

						if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
							IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) textRegion;
							textRegion = phpScriptRegion.getPhpToken(offset
									- container.getStartOffset()
									- phpScriptRegion.getStart());
							String type = textRegion.getType();
							if (PHPPartitionTypes
									.isPHPMultiLineCommentState(type)
									|| PHPPartitionTypes.isPHPDocState(type)
									|| PHPPartitionTypes
											.isPHPLineCommentState(type)
									|| PHPPartitionTypes.isPHPQuotesState(type)) {
								return true;
							}
						}
					} catch (Exception e) {
						Logger.logException(e);
					}
				} finally {
					structuredModel.releaseFromRead();
				}
			}
		}
		return false;
	}

	private ICompletionProposal[] filterUsingPrefix(
			ICompletionProposal[] completionProposals, String prefix) {
		if (prefix.length() == 0) { // no templats should be offered if there is
			// no prefix.
			return EMPTY_ICOMPLETION_PROPOSAL;
		}
		List<PhpTemplateProposal> matches = new ArrayList<PhpTemplateProposal>();
		for (int i = 0; i < completionProposals.length; i++) {
			PhpTemplateProposal phpTemplateProposal = (PhpTemplateProposal) completionProposals[i];
			Template template = phpTemplateProposal.getTemplateNew();
			if (template.getName().startsWith(prefix)) {
				matches.add(phpTemplateProposal);
			}
		}

		return (ICompletionProposal[]) matches
				.toArray(new ICompletionProposal[matches.size()]);
	}

	protected String extractPrefix(ITextViewer viewer, int offset) {
		int i = offset;
		IDocument document = viewer.getDocument();
		if (i > document.getLength())
			return ""; //$NON-NLS-1$

		try {
			while (i > 0) {
				char ch = document.getChar(i - 1);
				if (!(Character.isLetterOrDigit(ch))) {
					if (!('@' == ch || '_' == ch || '$' == ch)) {
						break;
					}
				}
				i--;
			}

			return document.get(i, offset - i);
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}
	}

	protected Template[] getTemplates(String contextTypeId) {
		Template templates[] = null;
		TemplateStore store = getTemplateStore();
		if (store != null)
			templates = store.getTemplates(contextTypeId);

		return templates;
	}

	protected TemplateContextType getContextType(ITextViewer viewer,
			IRegion region) {

		// For now always return the context type for ALL PHP regions
		TemplateContextType type = null;

		ContextTypeRegistry registry = getTemplateContextRegistry();
		if (registry != null)
			type = registry.getContextType(contextTypeId);

		return type;
	}

	protected Image getImage(Template template) {
		return PHPUiPlugin.getImageDescriptorRegistry().get(
				PHPPluginImages.DESC_TEMPLATE);
	}

	protected ContextTypeRegistry getTemplateContextRegistry() {
		return PHPUiPlugin.getDefault().getTemplateContextRegistry();
	}

	protected TemplateStore getTemplateStore() {
		return PHPUiPlugin.getDefault().getTemplateStore();
	}

	public void setContextTypeId(String contextTypeId) {
		this.contextTypeId = contextTypeId;
	}

	protected ICompletionProposal createProposal(Template template,
			TemplateContext context, IRegion region, int relevance) {
		return new PhpTemplateProposal(template, context, region,
				getImage(template), relevance);
	}

	protected IInformationControlCreator getInformationControlCreator() {
		int orientation = Window.getDefaultOrientation();
		IEditorPart editor = getContext().getEditor();
		if (editor == null)
			editor = DLTKUIPlugin.getActivePage().getActiveEditor();
		if (editor instanceof IWorkbenchPartOrientation)
			orientation = ((IWorkbenchPartOrientation) editor).getOrientation();
		return new TemplateInformationControlCreator(orientation);
	}

	/*
	 * @seeorg.eclipse.dltk.ui.templates.ScriptTemplateCompletionProcessor#
	 * getContextTypeId()
	 */
	protected String getContextTypeId() {
		return contextTypeId;
	}

	/*
	 * @see
	 * org.eclipse.dltk.ui.templates.ScriptTemplateCompletionProcessor#getIgnore
	 * ()
	 */
	protected char[] getIgnore() {
		return IGNORE;
	}

	/*
	 * @seeorg.eclipse.dltk.ui.templates.ScriptTemplateCompletionProcessor#
	 * getTemplateAccess()
	 */
	protected ScriptTemplateAccess getTemplateAccess() {
		return PhpTemplateAccess.getInstance();
	}

}
