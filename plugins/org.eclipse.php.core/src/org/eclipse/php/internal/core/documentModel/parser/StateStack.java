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
/*
 * StateStack.java
 *
 * Created on May 2, 2000, 12:36 PM
 */

package org.eclipse.php.internal.core.documentModel.parser;

/**
 * @author erez
 * @version A last in first out (LIFO) stack of integers that contains states
 *          pushed by the lexer.
 */
public class StateStack implements Cloneable {

	private byte[] stack;
	private int lastIn = -1;

	/**
	 * Creates new StateStack
	 */
	public StateStack() {
		this(5);
	}

	public StateStack(int stackSize) {
		stack = new byte[stackSize];
		lastIn = -1;
	}

	public boolean isEmpty() {
		return lastIn == -1;
	}

	public int popStack() {
		int result = stack[lastIn];
		lastIn--;
		return result;
	}

	public void pushStack(int state) {
		lastIn++;
		if (lastIn == stack.length)
			multiplySize();
		stack[lastIn] = (byte) state;
	}

	private void multiplySize() {
		int length = stack.length;
		byte[] temp = new byte[length * 2];
		System.arraycopy(stack, 0, temp, 0, length);
		stack = temp;
	}

	public int clear() {
		return lastIn = -1;
	}

	public int size() {
		return lastIn + 1;
	}

	public StateStack createClone() {
		StateStack rv = new StateStack(this.size());
		rv.copyFrom(this);
		return rv;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || !(obj instanceof StateStack)) {
			return false;
		}

		StateStack s2 = (StateStack) obj;
		if (this.lastIn != s2.lastIn) {
			return false;
		}

		for (int i = lastIn; i >= 0; i--) {
			if (this.stack[i] != s2.stack[i]) {
				return false;
			}
		}

		return true;
	}

	public void copyFrom(StateStack s) {
		while (s.lastIn >= this.stack.length) {
			this.multiplySize();
		}
		this.lastIn = s.lastIn;
		for (int i = 0; i <= s.lastIn; i++) {
			this.stack[i] = s.stack[i];
		}
	}

	public boolean contains(int state) {
		for (int i = 0; i <= lastIn; i++) {
			if (stack[i] == state) {
				return true;
			}
		}
		return false;
	}

	public int get(int index) {
		return stack[index];
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(50);
		for (int i = 0; i <= lastIn; i++) {
			sb.append(" stack[" + i + "]= " + stack[i]); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return sb.toString();
	}

}