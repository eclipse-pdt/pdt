/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.text.MessageFormat;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.PHPUIMessages;

/**
 * Add the <code>StatusBarUpdater</code> to your ViewPart to have the statusbar
 * describing the selected elements.
 */
public class StatusBarUpdater implements ISelectionChangedListener {

	//	private final long LABEL_FLAGS = PHPElementLabels.DEFAULT_QUALIFIED | PHPElementLabels.ROOT_QUALIFIED | PHPElementLabels.APPEND_ROOT_PATH | PHPElementLabels.M_PARAMETER_TYPES | PHPElementLabels.M_PARAMETER_NAMES | PHPElementLabels.M_PRE_RETURNTYPE | PHPElementLabels.F_APP_TYPE_SIGNATURE
	//		| PHPElementLabels.T_TYPE_PARAMETERS | PHPElementLabels.M_PRE_RETURNTYPE;

	private final long LABEL_FLAGS = AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS;

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
				return MessageFormat.format(PHPUIMessages.getString("StatusBarUpdater_num_elements_selected"), new Object[] { String.valueOf(nElements) });
			}
			Object elem = selection.getFirstElement();
			if (elem instanceof PHPCodeData || elem instanceof PHPWorkspaceModelManager || elem instanceof PHPProjectModel) {
				return formatPHPElementMessage(elem);
			} else if (elem instanceof IResource) {
				return formatResourceMessage((IResource) elem);
			}
		}
		return ""; //$NON-NLS-1$
	}

	private String formatPHPElementMessage(Object element) {
		String postfix = ""; //$NON-NLS-1$
		if (element instanceof PHPCodeData) {
			PHPCodeData codeData = (PHPCodeData) element;
			UserData userData = codeData.getUserData();
			if (userData != null)
				postfix = " (" + userData.getFileName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		String text = PHPElementLabels.getElementLabel(element, LABEL_FLAGS);
		return text + postfix;

	}

	private String formatResourceMessage(IResource element) {
		IContainer parent = element.getParent();
		if (parent != null && parent.getType() != IResource.ROOT)
			return element.getName() + PHPElementLabels.CONCAT_STRING + parent.getFullPath().makeRelative().toString();
		else
			return element.getName();
	}

}
