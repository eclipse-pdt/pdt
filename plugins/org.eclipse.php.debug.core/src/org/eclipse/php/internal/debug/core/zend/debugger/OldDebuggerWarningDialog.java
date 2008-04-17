package org.eclipse.php.internal.debug.core.zend.debugger;

import java.net.URL;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class OldDebuggerWarningDialog extends Dialog {

	public OldDebuggerWarningDialog(Shell parentShell) {
		super(parentShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		getShell().setText("Old Zend Debugger Protocol ID");

		Label label = new Label(composite, SWT.NONE);
		label.setText("The Zend Debugger protocol ID is older than the one you are using.\nSome debugging features may not work properly!\n");
		
		Link link = new Link(composite, SWT.NONE);
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link.setText("To download the latest Zend Debugger version click <a>here</a>."); //$NON-NLS-1$
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
					IWebBrowser browser = browserSupport.getExternalBrowser();
					browser.openURL(new URL("http://www.zend.com/en/products/studio/downloads"));
				} catch (Exception e) {
				}
			}
		});
		link = new Link(composite, SWT.NONE);
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link.setText("To download a PHP web server (includes Zend Debugger) click <a>here</a>.\n"); //$NON-NLS-1$
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
					IWebBrowser browser = browserSupport.getExternalBrowser();
					browser.openURL(new URL("http://www.zend.com/en/products/php-web-server-from-studio"));
				} catch (Exception e) {
				}
			}
		});
		
		final Button button = new Button(composite, SWT.CHECK);
		button.setText("Don't show this message again.");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PHPDebugPlugin.getDefault().getPluginPreferences().setValue("DontShowOlderDebuggerWarning", true);
			}
		});

		return composite;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
	}
}
