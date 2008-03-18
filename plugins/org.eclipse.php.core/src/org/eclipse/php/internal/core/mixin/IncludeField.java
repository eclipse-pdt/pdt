package org.eclipse.php.internal.core.mixin;

import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;

public class IncludeField extends SourceField {

	public static final String NAME = "__include__"; //$NON-NLS-1$
	private String filePath;

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
			IncludeField other = (IncludeField)o;
			return filePath.equals(other.filePath);
		}
		return false;
	}
}
