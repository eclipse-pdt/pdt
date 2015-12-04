/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
@SuppressWarnings("restriction")
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
