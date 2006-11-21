/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData.PHPSuperClassNameData;
import org.eclipse.swt.graphics.Image;

public class SuperClassLabelProvider implements ILabelProvider {
	protected ILabelProvider provider;

	public SuperClassLabelProvider(final ILabelProvider provider) {
		this.provider = provider;
	}

	public void addListener(final ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public Image getImage(final Object element) {
		if (element instanceof PHPSuperClassNameData) {
			final PHPSuperClassNameData superClassNameData = (PHPSuperClassNameData) element;
			final PHPClassData container = (PHPClassData) superClassNameData.getContainer();
			if (container != null) {
				final PHPClassData superClassData = PHPModelUtil.discoverSuperClass(container, superClassNameData.getName());
				if (superClassData != null)
					return provider.getImage(superClassData);
			}
		}
		return null;
	}

	public String getText(final Object element) {
		if (element instanceof PHPSuperClassNameData) {
			final PHPSuperClassNameData superClassNameData = (PHPSuperClassNameData) element;
			final PHPClassData container = (PHPClassData) superClassNameData.getContainer();
			if (container != null) {
				final PHPClassData superClassData = PHPModelUtil.discoverSuperClass(container, superClassNameData.getName());
				if (superClassData != null)
					return "Extends: " + provider.getText(superClassData);
			}
		}
		return null;
	}

	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	public void removeListener(final ILabelProviderListener listener) {
	}
}