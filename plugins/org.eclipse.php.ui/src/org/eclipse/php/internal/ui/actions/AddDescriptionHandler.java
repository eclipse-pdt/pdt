/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.dltk.core.IModelElement;

/**
 * Handler class for the add description action, It acts as a delegate to the
 * AddDescription action
 * 
 * @author Roy, 2008
 */
public class AddDescriptionHandler extends SelectionHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		final IModelElement element = getCurrentModelElement(event);
		if (element != null) {
			final AddDescriptionAction addDescriptionAction = new AddDescriptionAction();
			addDescriptionAction
					.setModelElement(new IModelElement[] { element });
			addDescriptionAction.run(null);
		}

		return null;
	}
}
