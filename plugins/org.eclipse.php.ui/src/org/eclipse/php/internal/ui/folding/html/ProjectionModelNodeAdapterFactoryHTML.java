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

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.wst.html.core.internal.provisional.HTML40Namespace;
import org.eclipse.wst.sse.core.internal.provisional.AbstractAdapterFactory;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.w3c.dom.Node;

public class ProjectionModelNodeAdapterFactoryHTML extends
		AbstractAdapterFactory {
	// copies of this class located in:
	// org.eclipse.wst.html.ui.internal.projection
	// org.eclipse.jst.jsp.ui.internal.projection

	/**
	 * List of projection viewers currently associated with this projection
	 * model node adapter factory.
	 */
	private HashMap fProjectionViewers;

	public ProjectionModelNodeAdapterFactoryHTML(Object adapterKey,
			boolean registerAdapters) {
		super(adapterKey, registerAdapters);
	}

	public ProjectionModelNodeAdapterFactoryHTML(Object adapterKey) {
		super(adapterKey);
	}

	public ProjectionModelNodeAdapterFactoryHTML() {
		this(ProjectionModelNodeAdapterHTML.class);
	}

	/**
	 * Actually creates an adapter for the parent of target if target is the
	 * "adapt-able" node
	 */
	protected INodeAdapter createAdapter(INodeNotifier target) {
		if ((isActive()) && (target instanceof Node)
				&& ((Node) target).getNodeType() == Node.ELEMENT_NODE) {
			Node node = (Node) target;
			if (isNodeProjectable(node)) {

				// actually work with the parent node to listen for add,
				// delete events
				Node parent = node.getParentNode();
				if (parent instanceof INodeNotifier) {
					INodeNotifier parentNotifier = (INodeNotifier) parent;
					ProjectionModelNodeAdapterHTML parentAdapter = (ProjectionModelNodeAdapterHTML) parentNotifier
							.getExistingAdapter(ProjectionModelNodeAdapterHTML.class);
					if (parentAdapter == null) {
						// create a new adapter for parent
						parentAdapter = new ProjectionModelNodeAdapterHTML(this);
						parentNotifier.addAdapter(parentAdapter);
					}
					// call update on parent because a new node has just been
					// added
					parentAdapter.updateAdapter(parent);
				}
			}
		}

		return null;
	}

	/**
	 * Returns true if node is a node type able to fold
	 * 
	 * @param node
	 * @return boolean true if node is projectable, false otherwise
	 */
	boolean isNodeProjectable(Node node) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			String tagName = node.getNodeName();
			// node is only projectable if it is head, body, script, style,
			// table, ul, ol, and div tags
			if (HTML40Namespace.ElementName.HEAD.equalsIgnoreCase(tagName)
					|| HTML40Namespace.ElementName.BODY
							.equalsIgnoreCase(tagName)
					|| HTML40Namespace.ElementName.SCRIPT
							.equalsIgnoreCase(tagName)
					|| HTML40Namespace.ElementName.STYLE
							.equalsIgnoreCase(tagName)
					|| HTML40Namespace.ElementName.TABLE
							.equalsIgnoreCase(tagName)
					|| HTML40Namespace.ElementName.UL.equalsIgnoreCase(tagName)
					|| HTML40Namespace.ElementName.OL.equalsIgnoreCase(tagName)
					|| HTML40Namespace.ElementName.DIV
							.equalsIgnoreCase(tagName))
				return true;
		}
		return false;
	}

	/**
	 * Return true if this factory is currently actively managing projection
	 * 
	 * @return
	 */
	boolean isActive() {
		return (fProjectionViewers != null && !fProjectionViewers.isEmpty());
	}

	/**
	 * Updates projection annotation model if document is not in flux.
	 * Otherwise, queues up the changes to be applied when document is ready.
	 * 
	 * @param node
	 * @param deletions
	 * @param additions
	 * @param modifications
	 */
	void queueAnnotationModelChanges(Node node, Annotation[] deletions,
			Map additions, Annotation[] modifications) {
		queueAnnotationModelChanges(node, deletions, additions, modifications,
				null);
	}

	/**
	 * Updates projection annotation model for a specific projection viewer if
	 * document is not in flux. Otherwise, queues up the changes to be applied
	 * when document is ready.
	 * 
	 * @param node
	 * @param deletions
	 * @param additions
	 * @param modifications
	 * @param viewer
	 */
	void queueAnnotationModelChanges(Node node, Annotation[] deletions,
			Map additions, Annotation[] modifications, ProjectionViewer viewer) {
		// create a change object for latest change and add to queue
		ProjectionAnnotationModelChanges newChange = new ProjectionAnnotationModelChanges(
				node, deletions, additions, modifications);
		if (fProjectionViewers != null) {
			if (viewer != null) {
				ProjectionViewerInformation info = (ProjectionViewerInformation) fProjectionViewers
						.get(viewer);
				if (info != null) {
					info.queueAnnotationModelChanges(newChange);
				}
			} else {
				Iterator infos = fProjectionViewers.values().iterator();
				while (infos.hasNext()) {
					ProjectionViewerInformation info = (ProjectionViewerInformation) infos
							.next();
					info.queueAnnotationModelChanges(newChange);
				}
			}
		}
	}

	public void release() {
		// go through every projectionviewer and call
		// removeProjectionViewer(viewer);
		if (fProjectionViewers != null) {
			Iterator infos = fProjectionViewers.values().iterator();
			while (infos.hasNext()) {
				ProjectionViewerInformation info = (ProjectionViewerInformation) infos
						.next();
				info.dispose();
				infos.remove();
			}
			fProjectionViewers = null;
		}
		super.release();
	}

	/**
	 * Adds viewer to list of projection viewers this factory is associated with
	 * 
	 * @param viewer
	 *            - assumes viewer's document and projection annotation model
	 *            are not null
	 */
	public void addProjectionViewer(ProjectionViewer viewer) {
		// remove old entry if it exists
		removeProjectionViewer(viewer);

		if (fProjectionViewers == null) {
			fProjectionViewers = new HashMap();
		}

		// create new object containing projection viewer and its info
		ProjectionViewerInformation info = new ProjectionViewerInformation(
				viewer);
		fProjectionViewers.put(viewer, info);
		info.initialize();
	}

	/**
	 * Removes the given viewer from the list of projection viewers this factor
	 * is associated with
	 * 
	 * @param viewer
	 */
	public void removeProjectionViewer(ProjectionViewer viewer) {
		if (fProjectionViewers != null) {
			// remove entry from list of viewers
			ProjectionViewerInformation info = (ProjectionViewerInformation) fProjectionViewers
					.remove(viewer);
			if (info != null) {
				info.dispose();
			}
			// if removing last projection viewer, clear out everything
			if (fProjectionViewers.isEmpty()) {
				fProjectionViewers = null;
			}
		}
	}
}
