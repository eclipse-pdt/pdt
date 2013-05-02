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
package org.eclipse.php.internal.core.ast.util;

import java.io.Reader;

/**
 * A characters array reader that provides reset options.
 * 
 * @author shalom
 *
 */
public class RandomAccessCharArrayReader extends Reader {

	private char[] input;
	private int position;

	/**
	 * Constructs a new RandomAccessCharArrayReader.
	 * 
	 * @param input The characters input array.
	 */
	public RandomAccessCharArrayReader(char[] input) {
		this.input = input;
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Reader#close()
	 */
	public void close() {
		input = null;
	}

	/**
	 * Read characters into a portion of an array.
	 *
	 * @param      cbuf  Destination buffer
	 * @param      off   Offset at which to start storing characters
	 * @param      len   Maximum number of characters to read
	 *
	 * @return     The number of characters read, or -1 if the end of the
	 *             stream has been reached
	 *
	 * @exception  IllegalStateException  In case this method was called after the reader was closed.
	 */
	public int read(char[] cbuf, int off, int len) {
		if (input == null) {
			throw new IllegalStateException("The char array reader was closed."); //$NON-NLS-1$
		}
		int result = len;
		if (len + position >= input.length) {
			len = input.length - position;
			result = len;
		}
		if (result <= 0) {
			return -1;
		}
		System.arraycopy(input, position, cbuf, off, len);
		position += len;
		return result;
	}

	/**
	 * Reset the char array reader to the given position.
	 * 
	 * @param position The new position to place the reader.
	 * 
	 * @throws IllegalStateException In case that the reader was closed.
	 * @throws IllegalArgumentException In case that the given position is negative of larger then the input array.
	 */
	public void reset(int position) {
		if (input == null) {
			throw new IllegalStateException("The char array reader was closed."); //$NON-NLS-1$
		}
		if (position < 0 || position >= input.length) {
			throw new IllegalArgumentException("Illegal position (got " + position + " for a character array in the length of " + input.length); //$NON-NLS-1$ //$NON-NLS-2$
		}
		this.position = position;
	}
}
