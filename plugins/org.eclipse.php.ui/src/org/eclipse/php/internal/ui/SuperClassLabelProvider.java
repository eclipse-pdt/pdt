/**
 * Copyright (c) 2006 Zend Technologies
 *
 */
package org.eclipse.php.internal.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPModifier;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData.PHPInterfaceNameData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData.PHPSuperClassNameData;
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
		} else if (element instanceof PHPInterfaceNameData) {
			final PHPInterfaceNameData interfaceNameData = (PHPInterfaceNameData) element;
			final PHPClassData container = (PHPClassData) interfaceNameData.getContainer();
			if (container != null) {
				final PHPClassData interfaceData = PHPModelUtil.discoverInterface(container, interfaceNameData.getName());
				if (interfaceData != null)
					return provider.getImage(interfaceData);
			}
		}
		return null;
	}

	public String getText(final Object element) {
		if (element instanceof PHPSuperClassNameData || element instanceof PHPInterfaceNameData && (((PHPClassData)((PHPInterfaceNameData)element).getContainer()).getModifiers() & PHPModifier.INTERFACE) > 0) {
			final PHPCodeData superClassNameData = (PHPCodeData) element;
			final PHPClassData container = (PHPClassData) superClassNameData.getContainer();
			if (container != null) {
				final PHPClassData superClassData;
				if(superClassNameData instanceof PHPSuperClassNameData) {
					superClassData = PHPModelUtil.discoverSuperClass(container, superClassNameData.getName());
				} else {
					superClassData = PHPModelUtil.discoverInterface(container, superClassNameData.getName());
				}
				if (superClassData != null)
					return "Extends: " + provider.getText(superClassData); //$NON-NLS-1$
			}
		} else if (element instanceof PHPInterfaceNameData) {
			final PHPInterfaceNameData interfaceNameData = (PHPInterfaceNameData) element;
			final PHPClassData container = (PHPClassData) interfaceNameData.getContainer();
			if (container != null) {
				final PHPClassData interfaceData = PHPModelUtil.discoverInterface(container, interfaceNameData.getName());
				if (interfaceData != null)
					return "Implements: " + provider.getText(interfaceData); //$NON-NLS-1$
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