package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ti.types.IEvaluatedType;

public interface IArgumentsContext {

	IEvaluatedType getArgumentType(String name);

}
