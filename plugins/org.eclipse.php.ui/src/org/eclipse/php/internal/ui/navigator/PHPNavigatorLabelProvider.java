/*******************************************************************************
 * Copyright (c) 2015, 2016 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.navigator;

import org.eclipse.php.internal.ui.explorer.PHPExplorerContentProvider;
import org.eclipse.php.internal.ui.explorer.PHPExplorerLabelProvider;

public class PHPNavigatorLabelProvider extends PHPExplorerLabelProvider {

	public PHPNavigatorLabelProvider() {
		super(new PHPExplorerContentProvider(true) {
			@Override
			protected void init() {
			}
		}, null);
	}
}
