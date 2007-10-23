/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.php.internal.ui.folding.projection;

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
import org.w3c.dom.Node;

/**
 * Updates projection annotation model with projection annotations for this
 * adapter node's children
 * @improtedFrom org.eclipse.wst.html.ui.internal.projection
 */
public class ProjectionModelNodeAdapterHTML implements INodeAdapter {
	// copies of this class located in:
	// org.eclipse.wst.html.ui.internal.projection
	// org.eclipse.jst.jsp.ui.internal.projection
	private final static boolean debugProjectionPerf = "true".equalsIgnoreCase(Platform.getDebugOption("org.eclipse.wst.html.ui/projectionperf")); //$NON-NLS-1$ //$NON-NLS-2$

	private static class TagProjectionAnnotation extends ProjectionAnnotation {
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
		@Override
		public void paint(GC gc, Canvas canvas, Rectangle rectangle) {
			/* workaround for BUG85874 */
			/*
			 * only need to check annotations that are expanded because hidden
			 * annotations should never have been given the chance to
			 * collapse.
			 */
			if (!isCollapsed()) {
				// working with rectangle, so line height
				FontMetrics metrics = gc.getFontMetrics();
				if (metrics != null) {
					// do not draw annotations that only span one line and
					// mark them as not visible
					if (rectangle.height / metrics.getHeight() <= 1) {
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
		 * @see org.eclipse.jface.text.source.projection.ProjectionAnnotation#markCollapsed()
		 */
		@Override
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
	protected Map<ProjectionAnnotation, Position> previousAnnotations = new HashMap<ProjectionAnnotation, Position>();

	public ProjectionModelNodeAdapterHTML(ProjectionModelNodeAdapterFactoryHTML factory) {
		fAdapterFactory = factory;
	}

	/**
	 * Create a projection position from the given node. Able to get
	 * projection position if node isNodeProjectable.
	 *
	 * @param node
	 * @return null if no projection position possible, a Position otherwise
	 */
	private Position createProjectionPosition(Node node) {
		Position pos = null;
		if (fAdapterFactory.isNodeProjectable(node) && node instanceof IndexedRegion) {
			// IDocument document =
			// fAdapterFactory.getProjectionViewer().getDocument();
			// if (document != null) {
			IndexedRegion inode = (IndexedRegion) node;
			int start = inode.getStartOffset();
			int end = inode.getEndOffset();
			if (start >= 0 && start < end) {
				// region-based
				// extra line when collapsed, but no region
				// increase when add newline
				pos = new Position(start, end - start);
				// try {
				// // line-based
				// // extra line when collapsed, but no region
				// // increase when add newline
				// IRegion startLineRegion =
				// document.getLineInformationOfOffset(start);
				// IRegion endLineRegion =
				// document.getLineInformationOfOffset(end);
				// int startOffset = startLineRegion.getOffset();
				// int endOffset = endLineRegion.getOffset() +
				// endLineRegion.getLength();
				// if (endOffset > startOffset) {
				// pos = new Position(startOffset, endOffset -
				// startOffset);
				// }
				//
				// // line-based
				// // no extra line when collapsed, but region increase
				// // when add newline
				// int startLine = document.getLineOfOffset(start);
				// int endLine = document.getLineOfOffset(end);
				// if (endLine + 1 < document.getNumberOfLines()) {
				// int offset = document.getLineOffset(startLine);
				// int endOffset = document.getLineOffset(endLine + 1);
				// pos = new Position(offset, endOffset - offset);
				// }
				// }
				// catch (BadLocationException x) {
				// Logger.log(Logger.WARNING_DEBUG, null, x);
				// }
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

		if (node != null && !previousAnnotations.isEmpty()) {
			Iterator it = previousAnnotations.keySet().iterator();
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

	public void notifyChanged(INodeNotifier notifier, int eventType, Object changedFeature, Object oldValue, Object newValue, int pos) {
		// check if folding is even enabled, if not, just ignore notifyChanged
		// events
		if (!fAdapterFactory.isActive()) {
			return;
		}

		if (eventType == INodeNotifier.STRUCTURE_CHANGED && notifier instanceof Node) {
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
					TagProjectionAnnotation newAnnotation = new TagProjectionAnnotation(childNode, false);
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
						previousAnnotations.remove(existing);
					}
				}
				childNode = childNode.getNextSibling();
			}

			// in the end, want to delete anything leftover in old list, add
			// everything in additions, and update everything in
			// projectionAnnotations
			ProjectionAnnotation[] oldList = null;
			if (!previousAnnotations.isEmpty()) {
				oldList = previousAnnotations.keySet().toArray(new ProjectionAnnotation[0]);
			}
			ProjectionAnnotation[] modifyList = null;
			if (!projectionAnnotations.isEmpty()) {
			// this line causes PHP folding to flash, when editing an HTML tag which is a sibling to PHP element (bug #202740)
			// modifyList = (ProjectionAnnotation[]) projectionAnnotations.keySet().toArray(new ProjectionAnnotation[0]);
			}

			// specifically add all annotations to viewer
			if (viewer != null && !projectionAnnotations.isEmpty()) {
				fAdapterFactory.queueAnnotationModelChanges(node, null, projectionAnnotations, null, viewer);
			}

			// only update when there is something to update
			if (oldList != null && oldList.length > 0 || !additions.isEmpty())
				fAdapterFactory.queueAnnotationModelChanges(node, oldList, additions, new HashMap());
		}

		// save new list of annotations
		previousAnnotations = projectionAnnotations;

		if (debugProjectionPerf) {
			long end = System.currentTimeMillis();
			String nodeName = node != null ? node.getNodeName() : "null"; //$NON-NLS-1$
			System.out.println("ProjectionModelNodeAdapterHTML.updateAdapter (" + nodeName + "):" + (end - start)); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}
