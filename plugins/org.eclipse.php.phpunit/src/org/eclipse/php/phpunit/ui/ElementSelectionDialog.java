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
package org.eclipse.php.phpunit.ui;

import java.util.Arrays;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.TwoPaneElementSelector;

/**
 * Dialog to select a class from a list of classes.
 */
public class ElementSelectionDialog extends TwoPaneElementSelector {

	private final IType[] fTypes;

	private static class PackageRenderer extends ModelElementLabelProvider {
		public PackageRenderer() {
			super(ModelElementLabelProvider.SHOW_PARAMETERS | ModelElementLabelProvider.SHOW_POST_QUALIFIED
					| ModelElementLabelProvider.SHOW_ROOT);
		}

		@Override
		public Image getImage(Object element) {
			return super.getImage(((IType) element).getScriptFolder());
		}

		@Override
		public String getText(Object element) {
			return super.getText(((IType) element).getScriptFolder());
		}
	}

	public ElementSelectionDialog(Shell shell, IType[] types) {
		super(shell,
				new ModelElementLabelProvider(
						ModelElementLabelProvider.SHOW_BASICS | ModelElementLabelProvider.SHOW_OVERLAY_ICONS),
				new PackageRenderer());
		fTypes = types;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
	}

	@Override
	public int open() {
		setElements(fTypes);
		return super.open();
	}

	@Override
	protected void computeResult() {
		getSelectedElements();
		Object[] results = getSelectedElements();
		setResult(Arrays.asList(results));
	}
}
