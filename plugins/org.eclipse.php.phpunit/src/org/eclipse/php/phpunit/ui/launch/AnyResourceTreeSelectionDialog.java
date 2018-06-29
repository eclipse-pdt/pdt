/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

/**
 * A class to select workspace elements of out a tree structure or by entering
 * name directly in control field.
 *
 */
public class AnyResourceTreeSelectionDialog extends ElementTreeSelectionDialog {

	private Text text;

	private boolean isRefresh;

	private TreeViewer viewer;

	private List<IPath> result;

	private String textMessage;

	public AnyResourceTreeSelectionDialog(Shell parent, ILabelProvider labelProvider,
			ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);
	}

	@Override
	protected TreeViewer createTreeViewer(Composite parent) {
		viewer = super.createTreeViewer(parent);

		viewer.addSelectionChangedListener(event -> {
			if (isRefresh) {
				return;
			}

			isRefresh = true;
			try {
				IStructuredSelection ssel = ((IStructuredSelection) event.getSelection());
				String newText = ssel.isEmpty() ? "" //$NON-NLS-1$
						: ((IResource) ssel.getFirstElement()).getProjectRelativePath().toPortableString();
				text.setText(newText);
				result = null;
			} finally {
				isRefresh = false;
			}
		});

		Label label = new Label(parent, SWT.BORDER);
		label.setText(textMessage);
		text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		text.addModifyListener(e -> {
			if (isRefresh) {
				return;
			}

			isRefresh = true;
			try {
				String newtext = text.getText();
				IPath path = new Path(newtext);
				IResource res = ((IContainer) viewer.getInput()).findMember(path);
				if (res != null) {
					viewer.setSelection(new StructuredSelection(res));
				} else {
					viewer.setSelection(StructuredSelection.EMPTY);
				}
				result = Collections.singletonList(path);
			} finally {
				isRefresh = false;
			}
		});

		return viewer;
	}

	@Override
	protected void computeResult() {
		if (result != null) {
			setResult(result);
		} else {
			super.computeResult();
		}
	}

	public void setTextMessage(String text) {
		textMessage = text;
	}

}
