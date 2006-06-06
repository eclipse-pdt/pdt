package org.eclipse.php.core.documentModel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.php.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class PHPFileVisitor implements IResourceProxyVisitor {

	protected List fFiles = new ArrayList();
	//	    private IContentType fContentTypeJSP = null;
	protected IReporter fReporter = null;

	public PHPFileVisitor(IReporter reporter) {
		fReporter = reporter;
	}

	public boolean visit(IResourceProxy proxy) throws CoreException {

		// check validation
		if (fReporter.isCancelled())
			return false;

		if (proxy.getType() == IResource.FILE) {
			IFile file = (IFile) proxy.requestResource();
			if (file.exists()) {
				if (canHandle(file)) {
					fFiles.add(file);
					// don't search deeper for files
					return false;
				}
			}
		}
		return true;
	}

	// Simple check for php file. When create PHP file wizard exist should be able 
	// to do a context check on file. 

	protected boolean canHandle(IFile file) {
		boolean result = false;
		if (file != null) {
			try {
				IContentTypeManager contentTypeManager = Platform.getContentTypeManager();

				IContentDescription contentDescription = file.getContentDescription();
				IContentType phpContentType = contentTypeManager.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
				if (contentDescription != null) {
					IContentType fileContentType = contentDescription.getContentType();

					if (phpContentType != null) {
						if (fileContentType.isKindOf(phpContentType)) {
							result = true;
						}
					}
				} else if (phpContentType != null) {
					result = phpContentType.isAssociatedWith(file.getName());
				}
			} catch (CoreException e) {
				// should be rare, but will ignore to avoid logging "encoding
				// exceptions" and the like here.
				// Logger.logException(e);
			}
		}
		return result;
	}

	public final IFile[] getFiles() {
		return (IFile[]) fFiles.toArray(new IFile[fFiles.size()]);
	}
}