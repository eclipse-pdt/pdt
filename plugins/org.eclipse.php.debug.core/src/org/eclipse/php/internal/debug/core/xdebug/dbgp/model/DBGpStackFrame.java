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
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.VariablesUtil;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpUtils;
import org.w3c.dom.Node;

public class DBGpStackFrame extends DBGpElement implements IStackFrame {

	protected final class DBGpVariablesContainer {

		private Map<String, IVariable> currentVariables;
		private Map<String, IVariable> previousVariables;
		private IVariable[] variables;
		private boolean shouldUpdate = true;

		IVariable[] getVariables() {
			if (shouldUpdate) {
				updateVariables();
				variables = currentVariables.values().toArray(new IVariable[currentVariables.size()]);
				shouldUpdate = false;
			}
			return variables;
		}

		void markToUpdate() {
			shouldUpdate = true;
		}

		/**
		 * Merges incoming variable. Merge is done by means of checking if related child
		 * variable existed in "one step back" state of a frame. If related variable
		 * existed, it is updated with the use of the most recent descriptor and
		 * returned instead of the incoming one.
		 * 
		 * @param variable
		 * @param descriptor
		 * @return merged variable
		 */
		private IVariable merge(IVariable variable) {
			if (previousVariables == null) {
				return variable;
			}
			if (!(variable instanceof DBGpVariable)) {
				return variable;
			}
			DBGpVariable incoming = (DBGpVariable) variable;
			if (incoming.getFullName().isEmpty()) {
				return incoming;
			}
			IVariable stored = previousVariables.get(incoming.getFullName());
			if (stored != null) {
				((DBGpVariable) stored).update(incoming.getDescriptor());
				return stored;
			}
			return variable;
		}

		private void updateVariables() {
			previousVariables = currentVariables;
			currentVariables = new LinkedHashMap<>();
			// fetch new set of variables
			IVariable[] incoming = ((DBGpTarget) getDebugTarget()).getVariables(stackLevel);
			VariablesUtil.sortContextMembers(incoming);
			for (int i = 0; i < incoming.length; i++) {
				DBGpVariable variable = ((DBGpVariable) incoming[i]);
				currentVariables.put(variable.getFullName(), merge(variable));
			}
		}

	}

	private DBGpThread owningThread;
	private String qualifiedFile = ""; // fully qualified name of //$NON-NLS-1$
										// the file this stack frame is in
	private String stackLevel; // the level of this stack frame
	private String fileName; // workspace file relative to project, null if not
								// in workspace
	private int lineNo; // line within the file of this stack frame
	private String name = ""; // string to display in debugger for //$NON-NLS-1$
								// this stack frame
	private Node descriptor;
	private DBGpVariablesContainer variablesContainer;

	// private IVariable[] variables; // variables exposed to this stack frame

	public DBGpStackFrame(DBGpThread threadOwner, Node stackData) {
		super(threadOwner.getDebugTarget());
		owningThread = threadOwner;
		variablesContainer = new DBGpVariablesContainer();
		update(stackData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getCharEnd()
	 */
	@Override
	public int getCharEnd() throws DebugException {
		// Don't support expression level stepping, only line level stepping
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getCharStart()
	 */
	@Override
	public int getCharStart() throws DebugException {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getLineNumber()
	 */
	@Override
	public int getLineNumber() throws DebugException {
		DBGpLogger.debug(this.hashCode() + "::DBGpStackFrame=" + lineNo); //$NON-NLS-1$
		return lineNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getName()
	 */
	@Override
	public String getName() throws DebugException {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#hasRegisterGroups()
	 */
	@Override
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getRegisterGroups()
	 */
	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return new IRegisterGroup[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getThread()
	 */
	@Override
	public IThread getThread() {
		return owningThread;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getVariables()
	 */
	@Override
	public synchronized IVariable[] getVariables() throws DebugException {
		DBGpLogger.debug("getting variables for stackframe on line: " + lineNo); //$NON-NLS-1$
		return variablesContainer.getVariables();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#hasVariables()
	 */
	@Override
	public boolean hasVariables() throws DebugException {
		return (getVariables() != null && getVariables().length > 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepInto()
	 */
	@Override
	public boolean canStepInto() {
		return owningThread.canStepInto();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepOver()
	 */
	@Override
	public boolean canStepOver() {
		return owningThread.canStepOver();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepReturn()
	 */
	@Override
	public boolean canStepReturn() {
		return owningThread.canStepReturn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#isStepping()
	 */
	@Override
	public boolean isStepping() {
		return owningThread.isStepping();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepInto()
	 */
	@Override
	public void stepInto() throws DebugException {
		owningThread.stepInto();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepOver()
	 */
	@Override
	public void stepOver() throws DebugException {
		owningThread.stepOver();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepReturn()
	 */
	@Override
	public void stepReturn() throws DebugException {
		owningThread.stepReturn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	@Override
	public boolean canResume() {
		return owningThread.canResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	@Override
	public boolean canSuspend() {
		return owningThread.canSuspend();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	@Override
	public boolean isSuspended() {
		return owningThread.isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	@Override
	public void resume() throws DebugException {
		owningThread.resume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	@Override
	public void suspend() throws DebugException {
		owningThread.suspend();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	@Override
	public boolean canTerminate() {
		return owningThread.canTerminate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	@Override
	public boolean isTerminated() {
		return owningThread.isTerminated();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	@Override
	public void terminate() throws DebugException {
		owningThread.terminate();
	}

	/**
	 * returns on the name of the file in this stackframe.
	 * 
	 * @return
	 */
	public String getSourceName() {
		return fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DBGpStackFrame) {
			DBGpStackFrame sf = (DBGpStackFrame) obj;
			try {
				// a stack frame is equal if they are at the same level, same
				// line and for
				// the same file
				//
				// if a stack frame is equal then eclipse doesn't refresh the
				// variables pane
				// but subsequent new stackframes created at the same level (but
				// on different
				// line numbers are not used to get the variables, the first one
				// at the level
				// is used (eg a stackframe for line 2 is used to get variables
				// for all other
				// lines at the same stack level, even though a stack level for
				// one at say line
				// 4 exists).
				//
				// so to stop the refresh of the variables pane, stack frames at
				// the same
				// level should report as equal, but because of this a stack
				// frame cannot
				// cache the variables at that line as eclipse goes to the stack
				// frame of
				// another line number to get the stack variables.
				boolean isEqual = sf.getQualifiedFile().equals(getQualifiedFile()) && sf.stackLevel.equals(stackLevel)
						&& (sf.owningThread == owningThread) && sf.getLineNumber() == getLineNumber();
				return isEqual;
			} catch (Exception e) {
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getQualifiedFile().hashCode() + stackLevel.hashCode() + owningThread.hashCode();
	}

	public String getQualifiedFile() {
		return qualifiedFile;
	}

	public String getStackLevel() {
		return stackLevel;
	}

	protected Node getDescriptor() {
		return descriptor;
	}

	protected void update(Node stackData) {
		// Reset state
		variablesContainer.markToUpdate();
		// Set up new descriptor
		descriptor = stackData;
		String line = DBGpResponse.getAttribute(descriptor, "lineno"); //$NON-NLS-1$
		stackLevel = DBGpResponse.getAttribute(descriptor, "level"); //$NON-NLS-1$
		lineNo = Integer.parseInt(line);
		qualifiedFile = DBGpUtils.getFilenameFromURIString(DBGpResponse.getAttribute(descriptor, "filename")); //$NON-NLS-1$
		qualifiedFile = ((DBGpTarget) getDebugTarget()).mapToWorkspaceFileIfRequired(qualifiedFile);
		String function = DBGpResponse.getAttribute(descriptor, "where"); //$NON-NLS-1$
		// Check to see if the file exists in the workspace.
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=511255
		// DBGpResponse.getAttribute(descriptor, "filename") can return
		// filenames like "dbgp://1" for PHP classes generated by eval(),
		// qualifiedFile will then be "" and fileFound will be an object of
		// type WorkspaceRoot (that doesn't inherit the IFile interface).
		IResource fileFound = ResourcesPlugin.getWorkspace().getRoot().findMember(qualifiedFile);
		if (fileFound instanceof IFile) {
			IFile file = (IFile) fileFound;
			// get the file found in workspace and show project/file
			String projectName = file.getProject().getName();
			String projectRelPath = file.getProjectRelativePath().toString();
			fileName = projectName + "/" + projectRelPath; //$NON-NLS-1$
		} else {
			// fileName = "";
			fileName = qualifiedFile;
		}
		name = fileName + "." + function + "()"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
