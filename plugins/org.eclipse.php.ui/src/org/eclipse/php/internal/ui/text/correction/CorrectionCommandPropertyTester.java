/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Janko Richter
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.documentModel.dom.IImplForPhp;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.wst.sse.ui.StructuredTextInvocationContext;

/**
 * This property tester checks, whether a correction command can be applied
 * 
 * Useful e.g. for menu contribution
 */
public class CorrectionCommandPropertyTester extends PropertyTester {

	private final static PHPCorrectionProcessor phpCorrectionProcessor = new PHPCorrectionProcessor();

	private static final String HAS_CORRECTION = "hasCorrection"; //$NON-NLS-1$

	private final Map<String, IHandlerActivation> fActivations = new HashMap<String, IHandlerActivation>();

	/**
	 * Returns the current active PHP editor, if available
	 * 
	 * @param receiver
	 * @return
	 */
	private static PHPStructuredEditor getCurrentEditor() {

		IWorkbenchPage page = PHPUiPlugin.getActivePage();
		if (null == page) {
			return null;
		}

		final IEditorPart editor = page.getActiveEditor();
		if (null == editor) {
			return null;
		}

		if (!(editor instanceof PHPStructuredEditor)) {
			return null;
		}

		return (PHPStructuredEditor) editor;
	}

	/**
	 * Returns the model element, if context is PHP
	 * 
	 * @param receiver
	 * @return
	 */
	private ISourceModule getModelElement(IImplForPhp receiver) {

		final IModelElement modelElement = receiver.getModelElement();

		if (!(modelElement.getElementType() == IModelElement.SOURCE_MODULE
				&& PHPToolkitUtil.isPhpElement(modelElement))) {
			return null;
		}

		if (modelElement instanceof ISourceModule) {
			return (ISourceModule) modelElement;
		}

		return null;
	}

	/**
	 * Registers the commands
	 */
	private void registerCommands() {
		if (null != fActivations) {
			return;
		}

		final IHandlerService handlerService = (IHandlerService) PlatformUI
				.getWorkbench().getService(IHandlerService.class);
		registerDescriptors(PHPCorrectionProcessor.getAssistProcessors(),
				handlerService);
		registerDescriptors(PHPCorrectionProcessor.getCorrectionProcessors(),
				handlerService);
	}

	/**
	 * Registers the contributed processors
	 */
	private void registerDescriptors(
			ContributedProcessorDescriptor[] descriptors,
			IHandlerService handlerService) {
		for (ContributedProcessorDescriptor descriptor : descriptors) {
			for (String commandId : descriptor.getSupportedCommands()) {
				fActivations.put(commandId, handlerService.activateHandler(
						commandId, new CorrectionCommandHandler()));
			}
		}
	}

	/**
	 * Checks, whether given commands are available for the current selection
	 * 
	 * @param receiver
	 * @param property
	 * @param args
	 *            List of command IDs
	 * @param expectedValue
	 *            Ignored
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {

		if (receiver instanceof List<?>) {
			List<?> list = (List<?>) receiver;
			if (list.size() > 0) {
				Object obj = list.get(0);
				if (!(obj instanceof IImplForPhp)) {
					return false;
				}

				final ISourceModule sourceModule = getModelElement(
						(IImplForPhp) obj);
				if (null == sourceModule) {
					return false;
				}

				final PHPStructuredEditor phpEditor = getCurrentEditor();
				if (null == phpEditor) {
					return false;
				}

				if (property.equals(HAS_CORRECTION)) {
					return testHasProposal(phpEditor, args);
				}
			}
		}

		return false;
	}

	/**
	 * Checks, whether given commands are available for the current selection
	 * 
	 * @param editor
	 * @param args
	 *            List of command IDs
	 */
	private boolean testHasProposal(PHPStructuredEditor editor, Object[] args) {

		final ITextSelection selection = (ITextSelection) editor.getSite()
				.getSelectionProvider().getSelection();

		registerCommands();
		for (Object arg : args) {
			if (arg instanceof String) {
				String commandId = (String) arg;
				final StructuredTextInvocationContext assistInvocation = new StructuredTextInvocationContext(
						editor.getViewer(), selection.getOffset(),
						selection.getLength(), new HashMap<Object, Object>());

				final ICompletionProposal[] proposals = phpCorrectionProcessor
						.computeQuickAssistProposals(assistInvocation);

				if (proposals == null || proposals.length == 0) {
					return false;
				}

				for (ICompletionProposal proposal : proposals) {
					if (proposal instanceof ICommandAccess) {
						final ICommandAccess command = (ICommandAccess) proposal;
						if (command.getCommandId() != null
								&& command.getCommandId().equals(commandId)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

}
