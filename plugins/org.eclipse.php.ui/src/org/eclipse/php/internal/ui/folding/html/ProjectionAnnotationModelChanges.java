/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.folding.html;

import java.util.Map;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.w3c.dom.Node;

/**
 * Contains a set of projection model additions/deletions/modifications
 */
class ProjectionAnnotationModelChanges {
	// copies of this class located in:
	// org.eclipse.wst.xml.ui.internal.projection
	// org.eclipse.wst.css.ui.internal.projection
	// org.eclipse.wst.html.ui.internal.projection
	// org.eclipse.jst.jsp.ui.internal.projection
	private Node fNode;
	private Annotation[] fDeletions;
	private Map<Annotation, Position> fAdditions;
	private Annotation[] fModifications;

	public ProjectionAnnotationModelChanges(Node node, Annotation[] deletions, Map<Annotation, Position> additions,
			Annotation[] modifications) {
		fNode = node;
		fDeletions = deletions;
		fAdditions = additions;
		fModifications = modifications;
	}

	public Map<? extends Annotation, ? extends Position> getAdditions() {
		return fAdditions;
	}

	public Annotation[] getDeletions() {
		return fDeletions;
	}

	public Annotation[] getModifications() {
		return fModifications;
	}

	public Node getNode() {
		return fNode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProjectionAnnotationModelChanges) {
			return ((ProjectionAnnotationModelChanges) obj).getNode().isSameNode(this.getNode());
		}
		return super.equals(obj);
	}

	public void updateChange(ProjectionAnnotationModelChanges newChange) {
		fModifications = newChange.getModifications();
		fAdditions.putAll(newChange.getAdditions());
		fDeletions = newChange.getDeletions();
	}

}
