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
package org.eclipse.php.internal.core.ast.binding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.php.internal.core.ast.nodes.VariableBase;

/**
 * Interface for scope information and actions
 */
public abstract class ScopeBase implements Scope {

	public final int start;
	public int length;

	public final ProgramScope programScope;

	// small map of <String, Attribute>  entries
	public final Map entryMap = new HashMap(1);

	public ScopeBase(final int start, ProgramScope programScope) {
		assert programScope != null;
		this.start = start;
		this.programScope = programScope;
	}

	/**
	 * @param identifier 
	 * @return the attribute with the specified identifier in the current scope (only) 
	 */
	public Attribute probe(String identifier) {
		assert identifier != null;

		return (Attribute) entryMap.get(identifier);
	}

	/**
	 * @param identifier 
	 * @return the attribute with the specified identifier in the current scope (only) 
	 */
	public Attribute lookup(VariableBase varBase) {
		// TODO : complete

		assert varBase != null;

		return Attribute.NULL_ATTRIBUTE;
		// return (Attribute) symbolTable.get(identifier);
	}

	/**
	 * Inserts a new binding of identifier and attribute 
	 * @param id
	 * @param valueContainer
	 */
	public void insertIdentifier(String id, Attribute attribute) {
		assert id != null && attribute != null;

		if (entryMap.containsKey(id)) {
			throw new IllegalArgumentException();
		}
		entryMap.put(id, attribute);
	}

	/**
	 * @see {@link Scope}
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @see {@link Scope}  
	 */
	public int getLength() {
		return length;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		toString(buffer, ""); //$NON-NLS-1$
		return buffer.toString();
	}

	/**
	 * Appends the start, length parameters to the buffer
	 */
	protected void appendInterval(StringBuffer buffer) {
		buffer.append(" start='").append(start).append("' length='").append(length).append("'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Appends the symbol table to the buffer
	 */
	public void toString(StringBuffer buffer, String tab) {
		for (Iterator iter = entryMap.entrySet().iterator(); iter.hasNext();) {
			buffer.append("\n").append(TAB).append(tab).append("<Entry"); //$NON-NLS-1$ //$NON-NLS-2$
			Map.Entry entry = (Map.Entry) iter.next();
			final String name = (String) entry.getKey();
			final Attribute attribute = (Attribute) entry.getValue();
			buffer.append(" key='").append(name).append("' value='").append(attribute.toString()).append("'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			buffer.append("/>"); //$NON-NLS-1$
		}
	}
}
