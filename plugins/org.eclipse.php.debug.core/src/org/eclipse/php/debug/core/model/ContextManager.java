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
package org.eclipse.php.debug.core.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.debug.core.Logger;
import org.eclipse.php.debug.core.debugger.*;

public class ContextManager {

    private PHPDebugTarget fTarget;
    private IRemoteDebugger fDebugger;
    private StackLayer[] fPreviousLayers;
    private IStackFrame[] fPreviousFrames = null;
    private Map fStackVariables;

    private int fSuspendCount;
    private IVariable[] fVariables;

    public ContextManager(PHPDebugTarget target, IRemoteDebugger debugger) {
        super();
        fTarget = target;
        fSuspendCount = target.getSuspendCount();
        fDebugger = debugger;
        fStackVariables = new HashMap();
    }

    public IStackFrame[] getStackFrames(int length, String context, boolean isWindows) throws DebugException {

        // check to see if eclipse is getting the same stack frames again.
        PHPstack stack = fDebugger.getCallStack();
        PHPThread thread = (PHPThread) fTarget.getThreads()[0];
        StackLayer[] layers = stack.getLayers();
        boolean main = false;
        if (layers.length == 1)
            main = true;

        if (fPreviousFrames == null) {
            fPreviousFrames = createNewFrames(layers, thread, length, context, isWindows);
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
            fPreviousFrames = createNewFrames(layers, thread, length, context, isWindows);
            fVariables = createVariables(main, false, true);
        }
        fPreviousFrames[0] = new PHPStackFrame(thread, fTarget.getLastFileName(), (main) ? "" : fPreviousFrames[1].getName(),
            fTarget.getLastStop(), 0, getLocalFileName(fTarget.getLastFileName(), context, length, isWindows));
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
            variables = (Expression[]) fStackVariables.get(key);
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
        return layer.getCallerFileName().equals(prevLayer.getCallerFileName()) && layer.getCallerFunctionName().equals(prevLayer.getCallerFunctionName())
            && layer.getCallerLineNumber() == prevLayer.getCallerLineNumber() && layer.getCalledFileName().equals(prevLayer.getCalledFileName())
            && layer.getCalledFunctionName().equals(prevLayer.getCalledFunctionName()) && layer.getCalledLineNumber() == prevLayer.getCalledLineNumber();
    }

    private IStackFrame[] createNewFrames(StackLayer[] layers, PHPThread thread, int length, String context, boolean isWindows) throws DebugException {

        IStackFrame[] frames = new IStackFrame[((layers.length - 1) * 2) + 1];
        int frameCt = ((layers.length - 1) * 2 + 1);
        for (int i = 1; i < layers.length; i++) {
            String sName = RemoteDebugger.convertToSystemIndependentFileName(layers[i].getCallerFileName());
            String rName = getLocalFileName(sName, context, length, isWindows);
            frames[frameCt - 1] = new PHPStackFrame(thread, sName, layers[i].getCallerFunctionName(), layers[i].getCallerLineNumber() + 1, frameCt, rName);
            frameCt--;
            sName = RemoteDebugger.convertToSystemIndependentFileName(layers[i].getCalledFileName());
            rName = getLocalFileName(sName, context, length, isWindows);
            frames[frameCt - 1] = new PHPStackFrame(thread, sName, layers[i].getCalledFunctionName(), layers[i].getCalledLineNumber() + 1, frameCt, layers[i], rName);
            frameCt--;
        }

        frames[0] = new PHPStackFrame(thread, fTarget.getLastFileName(), (layers.length == 1) ? "" : frames[1].getName(), 
            fTarget.getLastStop(), 0, getLocalFileName(fTarget.getLastFileName(), context, length, isWindows));
        fPreviousLayers = layers;
        return frames;
    }

    private void createStackVariables(StackLayer[] layers) {
        fStackVariables.clear();
        for (int i = 0; i < layers.length; i++) {
            Expression[] stackVariables = layers[i].getVariables();
            if (stackVariables.length != 0) {
                // TODO may need to fix for method name in classes
                String key = layers[i].getCalledFunctionName() + RemoteDebugger.convertToSystemIndependentFileName(layers[i].getCalledFileName());
                fStackVariables.put(key, stackVariables);
            }
        }
    }

    private IVariable[] createVariables(boolean main, boolean update, boolean clear) {

        DefaultExpressionsManager expressionsManager = (DefaultExpressionsManager) fTarget.getExpressionManager();
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
            ExpressionValue gEValue = new ExpressionValue(5, (Object) sArray, sArrayAsString, GlobalVariables);
            gExp.setValue(gEValue);
            variables[0] = new PHPVariable(fTarget, gExp, true);
        }
        return variables;

    }

    private String getLocalFileName(String filename, String context, int length, boolean isWindows) {
        String rName = filename.substring(length);
        if (context == null ){
            if (rName.startsWith("/")){
                rName = rName.substring(1); 
            }
            return rName;
        }
        if (isWindows){
            if ((rName.toLowerCase()).startsWith(context.toLowerCase())){
                rName = rName.substring(context.length()); 
            } else {
                rName = rName.substring(1);
            }
        } else {
            if (rName.startsWith(context)) {
                rName = rName.substring(context.length());
            } else {
                rName = rName.substring(1);
            }
        }
       
        return rName;
    }

}
