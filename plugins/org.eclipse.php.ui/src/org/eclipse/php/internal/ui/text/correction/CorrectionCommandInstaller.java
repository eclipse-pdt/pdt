/*******************************************************************************
 * Copyright (c) 2015 Dawid Pakuała and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;

public class CorrectionCommandInstaller {

	private static Map<String, IHandlerActivation> fActivations = new HashMap<String, IHandlerActivation>();

	private CorrectionCommandInstaller() {
	}

	public static void registerCommands() {
		final IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench()
				.getService(IHandlerService.class);
		registerDescriptors(PHPCorrectionProcessor.getAssistProcessors(), handlerService);
		registerDescriptors(PHPCorrectionProcessor.getCorrectionProcessors(), handlerService);
	}

	private static void registerDescriptors(ContributedProcessorDescriptor[] descriptors,
			IHandlerService handlerService) {
		for (ContributedProcessorDescriptor descriptor : descriptors) {
			for (String commandId : descriptor.getSupportedCommands()) {
				if (fActivations.containsKey(commandId)) {
					PHPUiPlugin.logErrorMessage("Command ID already registered: " + commandId); //$NON-NLS-1$
					continue;
				}
				fActivations.put(commandId, handlerService.activateHandler(commandId, new CorrectionCommandHandler()));
			}
		}

	}

	public static void unregisterCommands() {
		final IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench()
				.getService(IHandlerService.class);
		if (handlerService != null) {
			handlerService.deactivateHandlers(fActivations.values());
		}
		fActivations.clear();
	}
}
