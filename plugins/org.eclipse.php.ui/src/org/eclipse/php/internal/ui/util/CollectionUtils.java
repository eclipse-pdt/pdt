/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.util.*;

import org.eclipse.core.resources.IResource;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;

public class CollectionUtils {

	public static IResource[] setMinus(IResource[] setToRemoveFrom, IResource[] elementsToRemove) {
		Set setMinus = new HashSet(setToRemoveFrom.length - setToRemoveFrom.length);
		setMinus.addAll(Arrays.asList(setToRemoveFrom));
		setMinus.removeAll(Arrays.asList(elementsToRemove));
		return (IResource[]) setMinus.toArray(new IResource[setMinus.size()]);
	}

	public static PHPCodeData[] setMinus(PHPCodeData[] setToRemoveFrom, PHPCodeData[] elementsToRemove) {
		Set setMinus = new HashSet(setToRemoveFrom.length - setToRemoveFrom.length);
		setMinus.addAll(Arrays.asList(setToRemoveFrom));
		setMinus.removeAll(Arrays.asList(elementsToRemove));
		return (PHPCodeData[]) setMinus.toArray(new PHPCodeData[setMinus.size()]);
	}

	public static IResource[] getNotNulls(IResource[] resources) {
		Collection result = new ArrayList(resources.length);
		for (int i = 0; i < resources.length; i++) {
			IResource resource = resources[i];
			if (resource != null && !result.contains(resource))
				result.add(resource);
		}
		return (IResource[]) result.toArray(new IResource[result.size()]);
	}

	public static IResource[] getResources(PHPCodeData[] elements) {
		IResource[] result = new IResource[elements.length];
		for (int i = 0; i < elements.length; i++) {
			result[i] = PHPModelUtil.getResource(elements[i]);
		}
		return result;
	}

}
