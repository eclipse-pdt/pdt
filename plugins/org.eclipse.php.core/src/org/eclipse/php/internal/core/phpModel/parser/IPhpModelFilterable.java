package org.eclipse.php.internal.core.phpModel.parser;

import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

public interface IPhpModelFilterable extends IPhpModel {

	void setFilter(IPhpModelFilter filter);

	CodeData[] getFilteredClasses(String fileName, String className);

	CodeData[] getFilteredFunctions(String fileName, String className);

	CodeData[] getFilteredConstants(String fileName, String className);

	public interface IPhpModelFilter extends IWorkspaceModelListener {
		boolean select(IPhpModelFilterable model, CodeData element, Object data);
	}
}
