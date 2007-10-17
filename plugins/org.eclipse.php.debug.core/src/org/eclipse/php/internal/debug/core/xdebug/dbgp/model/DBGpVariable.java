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

//import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.w3c.dom.Node;

public class DBGpVariable extends DBGpBaseVariable implements IVariable {

	static final String PHP_BOOL = "bool";
	static final String PHP_INT = "int";
	static final String PHP_FLOAT = "float";
	static final String PHP_STRING = "string"; //size attribute
	static final String PHP_NULL = "null"; // unknown variable type
	static final String PHP_ARRAY = "array"; // children, numchildren, page, pagesize, recursive attribute
	static final String PHP_OBJECT = "object"; //children, classname, numchildren, page, pagesize, recursive attribute
	static final String PHP_RESOURCE = "resource"; // pre-rendered string for information, cannot be changed
	static final String PHP_UNINIT = "uninitialized";

	private DBGpValue value;
	private String name;
	private String address;
	private String type;

	public DBGpVariable(DBGpTarget target, Node property, String level) {
		super(target, level);
		parseProperty(property);
	}

	private void parseProperty(Node property) {

		// we could have a property which has no name, fullname or type
		// as a result of an expression evaluation
		name = DBGpResponse.getAttribute(property, "name");
		setFullName(DBGpResponse.getAttribute(property, "fullname"));

		// hopefully this will put the $ at appropriate point in the variable name
		if (getFullName().length() > 1 && name.equals(getFullName().substring(1))) {
			name = getFullName();
		}

		address = DBGpResponse.getAttribute(property, "address");
		type = DBGpResponse.getAttribute(property, "type");

		if (type.equals(PHP_BOOL)) {
			value = new DBGpBoolValue(this, property);
		} else if (type.equals(PHP_INT)) {
			value = new DBGpNumValue(this, property, PHP_INT);
		} else if (type.equals(PHP_FLOAT)) {
			value = new DBGpNumValue(this, property, PHP_FLOAT);
		} else if (type.equals(PHP_STRING)) {
			// TODO: strings support a size attribute which could be used in future
			value = new DBGpStringValue(this, property);
		} else if (type.equals(PHP_RESOURCE)) {
			value = new DBGpResourceValue(this, property);
		} else if (type.equals(PHP_NULL)) {
			value = new DBGpNullValue(this, property);
		} else if (type.equals(PHP_UNINIT)) {
			value = new DBGpUnInitValue(this);
		} else if (type.equals(PHP_ARRAY) || type.equals(PHP_OBJECT)) {
			value = new DBGpContainerValue(this, property);
		} else {
			// The default which is an uninitialised variable
			value = new DBGpUnInitValue(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IVariable#getName()
	 */
	public String getName() throws DebugException {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IVariable#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IVariable#getValue()
	 */
	public IValue getValue() throws DebugException {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IVariable#hasValueChanged()
	 */
	public boolean hasValueChanged() throws DebugException {
		// this is a GUI facility to show if a variable has changed since the 
		// last suspend. If you always return false, it doesn't highlight in the variables
		// view. A future facility.
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(java.lang.String)
	 */
	public void setValue(String expression) throws DebugException {
		// assume never called unless supportsValueModification is true
		// also assume that will only be called if been verified
		// BUG in eclipse 3.2: Cell modification doesn't call verify Value and it should. 
		// It does if you use the editor pane.
		if (!verifyValue(expression)) {
			Status stat = new Status(Status.WARNING, PHPDebugPlugin.ID, "setValue called, but verifyValue failed");
			throw new DebugException(stat);
			//DebugUIPlugin.errorDialog(Display.getDefault().getActiveShell(), ActionMessages.AssignValueAction_2, MessageFormat.format(ActionMessages.AssignValueAction_3, new String[] {expression, name}), new StatusInfo(IStatus.ERROR, ActionMessages.AssignValueAction_4));  //           
		} else {
			// attempt to set the property
			if (((DBGpTarget) getDebugTarget()).setProperty(this, expression)) {
				value.setValue(expression);
				fireEvent(new DebugEvent(this, DebugEvent.CHANGE, DebugEvent.CONTENT));
			} else {
				throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.ID, DebugException.TARGET_REQUEST_FAILED, "program under debug rejected value change", null));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(org.eclipse.debug.core.model.IValue)
	 */
	public void setValue(IValue xvalue) throws DebugException {
		// assume never called unless supportsValueModification is true
		setValue(xvalue.getValueString());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#supportsValueModification()
	 */
	public boolean supportsValueModification() {
		return value.isModifiable() && getFullName() != null && getFullName().trim().length() != 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#verifyValue(java.lang.String)
	 */
	public boolean verifyValue(String expression) throws DebugException {
		return value.verifyValue(expression);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IValueModification#verifyValue(org.eclipse.debug.core.model.IValue)
	 */
	public boolean verifyValue(IValue xvalue) throws DebugException {
		return verifyValue(xvalue.getValueString());
	}

	public String getAddress() {
		return address;
	}

}
