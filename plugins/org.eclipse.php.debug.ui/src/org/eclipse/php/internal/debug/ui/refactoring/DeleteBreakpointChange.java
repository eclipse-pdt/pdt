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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.php.internal.debug.core.model.PHPLineBreakpoint;

import com.ibm.icu.text.MessageFormat;

/**
 * A change to delete a breakpoint. Currently used for undo. When undoing a
 * refactoring, the "target/original" resource does not exist in time to create
 * a marker on it, and thus the operation cannot be undone. Instead, we delete
 * breakpoints on undo.
 * 
 * @since 3.2
 * 
 */
public class DeleteBreakpointChange extends BreakpointChange {

	public DeleteBreakpointChange(PHPLineBreakpoint breakpoint)
			throws CoreException {
		super(breakpoint);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Change#getName()
	 */
	public String getName() {
		return MessageFormat.format(
				RefactoringMessages.DeleteBreakpointChange_0,
				new String[] { getBreakpointLabel(getOriginalBreakpoint()) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime
	 * .IProgressMonitor)
	 */
	public Change perform(IProgressMonitor pm) throws CoreException {
		getOriginalBreakpoint().delete();
		return new NullChange();
	}

}
