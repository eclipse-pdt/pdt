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
package org.eclipse.php.internal.server.core.builtin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.model.PublishOperation;
import org.eclipse.wst.server.core.model.PublishTaskDelegate;

@SuppressWarnings("rawtypes")
public class PublishTask extends PublishTaskDelegate {

	@Override
	public PublishOperation[] getTasks(IServer server, int kind, List modules, List kindList) {
		if (modules == null)
			return null;

		PHPServerBehaviour phpServer = (PHPServerBehaviour) server.loadAdapter(PHPServerBehaviour.class, null);

		List<PublishOperation> tasks = new ArrayList<PublishOperation>();
		int size = modules.size();
		for (int i = 0; i < size; i++) {
			IModule[] module = (IModule[]) modules.get(i);
			Integer in = (Integer) kindList.get(i);
			tasks.add(new PublishOperation2(phpServer, kind, module, in.intValue()));
		}

		return tasks.toArray(new PublishOperation[tasks.size()]);
	}

}
