package org.eclipse.php.internal.debug.ui.PropertyTesters;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.ui.IEditorInput;

public class PHPCLILaunchPropertyTester extends PropertyTester {

	private static final String PROPERTY = "launchableCLIPHP"; //$NON-NLS-1$

	/**
	 * Executes the property test determined by the parameter
	 * <code>property</code>.
	 * 
	 * @param receiver
	 *            the receiver of the property test
	 * @param property
	 *            the property to test
	 * @param args
	 *            additional arguments to evaluate the property. If no arguments
	 *            are specified in the <code>test</code> expression an array of
	 *            length 0 is passed
	 * @param expectedValue
	 *            the expected value of the property. The value is either of
	 *            type <code>java.lang.String</code> or a boxed base type. If no
	 *            value was specified in the <code>test</code> expressions then
	 *            <code>null</code> is passed
	 * 
	 * @return returns
	 *         <code>true<code> if the property is equal to the expected value; 
	 *  otherwise <code>false</code> is returned
	 */
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		if (receiver instanceof List<?>) {
			List<?> list = (List<?>) receiver;
			if (list.size() > 0) {
				Object obj = list.get(0);

				if (PROPERTY.equals(property)) {
					if (obj instanceof IEditorInput) {
						return test(DLTKUIPlugin
								.getEditorInputModelElement((IEditorInput) obj));
					} else if (obj instanceof IAdaptable) {
						IResource resource = getResource((IAdaptable) obj);
						if (resource != null
								&& resource.getType() == IResource.FILE) {
							return PHPToolkitUtil.isPhpFile((IFile) resource)
									&& isCLIEnable((IFile) resource);
						}
					}
				}
			}
		}
		return false;
	}

	private IResource getResource(IAdaptable obj) {
		IModelElement modelElement = (IModelElement) ((IAdaptable) obj)
				.getAdapter(IModelElement.class);
		if (modelElement != null) {
			return modelElement.getResource();
		} else {
			return (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
		}
	}

	private boolean test(IModelElement modelElement) {
		return modelElement != null && isCLIEnable(modelElement.getResource())
				&& modelElement.getElementType() == IModelElement.SOURCE_MODULE
				&& PHPToolkitUtil.isPhpElement(modelElement);
	}

	private boolean isCLIEnable(IResource resource) {
		if (resource != null && resource.getProject() != null) {
			if (resource.getProject().getName().trim().length() == 0) {
				return true;
			}
			return PHPProjectPreferences
					.isEnableCLIDebug(resource.getProject());
		} else {
			return PHPProjectPreferences.isEnableCLIDebug(null);
		}
	}
}
