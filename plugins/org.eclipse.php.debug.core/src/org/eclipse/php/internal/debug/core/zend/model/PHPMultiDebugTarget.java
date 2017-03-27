/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.php.internal.debug.core.model.DebugOutput;
import org.eclipse.php.internal.debug.core.model.IPHPDebugTarget;
import org.eclipse.php.internal.debug.core.model.PHPDebugElement;

/**
 * This class is a wrapper for multiple debug targets that might be considered
 * as running in parallel in context of a particular debug session. This wrapper
 * pretends to be a multi-threaded element in debug model.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPMultiDebugTarget extends PHPDebugElement implements IPHPDebugTarget, IDebugEventSetListener {

	private ILaunch fLaunch;
	private IProcess fProcess;
	private boolean fTerminated = false;
	protected DebugOutput fDebugOutput = new DebugOutput();
	protected CopyOnWriteArrayList<IPHPDebugTarget> fDebugTargets = new CopyOnWriteArrayList<IPHPDebugTarget>();

	/**
	 * Creates new multiple debug targets wrapper.
	 * 
	 * @param launch
	 * @param process
	 * @throws CoreException
	 */
	public PHPMultiDebugTarget(ILaunch launch, IProcess process) throws CoreException {
		super(null);
		fLaunch = launch;
		fProcess = process;
		fProcess.setAttribute(IProcess.ATTR_PROCESS_TYPE, IPHPDebugConstants.PHPProcessType);
		if (fProcess instanceof PHPProcess) {
			((PHPProcess) fProcess).setDebugTarget(this);
		}
		fireCreationEvent();
		DebugPlugin.getDefault().addDebugEventListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.DebugElement#getDebugTarget()
	 */
	public IDebugTarget getDebugTarget() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.PHPDebugElement#getLaunch()
	 */
	@Override
	public ILaunch getLaunch() {
		return fLaunch;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getProcess()
	 */
	@Override
	public IProcess getProcess() {
		return fProcess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getThreads()
	 */
	@Override
	public IThread[] getThreads() throws DebugException {
		// Collect threads from all sub-targets
		List<IThread> threads = new ArrayList<IThread>();
		for (IPHPDebugTarget target : fDebugTargets)
			if (target.hasThreads())
				for (IThread thread : target.getThreads())
					threads.add(thread);
		return threads.toArray(new IThread[threads.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#hasThreads()
	 */
	@Override
	public boolean hasThreads() throws DebugException {
		// Check if any sub-target has at least one thread
		for (IPHPDebugTarget target : fDebugTargets)
			if (target.hasThreads())
				return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getName()
	 */
	@Override
	public String getName() throws DebugException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IDebugTarget#supportsBreakpoint(org.eclipse
	 * .debug.core.model.IBreakpoint)
	 */
	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		boolean supports = false;
		// Supports if any of sub-targets supports
		if (breakpoint.getModelIdentifier().equals(IPHPDebugConstants.ID_PHP_DEBUG_CORE)) {
			for (IPHPDebugTarget target : fDebugTargets)
				if (target.supportsBreakpoint(breakpoint))
					supports = true;
		}
		return supports;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	@Override
	public boolean canTerminate() {
		return !isTerminated();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	@Override
	public boolean isTerminated() {
		return fTerminated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	@Override
	public synchronized void terminate() throws DebugException {
		if (!fTerminated)
			for (IPHPDebugTarget target : fDebugTargets)
				target.terminate();
		DebugPlugin.getDefault().removeDebugEventListener(this);
		fireTerminateEvent();
		fTerminated = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	@Override
	public boolean canResume() {
		for (IPHPDebugTarget target : fDebugTargets)
			if (target.canResume())
				return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	@Override
	public boolean canSuspend() {
		for (IPHPDebugTarget target : fDebugTargets)
			if (target.canSuspend())
				return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	@Override
	public boolean isSuspended() {
		for (IPHPDebugTarget target : fDebugTargets)
			if (target.isSuspended())
				return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	@Override
	public void resume() throws DebugException {
		for (IPHPDebugTarget target : fDebugTargets)
			if (target.canResume())
				target.resume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	@Override
	public void suspend() throws DebugException {
		for (IPHPDebugTarget target : fDebugTargets)
			if (target.canSuspend())
				target.suspend();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.IBreakpointListener#breakpointAdded(org.eclipse
	 * .debug.core.model.IBreakpoint)
	 */
	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		for (IPHPDebugTarget target : fDebugTargets)
			target.breakpointAdded(breakpoint);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.IBreakpointListener#breakpointRemoved(org.eclipse
	 * .debug.core.model.IBreakpoint, org.eclipse.core.resources.IMarkerDelta)
	 */
	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		for (IPHPDebugTarget target : fDebugTargets)
			target.breakpointRemoved(breakpoint, delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.IBreakpointListener#breakpointChanged(org.eclipse
	 * .debug.core.model.IBreakpoint, org.eclipse.core.resources.IMarkerDelta)
	 */
	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		for (IPHPDebugTarget target : fDebugTargets)
			target.breakpointChanged(breakpoint, delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#canDisconnect()
	 */
	@Override
	public boolean canDisconnect() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#disconnect()
	 */
	@Override
	public void disconnect() throws DebugException {
		// ignore
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#isDisconnected()
	 */
	@Override
	public boolean isDisconnected() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#
	 * supportsStorageRetrieval ()
	 */
	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IMemoryBlockRetrieval#getMemoryBlock(long,
	 * long)
	 */
	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IPHPDebugTarget#getOutputBuffer
	 * ()
	 */
	@Override
	public DebugOutput getOutputBuffer() {
		return fDebugOutput;
	}

	/**
	 * always return false, this concept is xdebug specific.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IPHPDebugTarget#isWaiting()
	 */
	@Override
	public boolean isWaiting() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.IDebugEventSetListener#handleDebugEvents(org.
	 * eclipse .debug.core.DebugEvent[])
	 */
	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		for (int i = 0; i < events.length; i++) {
			DebugEvent event = events[i];
			Object source = event.getSource();
			if (event.getKind() == DebugEvent.TERMINATE) {
				if (source instanceof PHPDebugTarget) {
					PHPDebugTarget target = (PHPDebugTarget) source;
					if (fDebugTargets.contains(target))
						shutdown();
				}
			}
		}
	}

	/**
	 * Adds sub-target to this debug target.
	 * 
	 * @param target
	 */
	public void addSubTarget(IPHPDebugTarget target) {
		fDebugTargets.add(target);
	}

	private void shutdown() {
		// Check if all sub-targets are terminated
		for (IPHPDebugTarget target : fDebugTargets)
			if (!target.isTerminated())
				return;
		try {
			terminate();
		} catch (DebugException e) {
			Logger.logException(e);
		}
	}

}
