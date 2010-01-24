package org.eclipse.php.internal.debug.core.zend.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.actions.IWatchExpressionFactoryAdapter;

public class WatchExpressionFactoryAdapter implements
		IWatchExpressionFactoryAdapter {

	public String createWatchExpression(IVariable variable)
			throws CoreException {

		IValue value = variable.getValue();
		if (value instanceof PHPValue) {
			PHPValue phpValue = (PHPValue) value;
			return phpValue.getVariable().getFullName();
		}
		return variable.getName();

	}
}