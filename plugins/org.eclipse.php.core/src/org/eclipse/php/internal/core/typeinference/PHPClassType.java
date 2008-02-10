package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.evaluation.types.IClassType;
import org.eclipse.dltk.ti.types.ClassType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.mixin.PHPMixinParser;

public class PHPClassType extends ClassType implements IClassType {

	private String typeName;

	public PHPClassType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public boolean subtypeOf(IEvaluatedType type) {
		return false; //TODO
	}

	public String getModelKey() {
		return typeName + PHPMixinParser.CLASS_SUFFIX;
	}
}
