package org.eclipse.php.internal.core.filenetwork;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.core.IFileHierarchyInfo;
import org.eclipse.dltk.core.ISourceModule;

/**
 * This tree represents file references network
 */
public class ReferenceTree implements IFileHierarchyInfo {

	final private Node root;

	public ReferenceTree(Node root) {
		assert root != null;
		this.root = root;
	}

	public Node getRoot() {
		return root;
	}

	public String toString() {
		return root.toString();
	}

	/**
	 * Looks for the given file in tree hierarchy
	 * @param sourceModule
	 * @return <code>true</code> if such a file was found, otherwise - <code>false</code>
	 */
	public boolean find(ISourceModule sourceModule) {
		return root.find(sourceModule);
	}

	static class Node {

		final private ISourceModule file;
		private Set<Node> children;

		public Node(ISourceModule file) {
			assert file != null;
			this.file = file;
		}

		public void addChild(Node child) {
			if (children == null) {
				children = new HashSet<Node>();
			}
			children.add(child);
		}

		public Collection<Node> getChildren() {
			return children;
		}

		public ISourceModule getFile() {
			return file;
		}

		public boolean find(ISourceModule sourceModule) {
			if (sourceModule == null) {
				return false;
			}
			if (file.equals(sourceModule)) {
				return true;
			}
			if (children != null) {
				for (Node child : children) {
					if (child.find(sourceModule)) {
						return true;
					}
				}
			}
			return false;
		}

		private void toString(StringBuilder buf, int tabs) {
			String fileName = file.getPath().toOSString();

			if (tabs > 0) {
				for (int i = 0; i < tabs; ++i) {
					buf.append('\t');
				}
				buf.append("|\n"); //$NON-NLS-1$
				for (int i = 0; i < tabs; ++i) {
					buf.append('\t');
				}
				buf.append("+-> "); //$NON-NLS-1$
			}

			buf.append(fileName).append('\n');
			if (children != null) {
				for (Node child : children) {
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

	public boolean exists(ISourceModule file) {
		return find(file);
	}
}

