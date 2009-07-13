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
import java.util.HashMap;
import java.util.Map;

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
	private Map<IStackFrame, IVariable[]> fVariables = new HashMap<IStackFrame, IVariable[]>();

	private int fSuspendCount;

	private final static String DUMMY_PHP_FILE = "dummy.php";

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
		return ResolveBlackList.getInstance().containsEntry(fDebugger.getDebugHandler().getDebugTarget().getLaunch(), remoteFile);
	}

	public IStackFrame[] getStackFrames() throws DebugException {

		// check to see if eclipse is getting the same stack frames again.
		PHPstack stack = fDebugger.getCallStack();
		PHPThread thread = (PHPThread) fTarget.getThreads()[0];
		StackLayer[] layers = stack.getLayers();

		fFramesInitLock.acquire();
		try {
			if (fPreviousFrames == null) {
				fPreviousFrames = applyDebugFilters(createNewFrames(layers, thread));
				DefaultExpressionsManager expressionsManager = fTarget.getExpressionManager();
				if (expressionsManager != null) {
					expressionsManager.clear();
				}

				fVariables.clear();
				IVariable[] variables = createVariables();
				for (IStackFrame frame : fPreviousFrames) {
					fVariables.put(frame, variables);
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
		boolean layersSame = compareLayers(layers, fPreviousLayers);
		if (layersSame) {
			// Update top of the stack frame:
			PHPStackFrame originalFrame = (PHPStackFrame) fPreviousFrames[0];
			int topID = originalFrame.getIdentifier();
			String fileName = originalFrame.getAbsoluteFileName();
			String sourceFile = originalFrame.getSourceName();

			fVariables.remove(fPreviousFrames[0]);
			fPreviousFrames[0] = new PHPStackFrame(
				thread, fileName, (layers.length == 1) ? "" : fPreviousFrames[1].getName(), fTarget.getLastStop(), topID, sourceFile);

			fVariables.put(fPreviousFrames[0], createVariables());

		} else {
			if (layers.length == 1 && layers[0].getCalledFileName().endsWith(DUMMY_PHP_FILE)) {
				fDebugger.finish();//reached dummy file --> finish debug !
			} else {
				fPreviousFrames = applyDebugFilters(createNewFrames(layers, thread));

				IVariable[] variables = null;
				for (IStackFrame frame : fPreviousFrames) {
					if (!fVariables.containsKey(frame)) {
						if (variables == null) {
							variables = createVariables();
						}
						fVariables.put(frame, variables);
					}
				}

				fSuspendCount = fTarget.getSuspendCount();
			}
		}
		return fPreviousFrames;
	}

	private IStackFrame[] applyDebugFilters(IStackFrame[] previousFrames) {
		ArrayList<IStackFrame> tempStackFrames = new ArrayList<IStackFrame>();
		for (int i = 0; i < previousFrames.length; i++) {
			if (i == previousFrames.length - 1) {
				String stackFrameName = ((PHPStackFrame) previousFrames[i]).getAbsoluteFileName();
				if (stackFrameName.endsWith(DUMMY_PHP_FILE)) {
					continue;//do not add it to stack view, filter it out.
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
		IVariable[] variables = fVariables.get(stackFrame);
		return variables == null ? new IVariable[0] : variables;
	}

	public Expression[] getStackVariables(PHPStackFrame stack) {
		String functionName = "";
		try {
			functionName = stack.getName();
		} catch (DebugException e) {
			// PHPStack Doesn't throw exception, just log and ignore
			Logger.logException("PHP Problem getting name from stack", e);
		}

		Expression[] variables = new Expression[0];
		if (!functionName.equals("")) {
			StackLayer stackLayer = null;
			try {
				for (int i = fPreviousLayers.length - 1; i >= 0; --i) {
					StackLayer element = fPreviousLayers[i];
					if (element.getCalledFileName().equals(stack.getAbsoluteFileName())
							&& element.getCalledFunctionName().equals(stack.getName())) {
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
			&& layer.getCallerFunctionName().equals(prevLayer.getCallerFunctionName())
			&& layer.getCallerLineNumber() == prevLayer.getCallerLineNumber()
			&& layer.getCalledFileName().equals(prevLayer.getCalledFileName())
			&& layer.getCalledFunctionName().equals(prevLayer.getCalledFunctionName())
			&& layer.getCalledLineNumber() == prevLayer.getCalledLineNumber();
	}

	private IStackFrame[] createNewFrames(StackLayer[] layers, PHPThread thread) throws DebugException {
		RemoteDebugger remoteDebugger = (RemoteDebugger) fDebugger;
		String cwd = remoteDebugger.getCurrentWorkingDirectory();
		String currentScript = null;

		IStackFrame[] frames = new IStackFrame[((layers.length - 1) * 2) + 1];
		int frameCt = ((layers.length - 1) * 2 + 1);
		for (int i = 1; i < layers.length; i++) {

			String sName = layers[i].getCallerFileName();
			String rName = remoteDebugger.convertToLocalFilename(
				sName, cwd, frameCt < frames.length ? ((PHPStackFrame) frames[frameCt]).getSourceName() : null);
			if (rName == null) {
				rName = sName;
			}
			frames[frameCt - 1] = new PHPStackFrame(
				thread, sName, layers[i].getCallerFunctionName(), layers[i].getCallerLineNumber() + 1, frameCt, rName);
			frameCt--;

			sName = layers[i].getCalledFileName();
			rName = remoteDebugger.convertToLocalFilename(sName, cwd, rName);
			if (rName == null) {
				rName = sName;
			}
			frames[frameCt - 1] = new PHPStackFrame(
				thread, sName, layers[i].getCalledFunctionName(), layers[i].getCalledLineNumber() + 1, frameCt, layers[i], rName);
			frameCt--;

			if (!layers[i].getCalledFileName().equals(fTarget.getLastFileName())) {
				currentScript = rName;
			}
		}

		String resolvedFile = remoteDebugger.convertToLocalFilename(fTarget.getLastFileName(), cwd, currentScript);
		if (resolvedFile == null) {
			resolvedFile = fTarget.getLastFileName();
		}
		frames[0] = new PHPStackFrame(thread, fTarget.getLastFileName(), (layers.length == 1) ? "" : frames[1].getName(), fTarget.getLastStop(), frameCt, resolvedFile);
		fPreviousLayers = layers;
		return frames;
	}

	private IVariable[] createVariables() {
		DefaultExpressionsManager expressionsManager = fTarget.getExpressionManager();
		if (expressionsManager == null) {
			return new IVariable[0];
		}
		Expression[] localVariables = expressionsManager.getLocalVariables(1);
		IVariable[] variables = new PHPVariable[localVariables.length];
		for (int i = 0; i < localVariables.length; i++) {
			variables[i] = new PHPVariable(fTarget, localVariables[i]);
		}

		//			variables = new PHPVariable[localVariables.length + 1];
		//			for (int i = 0; i < localVariables.length; i++) {
		//				variables[i + 1] = new PHPVariable(fTarget, localVariables[i]);
		//			}
		//			
		//			Expression[] GlobalVariables = expressionsManager.getGlobalVariables(1);
		//			String global = "$GLOBALS";
		//			DefaultExpression gExp = new DefaultExpression(global);
		//			String sArray = "Array";
		//			String sArrayAsString = sArray + " [" + (new Integer(GlobalVariables.length).toString()) + "]";
		//			ExpressionValue gEValue = new ExpressionValue(5, sArray, sArrayAsString, GlobalVariables);
		//			gExp.setValue(gEValue);
		//			variables[0] = new PHPVariable(fTarget, gExp, true);

		return variables;
	}

	public IRemoteDebugger getRemoteDebugger() {
		return fDebugger;
	}
}
