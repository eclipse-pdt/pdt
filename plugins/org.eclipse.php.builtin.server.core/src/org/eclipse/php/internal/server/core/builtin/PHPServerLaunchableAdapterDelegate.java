/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software, Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Kaloyan Raev - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.server.core.IModuleArtifact;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.model.IURLProvider;
import org.eclipse.wst.server.core.model.LaunchableAdapterDelegate;
import org.eclipse.wst.server.core.util.HttpLaunchable;
import org.eclipse.wst.server.core.util.WebResource;

public class PHPServerLaunchableAdapterDelegate extends LaunchableAdapterDelegate {

	@Override
	public Object getLaunchable(IServer server, IModuleArtifact moduleArtifact) throws CoreException {
		if (server.getAdapter(PHPServer.class) == null) {
			return null;
		}
		if (!(moduleArtifact instanceof WebResource)) {
			return null;
		}

		HttpLaunchable launchable = null;
		try {
			URL url = ((IURLProvider) server.loadAdapter(IURLProvider.class, null))
					.getModuleRootURL(moduleArtifact.getModule());

			if (moduleArtifact instanceof WebResource) {
				WebResource resource = (WebResource) moduleArtifact;
				String path = resource.getPath().toString();
				// append path to the root url
				url = new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + path);
			}
			launchable = new HttpLaunchable(url);
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error getting URL for " + moduleArtifact, e); //$NON-NLS-1$
		}

		return launchable;
	}

}
