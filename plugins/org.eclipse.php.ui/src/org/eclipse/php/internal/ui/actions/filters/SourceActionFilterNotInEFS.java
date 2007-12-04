package org.eclipse.php.internal.ui.actions.filters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.actions.filters.IActionFilterContributor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;

public class SourceActionFilterNotInEFS implements IActionFilterContributor {

	public boolean testAttribute(Object target, String name, String value) {
		String fileName = null;
		if (target instanceof NodeImpl) {
			IStructuredDocument structuredDocument = ((NodeImpl) target).getStructuredDocument();
			IStructuredModel existingModelForRead = StructuredModelManager.getModelManager().getExistingModelForRead(structuredDocument);
			try {
				if (existingModelForRead instanceof DOMModelForPHP) {
					PHPFileData fileData = ((DOMModelForPHP) existingModelForRead).getFileData();
					if (fileData == null) {
						return false;
					}
					fileName = fileData.getName();
				}
			} finally {
				if (existingModelForRead != null) {
					existingModelForRead.releaseFromRead();
				}
			}
		}
		if (target instanceof PHPCodeData) {
			PHPCodeData codeData = (PHPCodeData) target;
			if (!codeData.isUserCode()) {
				return false;
			}
			fileName = (codeData).getUserData().getFileName();
		}
		// file is not in EFS, e.g include path
		IFile checkFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
		if (checkFile.exists()) {
			return true;
		}
		return false;
	}

}
