/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.util.PHPManualFactory;
import org.eclipse.ui.handlers.HandlerUtil;

public class SearchInManualHandler extends AbstractHandler {

	private static final String MANUAL_QUERY = "http://php.net/manual-lookup.php?pattern="; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			if (textSelection.getLength() > 0) {
				try {
					String url = MANUAL_QUERY + URLEncoder.encode(textSelection.getText(), "UTF-8"); //$NON-NLS-1$
					PHPManualFactory.getManual().showFunctionHelp(url);
				} catch (UnsupportedEncodingException e) {
					Logger.logException(e);
				}
			} else {
				MessageDialog.openWarning(HandlerUtil.getActiveShell(event),
						Messages.SearchInManualHandler_WarningTitle,
						Messages.SearchInManualHandler_EmptySelectionWarning);
			}
		}
		return null;
	}

}
