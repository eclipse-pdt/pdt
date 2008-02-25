package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;

public class PHPDocTag extends ASTNode implements PHPDocTagKinds {

	private final int tagKind;
	private String value;
	private SimpleReference[] references;

	public PHPDocTag(int start, int end, int tagKind, String value) {
		super(start, end);
		this.tagKind = tagKind;
		this.value = value;
		updateReferences(start, end);
	}

	private void updateReferences(int start, int end) {
		if (tagKind == RETURN || tagKind == PARAM || tagKind == VAR) {
			String[] parts = value.split(" ");
			if (tagKind == RETURN || tagKind == VAR) {
				if (parts.length != 0) {
					references = new SimpleReference[1];
					int firstWordPosition = start + value.indexOf(parts[0]);
					references[0] = new TypeReference(firstWordPosition, firstWordPosition + parts[0].length(), parts[0]);
				}
			} else {
				if (parts.length >= 2) {
					int firstWordPosition = start + value.indexOf(parts[0]);
					int secondWordPosition = start + value.indexOf(parts[1]);
					if (parts[0].charAt(0) == '$') {
						references = new SimpleReference[2];
						references[0] = new VariableReference(firstWordPosition, firstWordPosition + parts[0].length(), parts[0]);
						references[1] = new TypeReference(secondWordPosition, secondWordPosition + parts[1].length(), parts[1]);
					} else if (parts[1].charAt(0) == '$') {
						references = new SimpleReference[2];
						references[0] = new VariableReference(firstWordPosition, firstWordPosition + parts[1].length(), parts[1]);
						references[1] = new TypeReference(secondWordPosition, secondWordPosition + parts[0].length(), parts[0]);
					}
				}
			}
		}
		if (references == null) {
			references = new SimpleReference[0];
		}
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			for (SimpleReference ref : references) {
				ref.traverse(visitor);
			}
		}
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

	public SimpleReference[] getReferences() {
		return references;
	}

	public void adjustStart(int start) {
		setStart(sourceStart() + start);
		setEnd(sourceEnd() + start);
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
