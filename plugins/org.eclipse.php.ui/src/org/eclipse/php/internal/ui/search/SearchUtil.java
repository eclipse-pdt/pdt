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
package org.eclipse.php.internal.ui.search;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.php.internal.core.search.Messages;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IWorkingSet;
import org.osgi.framework.Bundle;

/**
 * This class contains some utility methods for the PHP Search.
 */
public class SearchUtil {

	public static boolean isSearchPlugInActivated() {
		return Platform.getBundle("org.eclipse.search").getState() == Bundle.ACTIVE; //$NON-NLS-1$
	}

	/**
	 * This helper method with Object as parameter is needed to prevent the
	 * loading of the Search plug-in: the VM verifies the method call and hence
	 * loads the types used in the method signature, eventually triggering the
	 * loading of a plug-in (in this case ISearchQuery results in Search plug-in
	 * being loaded).
	 */
	public static void runQueryInBackground(Object query) {
		NewSearchUI.runQueryInBackground((ISearchQuery) query);
	}

	/**
	 * This helper method with Object as parameter is needed to prevent the
	 * loading of the Search plug-in: the VM verifies the method call and hence
	 * loads the types used in the method signature, eventually triggering the
	 * loading of a plug-in (in this case ISearchQuery results in Search plug-in
	 * being loaded).
	 */
	public static IStatus runQueryInForeground(IRunnableContext context,
			Object query) {
		return NewSearchUI.runQueryInForeground(context, (ISearchQuery) query);
	}

	public static String toString(IWorkingSet[] workingSets) {
		Arrays.sort(workingSets, new WorkingSetComparator());
		String result = ""; //$NON-NLS-1$
		if (workingSets != null && workingSets.length > 0) {
			boolean firstFound = false;
			for (int i = 0; i < workingSets.length; i++) {
				String workingSetName = workingSets[i].getName();
				if (firstFound)
					result = Messages.format(
							PHPUIMessages.SearchUtil_workingSetConcatenation,
							new String[] { result, workingSetName }); 
				else {
					result = workingSetName;
					firstFound = true;
				}
			}
		}
		return result;
	}

}
