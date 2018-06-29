/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.pathmapper;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping;
import org.eclipse.php.internal.ui.preferences.ScrolledCompositeImpl;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.TableSorter;
import org.eclipse.php.internal.ui.wizards.fields.IListAdapter;
import org.eclipse.php.internal.ui.wizards.fields.ListDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class PathMappingComposite extends Composite {

	private static final int IDX_ADD = 0;
	private static final int IDX_EDIT = 1;
	private static final int IDX_REMOVE = 2;
	private static final String[] buttonLabels = { Messages.PathMappingComposite_0, Messages.PathMappingComposite_1,
			Messages.PathMappingComposite_2 };
	private static final String[] columnHeaders = { Messages.PathMappingComposite_5, Messages.PathMappingComposite_3,
			Messages.PathMappingComposite_4 };
	private static final ColumnLayoutData[] columnLayoutData = new ColumnLayoutData[] { new ColumnWeightData(15),
			new ColumnWeightData(50), new ColumnWeightData(50) };

	private ListDialogField<Mapping> fMapList;

	public PathMappingComposite(Composite parent, int style) {
		super(parent, style);
		initializeControls();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void initializeControls() {
		fMapList = new ListDialogField<Mapping>((IListAdapter) new ListAdapter(), buttonLabels, new LabelProvider(),
				new TableSorter());
		fMapList.setRemoveButtonIndex(IDX_REMOVE);
		fMapList.setTableColumns(new ListDialogField.ColumnsDescription(columnLayoutData, columnHeaders, true));

		GridLayout layout = new GridLayout();
		setLayout(layout);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		PixelConverter conv = new PixelConverter(this);

		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(this, SWT.V_SCROLL | SWT.H_SCROLL);
		scrolledCompositeImpl.setLayout(layout);
		scrolledCompositeImpl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite composite = new Composite(scrolledCompositeImpl, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		layout.numColumns = 2;
		composite.setLayout(layout);
		scrolledCompositeImpl.setContent(composite);
		scrolledCompositeImpl.setFont(getFont());

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = conv.convertWidthInCharsToPixels(50);
		data.heightHint = 200;
		Control listControl = fMapList.getListControl(composite);
		listControl.setLayoutData(data);

		Control buttonsControl = fMapList.getButtonBox(composite);
		buttonsControl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));

		Point size = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledCompositeImpl.setMinSize(size.x, size.y);

		updateButtonsEnablement();
	}

	protected void handleAdd() {
		PathMapperEntryDialog dialog = new PathMapperEntryDialog(getShell());
		if (dialog.open() == Window.OK) {
			Mapping mapping = dialog.getResult();
			fMapList.addElement(mapping);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		fMapList.setEnabled(enabled);

	}

	protected void handleEdit() {
		List<Mapping> l = fMapList.getSelectedElements();
		if (l.size() == 1) {
			Mapping oldElement = l.get(0);
			PathMapperEntryDialog dialog = new PathMapperEntryDialog(getShell(), oldElement);
			if (dialog.open() == Window.OK) {
				Mapping newElement = dialog.getResult();
				fMapList.replaceElement(oldElement, newElement);
			}
		}
	}

	protected void handleRemove() {
		fMapList.removeElements(fMapList.getSelectedElements());
	}

	/**
	 * Accepts only Mapping[] type
	 */
	@Override
	public void setData(Object data) {
		if (!(data instanceof Mapping[])) {
			throw new IllegalArgumentException("Data must be instance of Mapping[]"); //$NON-NLS-1$
		}
		Mapping[] mappings = (Mapping[]) data;
		fMapList.setElements(Arrays.asList(mappings));
		updateButtonsEnablement();
	}

	public Mapping[] getMappings() {
		List<Mapping> l = fMapList.getElements();
		return l.toArray(new Mapping[l.size()]);
	}

	protected void updateButtonsEnablement() {
		List<Mapping> selectedElements = fMapList.getSelectedElements();
		fMapList.enableButton(IDX_EDIT, selectedElements.size() == 1);
		fMapList.enableButton(IDX_REMOVE, selectedElements.size() > 0);
	}

	class ListAdapter implements IListAdapter<Object> {
		@SuppressWarnings("rawtypes")
		@Override
		public void customButtonPressed(ListDialogField field, int index) {
			switch (index) {
			case IDX_ADD:
				handleAdd();
				break;
			case IDX_EDIT:
				handleEdit();
				break;
			case IDX_REMOVE:
				handleRemove();
				break;
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void doubleClicked(ListDialogField field) {
			handleEdit();
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void selectionChanged(ListDialogField field) {
			updateButtonsEnablement();
		}
	}

	class LabelProvider extends org.eclipse.jface.viewers.LabelProvider implements ITableLabelProvider {
		private ScriptUILabelProvider phpLabelProvider = new ScriptUILabelProvider();

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 2) { // local path
				PathMapper.Mapping mapping = (PathMapper.Mapping) element;
				if (mapping.type == Type.EXTERNAL) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
				}
				if (mapping.type == Type.INCLUDE_VAR) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_ENV_VAR);
				}
				if (mapping.type == Type.INCLUDE_FOLDER) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
				}
				IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(mapping.localPath.toString());
				if (resource != null) {
					return phpLabelProvider.getImage(resource);
				}
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			PathMapper.Mapping mapping = (PathMapper.Mapping) element;
			switch (columnIndex) {
			case 0:
				return mapping.source.toString();
			case 1:
				return mapping.remotePath.toString();
			case 2:
				return mapping.localPath.toString();
			}
			return null;
		}
	}
}
