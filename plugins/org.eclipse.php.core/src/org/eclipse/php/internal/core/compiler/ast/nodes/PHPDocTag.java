package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.utils.CorePrinter;

public class PHPDocTag extends ASTNode implements PHPDocTagKinds {

	private final int tagKind;
	private String value;

	public PHPDocTag(int start, int end, int tagKind, String value) {
		super(start, end);
		this.tagKind = tagKind;
		this.value = value;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.PHP_DOC_TAG;
	}

	public int getTagKind() {
		return this.tagKind;
	}

	public String getValue() {
		return value;
	}

	public void adjustStart(int start) {
		setStart(sourceStart() + start);
		setEnd(sourceEnd() + start);
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("PHPDocBlock" + this.getSourceRange().toString() +"("+getTagKind(this.tagKind) +  "): ");
		output.formatPrint(this.value);
	}
	
	public static String getTagKind(int kind) {
		switch (kind) {
			case ABSTRACT:
				return "abstract";

			case AUTHOR:
				return "author";

			case DEPRECATED:
				return "deprecated";

			case FINAL:
				return "final";

			case GLOBAL:
				return "global";

			case NAME:
				return "name";

			case RETURN:
				return "return";

			case PARAM:
				return "param";

			case SEE:
				return "see";

			case STATIC:
				return "static";

			case STATICVAR:
				return "staticvar";

			case TODO:
				return "todo";

			case VAR:
				return "var";

			case PACKAGE:
				return "package";

			case ACCESS:
				return "access";

			case CATEGORY:
				return "category";

			case COPYRIGHT:
				return "copyright";

			case DESC:
				return "desc";

			case EXAMPLE:
				return "example";

			case FILESOURCE:
				return "filesource";

			case IGNORE:
				return "ignore";

			case INTERNAL:
				return "internal";

			case LICENSE:
				return "license";

			case LINK:
				return "link";

			case SINCE:
				return "since";

			case SUBPACKAGE:
				return "subpackage";

			case TUTORIAL:
				return "tutorial";

			case USES:
				return "uses";

			case VERSION:
				return "version";

			case THROWS:
				return "throws";

		}
		return "ERROR!!!";
	}
}
