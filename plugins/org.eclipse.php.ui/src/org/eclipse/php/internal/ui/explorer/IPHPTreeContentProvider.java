package org.eclipse.php.internal.ui.explorer;

import java.util.ArrayList;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * An interface to add additional elements to the PHP project explorer.
 * 
 * Use the phpTreeContentProviders extension-point to use this interface.
 * 
 * @since 3.1.2
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public interface IPHPTreeContentProvider {

	/**
	 * Add an {@link IAdaptable} to children to display an additional node in
	 * the root of the PHPExplorer
	 * 
	 * @param children
	 *            the existing root nodes
	 * @param scriptProject
	 *            the {@link IScriptProject} to add the node to
	 */
	void handleProjectChildren(ArrayList<Object> children,
			IScriptProject scriptProject);

	/**
	 * Return true if this {@link IPHPTreeContentProvider} can return children
	 * for the given parentElement
	 * 
	 * @param parentElement
	 * @return
	 */
	boolean canHandle(Object parentElement);

	/**
	 * Return the children for the parentElement
	 * 
	 * @param parentElement
	 * @return
	 */
	Object[] handleChildren(Object parentElement);

	/**
	 * Return the tree-text for a given element or null if if can't provide a
	 * text.
	 * 
	 * @param element
	 * @return String | null
	 */
	String getText(Object element);

	/**
	 * Return the {@link Image} for a given tree element or null if it can't
	 * provide an image.
	 * 
	 * @param element
	 * @return
	 */
	Image getImage(Object element);

}
