/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData.PHPSuperClassNameData;

public class SuperClassTreeContentProvider implements ITreeContentProvider {
	ITreeContentProvider provider;

	public SuperClassTreeContentProvider(final ITreeContentProvider provider) {
		this.provider = provider;
	}

	public void dispose() {
	}

	public Object[] getChildren(final Object object) {
		if (object instanceof PHPClassData) {
			final PHPClassData classData = (PHPClassData) object;
			final PHPSuperClassNameData superClassNameData = classData.getSuperClassData();
			if (superClassNameData != null && PHPModelUtil.discoverSuperClass(classData, superClassNameData.getName()) != null)
				return new Object[] { superClassNameData };
		}
		if (object instanceof PHPSuperClassNameData) {
			final PHPSuperClassNameData superClassNameData = (PHPSuperClassNameData) object;
			final PHPClassData classData = (PHPClassData) superClassNameData.getContainer();
			if (classData != null) {
				final PHPClassData superClassData = PHPModelUtil.discoverSuperClass(classData, superClassNameData.getName());
				if (superClassData != null)
					return provider.getChildren(superClassData);
			}
		}
		return new Object[0];
	}

	public Object[] getElements(final Object inputElement) {
		return null;
	}

	public Object getParent(final Object element) {
		if (element instanceof PHPSuperClassNameData)
			return ((PHPSuperClassNameData) element).getContainer();
		return null;
	}

	public boolean hasChildren(final Object element) {
		return getChildren(element).length > 0;
	}

	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	}
}