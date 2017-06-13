/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.util;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.search.MethodNameMatch;
import org.eclipse.dltk.core.search.MethodNameMatchRequestor;
import org.eclipse.dltk.internal.corext.util.MethodFilter;
import org.eclipse.dltk.ui.DLTKUILanguageManager;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.search.IElementNameMatch;

public class MethodNameMatchCollector extends MethodNameMatchRequestor {

	private final MethodFilter filter = new MethodFilter(DLTKUILanguageManager.getLanguageToolkit(PHPNature.ID));

	private final Collection<IElementNameMatch> fCollection;

	public MethodNameMatchCollector(Collection<IElementNameMatch> collection) {
		Assert.isNotNull(collection);
		fCollection = collection;
	}

	private boolean inScope(MethodNameMatch match) {
		return !filter.isFiltered(match);
	}

	@Override
	public void acceptMethodNameMatch(MethodNameMatch match) {
		if (inScope(match) && match instanceof IElementNameMatch) {
			fCollection.add((IElementNameMatch) match);
		}
	}

}
