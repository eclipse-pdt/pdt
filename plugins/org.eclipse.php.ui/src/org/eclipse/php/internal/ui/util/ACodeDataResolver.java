package org.eclipse.php.internal.ui.util;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public abstract class ACodeDataResolver {
	protected CodeData candidate;
	protected Object fileSource;
	protected int offset;

	public boolean initialize(Object fileSource, int offset) {
		this.fileSource = fileSource;
		this.offset = offset;
		candidate = null;
		return true;
	}

	public final void resolve() {
		try {
			if (isCommented() || isSingleQuoted()) {
				return;
			}
		} catch (CodeUnresolvedException e) {
		}
	}

	abstract boolean isSingleQuoted();

	abstract boolean isCommented();

	public final CodeData getResult() {
		return candidate;
	}

	public static class CodeUnresolvedException extends RuntimeException {
		public CodeUnresolvedException(String message) {
			super(message);
		}
	}
}
