package org.eclipse.php.internal.ui.corext;

/**
 * A source range defines an element's source coordinates relative to
 * its source buffer.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 */
public interface ISourceRange {

/**
 * Returns the number of characters of the source code for this element,
 * relative to the source buffer in which this element is contained.
 * 
 * @return the number of characters of the source code for this element,
 * relative to the source buffer in which this element is contained
 */
int getLength();
/**
 * Returns the 0-based index of the first character of the source code for this element,
 * relative to the source buffer in which this element is contained.
 * 
 * @return the 0-based index of the first character of the source code for this element,
 * relative to the source buffer in which this element is contained
 */
int getOffset();
}
