package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class PHPTraitType extends PHPClassType {

	public PHPTraitType(String typeName) {
		super(typeName);
	}

	public PHPTraitType(String namespace, String typeName) {
		super(namespace, typeName);
	}

	public int hashCode() {
		final int prime = 33;
		int result = 1;
		result = prime * result
				+ ((getNamespace() == null) ? 0 : getNamespace().hashCode());
		result = prime * result
				+ ((getTypeName() == null) ? 0 : getTypeName().hashCode());
		return result;
	}
}
