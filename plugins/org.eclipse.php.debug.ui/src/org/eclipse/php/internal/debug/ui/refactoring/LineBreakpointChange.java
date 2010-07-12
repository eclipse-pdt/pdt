/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.debug.core.model.PHPLineBreakpoint;

/**
 * Specialization of a {@link BreakpointChange}
 * 
 * @since 3.2
 */
public abstract class LineBreakpointChange extends BreakpointChange {

	private int fCharEnd, fCharStart, fLineNumber;

	// private boolean fConditionEnabled, fConditionSuspendOnTrue;
	// private String fCondition;

	/**
	 * Constructor
	 * 
	 * @param breakpoint
	 * @throws CoreException
	 */
	public LineBreakpointChange(PHPLineBreakpoint breakpoint)
			throws CoreException {
		super(breakpoint);
		fCharEnd = breakpoint.getCharEnd();
		fCharStart = breakpoint.getCharStart();
		fLineNumber = breakpoint.getLineNumber();
		// if (breakpoint.supportsCondition()) {
		// fCondition = breakpoint.getCondition();
		// fConditionEnabled = breakpoint.isConditionEnabled();
		// fConditionSuspendOnTrue = breakpoint.isConditionSuspendOnTrue();
		// }
	}

	/**
	 * Applies the original attributes to the new breakpoint
	 * 
	 * @param breakpoint
	 * @throws CoreException
	 */
	// protected void apply(IJavaLineBreakpoint breakpoint) throws CoreException
	// {
	// super.apply(breakpoint);
	// if (breakpoint.supportsCondition()) {
	// breakpoint.setCondition(fCondition);
	// breakpoint.setConditionEnabled(fConditionEnabled);
	// breakpoint.setConditionSuspendOnTrue(fConditionSuspendOnTrue);
	// }
	// }

	/**
	 * @see org.eclipse.php.internal.debug.ui.refactoring.BreakpointChange#getLineNumber()
	 */
	protected int getLineNumber() {
		return fLineNumber;
	}

	/**
	 * Returns the char end attribute of the underlying line breakpoint
	 * 
	 * @return the char end attribute of the underlying line breakpoint
	 */
	protected int getCharEnd() {
		return fCharEnd;
	}

	/**
	 * Returns the char start attribute of the underlying line breakpoint
	 * 
	 * @return the char start attribute of the underlying line breakpoint
	 */
	protected int getCharStart() {
		return fCharStart;
	}

}
