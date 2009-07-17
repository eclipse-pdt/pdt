package org.eclipse.php.internal.core.model;

import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;

public class IncludeField extends SourceField {

	public static final String NAME = "__include__"; //$NON-NLS-1$
	private String filePath;
	private int offset;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	private int length;

	public IncludeField(ModelElement parent, String filePath) {
		super(parent, NAME);

		assert filePath != null;

		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public boolean equals(Object o) {
		if (super.equals(o)) {
			IncludeField other = (IncludeField) o;
			return filePath.equals(other.filePath);
		}
		return false;
	}
}
