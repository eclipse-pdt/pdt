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
			if (textAsString.equalsIgnoreCase("")) {
				this.errorMessage = "Base Path cannot contain an empty value for root type '/'";
				this.fDefaultBasePath.setMessage(this.errorMessage);
				isValid = false;
			} else if (!textAsString.startsWith("/")) {
				this.errorMessage = "Base Path must start with '/' prefix";
				this.fDefaultBasePath.setMessage(this.errorMessage);
				isValid = false;

			} else {
				this.errorMessage = null;
				this.fDefaultBasePath
						.setMessage("Example Path: '/<projectName>' | /<path> | '/'");
				isValid = true;
			}
		} else {
			isValid = false;
		}

	}
}
