/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.breakpoint;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint;

/**
 * PHP exception breakpoint annotation.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPExceptionBreakpointAnnotation extends Annotation {

	private static final String ANNOTATION_TYPE = "org.eclipse.php.debug.ui.phpExceptionBreakpointAnnotation"; //$NON-NLS-1$
	private IPHPExceptionBreakpoint breakpoint;

	public PHPExceptionBreakpointAnnotation(IPHPExceptionBreakpoint breakpoint) {
		super(ANNOTATION_TYPE, false, breakpoint.getExceptionName());
		this.breakpoint = breakpoint;
	}

	/**
	 * Returns breakpoint associated with this annotation.
	 * 
	 * @return breakpoint
	 */
	public IPHPExceptionBreakpoint getBreakpoint() {
		return breakpoint;
	}

}
