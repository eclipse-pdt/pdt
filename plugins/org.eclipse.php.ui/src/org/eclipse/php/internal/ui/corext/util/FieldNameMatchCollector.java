/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.util;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ui.DLTKUILanguageManager;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.search.FieldNameMatch;
import org.eclipse.php.internal.core.search.FieldNameMatchRequestor;
import org.eclipse.php.internal.core.search.IElementNameMatch;

public class FieldNameMatchCollector extends FieldNameMatchRequestor {

	private final FieldFilter filter = new FieldFilter(DLTKUILanguageManager.getLanguageToolkit(PHPNature.ID));

	private final Collection<IElementNameMatch> fCollection;

	public FieldNameMatchCollector(Collection<IElementNameMatch> collection) {
		Assert.isNotNull(collection);
		fCollection = collection;
	}

	private boolean inScope(FieldNameMatch match) {
		return !filter.isFiltered(match);
	}

	@Override
	public void acceptFieldNameMatch(FieldNameMatch match) {
		if (inScope(match) && match instanceof IElementNameMatch) {
			fCollection.add((IElementNameMatch) match);
		}
	}

}
