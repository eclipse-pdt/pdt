package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ti.goals.AbstractGoal;

public class ConstantDeclarationGoal extends AbstractGoal {

	private String constantName;
	private String typeName;

	public ConstantDeclarationGoal(String constantName, String typeName) {
		super(null);
		this.constantName = constantName;
		this.typeName = typeName;
	}

	public String getConstantName() {
		return constantName;
	}

	public String getTypeName() {
		return typeName;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constantName == null) ? 0 : constantName.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

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
		ConstantDeclarationGoal other = (ConstantDeclarationGoal) obj;
		if (constantName == null) {
			if (other.constantName != null) {
				return false;
			}
		} else if (!constantName.equals(other.constantName)) {
			return false;
		}
		if (typeName == null) {
			if (other.typeName != null) {
				return false;
			}
		} else if (!typeName.equals(other.typeName)) {
			return false;
		}
		return true;
	}
}
