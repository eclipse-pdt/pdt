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
package org.eclipse.php.composer.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.*;
import org.eclipse.php.composer.api.MinimalPackage;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.graphics.Image;

public class PackageController extends StyledCellLabelProvider
		implements IStructuredContentProvider, ICheckStateProvider, ICheckStateListener {

	private List<MinimalPackage> packages;
	private List<String> checked = new ArrayList<String>();
	protected static Image pkgImage = ComposerUIPluginImages.PACKAGE.createImage();
	protected static Image phpImage = ComposerUIPluginImages.PHP.createImage();
	protected List<IPackageCheckStateChangedListener> pkgListeners = new ArrayList<IPackageCheckStateChangedListener>();

	public void addPackageCheckStateChangedListener(IPackageCheckStateChangedListener listener) {
		if (!pkgListeners.contains(listener)) {
			pkgListeners.add(listener);
		}
	}

	public void removePackageCheckStateChangedListener(IPackageCheckStateChangedListener listener) {
		pkgListeners.remove(listener);
	}

	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput == null) {
			packages = null;
		} else {
			packages = (List<MinimalPackage>) newInput;
		}
	}

	public Object[] getElements(Object inputElement) {
		if (packages != null) {
			return packages.toArray();
		} else {
			return null;
		}
	}

	public void addPackages(List<MinimalPackage> packages) {
		this.packages.addAll(packages);
	}

	public Image getImage(Object element) {
		return PackageController.getPackageImage(element);
	}

	public static Image getPackageImage(Object element) {
		String name = PackageController.getPackageName(element);
		if (!name.contains("/") && (name.equals("php") || name.startsWith("ext-"))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return phpImage;
		}
		return pkgImage;
	}

	public String getText(Object element) {
		return getName(element);
	}

	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		setChecked(((MinimalPackage) event.getElement()).getName(), event.getChecked());
	}

	public List<String> getCheckedPackages() {
		return checked;
	}

	public int getCheckedPackagesCount() {
		return checked.size();
	}

	public void clear() {
		checked.clear();
	}

	public void setChecked(String name, boolean checked) {
		if (checked && !this.checked.contains(name)) {
			this.checked.add(name);
		}

		if (!checked) {
			this.checked.remove(name);
		}

		for (IPackageCheckStateChangedListener listener : pkgListeners) {
			listener.packageCheckStateChanged(name, checked);
		}
	}

	private String getName(Object element) {
		return PackageController.getPackageName(element);
	}

	public static String getPackageName(Object element) {
		String name = null;
		if (element instanceof MinimalPackage) {
			name = ((MinimalPackage) element).getName();
		} else if (element instanceof String) {
			name = (String) element;
		}
		return name;
	}

	public void update(ViewerCell cell) {
		Object obj = cell.getElement();

		if (obj instanceof MinimalPackage) {
			MinimalPackage pkg = (MinimalPackage) obj;

			StyledString styledString = new StyledString();

			// if (author.getEmail() != null && author.getEmail().trim() != ""
			// && !author.getEmail().trim().equals("")) {
			// styledString.append(" <" + author.getEmail().trim() + ">",
			// StyledString.COUNTER_STYLER);
			// }
			//
			// if (author.getHomepage() != null && author.getHomepage().trim()
			// != "" && !author.getHomepage().trim().equals("")) {
			// styledString.append(" - " + author.getHomepage().trim(),
			// StyledString.DECORATIONS_STYLER);
			// }
			updateText(pkg, styledString);

			cell.setText(styledString.toString());
			cell.setStyleRanges(styledString.getStyleRanges());

			cell.setImage(getImage(pkg));

			super.update(cell);
		}
	}

	protected void updateText(MinimalPackage pkg, StyledString styledString) {
		styledString.append(getName(pkg));
	}

	@Override
	public boolean isChecked(Object element) {
		return checked.contains(getName(element));
	}

	@Override
	public boolean isGrayed(Object element) {
		// TODO Auto-generated method stub
		return false;
	}
}
