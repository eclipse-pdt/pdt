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

import java.util.*;
import java.util.Map.Entry;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.templates.ScriptTemplateAccess;
import org.eclipse.dltk.ui.templates.ScriptTemplateCompletionProcessor;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.*;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.jface.window.Window;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.text.template.contentassist.TemplateInformationControlCreator;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.IWorkbenchPartOrientation;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

public class PhpTemplateCompletionProcessor extends
		ScriptTemplateCompletionProcessor {

	private static final String $_LINE_SELECTION = "${" + GlobalTemplateVariables.LineSelection.NAME + "}"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String $_WORD_SELECTION = "${" + GlobalTemplateVariables.WordSelection.NAME + "}"; //$NON-NLS-1$ //$NON-NLS-2$

	private static final ICompletionProposal[] EMPTY = {};
	private String contextTypeId = PhpTemplateContextType.PHP_CONTEXT_TYPE_ID;

	private static char[] IGNORE = new char[] { '.', ':', '@', '$' };
	private IDocument document;
	private boolean explicit;
	private boolean isSelection;
	/** Positions created on the key documents to remove in reset. */
	private final Map<IDocument, Position> fPositions = new HashMap<IDocument, Position>();

	private IMethod enclosingMethod;
	private IType enclosingType;

	public PhpTemplateCompletionProcessor(
			ScriptContentAssistInvocationContext context, boolean explicit) {
		super(context);
		this.explicit = explicit;
	}

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		document = viewer.getDocument();
		try {
			String type = TextUtilities.getContentType(document,
					IStructuredPartitioning.DEFAULT_STRUCTURED_PARTITIONING,
					offset, true);
			if (!PHPPartitionTypes.PHP_DEFAULT.equals(type)) {
				return EMPTY;
			}
		} catch (BadLocationException e) {
		}
		if (isInDocOrCommentOrString(viewer, offset)) {
			return EMPTY;
		}

		ISourceModule sourceModule = getContext().getSourceModule();
		if (sourceModule == null) {
			return EMPTY;
		}
		List<String> contextIds = new ArrayList<String>();
		contextIds.add(contextTypeId);
		// check whether enclosing element is a method
		try {
			IModelElement enclosingElement = sourceModule.getElementAt(offset);
			while (enclosingElement instanceof IField) {
				enclosingElement = enclosingElement.getParent();
			}
			if ((enclosingElement instanceof IMethod)) {
				enclosingMethod = (IMethod) enclosingElement;
			}

			boolean isFieldAccess = false;

			try {
				if (offset > 2) {
					String accessPrefix = document.get(offset - 2, 2);
					if ("->".equals(accessPrefix) || "::".equals(accessPrefix)) { //$NON-NLS-1$ //$NON-NLS-2$
						isFieldAccess = true;
					}
				}
			} catch (BadLocationException e) {
			}

			// find the most outer enclosing type if exists
			while (enclosingElement != null
					&& !(enclosingElement instanceof IType)) {
				enclosingElement = enclosingElement.getParent();
			}
			enclosingType = (IType) enclosingElement;

			if (enclosingMethod == null && enclosingType == null) {

				contextIds
						.add(PhpTemplateContextType.PHP_STATEMENTS_CONTEXT_TYPE_ID);
				contextIds
						.add(PhpTemplateContextType.PHP_GLOBAL_MEMBERS_CONTEXT_TYPE_ID);
			} else if (enclosingMethod == null && enclosingType != null
					&& !isFieldAccess) {
				if (!PHPFlags.isNamespace(enclosingType.getFlags())) {
					contextIds
							.add(PhpTemplateContextType.PHP_TYPE_MEMBERS_CONTEXT_TYPE_ID);
					if (PHPFlags.isClass(enclosingType.getFlags())) {
						contextIds
								.add(PhpTemplateContextType.PHP_CLASS_MEMBERS_CONTEXT_TYPE_ID);
					}
				} else {
					contextIds
							.add(PhpTemplateContextType.PHP_STATEMENTS_CONTEXT_TYPE_ID);
					contextIds
							.add(PhpTemplateContextType.PHP_GLOBAL_MEMBERS_CONTEXT_TYPE_ID);
				}
			} else if (enclosingMethod != null && enclosingType != null
					&& !isFieldAccess) {
				if (!PHPFlags.isNamespace(enclosingType.getFlags())) {
					contextIds
							.add(PhpTemplateContextType.PHP_TYPE_METHOD_STATEMENTS_CONTEXT_TYPE_ID);
				}
				contextIds
						.add(PhpTemplateContextType.PHP_STATEMENTS_CONTEXT_TYPE_ID);
			} else if (enclosingMethod != null && enclosingType == null) {
				contextIds
						.add(PhpTemplateContextType.PHP_STATEMENTS_CONTEXT_TYPE_ID);
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}

		ITextSelection selection = (ITextSelection) viewer
				.getSelectionProvider().getSelection();

		ICompletionProposal[] selectionProposal = EMPTY;
		if (selection.getLength() != 0) {
			isSelection = true;
			int tempOffset = offset;
			// adjust offset to end of normalized selection
			if (selection.getOffset() == tempOffset)
				tempOffset = selection.getOffset() + selection.getLength();

			String prefix = extractPrefix(viewer, tempOffset);
			IRegion region = new Region(selection.getOffset(), 0);
			Position position = new Position(offset, selection.getLength());
			TemplateContext context = createContext(viewer, region, position);//
			if (context == null)
				return new ICompletionProposal[0];

			try {
				document.addPosition(position);
				fPositions.put(document, position);
			} catch (BadLocationException e) {
			}

			// name of the selection variables {line, word}_selection
			context.setVariable("selection", selection.getText()); //$NON-NLS-1$

			boolean multipleLinesSelected = areMultipleLinesSelected(viewer);

			List<TemplateProposal> matches = new ArrayList<TemplateProposal>();
			Template[] templates = getTemplates(contextIds);
			for (int i = 0; i != templates.length; i++) {
				Template template = templates[i];
				if (context.canEvaluate(template)
						&& (!multipleLinesSelected
								&& template.getPattern().indexOf(
										$_WORD_SELECTION) != -1 || (multipleLinesSelected && template
								.getPattern().indexOf($_LINE_SELECTION) != -1))) {
					matches.add((TemplateProposal) createProposal(templates[i],
							context, region, getRelevance(template, prefix)));
				}
			}
			selectionProposal = matches.toArray(new ICompletionProposal[matches
					.size()]);
		} else {
			isSelection = false;
		}
		String prefix = extractPrefix(viewer, offset);
		if (!isValidPrefix(prefix)) {
			return new ICompletionProposal[0];
		}
		IRegion region = new Region(offset - prefix.length(), prefix.length());
		TemplateContext context = createContext(viewer, region);
		if (context == null)
			return new ICompletionProposal[0];

		// name of the selection variables {line, word}_selection
		context.setVariable("selection", selection.getText()); //$NON-NLS-1$

		List<TemplateProposal> matches = new ArrayList<TemplateProposal>();

		Template[] templates = getTemplates(contextIds);
		for (int i = 0; i < templates.length; i++) {
			Template template = templates[i];
			try {
				context.getContextType().validate(template.getPattern());
			} catch (TemplateException e) {
				continue;
			}
			if (template.getName().startsWith(prefix))
				matches.add((TemplateProposal) createProposal(template,
						context, region, getRelevance(template, prefix)));
		}

		final IInformationControlCreator controlCreator = getInformationControlCreator();
		for (TemplateProposal proposal : matches) {
			proposal.setInformationControlCreator(controlCreator);
		}

		List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();
		for (int i = 0; i < selectionProposal.length; i++) {
			result.add(selectionProposal[i]);
		}
		for (int i = 0; i < matches.size(); i++) {
			result.add(matches.get(i));
		}

		return result.toArray(new ICompletionProposal[matches.size()]);
	}

	private Template[] getTemplates(List<String> contextIds) {
		List<Template> result = new ArrayList<Template>();
		for (String id : contextIds) {
			Template[] templates = getTemplates(id);
			result.addAll(Arrays.asList(templates));
		}
		return result.toArray(new Template[result.size()]);
	}

	/**
	 * Empties the collector.
	 */
	public void reset() {
		for (Iterator<Entry<IDocument, Position>> it = fPositions.entrySet()
				.iterator(); it.hasNext();) {
			Entry<IDocument, Position> entry = it.next();
			IDocument doc = entry.getKey();
			Position position = entry.getValue();
			doc.removePosition(position);
		}
		fPositions.clear();
	}

	protected TemplateContext createContext(ITextViewer viewer, IRegion region,
			Position position) {
		TemplateContextType contextType = getContextType(viewer, region);
		if (contextType instanceof ScriptTemplateContextType) {
			IDocument document = viewer.getDocument();

			ISourceModule sourceModule = getContext().getSourceModule();
			if (sourceModule == null) {
				return null;
			}
			return ((ScriptTemplateContextType) contextType).createContext(
					document, position, sourceModule);
		}
		return null;
	}

	protected boolean isValidPrefix(String prefix) {
		if ((!explicit || isSelection)
				&& (prefix == null || prefix.length() == 0)) {
			return false;
		}
		return true;
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

	/**
	 * Returns <code>true</code> if one line is completely selected or if
	 * multiple lines are selected. Being completely selected means that all
	 * characters except the new line characters are selected.
	 * 
	 * @param viewer
	 *            the text viewer
	 * @return <code>true</code> if one or multiple lines are selected
	 * @since 2.1
	 */
	private boolean areMultipleLinesSelected(ITextViewer viewer) {
		if (viewer == null)
			return false;

		Point s = viewer.getSelectedRange();
		if (s.y == 0)
			return false;

		try {

			IDocument document = viewer.getDocument();
			int startLine = document.getLineOfOffset(s.x);
			int endLine = document.getLineOfOffset(s.x + s.y);
			IRegion line = document.getLineInformation(startLine);
			return startLine != endLine
					|| (s.x == line.getOffset() && s.y == line.getLength());

		} catch (BadLocationException x) {
			return false;
		}
	}

	@Override
	protected int getRelevance(Template template, String prefix) {
		return 80;
	}
}
