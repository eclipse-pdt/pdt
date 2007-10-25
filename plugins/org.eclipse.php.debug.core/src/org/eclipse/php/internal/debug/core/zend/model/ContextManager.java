/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.zend.debugger.DefaultExpression;
import org.eclipse.php.internal.debug.core.zend.debugger.DefaultExpressionsManager;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionValue;
import org.eclipse.php.internal.debug.core.zend.debugger.IRemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.PHPstack;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.StackLayer;

public class ContextManager {

	private PHPDebugTarget fTarget;
	private IRemoteDebugger fDebugger;
	private StackLayer[] fPreviousLayers;
	private IStackFrame[] fPreviousFrames = null;
	private Map<String, Expression[]> fStackVariables;

	private int fSuspendCount;
	private IVariable[] fVariables;

	public ContextManager(PHPDebugTarget target, IRemoteDebugger debugger) {
		super();
		fTarget = target;
		fSuspendCount = target.getSuspendCount();
		fDebugger = debugger;
		fStackVariables = new HashMap<String, Expression[]>();
	}

	public IStackFrame[] getStackFrames() throws DebugException {
		// check to see if eclipse is getting the same stack frames again.
		PHPstack stack = fDebugger.getCallStack();
		PHPThread thread = (PHPThread) fTarget.getThreads()[0];
		StackLayer[] layers = stack.getLayers();
		boolean main = false;
		if (layers.length == 1)
			main = true;

		if (fPreviousFrames == null) {
			fPreviousFrames = createNewFrames(layers, thread);
			fVariables = createVariables(main, false, true);
			createStackVariables(layers);
			fSuspendCount = fTarget.getSuspendCount();
			return fPreviousFrames;
		}

		if (fSuspendCount == fTarget.getSuspendCount()) {
			return fPreviousFrames;
		}

		// check to see if layers are the same as the previous thread
		fSuspendCount = fTarget.getSuspendCount();
		boolean layersSame = compareLayers(layers, fPreviousLayers);
		if (layersSame) {
			fVariables = createVariables(main, false, false);
		} else {
			fPreviousFrames = createNewFrames(layers, thread);
			fVariables = createVariables(main, false, true);
		}
		int topID = fPreviousFrames[0] instanceof PHPStackFrame ? ((PHPStackFrame) fPreviousFrames[0]).getIdentifier() : 0;
		fPreviousFrames[0] = new PHPStackFrame(thread, fTarget.getLastFileName(), (main) ? "" : fPreviousFrames[1].getName(), fTarget.getLastStop(), topID, RemoteDebugger.convertToLocalFilename(fTarget.getLastFileName(), fTarget));
		createStackVariables(layers);
		return fPreviousFrames;
	}

	public IVariable[] getVariables() {
		return fVariables;
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
			String key = functionName + stack.getAbsoluteFileName();
			variables = fStackVariables.get(key);
		}
		return variables;
	}

	private boolean compareLayers(StackLayer[] layers, StackLayer[] prevLayers) {

		if (layers.length != prevLayers.length)
			return false;

		for (int i = 1; i < layers.length; i++) {
			if (!compareLayer(layers[i], prevLayers[i]))
				return false;
		}
		return true;
	}

	private boolean compareLayer(StackLayer layer, StackLayer prevLayer) {
		return layer.getCallerFileName().equals(prevLayer.getCallerFileName()) && layer.getCallerFunctionName().equals(prevLayer.getCallerFunctionName()) && layer.getCallerLineNumber() == prevLayer.getCallerLineNumber() && layer.getCalledFileName().equals(prevLayer.getCalledFileName())
			&& layer.getCalledFunctionName().equals(prevLayer.getCalledFunctionName()) && layer.getCalledLineNumber() == prevLayer.getCalledLineNumber();
	}

	private IStackFrame[] createNewFrames(StackLayer[] layers, PHPThread thread) throws DebugException {
		IStackFrame[] frames = new IStackFrame[((layers.length - 1) * 2) + 1];
		int frameCt = ((layers.length - 1) * 2 + 1);
		for (int i = 1; i < layers.length; i++) {
			String sName = RemoteDebugger.convertToSystemIndependentFileName(layers[i].getCallerFileName());
			String rName = RemoteDebugger.convertToLocalFilename(sName, fTarget);
			frames[frameCt - 1] = new PHPStackFrame(thread, sName, layers[i].getCallerFunctionName(), layers[i].getCallerLineNumber() + 1, frameCt, rName);
			frameCt--;
			sName = RemoteDebugger.convertToSystemIndependentFileName(layers[i].getCalledFileName());
			rName = RemoteDebugger.convertToLocalFilename(sName, fTarget);
			frames[frameCt - 1] = new PHPStackFrame(thread, sName, layers[i].getCalledFunctionName(), layers[i].getCalledLineNumber() + 1, frameCt, layers[i], rName);
			frameCt--;
		}

		frames[0] = new PHPStackFrame(thread, fTarget.getLastFileName(), (layers.length == 1) ? "" : frames[1].getName(), fTarget.getLastStop(), frameCt, RemoteDebugger.convertToLocalFilename(fTarget.getLastFileName(), fTarget));
		fPreviousLayers = layers;
		return frames;
	}

	private void createStackVariables(StackLayer[] layers) {
		fStackVariables.clear();
		for (StackLayer element : layers) {
			Expression[] stackVariables = element.getVariables();
			if (stackVariables.length != 0) {
				// TODO may need to fix for method name in classes
				String key = element.getCalledFunctionName() + RemoteDebugger.convertToSystemIndependentFileName(element.getCalledFileName());
				fStackVariables.put(key, stackVariables);
			}
		}
	}

	private IVariable[] createVariables(boolean main, boolean update, boolean clear) {

		DefaultExpressionsManager expressionsManager = fTarget.getExpressionManager();
		if (clear)
			expressionsManager.clear();
		Expression[] localVariables = expressionsManager.getLocalVariables(1);
		Expression[] GlobalVariables = expressionsManager.getGlobalVariables(1);
		IVariable[] variables;
		if (main) {
			variables = new PHPVariable[localVariables.length];
			for (int i = 0; i < localVariables.length; i++) {
				variables[i] = new PHPVariable(fTarget, localVariables[i]);
			}
		} else {
			variables = new PHPVariable[localVariables.length + 1];
			for (int i = 0; i < localVariables.length; i++) {
				variables[i + 1] = new PHPVariable(fTarget, localVariables[i]);
			}
			String global = "$GLOBALS";
			DefaultExpression gExp = new DefaultExpression(global);
			String sArray = "Array";
			String sArrayAsString = sArray + " [" + (new Integer(GlobalVariables.length).toString()) + "]";
			ExpressionValue gEValue = new ExpressionValue(5, sArray, sArrayAsString, GlobalVariables);
			gExp.setValue(gEValue);
			variables[0] = new PHPVariable(fTarget, gExp, true);
		}
		return variables;
	}

	public IRemoteDebugger getRemoteDebugger() {
		return fDebugger;
	}
}
