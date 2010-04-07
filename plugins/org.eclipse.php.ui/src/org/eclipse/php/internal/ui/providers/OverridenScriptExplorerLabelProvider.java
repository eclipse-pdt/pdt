package org.eclipse.php.internal.ui.providers;

import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;

public class OverridenScriptExplorerLabelProvider extends
		ScriptExplorerLabelProvider {

	public OverridenScriptExplorerLabelProvider(
			ScriptExplorerContentProvider cp, IPreferenceStore store) {
		super(cp, store);
		resetImageLabelProvider();
	}

	private void resetImageLabelProvider() {
		if (fImageLabelProvider != null) {
			fImageLabelProvider.dispose();
			fImageLabelProvider = new PHPElementImageProvider();
		}
	}
}
