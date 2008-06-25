/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui.editor.templates.resolver;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateContext;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

/**
 * @author seva
 *
 */
public class PhpTemplateFileResolver extends TemplateVariableResolver {
	public PhpTemplateFileResolver() {
		super(PHPUIMessages.getString("PhpTemplateFileResolver.0"), PHPUIMessages.getString("PhpTemplateFileResolver.1")); //$NON-NLS-1$ //$NON-NLS-2$
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
			DOMModelForPHP phpDOMModel = (DOMModelForPHP) structuredModel;
			IFile iFile = phpDOMModel.getIFile();
			if (iFile == null) {
				return null;
			}
			String fileName = iFile.getName();
			if (fileName == null || fileName.equals("")) { //$NON-NLS-1$
				return null;
			}
			return fileName;
		} finally {
			structuredModel.releaseFromRead();
		}
	}

}
