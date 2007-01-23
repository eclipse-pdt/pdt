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
package org.eclipse.php.internal.debug.ui.launching;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

//import com.ibm.mrclean.project.FlexibleProjectUtils;

public class ApplicationFileSelectionDialog extends ElementTreeSelectionDialog {

    protected String[] fExtensions;
    protected String[] fRequiredNatures;

    /**
     * FilteredFileSelectionDialog constructor comment.
     * @param parent Shell
     * @param title String
     * @param message String
     * @parent extensions String[]
     * @param allowMultiple boolean
     */
    public ApplicationFileSelectionDialog(Shell parent, ILabelProvider labelProvider, String title, String message, String[] extensions, String[] requiredNatures, boolean allowMultiple) {

        super(parent, labelProvider, new BaseWorkbenchContentProvider());

        setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
        setTitle(title);
        if (title == null)
            setTitle("Title"); //$NON-NLS-1$
        if (message == null)
            message = "Message"; //$NON-NLS-1$
        setMessage(message);
        setAllowMultiple(allowMultiple);

        if (extensions != null)
            addFilter(new ApplicationFileViewerFilter(requiredNatures, extensions));

    }

    public String[] getExtensions() {
        return fExtensions;
    }

    public void setExtensions(String[] extensions) {
        fExtensions = extensions;
    }
}
