/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.editors;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;
import org.eclipse.ui.forms.IMessage;
import org.eclipse.ui.forms.IMessageManager;

/**
 * @author Michal Niewrzal, 2014
 * 
 */
class MarkerManager {

	private static final String MESSAGE_KEY = "MESSAGE_KEY"; //$NON-NLS-1$

	private IMessageManager messageManager;

	public MarkerManager(IMessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public void updateMessages(IMarkerDelta[] deltas) {
		for (IMarkerDelta markerDelta : deltas) {
			if (markerDelta.getType().equals(IMarker.PROBLEM)) {
				if (markerDelta.getKind() == IResourceDelta.ADDED) {
					addProblem(markerDelta.getMarker());
				} else if (markerDelta.getKind() == IResourceDelta.REMOVED) {
					removeProblem(markerDelta.getMarker());
				}
			}
		}
	}

	public void addProblemsFromFile(IFile file) {
		if (file == null) {
			return;
		}
		try {
			IMarker[] markers = file.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);
			for (IMarker marker : markers) {
				addProblem(marker);
			}
		} catch (CoreException e) {
			ComposerUIPlugin.logError(e);
		}
	}

	private void addProblem(IMarker marker) {
		try {
			Object message = marker.getAttribute(IMarker.MESSAGE);
			messageManager.addMessage(MESSAGE_KEY + marker.getId(), message.toString(), null, IMessage.ERROR);
		} catch (CoreException e) {
			ComposerUIPlugin.logError(e);
		}
	}

	private void removeProblem(IMarker marker) {
		messageManager.removeMessage(MESSAGE_KEY + marker.getId());
	}

}
