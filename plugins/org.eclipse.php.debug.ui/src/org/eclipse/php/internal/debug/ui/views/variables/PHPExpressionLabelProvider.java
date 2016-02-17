/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.views.variables;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.internal.ui.model.elements.ExpressionLabelProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.php.internal.debug.core.model.IPHPDataType;
import org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType;

/**
 * PHP watch expression label provider.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class PHPExpressionLabelProvider extends ExpressionLabelProvider {

	private static final String QUOTED_STRING = "\"{0}\""; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.model.elements.VariableLabelProvider#
	 * getValueText(org.eclipse.debug.core.model.IVariable,
	 * org.eclipse.debug.core.model.IValue,
	 * org.eclipse.debug.internal.ui.viewers.model.provisional.
	 * IPresentationContext)
	 */
	@Override
	protected String getValueText(IVariable variable, IValue value, IPresentationContext context) throws CoreException {
		if (value instanceof IPHPDataType) {
			IPHPDataType dataType = (IPHPDataType) value;
			if (dataType.getDataType() == DataType.PHP_STRING) {
				return MessageFormat.format(QUOTED_STRING, escapeSpecialChars(value.getValueString()));
			}
		}
		return super.getValueText(variable, value, context);
	}

}
