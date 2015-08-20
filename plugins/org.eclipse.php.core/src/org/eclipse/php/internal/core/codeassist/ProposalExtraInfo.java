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
package org.eclipse.php.internal.core.codeassist;

public final class ProposalExtraInfo {
	public static final int DEFAULT = 1;
	public static final int TYPE_ONLY = 1 << 1;
	public static final int MAGIC_METHOD = 1 << 2;
	// this is for namespace
	public static final int FULL_NAME = 1 << 3;
	// the stub is a type of class/method/field,which does not exist yet
	public static final int STUB = 1 << 4;
	public static final int METHOD_ONLY = 1 << 5;
	public static final int NO_INSERT_NAMESPACE = 1 << 6;
	public static final int CLASS_IN_NAMESPACE = 1 << 7;
	public static final int ADD_QUOTES = 1 << 8;
	public static final int NO_INSERT_USE = 1 << 9;

	public static boolean isTypeOnly(int flags) {
		return (flags & TYPE_ONLY) != 0;
	}

	public static boolean isMagicMethod(int flags) {
		return (flags & MAGIC_METHOD) != 0;
	}

	public static boolean isFullName(int flags) {
		return (flags & FULL_NAME) != 0;
	}

	public static boolean isTypeOnly(Object flags) {
		if (flags instanceof Integer) {
			return isTypeOnly(((Integer) flags).intValue());
		} else {
			return false;
		}
	}

	public static boolean isMethodOnly(Object flags) {
		if (flags instanceof Integer) {
			return isMethodOnly(((Integer) flags).intValue());
		} else {
			return false;
		}
	}

	public static boolean isMethodOnly(int flags) {
		return (flags & METHOD_ONLY) != 0;
	}

	public static boolean isMagicMethod(Object flags) {
		if (flags instanceof Integer) {
			return isMagicMethod(((Integer) flags).intValue());
		} else {
			return false;
		}
	}

	public static boolean isFullName(Object flags) {
		if (flags instanceof Integer) {
			return isFullName(((Integer) flags).intValue());
		} else {
			return false;
		}
	}

	public static boolean isNoInsert(int flags) {
		return (flags & NO_INSERT_NAMESPACE) != 0;
	}

	public static boolean isNoInsert(Object flags) {
		if (flags instanceof Integer) {
			return isNoInsert(((Integer) flags).intValue());
		} else {
			return false;
		}
	}

	public static boolean isClassInNamespace(int flags) {
		return (flags & CLASS_IN_NAMESPACE) != 0;
	}

	public static boolean isClassInNamespace(Object flags) {
		if (flags instanceof Integer) {
			return isClassInNamespace(((Integer) flags).intValue());
		} else {
			return false;
		}
	}

	public static boolean isAddQuote(Object flags) {
		if (flags instanceof Integer) {
			return contain(((Integer) flags).intValue(), ADD_QUOTES);
		} else {
			return false;
		}
	}

	public static boolean isNotInsertUse(Object flags) {
		if (flags instanceof Integer) {
			return contain(((Integer) flags).intValue(), NO_INSERT_USE);
		} else {
			return false;
		}
	}

	public static boolean contain(int flags, int flag) {
		return (flags & flag) != 0;
	}

}
