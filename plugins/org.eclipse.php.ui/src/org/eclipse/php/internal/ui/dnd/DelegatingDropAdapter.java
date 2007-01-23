/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.dnd;

import org.eclipse.jface.util.Assert;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TransferData;

/**
 * A delegating drop adapter negotiates between a set of <code>TransferDropTargetListener</code>s
 * On <code>dragEnter</code> the adapter determines the listener to be used for any further
 * <code>drag*</code> callback.
 */
public class DelegatingDropAdapter implements DropTargetListener {

	private TransferDropTargetListener[] fListeners;

	private TransferDropTargetListener fChosenListener;

	/**
	 * Creates a new delegating drop adapter.
	 *
	 * @param listeners an array of potential listeners
	 */
	public DelegatingDropAdapter(TransferDropTargetListener[] listeners) {
		fListeners = listeners;
		Assert.isNotNull(listeners);
	}

	/* non Java-doc
	 * @see DropTargetListener
	 */
	public void dragEnter(DropTargetEvent event) {
		fChosenListener = null;
		event.currentDataType = selectPreferredListener(event.dataTypes);
		if (fChosenListener != null)
			fChosenListener.dragEnter(event);
	}

	/* non Java-doc
	 * @see DropTargetListener
	 */
	public void dragLeave(DropTargetEvent event) {
		if (fChosenListener != null)
			fChosenListener.dragLeave(event);
	}

	/* non Java-doc
	 * @see DropTargetListener
	 */
	public void dragOperationChanged(DropTargetEvent event) {
		if (fChosenListener != null)
			fChosenListener.dragOperationChanged(event);
	}

	/* non Java-doc
	 * @see DropTargetListener
	 */
	public void dragOver(DropTargetEvent event) {
		if (fChosenListener != null)
			fChosenListener.dragOver(event);
	}

	/* non Java-doc
	 * @see DropTargetListener
	 */
	public void drop(DropTargetEvent event) {
		if (fChosenListener != null)
			fChosenListener.drop(event);
		fChosenListener = null;
	}

	/* non Java-doc
	 * @see DropTargetListener
	 */
	public void dropAccept(DropTargetEvent event) {
		if (fChosenListener != null)
			fChosenListener.dropAccept(event);
	}

	private TransferData selectPreferredListener(TransferData[] dataTypes) {
		for (int i = 0; i < fListeners.length; i++) {
			TransferData data = computeTransferData(dataTypes, fListeners[i]);
			if (data != null)
				return data;
		}
		return null;
	}

	private TransferData computeTransferData(TransferData[] dataTypes, TransferDropTargetListener listener) {
		for (int i = 0; i < dataTypes.length; i++) {
			if (listener.getTransfer().isSupportedType(dataTypes[i])) {
				fChosenListener = listener;
				return dataTypes[i];
			}
		}
		return null;
	}
}
