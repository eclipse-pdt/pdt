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
package org.eclipse.php.internal.core.mixin;

import org.eclipse.dltk.core.IField;

public class PHPMixinElementInfo {

	public static final int K_VARIABLE = 1 << 1;
	public static final int K_CONSTANT = 1 << 2;
	public static final int K_INCLUDE = 1 << 3;
	public static final int K_NON_PHP = 1 << 5;

	private int kind = 0;
	private Object object = null;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + kind;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PHPMixinElementInfo other = (PHPMixinElementInfo) obj;
		if (kind != other.kind) {
			return false;
		}
		if (object == null) {
			if (other.object != null) {
				return false;
			}
		} else if (!object.equals(other.object)) {
			return false;
		}
		return true;
	}

	public PHPMixinElementInfo(int kind, Object object) {
		super();
		this.kind = kind;
		this.object = object;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public static PHPMixinElementInfo createInclude(IField includeField) {
		return new PHPMixinElementInfo(K_INCLUDE, includeField);
	}

	public static PHPMixinElementInfo createVariable(IField type) {
		return new PHPMixinElementInfo(K_VARIABLE, type);
	}

	public static PHPMixinElementInfo createConstant(IField type) {
		return new PHPMixinElementInfo(K_CONSTANT, type);
	}
}
