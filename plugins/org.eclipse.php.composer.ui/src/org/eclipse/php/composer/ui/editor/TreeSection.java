/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.ui.parts.StructuredViewerPart;
import org.eclipse.php.composer.ui.parts.TreePart;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class TreeSection extends StructuredViewerSection {
	protected boolean handleDefaultButton = true;

	class PartAdapter extends TreePart {
		private Label count;

		public PartAdapter(String[] buttonLabels) {
			super(buttonLabels);
		}

		public void entryModified(Object entry, String value) {
			TreeSection.this.entryModified(entry, value);
		}

		public void selectionChanged(IStructuredSelection selection) {
			getManagedForm().fireSelectionChanged(TreeSection.this, selection);
			TreeSection.this.selectionChanged(selection);
		}

		public void handleDoubleClick(IStructuredSelection selection) {
			TreeSection.this.handleDoubleClick(selection);
		}

		public void buttonSelected(Button button, int index) {
			TreeSection.this.buttonSelected(index);
			if (handleDefaultButton)
				button.getShell().setDefaultButton(null);
		}

		protected void createButtons(Composite parent, FormToolkit toolkit) {
			super.createButtons(parent, toolkit);
			enableButtons();
			if (createCount()) {
				Composite comp = toolkit.createComposite(fButtonContainer);
				comp.setLayout(createButtonsLayout());
				comp.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_END | GridData.FILL_BOTH));
				count = toolkit.createLabel(comp, ""); //$NON-NLS-1$
				count.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				count.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				getTreePart().getTreeViewer().getTree().addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent e) {
						updateLabel();
					}
				});
			}
		}

		protected void updateLabel() {
			if (count != null && !count.isDisposed())
				count.setText(NLS.bind(Messages.TreeSection_TotalCountLabel, getTreeViewer().getTree().getItemCount()));
		}
	}

	/**
	 * Constructor for TableSection.
	 * 
	 * @param formPage
	 */
	public TreeSection(ComposerFormPage formPage, Composite parent, int style, String[] buttonLabels) {
		this(formPage, parent, style, true, buttonLabels);
	}

	/**
	 * Constructor for TableSection.
	 * 
	 * @param formPage
	 */
	public TreeSection(ComposerFormPage formPage, Composite parent, int style, boolean titleBar,
			String[] buttonLabels) {
		super(formPage, parent, style, titleBar, buttonLabels);
	}

	protected StructuredViewerPart createViewerPart(String[] buttonLabels) {
		return new PartAdapter(buttonLabels);
	}

	protected TreePart getTreePart() {
		return (TreePart) viewerPart;
	}

	protected void entryModified(Object entry, String value) {
	}

	protected void selectionChanged(IStructuredSelection selection) {
	}

	protected void handleDoubleClick(IStructuredSelection selection) {
	}

	protected void enableButtons() {
	}

	protected boolean createCount() {
		return false;
	}
}
