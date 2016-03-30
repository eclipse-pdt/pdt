package org.eclipse.php.internal.debug.core.zend.model;

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.MOD_STATIC;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.actions.IWatchExpressionFactoryAdapter;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionsUtil;

public class WatchExpressionFactoryAdapter implements IWatchExpressionFactoryAdapter {

	private final String STATIC_MEMBER_PATTERN = "{0}::${1}"; //$NON-NLS-1$

	public String createWatchExpression(IVariable variable) throws CoreException {
		if (variable instanceof PHPVariable) {
			PHPVariable phpVariable = (PHPVariable) variable;
			Expression expression = (Expression) phpVariable.getAdapter(Expression.class);
			if (expression.hasFacet(MOD_STATIC)) {
				String className = ExpressionsUtil.fetchStaticMemberClassName(expression);
				return MessageFormat.format(STATIC_MEMBER_PATTERN, className, expression.getLastName());
			}
		}
		IValue value = variable.getValue();
		if (value instanceof PHPValue) {
			PHPValue phpValue = (PHPValue) value;
			return phpValue.getExpression().getFullName();
		}
		return variable.getName();
	}

}