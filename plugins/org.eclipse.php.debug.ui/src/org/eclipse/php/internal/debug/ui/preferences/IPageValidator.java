package org.eclipse.php.internal.debug.ui.preferences;


public interface IPageValidator {
	public void validate(IPageControlValidator pageValidator)
			throws ControlValidationException;
}
