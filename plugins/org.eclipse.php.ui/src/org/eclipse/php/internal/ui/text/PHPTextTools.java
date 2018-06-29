/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.text;

import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPTextTools extends ScriptTextTools {

	private static final String[] LEGAL_CONTENT_TYPES = { PHPPartitionTypes.PHP_DEFAULT, PHPPartitionTypes.PHP_DOC,
			PHPPartitionTypes.PHP_MULTI_LINE_COMMENT, PHPPartitionTypes.PHP_QUOTED_STRING,
			PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT, };

	public PHPTextTools(boolean autoDisposeOnDisplayDispose) {
		super(PHPPartitionTypes.PHP_DEFAULT, LEGAL_CONTENT_TYPES, autoDisposeOnDisplayDispose);
	}

	@Override
	public ScriptSourceViewerConfiguration createSourceViewerConfiguraton(IPreferenceStore preferenceStore,
			ITextEditor editor, String partitioning) {
		return null;
	}

	@Override
	public IColorManager getColorManager() {
		return PHPUiPlugin.getDefault().getColorManager();
	}
}
