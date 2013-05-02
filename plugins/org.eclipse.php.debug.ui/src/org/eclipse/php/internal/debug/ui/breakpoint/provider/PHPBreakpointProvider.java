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
package org.eclipse.php.internal.debug.ui.breakpoint.provider;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.IEditorInput;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.ISourceEditingTextTools;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.breakpoint.IBreakpointProvider;

public class PHPBreakpointProvider implements IBreakpointProvider,
		IExecutableExtension {

	private IPHPBreakpointProvider provider;

	public PHPBreakpointProvider() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(PHPDebugUIPlugin.getID(),
						"phpBreakpointProviders"); //$NON-NLS-1$
		for (IConfigurationElement element : elements) {
			if ("provider".equals(element.getName())) { //$NON-NLS-1$
				try {
					provider = (IPHPBreakpointProvider) element
							.createExecutableExtension("class"); //$NON-NLS-1$
					break;
				} catch (CoreException e) {
					PHPUiPlugin.log(e);
				}
			}
		}
		if (provider == null) {
			provider = new DefaultPHPBreakpointProvider();
		}
	}

	public IStatus addBreakpoint(IDocument document, IEditorInput input,
			int lineNumber, int offset) throws CoreException {
		return provider.addBreakpoint(document, input, lineNumber, offset);
	}

	public IResource getResource(IEditorInput input) {
		return provider.getResource(input);
	}

	public void setSourceEditingTextTools(ISourceEditingTextTools tool) {
		provider.setSourceEditingTextTools(tool);
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		provider.setInitializationData(config, propertyName, data);
	}
}
