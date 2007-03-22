/**
 * 
 */
package org.eclipse.php.internal.ui.wizards.fields;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author shachar
 *
 */
public class PasswordDialogField  extends StringDialogField {

	
	/**
	 * Creates or returns the created text control.
	 * @param parent The parent composite or <code>null</code> when the widget has
	 * already been created.
	 */
	public Text getTextControl(Composite parent) {
		if (fTextControl == null) {
			assertCompositeNotNull(parent);
			fModifyListener = new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					doModifyText(e);
				}
			};

			fTextControl = new Text(parent, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
			// moved up due to 1GEUNW2
			fTextControl.setText(fText);
			fTextControl.setFont(parent.getFont());
			fTextControl.addModifyListener(fModifyListener);

			fTextControl.setEnabled(isEnabled());
		}
		return fTextControl;
	}

	

}
