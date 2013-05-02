package org.eclipse.php.internal.debug.ui.preferences;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class BasePathValidator implements IPageControlValidator {

	private String errorMessage;
	private boolean isValid;
	private Text fDefaultBasePath;

	public BasePathValidator(Text fDefaultBasePath) {
		this.fDefaultBasePath = fDefaultBasePath;

	}

	public Control getControl() {

		return fDefaultBasePath;
	}

	public String getErrorMessage() {

		return errorMessage;
	}

	public boolean isValid() {
		return isValid;
	}

	public void validate() {
		if (fDefaultBasePath != null) {
			String textAsString = fDefaultBasePath.getText();
			if (textAsString.equalsIgnoreCase("")) { //$NON-NLS-1$
				this.errorMessage = Messages.BasePathValidator_1;
				this.fDefaultBasePath.setMessage(this.errorMessage);
				isValid = false;
			} else if (!textAsString.startsWith("/")) { //$NON-NLS-1$
				this.errorMessage = Messages.BasePathValidator_3;
				this.fDefaultBasePath.setMessage(this.errorMessage);
				isValid = false;

			} else {
				this.errorMessage = null;
				this.fDefaultBasePath
						.setMessage(Messages.BasePathValidator_4);
				isValid = true;
			}
		} else {
			isValid = false;
		}

	}
}
