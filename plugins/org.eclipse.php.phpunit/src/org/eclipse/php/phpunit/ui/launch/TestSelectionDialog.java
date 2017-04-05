/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.TwoPaneElementSelector;

/**
 * A dialog to select a test class or a test suite from a list of types.
 */
public class TestSelectionDialog extends TwoPaneElementSelector {

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

	public TestSelectionDialog(Shell shell, IType[] types) {
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

}
