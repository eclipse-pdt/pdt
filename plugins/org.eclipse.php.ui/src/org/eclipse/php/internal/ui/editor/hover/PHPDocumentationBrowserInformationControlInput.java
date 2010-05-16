package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.internal.text.html.BrowserInformationControlInput;

/**
 * Browser input for Javadoc hover.
 * 
 * @since 3.4
 */
public class PHPDocumentationBrowserInformationControlInput extends
		BrowserInformationControlInput {

	private final IModelElement fElement;
	private final String fHtml;
	private final int fLeadingImageWidth;

	/**
	 * Creates a new browser information control input.
	 * 
	 * @param previous
	 *            previous input, or <code>null</code> if none available
	 * @param element
	 *            the element, or <code>null</code> if none available
	 * @param html
	 *            HTML contents, must not be null
	 * @param leadingImageWidth
	 *            the indent required for the element image
	 */
	public PHPDocumentationBrowserInformationControlInput(
			PHPDocumentationBrowserInformationControlInput previous,
			IModelElement element, String html, int leadingImageWidth) {
		super(previous);
		Assert.isNotNull(html);
		fElement = element;
		fHtml = html;
		fLeadingImageWidth = leadingImageWidth;
	}

	/*
	 * @seeorg.eclipse.jface.internal.text.html.BrowserInformationControlInput#
	 * getLeadingImageWidth()
	 * 
	 * @since 3.4
	 */
	public int getLeadingImageWidth() {
		return fLeadingImageWidth;
	}

	/**
	 * Returns the Java element.
	 * 
	 * @return the element or <code>null</code> if none available
	 */
	public IModelElement getElement() {
		return fElement;
	}

	/*
	 * @see org.eclipse.jface.internal.text.html.BrowserInput#getHtml()
	 */
	public String getHtml() {
		return fHtml;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.infoviews.BrowserInput#getInputElement()
	 */
	public Object getInputElement() {
		return fElement == null ? (Object) fHtml : fElement;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.infoviews.BrowserInput#getInputName()
	 */
	public String getInputName() {
		return fElement == null ? "" : fElement.getElementName(); //$NON-NLS-1$
	}

}
