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
}
