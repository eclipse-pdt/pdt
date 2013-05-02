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
package org.eclipse.php.internal.debug.ui.wizards;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.pathmapper.PathMappingComposite;
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
		controlHandler.setImageDescriptor(PHPDebugUIImages
				.getImageDescriptor(PHPDebugUIImages.IMG_WIZBAN_PHPEXE));
		setDisplayName(Messages.PathMapperCompositeFragment_2);
		setTitle(Messages.PathMapperCompositeFragment_3);
		setDescription(Messages.PathMapperCompositeFragment_4);
		if (isForEditing) {
			setData(((PHPExeEditDialog) controlHandler).getPHPExeItem());
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
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
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
		PHPexeItem phpExeItem = getPHPExeItem();
		if (phpExeItem != null) {
			PathMapper pathMapper = PathMapperRegistry.getByPHPExe(phpExeItem);
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
		PHPexeItem phpExeItem = getPHPExeItem();
		if (phpExeItem != null) {
			PathMapper pathMapper = PathMapperRegistry.getByPHPExe(phpExeItem);
			pathMapper.setMapping(pathMapperComposite.getMappings());
			PathMapperRegistry.storeToPreferences();
		}
		return true;
	}

	public void setData(Object phpExeItem) {
		if (phpExeItem != null && !(phpExeItem instanceof PHPexeItem)) {
			throw new IllegalArgumentException(
					Messages.PathMapperCompositeFragment_5);
		}
		super.setData(phpExeItem);
		init();
		validate();
	}

	public PHPexeItem getPHPExeItem() {
		return (PHPexeItem) getData();
	}
}
