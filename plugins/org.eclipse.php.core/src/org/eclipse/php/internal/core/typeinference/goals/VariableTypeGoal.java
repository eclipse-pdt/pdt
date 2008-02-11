package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ast.references.VariableKind;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;

/**
 * Type of a class/instance/global/constant variables
 * 
 */
public class VariableTypeGoal extends AbstractTypeGoal {

	private final String name;
	private final String parentKey;
	private final VariableKind kind;

	public VariableTypeGoal(IContext context, String name, String parent,
			VariableKind kind) {
		super(context);
		this.name = name;
		parentKey = parent;
		this.kind = kind;
	}

	public VariableKind getKind() {
		return kind;
	}

	public String getName() {
		return name;
	}

	public String getParentKey() {
		return parentKey;
	}

}
