/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IStep;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;

/**
 * Currently this implementation is for a single threaded language, so in the
 * case of ITerminate, ISuspendResume, IStep we just ask what the debug target
 * would do. IThread implementation is provided here.
 */
public class DBGpThread extends DBGpElement implements IThread {

	private IBreakpoint[] breakpoints;

	public DBGpThread(DBGpTarget target) {
		super(target);
	}

	@Override
	public String getName() throws DebugException {
		// PHP only has one thread, so no special naming required
		// PHP Thread
		return PHPDebugCoreMessages.XDebug_DBGpThread_0;
	}

	@Override
	public int getPriority() throws DebugException {
		return 0;
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		if (breakpoints == null) {
			return new IBreakpoint[0];
		}
		return breakpoints;
	}

	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		if (isSuspended()) {
			return ((DBGpTarget) getDebugTarget()).getCurrentStackFrames();
		} else {
			return new IStackFrame[0];
		}
	}

	@Override
	public IStackFrame getTopStackFrame() throws DebugException {
		IStackFrame[] frames = getStackFrames();
		if (frames.length > 0) {
			return frames[0];
		}
		return null;
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return isSuspended();
	}

	@Override
	public boolean canResume() {
		return getDebugTarget().canResume();
	}

	@Override
	public boolean canSuspend() {
		return getDebugTarget().canSuspend();
	}

	@Override
	public boolean isSuspended() {
		return getDebugTarget().isSuspended();
	}

	@Override
	public boolean canTerminate() {
		return getDebugTarget().canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return getDebugTarget().isTerminated();
	}

	@Override
	public boolean canStepInto() {
		return ((IStep) getDebugTarget()).canStepInto();
	}

	@Override
	public boolean canStepOver() {
		return ((IStep) getDebugTarget()).canStepOver();
	}

	@Override
	public boolean canStepReturn() {
		return ((IStep) getDebugTarget()).canStepReturn();
	}

	@Override
	public boolean isStepping() {
		return ((IStep) getDebugTarget()).isStepping();
	}

	@Override
	public void resume() throws DebugException {
		getDebugTarget().resume();
	}

	@Override
	public void suspend() throws DebugException {
		getDebugTarget().suspend();
	}

	@Override
	public void stepInto() throws DebugException {
		((IStep) getDebugTarget()).stepInto();
	}

	@Override
	public void stepOver() throws DebugException {
		((IStep) getDebugTarget()).stepOver();
	}

	@Override
	public void stepReturn() throws DebugException {
		((IStep) getDebugTarget()).stepReturn();
	}

	@Override
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}

	/**
	 * Set the breakpoints that caused this thread to suspend. The debug target
	 * provides this info.
	 * 
	 * @param breakpoints
	 */
	protected void setBreakpoints(IBreakpoint[] breakpoints) {
		this.breakpoints = breakpoints;
	}
}
