package org.eclipse.php.ui.preferences.ui;

import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.internal.ui.preferences.PHPCoreOptionsConfigurationBlock;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.ui.preferences.IStatusChangeListener;
import org.eclipse.php.ui.util.ScrolledPageContent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;


public class LocaleConfigurationBlock extends PHPCoreOptionsConfigurationBlock {
	
	private static final Key PREF_LOCALE = getPHPCoreKey(PHPCoreConstants.WORKSPACE_LOCALE);
	
	private PixelConverter fPixelConverter;
	private Combo localeCombo;	
	
	
	private String[] locales =  new String[] { "en_US", "it_IT" };
	private String[] localesLabels = new String[] { Locale.ENGLISH.getDisplayLanguage(), Locale.ITALIAN.getDisplayLanguage() };
	


	public LocaleConfigurationBlock(IStatusChangeListener context, IProject project, IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);		
	}

	private static Key[] getKeys() {
		return new Key[] {
			PREF_LOCALE
		};
	}

	protected void prepareForBuild() {
		
	}

	protected Control createContents(Composite parent) {		
		
		fPixelConverter = new PixelConverter(parent);
		setShell(parent.getShell());

		Composite mainComp = new Composite(parent, SWT.NONE);
		mainComp.setFont(parent.getFont());
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		mainComp.setLayout(layout);

		Composite commonComposite = createStyleTabContent(mainComp);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = fPixelConverter.convertHeightInCharsToPixels(20);
		commonComposite.setLayoutData(gridData);

		validateSettings(null, null, null);

		return mainComp;
	}
	
	private Composite createStyleTabContent(Composite folder) {

	
		int nColumns = 3;

		final ScrolledPageContent sc1 = new ScrolledPageContent(folder);

		Composite composite = sc1.getBody();
		GridLayout layout = new GridLayout(nColumns, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
				
		localeCombo = addComboBox(composite, PHPUIMessages.getString("LanguageSetting_combobox_label"), PREF_LOCALE, locales, localesLabels);

		return sc1;
	}
	
	public String getLocaleComboValue(){
		return locales[localeCombo.getSelectionIndex()];
	}



	protected void validateSettings(Key changedKey, String oldValue, String newValue) {
		// TODO Auto-generated method stub
	}

	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {		
		return null;
	}



}
