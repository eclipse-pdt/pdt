package org.eclipse.php.internal.ui.util;

import java.net.URL;

import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class OpenBrowserUtil {

	/**
	 * Opens the given url in the browser as choosen in the preferences.
	 * 
	 * @param url
	 *            the URL
	 * @param display
	 *            the display
	 * @since 3.6
	 */
	public static void open(final URL url, Display display) {
		display.syncExec(new Runnable() {
			public void run() {
				internalOpen(url, false);
			}
		});
	}

	/**
	 * Opens the given URL in an external browser.
	 * 
	 * @param url
	 *            the URL
	 * @param display
	 *            the display
	 * @since 3.6
	 */
	public static void openExternal(final URL url, Display display) {
		display.syncExec(new Runnable() {
			public void run() {
				internalOpen(url, true);
			}
		});
	}

	private static void internalOpen(final URL url,
			final boolean useExternalBrowser) {
		BusyIndicator.showWhile(null, new Runnable() {
			public void run() {
				URL helpSystemUrl = PlatformUI.getWorkbench().getHelpSystem()
						.resolve(url.toExternalForm(), true);
				try {
					IWorkbenchBrowserSupport browserSupport = PlatformUI
							.getWorkbench().getBrowserSupport();
					IWebBrowser browser;
					if (useExternalBrowser)
						browser = browserSupport.getExternalBrowser();
					else
						browser = browserSupport.createBrowser(null);
					browser.openURL(helpSystemUrl);
				} catch (PartInitException ex) {
					// XXX: show dialog?
					//										PHPUiPlugin.logErrorStatus("Opening Javadoc failed", ex.getStatus()); 
				}
			}
		});
	}

	/**
	 * DO NOT REMOVE, used in a product.
	 * 
	 * @param url
	 *            the URL
	 * @param display
	 *            the display
	 * @param title
	 *            the title
	 * @deprecated As of 3.6, replaced by {@link #open(URL, Display)}
	 */
	public static void open(final URL url, Display display, String title) {
		open(url, display);
	}

}
