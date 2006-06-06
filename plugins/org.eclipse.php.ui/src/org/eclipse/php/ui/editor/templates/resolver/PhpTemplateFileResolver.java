/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.ui.editor.templates.resolver;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.core.documentModel.PHPEditorModel;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.editor.templates.PhpTemplateContext;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

/**
 * @author seva
 *
 */
public class PhpTemplateFileResolver extends TemplateVariableResolver {
	public PhpTemplateFileResolver() {
		super("file", "resolving a current file name");
	}

	protected String resolve(TemplateContext context) {
		PhpTemplateContext phpTemplateContext = (PhpTemplateContext) context;

		IModelManager modelManager = StructuredModelManager.getModelManager();
		if (modelManager == null) {
			return null;
		}

		IStructuredModel structuredModel = modelManager.getExistingModelForRead(phpTemplateContext.getDocument());
		if (structuredModel == null) {
			return null;
		}

		try {
			PHPEditorModel phpEditorModel = (PHPEditorModel) structuredModel;
			PHPFileData fileData = phpEditorModel.getFileData();
			if (fileData == null) {
				return null;
			}
			String fileName = fileData.getName();
			if (fileName == null || fileName.equals("")) {
				return null;
			}
			return fileName;
		} finally {
			structuredModel.releaseFromRead();
		}
	}

}
