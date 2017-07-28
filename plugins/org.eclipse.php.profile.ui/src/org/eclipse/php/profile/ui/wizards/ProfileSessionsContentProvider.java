/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.wizards;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.profile.core.engine.ProfilerDB;

/**
 * Profile session content provider.
 */
class ProfileSessionsContentProvider implements IStructuredContentProvider {
	private final Object[] EMPTY_SET = {};

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ProfilerDB[]) {
			return (ProfilerDB[]) inputElement;
		}
		return EMPTY_SET;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}