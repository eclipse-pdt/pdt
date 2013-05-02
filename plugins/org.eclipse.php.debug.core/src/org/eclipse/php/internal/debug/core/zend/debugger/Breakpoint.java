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
 * Breakpoint.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author guy
 */
public class Breakpoint implements Cloneable {

	public static final String NAME_CHANGED_PROPERTY = "fileName"; //$NON-NLS-1$
	public static final String LINE_CHANGED_PROPERTY = "lineNumber"; //$NON-NLS-1$
	public static final String ID_CHANGED_PROPERTY = "id"; //$NON-NLS-1$
	public static final String TYPE_CHANGED_PROPERTY = "type"; //$NON-NLS-1$
	public static final String LIFETIME_CHANGED_PROPERTY = "lifetime"; //$NON-NLS-1$
	public static final String EXPRESSION_CHANGED_PROPERTY = "expression"; //$NON-NLS-1$
	public static final String ENABLE_CHANGED_PROPERTY = "enable"; //$NON-NLS-1$

	static final long serialVersionUID = 8217568055748309793L;

	public static final int DEFAULT_ID = -1;

	// break point type
	public static final int ZEND_CONDITIONAL_BREAKPOINT = 2;
	public static final String CONDITIONAL_STRING = "Conditional"; //$NON-NLS-1$
	public static final int ZEND_STATIC_BREAKPOINT = 1;
	public static final String STATIC_STRING = "Static"; //$NON-NLS-1$
	// break point lifetime
	public static final int ZEND_ONETIME_BREAKPOINT = 1;
	public static final String ONETIME_STRING = "One time"; //$NON-NLS-1$
	public static final int ZEND_PERMANENT_BREAKPOINT = 2;
	public static final String PERMANENT_STRING = "Permanent"; //$NON-NLS-1$

	private int id;
	private int type;
	private int lifetime;
	private String expression = ""; //$NON-NLS-1$
	private boolean enable;
	private boolean conditionalFlag = false;
	private boolean staticFlag = true;

	protected String fileName = ""; //$NON-NLS-1$
	protected int lineNumber = -1;
	transient protected Collection listeners;

	/**
	 * Creates new Breakpoint
	 */
	public Breakpoint() {
		this("", DEFAULT_ID); //$NON-NLS-1$
	}

	public Breakpoint(String fileName, int lineNumber) {
		super();
		setFileName(fileName);
		setLineNumber(lineNumber);
		id = -1;
		setStaticFlag(true);
		lifetime = ZEND_PERMANENT_BREAKPOINT;
		enable = true;
	}

	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the Break Point id.
	 */
	public void setID(int id) {
		if (this.id != id) {
			int oldValue = this.id;
			this.id = id;
			fireBreakpointChanged(this, ID_CHANGED_PROPERTY, new Integer(
					oldValue), new Integer(id));
		}
	}

	/**
	 * Returns the Break Point id.
	 */
	public int getID() {
		return id;
	}

	public void setFileName(String newFileName) {
		// ensure we can never set the filename to null
		// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=312951
		if (newFileName == null) {
			newFileName = ""; //$NON-NLS-1$
		}

		if (fileName.equals(newFileName)) {
			return;
		}
		String oldFileName = fileName;
		this.fileName = newFileName;
		fireBreakpointChanged(this, NAME_CHANGED_PROPERTY, oldFileName,
				newFileName);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int newLineNumber) {
		if (lineNumber == newLineNumber) {
			return;
		}
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		fireBreakpointChanged(this, LINE_CHANGED_PROPERTY, new Integer(
				oldLineNumber), new Integer(newLineNumber));
	}

	/**
	 * Sets the break point type. The type can be : ZEND_CONDITIONAL_BREAKPOINT
	 * or ZEND_STATIC_BREAKPOINT.
	 */
	public void setConditionalFlag(boolean b) {
		this.conditionalFlag = b;
	}

	public void setStaticFlag(boolean b) {
		this.staticFlag = b;
	}

	public boolean getStaticFlag() {
		return staticFlag;
	}

	public boolean getConditionalFlag() {
		return conditionalFlag;
	}

	public void setType(int type) {
		if (type < 4) { // maximum value
			if (this.type != type) {
				int oldValue = this.type;
				this.type = type;
				conditionalFlag = (type >= 2) ? true : false;
				staticFlag = (type == 1 || type == 3) ? true : false;
				fireBreakpointChanged(this, TYPE_CHANGED_PROPERTY, new Integer(
						oldValue), new Integer(id));
			}
		}
	}

	/**
	 * Returns the break point type.. make the type variable a combination of
	 * two values
	 */
	public int getType() {
		int s = staticFlag ? ZEND_STATIC_BREAKPOINT : 0;
		int c = conditionalFlag ? ZEND_CONDITIONAL_BREAKPOINT : 0;
		type = s + c;
		return type;
	}

	/**
	 * Sets the break point lifetime. The lifetime can be:
	 * ZEND_ONETIME_BREAKPOINT or ZEND_PERMANENT_BREAKPOINT .
	 */
	public void setLifeTime(int lifetime) {
		if (lifetime == ZEND_ONETIME_BREAKPOINT
				|| lifetime == ZEND_PERMANENT_BREAKPOINT) {
			if (this.lifetime != lifetime) {
				int oldValue = lifetime;
				this.lifetime = lifetime;
				fireBreakpointChanged(this, LIFETIME_CHANGED_PROPERTY,
						new Integer(oldValue), new Integer(id));
			}
		}
	}

	/**
	 * Returns the break point lifetime.
	 */
	public int getLifeTime() {
		return lifetime;
	}

	/**
	 * Sets the expression of the ZEND_CONDITIONAL_BREAKPOINT .
	 */
	public void setExpression(String expression) {
		if (!this.expression.equals(expression)) {
			String oldValue = this.expression;
			this.expression = expression;
			if (!expression.equals("")) { //$NON-NLS-1$
				setConditionalFlag(true);
			} else {
				setConditionalFlag(false);
			}
			fireBreakpointChanged(this, EXPRESSION_CHANGED_PROPERTY, oldValue,
					expression);
		}
	}

	/**
	 * Returns the expression of the ZEND_CONDITIONAL_BREAKPOINT .
	 */
	public String getExpression() {
		return expression;
	}

	public void setEnable(boolean enable) {
		if (this.enable != enable) {
			boolean oldValue = this.enable;
			this.enable = enable;
			fireBreakpointChanged(this, ENABLE_CHANGED_PROPERTY, new Boolean(
					oldValue), new Boolean(enable));
		}
	}

	public boolean isEnable() {
		return enable;
	}

	public String toString() {
		return "id: " + id + " type: " + type + "  file name: " + getFileName() //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ " line: " + getLineNumber(); //$NON-NLS-1$
	}

	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (obj == null || !(obj instanceof Breakpoint)) {
			return false;
		}
		Breakpoint other = (Breakpoint) obj;
		return this.id == other.id && this.type == other.type
				&& this.lifetime == other.lifetime
				&& fileName.equals(other.getFileName())
				&& lineNumber == other.getLineNumber();
	}

	protected void fireBreakpointChanged(Breakpoint breakpoint,
			String property, Object oldValue, Object newValue) {
		if (listeners != null) {
			Iterator i = listeners.iterator();
			while (i.hasNext()) {
				BreakpointListener curr = (BreakpointListener) i.next();
				curr.breakpointChanged(breakpoint, property, oldValue, newValue);
			}
		}
	}

	public int compareTo(Object o) {
		if (!(o instanceof Breakpoint)) {
			return -1;
		}
		Breakpoint other = (Breakpoint) o;
		int rv = fileName.compareTo(other.getFileName());
		if (rv == 0) {
			rv = lineNumber - other.getLineNumber();
		}
		return rv;
	}

	/**
	 * Creates a copy of this Breakpoint.
	 * 
	 * @return a copy of this Breakpoint.
	 */
	public Object clone() {
		try {
			Breakpoint ble = (Breakpoint) super.clone();
			ble.listeners = null;
			return ble;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	public interface BreakpointListener {

		public void breakpointChanged(Breakpoint breakpoint, String property,
				Object oldValue, Object newValue);

	}

}