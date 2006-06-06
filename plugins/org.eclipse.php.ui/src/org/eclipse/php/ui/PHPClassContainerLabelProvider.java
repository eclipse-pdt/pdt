/**
 * 
 */
package org.eclipse.php.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.util.PHPElementImageProvider;
import org.eclipse.swt.graphics.Image;

public class PHPClassContainerLabelProvider extends LabelProvider {

	// TODO getImage

	public Image getImage(Object element) {
		Object imageElement = PHPModelUtil.getResource(element);
		if (imageElement == null) {
			imageElement = element;
		}
		return (new PHPElementImageProvider()).getImageLabel(imageElement, 0);
	}

	public String getText(Object element, StringBuffer buf) {

		if (element instanceof PHPFileData) {
			String label = ((PHPFileData) element).getName();
			IFile file = (IFile) PHPModelUtil.getResource(element);
			buf.ensureCapacity(buf.capacity() + label.length());
			buf.insert(0, label);
			if (file == null) {
				return buf.toString();
			}
			return buf.toString();
		}
		if (element instanceof PHPClassData) {
			PHPClassData classData = (PHPClassData) element;
			PHPCodeData container;
			if ((container = classData.getContainer()) != null) {
				return getText(container, buf);
			}
		}
		return "";

	}

	public String getText(Object element) {
		return getText(element, new StringBuffer());
	}
}