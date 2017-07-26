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

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ui.IEditorInput;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.breakpoint.IBreakpointProvider;

public interface IPHPBreakpointProvider extends IBreakpointProvider, IExecutableExtension {

	IBreakpoint createBreakpoint(IEditorInput input, IResource resource, int lineNumber, int charStart, int charEnd,
			Map<String, Comparable<?>> attributes) throws CoreException;
}
