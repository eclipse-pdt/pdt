/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData.PHPInterfaceNameData;
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
			
			List children = new LinkedList();
			
			final PHPSuperClassNameData superClassNameData = classData.getSuperClassData();
			if (superClassNameData != null && PHPModelUtil.discoverSuperClass(classData, superClassNameData.getName()) != null) {
				children.add(superClassNameData);
			}
			
			final PHPInterfaceNameData[] interfaceNameData = classData.getInterfacesNamesData();
			if (interfaceNameData != null && interfaceNameData.length > 0) {
				for (int i = 0; i < interfaceNameData.length; ++i) {
					if (PHPModelUtil.discoverInterface(classData, interfaceNameData[i].getName()) != null) {
						children.add(interfaceNameData[i]);
					}
				}
			}
			return children.toArray();
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
		if (object instanceof PHPInterfaceNameData) {
			final PHPInterfaceNameData interfaceNameData = (PHPInterfaceNameData) object;
			final PHPClassData classData = (PHPClassData) interfaceNameData.getContainer();
			if (classData != null) {
				final PHPClassData interfaceData = PHPModelUtil.discoverInterface(classData, interfaceNameData.getName());
				if (interfaceData != null)
					return provider.getChildren(interfaceData);
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
		if (element instanceof PHPInterfaceNameData)
			return ((PHPInterfaceNameData) element).getContainer();
		return null;
	}

	public boolean hasChildren(final Object element) {
		return getChildren(element).length > 0;
	}

	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	}
}