package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.php.internal.core.typeinference.ArrayDeclaration;

public class ArrayDeclarationGoal extends AbstractTypeGoal {

	private final ArrayDeclaration arrayDeclaration;

	public ArrayDeclarationGoal(IContext context,
			ArrayDeclaration arrayDeclaration) {
		super(context);
		this.arrayDeclaration = arrayDeclaration;
	}

	public ArrayDeclaration getExpression() {
		return arrayDeclaration;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((arrayDeclaration == null) ? 0 : arrayDeclaration.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArrayDeclarationGoal other = (ArrayDeclarationGoal) obj;
		if (arrayDeclaration == null) {
			if (other.arrayDeclaration != null)
				return false;
		} else if (!arrayDeclaration.equals(other.arrayDeclaration))
			return false;
		return true;
	}

	public String toString() {
		return "ExpressionTypeGoal: " //$NON-NLS-1$
				+ ((arrayDeclaration != null) ? arrayDeclaration.toString()
						: "null") //$NON-NLS-1$
				+ " context: " //$NON-NLS-1$
				+ ((context != null) ? context.toString() : "null"); //$NON-NLS-1$
	}
}
