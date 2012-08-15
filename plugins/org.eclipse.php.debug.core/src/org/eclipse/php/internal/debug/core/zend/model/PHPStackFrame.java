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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.PHPDebugElement;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionValue;

/**
 * PHP stack frame.
 */
public class PHPStackFrame extends PHPDebugElement implements IStackFrame {

	private static final Pattern LAMBDA_FUNC_PATTERN = Pattern
			.compile("(.*)\\((\\d+)\\) : runtime-created function"); //$NON-NLS-1$

	private PHPThread fThread;
	private String fFunctionName;
	private String fFileName;
	private String fResolvedFileName;
	private int fLineNumber;
	private int fDepth;
	private Expression[] fLocalVariables;

	/**
	 * Create new PHP stack frame
	 * 
	 * @param thread
	 *            Debug thread
	 * @param fileName
	 *            Current file name
	 * @param funcName
	 *            Current function name
	 * @param lineNumber
	 *            Current line number
	 * @param depth
	 *            Stack layer depth
	 * @param resolvedFileName
	 *            Resolved file name
	 * @param localVariables
	 *            All local function variables
	 */
	public PHPStackFrame(IThread thread, String fileName,
			String resolvedFileName, String funcName, int lineNumber,
			int depth, Expression[] localVariables) {
		super((PHPDebugTarget) thread.getDebugTarget());

		baseInit(thread, fileName, resolvedFileName, funcName, lineNumber,
				depth, localVariables);
	}

	private void baseInit(IThread thread, String fileName,
			String resolvedFileName, String funcName, int lineNumber,
			int depth, Expression[] localVariables) {

		Matcher matcher = LAMBDA_FUNC_PATTERN.matcher(fileName);
		if (matcher.matches()) {
			fileName = matcher.group(1);
			lineNumber = Integer.parseInt(matcher.group(2));
		}

		fFunctionName = funcName;
		fFileName = fileName;
		fResolvedFileName = resolvedFileName;
		fLineNumber = lineNumber;
		fDepth = depth;
		fThread = (PHPThread) thread;
		fLocalVariables = localVariables;
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
		Expression[] localVariables = ExpressionValue.sort(fLocalVariables);
		IVariable[] variables = new PHPVariable[localVariables.length];
		for (int i = 0; i < localVariables.length; i++) {
			variables[i] = new PHPVariable(
					(PHPDebugTarget) fThread.getDebugTarget(),
					localVariables[i]);
		}
		return variables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		return ((PHPDebugTarget) getDebugTarget()).getVariables(this).length > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getLineNumber()
	 */
	public int getLineNumber() throws DebugException {
		return fLineNumber;
	}

	public int checkLineNumber() throws DebugException {
		return fLineNumber;
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
		return fFunctionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getRegisterGroups()
	 */
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return new IRegisterGroup[0];
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
		return fResolvedFileName;
	}

	/**
	 * Returns the file name with full path.
	 * 
	 * @return the file name with full path
	 */
	public String getAbsoluteFileName() {
		return fFileName;
	}

	/**
	 * Returns this stack frame's unique identifier within its thread
	 * 
	 * @return this stack frame's unique identifier within its thread
	 */
	protected int getIdentifier() {
		return fDepth;
	}

	public Expression[] getStackVariables() {
		return fLocalVariables;
	}

	public void setStackVariables(Expression[] variables) {
		fLocalVariables = variables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fFunctionName == null) ? 0 : fFunctionName.hashCode());
		result = prime * result + fDepth;
		result = prime * result + fLineNumber;
		result = prime
				* result
				+ ((fResolvedFileName == null) ? 0 : fResolvedFileName
						.hashCode());
		result = prime * result + ((fThread == null) ? 0 : fThread.hashCode());
		result = prime * result
				+ ((fLocalVariables == null) ? 0 : fLocalVariables.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PHPStackFrame other = (PHPStackFrame) obj;
		if (fFunctionName == null) {
			if (other.fFunctionName != null)
				return false;
		} else if (!fFunctionName.equals(other.fFunctionName))
			return false;
		if (fDepth != other.fDepth)
			return false;
		if (fLineNumber != other.fLineNumber)
			return false;
		if (fResolvedFileName == null) {
			if (other.fResolvedFileName != null)
				return false;
		} else if (!fResolvedFileName.equals(other.fResolvedFileName))
			return false;
		if (fThread == null) {
			if (other.fThread != null)
				return false;
		} else if (!fThread.equals(other.fThread))
			return false;
		if (fLocalVariables == null) {
			if (other.fLocalVariables != null)
				return false;
		} else if (!fLocalVariables.equals(other.fLocalVariables))
			return false;
		return true;
	}
}
