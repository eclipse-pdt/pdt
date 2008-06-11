package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.dltk.ui.preferences.BuildPathsPropertyPage;
import org.eclipse.dltk.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class BuildpathProperties extends BuildPathsPropertyPage implements IWorkbenchPropertyPage {

	public BuildpathProperties() {
	}

	@Override
	protected BuildpathsBlock createBuildPathBlock(IWorkbenchPreferenceContainer pageContainer) {
		return new PHPBuildPathsBlock(new BusyIndicatorRunnableContext(), this, getSettings().getInt(INDEX), false, pageContainer);
	}
}
