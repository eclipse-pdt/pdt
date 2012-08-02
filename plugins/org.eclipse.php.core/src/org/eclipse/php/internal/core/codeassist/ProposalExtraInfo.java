package org.eclipse.php.internal.core.codeassist;

public final class ProposalExtraInfo {
	public static final int TYPE_ONLY = 1 << 1;
	public static final int MAGIC_METHOD = 1 << 2;
	// this is for namespace
	public static final int FULL_NAME = 1 << 3;
	// the stub is a type of class/method/field,which does not exist yet
	public static final int STUB = 1 << 4;
	public static final int METHOD_ONLY = 1 << 5;

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
}
