/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

import org.eclipse.dltk.core.search.indexing.IndexManager;
import org.eclipse.dltk.internal.core.ModelManager;

public class PHPBuildUtils {

	public static boolean isIndexing() {
		IndexManager indexManager = ModelManager.getModelManager().getIndexManager();
		// only one job can awaiting e.g. after file save (file will be indexed)
		return indexManager.awaitingJobsCount() > 1;
	}
}
