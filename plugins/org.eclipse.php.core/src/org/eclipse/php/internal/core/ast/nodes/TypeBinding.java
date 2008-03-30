package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class TypeBinding implements ITypeBinding {

	private final IEvaluatedType type;
	private final IModelElement element;

	public TypeBinding(IEvaluatedType type) {
		this.type = type;
		// TODO should assign element
		this.element = null;
	}
	
	public ITypeBinding createArrayType(int dimension) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getBinaryName() {
		return this.element.getHandleIdentifier();
	}

	public ITypeBinding getComponentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public IVariableBinding[] getDeclaredFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public IMethodBinding[] getDeclaredMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDeclaredModifiers() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDimensions() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ITypeBinding getElementType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITypeBinding[] getInterfaces() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getModifiers() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return this.type.getTypeName();
	}

	public ITypeBinding getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITypeBinding[] getTypeBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITypeBinding getTypeDeclaration() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isArray() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAssignmentCompatible(ITypeBinding variableType) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCastCompatible(ITypeBinding type) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isClass() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isFromSource() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isInterface() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNullType() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSubTypeCompatible(ITypeBinding type) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTypeVariable() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

	public IModelElement getPHPElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDeprecated() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEqualTo(IBinding binding) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRecovered() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSynthetic() {
		// TODO Auto-generated method stub
		return false;
	}
}
