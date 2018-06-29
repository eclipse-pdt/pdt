/*******************************************************************************
 * Copyright (c) 2015, 2016 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.navigator;

public class PHPNavigatorLabelProvider extends PHPExplorerLabelProvider {

	public PHPNavigatorLabelProvider() {
		super(new PHPExplorerContentProvider(true) {
			@Override
			protected void init() {
			}
		}, null);
	}
}
