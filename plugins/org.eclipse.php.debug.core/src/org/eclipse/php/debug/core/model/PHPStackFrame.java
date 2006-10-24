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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.debug.core.debugger.Expression;
import org.eclipse.php.debug.core.debugger.StackLayer;
import org.eclipse.php.debug.core.sourcelookup.PHPSourceSearchEngine;

/**
 * PHP stack frame.
 */
public class PHPStackFrame extends PHPDebugElement implements IStackFrame {

    private PHPThread fThread;

    private String fName;

    private String fFileName;

    private int fPC;

    private int fId;

    private String fResName;

    public PHPStackFrame(IThread thread, String fileName, String funcName, int lineNumber, int id, String rName) {
        super((PHPDebugTarget) thread.getDebugTarget());
        baseInit(thread, fileName, funcName, lineNumber, id, rName);
    }

    private void baseInit(IThread thread, String fileName, String funcName, int lineNumber, int id, String rName) {
        fName = funcName;
        fFileName = fileName;
        fPC = lineNumber;
        fId = id;
        fThread = (PHPThread) thread;
        PHPDebugTarget debugTarget = (PHPDebugTarget)fThread.getDebugTarget();
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(debugTarget.getProjectName());
        IFile file = PHPSourceSearchEngine.getResource(rName, project);
        fResName = file.getProjectRelativePath().toString();
    }

    public PHPStackFrame(IThread thread, String fileName, String funcName, int lineNumber, int id, StackLayer layer, String rName) {
        super((PHPDebugTarget) thread.getDebugTarget());
        baseInit(thread, fileName, funcName, lineNumber, id, rName);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStackFrame#getThread()
     */
    public IThread getThread() {
        return fThread;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStackFrame#getVariables()
     */
    public IVariable[] getVariables() throws DebugException {
        return ((PHPDebugTarget) getDebugTarget()).getVariables();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStackFrame#hasVariables()
     */
    public boolean hasVariables() throws DebugException {
        return ((PHPDebugTarget) getDebugTarget()).getVariables().length > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStackFrame#getLineNumber()
     */
    public int getLineNumber() throws DebugException {
        return fPC;
    }

    public int checkLineNumber() throws DebugException {
        return fPC;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStackFrame#getCharStart()
     */
    public int getCharStart() throws DebugException {
        return -1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStackFrame#getCharEnd()
     */
    public int getCharEnd() throws DebugException {
        return -1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStackFrame#getName()
     */
    public String getName() throws DebugException {
        return fName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStackFrame#getRegisterGroups()
     */
    public IRegisterGroup[] getRegisterGroups() throws DebugException {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStackFrame#hasRegisterGroups()
     */
    public boolean hasRegisterGroups() throws DebugException {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStep#canStepInto()
     */
    public boolean canStepInto() {
        return getThread().canStepInto();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStep#canStepOver()
     */
    public boolean canStepOver() {
        return getThread().canStepOver();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStep#canStepReturn()
     */
    public boolean canStepReturn() {
        return getThread().canStepReturn();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStep#isStepping()
     */
    public boolean isStepping() {
        return getThread().isStepping();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStep#stepInto()
     */
    public void stepInto() throws DebugException {
        getThread().stepInto();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStep#stepOver()
     */
    public void stepOver() throws DebugException {
        getThread().stepOver();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IStep#stepReturn()
     */
    public void stepReturn() throws DebugException {
        getThread().stepReturn();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
     */
    public boolean canResume() {
        return getThread().canResume();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
     */
    public boolean canSuspend() {
        return getThread().canSuspend();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
     */
    public boolean isSuspended() {
        return getThread().isSuspended();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.ISuspendResume#resume()
     */
    public void resume() throws DebugException {
        getThread().resume();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
     */
    public void suspend() throws DebugException {
        getThread().suspend();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
     */
    public boolean canTerminate() {
        return getThread().canTerminate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
     */
    public boolean isTerminated() {
        return getThread().isTerminated();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.ITerminate#terminate()
     */
    public void terminate() throws DebugException {
        getThread().terminate();
    }

    /**
     * Returns the name of the source file this stack frame is associated with.
     * 
     * @return the name of the source file this stack frame is associated with
     */
    public String getSourceName() {
        return fResName;
    }

    /**
     * Returns the file name with full path.
     * 
     * @return the file name with full path
     */
    public String getAbsoluteFileName() {
        return fFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
    	if (obj == this) {
    		return true;
    	}
        if (obj instanceof PHPStackFrame) {
            PHPStackFrame sf = (PHPStackFrame) obj;
            try {
                return sf.fId == fId && sf.checkSourceName().equals(checkSourceName()) && sf.getName().equals(getName());
            } catch (DebugException e) {
            }
        }
        return false;
    }

    private String checkSourceName() {
        return fResName;
 
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getSourceName().hashCode() + fId;
    }

    /**
     * Returns this stack frame's unique identifier within its thread
     * 
     * @return this stack frame's unique identifier within its thread
     */
    protected int getIdentifier() {
        return fId;
    }

    /**
     * Returns this frame's PHP stack variables 
     * 
     * @return this frame's PHP stack variables 
     */
    public Expression[] getStackVariables() {
        return ((PHPDebugTarget) getDebugTarget()).getStackVariables(this);
    }
}
