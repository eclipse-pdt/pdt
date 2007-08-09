/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.folding.projection;

import java.util.Map;

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
	// org.eclipse.php.internal.ui.projection


	private Node fNode;
	private Annotation[] fDeletions;
	private Map fAdditions;
	private Annotation[] fModifications;

	public ProjectionAnnotationModelChanges(Node node, Annotation[] deletions, Map additions, Annotation[] modifications) {
		fNode = node;
		fDeletions = deletions;
		fAdditions = additions;
		fModifications = modifications;
	}

	public Map getAdditions() {
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
}
