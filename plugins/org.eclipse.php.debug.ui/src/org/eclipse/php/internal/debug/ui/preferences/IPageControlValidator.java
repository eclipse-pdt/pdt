package org.eclipse.php.internal.debug.ui.preferences;


public interface IPageControlValidator {

	String getErrorMessage();

	boolean isValid();

	void validate();

}
