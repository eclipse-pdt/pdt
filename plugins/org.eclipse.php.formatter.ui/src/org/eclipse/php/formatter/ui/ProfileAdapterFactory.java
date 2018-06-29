/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.formatter.ui.preferences.ProfileManager;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.Profile;
import org.eclipse.php.formatter.ui.preferences.ProfileStore;

public class ProfileAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof IScopeContext) {
			IScopeContext currContext = (IScopeContext) adaptableObject;
			IScopeContext instanceScope = InstanceScope.INSTANCE;
			List<Profile> profiles = null;
			try {
				profiles = ProfileStore.readProfiles(instanceScope);
			} catch (CoreException e) {
				Logger.logException(e);
			}
			if (profiles == null) {
				try {
					profiles = ProfileStore.readProfilesFromPreferences(DefaultScope.INSTANCE);
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}

			if (profiles == null) {
				profiles = new ArrayList<>();
			}

			return (T) (new ProfileManager(profiles, currContext));
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return null;
	}

}
