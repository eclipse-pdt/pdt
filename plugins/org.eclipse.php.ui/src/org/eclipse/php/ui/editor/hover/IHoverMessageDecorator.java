package org.eclipse.php.ui.editor.hover;

/**
 * Decorates hover message 
 * 
 * @author eden
 */
public interface IHoverMessageDecorator {

	/**
	 * Gets the hover message and returns the decorated format 
	 * 
	 * @param editorPart
	 */
	public String getDecoratedMessage(String msg);

}
