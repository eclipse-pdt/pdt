/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui.tests;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * PHP editor simplified substitution for test purposes with reduced set of
 * features that are not required to perform UI tests.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPTestEditor extends PHPStructuredEditor {

	public static final String ID = "org.eclipse.php.test.editor";

	@Override
	protected void selectionChanged() {
		// No need to react
	}

	@Override
	protected void installOverrideIndicator(boolean provideAST) {
		// Do not install
	}

	@Override
	protected void installOccurrencesFinder(boolean forceUpdate) {
		// Do not install
	}

	@Override
	protected void updateOccurrenceAnnotations(ITextSelection selection, IModelElement sourceModule) {
		// skip
	}

	@Override
	protected void createActions() {
		// Do not create
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class required) {
		// Do not connect with outline page
		if (required == IContentOutlinePage.class) {
			return null;
		}
		return super.getAdapter(required);
	}

}
