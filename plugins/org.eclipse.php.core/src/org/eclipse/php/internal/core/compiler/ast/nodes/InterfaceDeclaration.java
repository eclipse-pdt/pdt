package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;

/**
 * Represents an interface declaration
 * <pre>
 * <pre>e.g.<pre>
 * interface MyInterface { },
 * interface MyInterface extends Interface1, Interface2 {
 *	 const MY_CONSTANT = 3;
 *	 public function myFunction($a);
 * }
 */
public class InterfaceDeclaration extends TypeDeclaration {

	public InterfaceDeclaration(int start, int end, int nameStart, int nameEnd, String interfaceName, List<TypeReference> interfaces, Block body) {
		super(interfaceName, nameStart, nameEnd, start, end);

		ASTListNode parentsList = new ASTListNode();
		for (TypeReference intface: interfaces) {
			parentsList.addNode(intface);
		}
		setSuperClasses(parentsList);

		setBody(body);
	}

	public int getKind() {
		return ASTNodeKinds.INTERFACE_DECLARATION;
	}
}
