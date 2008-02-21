package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ti.goals.AbstractGoal;

public class ScalarGoal extends AbstractGoal {

	private int scalarType;

	public ScalarGoal(int scalarType) {
		super(null);
	}

	public int getScalarType() {
		return scalarType;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + scalarType;
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
		ScalarGoal other = (ScalarGoal) obj;
		if (scalarType != other.scalarType) {
			return false;
		}
		return true;
	}
}
