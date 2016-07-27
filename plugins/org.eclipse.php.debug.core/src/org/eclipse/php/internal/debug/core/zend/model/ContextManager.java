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
package org.eclipse.php.internal.debug.core.zend.model;

import java.util.ArrayList;

import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.zend.debugger.*;
import org.eclipse.php.internal.debug.core.zend.model.ResolveBlackList.Type;

public class ContextManager {

	private PHPDebugTarget fTarget;
	private IRemoteDebugger fDebugger;
	private IStackFrame[] fFrames;
	private ILock fFramesInitLock = Job.getJobManager().newLock();
	private int fSuspendCount;

	private final static String DUMMY_PHP_FILE = "dummy.php"; //$NON-NLS-1$

	public ContextManager(PHPDebugTarget target, IRemoteDebugger debugger) {
		super();
		fTarget = target;
		fSuspendCount = target.getSuspendCount();
		fDebugger = debugger;
	}

	public void addToResolveBlacklist(VirtualPath path, Type type) {
		ResolveBlackList.getInstance().add(fDebugger.getDebugHandler().getDebugTarget().getLaunch(), path, type);
	}

	public boolean isResolveBlacklisted(String remoteFile) {
		return ResolveBlackList.getInstance().containsEntry(fDebugger.getDebugHandler().getDebugTarget().getLaunch(),
				remoteFile);
	}

	public IStackFrame[] getStackFrames() throws DebugException {
		fFramesInitLock.acquire();
		try {
			if (fSuspendCount == fTarget.getSuspendCount()) {
				return fFrames;
			}
			// check to see if eclipse is getting the same stack frames again.
			PHPstack stack = fDebugger.getCallStack();
			if (stack != null) {
				StackLayer[] layers = stack.getLayers();
				if (layers.length == 1 && layers[0].getCalledFileName().endsWith(DUMMY_PHP_FILE)) {
					fDebugger.finish();// reached dummy file --> finish debug !
					return fFrames;
				}
				IThread[] threads = fTarget.getThreads();
				if (threads == null || threads.length == 0) {
					// Connection was probably dumped
					fFrames = new IStackFrame[0];
					return fFrames;
				}
				PHPThread thread = (PHPThread) threads[0];
				IStackFrame[] newFrames = applyDebugFilters(createNewFrames(layers, thread));
				fFrames = newFrames;
			} else {
				// Connection was probably dumped
				fFrames = new IStackFrame[0];
			}
			fSuspendCount = fTarget.getSuspendCount();
			return fFrames;
		} finally {
			fFramesInitLock.release();
		}
	}

	private IStackFrame[] applyDebugFilters(IStackFrame[] previousFrames) {
		ArrayList<IStackFrame> tempStackFrames = new ArrayList<IStackFrame>();
		for (int i = 0; i < previousFrames.length; i++) {
			if (i == previousFrames.length - 1) {
				String stackFrameName = ((PHPStackFrame) previousFrames[i]).getAbsoluteFileName();
				if (stackFrameName.endsWith(DUMMY_PHP_FILE)) {
					continue;// do not add it to stack view, filter it out.
				}
			}
			tempStackFrames.add(previousFrames[i]);
		}
		IStackFrame[] result = new IStackFrame[tempStackFrames.size()];
		tempStackFrames.toArray(result);
		return result;
	}

	public IVariable[] getVariables(PHPStackFrame stackFrame) {
		if (stackFrame == null) {
			return createVariables();
		}

		IVariable[] variables = null;
		try {
			variables = stackFrame.getVariables();
		} catch (DebugException e) {
		}
		return variables == null ? new IVariable[0] : variables;
	}

	private IStackFrame[] createNewFrames(StackLayer[] layers, PHPThread thread) throws DebugException {

		RemoteDebugger remoteDebugger = (RemoteDebugger) fDebugger;
		String cwd = null;
		String currentScript = null;

		IStackFrame[] frames = new IStackFrame[layers.length];
		int frameCt = layers.length;
		for (int i = 1; i < layers.length; i++) {

			String sName = layers[i].getCallerFileName();
			String rName = layers[i].getResolvedCallerFileName();
			if (rName == null) {
				rName = remoteDebugger.convertToLocalFilename(sName, cwd,
						frameCt < frames.length ? ((PHPStackFrame) frames[frameCt]).getSourceName() : null);
				if (rName == null) {
					rName = sName;
				}
				layers[i].setResolvedCallerFileName(rName);
			}

			frames[frameCt - 1] = new PHPStackFrame(thread, sName, rName, layers[i].getCallerFunctionName(),
					layers[i].getCallerLineNumber() + 1, layers[i].getDepth(), layers[i - 1].getVariables());
			frameCt--;

			if (!layers[i].getCalledFileName().equals(fTarget.getLastFileName())) {
				currentScript = rName;
			}
		}

		String resolvedFile = remoteDebugger.convertToLocalFilename(fTarget.getLastFileName(), cwd, currentScript);
		if (resolvedFile == null) {
			resolvedFile = fTarget.getLastFileName();
		}
		PHPStackFrame topFrame = new PHPStackFrame(thread, fTarget.getLastFileName(), resolvedFile,
				(layers.length == 1) ? "" //$NON-NLS-1$
						: layers[layers.length - 1].getCalledFunctionName(),
				fTarget.getLastStop(), frameCt, getLocalVariables());

		if (fFrames != null)
			frames[0] = mergeFrame((PHPStackFrame) fFrames[0], topFrame);
		else
			frames[0] = topFrame;

		return frames;
	}

	/**
	 * Merge existing top frame with the incoming one. If both frames have only
	 * different line number then existing is being updated with the use of data
	 * from incoming one.
	 * 
	 * @param existingFrame
	 * @param incomingFrame
	 * @return merged frame
	 * @throws DebugException
	 */
	private IStackFrame mergeFrame(PHPStackFrame existingFrame, PHPStackFrame incomingFrame) throws DebugException {
		if (existingFrame.getName().equals(incomingFrame.getName())
				&& existingFrame.getAbsoluteFileName().equals(incomingFrame.getAbsoluteFileName())
				&& existingFrame.getSourceName().equals(incomingFrame.getSourceName())
				&& existingFrame.getThread() == incomingFrame.getThread()
				&& existingFrame.getDepth() == incomingFrame.getDepth()) {
			existingFrame.update(incomingFrame.getLineNumber(), incomingFrame.getStackVariables());
			return existingFrame;
		}
		return incomingFrame;
	}

	private Expression[] getLocalVariables() {
		DefaultExpressionsManager expressionsManager = fTarget.getExpressionManager();
		if (expressionsManager == null) {
			return new Expression[0];
		}
		return expressionsManager.getCurrentVariables(1);
	}

	private IVariable[] createVariables() {
		Expression[] localVariables = getLocalVariables();
		IVariable[] variables = new PHPVariable[localVariables.length];
		for (int i = 0; i < localVariables.length; i++) {
			variables[i] = new PHPVariable(fTarget, localVariables[i]);
		}
		return variables;
	}

	public IRemoteDebugger getRemoteDebugger() {
		return fDebugger;
	}
}
