package org.eclipse.php.internal.core.compiler.ast.nodes;

public interface Dereferencable {
	public PHPArrayDereferenceList getArrayDereferenceList();

	public void setArrayDereferenceList(
			PHPArrayDereferenceList arrayDereferenceList);
}
