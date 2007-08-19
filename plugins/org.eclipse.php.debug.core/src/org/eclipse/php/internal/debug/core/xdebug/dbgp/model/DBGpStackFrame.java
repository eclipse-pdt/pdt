/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpUtils;
import org.w3c.dom.Node;

public class DBGpStackFrame extends DBGpElement implements IStackFrame {

	private DBGpThread owningThread;
	private String qualifiedFile = ""; // fully qualified name of the file this stack frame is in
	private String stackLevel; // the level of this stack frame   
	private String fileName; // workspace file relative to project, null if not in workspace
	private int lineNo; // line within the file of this stack frame
	private String name = ""; // string to display in debugger for this stack frame
	private IVariable[] variables; // variables exposed to this stack frame

	public DBGpStackFrame(DBGpThread threadOwner, Node stackData) {
		super(threadOwner.getDebugTarget());
		owningThread = threadOwner;

		// parse the xml information about a stack

		/*
		    <stack level="{NUM}"
		           type="file|eval|?"
		           filename="..."
		           lineno="{NUM}"
		           where=""
		           cmdbegin="line_number:offset"
		           cmdend="line_number:offset"/>
		    <stack level="{NUM}"
		           type="file|eval|?"
		           filename="..."
		           lineno="{NUM}">
		        <input level="{NUM}"
		               type="file|eval|?"
		               filename="..."
		               lineno="{NUM}"/>
		    </stack>
		</response>       
		       */

		String line = DBGpResponse.getAttribute(stackData, "lineno");
		stackLevel = DBGpResponse.getAttribute(stackData, "level");
		lineNo = Integer.parseInt(line);
		qualifiedFile = DBGpUtils.getFilenameFromURIString(DBGpResponse.getAttribute(stackData, "filename"));
		qualifiedFile = ((DBGpTarget) getDebugTarget()).mapInboundFileIfRequired(qualifiedFile);
		// check to see if the file exists in the workspace
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(qualifiedFile));//             findFilesForLocation(new Path(fileName));
		if (file != null) {

			// file is in the workspace, need to locate it 

			//BUG 2: debugger doesn't distinguish between files of the same name in a different project
			//       however, because we are returning info relative to a project, if another project has 
			//       the same structure the lookup may go to the wrong one.
			//fileName = file.getFullPath().toString();  // to have multiple project granularity, but doesn't work with PHP IDE 
			fileName = file.getProjectRelativePath().toString();
			name = fileName + "." + DBGpResponse.getAttribute(stackData, "where") + " : lineno " + lineNo;
		} else {
			fileName = null;
			name = qualifiedFile + "." + DBGpResponse.getAttribute(stackData, "where") + " : lineno " + lineNo;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStackFrame#getCharEnd()
	 */
	public int getCharEnd() throws DebugException {
		// Don't support expression level stepping, only line level stepping
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStackFrame#getCharStart()
	 */
	public int getCharStart() throws DebugException {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStackFrame#getLineNumber()
	 */
	public int getLineNumber() throws DebugException {
		return lineNo;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStackFrame#getName()
	 */
	public String getName() throws DebugException {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStackFrame#hasRegisterGroups()
	 */
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStackFrame#getRegisterGroups()
	 */
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return new IRegisterGroup[0];
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStackFrame#getThread()
	 */
	public IThread getThread() {
		return owningThread;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStackFrame#getVariables()
	 */
	public IVariable[] getVariables() throws DebugException {
		if (variables == null) {
			variables = ((DBGpTarget) getDebugTarget()).getAllVars(stackLevel);
		}
		return variables;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStackFrame#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		getVariables();
		return (variables != null && variables.length > 0);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStep#canStepInto()
	 */
	public boolean canStepInto() {
		return owningThread.canStepInto();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStep#canStepOver()
	 */
	public boolean canStepOver() {
		return owningThread.canStepOver();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStep#canStepReturn()
	 */
	public boolean canStepReturn() {
		return owningThread.canStepReturn();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStep#isStepping()
	 */
	public boolean isStepping() {
		return owningThread.isStepping();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStep#stepInto()
	 */
	public void stepInto() throws DebugException {
		owningThread.stepInto();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStep#stepOver()
	 */
	public void stepOver() throws DebugException {
		owningThread.stepOver();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IStep#stepReturn()
	 */
	public void stepReturn() throws DebugException {
		owningThread.stepReturn();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	public boolean canResume() {
		return owningThread.canResume();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	public boolean canSuspend() {
		return owningThread.canSuspend();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public boolean isSuspended() {
		return owningThread.isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public void resume() throws DebugException {
		owningThread.resume();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public void suspend() throws DebugException {
		owningThread.suspend();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		return owningThread.canTerminate();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return owningThread.isTerminated();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		owningThread.terminate();
	}

	/**
	 * returns on the name of the file in this stackframe.
	 * @return
	 */
	public String getSourceName() {
		return fileName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof DBGpStackFrame) {
			DBGpStackFrame sf = (DBGpStackFrame) obj;
			try {

				// must check variables as a stackframe could
				// be on the same line as a previous one (eg looping
				// breakpoint, but they are essentially different
				// if the variable entries are both null, assume they are equal               
				boolean isEqual = sf.getQualifiedFile().equals(getQualifiedFile()) && sf.getLineNumber() == getLineNumber() && sf.stackLevel.equals(stackLevel) && variables == sf.variables;
				return isEqual;
			} catch (DebugException e) {
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getQualifiedFile().hashCode() + stackLevel.hashCode();
	}

	public String getQualifiedFile() {
		return qualifiedFile;
	}

	public String getStackLevel() {
		return stackLevel;
	}
}
