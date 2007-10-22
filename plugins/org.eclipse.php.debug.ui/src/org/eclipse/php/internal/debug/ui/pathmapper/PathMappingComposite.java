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
package org.eclipse.php.internal.debug.ui.pathmapper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping;
import org.eclipse.php.internal.ui.preferences.ScrolledCompositeImpl;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.util.PHPUILabelProvider;
import org.eclipse.php.internal.ui.util.PixelConverter;
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
	private static final String[] buttonLabels = { "&Add", "&Edit", "&Remove" };
	private static final String[] columnHeaders = { "Path on server", "Local path" };
	private static final ColumnLayoutData[] columnLayoutDatas = new ColumnLayoutData[] { new ColumnWeightData(50), new ColumnWeightData(50) };

	private PathMapper pathMapper;
	private ListDialogField fMapList;

	public PathMappingComposite(Composite parent, int style) {
		super(parent, style);
		initializeControls();
	}

	protected void initializeControls() {
		fMapList = new ListDialogField(new ListAdapter(), buttonLabels, new LabelProvider());
		fMapList.setRemoveButtonIndex(IDX_REMOVE);
		fMapList.setTableColumns(new ListDialogField.ColumnsDescription(columnLayoutDatas, columnHeaders, true));

		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;

		PixelConverter conv = new PixelConverter(this);

		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(this, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite composite = new Composite(scrolledCompositeImpl, SWT.NONE);
		composite.setLayout(layout);
		scrolledCompositeImpl.setContent(composite);
		scrolledCompositeImpl.setLayout(layout);
		scrolledCompositeImpl.setFont(getFont());

		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = conv.convertWidthInCharsToPixels(50);
		Control listControl = fMapList.getListControl(composite);
		listControl.setLayoutData(data);

		Control buttonsControl = fMapList.getButtonBox(composite);
		buttonsControl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));

		Point size = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledCompositeImpl.setMinSize(size.x, size.y);
	}

	protected void handleAdd() {
	}

	protected void handleEdit() {
	}

	@SuppressWarnings("unchecked")
	protected void handleRemove() {
		Iterator<Mapping> i = fMapList.getSelectedElements().iterator();
		while (i.hasNext()) {
			pathMapper.removeMapping(i.next());
		}
	}

	public void setData(PathMapper pathMapper) {
		this.pathMapper = pathMapper;
		fMapList.setElements(Arrays.asList(pathMapper.getMapping()));
	}

	class ListAdapter implements IListAdapter {
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

		public void doubleClicked(ListDialogField field) {
			handleEdit();
		}

		public void selectionChanged(ListDialogField field) {
			List<?> selectedElements = field.getSelectedElements();
			field.enableButton(IDX_EDIT, selectedElements.size() == 1);
			field.enableButton(IDX_REMOVE, selectedElements.size() > 0);
		}
	}

	class LabelProvider extends org.eclipse.jface.viewers.LabelProvider implements ITableLabelProvider {
		private PHPUILabelProvider phpLabelProvider = new PHPUILabelProvider();

		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 1) { // local path
				PathMapper.Mapping mapping = (PathMapper.Mapping) element;
				if (mapping.type == PathEntry.Type.EXTERNAL) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
				}
				if (mapping.type == PathEntry.Type.INCLUDE_VAR) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_ENV_VAR);
				}
				if (mapping.type == PathEntry.Type.INCLUDE_FOLDER) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
				}
				IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(mapping.localPath.toString());
				if (resource != null) {
					return phpLabelProvider.getImage(resource);
				}
			}
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			PathMapper.Mapping mapping = (PathMapper.Mapping) element;
			switch (columnIndex) {
				case 0:
					return mapping.remotePath.toString();
				case 1:
					return mapping.localPath.toString();
			}
			return null;
		}
	}
}
