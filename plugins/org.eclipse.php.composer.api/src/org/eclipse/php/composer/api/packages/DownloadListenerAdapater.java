/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.packages;

import java.io.InputStream;

public class DownloadListenerAdapater implements DownloadListenerInterface {

	@Override
	public void aborted(String url) {
	}

	@Override
	public void dataReceived(InputStream content, String url) {
	}

	@Override
	public void errorOccured(Exception e) {
	}

}
