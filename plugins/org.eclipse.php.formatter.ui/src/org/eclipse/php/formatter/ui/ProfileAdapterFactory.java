/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof IScopeContext) {
			IScopeContext currContext = (IScopeContext) adaptableObject;
			InstanceScope instanceScope = new InstanceScope();
			List<Profile> profiles = null;
			try {
				profiles = ProfileStore.readProfiles(instanceScope);
			} catch (CoreException e) {
				Logger.logException(e);
			}
			if (profiles == null) {
				try {
					profiles = ProfileStore
							.readProfilesFromPreferences(new DefaultScope());
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}

			if (profiles == null)
				profiles = new ArrayList<Profile>();

			return new ProfileManager(profiles, currContext);
		}
		return null;
	}

	public Class[] getAdapterList() {
		// TODO Auto-generated method stub
		return null;
	}

}
