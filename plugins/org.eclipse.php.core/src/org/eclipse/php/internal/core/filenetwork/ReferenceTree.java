/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.filenetwork;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.dltk.core.IFileHierarchyInfo;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;

/**
 * This tree represents file references network
 */
public class ReferenceTree implements IFileHierarchyInfo {

	final private Node root;
	final private boolean isLanguageModel;

	public ReferenceTree(Node root) {
		assert root != null;
		this.root = root;
		isLanguageModel = LanguageModelInitializer.isLanguageModelElement(root
				.getFile());
	}

	public Node getRoot() {
		return root;
	}

	public String toString() {
		return root.toString();
	}

	/**
	 * Looks for the given file in tree hierarchy
	 * 
	 * @param sourceModule
	 * @return <code>true</code> if such a file was found, otherwise -
	 *         <code>false</code>
	 */
	public boolean find(ISourceModule sourceModule) {
		if (isLanguageModel) {
			return true;
		}
		return root.find(sourceModule);
	}

	public static class Node {

		final private ISourceModule file;
		private Set<Node> children;

		public Node(ISourceModule file) {
			assert file != null;
			this.file = file;
		}

		public void addChild(Node child) {
			if (children == null) {
				children = new LinkedHashSet<Node>();
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
			String fileName = file.getPath().toString();

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
