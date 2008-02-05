package org.eclipse.php.internal.core.mixin;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;

public class PHPMixinElementInfo {

	public static final int K_CLASS = 0;
	public static final int K_INTERFACE = 1;
	public static final int K_METHOD = 2;
	public static final int K_VARIABLE = 3;
	public static final int K_CONSTANT = 4;
	public static final int K_INCLUDE = 5;

	private int kind = 0;
	private Object object = null;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + kind;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PHPMixinElementInfo other = (PHPMixinElementInfo) obj;
		if (kind != other.kind)
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}

	public PHPMixinElementInfo(int kind, Object object) {
		super();
		this.kind = kind;
		this.object = object;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public static PHPMixinElementInfo createClass(IType type) {
		return new PHPMixinElementInfo(K_CLASS, type);
	}

	public static PHPMixinElementInfo createInterface(IType type) {
		return new PHPMixinElementInfo(K_INTERFACE, type);
	}

	public static PHPMixinElementInfo createMethod(IMethod m) {
		return new PHPMixinElementInfo(K_METHOD, m);
	}

	public static PHPMixinElementInfo createInclude(String key) {
		return new PHPMixinElementInfo(K_INCLUDE, key);
	}

	public static PHPMixinElementInfo createVariable(IField type) {
		return new PHPMixinElementInfo(K_VARIABLE, type);
	}

	public static PHPMixinElementInfo createConstant(IField type) {
		return new PHPMixinElementInfo(K_CONSTANT, type);
	}
}
