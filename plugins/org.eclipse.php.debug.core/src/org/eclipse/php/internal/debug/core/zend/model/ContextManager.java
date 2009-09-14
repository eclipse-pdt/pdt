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
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.zend.debugger.*;
import org.eclipse.php.internal.debug.core.zend.model.ResolveBlackList.Type;

public class ContextManager {

	private PHPDebugTarget fTarget;
	private IRemoteDebugger fDebugger;
	private StackLayer[] fPreviousLayers;
	private IStackFrame[] fPreviousFrames;
	private ILock fFramesInitLock = Job.getJobManager().newLock();

	private int fSuspendCount;

	private final static String DUMMY_PHP_FILE = "dummy.php";

	public ContextManager(PHPDebugTarget target, IRemoteDebugger debugger) {
		super();
		fTarget = target;
		fSuspendCount = target.getSuspendCount();
		fDebugger = debugger;
	}

	public void addToResolveBlacklist(VirtualPath path, Type type) {
		ResolveBlackList.getInstance().add(
				fDebugger.getDebugHandler().getDebugTarget().getLaunch(), path,
				type);
	}

	public boolean isResolveBlacklisted(String remoteFile) {
		return ResolveBlackList.getInstance().containsEntry(
				fDebugger.getDebugHandler().getDebugTarget().getLaunch(),
				remoteFile);
	}

	private void copyVariablesFromPreviousFrames(IStackFrame[] frames) {
		if (fPreviousFrames != null) {
			for (int i = frames.length - 1, c = fPreviousFrames.length - 1; i > 0
					&& c >= 0; --i, --c) {
				if (((PHPStackFrame) frames[i]).getStackVariables().length == 0) {
					((PHPStackFrame) frames[i])
							.setStackVariables(((PHPStackFrame) fPreviousFrames[c])
									.getStackVariables());
				}
			}
		}
	}

	public IStackFrame[] getStackFrames() throws DebugException {

		// check to see if eclipse is getting the same stack frames again.
		PHPstack stack = fDebugger.getCallStack();
		PHPThread thread = (PHPThread) fTarget.getThreads()[0];
		StackLayer[] layers = stack.getLayers();

		fFramesInitLock.acquire();
		try {
			if (fPreviousFrames == null) {
				IStackFrame[] newFrames = applyDebugFilters(createNewFrames(
						layers, thread));
				copyVariablesFromPreviousFrames(newFrames);
				fPreviousFrames = newFrames;

				DefaultExpressionsManager expressionsManager = fTarget
						.getExpressionManager();
				if (expressionsManager != null) {
					expressionsManager.clear();
				}
				fSuspendCount = fTarget.getSuspendCount();
				return fPreviousFrames;
			}
		} finally {
			fFramesInitLock.release();
		}

		if (fSuspendCount == fTarget.getSuspendCount()) {
			return fPreviousFrames;
		}

		// check to see if layers are the same as the previous thread
		fSuspendCount = fTarget.getSuspendCount();

		if (layers.length == 1
				&& layers[0].getCalledFileName().endsWith(DUMMY_PHP_FILE)) {
			fDebugger.finish();// reached dummy file --> finish debug !
		} else {
			IStackFrame[] newFrames = applyDebugFilters(createNewFrames(layers,
					thread));
			copyVariablesFromPreviousFrames(newFrames);
			fPreviousFrames = newFrames;

			fSuspendCount = fTarget.getSuspendCount();
		}

		return fPreviousFrames;
	}

	private IStackFrame[] applyDebugFilters(IStackFrame[] previousFrames) {
		ArrayList<IStackFrame> tempStackFrames = new ArrayList<IStackFrame>();
		for (int i = 0; i < previousFrames.length; i++) {
			if (i == previousFrames.length - 1) {
				String stackFrameName = ((PHPStackFrame) previousFrames[i])
						.getAbsoluteFileName();
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

	public Expression[] getStackVariables(PHPStackFrame stack) {
		String functionName = "";
		try {
			functionName = stack.getName();
		} catch (DebugException e) {
			// PHPStack Doesn't throw exception, just log and ignore
			Logger.logException("Problem getting name from stack", e);
		}

		Expression[] variables = new Expression[0];
		if (!functionName.equals("")) {
			StackLayer stackLayer = null;
			try {
				for (int i = fPreviousLayers.length - 1; i >= 0; --i) {
					StackLayer element = fPreviousLayers[i];
					if (element.getCalledFileName().equals(
							stack.getAbsoluteFileName())
							&& element.getCalledFunctionName().equals(
									stack.getName())) {
						stackLayer = element;
						break;
					}
				}
			} catch (DebugException e) {
			}

			if (stackLayer != null) {
				variables = stackLayer.getVariables();
			}
		}
		return variables;
	}

	private boolean compareLayers(StackLayer[] layers, StackLayer[] prevLayers) {

		if (layers.length != prevLayers.length)
			return false;

		for (int i = 0; i < layers.length; i++) {
			if (!compareLayer(layers[i], prevLayers[i]))
				return false;
		}
		return true;
	}

	private boolean compareLayer(StackLayer layer, StackLayer prevLayer) {
		return layer.getCallerFileName().equals(prevLayer.getCallerFileName())
				&& layer.getCallerFunctionName().equals(
						prevLayer.getCallerFunctionName())
				&& layer.getCallerLineNumber() == prevLayer
						.getCallerLineNumber()
				&& layer.getCalledFileName().equals(
						prevLayer.getCalledFileName())
				&& layer.getCalledFunctionName().equals(
						prevLayer.getCalledFunctionName())
				&& layer.getCalledLineNumber() == prevLayer
						.getCalledLineNumber();
	}

	private IStackFrame[] createNewFrames(StackLayer[] layers, PHPThread thread)
			throws DebugException {

		boolean layersSame = false;
		if (fPreviousLayers != null) {
			layersSame = compareLayers(layers, fPreviousLayers);
		}

		RemoteDebugger remoteDebugger = (RemoteDebugger) fDebugger;
		String cwd = null;
		String currentScript = null;

		if (!layersSame) {
			cwd = remoteDebugger.getCurrentWorkingDirectory();
		}

		IStackFrame[] frames = new IStackFrame[((layers.length - 1) * 2) + 1];
		int frameCt = ((layers.length - 1) * 2 + 1);
		for (int i = 1; i < layers.length; i++) {

			String sName = layers[i].getCallerFileName();
			String rName;
			if (layersSame) {
				rName = fPreviousLayers[i].getResolvedCallerFileName();
			} else {
				rName = remoteDebugger
						.convertToLocalFilename(
								sName,
								cwd,
								frameCt < frames.length ? ((PHPStackFrame) frames[frameCt])
										.getSourceName()
										: null);
				if (rName == null) {
					rName = sName;
				}
				layers[i].setResolvedCallerFileName(rName);
			}

			frames[frameCt - 1] = new PHPStackFrame(thread, sName, layers[i]
					.getCallerFunctionName(),
					layers[i].getCallerLineNumber() + 1, frameCt, rName,
					layers[i - 1].getVariables());
			frameCt--;

			sName = layers[i].getCalledFileName();
			if (layersSame) {
				rName = fPreviousLayers[i].getResolvedCalledFileName();
			} else {
				rName = remoteDebugger
						.convertToLocalFilename(sName, cwd, rName);
				if (rName == null) {
					rName = sName;
				}
				layers[i].setResolvedCalledFileName(rName);
			}

			frames[frameCt - 1] = new PHPStackFrame(thread, sName, layers[i]
					.getCalledFunctionName(),
					layers[i].getCalledLineNumber() + 1, frameCt, layers[i],
					rName, layers[i].getVariables());
			frameCt--;

			if (!layers[i].getCalledFileName()
					.equals(fTarget.getLastFileName())) {
				currentScript = rName;
			}
		}

		String resolvedFile = remoteDebugger.convertToLocalFilename(fTarget
				.getLastFileName(), cwd, currentScript);
		if (resolvedFile == null) {
			resolvedFile = fTarget.getLastFileName();
		}
		frames[0] = new PHPStackFrame(thread, fTarget.getLastFileName(),
				(layers.length == 1) ? "" : frames[1].getName(), fTarget
						.getLastStop(), frameCt, resolvedFile,
				getLocalVariables());
		fPreviousLayers = layers;
		return frames;
	}

	private Expression[] getLocalVariables() {
		DefaultExpressionsManager expressionsManager = fTarget
				.getExpressionManager();
		if (expressionsManager == null) {
			return new Expression[0];
		}
		return expressionsManager.getLocalVariables(1);
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
