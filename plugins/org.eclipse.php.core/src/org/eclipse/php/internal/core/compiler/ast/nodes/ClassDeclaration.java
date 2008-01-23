package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;

/**
 * Represents a class declaration
 * <pre>
 * <pre>e.g.<pre>
 * class MyClass { },
 * class MyClass extends SuperClass implements Interface1, Interface2 {
 *   const MY_CONSTANT = 3;
 *   public static final $myVar = 5, $yourVar;
 *   var $anotherOne;
 *   private function myFunction($a) { }
 * }
 */
public class ClassDeclaration extends TypeDeclaration {

	public ClassDeclaration(int start, int end, int nameStart, int nameEnd, int modifier, String className, TypeReference superClass, List<TypeReference> interfaces, Block body) {
		super(className, nameStart, nameEnd, start, end);

		ASTListNode parentsList = new ASTListNode();
		if (superClass != null) {
			parentsList.addNode(superClass);
		}
		if (interfaces != null) {
			for (TypeReference intface: interfaces) {
				parentsList.addNode(intface);
			}
		}
		setSuperClasses(parentsList);

		setBody(body);
	}

	public int getKind() {
		return ASTNodeKinds.CLASS_DECLARATION;
	}
}
