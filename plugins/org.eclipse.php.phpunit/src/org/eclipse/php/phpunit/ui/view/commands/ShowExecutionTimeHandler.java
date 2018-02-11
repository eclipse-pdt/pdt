/*******************************************************************************
 * Copyright (c) 2018 Michał Niewrzał and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Michał Niewrzał - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.ui.preference.PHPUnitPreferenceKeys;
import org.eclipse.ui.handlers.HandlerUtil;

public class ShowExecutionTimeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Command command = event.getCommand();
		boolean oldValue = HandlerUtil.toggleCommandState(command);

		PHPUnitPlugin.getDefault().getPreferenceStore().setValue(PHPUnitPreferenceKeys.SHOW_EXECUTION_TIME, !oldValue);
		return null;
	}

}
