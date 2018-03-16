/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.ui.IPageLayout;

public class E4ModelProcessor {

	private static final String ID_EXPLORER = "org.eclipse.php.ui.explorer"; //$NON-NLS-1$

	@Execute
	public void execute(MApplication application, EModelService service) {

		List<MPart> findElements = service.findElements(application, ID_EXPLORER, MPart.class, null);
		for (MPart element : findElements) {
			element.setElementId(IPageLayout.ID_PROJECT_EXPLORER);
		}
	}

}
