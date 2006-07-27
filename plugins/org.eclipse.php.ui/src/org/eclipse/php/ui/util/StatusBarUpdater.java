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
package org.eclipse.php.ui.util;

import java.text.MessageFormat;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;


/**
 * Add the <code>StatusBarUpdater</code> to your ViewPart to have the statusbar
 * describing the selected elements.
 */
public class StatusBarUpdater implements ISelectionChangedListener {

	private final long LABEL_FLAGS = PHPElementLabels.DEFAULT_QUALIFIED | PHPElementLabels.ROOT_POST_QUALIFIED | PHPElementLabels.APPEND_ROOT_PATH | PHPElementLabels.M_PARAMETER_TYPES | PHPElementLabels.M_PARAMETER_NAMES | PHPElementLabels.M_APP_RETURNTYPE | PHPElementLabels.F_APP_TYPE_SIGNATURE
		| PHPElementLabels.T_TYPE_PARAMETERS;

	private IStatusLineManager fStatusLineManager;

	public StatusBarUpdater(IStatusLineManager statusLineManager) {
		fStatusLineManager = statusLineManager;
	}

	/*
	 * @see ISelectionChangedListener#selectionChanged
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		String statusBarMessage = formatMessage(event.getSelection());
		fStatusLineManager.setMessage(statusBarMessage);
	}

	protected String formatMessage(ISelection sel) {
		if (sel instanceof IStructuredSelection && !sel.isEmpty()) {
			IStructuredSelection selection = (IStructuredSelection) sel;

			int nElements = selection.size();
			if (nElements > 1) {
				return MessageFormat.format(PHPUIMessages.StatusBarUpdater_num_elements_selected, new Object[] { String.valueOf(nElements) });
			} else {
				Object elem = selection.getFirstElement();
				if (elem instanceof PHPCodeData || elem instanceof PHPWorkspaceModelManager || elem instanceof PHPProjectModel) {
					return formatPHPElementMessage(elem);
				} else if (elem instanceof IResource) {
					return formatResourceMessage((IResource) elem);
				}
			}
		}
		return ""; //$NON-NLS-1$
	}

	private String formatPHPElementMessage(Object element) {
		return PHPElementLabels.getElementLabel(element, LABEL_FLAGS);
	}

	private String formatResourceMessage(IResource element) {
		IContainer parent = element.getParent();
		if (parent != null && parent.getType() != IResource.ROOT)
			return element.getName() + PHPElementLabels.CONCAT_STRING + parent.getFullPath().makeRelative().toString();
		else
			return element.getName();
	}

}
