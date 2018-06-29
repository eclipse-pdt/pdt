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
package org.eclipse.php.internal.server.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.pathmapper.PathMappingComposite;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author michael
 */
public class PathMapperCompositeFragment extends CompositeFragment {

	private PathMappingComposite pathMapperComposite;

	public PathMapperCompositeFragment(Composite parent, IControlHandler handler, boolean isForEditing) {
		super(parent, handler, isForEditing);
		createDescription();
		if (isForEditing) {
			setData(((ServerEditPage) controlHandler).getServer());
		}
	}

	protected void createDescription() {
		setTitle(Messages.PathMapperCompositeFragment_0);
		controlHandler.setTitle(getTitle());
		setDescription(Messages.PathMapperCompositeFragment_1);
		controlHandler.setDescription(getDescription());
		setImageDescriptor(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_WIZBAN_MAPPING_SERVER));
		controlHandler.setImageDescriptor(getImageDescriptor());
		setDisplayName(Messages.PathMapperCompositeFragment_2);
	}

	/**
	 * Create the page
	 */
	@Override
	protected void createContents(Composite parent) {
		pathMapperComposite = new PathMappingComposite(parent, SWT.NONE);
		GridData data = new GridData(GridData.FILL_BOTH);
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

	@Override
	public void validate() {
		setMessage(getDescription(), IMessageProvider.NONE);
		setComplete(true);
		controlHandler.update();
	}

	@Override
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
	@Override
	public void setData(Object server) {
		if (!(server instanceof Server)) {
			throw new IllegalArgumentException("The given object is not a Server"); //$NON-NLS-1$
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
