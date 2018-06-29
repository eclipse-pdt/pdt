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
package org.eclipse.php.internal.ui.editor.adapter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.search.DLTKSearchPageScoreComputer;
import org.eclipse.php.internal.core.documentModel.dom.IImplForPHP;
import org.eclipse.php.internal.ui.actions.filters.GenericActionFilter;
import org.eclipse.search.ui.ISearchPageScoreComputer;
import org.eclipse.ui.IActionFilter;

/**
 * This adapter factory class is used to create a GenericActionFilter when
 * performing a right-click within the editor
 * 
 * @author yaronm
 */
public class PHPElementAdapterFactory implements IAdapterFactory {

	private static Map<Class<?>, Object> adapterType2Object = new HashMap<>(4);

	static {
		adapterType2Object.put(IActionFilter.class, new GenericActionFilter());
		adapterType2Object.put(ISearchPageScoreComputer.class, new DLTKSearchPageScoreComputer());
	}

	public PHPElementAdapterFactory() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof IImplForPHP) {
			if (adapterType == IModelElement.class) {
				return ((IImplForPHP) adaptableObject).getModelElement();
			}
			// commenting the next block of code fixes these bugs
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=257421
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=257681
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=271734

			// if (adapterType == IResource.class) {
			// IModelElement modelElement = ((IImplForPhp)
			// adaptableObject).getModelElement();
			// if (modelElement != null) {
			// return modelElement.getResource();
			// }
			// }
		}
		return adapterType2Object.get(adapterType);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class[] getAdapterList() {
		Class[] classArray = new Class[adapterType2Object.size()];
		adapterType2Object.entrySet().toArray(classArray);
		return classArray;
	}
}
