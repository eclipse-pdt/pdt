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
package org.eclipse.php.internal.ui.projection;

import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.dom.ElementImplForPhp;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.w3c.dom.Node;

public class ProjectionModelNodeAdapterFactoryPHP extends ProjectionModelNodeAdapterFactoryHTML {

	private boolean foldingClasses;
	private boolean foldingFunctions;
	private boolean foldingPhpDoc;

	public ProjectionModelNodeAdapterFactoryPHP() {
		this(ProjectionModelNodeAdapterPHP.class);
	}

	public ProjectionModelNodeAdapterFactoryPHP(Object adapterKey, boolean registerAdapters) {
		super(adapterKey, registerAdapters);
		initialize();
	}

	public ProjectionModelNodeAdapterFactoryPHP(Object adapterKey) {
		super(adapterKey);
		initialize();
	}

	/**
	 * Initialize the preferences of the PHP folding.
	 */
	private void initialize() {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		foldingClasses = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_CLASSES);
		foldingFunctions = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_FUNCTIONS);
		foldingPhpDoc = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_PHPDOC);
	}

	boolean isFoldingClasses() {
		return true;
		// return foldingClasses;
	}

	boolean isFoldingFunctions() {
		return true;
		// return foldingFunctions;
	}

	boolean isFoldingPhpDoc() {
		return true;
		// return foldingPhpDoc;
	}

	/**
	 * Actually creates an adapter for the parent of target if target is the
	 * "adapt-able" node
	 */
	protected INodeAdapter createAdapter(INodeNotifier target) {
		if (isActive() && target instanceof Node && ((Node) target).getNodeType() == Node.ELEMENT_NODE) {
			Node node = (Node) target;
			if (isNodeProjectable(node)) {

				// actually work with the parent node to listen for add,
				// delete events
				Node parent = node.getParentNode();
				if (parent instanceof INodeNotifier) {
					INodeNotifier parentNotifier = (INodeNotifier) parent;
					ProjectionModelNodeAdapterPHP parentAdapter = (ProjectionModelNodeAdapterPHP) parentNotifier.getExistingAdapter(ProjectionModelNodeAdapterPHP.class);
					if (parentAdapter == null) {
						// create a new adapter for parent
						parentAdapter = new ProjectionModelNodeAdapterPHP(this);
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
			ElementImplForPhp element = (ElementImplForPhp) node;
			if (element.isPhpTag()) {
				return true;
			}
		}
		return super.isNodeProjectable(node);
	}

	/**
	 * @param phpModel
	 */
	public ProjectionViewerInformation findInformation(DOMModelForPHP phpModel) {
		for (Map.Entry<ProjectionViewer, ProjectionViewerInformation> entry : fProjectionViewers.entrySet()) {
			ProjectionViewer viewer = entry.getKey();
			if (viewer.getDocument() == phpModel.getStructuredDocument()) {
				return entry.getValue();
			}
		}
		throw new IllegalStateException("Viewer should exist");
	}
}
