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
package org.eclipse.php.internal.ui;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.AbstractDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.ui.PHPElementLabels;

/**
 * An implementation of IDLTKUILanguageToolkit for PHP
 */
public class PHPUILanguageToolkit extends AbstractDLTKUILanguageToolkit {

	private static PHPUILanguageToolkit sToolkit = new PHPUILanguageToolkit();

	public static IDLTKUILanguageToolkit getInstance() {
		return sToolkit;
	}

	public IDLTKLanguageToolkit getCoreToolkit() {
		return PHPLanguageToolkit.getDefault();
	}

	public IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	public String getPartitioningId() {
		return PHPPartitionTypes.PHP_DEFAULT;
	}

	public String getEditorId(Object inputElement) {
		return "org.eclipse.php.editor"; //$NON-NLS-1$
	}

	public ScriptElementLabels getScriptElementLabels() {
		return new PHPElementLabels();
	}

}