package org.eclipse.php.internal.core.phpModel.parser;

import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;

public interface ExternalFilesModel extends IPhpModel {

	public Object getExternalResource(PHPFileData fileData);
}
