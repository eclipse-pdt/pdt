/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.scope;

import org.eclipse.php.core.codeassist.ICompletionScope;

/**
 * Provisoinal API
 */
public class CompletionScope implements ICompletionScope {

	private final Type type;

	private final ICompletionScope parent;

	private final int offset;
	private final int length;

	private final String name;

	public CompletionScope(Type type, int offset, int length, ICompletionScope parent) {
		this.type = type;
		this.parent = parent;
		this.offset = offset;
		this.length = length;
		this.name = null;
	}

	public CompletionScope(Type type, String name, int offset, int length, ICompletionScope parent) {
		this.type = type;
		this.parent = parent;
		this.offset = offset;
		this.length = length;
		this.name = name;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public ICompletionScope getParent() {
		return parent;
	}

	@Override
	public ICompletionScope findParent(Type... types) {
		ICompletionScope test = this;
		SEARCH: do {
			for (Type type : types) {
				if (test.getType() == type) {
					break SEARCH;
				}
			}
			test = test.getParent();
		} while (test != null);

		return test;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(type.name());
		sb.append('(').append(offset).append(',').append(length);
		if (name != null) {
			sb.append(',').append(name);
		}
		return sb.append(')').toString();
	}
}
