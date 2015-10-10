/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dawid Pakuła - Adapt for PDT
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.HashMap;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.ISources;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.wst.sse.ui.StructuredTextInvocationContext;

/**
 * This class allow to run quick assist / quick fix directly from shortcut
 * 
 * Based on JDT/JSDT implementation
 */
public class CorrectionCommandHandler extends AbstractHandler {
	private final static PHPCorrectionProcessor phpCorrectionProcessor = new PHPCorrectionProcessor();

	public CorrectionCommandHandler() {
		setBaseEnabled(false);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final String commandId = event.getCommand().getId();
		final IEvaluationContext context = (IEvaluationContext) event.getApplicationContext();

		final PHPStructuredEditor editor = (PHPStructuredEditor) context.getVariable(ISources.ACTIVE_PART_NAME);
		final ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();

		final StructuredTextInvocationContext assistInvocation = new StructuredTextInvocationContext(editor.getViewer(),
				selection.getOffset(), selection.getLength(), new HashMap<Object, Object>());

		final ICompletionProposal[] proposals = phpCorrectionProcessor.computeQuickAssistProposals(assistInvocation);

		if (proposals == null || proposals.length == 0) {
			return null;
		}

		for (ICompletionProposal proposal : proposals) {
			if (proposal instanceof ICommandAccess) {
				final ICommandAccess command = (ICommandAccess) proposal;
				if (command.getCommandId() != null && command.getCommandId().equals(commandId)) {
					runProposal(editor, proposal, assistInvocation);
					return null;
				}
			}
		}

		return null;
	}

	/**
	 * Triggers are ignored
	 */
	private void runProposal(PHPStructuredEditor editor, ICompletionProposal proposal,
			StructuredTextInvocationContext context) {
		if (proposal instanceof ICompletionProposalExtension2) {
			((ICompletionProposalExtension2) proposal).apply(editor.getTextViewer(), (char) 0, 0, context.getOffset());
		} else if (proposal instanceof ICompletionProposalExtension && editor.getDocument() != null) {
			((ICompletionProposalExtension) proposal).apply(editor.getDocument(), (char) 0, context.getOffset());
		} else {
			proposal.apply(editor.getDocument());
		}
	}

	@Override
	public void setEnabled(Object evaluationContext) {

		try {
			IEvaluationContext context = (IEvaluationContext) evaluationContext;
			if (!(context.getVariable(ISources.ACTIVE_PART_NAME) instanceof PHPStructuredEditor)) {
				setBaseEnabled(false);
				return;
			}
			PHPStructuredEditor editor = (PHPStructuredEditor) context.getVariable(ISources.ACTIVE_PART_NAME);

			final ISelection selection = editor.getSelectionProvider().getSelection();
			if (!(selection instanceof ITextSelection)) {
				setBaseEnabled(false);
				return;
			}
			final int offset = ((ITextSelection) selection).getOffset();
			ITypedRegion partition = editor.getDocument().getPartition(offset);
			setBaseEnabled(partition.getType().equals(PHPPartitionTypes.PHP_DEFAULT));
			return;
		} catch (IllegalArgumentException e) {
		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}
		setBaseEnabled(false);
	}

	/**
	 * 
	 */
	public static String getShortcut(String commandId) {
		final IBindingService keys = (IBindingService) PlatformUI.getWorkbench().getService(IBindingService.class);

		if (commandId != null && keys != null) {
			TriggerSequence trigger = keys.getBestActiveBindingFor(commandId);
			if (trigger != null && trigger.getTriggers().length > 0) {
				return trigger.format();
			}
		}

		return null;
	}

	public static String appendShortcut(String name, String commandId) {
		if (commandId != null && commandId.isEmpty()) {
			String shortcut = getShortcut(commandId);
			if (shortcut != null) {
				return NLS.bind(CorrectionMessages.ChangeCorrectionProposal_name_with_shortcut,
						new String[] { name, shortcut });
			}
		}
		return name;
	}

	public static StyledString styleWithShortcut(String name, String commandId) {
		StyledString str = new StyledString(name);
		if (commandId != null && !commandId.isEmpty()) {
			String keys = getShortcut(commandId);
			if (keys == null) {
				return str;
			}
			String decorated = NLS.bind(CorrectionMessages.ChangeCorrectionProposal_name_with_shortcut,
					new String[] { name, keys });
			return StyledCellLabelProvider.styleDecoratedString(decorated, StyledString.QUALIFIER_STYLER, str);

		}

		return str;
	}
}
