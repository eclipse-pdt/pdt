package org.eclipse.php.ui.editor.hover;

/**
 * This interface will be implemented in order to use hoverMessageDecorators extension point. 
 * 
 * @author eden
 */
public interface IHoverMessageDecorators {

	/**
	 * Gets the hover message and returns the decorated format 
	 * 
	 * @param editorPart
	 */
	public String getDecoratedMessage(String msg);

}
