package org.eclipse.php.core.ast.nodes;

import java.util.List;

abstract public class AttributedStatement extends Statement {

	public AttributedStatement(AST ast) {
		super(ast);
	}

	public AttributedStatement(int start, int end, AST ast) {
		super(start, end, ast);
	}

	private ASTNode.NodeList<AttributeGroup> attributes = new ASTNode.NodeList<>(getAttributesProperty());;

	protected abstract ChildListPropertyDescriptor getAttributesProperty();

	public List<AttributeGroup> attributes() {
		return attributes;
	}

	@Override
	List<? extends ASTNode> internalGetChildListProperty(ChildListPropertyDescriptor property) {
		if (property == getAttributesProperty()) {
			return attributes();
		}
		return super.internalGetChildListProperty(property);
	}

	protected void toStringAttributes(StringBuilder buffer, String tab) {
		if (attributes().size() > 0) {
			buffer.append(tab).append("<Attributes>\n"); //$NON-NLS-1$
			for (AttributeGroup g : attributes()) {
				g.toString(buffer, TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(tab).append("</Attributes>\n"); //$NON-NLS-1$
		}
	}

}
