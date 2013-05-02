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
package org.eclipse.php.internal.server.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping;
import org.eclipse.php.internal.debug.ui.pathmapper.PathMappingComposite;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author michael
 */
public class PathMapperCompositeFragment extends CompositeFragment {

	private PathMappingComposite pathMapperComposite;

	public PathMapperCompositeFragment(Composite parent,
			IControlHandler handler, boolean isForEditing) {
		super(parent, handler, isForEditing);
		controlHandler.setTitle(Messages.PathMapperCompositeFragment_0);
		controlHandler
				.setDescription(Messages.PathMapperCompositeFragment_1);
		controlHandler.setImageDescriptor(ServersPluginImages.DESC_WIZ_SERVER);
		setDisplayName(Messages.PathMapperCompositeFragment_2);
		setTitle(Messages.PathMapperCompositeFragment_3);
		setDescription(Messages.PathMapperCompositeFragment_4);
		if (isForEditing) {
			setData(((ServerEditDialog) controlHandler).getServer());
		}
		createControl(isForEditing);
	}

	/**
	 * Create the page
	 */
	protected void createControl(boolean isForEditing) {
		// set layout for this composite (whole page)
		GridLayout pageLayout = new GridLayout();
		setLayout(pageLayout);

		Composite composite = new Composite(this, SWT.NONE);
		pageLayout.numColumns = 1;
		composite.setLayout(pageLayout);
		GridData data = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(data);

		pathMapperComposite = new PathMappingComposite(composite, SWT.NONE);
		data = new GridData(GridData.FILL_BOTH);
		pathMapperComposite.setLayoutData(data);

		Dialog.applyDialogFont(this);

		init();
		validate();
	}

	protected void init() {
		if (pathMapperComposite == null || pathMapperComposite.isDisposed()) {
			return;
		}
		Server server = getServer();
		if (server != null) {
			PathMapper pathMapper = PathMapperRegistry.getByServer(server);
			if (pathMapper != null) {
				pathMapperComposite.setData(pathMapper.getMapping());
			}
		}
	}

	protected void validate() {
		setMessage(getDescription(), IMessageProvider.NONE);
		setComplete(true);

		controlHandler.update();
	}

	protected void setMessage(String message, int type) {
		controlHandler.setMessage(message, type);
		setComplete(type != IMessageProvider.ERROR);
		controlHandler.update();
	}

	public boolean performOk() {
		Server server = getServer();
		if (server != null) {
			PathMapper pathMapper = PathMapperRegistry.getByServer(server);
			pathMapper.setMapping(pathMapperComposite.getMappings());
			PathMapperRegistry.storeToPreferences();
		}
		return true;
	}

	/**
	 * Override the super setData to handle only Server types.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given object is not a {@link Server}
	 */
	public void setData(Object server) {
		if (!(server instanceof Server)) {
			throw new IllegalArgumentException(
					"The given object is not a Server"); //$NON-NLS-1$
		}
		super.setData(server);
		init();
		validate();
	}

	public Mapping[] getMappings() {
		return pathMapperComposite.getMappings();
	}

	/**
	 * Returns the Server that is attached to this fragment.
	 * 
	 * @return The attached Server.
	 */
	public Server getServer() {
		return (Server) getData();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		pathMapperComposite.setEnabled(enabled);
	}
}
