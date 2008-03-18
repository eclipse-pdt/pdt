package org.eclipse.php.internal.core.filenetwork;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.core.ISourceModule;

public class ReferenceTreeNode {

	final private ISourceModule file;
	private Set<ReferenceTreeNode> children;

	public ReferenceTreeNode(ISourceModule file) {
		this.file = file;
	}

	public void addChild(ReferenceTreeNode child) {
		if (children == null) {
			children = new HashSet<ReferenceTreeNode>();
		}
		children.add(child);
	}

	public Collection<ReferenceTreeNode> getChildren() {
		return children;
	}

	public ISourceModule getFile() {
		return file;
	}

	private void toString(StringBuilder buf, int tabs) {
		String fileName = file.getPath().toOSString();

		if (tabs > 0) {
			for (int i = 0; i < tabs; ++i) {
				buf.append('\t');
			}
			buf.append("|\n");
			for (int i = 0; i < tabs; ++i) {
				buf.append('\t');
			}
			buf.append("+-> ");
		}

		buf.append(fileName).append('\n');
		if (children != null) {
			for (ReferenceTreeNode child : children) {
				child.toString(buf, tabs + 1);
			}
		}
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		toString(buf, 0);
		return buf.toString();
	}
}