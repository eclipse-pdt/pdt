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
package org.eclipse.php.internal.ui.dialogs.saveFiles;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.dialogs.saveFiles.SaveFilesHandler.SaveFilesResult;
import org.eclipse.php.internal.ui.util.ListContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ListSelectionDialog;

/**
 * A generic save files dialog. The bulk of the code for this dialog was taken
 * from the JDT refactoring support in
 * org.eclipse.jdt.internal.ui.refactoring.RefactoringSaveHelper. This class is
 * a good candidate for reuse amoung components.
 */
public class SaveFilesDialog extends ListSelectionDialog {

	boolean promptAutoSave;
	SaveFilesResult result;

	public SaveFilesDialog(Shell parent, List dirtyEditors,
			SaveFilesResult result, boolean promptAutoSave) {
		super(parent, dirtyEditors, new ListContentProvider(),
				new LabelProvider() {
					public Image getImage(Object element) {
						return ((IEditorPart) element).getTitleImage();
					}

					public String getText(Object element) {
						IEditorPart editor = (IEditorPart) element;
						IFile file = (IFile) editor.getEditorInput()
								.getAdapter(IResource.class);
						String title = editor.getTitle();
						if (file == null) {
							return title;
						}
						return NLS
								.bind("{0} [{1}]", new String[] { title, file.getFullPath().toString() }); //$NON-NLS-1$
					}
				}, PHPUIMessages.SaveFilesDialog_1); 
		this.promptAutoSave = promptAutoSave;
		this.result = result;
		setTitle(PHPUIMessages.SaveFilesDialog_2); 
		setMessage(PHPUIMessages.SaveFilesDialog_3); 
	}

	protected Control createDialogArea(Composite container) {
		Composite area = (Composite) super.createDialogArea(container);
		if (promptAutoSave) {
			final Button check = new Button(area, SWT.CHECK);
			check.setText(PHPUIMessages.SaveFilesDialog_4); 
			check.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					result.setAutoSave(check.getSelection());
				}
			});
			applyDialogFont(area);
		}
		return area;
	}
}
