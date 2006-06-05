package org.eclipse.php.core.phpModel.parser;

import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;

public interface ExternalFilesModel extends IPhpModel {

	public Object getExternalResource(PHPFileData fileData);
}
