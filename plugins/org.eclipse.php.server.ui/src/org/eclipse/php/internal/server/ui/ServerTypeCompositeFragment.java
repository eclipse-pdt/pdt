/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui;

import java.util.Collection;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.server.core.types.IServerType;
import org.eclipse.php.server.core.types.ServerTypesManager;
import org.eclipse.php.server.ui.types.IServerTypeDescriptor;
import org.eclipse.php.server.ui.types.IServerTypeDescriptor.ImageType;
import org.eclipse.php.server.ui.types.ServerTypesDescriptorRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

/**
 * Composite fragment for server type selection page.
 */
public class ServerTypeCompositeFragment extends CompositeFragment {

	private class TypesLabelProvider extends StyledCellLabelProvider {

		@Override
		public void update(ViewerCell cell) {
			Object element = cell.getElement();
			if (element instanceof IServerType) {
				String name = ((IServerType) element).getName();
				String description = ((IServerType) element).getDescription();
				cell.setText(name + "\n" + description); //$NON-NLS-1$
				IServerTypeDescriptor serverTypeDescriptor = ServerTypesDescriptorRegistry
						.getDescriptor((IServerType) element);
				cell.setImage(serverTypeDescriptor.getImage(ImageType.ICON_32));
				StyleRange greyDesc = new StyleRange(name.length() + 1, description.length(),
						Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY), null);
				StyleRange[] range = { greyDesc };
				cell.setStyleRanges(range);
			}
			super.update(cell);
		}
	};

	private IServerType currentType;

	public ServerTypeCompositeFragment(Composite parent, IControlHandler handler, boolean isForEditing) {
		super(parent, handler, isForEditing);
		setTitle(PHPServerUIMessages.getString("ServerTypeCompositeFragment.Title")); //$NON-NLS-1$
		setDescription(PHPServerUIMessages.getString("ServerTypeCompositeFragment.Description")); //$NON-NLS-1$
		controlHandler.setTitle(PHPServerUIMessages.getString("ServerTypeCompositeFragment.Title")); //$NON-NLS-1$
		controlHandler.setDescription(getDescription());
		controlHandler.setImageDescriptor(ServersPluginImages.DESC_WIZ_SERVER);
		setDisplayName(PHPServerUIMessages.getString("ServerCompositeFragment.server")); //$NON-NLS-1$
	}

	@Override
	public boolean isComplete() {
		return getType() != null;
	}

	@Override
	public boolean performOk() {
		Server server = getServer();
		server.setAttribute(IServerType.TYPE, currentType.getId());
		return true;
	}

	@Override
	public void setData(Object server) throws IllegalArgumentException {
		if (server != null && !(server instanceof Server)) {
			throw new IllegalArgumentException("The given object is not a Server"); //$NON-NLS-1$
		}
		super.setData(server);
		validate();
	}

	@Override
	public void validate() {
	}

	public Server getServer() {
		return (Server) getData();
	}

	public IServerType getType() {
		return currentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.CompositeFragment#createContents(
	 * org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createContents(Composite parent) {
		Collection<IServerType> types = ServerTypesManager.getInstance().getAll();
		TableViewer viewer = new TableViewer(parent);
		Table table = viewer.getTable();
		table.addListener(SWT.MeasureItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.height = 40;
			}
		});
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object input) {
				if (input instanceof Collection<?>) {
					Collection<?> entries = (Collection<?>) input;
					return entries.toArray(new IServerType[entries.size()]);
				}
				return new IServerType[0];
			}
		});
		viewer.setLabelProvider(new TypesLabelProvider());
		viewer.setInput(types);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				if (!structuredSelection.isEmpty()) {
					currentType = (IServerType) structuredSelection.toArray()[0];
					controlHandler.update();
				}
			}
		});
		validate();
		Dialog.applyDialogFont(parent);
	}

}