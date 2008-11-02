package org.eclipse.php.internal.ui.functions;

import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for the PHP Functions view. Inherits all its behavior from the ScriptUILabelProvider
 * but adds special handling for the Constants Node 
 * @author Eden K., 2008
 *
 */
public class PHPFunctionsLabelProvider extends ScriptUILabelProvider {

	/**
	 * Creates a new label provider with default flags.
	 */
	public PHPFunctionsLabelProvider() {
		this(ScriptElementLabels.ALL_DEFAULT, ScriptElementImageProvider.OVERLAY_ICONS);
	}

	/**
	 * @param textFlags
	 *            Flags defined in <code>ScriptElementLabels</code>.
	 * @param imageFlags
	 *            Flags defined in <code>ScriptElementImageProvider</code>.
	 */
	public PHPFunctionsLabelProvider(long textFlags, int imageFlags) {
		super(textFlags, imageFlags);
		fImageLabelProvider = new ScriptElementImageProvider();
	}

	public String getText(Object element) {
		if (element instanceof ConstantNode) {
			return ((ConstantNode) element).getName();
		}
		return super.getText(element);
	}
	
	public Image getImage(Object element) {
		if(element instanceof ConstantNode){
			return PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP.createImage();					
		}
		return super.getImage(element);
	}

}
