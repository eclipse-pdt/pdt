package org.eclipse.php.internal.core.phpModel.parser;

import java.util.Collection;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public interface IPhpModelFilterable {

	void setFilter(IPhpModelFilter filter);

	public interface IPhpModelFilter {
		Collection filter(CodeData[] elements, Collection filterData);
	}
}
