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
package org.eclipse.php.internal.ui.folding.html;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.xml.core.internal.document.CommentImpl;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.ui.internal.projection.XMLCommentFoldingPosition;
import org.eclipse.wst.xml.ui.internal.projection.XMLElementFoldingPosition;
import org.w3c.dom.Node;

/**
 * Updates projection annotation model with projection annotations for this
 * adapter node's children
 */
public class ProjectionModelNodeAdapterHTML implements INodeAdapter {
	// copies of this class located in:
	// org.eclipse.wst.html.ui.internal.projection
	// org.eclipse.jst.jsp.ui.internal.projection
	private final static boolean debugProjectionPerf = "true".equalsIgnoreCase(Platform.getDebugOption("org.eclipse.wst.html.ui/projectionperf")); //$NON-NLS-1$ //$NON-NLS-2$

	private class TagProjectionAnnotation extends ProjectionAnnotation {
		private boolean fIsVisible = false; /* workaround for BUG85874 */
		private Node fNode;

		public TagProjectionAnnotation(Node node, boolean isCollapsed) {
			super(isCollapsed);
			fNode = node;
		}

		public Node getNode() {
			return fNode;
		}

		public void setNode(Node node) {
			fNode = node;
		}

		/**
		 * Does not paint hidden annotations. Annotations are hidden when they
		 * only span one line.
		 * 
		 * @see ProjectionAnnotation#paint(org.eclipse.swt.graphics.GC,
		 *      org.eclipse.swt.widgets.Canvas,
		 *      org.eclipse.swt.graphics.Rectangle)
		 */
		public void paint(GC gc, Canvas canvas, Rectangle rectangle) {
			/* workaround for BUG85874 */
			/*
			 * only need to check annotations that are expanded because hidden
			 * annotations should never have been given the chance to collapse.
			 */
			if (!isCollapsed()) {
				// working with rectangle, so line height
				FontMetrics metrics = gc.getFontMetrics();
				if (metrics != null) {
					// do not draw annotations that only span one line and
					// mark them as not visible
					if ((rectangle.height / metrics.getHeight()) <= 1) {
						fIsVisible = false;
						return;
					}
				}
			}
			fIsVisible = true;
			super.paint(gc, canvas, rectangle);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @seeorg.eclipse.jface.text.source.projection.ProjectionAnnotation#
		 * markCollapsed()
		 */
		public void markCollapsed() {
			/* workaround for BUG85874 */
			// do not mark collapsed if annotation is not visible
			if (fIsVisible)
				super.markCollapsed();
		}
	}

	// copies of this class located in:
	// org.eclipse.wst.html.ui.internal.projection
	// org.eclipse.jst.jsp.ui.internal.projection

	ProjectionModelNodeAdapterFactoryHTML fAdapterFactory;
	private Map fTagAnnotations = new HashMap();

	public ProjectionModelNodeAdapterHTML(
			ProjectionModelNodeAdapterFactoryHTML factory) {
		fAdapterFactory = factory;
	}

	/**
	 * Create a projection position from the given node. Able to get projection
	 * position if node isNodeProjectable.
	 * 
	 * @param node
	 * @return null if no projection position possible, a Position otherwise
	 */
	private Position createProjectionPosition(Node node) {
		Position pos = null;
		if (fAdapterFactory.isNodeProjectable(node)
				&& node instanceof IndexedRegion) {
			// IDocument document =
			// fAdapterFactory.getProjectionViewer().getDocument();
			// if (document != null) {
			IndexedRegion inode = (IndexedRegion) node;
			// only want to fold regions of the valid type and with a valid
			// range
			if (inode.getStartOffset() >= 0 && inode.getLength() >= 0) {
				IDOMNode node1 = (IDOMNode) inode;
				IStructuredDocumentRegion startRegion = node1
						.getStartStructuredDocumentRegion();
				IStructuredDocumentRegion endRegion = node1
						.getEndStructuredDocumentRegion();
				// if the node has an endRegion (end tag) then folding region is
				// between the start and end tag
				// else if the region is a comment
				// else if the region is only an open tag or an open/close tag
				// then don't fold it
				if (startRegion != null && endRegion != null) {
					pos = new XMLElementFoldingPosition(startRegion, endRegion);
				} else if (startRegion != null && node instanceof CommentImpl) {
					pos = new XMLCommentFoldingPosition(startRegion);
				}
			}
		}
		// }
		return pos;
	}

	/**
	 * Find TagProjectionAnnotation for node in the current list of projection
	 * annotations for this adapter
	 * 
	 * @param node
	 * @return TagProjectionAnnotation
	 */
	private TagProjectionAnnotation getExistingAnnotation(Node node) {
		TagProjectionAnnotation anno = null;

		if ((node != null) && (!fTagAnnotations.isEmpty())) {
			Iterator it = fTagAnnotations.keySet().iterator();
			while (it.hasNext() && anno == null) {
				TagProjectionAnnotation a = (TagProjectionAnnotation) it.next();
				Node n = a.getNode();
				if (node.equals(n)) {
					anno = a;
				}
			}
		}
		return anno;
	}

	public boolean isAdapterForType(Object type) {
		return type == ProjectionModelNodeAdapterHTML.class;
	}

	public void notifyChanged(INodeNotifier notifier, int eventType,
			Object changedFeature, Object oldValue, Object newValue, int pos) {
		// check if folding is even enabled, if not, just ignore notifyChanged
		// events
		if (!fAdapterFactory.isActive()) {
			return;
		}

		if ((eventType == INodeNotifier.STRUCTURE_CHANGED)
				&& (notifier instanceof Node)) {
			updateAdapter((Node) notifier);
		}
	}

	/**
	 * Update the projection annotation of all the nodes that are children of
	 * node
	 * 
	 * @param node
	 */
	public void updateAdapter(Node node) {
		updateAdapter(node, null);
	}

	/**
	 * Update the projection annotation of all the nodes that are children of
	 * node and adds all projection annotations to viewer (for newly added
	 * viewers)
	 * 
	 * @param node
	 * @param viewer
	 */
	void updateAdapter(Node node, ProjectionViewer viewer) {
		long start = System.currentTimeMillis();

		Map additions = new HashMap();
		Map projectionAnnotations = new HashMap();

		// go through immediate child nodes and figure out projection
		// model annotations
		if (node != null) {
			Node childNode = node.getFirstChild();
			while (childNode != null) {
				Position newPos = createProjectionPosition(childNode);
				if (newPos != null) {
					TagProjectionAnnotation newAnnotation = new TagProjectionAnnotation(
							childNode, false);
					TagProjectionAnnotation existing = getExistingAnnotation(childNode);
					if (existing == null) {
						// add to map containing all annotations for this
						// adapter
						projectionAnnotations.put(newAnnotation, newPos);
						// add to map containing annotations to add
						additions.put(newAnnotation, newPos);
					} else {
						// add to map containing all annotations for this
						// adapter
						projectionAnnotations.put(existing, newPos);
						// remove from map containing annotations to delete
						fTagAnnotations.remove(existing);
					}
				}
				childNode = childNode.getNextSibling();
			}

			// in the end, want to delete anything leftover in old list, add
			// everything in additions, and update everything in
			// projectionAnnotations
			ProjectionAnnotation[] oldList = null;
			if (!fTagAnnotations.isEmpty()) {
				oldList = (ProjectionAnnotation[]) fTagAnnotations.keySet()
						.toArray(new ProjectionAnnotation[0]);
			}
			ProjectionAnnotation[] modifyList = null;
			if (!projectionAnnotations.isEmpty()) {
				modifyList = (ProjectionAnnotation[]) projectionAnnotations
						.keySet().toArray(new ProjectionAnnotation[0]);
			}

			// specifically add all annotations to viewer
			if (viewer != null && !projectionAnnotations.isEmpty()) {
				fAdapterFactory.queueAnnotationModelChanges(node, null,
						projectionAnnotations, null, viewer);
			}

			// only update when there is something to update
			if ((oldList != null && oldList.length > 0)
					|| (!additions.isEmpty())
					|| (modifyList != null && modifyList.length > 0))
				fAdapterFactory.queueAnnotationModelChanges(node, oldList,
						additions, modifyList);
		}

		// save new list of annotations
		fTagAnnotations = projectionAnnotations;

		if (debugProjectionPerf) {
			long end = System.currentTimeMillis();
			String nodeName = node != null ? node.getNodeName() : "null"; //$NON-NLS-1$
			System.out
					.println("ProjectionModelNodeAdapterHTML.updateAdapter (" + nodeName + "):" + (end - start)); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}
