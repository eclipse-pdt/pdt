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

import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.w3c.dom.Document;
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
		initializePreference();
	}

	public ProjectionModelNodeAdapterFactoryPHP(Object adapterKey) {
		super(adapterKey);
		initializePreference();
	}

	/**
	 * Initialize the preferences of the PHP folding.
	 */
	public void initializePreference() {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		foldingClasses = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_CLASSES);
		foldingFunctions = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_FUNCTIONS);
		foldingPhpDoc = store.getBoolean(PreferenceConstants.EDITOR_FOLDING_PHPDOC);
	}

	boolean isFoldingClasses() {
		return foldingClasses;
	}

	boolean isFoldingFunctions() {
		return foldingFunctions;
	}

	boolean isFoldingPhpDoc() {
		return foldingPhpDoc;
	}

	/**
	 * Actually creates an adapter for the parent of target if target is the
	 * "adapt-able" node
	 */
	@Override
	protected INodeAdapter createAdapter(INodeNotifier target) {

		if (isActive() && target instanceof Node && ((Node) target).getNodeType() == Node.DOCUMENT_NODE) {
			// actually work with the parent node to listen for add,
			// delete events
			final Document document = (Document) target;
			if (document instanceof INodeNotifier) {
				INodeNotifier documentNotifier = (INodeNotifier) document;
				ProjectionModelNodeAdapterPHP documentAdapter = (ProjectionModelNodeAdapterPHP) documentNotifier.getExistingAdapter(ProjectionModelNodeAdapterPHP.class);
				if (documentAdapter == null) {
					// create a new adapter for parent
					documentAdapter = new ProjectionModelNodeAdapterPHP(this);
					documentNotifier.addAdapter(documentAdapter);
				}
				// call update on parent because a new node has just been
				// added
				documentAdapter.updateAdapter(document);
			}
		}

		return null;
	}

	/**
	 * @param phpModel
	 */
	public ProjectionViewerInformation getInformation(ProjectionViewer viewer) {
		return fProjectionViewers.get(viewer);
	}

	/**
	 * @param phpModel
	 */
	public ProjectionViewer findViewer(DOMModelForPHP phpModel) {
		// this situation is possible if the release() operation claled beforehand
		if (fProjectionViewers == null) {
			return null;
		}

		for (Map.Entry<ProjectionViewer, ProjectionViewerInformation> entry : fProjectionViewers.entrySet()) {
			ProjectionViewer viewer = entry.getKey();
			if (viewer.getDocument() == phpModel.getStructuredDocument()) {
				return entry.getKey();
			}
		}
		throw new IllegalStateException(PHPUIMessages.getString("ProjectionModelNodeAdapterFactoryPHP.0")); //$NON-NLS-1$
	}

}
