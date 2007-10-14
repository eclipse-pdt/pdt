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
package org.eclipse.php.internal.ui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.util.WeakPropertyChangeListener;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class PHPManual implements IPropertyChangeListener {
	
	private static final String BROWSER_ID = "PHPManual.browser"; //$NON-NLS-1$
	private static final Pattern HTTP_URL_PATTERN = Pattern.compile("http://[^\\s]*"); //$NON-NLS-1$
	private static int browserCount = 0;
	
	private PHPManualSite site;
	private boolean openManualInNewBrowser;
	private IPreferenceStore fStore;
	private static Map phpEntityPathMap;
	
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
	
	private synchronized Map getPHPEntityPathMap() {
		if (phpEntityPathMap == null) {
			phpEntityPathMap = new HashMap();
			URL url = FileLocator.find(Platform.getBundle(PHPUiPlugin.getPluginId()), new Path("phpdoc.mapping"), null); //$NON-NLS-1$
			if (url != null) {
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
					String line;
					while ((line = r.readLine()) != null) {
						int sepIdx = line.indexOf('=');
						if (sepIdx != -1) {
							phpEntityPathMap.put(line.substring(0, sepIdx).toLowerCase(), line.substring(sepIdx+1));
						}
					}
				} catch (IOException e) {
				}
			}
		}
		return phpEntityPathMap;
	}
	
	private String buildPathForMethod(String className, String methodName) {
		StringBuffer buf = new StringBuffer();
		buf.append("function."); //$NON-NLS-1$
		if (className != null) {
			buf.append(className);
			buf.append("-"); //$NON-NLS-1$
		}
		buf.append(Pattern.compile("([A-Z])").matcher(methodName).replaceAll("-$1").replaceAll("_", "-")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$)
		return buf.toString().toLowerCase().replaceAll("-+", "-"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * This method tries to determine PHP manual URL for the specified PHP element
	 * 
	 * @param codeData PHP element code data
	 * @return URL for the manual page
	 */
	public String getURLForManual (PHPCodeData codeData) {
		if (codeData == null) {
			throw new IllegalArgumentException();
		}
		
		String path = null;
		
		PHPDocBlock docBlock = codeData.getDocBlock();
		if (docBlock != null) {
			Iterator i = docBlock.getTags(PHPDocTag.LINK);
			while (i.hasNext()) {
				PHPDocTag docTag = (PHPDocTag)i.next();
				Matcher m = HTTP_URL_PATTERN.matcher(docTag.getValue().trim());
				if (m.find()) {
					try {
						URL url = new URL(m.group());
						path = new File(url.getFile()).getName();
						int extIdx = path.lastIndexOf('.');
						if (extIdx > 0) {
							path = path.substring(0, extIdx);
						}
						break;
					} catch (MalformedURLException e) {
					}
				}
			}
		}

		if (path == null) {
			if (codeData instanceof PHPFunctionData) {
				PHPFunctionData funcData = (PHPFunctionData)codeData;
				PHPCodeData container = funcData.getContainer();
				if (container instanceof PHPClassData) {
					String functionName = container.getName() + "::" + funcData.getName(); //$NON-NLS-1$
					path = (String)getPHPEntityPathMap().get(functionName.toLowerCase());
					if (path == null) {
						path = buildPathForMethod(container.getName(),funcData.getName());
					}
				} else {
					path = (String)getPHPEntityPathMap().get(funcData.getName().toLowerCase());
					if (path == null) {
						path = buildPathForMethod(null,funcData.getName());
					}
				}
			} else if (codeData instanceof PHPKeywordData) {
				path = (String)getPHPEntityPathMap().get(codeData.getName().toLowerCase());
			}
		}
		
		if (path == null) {
			return null;
		}
		
		StringBuffer url = new StringBuffer();
		url.append(site.getUrl());
		if (!site.getUrl().endsWith("/")) { //$NON-NLS-1$
			url.append("/"); //$NON-NLS-1$
		}
		url.append(path);
		url.append("."); //$NON-NLS-1$
		url.append(site.getExtension());
		
		return url.toString();
	}
	
	/**
	 * This function launches browser and shows PHP manual page for the specified URL
	 */
	public void showFunctionHelp(String url) {
		IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
		IWebBrowser browser;
		try {
			if (openManualInNewBrowser) {
				browser = browserSupport.createBrowser(BROWSER_ID + ++browserCount);
			} else {
				browser = browserSupport.createBrowser(BROWSER_ID);
			}
			
			if (url.startsWith("mk:")) { //$NON-NLS-1$
                browser.openURL(new URL(null, url, new MkHandler()));
            } else {
                browser.openURL(new URL(url));
            }
			
		} catch (PartInitException e) {
			Logger.logException(e);
		} catch (MalformedURLException e) {
			Logger.logException(e);
		}
	}
	
	private class MkHandler extends URLStreamHandler {
        protected URLConnection openConnection(URL arg0) throws IOException {
            return null;
        }
    }
}
