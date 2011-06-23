package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.core.IType;

public class PHPThisClassType extends PHPClassType {

	private IType type;

	public PHPThisClassType(String namespace, String typeName, IType type) {
		super(namespace, typeName);
		this.type = type;
	}

	public PHPThisClassType(String typeName, IType type) {
		super(typeName);
		this.type = type;
	}

	public IType getType() {
		return type;
	}
}
