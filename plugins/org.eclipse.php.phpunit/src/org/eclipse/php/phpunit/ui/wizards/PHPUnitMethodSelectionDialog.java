/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.wizards;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.TwoPaneElementSelector;

public class PHPUnitMethodSelectionDialog extends TwoPaneElementSelector {

	public PHPUnitMethodSelectionDialog(Shell shell, IMethod[] types) {
		super(shell,
				new ModelElementLabelProvider(
						ModelElementLabelProvider.SHOW_BASICS | ModelElementLabelProvider.SHOW_OVERLAY_ICONS),
				new PackageRenderer());
		fMethods = types;
	}

	private final IMethod[] fMethods;

	private static class PackageRenderer extends ModelElementLabelProvider {
		public PackageRenderer() {
			super(ModelElementLabelProvider.SHOW_PARAMETERS | ModelElementLabelProvider.SHOW_POST_QUALIFIED
					| ModelElementLabelProvider.SHOW_ROOT);
		}

		@Override
		public Image getImage(Object element) {
			return super.getImage(((IMethod) element).getParent());
		}

		@Override
		public String getText(Object element) {
			return super.getText(((IMethod) element).getParent());
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
	}

	@Override
	public int open() {
		setElements(fMethods);
		return super.open();
	}
}
