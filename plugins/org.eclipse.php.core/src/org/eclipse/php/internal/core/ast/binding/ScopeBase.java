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
	public final Entry symbolTable;

	public ScopeBase(final int start, ProgramScope programScope) {
		this(start, programScope, false);
	}

	public ScopeBase(final int start, ProgramScope programScope, boolean isComposite) {
		assert programScope != null;
		this.start = start;
		this.programScope = programScope;
		if (isComposite) {
			this.symbolTable = new CompositeEntry();
		} else {
			this.symbolTable = new EntryBase();
		}
	}

	/**
	 * @param identifier 
	 * @return the attribute with the specified identifier in the current scope (only) 
	 */
	public Attribute probe(String identifier) {
		assert identifier != null;

		return symbolTable.getAttribute(identifier);
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

		if (symbolTable.containsAttribute(id)) {
			throw new IllegalArgumentException();
		}
		symbolTable.setAttribute(id, attribute);
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
		toString(buffer, "");
		return buffer.toString();
	}

	/**
	 * Appends the start, length parameters to the buffer
	 */
	protected void appendInterval(StringBuffer buffer) {
		buffer.append(" start='").append(start).append("' length='").append(length).append("'");
	}

	/**
	 * Appends the symbol table to the buffer
	 */
	public void toString(StringBuffer buffer, String tab) {
		symbolTable.appendEntry(buffer, tab);
	}

	private static interface Entry {
		public abstract boolean setAttribute(String identifier, Attribute attribute);

		public abstract Attribute getAttribute(String identifier);

		public abstract boolean containsAttribute(String identifier);

		public abstract void appendEntry(StringBuffer buffer, String tab);
	}

	private static class EntryBase implements Entry {
		public String name;
		public Attribute attribute;

		public boolean setAttribute(String name, Attribute attribute) {
			this.name = name;
			this.attribute = attribute;
			return false;
		}

		public void appendEntry(StringBuffer buffer, String tab) {
			buffer.append("\n").append(TAB).append(tab).append("<Entry");
			buffer.append(" key='").append(name).append("' value='").append(attribute.toString()).append("'");
			buffer.append("/>");
		}

		public Attribute getAttribute(String identifier) {
			return name.equals(identifier) ? attribute : null;
		}

		public boolean containsAttribute(String identifier) {
			return identifier.equals(this.name);
		}
	}

	private static class CompositeEntry implements Entry {
		// small map of <String, Attribute>  entries
		public final Map entryMap = new HashMap(2);

		public CompositeEntry() {
		}

		public boolean addEntry(String identifier, Attribute attribute) {
			assert identifier != null && attribute != null;
			final Object put = entryMap.put(identifier, attribute);
			return put == null;
		}

		public Attribute getAttribute(String identifier) {
			assert identifier != null;
			return (Attribute) entryMap.get(identifier);
		}

		public boolean containsAttribute(String identifier) {
			assert identifier != null;
			return (Attribute) entryMap.get(identifier) != null;
		}

		public boolean setAttribute(String identifier, Attribute attribute) {
			assert identifier != null && attribute != null;
			final Object put = entryMap.put(identifier, attribute);
			return put == null;
		}

		public void appendEntry(StringBuffer buffer, String tab) {
			for (Iterator iter = entryMap.entrySet().iterator(); iter.hasNext();) {
				buffer.append("\n").append(TAB).append(tab).append("<Entry");
				Map.Entry entry = (Map.Entry) iter.next();
				final String name = (String) entry.getKey();
				final Attribute attribute = (Attribute) entry.getKey();
				buffer.append(" key='").append(name).append("' value='").append(attribute.toString()).append("'");
				buffer.append("/>");
			}
		}
	}
}
