package org.eclipse.php.ui.preferences.ui;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.Logger;
import org.eclipse.php.core.util.FileUtils;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.PropertyAndPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * Page used to configure both workspace and project specific compiler settings
 */
public class LocalePreferencePage extends PropertyAndPreferencePage {

	public static final String PREF_ID = "org.eclipse.php.ui.preferences.LocalePreferencePage"; //$NON-NLS-1$
	

	public static final String DATA_SELECT_OPTION_KEY = "select_option_key"; //$NON-NLS-1$
	public static final String DATA_SELECT_OPTION_QUALIFIER = "select_option_qualifier"; //$NON-NLS-1$

	private LocaleConfigurationBlock fConfigurationBlock;
	
	public LocalePreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());

		// only used when page is shown programatically
		setTitle(PHPUIMessages.getString("LanguageSetting_preferences_title"));
				
	}

	/*
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		fConfigurationBlock = new LocaleConfigurationBlock(getNewStatusChangedListener(), getProject(), container);
			
		super.createControl(parent);
		// TODO - set help

	}

	protected Control createPreferenceContent(Composite composite) {
		return fConfigurationBlock.createContents(composite);
	}

	protected boolean hasProjectSpecificOptions(IProject project) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#getPreferencePageID()
	 */
	protected String getPreferencePageID() {
		return PREF_ID;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#getPropertyPageID()
	 */
	protected String getPropertyPageID() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	public void dispose() {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.dispose();
		}
		super.dispose();
	}

	
	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		super.performDefaults();
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performDefaults();
		}
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {	
		if (fConfigurationBlock != null && !fConfigurationBlock.performOk()) {		
			return false;
		}
		
		if (fConfigurationBlock.hasChanges){				
			String selection = fConfigurationBlock.getLocaleComboValue();			
			// write the new selected local in the config file
			if (addLocaleToConfigFile(selection)){
				MessageDialog.openInformation(getShell(), PHPUIMessages.getString("LanguageSetting_restartwindow_title"),PHPUIMessages.getString("LanguageSetting_restartwindow_message"));				
			}
		}
		
		return super.performOk();
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performApply()
	 */
	public void performApply() {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performApply();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#applyData(java.lang.Object)
	 */
	public void applyData(Object data) {
		super.applyData(data);
		if (data instanceof Map && fConfigurationBlock != null) {
			Map map = (Map) data;
			Object key = map.get(DATA_SELECT_OPTION_KEY);
			Object qualifier = map.get(DATA_SELECT_OPTION_QUALIFIER);
			if (key instanceof String && qualifier instanceof String) {
				fConfigurationBlock.selectOption((String) key, (String) qualifier);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	public void setElement(IAdaptable element) {
		super.setElement(element);
		setDescription(null); // no description for property page
	}
	
	
	public String findConfigFile(){
		String filePath = Platform.getInstallLocation().getURL().getFile();
		File dir = new File(filePath);
		
		String configFileName = null;
		
		if (dir.isDirectory()) {
            String[] files = dir.list();
            for (int i=0; i<files.length; i++) {
                if (files[i].indexOf(".ini") != -1){
                	configFileName = files[i];
                	break;
                }
            }
        }
		
		String fullPath = null;
		if (configFileName != null){
			fullPath = filePath + configFileName;
		}
			
		return fullPath;

	}
	
	public boolean addLocaleToConfigFile(String locale){

		String configFilePath = findConfigFile();
		 
		if (configFilePath == null) {
			Logger.log(Logger.ERROR,PHPUIMessages.getString("LanguageSetting_config_file_error"));
	    	return false;
		}
				
	    File configFile = new File(configFilePath);
	    
	    try {
	    	String fileContents = FileUtils.getContents(configFile);
	    	
	    	// in case nl already exists in the config file, remove it (before the new addition)
	    	if ((fileContents.indexOf("-nl")) != -1){
	    		fileContents = fileContents.replaceAll("-nl[\n\r]*.*##[\n\r]*", "");
	    	}
	    	
	    	StringBuffer buffer = new StringBuffer();
		    buffer.append("-nl");
		    buffer.append(System.getProperty("line.separator"));
		    buffer.append(locale);
		    buffer.append("##"); // add delimiter in order to enable replacement
		    buffer.append(System.getProperty("line.separator"));
	    	buffer.append(fileContents);
	    	FileUtils.setContents(configFile, buffer.toString());
	    } catch (Exception ex){
	    	Logger.logException(PHPUIMessages.getString("LanguageSetting_change_locale_error"), ex);
	    	return false;
	    }
	    
	    return true;
	}
	
	
}
