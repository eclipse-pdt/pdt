package org.eclipse.php.internal.ui.text;

import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPTextTools extends ScriptTextTools {
	
	private static final String[] LEGAL_CONTENT_TYPES = {
		PHPPartitionTypes.PHP_DEFAULT,
		PHPPartitionTypes.PHP_DOC,
		PHPPartitionTypes.PHP_MULTI_LINE_COMMENT,
		PHPPartitionTypes.PHP_QUOTED_STRING,
		PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT,
	};
	
	public PHPTextTools(boolean autoDisposeOnDisplayDispose) {
		super(PHPPartitionTypes.PHP_DEFAULT, LEGAL_CONTENT_TYPES, autoDisposeOnDisplayDispose);
	}

	public ScriptSourceViewerConfiguration createSourceViewerConfiguraton(IPreferenceStore preferenceStore, ITextEditor editor, String partitioning) {
		return null;
	}
	
	public IColorManager getColorManager() {
		return PHPUiPlugin.getDefault().getColorManager();
	}
}
