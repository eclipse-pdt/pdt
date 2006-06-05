/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.Logger;
import org.eclipse.php.core.util.WeakPropertyChangeListener;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class PHPManual implements IPropertyChangeListener {
	
	private static final String BROWSER_ID = "PHPManual.browser"; //$NON-NLS-1$
	private static int browserCount = 0;
	
	private PHPManualSite site;
	private boolean openManualInNewBrowser;
	private IPreferenceStore fStore;
	
	public PHPManual (PHPManualSite site) {
		this.site = site;
		
		fStore = PHPUiPlugin.getDefault().getPreferenceStore();
		initPreferences();
		fStore.addPropertyChangeListener(WeakPropertyChangeListener.create(this, fStore));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(PreferenceConstants.PHP_MANUAL_OPEN_IN_NEW_BROWSER)) {
			initPreferences();
		}
	}

	public void initPreferences() {
		openManualInNewBrowser = fStore.getBoolean(PreferenceConstants.PHP_MANUAL_OPEN_IN_NEW_BROWSER);
	}

	public PHPManualSite getSite() {
		return site;
	}

	public void setSite(PHPManualSite site) {
		this.site = site;
	}
	
	public void showFunctionHelp (String funcName) {
		if (funcName != null) {
			// Look at the special directors first
			PHPManualDirector director = site.getDirector();
			StringBuffer theURL = new StringBuffer();
			theURL.append(site.getUrl());
			if (!site.getUrl().endsWith("/")) {
				theURL.append("/");
			}
			theURL.append(director.getPath(funcName, site.getExtension()));
			IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
			IWebBrowser browser;
			try {
				if (openManualInNewBrowser) {
					browser = browserSupport.createBrowser(BROWSER_ID + ++browserCount);
				} else {
					browser = browserSupport.createBrowser(BROWSER_ID);
				}
			} catch (PartInitException e) {
				Logger.logException(e);
				return;
			}
			try {
				browser.openURL(new URL(theURL.toString()));
			} catch (PartInitException e) {
				Logger.logException(e);
			} catch (MalformedURLException e) {
				Logger.logException(e);
			}
		}
	}
}
